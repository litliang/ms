package com.yzb.card.king.ui.my.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/27 21:24
 */
public class Payee implements Serializable
{
    private String tradeType;//1平台账户；2储蓄卡
    private long creditId ;//收款人id
    private String creditName;//收款人名称
    private String tradeAccount;//平台账户时为平台账号，储蓄卡时为储蓄卡卡号
    private String photoUrl;
    private String bankName;
    private Long bankId;
    private String bankNo;
    private String bankMark;//用于提现

    public String getBankMark()
    {
        return bankMark;
    }

    public void setBankMark(String bankMark)
    {
        this.bankMark = bankMark;
    }

    public String getTradeType()
    {
        return tradeType;
    }

    public void setTradeType(String tradeType)
    {
        this.tradeType = tradeType;
    }

    public long getCreditId()
    {
        return creditId;
    }

    public void setCreditId(long creditId)
    {
        this.creditId = creditId;
    }

    public String getCreditName()
    {
        return creditName;
    }

    public void setCreditName(String creditName)
    {
        this.creditName = creditName;
    }

    public String getTradeAccount()
    {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount)
    {
        this.tradeAccount = tradeAccount;
    }

    public String getPhotoUrl()
    {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl)
    {
        this.photoUrl = photoUrl;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public Long getBankId()
    {
        return bankId;
    }

    public void setBankId(Long bankId)
    {
        this.bankId = bankId;
    }

    public String getBankNo()
    {
        return bankNo;
    }

    public void setBankNo(String bankNo)
    {
        this.bankNo = bankNo;
    }
}
