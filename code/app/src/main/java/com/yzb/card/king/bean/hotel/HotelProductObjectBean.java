package com.yzb.card.king.bean.hotel;

import com.yzb.card.king.bean.common.FavInfoBean;

import java.io.Serializable;

/**
 * 酒店列表 --- 酒店产品对象
 * Created by 玉兵 on 2017/7/31.
 */

public class HotelProductObjectBean implements Serializable {
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
    private String defaultImgUrl;
    /**
     * 品牌类型描述
     */
    private String brandTypeDesc;
    /**
     * 评分
     */
    private double vote;
    /**
     * 距离（m）
     */
    private int dis;
    /**
     * 最新预定说明
     */
    private String lastReserve;
    /**
     * 入住日期最低价
     */
    private int minPrice;
    /**
     * 酒店经度
     */
    private double lng;
    /**
     * 酒店纬度
     */
    private double lat;
    /**
     * 优惠信息
     */
    private FavInfoBean disMap;


    public String getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(String hotelId)
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

    public String getDefaultImgUrl()
    {
        return defaultImgUrl;
    }

    public void setDefaultImgUrl(String defaultImgUrl)
    {
        this.defaultImgUrl = defaultImgUrl;
    }

    public String getBrandTypeDesc()
    {
        return brandTypeDesc;
    }

    public void setBrandTypeDesc(String brandTypeDesc)
    {
        this.brandTypeDesc = brandTypeDesc;
    }

    public double getVote()
    {
        return vote;
    }

    public void setVote(double vote)
    {
        this.vote = vote;
    }

    public int getDis()
    {
        return dis;
    }

    public void setDis(int dis)
    {
        this.dis = dis;
    }

    public String getLastReserve()
    {
        return lastReserve;
    }

    public void setLastReserve(String lastReserve)
    {
        this.lastReserve = lastReserve;
    }

    public int getMinPrice()
    {
        return minPrice;
    }

    public void setMinPrice(int minPrice)
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

    public FavInfoBean getDisMap()
    {
        return disMap;
    }

    public void setDisMap(FavInfoBean disMap)
    {
        this.disMap = disMap;
    }



}
