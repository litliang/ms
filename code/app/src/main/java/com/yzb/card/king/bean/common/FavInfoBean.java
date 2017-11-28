package com.yzb.card.king.bean.common;

import java.io.Serializable;

/**
 * 类  名：优惠信息
 * 作  者：Li Yubing
 * 日  期：2017/9/6
 * 描  述：
 */
public class FavInfoBean implements Serializable{

    /**
     * 银行优惠状态  （1有；0无）
     */
    private String bankStatus;
    /**
     * 银行优惠描述
     */
    private String bankDesc;
    /**
     * 券状态（1有；0无）
     */
    private String ticketStatus;
    /**
     * 限时抢购状态（1有；0无）
     */
    private String flashsaleStatus;
    /**
     * 卡权益状态（1有；0无）
     */
    private String giftsStatus;
    /**
     * 返现状态（1有；0无）
     */
    private String cashbackStatus;
    /**
     * 分期状态（1有；0无）
     */
    private String stageStatus;
    /**
     * 分期状态描述
     */
    private String stageDesc;

    /**
     * 甩房状态（1有；0无）
     */
    private String leftStatus;

    /**
     * 商家积分状态（1有；0无）
     */
    private String shopPointsStatus;
    /**
     * 立减状态（1有；0无）
     */
    private String minusStatus;

    public String getMinusStatus()
    {
        return minusStatus;
    }

    public void setMinusStatus(String minusStatus)
    {
        this.minusStatus = minusStatus;
    }

    public String getBankStatus()
    {
        return bankStatus;
    }

    public void setBankStatus(String bankStatus)
    {
        this.bankStatus = bankStatus;
    }

    public String getTicketStatus()
    {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus)
    {
        this.ticketStatus = ticketStatus;
    }

    public String getFlashsaleStatus()
    {
        return flashsaleStatus;
    }

    public void setFlashsaleStatus(String flashsaleStatus)
    {
        this.flashsaleStatus = flashsaleStatus;
    }

    public String getGiftsStatus()
    {
        return giftsStatus;
    }

    public void setGiftsStatus(String giftsStatus)
    {
        this.giftsStatus = giftsStatus;
    }

    public String getCashbackStatus()
    {
        return cashbackStatus;
    }

    public void setCashbackStatus(String cashbackStatus)
    {
        this.cashbackStatus = cashbackStatus;
    }

    public String getStageStatus()
    {
        return stageStatus;
    }

    public void setStageStatus(String stageStatus)
    {
        this.stageStatus = stageStatus;
    }

    public String getLeftStatus()
    {
        return leftStatus;
    }

    public void setLeftStatus(String leftStatus)
    {
        this.leftStatus = leftStatus;
    }

    public String getShopPointsStatus()
    {
        return shopPointsStatus;
    }

    public void setShopPointsStatus(String shopPointsStatus)
    {
        this.shopPointsStatus = shopPointsStatus;
    }

    public String getBankDesc()
    {
        return bankDesc;
    }

    public void setBankDesc(String bankDesc)
    {
        this.bankDesc = bankDesc;
    }

    public String getStageDesc()
    {
        return stageDesc;
    }

    public void setStageDesc(String stageDesc)
    {
        this.stageDesc = stageDesc;
    }
}
