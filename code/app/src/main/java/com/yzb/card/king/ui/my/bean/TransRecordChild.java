package com.yzb.card.king.ui.my.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/27 15:04
 */
public class TransRecordChild implements Serializable
{

    private Date tradeTime;//yyyy-MM-dd HH:mm:ss
    private String paymentsStatus;//1收；2支
    private float tradeAmount;
    private String tradeTypeDesc;
    private String tradeDesc;

    public String getPaymentsStatus()
    {
        return paymentsStatus;
    }

    public void setPaymentsStatus(String paymentsStatus)
    {
        this.paymentsStatus = paymentsStatus;
    }

    public Date getTradeTime()
    {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime)
    {
        this.tradeTime = tradeTime;
    }

    public float getTradeAmount()
    {
        return tradeAmount;
    }

    public void setTradeAmount(float tradeAmount)
    {
        this.tradeAmount = tradeAmount;
    }

    public String getTradeTypeDesc()
    {
        return tradeTypeDesc;
    }

    public void setTradeTypeDesc(String tradeTypeDesc)
    {
        this.tradeTypeDesc = tradeTypeDesc;
    }

    public String getTradeDesc()
    {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc)
    {
        this.tradeDesc = tradeDesc;
    }
}
