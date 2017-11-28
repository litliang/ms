package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.yzb.wallet.logic.pay.RechargeHandle;
import com.yzb.wallet.openInterface.PayFailBackListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：充值
 * 作者：殷曙光
 * 日期：2016/12/28 14:45
 */
@ContentView(R.layout.activity_charge)
public class ChargeActivity extends BaseActivity
{

    @ViewInject(R.id.ivBankLogo)
    private ImageView ivBankLogo;

    @ViewInject(R.id.tvBankName)
    private TextView tvBankName;

    @ViewInject(R.id.tvUserName)
    private TextView tvUserName;

    @ViewInject(R.id.tvTailNum)
    private TextView tvTailNum;

    @ViewInject(R.id.tvLimit)
    private TextView tvLimit;

    @ViewInject(R.id.etAmount)
    private EditText etAmount;

    @ViewInject(R.id.btNext)
    private View btNext;

    private static final int REQ_PAY_METHOD = 2;
    private PayMethod payMethod;
    private Long orderId;

    String titelName ="充值";

    private int accountType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
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
                btNext.setEnabled(submitAble);
            }
        });
    }

    private void initView()
    {
        btNext.setEnabled(false);
    }

    private void initData()
    {
       if( getIntent().hasExtra("titleName") && getIntent().hasExtra("accountType")){

           titelName = getIntent().getStringExtra("titleName");

           accountType = getIntent().getIntExtra("accountType",1);
       }

        setTitleNmae(titelName);

        loadDefaultMethod();
    }

    private void loadDefaultMethod()
    {
        new GetFirstPayWay(this, GetFirstPayWay.DEBIT, new GetFirstPayWay.OnPayWayGetListener()
        {
            @Override
            public void onGet(PayMethod payMethod)
            {
                ChargeActivity.this.payMethod = payMethod;
                setBankInfo();
            }

            @Override
            public void onFail(String msg)
            {
                UiUtils.shortToast(msg);
            }
        });
    }

    @Event(R.id.btNext)
    private void next(View v)
    {
        if (payMethod != null && validAmount())
        {
            createBaseOrder();
        } else
        {
            UiUtils.shortToast("请选择支付方式");
        }
    }

    private boolean validAmount()
    {
        String amount = etAmount.getText().toString();
        float amountf = Float.parseFloat(amount);
        if (payMethod.getLimitDay() - amountf > 0)
        {
            return true;
        } else
        {
            UiUtils.shortToast("余额不足");
            return false;

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
        param.put("intro", titelName);//订单信息
        param.put("orderType", OrderType.CHARGE.getValue());//订单类型
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<BaseOrder>()
        {
            @Override
            public void onSuccess(BaseOrder data)
            {
                orderId = data.getOrderId();
                charge(data.getOrderNo());
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void charge(String orderNo)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());

        if(accountType ==1){
            params.put("accountType", AccountType.CASH.getValue());
        }else  if(accountType ==2){
            params.put("accountType", AccountType.RED_BAG.getValue());
        }else  if(accountType ==3){
            params.put("accountType", AccountType.GIFT_CARD.getValue());
        }

        params.put("amount", etAmount.getText().toString());
        params.put("goodsName",titelName);
        params.put("sortCode", payMethod.getSortCode());
        params.put("transIp", AppUtils.getLocalIpAddress(ChargeActivity.this));
        params.put("orderNo", orderNo);
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);

        // 初始化
        RechargeHandle payHandle = new RechargeHandle(ChargeActivity.this);

        // 充值操作 显示"忘记密码"：是true 否false或null
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
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                createOrder();
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                UiUtils.shortToast(ERROR_MSG);
                cancelOrder();
            }
        });

        // 忘记密码回掉
        payHandle.setForgetPwdCallBack(new ForgetPwdImpl());
    }

    private void cancelOrder()
    {
        new CancelBaseOrder(orderId);
    }

    private void createOrder()
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderType", OrderType.CHARGE.getValue());//1充值；2转账；3提现；
        param.put("orderId", orderId);
        param.put("orderAmount", etAmount.getText().toString());
        param.put("tradeObject", "1");//1平台账户；2银行卡
        param.put("creditId", UserManager.getInstance().getUserBean().getAccount());

        param.put("payMethod", "3");//付款方式 1钱包余额；2信用卡；3储蓄卡；4红包余额
        param.put("debitBankId", payMethod.getBankId());//付款方银行id
        param.put("debitBankNo", payMethod.getBankNo());//付款方银行卡号
        param.put("debitBankPerson",payMethod.getName() );//付款方银行户主

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

    private void nextActivity()
    {
        Intent intent = new Intent(ChargeActivity.this, ChargeSuccessActivity.class);
        intent.putExtra("amount", etAmount.getText().toString());
        intent.putExtra("bankName", payMethod.getBankName());
        intent.putExtra("tailNo", payMethod.getSortNo());
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
    }

    @Event(R.id.rlBankCard)
    private void selectedCard(View v)
    {
        Intent intent = new Intent(this, SelectPayWayActivity.class);


        startActivityForResult(intent, REQ_PAY_METHOD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_PAY_METHOD:
                    payMethod = (PayMethod) data.getSerializableExtra("payMethod");
                    setBankInfo();
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
            tvLimit.setText(UiUtils.getString(R.string.charge_amount_limit, payMethod.getLimitDay()));

            ivBankLogo.setVisibility(View.VISIBLE);
        }else {

            ivBankLogo.setVisibility(View.GONE);

            tvBankName.setText("请绑定银行卡（储蓄卡）");
        }
    }
}
