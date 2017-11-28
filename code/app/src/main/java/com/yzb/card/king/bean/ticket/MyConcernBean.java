package com.yzb.card.king.bean.ticket;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/28
 * 描  述：
 */
public class MyConcernBean implements Serializable {

    private String flightId;  //航班Id
    private String flightNumber;  //航班号
    private String timeSeres;   //出发日
    private String arrDay;   //到达日
    private String depTime;  //起飞时间
    private String arrTime;     //到达时间
    private String carrierId;    //市场方
    private String depAirPort;  //起始机场信息
    private String arrAirPort;  //到达机场信息
    private String flyingTime;  //飞行耗时
    private String acft;    //机型
    private String isCodeShareairline;  //是否共享航班
    private String isFlightnumber;  //共享航班号
    private String operaCode;     //承运方
    private String airlineInfo; //航空公司信息
    private String storeName;   //航空公司名称
    private String shopLogo;    //航空公司logo
    private String arrivalcity; //出发城市
    private String departureCity;   //到达城市

    public String getPlaneType()
    {
        return planeType;
    }

    public void setPlaneType(String planeType)
    {
        this.planeType = planeType;
    }

    private String planeType;    //飞行动态

    public String getFlightId()
    {
        return flightId;
    }

    public void setFlightId(String flightId)
    {
        this.flightId = flightId;
    }

    public String getFlightNumber()
    {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber)
    {
        this.flightNumber = flightNumber;
    }

    public String getTimeSeres()
    {
        return timeSeres;
    }

    public void setTimeSeres(String timeSeres)
    {
        this.timeSeres = timeSeres;
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

    public String getDepAirPort()
    {
        return depAirPort;
    }

    public void setDepAirPort(String depAirPort)
    {
        this.depAirPort = depAirPort;
    }

    public String getCarrierId()
    {
        return carrierId;
    }

    public void setCarrierId(String carrierId)
    {
        this.carrierId = carrierId;
    }

    public String getArrAirPort()
    {
        return arrAirPort;
    }

    public void setArrAirPort(String arrAirPort)
    {
        this.arrAirPort = arrAirPort;
    }

    public String getFlyingTime()
    {
        return flyingTime;
    }

    public void setFlyingTime(String flyingTime)
    {
        this.flyingTime = flyingTime;
    }

    public String getAcft()
    {
        return acft;
    }

    public void setAcft(String acft)
    {
        this.acft = acft;
    }

    public String getIsCodeShareairline()
    {
        return isCodeShareairline;
    }

    public void setIsCodeShareairline(String isCodeShareairline)
    {
        this.isCodeShareairline = isCodeShareairline;
    }

    public String getIsFlightnumber()
    {
        return isFlightnumber;
    }

    public void setIsFlightnumber(String isFlightnumber)
    {
        this.isFlightnumber = isFlightnumber;
    }

    public String getOperaCode()
    {
        return operaCode;
    }

    public void setOperaCode(String operaCode)
    {
        this.operaCode = operaCode;
    }

    public String getAirlineInfo()
    {
        return airlineInfo;
    }

    public void setAirlineInfo(String airlineInfo)
    {
        this.airlineInfo = airlineInfo;
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

    public String getArrivalcity()
    {
        return arrivalcity;
    }

    public void setArrivalcity(String arrivalcity)
    {
        this.arrivalcity = arrivalcity;
    }

    public String getDepartureCity()
    {
        return departureCity;
    }

    public void setDepartureCity(String departureCity)
    {
        this.departureCity = departureCity;
    }




}
