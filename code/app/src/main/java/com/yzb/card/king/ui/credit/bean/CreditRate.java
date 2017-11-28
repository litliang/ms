package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/29
 */
public class CreditRate implements Serializable
{
    private long id;
    private long createTime;
    private long bankId;
    //期次
    private int stages;
    //费率
    private float rate;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(long createTime)
    {
        this.createTime = createTime;
    }

    public long getBankId()
    {
        return bankId;
    }

    public void setBankId(long bankId)
    {
        this.bankId = bankId;
    }

    public int getStages()
    {
        return stages;
    }

    public void setStages(int stages)
    {
        this.stages = stages;
    }

    public float getRate()
    {
        return rate;
    }

    public void setRate(float rate)
    {
        this.rate = rate;
    }
}
