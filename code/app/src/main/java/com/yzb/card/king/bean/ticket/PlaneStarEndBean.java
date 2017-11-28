package com.yzb.card.king.bean.ticket;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/9
 * 描  述：
 */
public class PlaneStarEndBean implements Serializable {
    private String FlightNum;
    private String AirlineCode;
    private String Airline;
    private String DepCity;
    private String ArrCity;
    private String DepCode;
    private String ArrCode;
    private String OnTimeRate;
    private String shopLogo;

    public String getShopLogo()
    {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo)
    {
        this.shopLogo = shopLogo;
    }

    public String getSturt()
    {
        return sturt;
    }

    public void setSturt(String sturt)
    {
        this.sturt = sturt;
    }

    private String sturt;

    public String getFlightNum()
    {
        return FlightNum;
    }

    public void setFlightNum(String flightNum)
    {
        FlightNum = flightNum;
    }

    public String getAirlineCode()
    {
        return AirlineCode;
    }

    public void setAirlineCode(String airlineCode)
    {
        AirlineCode = airlineCode;
    }

    public String getAirline()
    {
        return Airline;
    }

    public void setAirline(String airline)
    {
        Airline = airline;
    }

    public String getDepCity()
    {
        return DepCity;
    }

    public void setDepCity(String depCity)
    {
        DepCity = depCity;
    }

    public String getArrCity()
    {
        return ArrCity;
    }

    public void setArrCity(String arrCity)
    {
        ArrCity = arrCity;
    }

    public String getDepCode()
    {
        return DepCode;
    }

    public void setDepCode(String depCode)
    {
        DepCode = depCode;
    }

    public String getArrCode()
    {
        return ArrCode;
    }

    public void setArrCode(String arrCode)
    {
        ArrCode = arrCode;
    }

    public String getOnTimeRate()
    {
        return OnTimeRate;
    }

    public void setOnTimeRate(String onTimeRate)
    {
        OnTimeRate = onTimeRate;
    }

    public String getDepTerminal()
    {
        return DepTerminal;
    }

    public void setDepTerminal(String depTerminal)
    {
        DepTerminal = depTerminal;
    }

    public String getArrTerminal()
    {
        return ArrTerminal;
    }

    public void setArrTerminal(String arrTerminal)
    {
        ArrTerminal = arrTerminal;
    }

    public String getFlightDate()
    {
        return FlightDate;
    }

    public void setFlightDate(String flightDate)
    {
        FlightDate = flightDate;
    }

    public String getPEKDate()
    {
        return PEKDate;
    }

    public void setPEKDate(String PEKDate)
    {
        this.PEKDate = PEKDate;
    }

    public String getDepTime()
    {
        return DepTime;
    }

    public void setDepTime(String depTime)
    {
        DepTime = depTime;
    }

    public String getArrTime()
    {
        return ArrTime;
    }

    public void setArrTime(String arrTime)
    {
        ArrTime = arrTime;
    }

    public String getDexpected()
    {
        return Dexpected;
    }

    public void setDexpected(String dexpected)
    {
        Dexpected = dexpected;
    }

    public String getAexpected()
    {
        return Aexpected;
    }

    public void setAexpected(String aexpected)
    {
        Aexpected = aexpected;
    }

    private String DepTerminal;
    private String ArrTerminal;
    private String FlightDate;
    private String PEKDate;
    private String DepTime;
    private String ArrTime;
    private String Dexpected;
    private String Aexpected;


}
