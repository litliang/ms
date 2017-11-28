package com.yzb.card.king.bean.hotel;

import java.io.Serializable;

/**
 * 类  名：酒店标签bean
 * 作  者：Li JianQiang
 * 日  期：2016/10/27
 * 描  述：
 */
public class HotelLabelBean implements Serializable {

    private int labelId;

    private String labelName;

    public String getLabelDesc()
    {
        return labelDesc;
    }

    public void setLabelDesc(String labelDesc)
    {
        this.labelDesc = labelDesc;
    }

    public String getLabelName()
    {
        return labelName;
    }

    public void setLabelName(String labelName)
    {
        this.labelName = labelName;
    }

    public int getLabelId()
    {
        return labelId;
    }

    public void setLabelId(int labelId)
    {
        this.labelId = labelId;
    }

    private String labelDesc;
}
