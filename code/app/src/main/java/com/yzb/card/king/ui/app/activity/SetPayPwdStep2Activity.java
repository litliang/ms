package com.yzb.card.king.ui.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.wallet.logic.comm.PaypswdAddLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiyun
 *         设置支付密码；
 */
public class SetPayPwdStep2Activity extends BaseActivity implements View.OnClickListener
{
    private EditText etPayPwd;
    private EditText etAgainPayPwd;
    private View showOrHidePwdView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pay_pwd_step2);
        assignViews();
    }

    private void assignViews()
    {
        setTitleNmae(getString(R.string.setting_set_pay_pwd));
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
                    resetPayPwd();
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
     * 设置支付密码；
     */
    private void resetPayPwd()
    {
        final String newPwd = etPayPwd.getText().toString().trim();
        Map<String, String> params = new HashMap<>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAmountAccount());
        params.put("payPasswd", newPwd);
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", AppConstant.sign);
        PaypswdAddLogic payHandle = new PaypswdAddLogic(this);
        payHandle.add(params);
        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE) {
                closePDialog();
                toastCustom(R.string.setting_paypwd_set_success);
                finish();
                readyGo(SetPayPwdStep2Activity.this, PwdSettingActivity.class);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                closePDialog();
                toastCustom(R.string.setting_paypwd_set_success);
                finish();
                readyGo(SetPayPwdStep2Activity.this, PwdSettingActivity.class);
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG) {
                closePDialog();
                toastCustom(R.string.setting_paypwd_set_fail);
            }
        });
    }

    private boolean isInputRight()
    {
        String newPwd = etPayPwd.getText().toString().trim();
        String newPwd2 = etAgainPayPwd.getText().toString().trim();

        if (TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(newPwd2))
        {
            ToastUtil.i(this, getString(R.string.setting_pwd_not_allow_empty));
            return false;
        }
        if (newPwd.length() < 6)
        {
            ToastUtil.i(this, getString(R.string.setting_no_valid_pay_pwd));
            return false;
        }

        if (!newPwd.equals(newPwd2))
        {
            ToastUtil.i(this, getString(R.string.setting_pwd_not_equal));
            return false;
        }
        return true;
    }

}
