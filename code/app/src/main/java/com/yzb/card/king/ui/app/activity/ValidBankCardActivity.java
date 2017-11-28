package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.view.View;

import com.yzb.card.king.ui.app.base.BaseValidIdActivity;
import com.yzb.card.king.ui.my.holder.ValidBankCardHolder;

import java.util.Map;


public class ValidBankCardActivity extends BaseValidIdActivity
{
    private static final int MY_SCAN_REQUEST_CODE = 0;
    private ValidBankCardHolder holder;


    @Override
    protected View getContent()
    {
        Map<String,String> param = (Map<String, String>) getIntent().getSerializableExtra("param");
        holder = new ValidBankCardHolder(this);
        holder.setData(param);
        return holder.getView();
    }


    @Override
    protected void onTvButtonClick()
    {
    }


    @Override
    protected void initData()
    {
        setStep(2);
    }

    @Override
    protected String setMidTitle()
    {
        return "校验本人银行卡";
    }

    @Override
    protected String getButtonText()
    {
        return "确认";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
     holder.onActivityResult(requestCode,resultCode,data);
        switch (requestCode)
        {
            case MY_SCAN_REQUEST_CODE:
                setCardNum(data);
                break;
        }
    }

    private void setCardNum(Intent data)
    {

    }

}
