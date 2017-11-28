package com.yzb.card.king.bean.hotel;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/3
 * 描  述：
 */
public class HotelServiceFacilityBean implements Serializable{


    /**
     * 类别名称
     */
    private String typeName;
    /**
     * 子项名称
     */
    private String itemName;

    /**
     * 子项图片
     */
    private String itemPhoto;


    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;


    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;


    }

    public String getItemPhoto()
    {
        return itemPhoto;
    }

    public void setItemPhoto(String itemPhoto)
    {
        this.itemPhoto = itemPhoto;



    }

}
