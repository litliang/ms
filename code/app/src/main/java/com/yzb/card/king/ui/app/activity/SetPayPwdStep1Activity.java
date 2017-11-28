package com.yzb.card.king.ui.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.presenter.VerifyCodePresenter;
import com.yzb.card.king.ui.app.presenter.VerifyCodeValidityPresenter;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.SMSBroadcastHelper;
import com.yzb.card.king.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiyun
 *         设置支付密码-->验证手机；
 */
public class SetPayPwdStep1Activity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface
{
    private TextView etBindedPhone;
    private EditText etMsgCode;
    private TextView tv_send_verify_code;
    private VerifyCodePresenter verifyCodePresenter;
    private VerifyCodeValidityPresenter verifyMobilePresenter;
    private static int VERIFY_CODE_TIME_OUT = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pay_pwd_step1);
        assignViews();
        verifyCodePresenter = new VerifyCodePresenter(this);
        verifyMobilePresenter = new VerifyCodeValidityPresenter(this);
        SMSBroadcastHelper.registerBroadcast(etMsgCode);
    }

    private void assignViews()
    {
        setTitleNmae(getString(R.string.setting_set_pay_pwd));

        etBindedPhone = (TextView) findViewById(R.id.et_binded_phone);

        String mobile = UserManager.getInstance().getUserBean().getAccount();
        if (!TextUtils.isEmpty(mobile))
        {
            etBindedPhone.setText(RegexUtil.hide5PhoneNum(mobile));
        }
        etMsgCode = (EditText) findViewById(R.id.et_msg_code);
        tv_send_verify_code = (TextView) findViewById(R.id.tv_get_msg_code);
        tv_send_verify_code.setOnClickListener(this);

        findViewById(R.id.tv_safe_verify).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_get_msg_code: // 获取验证码；
                getVerifyCode();
                break;
            case R.id.tv_safe_verify:// 进行安全验证；
                if (isInputRight())
                {
                    safeVerifyPhone();
                }
                break;
        }
    }

    /**
     * 验证码验证；
     */
    private void safeVerifyPhone()
    {
        showPDialog(R.string.setting_loading);
        String msgCode = etMsgCode.getText().toString().trim();
        Map<String, Object> param = new HashMap<>();
        param.put("smsCode", msgCode);
        param.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        param.put("type", "5");
        verifyMobilePresenter.loadData(param);
    }

    /**
     * 获取验证码；
     */
    private void getVerifyCode()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount()); //用户名
        params.put("type", "5"); //类别（1注册；2重置登录密码；3发送新密码；4添加信用卡,5，手机号）
        LogUtil.i("获取验证码-提交参数:" + params);
        verifyCodePresenter.loadData(params);
    }

    /**
     * 开始倒计时；
     */
    private void statCount()
    {
        VERIFY_CODE_TIME_OUT = 60;
        tv_send_verify_code.setEnabled(false);
        handler.sendEmptyMessage(0x001);
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            tv_send_verify_code.setText("剩余" + --VERIFY_CODE_TIME_OUT + "s");

            if (VERIFY_CODE_TIME_OUT <= 0)
            {
                tv_send_verify_code.setEnabled(true);
                tv_send_verify_code.setText("发送验证码");
            } else
            {
                handler.sendEmptyMessageDelayed(0x001, 1000);
            }
        }
    };

    private boolean isInputRight()
    {
        String msgCode = etMsgCode.getText().toString().trim();
        if (TextUtils.isEmpty(msgCode))
        {
            ToastUtil.i(this, getString(R.string.debit_rise_not_allow_empty));
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        SMSBroadcastHelper.unRegisterBroadcast();
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if(type ==2){
            closePDialog();
            readyGo(this, SetPayPwdStep2Activity.class);
        }else  if(type == 10){
            toastCustom(R.string.setting_get_verifycode_sucess);
            statCount();
        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if(type == 2){
            closePDialog();
            toastCustom(R.string.app_load_verify_error);
        }else if(type == 10){
            toastCustom(R.string.setting_get_verifycode_fail);
        }

    }
}
