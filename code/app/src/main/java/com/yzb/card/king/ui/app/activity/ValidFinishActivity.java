package com.yzb.card.king.ui.app.activity;

import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.base.BaseValidIdActivity;
import com.yzb.card.king.ui.app.manager.ValidManager;
import com.yzb.card.king.ui.my.holder.IdentifyComplete;

public class ValidFinishActivity extends BaseValidIdActivity
{
    @Override
    protected String setMidTitle()
    {
        return getString(R.string.setting_verify_finished);
    }

    protected String getButtonText()
    {
        return getString(R.string.finish);
    }

    @Override
    protected void initData()
    {
        setStep(3);
    }

    @Override
    protected View getContent()
    {
        IdentifyComplete complete = new IdentifyComplete(this);
        return complete.getView();
    }

    @Override
    protected void onTvButtonClick()
    {
        ValidManager.getValidManager().finishAll();
    }
}
