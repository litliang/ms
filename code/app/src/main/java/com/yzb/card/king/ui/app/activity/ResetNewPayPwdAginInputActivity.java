package com.yzb.card.king.ui.app.activity;

import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.LogUtil;
import com.yzb.wallet.logic.comm.PaypswdAddLogic;
import com.yzb.wallet.openInterface.PayPwdHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.19
 * 重置新支付密码(第2次密码输入)；
 */
public class ResetNewPayPwdAginInputActivity extends SafeVerifyOldPayPwdActivity
{
    private String firstPwd; // 上次输入的密码；

    @Override
    protected void assignViews()
    {
        super.assignViews();
        firstPwd = getIntent().getStringExtra("firstPwd");

        setTitleNmae(getString(R.string.setting_reset_pay_pwd));
        tvInputNotice.setText(getString(R.string.setting_safe_verify_new_notice2));
        tvNextStep.setText(getString(R.string.finish));

        viewOtherPayMethod.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_next_step: // 完成；
                if (isInputRight())
                {
                    // 验证密码等值情况；
                    if (!gvPayPwd.getPassWord().equals(firstPwd))
                    {
                        toastCustom(R.string.setting_pwd_not_equal);
                        return;
                    }
                    checkPayPwdEquals();
                }
                break;
        }
    }

    /**
     * 检验新旧支付密码是否相同；
     */
    private void checkPayPwdEquals()
    {
        showPDialog(R.string.setting_committing);

        String newPwd = gvPayPwd.getPassWord().trim();

        PayPwdHandle payHandle = new PayPwdHandle(this);

        UserBean userBean = UserManager.getInstance().getUserBean();
        if (!UserManager.getInstance().isLogin())
        {
            toastCustom(R.string.toast_no_login);
            return;
        }
        payHandle.validate(userBean.getAmountAccount(), newPwd);

        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                closePDialog();
                toastCustom(R.string.setting_pwd_same);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                // 新旧密码不同；开始重置；
                startVerify();
            }
        });
    }

    /**
     * 开始重置支付密码；
     */
    @Override
    protected void startVerify()
    {
        final String newPwd = gvPayPwd.getPassWord().trim();
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("payPasswd",newPwd);
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);
        PaypswdAddLogic payHandle = new PaypswdAddLogic(this);

        payHandle.add(params);
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                closePDialog();
                toastCustom(R.string.setting_paypwd_set_success);
                LogUtil.e("RESULT_CODE" + RESULT_CODE);
                readyGo(ResetNewPayPwdAginInputActivity.this, PwdSettingActivity.class);
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
                toastCustom(R.string.setting_paypwd_set_fail);
            }
        });
    }

}
