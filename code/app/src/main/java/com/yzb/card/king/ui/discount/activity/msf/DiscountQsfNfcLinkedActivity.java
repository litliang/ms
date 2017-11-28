package com.yzb.card.king.ui.discount.activity.msf;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.QrPayBean;
import com.yzb.card.king.bean.order.FastPaymentOrderBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.PreferencesService;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.nfc.LoyaltyCardReader;
import com.yzb.card.king.util.nfc.MyHostApduService;
import com.yzb.card.king.zxing.activity.MipcaActivityCapture;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 类  名：牵手付 sms
 * 作  者：Li Yubing
 * 日  期：2017/2/9
 * 描  述：
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class DiscountQsfNfcLinkedActivity extends MsfBaseActivity implements LoyaltyCardReader.MsgCallback {


    private TextView showAmount;

    private String money = "-1000";

    private LinearLayout llPayMoney, llGetmoney;

    private String userSessionId;

    private String customerType;

    //NFC
    public static final String TAG = "AppCompatActivity";
    private Vibrator mVibrator;
    public LoyaltyCardReader mLoyaltyCardReader;
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    private String nfcWallInfoStr;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_discount_qsf_smsd);
        // 设置标题
        setHeader(R.mipmap.icon_back_white, "牵手付");

        switchContentLeft(AppConstant.RES_HOME_PAGE);

        init();
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

        showAmount = (TextView) findViewById(R.id.showAmount);

        Intent intent = getIntent();

        if (intent.hasExtra("money")) {

            money = intent.getStringExtra("money");

            showAmount.setText(money);
        }
        //注册evenBus
        GlobalApp.activityStr = "qsNfcLinked";
        EventBus.getDefault().register(this);

        userStatusUI();
        //开启nfc
        nfcAction();

        setNfcStatus();


    }

    private void nfcAction()
    {

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mLoyaltyCardReader = new LoyaltyCardReader(this);


        if (!flagPayType) {

            enableReaderMode();

            uiHandler.sendEmptyMessageAtTime(0, 1000);

        } else {

            disableReaderMode();

            uiHandler.sendEmptyMessageAtTime(0, 1000);
        }


    }

    private void setNfcStatus()
    {

        if (flagPayType) {

            llPayMoney.setVisibility(View.VISIBLE);

            llGetmoney.setVisibility(View.GONE);


        } else {

            llGetmoney.setVisibility(View.VISIBLE);

            llPayMoney.setVisibility(View.GONE);

        }

    }

    private void init()
    {

        headerLayout = (RelativeLayout) findViewById(R.id.headerLayout);

        llPayMoney = (LinearLayout) findViewById(R.id.llPayMoney);

        llGetmoney = (LinearLayout) findViewById(R.id.llGetmoney);

        showGet = (TextView) findViewById(R.id.showGet);

        showPay = (TextView) findViewById(R.id.showPay);
        showGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (flagPayType) {

                    payType = AppConstant.CREDIT;

                    Intent intent1 = new Intent();

                    intent1.putExtra("payType", AppConstant.CREDIT);

                    intent1.setClass(DiscountQsfNfcLinkedActivity.this, DiscountQsfMoneyActivty.class);

                    startActivity(intent1);

                    finish();
                }

            }
        });

        showPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (!flagPayType) {

                    payType = AppConstant.DEBIT;

                    uiHandler1.sendEmptyMessage(0);

                }
            }
        });

    }

    private Handler uiHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            initUI();
            setNfcStatus();
        }
    };

    /**
     * 标题左侧返回
     */
    public void switchContentLeft(final int resultCode)
    {

        LinearLayout headerLeft = (LinearLayout) findViewById(R.id.headerLeft);

        // 返回首页
        headerLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                setResult(resultCode);

                finish();
            }
        });
    }

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            // ToastUtil.i(DiscountQsfNfcLinkedActivity.this,"payType="+payType);
            //nfc传递信息
            nfcWallInfoStr = PayinfoUtil.createPayGetInfo(null, money, payType, customerType, userSessionId);

            Intent intent = new Intent(DiscountQsfNfcLinkedActivity.this, MyHostApduService.class);

            intent.putExtra("sendMsg", nfcWallInfoStr);

            intent.putExtra("payment", flagPayType);

            DiscountQsfNfcLinkedActivity.this.startService(intent);

            mLoyaltyCardReader.setSendMsg(nfcWallInfoStr);

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
        if("qsNfcLinked".equals(event.getMarkActivity())){//接收转账成功信息

            AppUtils.transferInforRemind(this,flagPayType,event);

        }
    }

    @Override
    public void onMsgReceived(final String a)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run()
            {

                mVibrator.vibrate(300);

                //收款方读取付款方通过nfc传递的数据信息
                /*
                   1、判断对方是否存在网络
                   2、根据对方的网络信息跳转相应的页面
                 */
                QrPayBean qrPayBean = PayinfoUtil.parsingQrBitmap(a);

                if (qrPayBean != null) {

                    boolean netFlag = qrPayBean.isNetStatus();

                    if (netFlag) {//有网络，页面无需跳转


                    } else {//无网络跳转到，收方转账页面

                        QrPayBean qrPayBeanLocal = PayinfoUtil.parsingQrBitmap(nfcWallInfoStr);


                        qrPayBean.setAmount(qrPayBeanLocal.getAmount());

                        Intent goMsfGetPsd = new Intent();
                        goMsfGetPsd.putExtra("payType", payType);
                        goMsfGetPsd.putExtra(MipcaActivityCapture.QRPAYBEAN, qrPayBean);
                        goMsfGetPsd.setClass(DiscountQsfNfcLinkedActivity.this, DiscountMsfPaySMSDActivity.class);
                        DiscountQsfNfcLinkedActivity.this.startActivity(goMsfGetPsd);

                    }

                }else{

                    ToastUtil.i(DiscountQsfNfcLinkedActivity.this,"请重新操作。");
                }


            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        if (intent.getExtras() != null) {

            //付款方读取付款方通过nfc传递的数据信息
                /*
                   1、判断对方是否存在网络
                   2、根据对方的网络信息跳转相应的页面
                 */

            String recvMsg = intent.getExtras().getString("recvMsg");

            LogUtil.e("BBBB","recvMsg="+recvMsg);
            QrPayBean qrPayBean = PayinfoUtil.parsingQrBitmap(recvMsg);
            boolean netStatus = ServiceDispatcher.isNetworkConnected(GlobalApp.getInstance().getContext());
            if (netStatus) {//有网络，跳转到付款方页面

                Intent goMsfGetPsd = new Intent();
                goMsfGetPsd.putExtra("payType", payType);
                goMsfGetPsd.putExtra(MipcaActivityCapture.QRPAYBEAN, qrPayBean);
                goMsfGetPsd.setClass(DiscountQsfNfcLinkedActivity.this, DiscountMsfPaySMSDActivity.class);
                DiscountQsfNfcLinkedActivity.this.startActivity(goMsfGetPsd);

            } else {
                ToastUtil.i(DiscountQsfNfcLinkedActivity.this,"请重新操作。");
            }

        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        disableReaderMode();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!flagPayType) enableReaderMode();
    }

    private void disableReaderMode()
    {
        Log.i(TAG, "Disabling reader mode");

        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
        if (nfc != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                nfc.disableReaderMode(this);
            }


        }
    }

    private void enableReaderMode()
    {
        Log.i(TAG, "Enabling reader mode");

        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
        if (nfc != null) {
            nfc.enableReaderMode(this, mLoyaltyCardReader, READER_FLAGS, null);
        }
    }


}
