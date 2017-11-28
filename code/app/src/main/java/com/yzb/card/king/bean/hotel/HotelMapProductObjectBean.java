package com.yzb.card.king.bean.hotel;

import java.io.Serializable;

/**
 * 类  名：酒店地图产品类
 * 作  者：Li Yubing
 * 日  期：2017/9/4
 * 描  述：
 */
public class HotelMapProductObjectBean implements Serializable{

    private long hotelId;

    private String hotelName;

    private double vote;

    private String minPrice;

    private double lng;

    private double lat;

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

    public double getVote()
    {
        return vote;
    }

    public void setVote(double vote)
    {
        this.vote = vote;
    }

    public String getMinPrice()
    {
        return minPrice;
    }

    public void setMinPrice(String minPrice)
    {
        this.minPrice = minPrice;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }
}
