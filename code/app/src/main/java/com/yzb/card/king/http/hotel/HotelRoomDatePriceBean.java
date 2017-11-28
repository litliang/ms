package com.yzb.card.king.http.hotel;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/14
 * 描  述：
 */
public class HotelRoomDatePriceBean implements Serializable {

    private  long priceId;

    private String roomsDate;

    private int price;

    private int residualQuantity;
    /**
     * 是否最低价(1最低价、0非最低价)
     */
    private int minPriceStatus;
    /**
     * 保留时间描述
     */
    private String retentionTimeDesc;
    /**
     * 担保金额
     */
    private float guaranteeAmount;

    /**
     * 担保时间描述
     *
     */
    private String guaranteeTimeDesc;

    public String getGuaranteeTimeDesc()
    {
        return guaranteeTimeDesc;
    }

    public void setGuaranteeTimeDesc(String guaranteeTimeDesc)
    {
        this.guaranteeTimeDesc = guaranteeTimeDesc;
    }

    public long getPriceId()
    {
        return priceId;
    }

    public void setPriceId(long priceId)
    {
        this.priceId = priceId;
    }

    public String getRoomsDate()
    {
        return roomsDate;
    }

    public void setRoomsDate(String roomsDate)
    {
        this.roomsDate = roomsDate;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public int getResidualQuantity()
    {
        return residualQuantity;
    }

    public void setResidualQuantity(int residualQuantity)
    {
        this.residualQuantity = residualQuantity;
    }

    public int getMinPriceStatus()
    {
        return minPriceStatus;
    }

    public void setMinPriceStatus(int minPriceStatus)
    {
        this.minPriceStatus = minPriceStatus;
    }

    public String getRetentionTimeDesc()
    {
        return retentionTimeDesc;
    }

    public void setRetentionTimeDesc(String retentionTimeDesc)
    {
        this.retentionTimeDesc = retentionTimeDesc;
    }

    public float getGuaranteeAmount()
    {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(float guaranteeAmount)
    {
        this.guaranteeAmount = guaranteeAmount;
    }
}
