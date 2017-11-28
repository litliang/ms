package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.yzb.card.king.ui.app.activity.IDAuthenticationActivity;
import com.yzb.card.king.ui.app.activity.ia.ExamineIaActivity;
import com.yzb.card.king.ui.app.activity.ia.VerifyResultActivity;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.credit.holder.RepayBankHolder;
import com.yzb.card.king.ui.credit.popup.SimpleListPop;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.BaseOrder;
import com.yzb.card.king.ui.my.cotroller.CreateTradeOrder;
import com.yzb.card.king.ui.my.enu.AccountType;
import com.yzb.card.king.ui.my.enu.OrderType;
import com.yzb.card.king.ui.my.pop.RealNameCertificationDialog;
import com.yzb.card.king.ui.my.pop.ResetPayPasswordDialog;
import com.yzb.card.king.ui.other.listeners.ForgetPwdImpl;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.wallet.logic.pay.TransferHandle;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.PayFailBackListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepaymentActivity extends BaseActivity {

    private static final int REQ_CARD_MANAGE = 2;
    private static final int REQ_ADD_BANK_CARD = 10;
    //    private static CreditCard data;
    private RelativeLayout rlRepayBank;
    private EditText etPayment;
    private Button btRepay;
    private TextView tvSearchBill;
    private TextView tvPayForOther;
    private SimpleListPop searchBillPop;
    private SimpleListPop payForOtherPop;
    private View mRoot;
    private static RepayBankHolder repayBankHolder;
    private TransferHandle payHandle;
    private Long orderId;
    private TextView tvScheduled;

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
        mRoot = UiUtils.inflate(R.layout.activity_repayment);
        setContentView(mRoot);
        rlRepayBank = (RelativeLayout) findViewById(R.id.rlRepayBank);
        etPayment = (EditText) findViewById(R.id.etPayment);
        btRepay = (Button) findViewById(R.id.btRepay);
        tvSearchBill = (TextView) findViewById(R.id.tvSearchBill);
        tvPayForOther = (TextView) findViewById(R.id.tvPayForOther);
        tvScheduled = (TextView) findViewById(R.id.tvScheduled);
    }


    public static void setData(CreditCard info)
    {
//        data = info;
        repayBankHolder.setData(info);
    }

    private void initListener()
    {

        findViewById(R.id.llTemp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        findViewById(R.id.llRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (getData() != null) {
                    Intent intent = new Intent(RepaymentActivity.this, CardManageActivity.class);
                    intent.putExtra("data", getData());
                    startActivityForResult(intent, REQ_CARD_MANAGE);
                }

            }
        });

        findViewById(R.id.tvOtherRepayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //他人代还
                if ("1".equals(getValidStatus())) {
                    if (getData() != null) {
                        Intent intent = new Intent(RepaymentActivity.this, FullRepayActivity.class);
                        intent.putExtra("creditCard", getData());
                        intent.putExtra("type", 100);
                        startActivity(intent);
                    } else {
                        UiUtils.shortToast("请选择信用卡");
                    }
                } else if ("3".equals(getValidStatus())) {
                    UiUtils.longToast("身份信息正在审核中，审核通过才能访问");
                } else {
                    RealNameCertificationDialog.getInstance().setDataHandler(uiHandler).show(getSupportFragmentManager(), "");
                }
            }
        });

        etPayment.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                btRepay.setEnabled(s.toString().trim().length() > 0);
            }
        });


        tvSearchBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showSearchBillPop(v);
            }
        });

        tvPayForOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if ("1".equals(getValidStatus())) {
                    if (getData() != null) {
                        // showPayForOtherPop(v);
                        Intent intent = new Intent(RepaymentActivity.this, FullRepayActivity.class);
                        intent.putExtra("creditCard", getData());
                        startActivity(intent);
                    } else {
                        UiUtils.shortToast("请选择信用卡");
                    }
                } else if ("3".equals(getValidStatus())) {
                    UiUtils.longToast("身份信息正在审核中，审核通过才能访问");
                } else {
                    RealNameCertificationDialog.getInstance().setDataHandler(uiHandler).show(getSupportFragmentManager(), "");
                }

            }
        });

        btRepay.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v)
            {
                if ("1".equals(getValidStatus())) {
                    if (getData() != null) {
                        createBaseOrder();
                    } else {
                        UiUtils.shortToast("请选择信用卡");
                    }
                } else if ("3".equals(getValidStatus())) {
                    UiUtils.longToast("身份信息正在审核中，审核通过才能访问");
                } else {
                    RealNameCertificationDialog.getInstance().setDataHandler(uiHandler).show(getSupportFragmentManager(), "");
                }

            }
        });

        tvScheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //预约还款
            }
        });
    }

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            if (RealNameCertificationDialog.WHAT_LOOK == msg.what) {
                //进入实名认证流程
                startActivityForResult(new Intent(RepaymentActivity.this,ExamineIaActivity.class), VerifyResultActivity.RESULT_REQUEST_CODE);

            } else if (0 == msg.what) {

                ResetPayPasswordDialog.getInstance().setDataHandler(null).show(getSupportFragmentManager(), "");
            }
        }
    };

    private void createBaseOrder()
    {
        SimpleRequest<BaseOrder> request = new SimpleRequest<BaseOrder>(CardConstant.CREATE_BASE_ORDERS) {
            @Override
            protected BaseOrder parseData(String data)
            {
                return JSON.parseObject(data, BaseOrder.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("creditId", getData().getBankId());//收款方id
        param.put("amount", etPayment.getText().toString());//订单金额
        param.put("intro", "信用卡还款");//订单信息
        param.put("orderType", OrderType.REPAYMENT.getValue());//订单类型
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<BaseOrder>() {
            @Override
            public void onSuccess(BaseOrder data)
            {
                orderId = data.getOrderId();
                sendTransRequest(data.getOrderNo(), data.getNotifyUrl());
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void sendTransRequest(String orderNo, String url)
    {
        UserManager userManager = UserManager.getInstance();
        UserBean userBean = userManager.getUserBean();
        Map<String, String> params = new HashMap<String, String>();

        params.put("orderNo", orderNo);

        params.put("amount", etPayment.getText().toString());
        params.put("goodsName", "信用卡还款");
        params.put("transIp", AppUtils.getLocalIpAddress(this));

        params.put("debitMobile", userBean.getAccount());//付款方

        params.put("creditMobile", getData().getReservedMobile());//收款方手机
        params.put("creditType", AccountType.FLASH_PAY.getValue());
        params.put("creditCardName", getData().getUserName());
        params.put("creditCardNo", getData().getCardNo());
        params.put("bankMark", getData().getBankMark());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);
        params.put("notifyUrl", url);
        LogUtil.e("转账参数：" + JSON.toJSONString(params));
        // 初始化
        payHandle = new TransferHandle(this);
        // 转账操作 显示"忘记密码"：是true 否false或null
        payHandle.pay(params, true);
        payHandle.payFailCallBack(new PayFailBackListener() {
            @Override
            public void callBack()
            {
                cancelOrder();
            }
        });
        // 操作结果
        payHandle.setCallBack(new WalletBackListener() {
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
                repayOrder("2", resultMap);
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                UiUtils.shortToast(ERROR_MSG);
                cancelOrder();
                repayOrder("3", null);
            }
        });
        // 忘记密码回掉
        payHandle.setForgetPwdCallBack(new ForgetPwdImpl());

        payHandle.setAddCardCallBack(new AddCardBackListener() {
            @Override
            public void callBack()
            {
                startActivityForResult(new Intent(RepaymentActivity.this, AddBankCardActivity.class),
                        REQ_ADD_BANK_CARD);
            }
        });
    }

    private void cancelOrder()
    {
        new CancelBaseOrder(orderId);
    }

    private void createOrder(Map<String, String> resultMap)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderType", OrderType.REPAYMENT.getValue());//1充值；2转账；3提现；
        param.put("orderId", orderId);
        param.put("orderAmount", etPayment.getText().toString());
        param.put("tradeObject", "2");//1平台账户；2银行卡

        param.putAll(resultMap);
//        param.put("payMethod", "2");//付款方式 1钱包余额；2信用卡；3储蓄卡；4红包余额
////        param.put("debitBankId", resultMap.get("debitBankId"));//付款方银行id
////        param.put("debitBankNo", resultMap.get("debitBankNo"));//付款方银行卡号
////        param.put("debitBankPerson", resultMap.get("debitBankPerson"));//付款方银行户主

//        param.put("creditId", String.valueOf(payMethod.getBankId()));//收款人id  平台账户为账号id，储蓄卡时为银行id
        param.put("creditBankId", getData().getBankId());//收款方银行id
        param.put("creditBankNo", getData().getCardNo());//收款方银行卡号
        param.put("creditBankPerson", getData().getUserName());//收款方银行户主

        param.put("remark", "");

        new CreateTradeOrder(param, new BaseCallBack<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> data)
            {
//                nextActivity();
                UiUtils.shortToast("还款成功！");
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void repayOrder(String status, Map<String, String> resultMap)
    {
        SimpleRequest<String> request = new SimpleRequest<String>("RepayCreditCard") {
            @Override
            protected String parseData(String data)
            {
                return data;
            }
        };
        Map<String, Object> param = new HashMap<>();
        param.put("amount", etPayment.getText().toString());
        param.put("creditId", getData().getId());
        param.put("payType", "0");
        param.put("debitSortCode", resultMap == null ? "" : resultMap.get("debitBankId"));
        param.put("status", status);//2成功，3失敗
        param.put("bankMark", getData().getBankMark());
        request.setParam(param);
        request.sendRequestNew(null);
    }

    private void nextActivity()
    {
        setResult(RESULT_OK);
        finish();
    }

    public float getAmount()
    {
        return Float.parseFloat(etPayment.getText().toString());
    }

    private void showSearchBillPop(View view)
    {

        if (searchBillPop == null) {
            List<String> list = new ArrayList<>();
            list.add("邮箱查询");
            list.add("短信查询");
            searchBillPop = new SimpleListPop(this) {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    if (position == 0) {
                        startActivity(new Intent(RepaymentActivity.this, BillImportActivity.class));
                    } else if (position == 1) {
                        sendMessage(getData().getMobile(), "CC账单#" + getData().getSortNo());
                    }
                    searchBillPop.dismiss();
                }
            };
            searchBillPop.setDataList(list);
        }
        searchBillPop.showAtLocation(mRoot, Gravity.CENTER_HORIZONTAL, 0, -UiUtils.dp2px(50));
    }

    private void sendMessage(String number, String message)
    {
        Uri uri = Uri.parse("smsto:" + number);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        sendIntent.putExtra("sms_body", message);
        UiUtils.startActivity(sendIntent);
    }

//    private void showPayForOtherPop(View v)
//    {
//        if (payForOtherPop == null)
//        {
//            List<String> list = new ArrayList<>();
//            list.add("全额代还");
//            list.add("分期代还");
//            payForOtherPop = new SimpleListPop(this)
//            {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//                {
//                    if (position == 0)
//                    {
//                        Intent intent = new Intent(RepaymentActivity.this, FullRepayActivity.class);
//                        intent.putExtra("creditCard", getData());
//                        startActivity(intent);
//                    } else if (position == 1)
//                    {
//                        UiUtils.shortToast("目前不提供此服务，敬请期待！");
//                    }
//                    payForOtherPop.dismiss();
//                }
//            };
//            payForOtherPop.setDataList(list);
//        }
//        payForOtherPop.showAtLocation(v, Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, UiUtils.dp2px(200));
//    }

    private void initData()
    {
        getIntentData();
    }


    private void setRepayBank(CreditCard data)
    {
        rlRepayBank.removeAllViews();
        repayBankHolder = new RepayBankHolder(this);
        repayBankHolder.setData(data);
        rlRepayBank.addView(repayBankHolder.getConvertView());
    }


    private void getIntentData()
    {
        setRepayBank((CreditCard) getIntent().getSerializableExtra("data"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        repayBankHolder.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CARD_MANAGE:
                    finish();
                    break;
                case REQ_ADD_BANK_CARD:
                    payHandle.showAddCardSuccess();
                    break;
            }

        }
    }

    public CreditCard getData()
    {
        return repayBankHolder.getData();
    }
}
