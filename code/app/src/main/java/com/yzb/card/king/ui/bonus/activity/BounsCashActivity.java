package com.yzb.card.king.ui.bonus.activity;


import com.yzb.card.king.ui.my.activity.CashActivity;
import com.yzb.card.king.ui.my.enu.AccountType;

/**
 * 功能：红包提现；
 *
 * @author:gengqiyun
 * @date: 2017/1/18
 */
public class BounsCashActivity extends CashActivity
{
    @Override
    protected String getAccountType()
    {
        return AccountType.RED_BAG.getValue();
    }

    @Override
    protected String getWithdrawType()
    {
        return "2";
    }


    @Override
    protected String getPayMethod()
    {
        return "4";
    }
}
