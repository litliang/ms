package com.yzb.card.king.zxing.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.QrPayBean;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.appdialog.ConfirmDialog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.activity.msf.DiscountMsfMoneyPGActivity;
import com.yzb.card.king.ui.discount.activity.msf.DiscountMsfPaySMSDActivity;
import com.yzb.card.king.ui.discount.activity.msf.PayinfoUtil;
import com.yzb.card.king.ui.integral.IntegrationShareActivity;
import com.yzb.card.king.ui.my.activity.SelectCardActivity;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.PreferencesService;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ValidatorUtil;
import com.yzb.card.king.zxing.camera.CameraManager;
import com.yzb.card.king.zxing.decoding.CaptureActivityHandler;
import com.yzb.card.king.zxing.decoding.InactivityTimer;
import com.yzb.card.king.zxing.task.AddPointContactTask;
import com.yzb.card.king.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

/**
 * 二维码扫描
 */
public class MipcaActivityCapture extends BaseActivity implements Callback {

    public final static  String  QRPAYBEAN = "qrPayBean";
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private LinearLayout switchLayout;
    private TextView showGet;
    private TextView showPay;
    private String payType;
    //  首付款
    private boolean isSaoYiSao;

    private RelativeLayout headerLayout;

    private  QrPayBean bean;

    private boolean flagPayType;

