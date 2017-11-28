package com.yzb.card.king.bean.ticket;

import com.yzb.card.king.ui.app.bean.BaseBean;

/**
 * 功能：机票订单-->航班信息bean；
 *
 * @author:gengqiyun
 * @date: 2016/10/11
 */
public class OrderFlightBean extends BaseBean
{
    private String flightId;//航班id
    private String agentId; //代理商id
    private String state = "0"; //0：正常 1： 改升   2：退票   3：改签
    private String passengerType; //乘机人类型
    private String seatNo; //座位号id，默认为空

    private String passengerId; //乘机人id
    private String packages; //套餐类型（可以包含多个  ，使用逗号分隔）
    private String ticketPriceId; //票价id
    private String fareInfor; //票价；
    private String fueltax; //机建燃油费；
    private String routeType;//航班类型；

    public String getRouteType()
    {
        return routeType;
    }

    public void setRouteType(String routeType)
    {
        this.routeType = routeType;
    }

    public String getFareInfor()
    {
        return super.isStrEmpty(fareInfor);
    }

    public void setFareInfor(String fareInfor)
    {
        this.fareInfor = fareInfor;
    }

    public String getFueltax()
    {
        return super.isStrEmpty(fueltax);
    }

    public void setFueltax(String fueltax)
    {
        this.fueltax = fueltax;
    }

    public String getFlightId()
    {
        return super.isStrEmpty(flightId);
    }

    public void setFlightId(String flightId)
    {
        this.flightId = flightId;
    }

    public String getAgentId()
    {
        return super.isStrEmpty(agentId);
    }

    public void setAgentId(String agentId)
    {
        this.agentId = agentId;
    }

    public String getState()
    {
        return super.isStrEmpty(state);
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getPassengerType()
    {
        return super.isStrEmpty(passengerType);
    }

    public void setPassengerType(String passengerType)
    {
        this.passengerType = passengerType;
    }

    public String getSeatNo()
    {
        return super.isStrEmpty(seatNo);
    }

    public void setSeatNo(String seatNo)
    {
        this.seatNo = seatNo;
    }

    public String getPassengerId()
    {
        return super.isStrEmpty(passengerId);
    }

    public void setPassengerId(String passengerId)
    {
        this.passengerId = passengerId;
    }

    public String getPackages()
    {
        return super.isStrEmpty(packages);
    }

    public void setPackages(String packages)
    {
        this.packages = packages;
    }

    public String getTicketPriceId()
    {
        return super.isStrEmpty(ticketPriceId);
    }

    public void setTicketPriceId(String ticketPriceId)
    {
        this.ticketPriceId = ticketPriceId;
    }

}
