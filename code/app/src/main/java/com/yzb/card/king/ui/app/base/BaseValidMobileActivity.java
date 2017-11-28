package com.yzb.card.king.ui.app.base;

import android.widget.TextView;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/21 10:38
 * 描述：
 */
public abstract class BaseValidMobileActivity extends BaseValidActivity {
    @Override
    protected void setStepText(TextView tvStep1, TextView tvStep2, TextView tvStep3) {
        tvStep1.setText("验证身份");
        tvStep2.setText("设置新的绑定手机");
        tvStep3.setText("绑定成功");
    }

}
