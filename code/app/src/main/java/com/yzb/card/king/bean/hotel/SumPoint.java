package com.yzb.card.king.bean.hotel;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/3/14 15:07
 */
public class SumPoint implements Serializable
{
    private Long actId;
    private String actName;
    private Integer platformType;//1平台活动，2商家活动

    public Long getActId()
    {
        return actId;
    }

    public void setActId(Long actId)
    {
        this.actId = actId;
    }

    public String getActName()
    {
        return actName;
    }

    public void setActName(String actName)
    {
        this.actName = actName;
    }

    public Integer getPlatformType()
    {
        return platformType;
    }

    public void setPlatformType(Integer platformType)
    {
        this.platformType = platformType;
    }
}
