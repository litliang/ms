package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.view.View;

import com.yzb.card.king.ui.app.base.BaseValidIdActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.holder.IdentifyStart;
import com.yzb.card.king.ui.my.holder.IdentifySuccess;

public class IDAuthenticationActivity extends BaseValidIdActivity
{
    protected String setMidTitle()
    {
        return "身份验证";
    }

    protected String getButtonText()
    {
        return "校验本人银行卡";
    }

    protected View getContent()
    {
        if ("1".equals(getAuthenticationStatus()))
        {
            IdentifySuccess identifySuccess = new IdentifySuccess();
            return identifySuccess.getView();
        } else
        {
            IdentifyStart identifyStart = new IdentifyStart(this);
            return identifyStart.getView();
        }
    }

    private String getAuthenticationStatus()
    {
        return UserManager.getInstance().getUserBean().getAuthenticationStatus();
    }

    protected void initData()
    {
        hideStep();
        setStep(1);
    }

    protected void onTvButtonClick()
    {
        startActivity(new Intent(this, ValidIdentificationActivity.class));
    }

}
