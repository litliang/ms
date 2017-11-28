package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;

/**
 * @author gengqiyun
 * @date 2016.6.19
 * 重置支付密码；
 */
public class ResetPayPwdActivity extends BaseActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pay_pwd);
        assignViews();
    }

    private void assignViews()
    {
        setTitleNmae(getString(R.string.setting_reset_pay_pwd));

        findViewById(R.id.rl_input_orignal_pay_pwd).setOnClickListener(this);
        findViewById(R.id.rl_forget_orignal_pay_pwd).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.rl_input_orignal_pay_pwd:
                Intent intent = new Intent(this, SafeVerifyOldPayPwdActivity.class);
                intent.putExtra(SafeVerifyOldPayPwdActivity.SOURCE_KEY, SafeVerifyOldPayPwdActivity.RESET_PAY_PWD_ACTIVITY);
                startActivity(intent);
                break;
            case R.id.rl_forget_orignal_pay_pwd:
                validIdentify();
                break;
        }
    }

    private void validIdentify()
    {
        Intent intent = new Intent(this,VerifyIdentificationActivity.class);
        intent.putExtra("source","ResetPayPwdActivity");
        startActivity(intent);
    }

    @Override
    protected boolean isApplyStatusBarTranslucency()
    {
        return true;
    }
}
