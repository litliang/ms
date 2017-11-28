package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/30
 */

public class BankContactItem implements Serializable
{
    private String name;
    private String desc;
    private String phone;

    public BankContactItem()
    {
    }

    public BankContactItem(String name, String desc, String phone)
    {
        this.name = name;
        this.desc = desc;
        this.phone = phone;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
}
