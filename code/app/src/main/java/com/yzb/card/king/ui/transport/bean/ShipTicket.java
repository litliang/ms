package com.yzb.card.king.ui.transport.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yinsg on 2016/5/30.
 */
public class ShipTicket implements Serializable {

    public String voyageId;
    public String startTime;
    public String endTime;
    public String startShipName;//出发站名
    public String endShipName;//终点站名
    public String shipSeatType;
    public String shipName;
    public int price;
    public int allowance;//余票数
    public String timeLength;
    public float accidentInsurance;//意外保险
    
    public Date startDate;
    public String startCityName;
    public String endCityName;
}
