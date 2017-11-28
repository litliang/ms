package com.yzb.card.king.bean.ticket;

import android.content.res.ColorStateList;
import android.view.View;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/28 15:18
 */
public abstract class AbsFilter
{
    private String name;
    private int pic;
    private ColorStateList textColor;

    public AbsFilter(String name, int pic,ColorStateList textColor)
    {
        this.name = name;
        this.pic = pic;
        this.textColor = textColor;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPic()
    {
        return pic;
    }

    public void setPic(int pic)
    {
        this.pic = pic;
    }

    public abstract void clickAction(View view);

    public ColorStateList getTextColor()
    {
        return textColor;
    }

    public void setTextColor(ColorStateList textColor)
    {
        this.textColor = textColor;
    }
}
