package com.yzb.card.king.ui.other.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/8 15:38
 */
public class CalendarMonth implements Serializable
{
    String title;
    List<CalendarDay> dayList;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<CalendarDay> getDayList()
    {
        return dayList;
    }

    public void setDayList(List<CalendarDay> dayList)
    {
        this.dayList = dayList;
    }
}
