package com.yzb.card.king.bean.hotel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/5 18:27
 */
public class HallBean implements Serializable
{
    private Hotel hotel;
    private long hallId;
    private String photoUrls;
    private String hallName;
    private Float price;
    private String areaDesc;
    //容量描述
    private String capacityDesc;
    private float length;
    private float width;
    private String floorDesc;
    private Date meetingDate;
    private String startTime;
    private String endTime;
    private String pointsDesc;
    private String paymentType;
    private Long shopId;
    private String shopType;//1平台商家；2代理商；
    private boolean couponStatus;
    private boolean bankStatus;
    private List<SumPoint> pointsList;

    public String getShopType()
    {
        return shopType;
    }

    public void setShopType(String shopType)
    {
        this.shopType = shopType;
    }

    public Long getShopId()
    {
        return shopId;
    }

    public void setShopId(Long shopId)
    {
        this.shopId = shopId;
    }

    public String getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    //营业时间（多个使用英文逗号分隔）
    private List<HallLayout> layoutList;

    public Hotel getHotel()
    {
        return hotel;
    }

    public void setHotel(Hotel hotel)
    {
        this.hotel = hotel;
    }

    public long getHallId()
    {
        return hallId;
    }

    public void setHallId(long hallId)
    {
        this.hallId = hallId;
    }

    public String getPhotoUrls()
    {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls)
    {
        this.photoUrls = photoUrls;
    }

    public String getHallName()
    {
        return hallName;
    }

    public void setHallName(String hallName)
    {
        this.hallName = hallName;
    }

    public Float getPrice()
    {
        return price;
    }

    public void setPrice(Float price)
    {
        this.price = price;
    }

    public String getAreaDesc()
    {
        return areaDesc;
    }

    public void setAreaDesc(String areaDesc)
    {
        this.areaDesc = areaDesc;
    }

    public String getCapacityDesc()
    {
        return capacityDesc;
    }

    public void setCapacityDesc(String capacityDesc)
    {
        this.capacityDesc = capacityDesc;
    }

    public float getLength()
    {
        return length;
    }

    public void setLength(float length)
    {
        this.length = length;
    }

    public float getWidth()
    {
        return width;
    }

    public void setWidth(float width)
    {
        this.width = width;
    }

    public String getFloorDesc()
    {
        return floorDesc;
    }

    public void setFloorDesc(String floorDesc)
    {
        this.floorDesc = floorDesc;
    }

    public List<HallLayout> getLayoutList()
    {
        return layoutList;
    }

    public void setLayoutList(List<HallLayout> layoutList)
    {
        this.layoutList = layoutList;
    }

    public Date getMeetingDate()
    {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate)
    {
        this.meetingDate = meetingDate;
    }

    public String  getPointsDesc()
    {
        return pointsDesc;
    }

    public void setPointsDesc(String pointsDesc)
    {
        this.pointsDesc = pointsDesc;
    }

    public void setCouponStatus(boolean couponStatus)
    {
        this.couponStatus = couponStatus;
    }

    public void setBankStatus(boolean bankStatus)
    {
        this.bankStatus = bankStatus;
    }

    public void setPointsList(List<SumPoint> pointsList)
    {
        this.pointsList = pointsList;
    }

    public List<SumPoint> getPointsList()
    {
        return pointsList;

    }

    public boolean isCouponStatus()
    {
        return couponStatus;
    }

    public boolean isBankStatus()
    {
        return bankStatus;
    }
}
