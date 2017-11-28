package com.yzb.card.king.bean.hotel;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/4
 * 描  述：
 */
public class HotelRoomParam {
    /**
     * 酒店id
     */
    private   long hotelId;
    /**
     * 房间id
     */
    private  long roomsId;
    /**
     * 入住日期  yyyy-MM-dd
     */
    private String arrDate;
    /**
     * 离店日期  yyyy-MM-dd
     */
    private String depDate;
    /**
     * 是否直销   1是；
     */
    public  int directStatus=-1;
    /**
     * 付款方式 1到店付、2在线支付、3担保支付
     */
    public int paymentType=-1;

    /**
     * 用餐类型 0不含早、1单早、2双早
     */

    public int mealType=-1;
    /**
     * 1单床、2双床、3大床、4圆床、5水床、6单床/双床、7大床/单床
     */
    public int roomsType=-1;
    /**
     * 酒店商品类型 2餐厅、3酒吧、5SPA、6大堂吧、9健身房、10游泳池
     */
    public int goodsType;
    /**
     *
     */
    public String goodsId;

    public HotelRoomParam(long hotelId, String arrDate, String depDate)
    {
        this.hotelId = hotelId;
        this.arrDate = arrDate;
        this.depDate = depDate;
    }

    public HotelRoomParam(long hotelId, String arrDate, int goodsType)
    {
        this.hotelId = hotelId;
        this.arrDate = arrDate;
        this.goodsType = goodsType;
    }

    public HotelRoomParam(long roomsId, String arrDate)
    {
        this.roomsId = roomsId;
        this.arrDate = arrDate;
    }

    public HotelRoomParam(String arrDate, int goodsType, String goodsId)
    {
        this.arrDate = arrDate;
        this.goodsType = goodsType;
        this.goodsId = goodsId;
    }

    public String getGoodsId()
    {
        return goodsId;
    }

    public void setGoodsId(String goodsId)
    {
        this.goodsId = goodsId;
    }

    public void setHotelId(long hotelId)
    {
        this.hotelId = hotelId;
    }

    public void setArrDate(String arrDate)
    {
        this.arrDate = arrDate;
    }

    public void setDepDate(String depDate)
    {
        this.depDate = depDate;
    }

    public long getHotelId()
    {
        return hotelId;
    }

    public String getArrDate()
    {
        return arrDate;
    }

    public String getDepDate()
    {
        return depDate;
    }

    public long getRoomsId()
    {
        return roomsId;
    }

    public void setRoomsId(long roomsId)
    {
        this.roomsId = roomsId;
    }

    public int getGoodsType()
    {
        return goodsType;
    }

    public int getDirectStatus()
    {
        return directStatus;
    }

    public void setDirectStatus(int directStatus)
    {
        this.directStatus = directStatus;
    }

    public int getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(int paymentType)
    {
        this.paymentType = paymentType;
    }

    public int getMealType()
    {
        return mealType;
    }

    public void setMealType(int mealType)
    {
        this.mealType = mealType;
    }

    public int getRoomsType()
    {
        return roomsType;
    }

    public void setRoomsType(int roomsType)
    {
        this.roomsType = roomsType;
    }

    public void setGoodsType(int goodsType)
    {
        this.goodsType = goodsType;
    }
}
