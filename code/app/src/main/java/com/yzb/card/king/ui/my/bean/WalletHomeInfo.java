package com.yzb.card.king.ui.my.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/12 18:07
 */
public class WalletHomeInfo implements Serializable
{
    String balance;//账户余额
    String preProfit;//今日收益
    float annualRateBalance;//年化利率
    String totalProfit;//累计收益

    public WalletHomeInfo()
    {
    }

    public WalletHomeInfo(String balance, String preProfit, float annualRateBalance, String totalProfit)
    {
        this.balance = balance;
        this.preProfit = preProfit;
        this.annualRateBalance = annualRateBalance;
        this.totalProfit = totalProfit;
    }

    public String getBalance()
    {
        return balance;
    }

    public void setBalance(String balance)
    {
        this.balance = balance;
    }

    public String getPreProfit()
    {
        return preProfit;
    }

    public void setPreProfit(String preProfit)
    {
        this.preProfit = preProfit;
    }

    public float getAnnualRateBalance()
    {
        return annualRateBalance;
    }

    public void setAnnualRateBalance(float annualRateBalance)
    {
        this.annualRateBalance = annualRateBalance;
    }

    public String getTotalProfit()
    {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit)
    {
        this.totalProfit = totalProfit;
    }
}
