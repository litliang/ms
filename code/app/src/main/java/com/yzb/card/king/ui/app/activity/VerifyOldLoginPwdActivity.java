package com.yzb.card.king.ui.app.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.UiUtils;

import org.xutils.common.util.MD5;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.19
 * 验证旧登录密码；
 */
public class VerifyOldLoginPwdActivity extends BaseActivity implements View.OnClickListener
{
    private static final String TYPE_PWD = "1";//类别（1密码；2证件）
    private EditText etPayPwd;
    private View showOrHidePwdView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_old_login_pwd);
        assignViews();
    }

    private void assignViews()
    {
        setTitleNmae(getString(R.string.setting_reset_login_pwd));
        etPayPwd = (EditText) findViewById(R.id.et_pay_pwd);
        showOrHidePwdView = findViewById(R.id.iv_show_or_hide_pwd);
        hidePwd();

        showOrHidePwdView.setOnClickListener(this);
        showOrHidePwdView.setBackgroundResource(R.mipmap.icon_hide_grey);

        findViewById(R.id.tv_forget_login_pwd).setOnClickListener(this);
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
            case R.id.tv_forget_login_pwd: //忘记登录密码；
                readyGo(this, VerifyIdentificationNoticeActivity.class);
                break;
            case R.id.tv_ok:
                if (isInputRight())
                {
                    exeVerifyPwd(etPayPwd.getText().toString().trim());
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

    /**
     * 验证旧的登录密码；
     *
     * @param oldLoginPwd
     */
    private void exeVerifyPwd(final String oldLoginPwd)
    {
        showPDialog(R.string.setting_checking_login_pwd);
        Map<String, Object> params = new HashMap<>();

        params.put("provingMobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("type", TYPE_PWD);
        params.put("passwd", MD5.md5(oldLoginPwd)); // 旧密码；
        SimpleRequest<String> request = new SimpleRequest<String>(CardConstant.setting_provingcustomerinfo)
        {
            @Override
            protected String parseData(String data)
            {
                return data;
            }
        };
        request.setParam(params);
        request.sendRequestNew(new BaseCallBack<String>()
        {
            @Override
            public void onSuccess(String data)
            {
                closePDialog();
                readyGo(VerifyOldLoginPwdActivity.this, ResetLoginPwdActivity.class);
            }

            @Override
            public void onFail(Error error)
            {
                closePDialog();
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private boolean isInputRight()
    {
        String oldPwd = etPayPwd.getText().toString().trim();
        if (isEmpty(oldPwd))
        {
            toastCustom(R.string.setting_pwd_not_allow_empty);
            return false;
        }
        return true;
    }

}
