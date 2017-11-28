package com.yzb.card.king.bean.ticket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：机票首页参数
 * 作者：殷曙光
 * 日期：2016/9/29 17:32
 */
public class FlightManager implements Serializable
{
    private List<Flight> flights = new ArrayList<>();
    //当前航线类型
    private int currentLine;

    //用户已经预定的机票数据
    private List<PlaneTicket> tickets = new ArrayList<>();
    //用户当前选择的机票
    private PlaneTicket ticket;
    //成人票数
    private int adultNum;
    //儿童票数
    private int childrenNum;
    //婴儿票数
    private int babyNum;

    public void addFlight(Flight flight)
    {
        flights.add(flight);
    }

    public Flight getCurrentFlight()
    {
        int index = tickets.size();
        if (index < flights.size())
        {
            Flight flight = flights.get(index);
            return flight;
        }
        return null;
    }

    public List<Flight> getFlights()
    {
        return flights;
    }

    public int getCurrentLine()
    {
        return currentLine;
    }

    public void setCurrentLine(int currentLine)
    {
        this.currentLine = currentLine;
    }

    public void addTicket()
    {
        tickets.add(ticket);
    }

    public List<PlaneTicket> getTickets()
    {
        return tickets;
    }

    public void removeLastTicket()
    {
        if (tickets.size() > 0)
        {
           ticket = tickets.remove(tickets.size() - 1);
        }else {
            ticket = null;
        }
    }

    public void clearTicket()
    {
        tickets.clear();
    }

    public int getAdultNum()
    {
        return adultNum;
    }

    public void setAdultNum(int adultNum)
    {
        this.adultNum = adultNum;
    }

    public int getChildrenNum()
    {
        return childrenNum;
    }

    public void setChildrenNum(int childrenNum)
    {
        this.childrenNum = childrenNum;
    }

    public int getBabyNum()
    {
        return babyNum;
    }

    public void setBabyNum(int babyNum)
    {
        this.babyNum = babyNum;
    }

    public void clearFlight()
    {
        flights.clear();
    }

    public void setFlights(List<Flight> flights)
    {
        this.flights = flights;
    }

    public PlaneTicket getTicket()
    {
        return ticket;
    }

    public void setTicket(PlaneTicket ticket)
    {
        this.ticket = ticket;
    }
}
