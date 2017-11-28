package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * 功能：特惠付款->使用积分bean；
 *
 * @author:gengqiyun
 * @date: 2016/8/21
 */
public class DiscountIntegralBean implements Serializable
{
    private float integralBalance; //积分余额；
    private int integralRate; //积分兑换比例；
    private String bankName; //银行卡名称；
    private int bankId; //银行卡id；
    private boolean isSelected;//是否被选中，只在客户端使用；
    private String accountType; //账户类型；
    private String bankLogo;

    public String getBankLogo()
    {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo)
    {
        this.bankLogo = bankLogo;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public float getIntegralBalance()
    {
        return integralBalance;
    }

    public void setIntegralBalance(float integralBalance)
    {
        this.integralBalance = integralBalance;
    }

    public int getIntegralRate()
    {
        return integralRate;
    }

    public void setIntegralRate(int integralRate)
    {
        this.integralRate = integralRate;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public int getBankId()
    {
        return bankId;
    }

    public void setBankId(int bankId)
    {
        this.bankId = bankId;
    }
}
