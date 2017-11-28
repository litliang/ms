package com.yzb.card.king.ui.app.activity;

import android.os.Bundle;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;

/**
 * @author gengqiyun
 * @date 2016.6.19
 * 验证身份证件提示也；
 */
public class VerifyIdentificationNoticeActivity extends BaseActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_identification_notice);
        assignViews();
    }

    private void assignViews()
    {
        setTitleNmae(getString(R.string.setting_reset_login_pwd));
        findViewById(R.id.rl_verify_identification).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.rl_verify_identification:
                readyGo(this, VerifyIdentificationActivity.class);
                break;
        }
    }

}
