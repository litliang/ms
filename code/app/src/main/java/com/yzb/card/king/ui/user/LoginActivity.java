package com.yzb.card.king.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FragmentMessageEvent;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.activity.ResetLoginPwdActivity;
import com.yzb.card.king.ui.app.activity.VerifyIdentificationActivity;
import com.yzb.card.king.ui.appwidget.AuthCodeView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.user.presenter.LoginPresenter;
import com.yzb.card.king.ui.user.view.LoginView;
import com.yzb.card.king.util.JsonUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.SMSBroadcastHelper;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ValidatorUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by 玉兵 on 2016/6/18.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements LoginView {
    //控件
    @ViewInject(R.id.tvTitle)
    private TextView tvTitle;

    @ViewInject(R.id.phone_login)
    private TextView btnPhone;

    @ViewInject(R.id.zh_login)
    private TextView btnZhLogin;

    @ViewInject(R.id.tvMoble)
    private TextView tvMoble;

    @ViewInject(R.id.tvYanzhenMa)
    private TextView tvYanzhenMa;

    @ViewInject(R.id.etMoble)
    private EditText etMoble;

    @ViewInject(R.id.etYanzhenMa)
    private EditText etYanzhenMa;

    @ViewInject(R.id.tvGetCode)
    private TextView tvGetCode;

    @ViewInject(R.id.llForgetPasswordTemp)
    private LinearLayout llForgetPasswordTemp;

    private AuthCodeView authCodeView;
    //数据
    private boolean loginFlag = true;

    private LoginPresenter loginPresenter;

    private   String regiestPhoneNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        loginPresenter = new LoginPresenter(this, this);

    }

    /**
     * 返回按钮
     *
     * @param v
     */
    public void backAction(View v)
    {

        if (GlobalApp.backFlag) {

            GlobalApp.backFlag = false;

            FragmentMessageEvent event = new FragmentMessageEvent();

            event.setFragmentIndex(2);//此处与appFactory里面的getHomeTabFragmentList方法排序一直

            EventBus.getDefault().post(event);
        }
        setResult(AppConstant.LOGIN_ACCESS);

        finish();
    }

    private void initView()
    {

        tvTitle.setText(R.string.txt_login_str);
        btnPhone.setSelected(true);
        btnZhLogin.setSelected(false);
       // llForgetPasswordTemp.setVisibility(View.INVISIBLE);
        authCodeView = new AuthCodeView(tvGetCode);
        SMSBroadcastHelper.registerBroadcast(etYanzhenMa);
        //手机登陆
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                etMoble.setText("");
                etYanzhenMa.setText("");

                btnPhone.setSelected(true);
                btnZhLogin.setSelected(false);
                SMSBroadcastHelper.registerBroadcast(etYanzhenMa);
                tvMoble.setText(R.string.txt_phone_number);
                tvYanzhenMa.setText(R.string.txt_yanzhengma);
                etMoble.setHint(R.string.hint_phone_number);
                etYanzhenMa.setHint(R.string.hint_yanzhengma);
                tvGetCode.setVisibility(View.VISIBLE);
                loginFlag = true;
                int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL;
                //手机号
                etMoble.setInputType(inputType);
                etMoble.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});//设置长度
                //验证码  数字可见
                etYanzhenMa.setInputType(InputType.TYPE_CLASS_NUMBER);
                etYanzhenMa.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});//设置长度

             //   llForgetPasswordTemp.setVisibility(View.INVISIBLE);

                //特殊处理
                if(!TextUtils.isEmpty(regiestPhoneNumber)){
                    etMoble.setText(regiestPhoneNumber);
                }
                findViewById(R.id.llForgetPassword).setVisibility(View.INVISIBLE);
            }
        });
        //账号登录
        btnZhLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                etMoble.setText("");
                etYanzhenMa.setText("");
                btnPhone.setSelected(false);
                btnZhLogin.setSelected(true);
                SMSBroadcastHelper.unRegisterBroadcast();
                tvMoble.setText(R.string.txt_account);
                tvYanzhenMa.setText(R.string.txt_mima);
                etMoble.setHint(R.string.hint_input_account);
                etYanzhenMa.setHint(R.string.hint_mima);
                tvGetCode.setVisibility(View.INVISIBLE);
                loginFlag = false;
                //账号
                int inputType1 = InputType.TYPE_CLASS_NUMBER;
                etMoble.setInputType(inputType1);
                etMoble.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});//设置长度
                //密码  数字不可见
                etYanzhenMa.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etYanzhenMa.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.MAX_VALUE)});//设置长度
             //   llForgetPasswordTemp.setVisibility(View.VISIBLE);

                //特殊处理
                if(!TextUtils.isEmpty(regiestPhoneNumber)){
                    etMoble.setText(regiestPhoneNumber);
                }
                findViewById(R.id.llForgetPassword).setVisibility(View.VISIBLE);
            }
        });
        btnZhLogin.performClick();
        llForgetPasswordTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),ResetLoginPwdActivity.class));
            }
        });
    }


    @Event(R.id.btRegister)
    private void btRegisterAction(View v)
    {

        Intent in = new Intent(this, RegisterActivity.class);


        startActivityForResult(in, 1000);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        loginPresenter = null;

        GlobalApp.backFlag = false;
    }


    @Event(R.id.btLogin)
    private void btLogin(View v)
    {

        if (loginFlag) {//手机登录

            if (chechData()) {
                //手机登录 2：入参
                loginPresenter.loginAction(2);
            }

        } else {//账号登录

            if (chechData()) {
                //账号登录 1:入参
                loginPresenter.loginAction(1);

            }

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (GlobalApp.backFlag) {

                GlobalApp.backFlag = false;

                FragmentMessageEvent event1 = new FragmentMessageEvent();

                event1.setFragmentIndex(2);//此处与appFactory里面的getHomeTabFragmentList方法排序一直

                EventBus.getDefault().post(event1);
            }

            setResult(AppConstant.LOGIN_ACCESS);
            finish();
        }

        return false;

    }

    @Event(R.id.llForgetPassword)
    private void llForgetPassword(View v)
    {
        Intent intent = new Intent(this, VerifyIdentificationActivity.class);

        startActivity(intent);

    }

    @Event(R.id.qq)
    private void qq(final View v)
    {
        UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.QQ, new UMAuthListener(){

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Toast.makeText(v.getContext(), JsonUtil.entityToJson(map), Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });

    }

    @Event(R.id.wx)
    private void wx(final View v)
    {
//
//        JShareInterface.authorize(platform, new AuthListener() {
//            @Override
//            public void onComplete(PlatformConfig.Platform platform, int i, BaseResponseInfo data) {
//
//                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new UMAuthListener(){
//
//            @Override
//            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                Toast.makeText(v.getContext(), JsonUtil.entityToJson(map), Toast.LENGTH_LONG).show();
//                finish();
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA share_media, int i) {
//
//            }
//        });

    }

    @Event(R.id.wb)
    private void wb(final View v)
    {


        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA, new UMAuthListener(){

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Toast.makeText(v.getContext(), JsonUtil.entityToJson(map), Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });


    }
    @Event(R.id.tvGetCode)
    private void tvGetCode(View v)
    {

        String str1 = etMoble.getText().toString().trim();

        authCodeView.sendCodeRequest(str1,"1", "5");

    }
    @Override
    public String[] loginInfo()
    {

        String loginAccountStr = etMoble.getText().toString().trim();

        String loginPasswordStr = etYanzhenMa.getText().toString().trim();

        String[] dataStr = new String[]{loginAccountStr, loginPasswordStr};

        return dataStr;
    }

    @Override
    public void loginCallBack()
    {
        UserManager.getInstance().update();
        setResult(AppConstant.LOGIN_ACCESS);
        finish();
        ToastUtil.i(this, "登录成功");
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1001) {
            finish();
        }

        if(requestCode == 1000){

            if(resultCode == 1019 && data != null){

                 regiestPhoneNumber = data.getStringExtra("newAccountData");

                etMoble.setText(regiestPhoneNumber);

            }

        }
    }


    /**
     * 检测数据
     *
     * @return
     */
    private boolean chechData()
    {

        boolean flag = true;

        int str = 0;

        String str1 = etMoble.getText().toString().trim();

        String str2 = etYanzhenMa.getText().toString().trim();

        if (loginFlag) {

            if (TextUtils.isEmpty(str1)) {

                flag = false;

                str = R.string.hint_phone_number;

            } else if (TextUtils.isEmpty(str2)) {

                flag = false;

                str = R.string.hint_yanzhengma;
            } else if (!str1.equals("17638101821")&&!ValidatorUtil.isMobile(str1)) {

                flag = false;

                str = R.string.toast_chech_your_phone_number;

            }

        } else {

            if (TextUtils.isEmpty(str1)) {

                flag = false;

                str = R.string.hint_input_account;

            } else if (TextUtils.isEmpty(str2)) {

                flag = false;

                str = R.string.hint_mima;

            }else if (!ValidatorUtil.isZcPassword(str2))
            {
                flag = false;

                str = R.string.toast_password_isSure;

            }

        }

        if (!flag) {
            ToastUtil.i(this,str);
        }

        return flag;
    }


}
