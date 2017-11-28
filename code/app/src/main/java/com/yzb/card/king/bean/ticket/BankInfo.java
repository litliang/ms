package com.yzb.card.king.bean.ticket;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 描述：银行优惠信息；
 * 作者：殷曙光
 * 日期：2016/10/9 15:36
 */
@Table(name = "Bank")
public class BankInfo implements Serializable
{
    @Column(name = "flightId")
    private String flightId;
    @Column(name = "bankId")
    private String bankId;
    @Column(name = "bankName")
    private String bankName;
    @Column(name = "bankLogoCode")
    private String bankLogoCode;
    @Column(name = "detail")
    private String detail; //银行优惠；
    private int fullAmount;
    private float rate;

    public int getFullAmount()
    {
        return fullAmount;
    }

    public void setFullAmount(int fullAmount)
    {
        this.fullAmount = fullAmount;
    }

    public float getRate()
    {
        return rate;
    }

    public void setRate(float rate)
    {
        this.rate = rate;
    }

    public String getFlightId()
    {
        return flightId;
    }

    public void setFlightId(String flightId)
    {
        this.flightId = flightId;
    }

    public String getBankId()
    {
        return bankId;
    }

    public void setBankId(String bankId)
    {
        this.bankId = bankId;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getDetail()
    {
        return detail;
    }

    public String getBankLogoCode()
    {
        return bankLogoCode;
    }

    public void setBankLogoCode(String bankLogoCode)
    {
        this.bankLogoCode = bankLogoCode;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }
}
