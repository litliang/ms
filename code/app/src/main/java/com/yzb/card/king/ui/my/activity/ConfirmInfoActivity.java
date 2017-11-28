package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.creditcard.CancelBaseOrder;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.bean.Bank;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.BaseOrder;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.my.cotroller.CreateTradeOrder;
import com.yzb.card.king.ui.my.enu.OrderType;
import com.yzb.card.king.ui.other.listeners.ForgetPwdImpl;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.wallet.logic.pay.TransferHandle;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.PayFailBackListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：确认转账信息
 * 作者：殷曙光
 * 日期：2016/12/28 10:41
 */
@ContentView(R.layout.activity_confirm_info)
public class ConfirmInfoActivity extends BaseActivity
{
    private static final int REQ_TRANSFER_SUCCESS = 2;
    public static final int REQ_ADD_BANK_CARD = 3;

    @ViewInject(R.id.tvName)
    private TextView tvName;

    @ViewInject(R.id.ivBankLogo)
    private ImageView ivBankLogo;

    @ViewInject(R.id.tvCardName)
    private TextView tvCardName;

    @ViewInject(R.id.tvAmount)
    private TextView tvAmount;

    @ViewInject(R.id.tvFee)
    private TextView tvFee;

    @ViewInject(R.id.tvTimeTable)
    private TextView tvTimeTable;

    @ViewInject(R.id.etComments)
    private EditText etComments;

    private String name;
    private Bank bank;
    private String amount;
    private String time;
    private String creditNo;
    private Payee payee;
    private TransferHandle payHandle;
    private Long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData()
    {
        setTitleNmae("确认转账信息");
        getIntentData();
        setViewData();
        loadFee();
    }

