package com.yzb.card.king.bean.ticket;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/21
 * 描  述：
 */
public class MyFocusOnBean implements Serializable {

    private String flightId;
    private String flightNumber;

    private String timeSeres;
    private String arrDay;
    private String depTime;
    private String arrTime;

    public String getFlightId()
    {
        return flightId;
    }

    public void setFlightId(String flightId)
    {
        this.flightId = flightId;
    }

    public String getTimeSeres()
    {
        return timeSeres;
    }

    public void setTimeSeres(String timeSeres)
    {
        this.timeSeres = timeSeres;
    }

    public String getFlightNumber()
    {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber)
    {
        this.flightNumber = flightNumber;
    }

    public String getArrDay()
    {
        return arrDay;
    }

    public void setArrDay(String arrDay)
    {
        this.arrDay = arrDay;
    }

    public String getDepTime()
    {
        return depTime;
    }

    public void setDepTime(String depTime)
    {
        this.depTime = depTime;
    }

    public String getArrTime()
    {
        return arrTime;
    }

    public void setArrTime(String arrTime)
    {
        this.arrTime = arrTime;
    }

    public String getArrivalcity()
    {
        return arrivalcity;
    }

    public void setArrivalcity(String arrivalcity)
    {
        this.arrivalcity = arrivalcity;
    }

    public String getAcft()
    {
        return acft;
    }

    public void setAcft(String acft)
    {
        this.acft = acft;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public String getShopLogo()
    {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo)
    {
        this.shopLogo = shopLogo;
    }

    public String getPlanType()
    {
        return planType;
    }

    public void setPlanType(String planType)
    {
        this.planType = planType;
    }

    public String getDepartureCity()
    {
        return departureCity;
    }

    public void setDepartureCity(String departureCity)
    {
        this.departureCity = departureCity;
    }

    private String arrivalcity;
    private String acft;
    private String storeName;
    private String shopLogo;
    private String planType;
    private String departureCity;
}
