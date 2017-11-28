package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/22
 */

public class BankContacts implements Serializable
{
    private String bankName;
    //客服热线
    private String telServiceLine;
    //人工服务
    private String telServicePeople;
    //紧急挂失/人工
    private String telServiceLost;
    //
    private String mobile;

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getTelServiceLine()
    {
        return telServiceLine;
    }

    public void setTelServiceLine(String telServiceLine)
    {
        this.telServiceLine = telServiceLine;
    }

    public String getTelServicePeople()
    {
        return telServicePeople;
    }

    public void setTelServicePeople(String telServicePeople)
    {
        this.telServicePeople = telServicePeople;
    }

    public String getTelServiceLost()
    {
        return telServiceLost;
    }

    public void setTelServiceLost(String telServiceLost)
    {
        this.telServiceLost = telServiceLost;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
}
