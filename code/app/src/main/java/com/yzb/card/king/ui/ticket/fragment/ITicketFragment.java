package com.yzb.card.king.ui.ticket.fragment;

import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.bean.ticket.ShippingSpace;
import com.yzb.card.king.ui.other.bean.City;

import java.util.Date;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/17 15:47
 */
public interface ITicketFragment
{
    void setPlace(City city);

    void setDate(Date date);

    void setShippSpace(ShippingSpace shippSpace);

    List<Flight> getFlights();
}
