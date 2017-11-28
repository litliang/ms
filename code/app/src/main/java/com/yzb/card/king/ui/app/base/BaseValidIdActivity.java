package com.yzb.card.king.ui.app.base;

import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/21 10:44
 * 描述：
 */
public abstract class BaseValidIdActivity extends BaseValidActivity
{
    @Override
    protected void setStepText(TextView tvStep1, TextView tvStep2, TextView tvStep3)
    {
        llStep.setVisibility(View.GONE);
        tvStep1.setText(getString(R.string.setting_verify_zj));
        tvStep2.setText(getString(R.string.setting_bank_card));
        tvStep3.setText(getString(R.string.setting_verify_cg));
    }
}
