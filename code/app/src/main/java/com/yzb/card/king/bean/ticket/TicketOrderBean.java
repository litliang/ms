package com.yzb.card.king.bean.ticket;

import com.yzb.card.king.bean.order.OrderBean;

import java.io.Serializable;

/**
 * 类名： TicketOrderBean
 * 作者： Lei Chao.
 * 日期： 2016-10-13
 * 描述：
 */
public class TicketOrderBean implements Serializable
{

    public String[] product = new String[4];
    public String[] startCityName = new String[4];
    public String[] endCityName = new String[4];
    public String[] startTime = new String[4];
    public String[] endTime = new String[4];
    public String[] timeSereses = new String[4];
    public String[] fareInfors = new String[4];
    // 藏等
    public String[] basecabinCodes = new String[4];
    // 代理商logo
    public String[] carrierLogos = new String[4];
    // 代理商名稱
    public String[] carrierNames = new String[4];
    // 航班号   最多4个航班；
    public String[] flightnumbers = new String[4];
    // 状态
    public String[] sturts = new String[4];
    // 航线类型
    public String[] routeType = new String[4];
    public int size = startCityName.length;
    public int orderStatus;
    public String orderID;
    public String orderAmount;
    public String[] orderNo = new String[4];
    private OrderBean orderBean; //大订单数据；

    public void setOrderBean(OrderBean orderBean)
    {
        this.orderBean = orderBean;
    }

    public OrderBean getOrderBean()
    {
        return orderBean;
    }
}