package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.view.View;

import com.yzb.card.king.R;

/**
 * @author gengqiyun
 * @date 2016.6.19
 * 重置新支付密码(第一次密码输入)；
 */
public class ResetNewPayPwdActivity extends SafeVerifyOldPayPwdActivity {

    @Override
    protected void assignViews() {
        super.assignViews();
        setTitleNmae( getString(R.string.setting_reset_pay_pwd));
        tvInputNotice.setText(getString(R.string.setting_safe_verify_new_notice));

        viewOtherPayMethod.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next_step: // 下一步；
                if (isInputRight()) {
                    Intent intent = new Intent(this, ResetNewPayPwdAginInputActivity.class);
                    intent.putExtra("firstPwd", gvPayPwd.getPassWord().trim());
                    startActivity(intent);
                }
                break;
        }
    }

}
