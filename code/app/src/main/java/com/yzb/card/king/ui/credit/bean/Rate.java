package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/30
 */
public class Rate implements Serializable
{
    private long rateId;
    private float rate;

    public long getRateId()
    {
        return rateId;
    }

    public void setRateId(long rateId)
    {
        this.rateId = rateId;
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
