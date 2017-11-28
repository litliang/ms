package com.yzb.card.king.bean.hotel;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/20
 * 描  述：促销类型bean
 */
public class PromotionTypeBean {

    private String typeName;

    private int typeValue;

    private boolean selectedFlag = false;

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public int getTypeValue()
    {
        return typeValue;
    }

    public void setTypeValue(int typeValue)
    {
        this.typeValue = typeValue;
    }

    public boolean isSelectedFlag()
    {
        return selectedFlag;
    }

    public void setSelectedFlag(boolean selectedFlag)
    {
        this.selectedFlag = selectedFlag;
    }
}
