package com.yzb.card.king.ui.gift.bean;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：gengqiyun
 * 日  期：2016/12/22
 * 描  述：e卡；
 */
public class ECardBean implements Serializable
{
    private String productId;//	Long	产品id	N
    private String typeName;//	String	分类名称	N
    private String blessWord;//	String	祝福语	Y
    private String imageCode;    //String	卡样图	N
    private int orderQuantity;//	int	订购数量	N

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }


    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getBlessWord()
    {
        return blessWord;
    }

    public void setBlessWord(String blessWord)
    {
        this.blessWord = blessWord;
    }

    public String getImageCode()
    {
        return imageCode;
    }

    public void setImageCode(String imageCode)
    {
        this.imageCode = imageCode;
    }


    public int getOrderQuantity()
    {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity)
    {
        this.orderQuantity = orderQuantity;
    }
}
