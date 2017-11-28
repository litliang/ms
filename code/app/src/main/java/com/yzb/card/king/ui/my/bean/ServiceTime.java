package com.yzb.card.king.ui.my.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/17 17:24
 */
public class ServiceTime implements Serializable
{
    String name;
    String startTime;
    String endTime;
    String divider = "-";

    public ServiceTime()
    {
    }

    public ServiceTime(String name, String startTime, String endTime, String divider)
    {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.divider = divider;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getDivider()
    {
        return divider;
    }

    public void setDivider(String divider)
    {
        this.divider = divider;
    }
}
