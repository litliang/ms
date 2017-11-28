package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gengqiyun on 2016/4/27.
 * 门店实体；
 */
public class StoreBean implements Serializable {
    public String id;
    public String cityId;
    public String cityName;
    public String regionId;
    public String circleId;
    public String shopId;
    public String shopName;
    public String storeName;
    public String storeAddr;
    public String storeTel;
    public String parentId;
    public String grandParentId;
    public String grandParentName; // 一级分类的名称；
    public String recentEvents;
    public String storePhoto;
    public double lat;
    public double lng;
    public float vote;
    public String avgPrice;
    public String createTime;
    public int collectCount; //收藏数量；
    public String status;
    public String isColletcion; // 是否收藏；
    public int orderCount;
    public int voteCount;
    public int shopCount;

    public String platformPoint; //平台积分赠送比率
    public String shopPoint; //商家积分赠送比率

    public int countCoupon; // 优惠券数量；
    public int countBouns;// 红包数量；

    public String picCodes;
    public List<ActivityList> activityList;
    public String url; //行程详情url
    public String shopIntro; //商家简介F

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
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

    public String getShopId()
    {
        return shopId;
    }

    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public String getStoreAddr()
    {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr)
    {
        this.storeAddr = storeAddr;
    }

    public String getStoreTel()
    {
        return storeTel;
    }

    public void setStoreTel(String storeTel)
    {
        this.storeTel = storeTel;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getGrandParentId()
    {
        return grandParentId;
    }

    public void setGrandParentId(String grandParentId)
    {
        this.grandParentId = grandParentId;
    }

    public String getGrandParentName()
    {
        return grandParentName;
    }

    public void setGrandParentName(String grandParentName)
    {
        this.grandParentName = grandParentName;
    }

    public String getRecentEvents()
    {
        return recentEvents;
    }

    public void setRecentEvents(String recentEvents)
    {
        this.recentEvents = recentEvents;
    }

    public String getStorePhoto()
    {
        return storePhoto;
    }

    public void setStorePhoto(String storePhoto)
    {
        this.storePhoto = storePhoto;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    public float getVote()
    {
        return vote;
    }

    public void setVote(float vote)
    {
        this.vote = vote;
    }

    public String getAvgPrice()
    {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice)
    {
        this.avgPrice = avgPrice;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public int getCollectCount()
    {
        return collectCount;
    }

    public void setCollectCount(int collectCount)
    {
        this.collectCount = collectCount;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getIsColletcion()
    {
        return isColletcion;
    }

    public void setIsColletcion(String isColletcion)
    {
        this.isColletcion = isColletcion;
    }

    public int getOrderCount()
    {
        return orderCount;
    }

    public void setOrderCount(int orderCount)
    {
        this.orderCount = orderCount;
    }

    public int getVoteCount()
    {
        return voteCount;
    }

    public void setVoteCount(int voteCount)
    {
        this.voteCount = voteCount;
    }

    public int getShopCount()
    {
        return shopCount;
    }

    public void setShopCount(int shopCount)
    {
        this.shopCount = shopCount;
    }

    public String getPlatformPoint()
    {
        return platformPoint;
    }

    public void setPlatformPoint(String platformPoint)
    {
        this.platformPoint = platformPoint;
    }

    public String getShopPoint()
    {
        return shopPoint;
    }

    public void setShopPoint(String shopPoint)
    {
        this.shopPoint = shopPoint;
    }

    public int getCountCoupon()
    {
        return countCoupon;
    }

    public void setCountCoupon(int countCoupon)
    {
        this.countCoupon = countCoupon;
    }

    public int getCountBouns()
    {
        return countBouns;
    }

    public void setCountBouns(int countBouns)
    {
        this.countBouns = countBouns;
    }

    public String getPicCodes()
    {
        return picCodes;
    }

    public void setPicCodes(String picCodes)
    {
        this.picCodes = picCodes;
    }

    public List<ActivityList> getActivityList()
    {
        return activityList;
    }

    public void setActivityList(List<ActivityList> activityList)
    {
        this.activityList = activityList;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getShopIntro()
    {
        return shopIntro;
    }

    public void setShopIntro(String shopIntro)
    {
        this.shopIntro = shopIntro;
    }

    public StoreBean() {

    }

    public StoreBean(String id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    public StoreBean(String id, String storeName, double lat, double lng, float vote, String avgPrice) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.vote = vote;
        this.avgPrice = avgPrice;
        this.storeName = storeName;
    }

    public static class ActivityList implements Serializable {
        public String id;
        public int bankId;
        public String detail;
        public String discount;
        public String category;
        public String specialPrice;
        public String otherTitle;
        public String keyWords;
        public String features;
        public String endTime;
        public String createTime;
        public String status;
        public String photo;
        public String fileName;
        public String code;
        public String name;
        public String address;
        public String type;
        public String level;
        public String tel;
        public String bankName;
        public String storeName;
        public String storeAddr;
        public String storeTel;
        public String enjoyNum;
        public String activityBankStore;
    }
}
