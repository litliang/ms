package com.yzb.card.king.ui.app.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/19 15:28
 */
public class LevelCondition implements Serializable
{
    Long conditionId;
    String conditionDesc;

    public Long getConditionId()
    {
        return conditionId;
    }

    public void setConditionId(Long conditionId)
    {
        this.conditionId = conditionId;
    }

    public String getConditionDesc()
    {
        return conditionDesc;
    }

    public void setConditionDesc(String conditionDesc)
    {
        this.conditionDesc = conditionDesc;
    }
}
