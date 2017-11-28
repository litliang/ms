package com.yzb.card.king.ui.gift.bean;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：gengqiyun
 * 日  期：2016/12/22
 * 描  述：未领取的礼品卡；
 */
public class NoRecvCardBean implements Serializable
{
    private String orderId;//	Long	订单idN
    private String typeName;//	String	分类名称	N
    private String blessWord;//	String	祝福语	Y
    private String imageCode;    //String	卡样图	N
    private String issuePerson;//	发放人名称  N

    public String getOrderId()
    {
        return orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
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

    public String getIssuePerson()
    {
        return issuePerson;
    }

    public void setIssuePerson(String issuePerson)
    {
        this.issuePerson = issuePerson;
    }
}
