package com.yzb.card.king.bean;

import java.io.Serializable;

/**
 * 类  名：酒店旅游列表获取分类的参数实体类
 * 作  者：Li JianQiang
 * 日  期：2016/8/31
 * 描  述：
 */
public class HotelTravelFlParamBean implements Serializable {

    //城市id
    private String cityId;
    //经度
    private String lat;
    //纬度
    private String lng;
    //距离
    private String distance;

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getLat()
    {
        return lat;
    }

    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public String getLng()
    {
        return lng;
    }

    public void setLng(String lng)
    {
        this.lng = lng;
    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public String getBankId()
    {
        return bankId;
    }

    public void setBankId(String bankId)
    {
        this.bankId = bankId;
    }

    public String getRegionId()
    {
        return regionId;
    }

    public void setRegionId(String regionId)
    {
        this.regionId = regionId;
    }

    public String getCircleId()
    {
        return circleId;
    }

    public void setCircleId(String circleId)
    {
        this.circleId = circleId;
    }

    public String getSpotId()
    {
        return spotId;
    }

    public void setSpotId(String spotId)
    {
        this.spotId = spotId;
    }

    //银行
    private String bankId;
    //区id
    private String regionId;
    //商圈id
    private String circleId;
    //热门地标id
    private String spotId;
}
