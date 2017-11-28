package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * Created by Timmy on 16/7/25.
 */
public class HotelRoomOrderBean implements Serializable {

    /**
     * 房间id
     */
    private long roomsId;
    /**
     * 房间名称
     */
    private String roomsName;

    /**
     * 套餐id
     */
    private String policysId;
    /**
     * 商家id
     */
    private long shopId;
    /**
     * 床型
     */
    private String bedType;
    /**
     * 用餐信息
     */
    private String mealInfo;
    /**
     * 取消方式(1免费取消、2限时取消、3付费取消)
     */
    private String cancelType;

    public String getCancelType()
    {
        return cancelType;
    }

    public void setCancelType(String cancelType)
    {
        this.cancelType = cancelType;
    }

    public long getRoomsId()
    {
        return roomsId;
    }

    public void setRoomsId(long roomsId)
    {
        this.roomsId = roomsId;
    }

    public String getRoomsName()
    {
        return roomsName;
    }

    public void setRoomsName(String roomsName)
    {
        this.roomsName = roomsName;
    }

    public String getPolicysId()
    {
        return policysId;
    }

    public void setPolicysId(String policysId)
    {
        this.policysId = policysId;
    }

    public long getShopId()
    {
        return shopId;
    }

    public void setShopId(long shopId)
    {
        this.shopId = shopId;
    }

    public String getBedType()
    {
        return bedType;
    }

    public void setBedType(String bedType)
    {
        this.bedType = bedType;
    }

    public String getMealInfo()
    {
        return mealInfo;
    }

    public void setMealInfo(String mealInfo)
    {
        this.mealInfo = mealInfo;
    }
}
