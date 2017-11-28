package com.yzb.card.king.ui.other.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/8 15:39
 */
public class CalendarDay implements Serializable
{
    Date day;
    String bottomText;
    String rightText;
    String holiday;
    boolean inOneYear;
    boolean selected;

    public CalendarDay()
    {
    }

    public CalendarDay(Date day)
    {
        this.day = day;
    }

    public Date getDay()
    {
        return day;
    }

    public void setDay(Date day)
    {
        this.day = day;
    }

    public String getBottomText()
    {
        return bottomText;
    }

    public void setBottomText(String bottomText)
    {
        this.bottomText = bottomText;
    }

    public String getRightText()
    {
        return rightText;
    }

    public void setRightText(String rightText)
    {
        this.rightText = rightText;
    }

    public String getHoliday()
    {
        return holiday;
    }

    public void setHoliday(String holiday)
    {
        this.holiday = holiday;
    }

    public boolean isInOneYear()
    {
        return inOneYear;
    }

    public void setInOneYear(boolean inOneYear)
    {
        this.inOneYear = inOneYear;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}
