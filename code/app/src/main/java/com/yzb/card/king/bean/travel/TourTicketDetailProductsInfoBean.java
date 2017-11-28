package com.yzb.card.king.bean.travel;

import java.io.Serializable;

/**
 * Created by 1 on 2016/11/24.
 */

public class TourTicketDetailProductsInfoBean implements Serializable{
    private String productId;  //产品id
    private String productName;//产品名称
    private String classicSpots;//经典景点描述
    private String lineId;//线路id
    private String lineName;//线路名称int
    private String depDate;//出发日期
    private  int roomCount;//房间数量
    private String flatshareStatus;//是否拼房
    private String productNum; //购买份数

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getClassicSpots() {
        return classicSpots;
    }

    public void setClassicSpots(String classicSpots) {
        this.classicSpots = classicSpots;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public String getFlatshareStatus() {
        return flatshareStatus;
    }

    public void setFlatshareStatus(String flatshareStatus) {
        this.flatshareStatus = flatshareStatus;
    }
}
