package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.base.BaseValidIdActivity;
import com.yzb.card.king.ui.app.holder.ValidIdentificationHolder;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/20 16:53
 * 描述：
 */
public class ValidIdentificationActivity extends BaseValidIdActivity
{
    private View content;
    private ValidIdentificationHolder holder;
    private String cardNo;

    @Override
    protected void initData()
    {
        setStep(1);
        cardNo = getIntent().getStringExtra("cardNo");
    }

    @Override
    protected String setMidTitle()
    {
        return getString(R.string.setting_check_own_identification);
    }

    @Override
    protected String getButtonText()
    {
        return getString(R.string.bt_dailyrent_submit);
    }

    @Override
    protected View getContent()
    {
        holder = new ValidIdentificationHolder(this);
        content = holder.getConvertView();
        return content;
    }

    @Override
    protected void onTvButtonClick()
    {
        startActivity(new Intent(this, ValidBankCardActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        holder.onActivityResult(requestCode, resultCode, data);
    }
}
