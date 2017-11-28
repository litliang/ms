package com.yzb.card.king.bean;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/19
 * 描  述：月份信息
 */
public class MonthBean {

    private String monthStr;

    private int monthNumber;

    private boolean ifSelector = false;

    public int getMonthNumber()
    {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber)
    {
        this.monthNumber = monthNumber;
    }

    public String getMonthStr()
    {
        return monthStr;
    }

    public void setMonthStr(String monthStr)
    {
        this.monthStr = monthStr;
    }

    public boolean isIfSelector()
    {
        return ifSelector;
    }

    public void setIfSelector(boolean ifSelector)
    {
        this.ifSelector = ifSelector;
    }
}
