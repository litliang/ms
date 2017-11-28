package com.yzb.card.king.bean.food;

import java.io.Serializable;

/**
 * 类  名：主题餐厅
 * 作  者：Li Yubing
 * 日  期：2016/7/20
 * 描  述：
 */
public class ThemeDiningRoomBean implements Serializable{


    private String storeId;

    private String hotelName;

    private String storeRemark;

    private String exteriorImage;//外景

    private String hallImage;//大厅

    private String interiorImage;//内景图片

    private String avgPrice;//均价

    private String lng;//经度

    private String lat;//纬度

    private String channelName;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getStoreRemark() {
        return storeRemark;
    }

    public void setStoreRemark(String storeRemark) {
        this.storeRemark = storeRemark;
    }

    public String getExteriorImage() {
        return exteriorImage;
    }

    public void setExteriorImage(String exteriorImage) {
        this.exteriorImage = exteriorImage;
    }

    public String getHallImage() {
        return hallImage;
    }

    public void setHallImage(String hallImage) {
        this.hallImage = hallImage;
    }

    public String getInteriorImage() {
        return interiorImage;
    }

    public void setInteriorImage(String interiorImage) {
        this.interiorImage = interiorImage;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }



    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
