package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.my.holder.BankCardHolder;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/28 17:29
 */
public class SelectPayWayActivity extends SelectCardActivity
{

    @NonNull
    @Override
    protected String getFooterText()
    {
        return "使用新卡付款";
    }

    @NonNull
    @Override
    protected String getTitleStr()
    {
        return "选择付款方式";
    }

    @Override
    protected Boolean showCredit()
    {
        return false;
    }

    @Override
    protected void addNewCard()
    {
        startActivity(new Intent(this, AddBankCardActivity.class));

    }

    @Override
    protected BankCardHolder getAdapterHolder()
    {
        return new BankCardHolder();
    }
}
