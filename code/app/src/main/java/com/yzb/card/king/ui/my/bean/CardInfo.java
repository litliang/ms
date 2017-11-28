package com.yzb.card.king.ui.my.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/22 17:01
 */
public class CardInfo implements Serializable
{
    String sortNo;//尾号
    String bankName;
    String typeName;//卡类型
    String sortCode;//银行卡关联码(快捷支付)
    String fullNo;//完整卡号
    String archivesPhoto;//仿真卡图片
    float limitDay;//当日可用额度(快捷支付)
    float limitMonth;//当月可用额度(快捷支付)
    String logo;//银行logo(快捷支付)
    String cardType;//1储蓄卡，2信用卡

    boolean selected;

    public String getFullNo()
    {
        return fullNo;
    }

    public void setFullNo(String fullNo)
    {
        this.fullNo = fullNo;
    }

    public String getArchivesPhoto()
    {
        return archivesPhoto;
    }

    public void setArchivesPhoto(String archivesPhoto)
    {
        this.archivesPhoto = archivesPhoto;
    }

    public String getCardType()
    {
        return cardType;
    }

    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    public String getSortNo()
    {
        return sortNo;
    }

    public void setSortNo(String sortNo)
    {
        this.sortNo = sortNo;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getSortCode()
    {
        return sortCode;
    }

    public void setSortCode(String sortCode)
    {
        this.sortCode = sortCode;
    }

    public float getLimitDay()
    {
        return limitDay;
    }

    public void setLimitDay(float limitDay)
    {
        this.limitDay = limitDay;
    }

    public float getLimitMonth()
    {
        return limitMonth;
    }

    public void setLimitMonth(float limitMonth)
    {
        this.limitMonth = limitMonth;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}
