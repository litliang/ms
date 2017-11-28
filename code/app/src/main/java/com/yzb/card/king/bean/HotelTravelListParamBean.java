package com.yzb.card.king.bean;

import java.io.Serializable;

/**
 * 类  名：酒店旅游列表参数实体类
 * 作  者：Li JianQiang
 * 日  期：2016/8/31
 * 描  述：
 */
public class HotelTravelListParamBean implements Serializable
{

    //城市id
    private String cityId;
    //数据来源
    private String source;
    //分页参数
    private int pageStart;
    //主页请求
    private String leavelId;
    //起始价格
    private String bgnPrice;
    //结束价格
    private String endPrice;
    //经度
    private String lng;
    //纬度
    private String lat;
    //银行id
    private String bankId;
    //距离
    private String distance;
    //区id
    private String regionId;
    //商圈id
    private String circleId;
    //热门地标id
    private String spotId;
    //分类id
    private String typeId;
    //父分类id
    private String typeParentId;
    //筛选
    private String filterType;
    //排序
    private String sort;

    public String getShopId()
    {
        return shopId;
    }

    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }

    private String shopId;

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public int getPageStart()
    {
        return pageStart;
    }

    public void setPageStart(int pageStart)
    {
        this.pageStart = pageStart;
    }

    public String getLeavelId()
    {
        return leavelId;
    }

    public void setLeavelId(String leavelId)
    {
        this.leavelId = leavelId;
    }

    public String getBgnPrice()
    {
        return bgnPrice;
    }

    public void setBgnPrice(String bgnPrice)
    {
        this.bgnPrice = bgnPrice;
    }

    public String getEndPrice()
    {
        return endPrice;
    }

    public void setEndPrice(String endPrice)
    {
        this.endPrice = endPrice;
    }

    public String getLng()
    {
        return lng;
    }

    public void setLng(String lng)
    {
        this.lng = lng;
    }

    public String getLat()
    {
        return lat;
    }

    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public String getBankId()
    {
        return bankId;
    }

    public void setBankId(String bankId)
    {
        this.bankId = bankId;
    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
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

    public String getTypeId()
    {
        return typeId;
    }

    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }

    public String getTypeParentId()
    {
        return typeParentId;
    }

    public void setTypeParentId(String typeParentId)
    {
        this.typeParentId = typeParentId;
    }

    public String getFilterType()
    {
        return filterType;
    }

    public void setFilterType(String filterType)
    {
        this.filterType = filterType;
    }

    public String getSort()
    {
        return sort;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }
}
