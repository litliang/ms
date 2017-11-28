package com.yzb.card.king.ui.discount.activity.msf;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.FastPaymentOrderBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.PreferencesService;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.QrUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.zxing.activity.MipcaActivityCapture;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;

/**
 * 码尚付 --- 收款方或者付款方的二维码
 */
public class DiscountMsfQrActivity extends BaseActivity implements View.OnClickListener {

    private ImageView qrImage;
    private TextView showGetAmount;
    private LinearLayout headerLeft;


    private RelativeLayout headerLayout;

    private TextView showGet, showPay;

    private boolean flagPayType;

    private String payType;

    private String moneyStr;

    private String userSessionId;

    private PayMethod payMethod;

    private String customerType;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discount_msf_qr);


        // 设置标题
        setHeader("码尚付");

        initData();

        PreferencesService pre = new PreferencesService(GlobalApp.getInstance().getContext());

        userSessionId = pre.getValue("qrCodeSessionId");

        if (TextUtils.isEmpty(userSessionId)) {

            ToastUtil.i(this, "暂无用户信息，请登录");

            return;
        }
        customerType = pre.getValue("customerType");

        if (TextUtils.isEmpty(customerType)) {

            ToastUtil.i(this, "暂无用户信息，请登录");

            return;
        }

        payMethod = (PayMethod) getIntent().getSerializableExtra("payMethod");

        createQrCode();

        //注册evenBus
        GlobalApp.activityStr = "mfQr";
        EventBus.getDefault().register(this);
    }



    /**
     * 创建二维码图
     */
    private void createQrCode()
    {

        String string = PayinfoUtil.createPayGetInfo(payMethod,moneyStr,payType,customerType,userSessionId);


        qrImage.setImageBitmap(QrUtil.createQrImage(string, 800, 800));

    }

    private void initData()
    {
        Intent intent = getIntent();
        String amount = intent.getStringExtra("amount");
        payType = intent.getStringExtra("payType");
        flagPayType = payType.equals(AppConstant.DEBIT);

        headerLayout = (RelativeLayout) findViewById(R.id.headerLayout);
        headerLeft = (LinearLayout) findViewById(R.id.headerLeft);
        headerLeft.setOnClickListener(this);
        showGetAmount = (TextView) findViewById(R.id.showGetAmount);
        moneyStr = new BigDecimal(amount).setScale(2) + "";
        showGetAmount.setText("￥" + moneyStr);
        qrImage = (ImageView) findViewById(R.id.qrImage);

        uiHandler.sendEmptyMessageDelayed(0, 60 * 1000);

        TextView tvMessageText = (TextView) findViewById(R.id.tvMessageText);
        TextView tvMsg = (TextView) findViewById(R.id.tvMsg);

        showGet = (TextView) findViewById(R.id.showGet);

        showPay = (TextView) findViewById(R.id.showPay);

        showPay.setOnClickListener(this);

        showGet.setOnClickListener(this);

        if (flagPayType) {//蓝色
            tvMsg.setText(R.string.msf_for_me_collection_money);
            headerLayout.setBackgroundColor(getResources().getColor(R.color.titleRed));
            tvMessageText.setVisibility(View.VISIBLE);
            showPay.setTextColor(getResources().getColor(R.color.titleRed));
            showGet.setTextColor(getResources().getColor(R.color.wordGrey));
        } else {//红色

            headerLayout.setBackgroundColor(getResources().getColor(R.color.titleBlue));
            tvMessageText.setVisibility(View.GONE);
            tvMsg.setText(R.string.msf_for_me_pay_money);
            showPay.setTextColor(getResources().getColor(R.color.wordGrey));
            showGet.setTextColor(getResources().getColor(R.color.titleBlue));
        }
    }


    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            createQrCode();

            if(uiHandler != null){
                uiHandler.sendEmptyMessageDelayed(0, 60 * 1000);
            }

        }
    };


    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        GlobalApp.activityStr = null;
        //取消注册事件
        EventBus.getDefault().unregister(this);
        if (uiHandler != null) {

            uiHandler = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void mainEventThread(FastPaymentOrderBean event)
    {
         if("mfQr".equals(event.getMarkActivity())){//接收转账成功信息

             AppUtils.transferInforRemind(this,flagPayType,event);

         }

    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId()) {
            case R.id.headerLeft:// 返回
                // 确定是否返回，确定返回并取消订单
                confirmDialog();
                break;
            case R.id.showGet:

                if(!flagPayType){
                    return;
                }

                payType = AppConstant.CREDIT;

                if (ServiceDispatcher.isNetworkConnected(DiscountMsfQrActivity.this)) {

                    Intent intent = new Intent();

                    intent.putExtra("isSaoYiSao", false);

                    intent.putExtra("payType", payType);

                    intent.setClass(DiscountMsfQrActivity.this, MipcaActivityCapture.class);

                    startActivity(intent);

                    finish();

                } else {

                    Intent intent = new Intent(DiscountMsfQrActivity.this, DiscountMsfMoneyPGActivity.class);

                    intent.putExtra("payType", payType);

                    DiscountMsfQrActivity.this.startActivity(intent);

                    DiscountMsfQrActivity.this.finish();
                }
                break;
            case R.id.showPay:
                if(flagPayType){
                    return;
                }
                payType = AppConstant.DEBIT;

                if (ServiceDispatcher.isNetworkConnected(DiscountMsfQrActivity.this)) {


                    Intent intent = new Intent();
                    intent.putExtra("isSaoYiSao", false);
                    intent.putExtra("payType", payType);
                    intent.setClass(DiscountMsfQrActivity.this, MipcaActivityCapture.class);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(DiscountMsfQrActivity.this, DiscountMsfMoneyPGActivity.class);

                    intent.putExtra("payType", payType);

                    DiscountMsfQrActivity.this.startActivity(intent);

                    DiscountMsfQrActivity.this.finish();

                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {//按下的如果是BACK，同时没有重复
            // 确定是否返回，确定返回并取消订单
            confirmDialog();
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 确定是否返回，确定返回并取消订单
     */
    public void confirmDialog()
    {

//        ConfirmDialog.Builder dialog = new ConfirmDialog.Builder(DiscountMsfQrActivity.this);
//        dialog.setTitle("是否取消该笔订单？");
//        dialog.setCancelButton("取消");
//        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
        // 取消订单
        DiscountMsfQrActivity.this.finish();
//            }
//        });
//        dialog.create().show();
    }


}
