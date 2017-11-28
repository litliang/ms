package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * 功能：特惠付款->积分明细bean；
 *
 * @author:gengqiyun
 * @date: 2016/8/21
 */
public class DiscountIntegralDetailBean implements Serializable
{
    private String id;
    private int bankId; //银行卡id：
    private String accountType; //账户类型；
    private float exchangeIntegralNum; //已兑换积分数量；
    private float exchangeMoneyNum; //已兑换现金数量；
    private String bankName; //银行卡名称；

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public float getExchangeIntegralNum()
    {
        return exchangeIntegralNum;
    }

    public void setExchangeIntegralNum(float exchangeIntegralNum)
    {
        this.exchangeIntegralNum = exchangeIntegralNum;
    }

    public int getBankId()
    {
        return bankId;
    }

    public void setBankId(int bankId)
    {
        this.bankId = bankId;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public float getExchangeMoneyNum()
    {
        return exchangeMoneyNum;
    }

    public void setExchangeMoneyNum(float exchangeMoneyNum)
    {
        this.exchangeMoneyNum = exchangeMoneyNum;
    }
}
