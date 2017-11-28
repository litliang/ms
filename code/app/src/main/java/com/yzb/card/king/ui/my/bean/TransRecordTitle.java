package com.yzb.card.king.ui.my.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/27 15:04
 */
public class TransRecordTitle implements Serializable
{
    private String monthDesc;

    private float payAmount;

    private float incomeAmount;

    private List<TransRecordChild> detailList;

    public String getMonthDesc()
    {
        return monthDesc;
    }

    public void setMonthDesc(String monthDesc)
    {
        this.monthDesc = monthDesc;
    }

    public float getPayAmount()
    {
        return payAmount;
    }

    public void setPayAmount(float payAmount)
    {
        this.payAmount = payAmount;
    }

    public float getIncomeAmount()
    {
        return incomeAmount;
    }

    public void setIncomeAmount(float incomeAmount)
    {
        this.incomeAmount = incomeAmount;
    }

    public List<TransRecordChild> getDetailList()
    {
        return detailList;
    }

    public void setDetailList(List<TransRecordChild> detailList)
    {
        this.detailList = detailList;
    }
}
