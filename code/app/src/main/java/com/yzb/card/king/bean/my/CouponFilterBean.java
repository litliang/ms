package com.yzb.card.king.bean.my;

import java.io.Serializable;

/**
 * 优惠券过滤器
 *
 * @author gengqiyun
 * @date 2016.12.12
 */
public class CouponFilterBean implements Serializable
{
    private String key;
    private String value;
    private boolean isSelected;

    public CouponFilterBean()
    {
    }

    public CouponFilterBean(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
