package com.yzb.card.king.ui.discount.activity.msf;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jungly.gridpasswordview.GridPasswordView;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.QrPayBean;
import com.yzb.card.king.bean.order.FastPaymentOrderBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.AuthCodeView;
import com.yzb.card.king.ui.appwidget.NumberKeypadView;
import com.yzb.card.king.ui.appwidget.appdialog.ConfirmDialog;
import com.yzb.card.king.ui.appwidget.appdialog.WaitingDialog;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.AppQuickPreConsumeBean;
import com.yzb.card.king.ui.discount.presenter.CreateFastPaymentOrderPresenter;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.presenter.SelectCardPresenter;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.zxing.activity.MipcaActivityCapture;

import java.util.List;
import java.util.Map;

/**
 * 牵手付和码尚付，转账验证信息区
 * <p/>
 * Created by leichao on 2016/7/4.
 */
public class DiscountMsfPaySMSDActivity extends MsfBaseActivity implements View.OnClickListener, DataCallBack {

    private TextView discount_msf_choice_howmatch;

    private LinearLayout headerLeft;

    private String price;

    private View viewNumberKeypay;

    private NumberKeypadView numberKeypadView;

    private GridPasswordView inputPwd;

    private CreateFastPaymentOrderPresenter presenter;

    private AuthCodeView authCodeView;
    /**
     * 二维码图形信息
     */
    private QrPayBean qrPayBean;
    /**
     * 付款方式
     */
    private SelectCardPresenter selectCardPresenter;

    /**
     * 二维码图片生成方的默认付款方式
     */
    private PayMethod currentPayMethod;

    /**
     * 二维码图：平台账户验证码和其它银行验证码区别符号
     */
    private boolean defaultPlatorm = false;

    /**
     * 扫描方方的默认付款方式
     */
    private PayMethod scanPayMethod = null;
    /**
     * 扫描方：平台账户验证码和其它银行验证码区别符号
     */
    private boolean scanPlatorm = false;
    /**
     * 订单信息对象
     */
    private FastPaymentOrderBean fastPaymentOrderBean;
    /**
     * true:表示牵手付；fals：表示码尚付
     */
    private boolean statusFlag = false;

