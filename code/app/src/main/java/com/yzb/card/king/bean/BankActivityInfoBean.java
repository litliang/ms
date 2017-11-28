package com.yzb.card.king.bean;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：生活分期 、银行优惠
 */
public class BankActivityInfoBean implements Serializable {

    /**
     * 银行id
     */
    private  long bankId;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行logo
     */
    private String bankLogo;
    /**
     * 活动id
     */
    private String actId;
    /**
     * 活动名称
     */
    private String actName;
    /**
     * 分期描述
     */
    private String stageDesc;
    /**
     * 有效期开始日期
     */
    private String startDate;
    /**
     * 有效期截止日期
     */
    private String endDate;
    /**
     * 优惠类型(1满减；2折扣；3随机立减；)
     */
    private int actCls;
    /**
     * 满额(满减有效)
     */
    private int fuuAmount;
    /**
     * 优惠内容(满减时为减额（单位元），折扣时为折扣率)
     */
    private int disContent;


    public BankActivityInfoBean(){

    }

    public int getActCls()
    {
        return actCls;
    }

    public void setActCls(int actCls)
    {
        this.actCls = actCls;
    }

    public int getFuuAmount()
    {
        return fuuAmount;
    }

    public void setFuuAmount(int fuuAmount)
    {
        this.fuuAmount = fuuAmount;
    }

    public int getDisContent()
    {
        return disContent;
    }

    public void setDisContent(int disContent)
    {
        this.disContent = disContent;
    }

    public long getBankId()
    {
        return bankId;
    }

    public void setBankId(long bankId)
    {
        this.bankId = bankId;
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

    public String getActId()
    {
        return actId;
    }

    public void setActId(String actId)
    {
        this.actId = actId;
    }

    public String getActName()
    {
        return actName;
    }

    public void setActName(String actName)
    {
        this.actName = actName;
    }

    public String getStageDesc()
    {
        return stageDesc;
    }

    public void setStageDesc(String stageDesc)
    {
        this.stageDesc = stageDesc;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    private String tempStr ;

    public BankActivityInfoBean(String tempStr)
    {
        this.tempStr = tempStr;
    }

    public String getTempStr()
    {
        return tempStr;
    }

    public void setTempStr(String tempStr)
    {
        this.tempStr = tempStr;
    }
}
