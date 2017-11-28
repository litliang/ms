package com.yzb.card.king.bean.hotel;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：
 */
public class RoomInfoBean implements Serializable {
    /**
     * 酒店id
     */
    private String hotelId;
    /**
     * 酒店名称
     */
    private String hotelName;
    /**
     * 房间id
     */
    private String  roomsId;
    /**
     * 房间名称
     */
    private String roomsName;
    /**
     * 床型描述
     */
    private String roomsTypeDesc;
    /**
     * 房间图片
     */
    private String photoUrls;
    /**
     * 套餐id
     */
    private String policysId;
    /**
     * 位置描述
     */
    private String positionDesc;
    /**
     * 实付价格
     */
    private  int actualPrice;
    /**
     * 套餐价格
     */
    private  int policysPrice;
    /**
     * 预定开始时间
     */
    private  String startTime;
    /**
     * 剩余数量
     */
    private int residualQuantity;

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

    public String getRoomsId() {
        return roomsId;
    }

    public void setRoomsId(String roomsId) {
        this.roomsId = roomsId;
    }

    public String getRoomsName() {
        return roomsName;
    }

    public void setRoomsName(String roomsName) {
        this.roomsName = roomsName;
    }

    public String getRoomsTypeDesc() {
        return roomsTypeDesc;
    }

    public void setRoomsTypeDesc(String roomsTypeDesc) {
        this.roomsTypeDesc = roomsTypeDesc;
    }

    public String getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls) {
        this.photoUrls = photoUrls;
    }

    public String getPolicysId() {
        return policysId;
    }

    public void setPolicysId(String policysId) {
        this.policysId = policysId;
    }

    public String getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }

    public int getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(int actualPrice) {
        this.actualPrice = actualPrice;
    }

    public int getPolicysPrice() {
        return policysPrice;
    }

    public void setPolicysPrice(int policysPrice) {
        this.policysPrice = policysPrice;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getResidualQuantity() {
        return residualQuantity;
    }

    public void setResidualQuantity(int residualQuantity) {
        this.residualQuantity = residualQuantity;
    }
}
