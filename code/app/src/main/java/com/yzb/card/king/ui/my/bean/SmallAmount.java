package com.yzb.card.king.ui.my.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/26 20:09
 */
public class SmallAmount implements Serializable
{
    private String value;
    private boolean checked;



    public SmallAmount(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
}
