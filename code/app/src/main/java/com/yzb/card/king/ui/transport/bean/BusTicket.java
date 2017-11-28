package com.yzb.card.king.ui.transport.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yinsg on 2016/5/27.
 */
public class BusTicket implements Serializable{
    public String lineId;
    public String busType;
    public String departTime;
    public Date departDate;
    public String departStationName;
    public String reachStationName;
    public String price;
    public String seatNum;
    public String duration;

    public String startCityName;
    public String endCityName;
    public String startCityId;
    public String endCityId;
}
