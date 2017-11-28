package com.yzb.card.king.ui.discount.activity.msf;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.NumberKeypadView;
import com.yzb.card.king.ui.appwidget.appdialog.ConfirmDialog;
import com.yzb.card.king.ui.appwidget.appdialog.WaitingDialog;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.zxing.activity.MipcaActivityCapture;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * 码尚付输入金额
 */
public class DiscountMsfMoneyPGActivity extends MsfBaseActivity implements View.OnClickListener {

    private LinearLayout headerLeft;

    private EditText getAmount;

    private View viewNumberKeypay;

    private NumberKeypadView numberKeypadView;

    private LinearLayout headerRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discount_msf_pay);

        init();
        // 设置标题
        setTitle();

    }

    private void setTitle(){
        if(flagPayType){

            setHeader(R.mipmap.icon_back_white, "码尚付",0);

        }else{
            setHeader(R.mipmap.icon_back_white, "码尚付", R.mipmap.icon_top_sys);
        }
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
       // LogUtil.e("XYYYYYYY","-----------onNewIntent-------------");
        getAmount.setText("");
    }

    public void init() {

        showGet = (TextView) findViewById(R.id.showGet);

        showPay = (TextView) findViewById(R.id.showPay);
        headerLayout = (RelativeLayout) findViewById(R.id.headerLayout);

        headerRight = (LinearLayout) findViewById(R.id.headerRight);

        headerRight.setOnClickListener(this);

        userStatusUI();

        showGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!flagPayType){
                    return;
                }

                if (ServiceDispatcher.isNetworkConnected(DiscountMsfMoneyPGActivity.this)) {
                    Intent intent = new Intent();
                    intent.putExtra("isSaoYiSao", false);
                    intent.putExtra("payType",AppConstant.CREDIT);
                    intent.setClass(DiscountMsfMoneyPGActivity.this, MipcaActivityCapture.class);
                    startActivity(intent);

                }else{
                    payType = AppConstant.CREDIT;
                    uiHandler1.sendEmptyMessage(0);
                }
            }
        });

        showPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(flagPayType){
                    return;
                }


                if (ServiceDispatcher.isNetworkConnected(DiscountMsfMoneyPGActivity.this)) {
                    Intent intent = new Intent();
                    intent.putExtra("isSaoYiSao", false);
                    intent.putExtra("payType",AppConstant.DEBIT);
                    intent.setClass(DiscountMsfMoneyPGActivity.this, MipcaActivityCapture.class);
                    startActivity(intent);

                }else{

                    payType = AppConstant.DEBIT;

                    uiHandler1.sendEmptyMessage(0);
                }

            }
        });



        // 遮盖层,不弹出系统自带键盘
        ((EditText) findViewById(R.id.shadowText)).clearFocus();

        headerLeft = (LinearLayout) findViewById(R.id.headerLeft);

        headerLeft.setOnClickListener(this);

        getAmount = (EditText) findViewById(R.id.getAmount);

        getAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String temp = s.toString();
                int len = temp.length();
                // 如果第一位是0则第二位不允许输入数字
                if (len == 2 && "0".equals(temp.substring(0, 1))) {
                    if (Pattern.matches("(\\d)", temp.substring(1, 2)))
                        s.delete(1, 2);
                }

                // 保留2位小数
                int posDot = temp.indexOf(".");
                if (posDot == 0) s.clear();
                if (posDot < 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }

            }
        });
        //数据键盘
        viewNumberKeypay = findViewById(R.id.viewNumberKeypay);

        numberKeypadView = new NumberKeypadView(viewNumberKeypay,this);

        numberKeypadView.setAmount(getAmount);

        numberKeypadView.setKeyHandler(keyHandler);
    }

    private Handler keyHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            //生成转账订单
            orderCreate();
        }
    };

    private Handler uiHandler1 = new Handler(){

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);


            initUI();

            setTitle();
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.headerLeft:

                // 返回码尚付输入金额(收)
                setResult(AppConstant.RES_MSF_GET);
                finish();
                break;
            case R.id.headerRight:

                if (ServiceDispatcher.isNetworkConnected(DiscountMsfMoneyPGActivity.this)) {

                    Intent intent = new Intent();
                    intent.putExtra("isSaoYiSao", false);
                    intent.putExtra("payType",AppConstant.CREDIT);
                    intent.setClass(DiscountMsfMoneyPGActivity.this, MipcaActivityCapture.class);
                    startActivity(intent);
                    DiscountMsfMoneyPGActivity.this.finish();
                }else {

                    ToastUtil.i(DiscountMsfMoneyPGActivity.this,"请开启网络");
                }
                break;
        }
    }

    /**
     * 生成转账订单
     */
    public void orderCreate() {

        WaitingDialog.create(DiscountMsfMoneyPGActivity.this, "正在生成二维码...");

        uiHandler.sendEmptyMessage(0);
    }

    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            WaitingDialog.close();
            String amount = getAmount.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("amount", amount);
            intent.putExtra("payType",payType);//支付状态
            intent.setClass(DiscountMsfMoneyPGActivity.this, DiscountMsfQrActivity.class);
            startActivity(intent);
        }
    };


    public void ConfirmDialog(String title) {
        // 取消定时任务

        ConfirmDialog.Builder dialog = new ConfirmDialog.Builder(DiscountMsfMoneyPGActivity.this);
        dialog.setTitle(title);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 返回
                setResult(AppConstant.RES_MSF_GET);
                finish();
            }
        });
        dialog.create().show();
    }



}