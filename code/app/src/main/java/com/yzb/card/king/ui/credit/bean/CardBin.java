package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/5 14:40
 */
public class CardBin implements Serializable
{

    public static final int CARD_DEBIT = 1;//储蓄卡

    public static final int CARD_CREDIT = 2;//信用卡

    //识别码
    private String bin;
    //卡种名称
    private String archivesName;
    //卡种背景图
    private String archivesPhoto;
    //银行名称
    private String bankName;
    //银行logo
    private String bankLogo;

    private Long bankId;

    private String type;//1储蓄卡 2信用卡

    private String bankCode;//判断是否调用"银行卡签约鉴权接口(发短信)",值为 spdb 则调用，其他值则不掉用

    private String bankMark;//用于提现

    public String getBankMark()
    {
        return bankMark;
    }

    public void setBankMark(String bankMark)
    {
        this.bankMark = bankMark;
    }

    public String getBin()
    {
        return bin;
    }

    public void setBin(String bin)
    {
        this.bin = bin;
    }

    public String getArchivesName()
    {
        return archivesName;
    }

    public void setArchivesName(String archivesName)
    {
        this.archivesName = archivesName;
    }

    public String getArchivesPhoto()
    {
        return archivesPhoto;
    }

    public void setArchivesPhoto(String archivesPhoto)
    {
        this.archivesPhoto = archivesPhoto;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getBankLogo()
    {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo)
    {
        this.bankLogo = bankLogo;
    }

    public Long getBankId()
    {
        return bankId;
    }

    public void setBankId(Long bankId)
    {
        this.bankId = bankId;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getBankCode()
    {
        return bankCode;
    }

    public void setBankCode(String bankCode)
    {
        this.bankCode = bankCode;
    }
}