    private LinearLayout headerRight;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        defaultFlag = true;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.qr_capture);

        CameraManager.init(getApplication());

        switchLayout = (LinearLayout) findViewById(R.id.switchLayout);

        headerLayout = (RelativeLayout) findViewById(R.id.headerLayout);

        showGet = (TextView) findViewById(R.id.showGet);

        showPay = (TextView) findViewById(R.id.showPay);

        headerRight = (LinearLayout) findViewById(R.id.headerRight);

        // 获取bundle 判断是扫一扫还是码上付
        isSaoYiSao = getIntent().getBooleanExtra("isSaoYiSao", false);

        if (isSaoYiSao) {

            setHeader("二维码/条码");
            switchLayout.setVisibility(View.GONE);
            headerRight.setVisibility(View.INVISIBLE);

        } else {
             //R.mipmap.icon_refund_qr
            setHeader("码尚付");

            headerLayout.setBackgroundColor(Color.TRANSPARENT);

            payType = getIntent().getStringExtra("payType");

            handlerPayGetUI();

            headerRight.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(MipcaActivityCapture.this, DiscountMsfMoneyPGActivity.class);

                    intent.putExtra("payType", payType);

                    MipcaActivityCapture.this.startActivity(intent);

                    MipcaActivityCapture.this.finish();
                }
            });
        }


        //收款方
        showGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(!flagPayType){
                    return;
                }

                payType = AppConstant.CREDIT;

                if (ServiceDispatcher.isNetworkConnected(MipcaActivityCapture.this)) {

                    handlerPayGetUI();

                } else {

                    Intent intent = new Intent(MipcaActivityCapture.this, DiscountMsfMoneyPGActivity.class);

                    intent.putExtra("payType", payType);

                    MipcaActivityCapture.this.startActivity(intent);

                    MipcaActivityCapture.this.finish();
                }
            }
        });
        //付款方
        showPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(flagPayType){
                    return;
                }
                payType = AppConstant.DEBIT;

                if (ServiceDispatcher.isNetworkConnected(MipcaActivityCapture.this)) {

                    handlerPayGetUI();

                } else {

                    Intent intent = new Intent(MipcaActivityCapture.this, DiscountMsfMoneyPGActivity.class);
                    intent.putExtra("payType", payType);
                    MipcaActivityCapture.this.startActivity(intent);

                }
            }
        });

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
    }

    private void handlerPayGetUI()
    {
        flagPayType = payType.equals(AppConstant.DEBIT);
        //支付和收款按钮处理
        showGet.setTextColor(flagPayType ? getResources().getColor(R.color.wordGrey) : getResources().getColor(R.color.wordBlue));

        showPay.setTextColor(flagPayType ? getResources().getColor(R.color.wordRed) : getResources().getColor(R.color.wordGrey));

        if(flagPayType){
            headerRight.setVisibility(View.INVISIBLE);
        }else{
            headerRight.setVisibility(View.VISIBLE);

        }

       // headerLayout.setBackgroundColor(payType.equals(AppConstant.DEBIT) ? getResources().getColor(R.color.wordRed) : getResources().getColor(R.color.titleBlue));
    }

    /**
     * 提示是付款还是收款
     */
    public void ConfirmDialog(String title)
    {

        ConfirmDialog.Builder dialog = new ConfirmDialog.Builder(MipcaActivityCapture.this);
        dialog.setTitle(title);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // 返回
                dialog.dismiss();
            }
        });
        dialog.create().show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy()
    {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode)
    {
        inactivityTimer.onActivity();

        playBeepSoundAndVibrate();

        String resultString = result.getText();


        if (resultString.equals("")) {
            Toast.makeText(MipcaActivityCapture.this, "扫描失败!", Toast.LENGTH_SHORT).show();
        } else {

            int index = resultString.indexOf(ServiceDispatcher.base_url_api);

            if (index == -1) {

                //网址
                if (ValidatorUtil.isUrl(resultString)) {
                    final String url = resultString;
                    ConfirmDialog.Builder dialog = new ConfirmDialog.Builder(MipcaActivityCapture.this);
                    dialog.setTitle("是否打开地址：" + resultString);
                    dialog.setCancelButton("取消");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Uri uri = Uri.parse(url);
                            Intent intentUri = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intentUri);
                        }
                    });
                    dialog.create().show();
                    return;
                }

            } else {

                if (resultString.indexOf(AppConstant.ADD_POINT_CONTACT) != -1) {//添加到积分分享名单
                    String[] strings = resultString.split("&");
                    new AddPointContactTask(strings[1], strings[2]).sendRequest(null);
                    startActivity(new Intent(this, IntegrationShareActivity.class));
                    finish();
                    return;
                }

                int fastIndex = resultString.indexOf("createFastPaymentOrder");
                LogUtil.e("BBBB","--------MipcaAc------fastIndex="+fastIndex);
                /**
                 * 码尚付
                 */
                if (fastIndex != -1) {

                    PreferencesService pre = new PreferencesService(GlobalApp.getInstance().getContext());

                    String sessionIdTemp = pre.getValue("qrCodeSessionId");

                    AppConstant.qrCodeSessionId = sessionIdTemp;

                    LogUtil.e("BBBB","--------MipcaAc------sessionIdTemp="+sessionIdTemp);

                    if (TextUtils.isEmpty(sessionIdTemp)) {

                        ToastUtil.i(this, "暂无用户信息，请登录");

                        return;
                    }
                    String customerType = pre.getValue("customerType");
                    if (TextUtils.isEmpty(customerType)) {

                        ToastUtil.i(this, "暂无用户信息，请登录");

                        return;
                    }

                     bean = PayinfoUtil.parsingQrBitmap(resultString);

                    if(bean == null){

                        ToastUtil.i(this,"扫描识别，请重新操作");
                    }

                    String  qrFlag =   bean.getFlag();

                    LogUtil.e("BBBB","--------MipcaAc------qrFlag="+qrFlag);

                    if(payType.equals(qrFlag)){

                        if(qrFlag.equals(AppConstant.CREDIT)){

                            ToastUtil.i(this,"请切换成付款方");
                        }else{
                            ToastUtil.i(this,"请切换成收款方");
                        }

                        return;
                    }

                    //码尚付
                    if (payType.equals(AppConstant.CREDIT)) {// 如果是收款扫
                        // 处理扫描结果 得到
                        Intent goMsfGetPsd = new Intent();
                        goMsfGetPsd.putExtra("payType", payType);
                        goMsfGetPsd.putExtra(QRPAYBEAN,bean);
                        goMsfGetPsd.setClass(this, DiscountMsfPaySMSDActivity.class);
                        startActivityForResult(goMsfGetPsd, AppConstant.REQ_MSF_PAY_QR);
                        this.finish();

                    } else if (payType.equals(AppConstant.DEBIT)) { // 如果是付款扫
                        //检查是否是商户还是普通用户
                        String customerTypeQr = bean.getCustomerType();

                        if (UserBean.C.equals(customerTypeQr)) {

                            // 获取对方ID
                            Intent intent1 = new Intent();
                            intent1.putExtra("platformFlag", true);
                            intent1.setClass(this, SelectCardActivity.class);
                            startActivityForResult(intent1, 10000);

                        } else {
                            Intent goMsfGetPsd = new Intent();
                            goMsfGetPsd.putExtra("payType", payType);
                            goMsfGetPsd.putExtra(QRPAYBEAN,bean);
                            goMsfGetPsd.setClass(this, DiscountMsfPaySMSDActivity.class);
                            startActivityForResult(goMsfGetPsd, AppConstant.REQ_MSF_PAY_QR);
                            this.finish();

                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == AppConstant.REQ_MSF_GET_PSD || requestCode == AppConstant.REQ_MSF_PAY) && resultCode == AppConstant.RES_MSF_GET) {
            setResult(AppConstant.RES_MSF_GET);
            finish();
        } else if (requestCode == 10000) {

            if (RESULT_OK == resultCode) {

                PayMethod payMethod = (PayMethod) data.getSerializableExtra("payMethod");
                //输入金额
                Intent intent = new Intent();
                intent.setClass(MipcaActivityCapture.this, DiscountMsfPaySMSDActivity.class);
                intent.putExtra("payType", AppConstant.DEBIT);
                intent.putExtra("payMethod", payMethod);
                intent.putExtra(QRPAYBEAN,bean);
                startActivityForResult(intent, requestCode);
            }
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder)
    {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView()
    {
        return viewfinderView;
    }

    public Handler getHandler()
    {
        return handler;
    }

    public void drawViewfinder()
    {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound()
    {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate()
    {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer)
        {
            mediaPlayer.seekTo(0);
        }
    };


}