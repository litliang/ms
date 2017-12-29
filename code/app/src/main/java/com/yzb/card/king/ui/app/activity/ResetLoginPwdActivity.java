package com.yzb.card.king.ui.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.app.presenter.ResetLoginPwdPresenter;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.ValidatorUtil;

import org.xutils.common.util.MD5;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.19
 * 重置登录密码；
 */
public class ResetLoginPwdActivity extends BaseActivity implements View.OnClickListener, AppBaseView
{
    private EditText etPayPwd;
    private EditText etAgainPayPwd;
    private View showOrHidePwdView;
    private ResetLoginPwdPresenter resetLoginPwdPresenter; //重置登录密码；

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_login_pwd);
        resetLoginPwdPresenter = new ResetLoginPwdPresenter(this);
        assignViews();
    }

    private void assignViews()
    {
        setTitleNmae(getString(R.string.setting_reset_login_pwd));
        etPayPwd = (EditText) findViewById(R.id.et_pay_pwd);
        etAgainPayPwd = (EditText) findViewById(R.id.et_again_pay_pwd);
        showOrHidePwdView = findViewById(R.id.iv_show_or_hide_pwd);
        showOrHidePwdView.setOnClickListener(this);
        showOrHidePwdView.setBackgroundResource(R.mipmap.icon_hide_grey);
        hidePwd();
        findViewById(R.id.tv_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_show_or_hide_pwd:
                // 此时显示密码；
                if (!"1".equals(v.getTag()))
                {
                    showPwd();
                } else
                {
                    hidePwd();
                }
                break;
            case R.id.tv_ok:
                if (isInputRight())
                {
                    resetLoginPwd();
                }
                break;
        }
    }

    /**
     * 显示密码；
     */
    public void showPwd()
    {
        showOrHidePwdView.setBackgroundResource(R.mipmap.icon_eye_grey);
        etPayPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        etPayPwd.setSelection(etPayPwd.getText().toString().length());
        showOrHidePwdView.setTag("1");
    }

    /**
     * 隐藏密码；
     */
    public void hidePwd()
    {
        showOrHidePwdView.setBackgroundResource(R.mipmap.icon_hide_grey);
        etPayPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etPayPwd.setSelection(etPayPwd.getText().toString().length());
        showOrHidePwdView.setTag(null);
    }

    @Override
    public void onViewCallBackSucess(Object data)
    {
        closePDialog();
        toastCustom(getString(R.string.setting_reset_pwd_sucess));
        if(UserManager.getInstance().isLogin()){

            readyGo(ResetLoginPwdActivity.this, PwdSettingActivity.class);

        }else{
            readyGo(ResetLoginPwdActivity.this, LoginActivity.class);

        }

    }

    @Override
    public void onViewCallBackFail(String failMsg)
    {
        closePDialog();
        toastCustom(failMsg);
    }

    /**
     * 重置登录密码；
     */
    private void resetLoginPwd()
    {
        showPDialog(R.string.setting_committing);
        final String newPwd = etPayPwd.getText().toString().trim();
        Map<String, Object> params = new HashMap<>();
        params.put("customerMod", UserBean.MOD);
        params.put("passwd", MD5.md5(newPwd));
        params.put("resetMobile",UserManager.getInstance().getUserBean()!=null?UserManager.getInstance().getUserBean().getAccount():getIntent().getStringExtra("userPhone"));
        resetLoginPwdPresenter.loadData(params);
    }

    /**
     * 验证入参格式；
     *
     * @return
     */
    private boolean isInputRight()
    {
        String newPwd = etPayPwd.getText().toString().trim();
        String newPwd2 = etAgainPayPwd.getText().toString().trim();
        if (TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(newPwd2))
        {
            toastCustom(R.string.setting_pwd_not_allow_empty);
            return false;
        }

        if (!ValidatorUtil.isLoginPassword(newPwd))
        {
            toastCustom(R.string.setting_pwd_format_error);
            return false;
        }

        if (!newPwd.equals(newPwd2))
        {
            toastCustom(R.string.setting_pwd_not_equal);
            return false;
        }
        return true;
    }

}
