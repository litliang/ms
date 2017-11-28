package com.yzb.card.king.bean.ticket;

import java.io.Serializable;

/**
 * 类名： ReasonForChangeBean
 * 作者： Lei Chao.
 * 日期： 2016-10-12
 * 描述： 退改签原因
 */
public class ReasonForChangeBean implements Serializable
{
    private String content;
    private String applyType; // 退票原因类型
    private int id;
    private boolean isSelect;

    public String getApplyType()
    {
        return applyType;
    }

    public void setApplyType(String applyType)
    {
        this.applyType = applyType;
    }

    public boolean isSelect()
    {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect)
    {
        this.isSelect = isSelect;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}