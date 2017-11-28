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
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.creditcard.CancelBaseOrder;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.BaseOrder;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.my.cotroller.CreateTradeOrder;
import com.yzb.card.king.ui.my.enu.OrderType;
import com.yzb.card.king.ui.other.listeners.ForgetPwdImpl;
import com.yzb.card.king.ui.other.listeners.MoneyFormatWatcher;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
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
 * 描述：转到嗨生活
 * 作者：殷曙光
 * 日期：2016/12/27 9:56
 */
@ContentView(R.layout.activity_hi_life_transfer)
public class HiLifeTransferActivity extends BaseActivity
{

    private static final int REQ_ADD_BANK_CARD = 2;
    @ViewInject(R.id.headerRight)
    private View headerRight;

    @ViewInject(R.id.headerLeft)
    private View headerLeft;

    @ViewInject(R.id.ivPhoto)
    private ImageView ivPhoto;

    @ViewInject(R.id.tvName)
    private TextView tvName;

    @ViewInject(R.id.tvAccount)
    private TextView tvAccount;

    @ViewInject(R.id.etAmount)
    private EditText etAmount;

    @ViewInject(R.id.btnSubmit)
    private Button btnSubmit;

    @ViewInject(R.id.etComments)
    private EditText etComments;

    private Payee payee;
    private TransferHandle payHandle;
    private Long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        getIntentData();
        initView();
        initListener();
        initData();
    }

    private void initListener()
    {
        etAmount.addTextChangedListener(new MoneyFormatWatcher()
        {
            @Override
            public void submitAble(boolean submitAble)
            {
                btnSubmit.setEnabled(submitAble);
            }
        });
    }

    @Event(R.id.headerRight)
    private void headerRight(View v)
    {
        Intent intent = new Intent(this, TransRecordActivity.class);
        intent.putExtra("payee",payee);
        startActivity(intent);
    }

    @Event(R.id.headerLeft)
    private void headerLeft(View v)
    {
        finish();
    }

    @Event(R.id.btnSubmit)
    private void transfer(View view)
    {
        createBaseOrder();
    }

    private void sendTransRequest(String orderNo,String url)
    {
        UserManager userManager = UserManager.getInstance();
        UserBean userBean = userManager.getUserBean();
        Map<String, String> params = new HashMap<String, String>();

        params.put("orderNo", orderNo);
        params.put("fee", "0");

        params.put("amount", etAmount.getText().toString());
        params.put("goodsName", "转账");
        params.put("transIp", AppUtils.getLocalIpAddress(this));

        params.put("debitMobile", userBean.getAccount());//付款方

        params.put("creditMobile", payee.getTradeAccount());//收款方手机
        params.put("creditType", "1");
        params.put("creditCardName", payee.getCreditName());

        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);
        params.put("notifyUrl", url);

        LogUtil.e("转账参数：" + JSON.toJSONString(params));
        // 初始化
        payHandle = new TransferHandle(HiLifeTransferActivity.this);

        // 转账操作 显示"忘记密码"：是true 否false或null
        payHandle.pay(params, true);

        // 操作结果
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE + "返回数据=>" + resultMap);
                createOrder(resultMap);
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                UiUtils.shortToast(ERROR_MSG);
                cancelOrder();
            }
        });
        // 忘记密码回掉
        payHandle.setForgetPwdCallBack(new ForgetPwdImpl());

        payHandle.setAddCardCallBack(new AddCardBackListener()
        {
            @Override
            public void callBack()
            {
                startActivityForResult(new Intent(HiLifeTransferActivity.this, AddBankCardActivity.class),
                        REQ_ADD_BANK_CARD);
            }
        });

        payHandle.payFailCallBack(new PayFailBackListener()
        {
            @Override
            public void callBack()
            {
                cancelOrder();
            }
        });
    }

    private void cancelOrder()
    {
        new CancelBaseOrder(orderId);
    }


    private void nextActivity()
    {
        Intent intent = new Intent(this, TransRecordActivity.class);
        intent.putExtra("payee",payee);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
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
        param.put("amount", etAmount.getText().toString());//订单金额
        param.put("intro", "平台转账");//订单信息
        param.put("orderType", OrderType.TRANSFER.getValue());//订单类型
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<BaseOrder>()
        {
            @Override
            public void onSuccess(BaseOrder data)
            {
                orderId = data.getOrderId();
                sendTransRequest(data.getOrderNo(),data.getNotifyUrl());
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void createOrder(Map<String, String> resultMap)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderType", OrderType.TRANSFER.getValue());//1充值；2转账；3提现；
        param.put("orderId", orderId);
        param.put("orderAmount", etAmount.getText().toString());
        param.put("tradeObject", "1");//1平台账户；2银行卡

        param.put("payMethod", "1");//付款方式 1钱包余额；2信用卡；3储蓄卡；4红包余额
//        param.put("debitBankId", resultMap.get("debitBankId"));//付款方银行id
//        param.put("debitBankNo", resultMap.get("debitBankNo"));//付款方银行卡号
//        param.put("debitBankPerson", resultMap.get("debitBankPerson"));//付款方银行户主

        param.put("creditId", String.valueOf(payee.getCreditId()));//收款人id  平台账户为账号id，储蓄卡时为银行id
        param.put("creditBankId", payee.getBankId());//收款方银行id
        param.put("creditBankNo", payee.getBankNo());//收款方银行卡号
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

    private void initView()
    {
        btnSubmit.setEnabled(false);
    }

    private void initData()
    {
        x.image().bind(ivPhoto, payee.getPhotoUrl());
        tvName.setText(payee.getCreditName());
        tvAccount.setText(RegexUtil.hide4PhoneNum(payee.getTradeAccount()));
    }

    private void getIntentData()
    {
        payee = (Payee) getIntent().getSerializableExtra("payee");
        if (payee == null)
        {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_ADD_BANK_CARD:
                    payHandle.showAddCardSuccess();
                    break;
            }
        }
    }
}
