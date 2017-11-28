package com.yzb.card.king.ui.credit.bean;

import com.yzb.card.king.ui.credit.interfaces.IOnlineCardPop;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/21
 */
public class Bank implements Serializable,IOnlineCardPop
{
    private Long bankId;
    private String bankName;
    private String bankLogo;
    private boolean status;
    private String bankMark;//用于提现

    public String getBankMark()
    {
        return bankMark;
    }

    public void setBankMark(String bankMark)
    {
        this.bankMark = bankMark;
    }

    private String bankDescript;

    private String tel;

    public Bank()
    {
    }

    public Bank(Long bankId, String bankName)
    {
        this.bankId = bankId;
        this.bankName = bankName;
    }

    public String getBankDescript()
    {
        return bankDescript;
    }

    public void setBankDescript(String bankDescript)
    {
        this.bankDescript = bankDescript;
    }

    public Long getBankId()
    {
        return bankId;
    }

    public void setBankId(Long bankId)
    {
        this.bankId = bankId;
    }

    public String getBankLogo()
    {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo)
    {
        this.bankLogo = bankLogo;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    @Override
    public String name()
    {
        return bankName;
    }

    @Override
    public Long id()
    {
        return bankId;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }
}
