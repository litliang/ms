package com.yzb.card.king.bean.giftcard;

import java.io.Serializable;

/**
 * 类  名：礼品套餐增值项
 * 作  者：Li Yubing
 * 日  期：2017/9/11
 * 描  述：
 */
public class GiftsIncrementBean implements Serializable{
    /**
     * 增值项名称
     */
    private String itemName;
    /**
     * 增值项数量
     */
    private int itemQuantity;
    /**
     * 使用场所
     */
    private String userPlace;
    /**
     * 条款和适用条件
     */
    private String useCondition;

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public int getItemQuantity()
    {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity)
    {
        this.itemQuantity = itemQuantity;
    }

    public String getUserPlace()
    {
        return userPlace;
    }

    public void setUserPlace(String userPlace)
    {
        this.userPlace = userPlace;
    }

    public String getUseCondition()
    {
        return useCondition;
    }

    public void setUseCondition(String useCondition)
    {
        this.useCondition = useCondition;
    }
}
