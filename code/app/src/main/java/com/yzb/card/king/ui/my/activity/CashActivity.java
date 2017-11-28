package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.creditcard.CancelBaseOrder;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.BaseOrder;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.cotroller.CreateTradeOrder;
import com.yzb.card.king.ui.my.enu.AccountType;
import com.yzb.card.king.ui.my.enu.OrderType;
import com.yzb.card.king.http.creditcard.GetFirstPayWay;
import com.yzb.card.king.ui.other.listeners.ForgetPwdImpl;
import com.yzb.card.king.ui.other.listeners.MoneyFormatWatcher;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.wallet.logic.pay.SettleHandle;
import com.yzb.wallet.openInterface.PayFailBackListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：提现
 * 作者：殷曙光
 * 日期：2016/12/28 14:47
 */
@ContentView(R.layout.activity_cash)
public class CashActivity extends BaseActivity
{

    private static final int REQ_SELECT_BANK_CARD = 1;
    private static final int REQ_CASH_SUCCESS = 2;

    private PayMethod payMethod;

    @ViewInject(R.id.ivBankLogo)
    private ImageView ivBankLogo;

    @ViewInject(R.id.tvTailNum)
    private TextView tvTailNum;

    @ViewInject(R.id.tvUserName)
    private TextView tvUserName;

    @ViewInject(R.id.tvBankName)
    private TextView tvBankName;

    @ViewInject(R.id.etAmount)
    private EditText etAmount;

    @ViewInject(R.id.tvLeftAmount)
    private TextView tvLeftAmount;

    @ViewInject(R.id.btnSubmit)
    private Button btnSubmit;
    private Long orderId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    protected void initView()
    {
        btnSubmit.setEnabled(false);

        MoneyFormatWatcher watcher = new MoneyFormatWatcher()
        {
            @Override
            public void submitAble(boolean submitAble)
            {
                btnSubmit.setEnabled(submitAble);
            }
        };
        etAmount.addTextChangedListener(watcher);
    }

    protected void initData()
    {
        setTitleNmae("提现");
        getIntentData();
        loadDefaultMethod();
    }


    private void loadDefaultMethod()
    {
        new GetFirstPayWay(this, GetFirstPayWay.DEBIT, new GetFirstPayWay.OnPayWayGetListener()
        {
            @Override
            public void onGet(PayMethod payMethod)
            {
                CashActivity.this.payMethod = payMethod;
                setBankInfo();
            }

            @Override
            public void onFail(String msg)
            {
                UiUtils.shortToast(msg);
            }
        });
    }

    private void getIntentData()
    {
        String amount = getIntent().getStringExtra("amount");
        tvLeftAmount.setText(amount);
    }

    @Event(R.id.rlBankCard)
    private void selectBankCard(View v)
    {
        Intent intent = new Intent(this, SelectCardActivity.class);
        startActivityForResult(intent, REQ_SELECT_BANK_CARD);
    }

    @Event(R.id.btnSubmit)
    private void submit(View view)
    {
        if (payMethod != null)
        {
            createBaseOrder();
        } else
        {
            UiUtils.shortToast("请选择到账银行卡");
        }
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
        param.put("amount", etAmount.getText().toString());//订单金额
        param.put("intro", "提现");//订单信息
        param.put("orderType", OrderType.CASH.getValue());//订单类型
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<BaseOrder>()
        {
            @Override
            public void onSuccess(BaseOrder data)
            {
                orderId = data.getOrderId();
                cash();
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }


    private void cash()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("accountType", getAccountType());
        params.put("amount", etAmount.getText().toString());
        params.put("goodsName", "提现");
        params.put("sortCode", payMethod.getSortCode());
        params.put("transIp", AppUtils.getLocalIpAddress(this));

        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);

        SettleHandle payHandle = new SettleHandle(CashActivity.this);
        payHandle.pay(params, true);
        payHandle.payFailCallBack(new PayFailBackListener()
        {
            @Override
            public void callBack()
            {
                cancelOrder();
            }
        });
        payHandle.setForgetPwdCallBack(new ForgetPwdImpl());
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String s)
            {
                createOrder();
            }

            @Override
            public void setSuccess(Map<String, String> map, String s)
            {
                LogUtil.e("第二个回调" + s);
            }

            @Override
            public void setError(String s, String s1)
            {
                UiUtils.shortToast(s1);
            }
        });
    }

    private void cancelOrder()
    {
        new CancelBaseOrder(orderId);
    }

    /**
     * 获取账户类型；
     */
    protected String getAccountType()
    {
        return AccountType.CASH.getValue();
    }

    private void nextActivity()
    {
        Intent intent = new Intent(this, CashSuccActivity.class);
        intent.putExtra("amount", etAmount.getText().toString());
        intent.putExtra("payMethod", payMethod);
        startActivityForResult(intent, REQ_CASH_SUCCESS);
    }


    /**
     * 创建交易订单；
     */
    private void createOrder()
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderType", OrderType.CASH.getValue());//1充值；2转账；3提现；
        param.put("orderId", orderId);
        param.put("orderAmount", etAmount.getText().toString());
        param.put("tradeObject", "2");//1平台账户；2银行卡
        param.put("withdrawType", getWithdrawType());//提现类型；1钱包，2红包

        param.put("payMethod", getPayMethod());//付款方式 1钱包余额；2信用卡；3储蓄卡；4红包余额

//        param.put("creditId", String.valueOf(payMethod.getBankId()));//收款人id  平台账户为账号id，储蓄卡时为银行id
        param.put("creditBankId", payMethod.getBankId());//收款方银行id
        param.put("creditBankNo", payMethod.getBankNo());//收款方银行卡号
        param.put("creditBankPerson", payMethod.getName());//收款方银行户主

        param.put("remark", "");

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

    protected String getWithdrawType()
    {
        return "1";
    }

    protected String getPayMethod()
    {
        return "1";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_SELECT_BANK_CARD:
                    payMethod = (PayMethod) data.getSerializableExtra("payMethod");
                    setBankInfo();
                    break;
                case REQ_CASH_SUCCESS:
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }

    private void setBankInfo()
    {
        if (payMethod != null)
        {
            x.image().bind(ivBankLogo, ServiceDispatcher.getImageUrl(payMethod.getLogo()));

            tvBankName.setText(payMethod.getBankName());

            if(payMethod.getCardType() == 1){

                tvUserName.setText("储蓄卡");

            }else {
                tvUserName.setText("信用卡");
            }

            tvTailNum.setText("("+payMethod.getSortNo()+")");

            ivBankLogo.setVisibility(View.VISIBLE);
        }else {

            ivBankLogo.setVisibility(View.GONE);

            tvBankName.setText("请绑定银行卡（储蓄卡）");
        }
    }
}
