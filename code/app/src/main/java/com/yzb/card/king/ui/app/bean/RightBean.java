package com.yzb.card.king.ui.app.bean;

import com.yzb.card.king.bean.common.PayMethod;

/**
 * 描述：保存支付顺序时，PayMethod 里的字段太多保存不成功，使用此bean来保存
 * 作者：殷曙光
 * 日期：2017/2/19 17:02
 */

public class RightBean
{
    private String accountType;//支付类型 0银行卡快捷支付 ,1(余额)，2，3，4，5支付账号类型
    private String typeName;//支付名称
    private String sortCode;//银行卡关联码(快捷支付)
    private double limitDay;//当日可用额度(快捷支付)
    private double limitMonth;//当月可用额度(快捷支付)
    private String logo;//银行logo(快捷支付)

    public RightBean(PayMethod payMethod)
    {
        this.accountType = payMethod.getAccountType();
        this.typeName = payMethod.getTypeName();
        this.sortCode = payMethod.getSortCode();
        this.limitDay = payMethod.getLimitDay();
        this.limitMonth = payMethod.getLimitMonth();
        this.logo = payMethod.getLogo();
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getSortCode()
    {
        return sortCode;
    }

    public void setSortCode(String sortCode)
    {
        this.sortCode = sortCode;
    }

    public double getLimitDay()
    {
        return limitDay;
    }

    public void setLimitDay(double limitDay)
    {
        this.limitDay = limitDay;
    }

    public double getLimitMonth()
    {
        return limitMonth;
    }

    public void setLimitMonth(double limitMonth)
    {
        this.limitMonth = limitMonth;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }
}