    private void loadFee()
    {
        SimpleRequest<Map<String, String>> request =
                new SimpleRequest<Map<String, String>>("QueryBankServiceRate")
                {
                    @Override
                    protected Map<String, String> parseData(String data)
                    {
                        return JSON.parseObject(data, Map.class);
                    }
                };
        Map<String, Object> param = new HashMap<>();
        param.put("bankId", bank.getBankId());
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<Map<String, String>>()
        {
            @Override
            public void onSuccess(Map<String, String> data)
            {
                Float price = Float.parseFloat(amount) * Float.parseFloat(data.get("rate"));
                if (price > 0)
                {
                    tvFee.setText(UiUtils.getString(R.string.wallet_fee, price));
                } else
                {
                    tvFee.setText("免服务费");
                }
            }

            @Override
            public void onFail(Error error)
            {
                tvFee.setText("免服务费");
            }
        });
    }

    private void setViewData()
    {
        tvName.setText(name);
        x.image().bind(ivBankLogo, ServiceDispatcher.getImageUrl(bank.getBankLogo()));
        tvCardName.setText(UiUtils.getString(R.string.transfer_cardName_tailNum, bank.getBankName(), getTailNo(creditNo)));
        tvAmount.setText(UiUtils.getString(R.string.string_price, amount));
        tvFee.setText("");
        tvTimeTable.setText(time);
    }

    private void getIntentData()
    {
        name = getIntent().getStringExtra("name");
        bank = (Bank) getIntent().getSerializableExtra("bank");
        amount = getIntent().getStringExtra("amount");
        time = getIntent().getStringExtra("time");
        creditNo = getIntent().getStringExtra("creditNo");
        payee = (Payee) getIntent().getSerializableExtra("payee");
    }

    private String getTailNo(String cardNo)
    {
        if (!TextUtils.isEmpty(cardNo) && cardNo.length() > 4)
        {
            return cardNo.substring(cardNo.length() - 4, cardNo.length());
        }
        return null;
    }

    @Event(R.id.btNext)
    private void next(View view)
    {
        createBaseOrder();

    }

    private void createBaseOrder()
    {
        SimpleRequest<BaseOrder> request = new SimpleRequest<BaseOrder>(CardConstant.CREATE_BASE_ORDERS)
        {
            @Override
            protected BaseOrder parseData(String data)
            {
                return JSON.parseObject(data, BaseOrder.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("creditId", payee.getCreditId());//收款方id
        param.put("amount", amount);//订单金额
        param.put("intro", "银行卡转账");//订单信息
        param.put("orderType", OrderType.TRANSFER.getValue());//订单类型
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<BaseOrder>()
        {
            @Override
            public void onSuccess(BaseOrder data)
            {
                orderId = data.getOrderId();
                transfer(data.getOrderNo(),data.getNotifyUrl());
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }


    private void transfer(String orderNo,String url)
    {
        UserBean userBean = UserManager.getInstance().getUserBean();
        Map<String, String> params = new HashMap<String, String>();
        params.put("amount", amount);
        params.put("orderNo", orderNo);
        params.put("goodsName", "转账");
        params.put("transIp", AppUtils.getLocalIpAddress(this));

        params.put("debitMobile", userBean.getAccount());
        params.put("creditType", "0");
        params.put("creditCardName", name);
        params.put("creditCardNo", creditNo);
        params.put("bankMark",payee.getBankMark());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);
        params.put("notifyUrl", url);
        LogUtil.e("转账参数："+JSON.toJSONString(params));

        // 初始化
        payHandle = new TransferHandle(ConfirmInfoActivity.this);

        // 转账操作 显示"忘记密码"：是true 否false或null
        payHandle.pay(params, true);

        payHandle.payFailCallBack(new PayFailBackListener()
        {
            @Override
            public void callBack()
            {
                cancelOrder();
            }
        });
        // 操作结果
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                LogUtil.e("=返回结果=>code" + RESULT_CODE);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                LogUtil.e("=返回结果=>code" + RESULT_CODE + "返回数据=>" + resultMap);
                createOrder(resultMap);
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                LogUtil.e("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                cancelOrder();
                UiUtils.shortToast(ERROR_MSG);
            }
        });
        // 忘记密码回掉
        payHandle.setForgetPwdCallBack(new ForgetPwdImpl());

        payHandle.setAddCardCallBack(new AddCardBackListener()
        {

            @Override
            public void callBack()
            {
                startActivityForResult(new Intent(ConfirmInfoActivity.this, AddBankCardActivity.class),
                        REQ_ADD_BANK_CARD);
            }
        });
    }

    private void cancelOrder()
    {
        new CancelBaseOrder(orderId);
    }

    private void nextActivity()
    {
        Intent intent = new Intent(this, TransSuccActivity.class);
        intent.putExtra("amount", amount);
        PayMethod payMethod = new PayMethod();
        payMethod.setBankName(bank.getBankName());
        payMethod.setName(name);
        payMethod.setSortNo(getTail());
        intent.putExtra("payMethod", payMethod);
        startActivityForResult(intent, REQ_TRANSFER_SUCCESS);
    }

    private String getTail()
    {
        if (!TextUtils.isEmpty(creditNo) && creditNo.length() > 4)
            return creditNo.substring(creditNo.length() - 4);
        return "";
    }

    private void createOrder(Map<String, String> resultMap)
    {
        if (resultMap == null) return;
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(resultMap);
        param.put("orderType", OrderType.TRANSFER.getValue());//1充值；2转账；3提现；
        param.put("orderId", orderId);
        param.put("orderAmount", amount);
        param.put("tradeObject", "2");//1平台账户；2银行卡
//        param.put("creditId", String.valueOf(payee.getCreditId()));//收款人id  平台账户为账号id，储蓄卡时为银行id
        param.put("creditBankId", payee.getCreditId());//收款方银行id
        param.put("creditBankNo", payee.getTradeAccount().replace(" ",""));//收款方银行卡号
        param.put("creditBankPerson", payee.getCreditName());//收款方银行户主

        param.put("remark", etComments.getText().toString());

        new CreateTradeOrder(param, new BaseCallBack<Map<String, String>>()
        {
            @Override
            public void onSuccess(Map<String, String> data)
            {
                nextActivity();
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    @Event(R.id.tvTimeTable)
    private void timeTable(View view)
    {
        startActivity(new Intent(this, TimeTableActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_TRANSFER_SUCCESS:
                    setResult(RESULT_OK);
                    finish();
                    break;
                case REQ_ADD_BANK_CARD:
                    payHandle.showAddCardSuccess();
                    break;
            }
        }
    }
}