    //他行消费鉴权信息
    private AppQuickPreConsumeBean token;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discount_msf_choice_pay);

        if (getIntent().hasExtra("payType")) {

            statusFlag = getIntent().getBooleanExtra("payType", false);
        }

        if (getIntent().hasExtra(MipcaActivityCapture.QRPAYBEAN)) {
            qrPayBean = (QrPayBean) getIntent().getSerializableExtra(MipcaActivityCapture.QRPAYBEAN);
            //对商户付款，可选择不同的付款方式
            if (getIntent().hasExtra("payMethod")) {
                scanPayMethod = (PayMethod) getIntent().getSerializableExtra("payMethod");
            }

            LogUtil.e("AAAA", "二维码方sessid=" + qrPayBean.getProvideAccount());
            LogUtil.e("AAAA", "扫  描方sessid=" + AppConstant.sessionId);
        }

        if(statusFlag){

            setHeader("牵手付");

        }else{

            setHeader("码尚付");
        }

        init();

        presenter = new CreateFastPaymentOrderPresenter(this);

        authCodeView = new AuthCodeView(btGetCode);

        authCodeView.setDataHandler(dataHandler);

        WaitingDialog.create(DiscountMsfPaySMSDActivity.this, "正在生成订单...");

        String aSessionId = qrPayBean.getProvideAccount();//二维码的sessionId

        selectCardPresenter = new SelectCardPresenter(this);
        //发送二维码图方的付款方式请求,获取二维码方的默认付款方式
        selectCardPresenter.querySpecifiedUserSelectCardRequest(aSessionId);

    }

    private void init()
    {
        this.discount_msf_choice_howmatch = (TextView) findViewById(R.id.discount_msf_choice_howmatch);

        btGetCode = (TextView) findViewById(R.id.btGetCode);

        btGetCode.setOnClickListener(this);

        headerLayout = (RelativeLayout) findViewById(R.id.headerLayout);
        // 遮盖层,不弹出系统自带键盘
        ((EditText) findViewById(R.id.shadowText)).clearFocus();
        // 解析QR
        decodeQr();
        this.showGet = (TextView) findViewById(R.id.showGet);
        this.showPay = (TextView) findViewById(R.id.showPay);
        showGet.setOnClickListener(this);
        showPay.setOnClickListener(this);
        this.headerLeft = (LinearLayout) findViewById(R.id.headerLeft);
        this.headerLeft.setOnClickListener(this);

        inputPwd = (GridPasswordView) findViewById(R.id.inputPwd);
        inputPwd.setPasswordVisibility(true);

        userStatusUI();
        //数据键盘
        viewNumberKeypay = findViewById(R.id.viewNumberKeypay);

        numberKeypadView = new NumberKeypadView(viewNumberKeypay, this);

        numberKeypadView.setKeyHandler(keyHandler);

    }

    private Handler keyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            int what = msg.what;

            if (what == R.id.keyQc) {//清除

                if (inputPwd.getPassWord().toString().length() > 0) {

                    String text = inputPwd.getPassWord().toString().substring(0, inputPwd.getPassWord().toString().length() - 1);

                    inputPwd.setPassword(text);
                }

            } else if (what == R.id.keyQr) {//确认，转账

                String passwordStr = inputPwd.getPassWord().toString();

                if (passwordStr.length() == 6) {
                    //发送转账请求
                    WaitingDialog.create(DiscountMsfPaySMSDActivity.this, "正在转账...");
                    presenter.sendAppTransferFastpaymentRequest(qrPayBean, token, passwordStr, currentPayMethod, scanPayMethod, fastPaymentOrderBean);
                } else {
                    ToastUtil.i(DiscountMsfPaySMSDActivity.this, "请检测转账验证码");
                }

            } else if (what == R.id.keyQx) {//取消

                DiscountMsfPaySMSDActivity.this.finish();

            } else {//输入

                String str = msg.obj + "";
                String text = inputPwd.getPassWord().toString() + Integer.parseInt(str);
                inputPwd.setPassword(text);


            }
        }
    };

    private void decodeQr()
    {

        if (getIntent() != null) {

            price = qrPayBean.getAmount();//result[1];

            this.discount_msf_choice_howmatch.setText("￥" + price);

        }
    }



    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {


            case R.id.showGet://收款方
                if(!flagPayType){
                    return;
                }

                payType = AppConstant.CREDIT;

                if (statusFlag) {

                    initUI();

                } else {

                    if (ServiceDispatcher.isNetworkConnected(DiscountMsfPaySMSDActivity.this)) {

                        Intent intent = new Intent(DiscountMsfPaySMSDActivity.this, MipcaActivityCapture.class);
                        intent.putExtra("isSaoYiSao", false);
                        intent.putExtra("payType", payType);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(DiscountMsfPaySMSDActivity.this, DiscountMsfMoneyPGActivity.class);

                        intent.putExtra("payType", payType);

                        DiscountMsfPaySMSDActivity.this.startActivity(intent);

                        DiscountMsfPaySMSDActivity.this.finish();
                    }
                }
                break;
            case R.id.showPay://付款方
                if(flagPayType){
                    return;
                }
                payType = AppConstant.DEBIT;
                if (statusFlag) {

                    initUI();

                } else {
                    if (ServiceDispatcher.isNetworkConnected(DiscountMsfPaySMSDActivity.this)) {

                        Intent intent = new Intent(DiscountMsfPaySMSDActivity.this, MipcaActivityCapture.class);
                        intent.putExtra("isSaoYiSao", false);
                        intent.putExtra("payType", payType);
                        startActivity(intent);
                        finish();

                    } else {
                        Intent intent = new Intent(DiscountMsfPaySMSDActivity.this, DiscountMsfMoneyPGActivity.class);

                        intent.putExtra("payType", payType);

                        DiscountMsfPaySMSDActivity.this.startActivity(intent);

                        DiscountMsfPaySMSDActivity.this.finish();

                    }
                }
                break;

            case R.id.headerLeft:
                confirmdalog("取消付款吗?", true);
                break;
            case R.id.btGetCode:

                    /*
                     * 1、检查付款方
                     * 2、检查是否是浦发
                     * 3、确定发送转账验证码发送的方法
                     */
                String flag = qrPayBean.getFlag();

                LogUtil.e("BBBB","--------MsfPaySMSa------flag="+flag);
                if (AppConstant.DEBIT.equals(flag)) {//检查二维码的信息用户是否是付款方

                    if (currentPayMethod != null && fastPaymentOrderBean != null) {

                        String str1 = qrPayBean.getProvideAccount();

                        if (defaultPlatorm) {


                            authCodeView.sendAppQuickPreConsume(str1, currentPayMethod.getSortCode(), qrPayBean.getAmount(), fastPaymentOrderBean.getOrderNo());

                        } else {
                            //发送转账验证码
                            authCodeView.sendCodeRequest(str1, "2", "6");

                        }
                    }

                } else {//扫描方为付款方

                    if (scanPayMethod != null && fastPaymentOrderBean != null) {

                        String str1 = AppConstant.sessionId;

                        if (scanPlatorm) {

                            authCodeView.sendAppQuickPreConsume(str1, scanPayMethod.getSortCode(), qrPayBean.getAmount(), fastPaymentOrderBean.getOrderNo());

                        } else {

                            //发送转账验证码
                            authCodeView.sendCodeRequest(str1, "2", "6");
                        }
                    }

                }

                break;
        }
    }

    private void confirmdalog(String title, final boolean closeWindow)
    {
        final ConfirmDialog.Builder dialog = new ConfirmDialog.Builder(DiscountMsfPaySMSDActivity.this);
        dialog.setTitle(title);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                if (closeWindow) {
                    DiscountMsfPaySMSDActivity.this.finish();
                }
            }
        });
        dialog.create().show();
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        confirmdalog("取消付款吗？", true);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == 1) {//二维码方
            String str = o + "";
            List<PayMethod> list = JSON.parseArray(str, PayMethod.class);
            if (list != null && list.size() > 0) {

                PayMethod tempPayMethod = list.get(0);

                scanPayMethod = tempPayMethod;

                scanPlatorm = AppUtils.ifPlatorm(scanPayMethod);

            }

            //发送生成码尚付订单请求
            presenter.sendOrderRequest(qrPayBean, currentPayMethod, scanPayMethod);


        } else if (type == 2) {//创建订单

            WaitingDialog.close();
            //创建订单成功
            String str = o + "";

            fastPaymentOrderBean = JSON.parseObject(str, FastPaymentOrderBean.class);

        } else if (type == 3) {//转账付款
            WaitingDialog.close();

            AppUtils.transferInforRemind(this,flagPayType,fastPaymentOrderBean);


        } else if (type == 1001) {//二维码方的付款方式

            String str = o + "";

            List<PayMethod> list = JSON.parseArray(str, PayMethod.class);

            if (list != null && list.size() > 0) {

                PayMethod tempPayMethod = list.get(0);

                currentPayMethod = tempPayMethod;

                defaultPlatorm = AppUtils.ifPlatorm(currentPayMethod);
            }

            if (scanPayMethod != null) {//扫描方的付款方式

                scanPlatorm = AppUtils.ifPlatorm(scanPayMethod);
                //发送生成码尚付订单请求
                presenter.sendOrderRequest(qrPayBean, currentPayMethod, scanPayMethod);
            } else {
                //发送扫描方的付款方式请求，获取扫描方的默认付款方式
                selectCardPresenter.sendUserPaymethod(AppConstant.sessionId);
            }
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {

        WaitingDialog.close();
        if (o != null) {
            //检查出code=1111，表示免密快捷支付
            Map<String, String> onSuccessData = (Map<String, String>) o;

            if(onSuccessData.containsKey(HttpConstant.SERVER_CODE)){

                String codeStr = onSuccessData.get(HttpConstant.SERVER_CODE);

                if(HttpConstant.AVOID_CLOSE_CODE.equals(codeStr)){

                    fastPaymentOrderBean = JSON.parseObject(onSuccessData.get(HttpConstant.SERVER_DATA), FastPaymentOrderBean.class);

                    AppUtils.transferInforRemind(this,flagPayType,fastPaymentOrderBean);

                    return;
                }

            }

            String data = HttpConstant.getData(o);
            if (data != null) {
                ToastUtil.i(this, data + "");
            }
        }

    }


    private Handler dataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            int what = msg.what;

            if (what == 0) {//验证成功


            } else if (what == 1) {//验证失败

                Object o = msg.obj;
                String data = HttpConstant.getData(o);
                LogUtil.e("XYYYYY", "------2-------" + data);
                if (data != null) {
                    ToastUtil.i(DiscountMsfPaySMSDActivity.this, data + "");
                }
            } else if (what == 2) {//发送他行验证码成功

                String tokenStr = msg.obj + "";
                LogUtil.e("XYYYYY", "------3-------" + tokenStr);
                AppQuickPreConsumeBean appQuickPreConsumeBean  = JSON.parseObject(tokenStr+"", AppQuickPreConsumeBean.class);

                token = appQuickPreConsumeBean;

            }

        }
    };
}
