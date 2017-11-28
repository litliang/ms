package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/22
 */

public class AdjustCard implements Serializable
{
    private String cardPhoto;

    //额度查询短信
    private String msgLimitQuery;
    private String msgQueryAddress;

    //额度调整短信
    private String msgLimitAdjust;
    private String msgAdjustAddress;
    //额度查询电话
    private String phoneLimitQuery;

    //额度调整电话
    private String phoneLimitAdjust;

    //1短信查询2.电话查询
    private String msgStatus ;

    //1短信调整2.电话调整
    private String adjustStatus;

    public String getMsgQueryAddress()
    {
        return msgQueryAddress;
    }

    public void setMsgQueryAddress(String msgQueryAddress)
    {
        this.msgQueryAddress = msgQueryAddress;
    }

    public String getMsgAdjustAddress()
    {
        return msgAdjustAddress;
    }

    public void setMsgAdjustAddress(String msgAdjustAddress)
    {
        this.msgAdjustAddress = msgAdjustAddress;
    }

    public String getCardPhoto()
    {
        return cardPhoto;
    }

    public void setCardPhoto(String cardPhoto)
    {
        this.cardPhoto = cardPhoto;
    }

    public String getMsgLimitQuery()
    {
        return msgLimitQuery;
    }

    public void setMsgLimitQuery(String msgLimitQuery)
    {
        this.msgLimitQuery = msgLimitQuery;
    }

    public String getMsgLimitAdjust()
    {
        return msgLimitAdjust;
    }

    public void setMsgLimitAdjust(String msgLimitAdjust)
    {
        this.msgLimitAdjust = msgLimitAdjust;
    }

    public String getPhoneLimitQuery()
    {
        return phoneLimitQuery;
    }

    public void setPhoneLimitQuery(String phoneLimitQuery)
    {
        this.phoneLimitQuery = phoneLimitQuery;
    }

    public String getPhoneLimitAdjust()
    {
        return phoneLimitAdjust;
    }

    public void setPhoneLimitAdjust(String phoneLimitAdjust)
    {
        this.phoneLimitAdjust = phoneLimitAdjust;
    }

    public String getMsgStatus()
    {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus)
    {
        this.msgStatus = msgStatus;
    }

    public String getAdjustStatus()
    {
        return adjustStatus;
    }

    public void setAdjustStatus(String adjustStatus)
    {
        this.adjustStatus = adjustStatus;
    }
}
