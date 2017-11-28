package com.yzb.card.king.bean.ticket;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/12 14:38
 */
public class CalendarPrice
{
    private String timeseres;//日期
    private float fareInfor;//票价
    private String flightId;//航班id

    public String getTimeseres()
    {
        return timeseres;
    }

    public void setTimeseres(String timeseres)
    {
        this.timeseres = timeseres;
    }

    public float getFareInfor()
    {
        return fareInfor;
    }

    public void setFareInfor(float fareInfor)
    {
        this.fareInfor = fareInfor;
    }

    public String getFlightId()
    {
        return flightId;
    }

    public void setFlightId(String flightId)
    {
        this.flightId = flightId;
    }
}
