package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.app.fragment.IInputDialog;
import com.yzb.card.king.ui.app.fragment.PwdInputDialogFragment;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.wallet.gridpasswordview.GridPasswordView;
import com.yzb.wallet.logic.comm.PaypswdValidateLogic;
import com.yzb.wallet.openInterface.PayPwdHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.19
 * 支付密码安全校验；
 */
public class SafeVerifyOldPayPwdActivity extends BaseActivity implements View.OnClickListener,IInputDialog
{
    public static final String SOURCE_KEY = "sourceActivity";
    public static final int RESET_PAY_PWD_ACTIVITY = 1;
    public static final int MODIFY_BIND_PHONE_ACTIVITY = 2;

    protected GridPasswordView gvPayPwd;
    protected TextView tvInputNotice;
    protected TextView tvNextStep;

    private int sourceActivity;
    protected View viewOtherPayMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_verify_pay_pwd);
        initData();
        assignViews();
    }

    private void initData()
    {
        sourceActivity = getIntent().getIntExtra(SOURCE_KEY, 0);
    }

    protected void assignViews()
    {
        setTitleNmae(getString(R.string.setting_safe_verify2));
        tvInputNotice = (TextView) findViewById(R.id.tv_input_notice);

        gvPayPwd = (GridPasswordView) findViewById(R.id.gv_pay_pwd);

        viewOtherPayMethod = findViewById(R.id.tv_other_pay_method);

        viewOtherPayMethod.setOnClickListener(this);

        // 遮盖层,不弹出系统自带键盘
        EditText  shadowText = ((EditText) findViewById(R.id.shadowText));
        shadowText.clearFocus();
        tvNextStep = (TextView) findViewById(R.id.tv_next_step);
        tvNextStep.setOnClickListener(this);

        shadowText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showPwdInputDialog();
            }
        });
    }

    @Override
    public void updatePwd(String keyWord)
    {
        //删除；
        if (keyWord == null)
        {
            String orignalPwd = gvPayPwd.getPassWord().trim();
            if (!TextUtils.isEmpty(orignalPwd))
            {
                gvPayPwd.setPassword(orignalPwd.substring(0, orignalPwd.length() - 1));
            }
        } else
        {
            gvPayPwd.setPassword(gvPayPwd.getPassWord() + keyWord);
        }
    }

    /**
     * 显示密码输入键盘；
     */
    private void showPwdInputDialog()
    {
        PwdInputDialogFragment pwdInputDialogFragment = PwdInputDialogFragment.getInstance("", "");
        pwdInputDialogFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_other_pay_method:
                readyGo(this, VerifyIdentificationNeedBankActivity.class);
                break;
            case R.id.tv_next_step:
                switchNextActivity();
                break;
        }
    }

    private void switchNextActivity()
    {
        switch (sourceActivity)
        {
            case MODIFY_BIND_PHONE_ACTIVITY:
            case RESET_PAY_PWD_ACTIVITY://支付密码
                if (isInputRight())
                {
                    checkPassword(gvPayPwd.getPassWord());
                }
                break;
        }
    }

    private void checkPassword(String payPwd)
    {
        showPDialog(R.string.setting_checking_login_pwd);
        // 刑琰接口---- 支付密码效验
        PaypswdValidateLogic payHandle = new PaypswdValidateLogic(this);
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                closePDialog();
                // 验证密码成功
                if (RESULT_CODE.equals(AppConstant.CODE_OK))
                {
                    readyGoThenFinish(SafeVerifyOldPayPwdActivity.this, ResetNewPayPwdActivity.class);
                }
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                closePDialog();

            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                closePDialog();
                UiUtils.shortToast(ERROR_MSG);
                clearPassword();
            }
        });

        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("payPasswd", payPwd);
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);
        LogUtil.e("密码校验参数：" + JSON.toJSONString(params));
        payHandle.validate(params);
    }

    private void clearPassword()
    {
        gvPayPwd.setPassword("");
    }

    /**
     * 验证旧的支付密码；
     */
    protected void startVerify()
    {
        showPDialog(R.string.setting_checking_login_pwd);
        final String pwd = gvPayPwd.getPassWord().trim();
        PayPwdHandle payHandle = new PayPwdHandle(this);
        payHandle.validate(UserManager.getInstance().getUserBean().getAmountAccount(), pwd);

        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                closePDialog();
                toastCustom(R.string.setting_verify_success);
                if (sourceActivity == MODIFY_BIND_PHONE_ACTIVITY)
                {
                    Intent intent = new Intent(UiUtils.getContext(), ModifyBindPhoneActivity.class);
                    intent.putExtra("step", 2);
                    startActivity(intent);
                } else if (sourceActivity == RESET_PAY_PWD_ACTIVITY)
                {
                    readyGoThenFinish(SafeVerifyOldPayPwdActivity.this, ResetNewPayPwdActivity.class);
                }
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                closePDialog();
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                closePDialog();
                toastCustom(R.string.app_load_verify_error);
            }
        });

    }

    protected boolean isInputRight()
    {
        String pwd = gvPayPwd.getPassWord();
        if (!TextUtils.isEmpty(pwd) && pwd.length() == 6)
        {
            return true;
        }
        ToastUtil.i(this, getString(R.string.setting_please_input_six_pwd));
        return false;
    }
}
