package com.yzb.card.king.ui.gift.bean;

/**
 * 功能：礼品卡分类
 *
 * @author:gengqiyun
 * @date: 2016/12/28
 */
public class GiftcardTypeBean
{
    private String typeId; //Long	分类id	N
    private String typeName;//String	分类名称	N
    private boolean isSelected;

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public String getTypeId()
    {
        return typeId;
    }

    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
}
