package com.yzb.card.king.bean.hotel;

import com.yzb.card.king.ui.app.bean.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/19
 * 描  述：
 */
public class RoomBean extends BaseBean implements Serializable
{
    private Hotel hotel;
    private String areaDesc;
    private String roomsName;
    private long roomsId;
    private String bedTypeDesc;
    private String photoUrls;
    private int minPrice;
    private String breakfastDesc;
    private String floorDesc;
    private String smokeDesc;
    private String wifiDesc;
    private String addBedDesc;
    private String bedWidth;
    private boolean scheduleAble;
    private Date dateStart;
    private Date dateEnd;
    private String windowDesc;

    public String getWindowDesc()
    {
        return windowDesc;
    }

    public void setWindowDesc(String windowDesc)
    {
        this.windowDesc = windowDesc;
    }

    public Date getDateStart()
    {
        return dateStart;
    }

    public void setDateStart(Date dateStart)
    {
        this.dateStart = dateStart;
    }

    public Date getDateEnd()
    {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd)
    {
        this.dateEnd = dateEnd;
    }

    public Hotel getHotel()
    {
        return hotel;
    }

    public void setHotel(Hotel hotel)
    {
        this.hotel = hotel;
    }

    public String getAreaDesc()
    {
        return super.isStrEmpty(areaDesc);
    }

    public void setAreaDesc(String areaDesc)
    {
        this.areaDesc = areaDesc;
    }

    public String getRoomsName()
    {
        return super.isStrEmpty(roomsName);
    }

    public void setRoomsName(String roomsName)
    {
        this.roomsName = roomsName;
    }

    public long getRoomsId()
    {
        return roomsId;
    }

    public void setRoomsId(long roomsId)
    {
        this.roomsId = roomsId;
    }

    public String getBedTypeDesc()
    {
        return super.isStrEmpty(bedTypeDesc);
    }

    public void setBedTypeDesc(String bedTypeDesc)
    {
        this.bedTypeDesc = bedTypeDesc;
    }

    public String getPhotoUrls()
    {
        return super.isStrEmpty(photoUrls);
    }

    public void setPhotoUrls(String photoUrls)
    {
        this.photoUrls = photoUrls;
    }

    public int getMinPrice()
    {
        return minPrice;
    }

    public void setMinPrice(int minPrice)
    {
        this.minPrice = minPrice;
    }

    public String getBreakfastDesc()
    {
        return super.isStrEmpty(breakfastDesc);
    }

    public void setBreakfastDesc(String breakfastDesc)
    {
        this.breakfastDesc = breakfastDesc;
    }

    public String getFloorDesc()
    {
        return super.isStrEmpty(floorDesc);
    }

    public void setFloorDesc(String floorDesc)
    {
        this.floorDesc = floorDesc;
    }

    public String getSmokeDesc()
    {
        return super.isStrEmpty(smokeDesc);
    }

    public void setSmokeDesc(String smokeDesc)
    {
        this.smokeDesc = smokeDesc;
    }

    public String getWifiDesc()
    {
        return super.isStrEmpty(wifiDesc);
    }

    public void setWifiDesc(String wifiDesc)
    {
        this.wifiDesc = wifiDesc;
    }

    public String getAddBedDesc()
    {
        return super.isStrEmpty(addBedDesc);
    }

    public void setAddBedDesc(String addBedDesc)
    {
        this.addBedDesc = addBedDesc;
    }

    public String getBedWidth()
    {
        return bedWidth;
    }

    public void setBedWidth(String bedWidth)
    {
        this.bedWidth = bedWidth;
    }

    public boolean isScheduleAble()
    {
        return scheduleAble;
    }

    public void setScheduleAble(boolean scheduleAble)
    {
        this.scheduleAble = scheduleAble;
    }
}
