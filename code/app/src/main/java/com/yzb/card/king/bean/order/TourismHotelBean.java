package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * Created by Timmy on 16/7/27.
 */
public class TourismHotelBean implements Serializable{
    private long checkInTime;
    private String departureTime;
    private String hotelAddr;
    private String hotelName;
    private String status;

    public long getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(long checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getHotelAddr() {
        return hotelAddr;
    }

    public void setHotelAddr(String hotelAddr) {
        this.hotelAddr = hotelAddr;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
