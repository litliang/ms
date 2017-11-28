package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yzb.card.king.ui.appwidget.appdialog.WaitingDialog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.credit.bean.Rate;
import com.yzb.card.king.ui.credit.holder.CompleteHolder;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.BaseOrder;
import com.yzb.card.king.ui.my.cotroller.CreateTradeOrder;
import com.yzb.card.king.ui.my.enu.AccountType;
import com.yzb.card.king.ui.my.enu.OrderType;
import com.yzb.card.king.ui.other.listeners.ForgetPwdImpl;
import com.yzb.card.king.ui.other.listeners.MoneyFormatWatcher;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.wallet.logic.pay.TransferHandle;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.PayFailBackListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：全额代还
 * 作者：殷曙光
 * 日期：2017/1/4 20:16
 */

public class FullRepayActivity extends BaseActivity
{

    private static final int REQ_ADD_BANK_CARD = 2;
    private TextView tvFee;
    private EditText etAmount;
    private Button btnSubmit;
    private Rate rate;
    private TextView tvRate;
    private LinearLayout llContent;
    private CreditCard data;
    private Long orderId;
    private TransferHandle payHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        setContentView(R.layout.activity_full_repay);

        etAmount = (EditText) findViewById(R.id.etAmount);
        tvFee = (TextView) findViewById(R.id.tvFee);
        tvRate = (TextView) findViewById(R.id.tvRate);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        llContent = (LinearLayout) findViewById(R.id.llContent);

        btnSubmit.setEnabled(false);
    }

    private void initListener()
    {
        etAmount.addTextChangedListener(new MoneyFormatWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                super.onTextChanged(s, start, before, count);
                if (rate != null && check(s.toString()) && s.length()>0)
                {
                    float fee = Float.parseFloat(s.toString()) * rate.getRate() / 100;
                    DecimalFormat format = new DecimalFormat("#.##");
                    tvFee.setText(format.format(fee));
                }
            }

            private boolean check(String count)
            {
                return !(count.contains(".") && count.length() - 1 == count.indexOf("."));
            }

            @Override
            public void submitAble(boolean submitAble)
            {
                btnSubmit.setEnabled(submitAble);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(type == 100){//他人代还

                    Intent intent = new Intent(FullRepayActivity.this,RepaymentCrediteInfoActivity.class);

                    intent.putExtra("creditCard",data);

                     String amount = etAmount.getText().toString();

                    intent.putExtra("amount",amount);

                    String fee = tvFee.getText().toString().trim();

                    intent.putExtra("fee",fee);

                    startActivity(intent);

                }else {

                    WaitingDialog.create(FullRepayActivity.this, null);
                    createBaseOrder();
                }


            }

        });

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
        param.put("creditId", data.getBankId());//收款方id
        param.put("amount", etAmount.getText().toString());//订单金额
        param.put("intro", "信用卡还款");//订单信息
        param.put("orderType", OrderType.FULL_REPAY.getValue());//订单类型
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
                WaitingDialog.close();
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void sendTransRequest(String orderNo,String url)
    {
        UserManager userManager = UserManager.getInstance();
        UserBean userBean = userManager.getUserBean();
        Map<String, String> params = new HashMap<String, String>();

        params.put("orderNo", orderNo);
        params.put("fee", tvFee.getText().toString());

        params.put("amount", etAmount.getText().toString());
        params.put("goodsName", "信用卡还款");
        params.put("transIp", AppUtils.getLocalIpAddress(this));

        params.put("debitMobile", userBean.getAccount());//付款方

        params.put("creditMobile", data.getReservedMobile());//收款方手机
        params.put("creditType", AccountType.FLASH_PAY.getValue());
        params.put("creditCardName", data.getUserName());
        params.put("creditCardNo", data.getCardNo());

        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);

        params.put("bankMark",data.getBankMark());
        params.put("notifyUrl", url);
        LogUtil.e("转账参数：" + JSON.toJSONString(params));
        // 初始化
        payHandle = new TransferHandle(this);
        // 转账操作 显示"忘记密码"：是true 否false或null
        payHandle.pay(params, true);

        payHandle.showDebitCard(false);
        payHandle.showCreditCard(true);
        payHandle.showBalancePay(false);
        payHandle.hiddenCard(data.getCardNo());
        payHandle.payFailCallBack(new PayFailBackListener()
        {
            @Override
            public void callBack()
            {
                cancelOrder();
            }
        });
        WaitingDialog.close();
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
                WaitingDialog.close();
                createOrder(resultMap);
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                WaitingDialog.close();
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
                startActivityForResult(new Intent(FullRepayActivity.this, AddCanPayCardActivity.class),
                        REQ_ADD_BANK_CARD);
            }
        });
    }

    private void cancelOrder()
    {
        new CancelBaseOrder(orderId);
    }

    private void showCompleteView()
    {
        llContent.removeAllViews();
        CompleteHolder holder = new CompleteHolder(null);
        holder.setData("操作成功");
        llContent.addView(holder.getView());
    }

    private void createOrder(Map<String, String> resultMap)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderType", OrderType.REPAYMENT.getValue());//1充值；2转账；3提现；
        param.put("orderId", orderId);
        param.put("orderAmount", etAmount.getText().toString());
        param.put("tradeObject", "2");//1平台账户；2银行卡

        param.putAll(resultMap);
//        param.put("payMethod", "2");//付款方式 1钱包余额；2信用卡；3储蓄卡；4红包余额
////        param.put("debitBankId", resultMap.get("debitBankId"));//付款方银行id
////        param.put("debitBankNo", resultMap.get("debitBankNo"));//付款方银行卡号
////        param.put("debitBankPerson", resultMap.get("debitBankPerson"));//付款方银行户主

//        param.put("creditId", String.valueOf(payMethod.getBankId()));//收款人id  平台账户为账号id，储蓄卡时为银行id
        param.put("creditBankId", data.getBankId());//收款方银行id
        param.put("creditBankNo", data.getCardNo());//收款方银行卡号
        param.put("creditBankPerson", data.getUserName());//收款方银行户主

        param.put("remark", "");

        new CreateTradeOrder(param, new BaseCallBack<Map<String, String>>()
        {
            @Override
            public void onSuccess(Map<String, String> data)
            {
                showCompleteView();
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void initData()
    {
        setTitleNmae("全额代还");
        getIntentData();
        getRate();


    }

    private int type;

    private void getIntentData()
    {
        data = (CreditCard) getIntent().getSerializableExtra("creditCard");

        if(getIntent().hasExtra("type")){

            type = getIntent().getIntExtra("type",0);
        }

    }


    private void getRate()
    {
        SimpleRequest<Rate> request = new SimpleRequest<Rate>("QueryFullGenerationCheck")
        {
            @Override
            protected Rate parseData(String data)
            {
                return JSON.parseObject(data, Rate.class);
            }
        };
        request.sendRequestNew(new BaseCallBack<Rate>()
        {
            @Override
            public void onSuccess(Rate data)
            {
                rate = data;
                tvRate.setText(UiUtils.getString(R.string.card_rate, data.getRate()));
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
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
