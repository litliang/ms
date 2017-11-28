package com.yzb.card.king.bean.order;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/16
 * 描  述：
 */
public class HotelOrderServeBean extends BaseOrderServeBean{
    /**
     * 商家id
     */
    private long shopId;
    /**
     * 商品数量
     */
    private int goodsQuantity;
    /**
     * 入住日期(yyyy-MM-dd)
     */
    private String arrDate;
    /**
     * 离店日期(yyyy-MM-dd)
     */
    private String depDate;
    /**
     * 酒店id
     */
    private long hotelId;
    /**
     * 酒店名称
     */
    private String hotelName;
    /**
     * 酒店地址
     */
    private String hotelAddr;
    /**
     * 城市id
     */
    private long cityId;
    /**
     * 房间id
     */
    private long roomsId;
    /**
     * 房间名称
     */
    private String roomsName;
    /**
     * 套餐id
     */
    private long policysId;
    /**
     * 返现金额
     */
    private float cashbackAmount;

    /**
     * 担保金额
     */
    private float guaranteeAmount;


    public long getShopId()
    {
        return shopId;
    }

    public void setShopId(long shopId)
    {
        this.shopId = shopId;
    }

    public int getGoodsQuantity()
    {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity)
    {
        this.goodsQuantity = goodsQuantity;
    }

    public String getArrDate()
    {
        return arrDate;
    }

    public void setArrDate(String arrDate)
    {
        this.arrDate = arrDate;
    }

    public String getDepDate()
    {
        return depDate;
    }

    public void setDepDate(String depDate)
    {
        this.depDate = depDate;
    }

    public long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(long hotelId)
    {
        this.hotelId = hotelId;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
    }

    public String getHotelAddr()
    {
        return hotelAddr;
    }

    public void setHotelAddr(String hotelAddr)
    {
        this.hotelAddr = hotelAddr;
    }

    @Override
    public long getCityId()
    {
        return cityId;
    }

    @Override
    public void setCityId(long cityId)
    {
        this.cityId = cityId;
    }

    public long getRoomsId()
    {
        return roomsId;
    }

    public void setRoomsId(long roomsId)
    {
        this.roomsId = roomsId;
    }

    public String getRoomsName()
    {
        return roomsName;
    }

    public void setRoomsName(String roomsName)
    {
        this.roomsName = roomsName;
    }

    public long getPolicysId()
    {
        return policysId;
    }

    public void setPolicysId(long policysId)
    {
        this.policysId = policysId;
    }

    public float getCashbackAmount()
    {
        return cashbackAmount;
    }

    public void setCashbackAmount(float cashbackAmount)
    {
        this.cashbackAmount = cashbackAmount;
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
