package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/28
 */
public class PayWay implements Serializable
{
    //付款类型,0代表储蓄卡 1代表余额账户
    private String accountType;

    //账户名称或银行名称
    private String payName;

    private String logo;

    //根据accountType不同类型区分：(1)、accountType为1时，值为<账户id>   (2)、accountType为0值为<第三方关联编码>
    private String sortCode;

    //accountType为1时可为空
    private String sortNo;

    //根据accountType不同类型区分：(1)、accountType为1时，值为<账户余额>   (2)、accountType为0值为<一日交易限额>
    private float limitAmount;


    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public String getPayName()
    {
        return payName;
    }

    public void setPayName(String payName)
    {
        this.payName = payName;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getSortCode()
    {
        return sortCode;
    }

    public void setSortCode(String sortCode)
    {
        this.sortCode = sortCode;
    }

    public String getSortNo()
    {
        return sortNo;
    }

    public void setSortNo(String sortNo)
    {
        this.sortNo = sortNo;
    }

    public float getLimitAmount()
    {
        return limitAmount;
    }

    public void setLimitAmount(float limitAmount)
    {
        this.limitAmount = limitAmount;
    }
}
