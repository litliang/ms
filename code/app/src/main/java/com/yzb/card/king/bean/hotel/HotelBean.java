package com.yzb.card.king.bean.hotel;

import java.io.Serializable;

/**
 * 类  名：酒店类
 * 作  者：Li Yubing
 * 日  期：2016/7/14
 * 描  述：
 */
public class HotelBean implements Serializable{

    private int type ;

    /**
     * 酒店id
     */
    private String hotelId;
    /**
     * 酒店名称
     */
    private String hotelName;
    /**
     * 酒店默认图片
     */
    private  String defaultImgUrl;
    /**
     * 今日最低价
     */
    private int minPrice;
    /**
     * 距离市中心距离（m）
     */
    private int centerDis;

    public HotelBean(){

    }


    public HotelBean(int type ){
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }


    public String getDefaultImgUrl()
    {
        return defaultImgUrl;
    }

    public void setDefaultImgUrl(String defaultImgUrl)
    {
        this.defaultImgUrl = defaultImgUrl;
    }

    public int getMinPrice()
    {
        return minPrice;
    }

    public void setMinPrice(int minPrice)
    {
        this.minPrice = minPrice;
    }

    public int getCenterDis()
    {
        return centerDis;
    }

    public void setCenterDis(int centerDis)
    {
        this.centerDis = centerDis;
    }

    private String roomName;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }


    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }



}
