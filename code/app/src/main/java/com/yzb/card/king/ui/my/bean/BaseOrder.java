package com.yzb.card.king.ui.my.bean;

import java.util.Date;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/2/15 18:16
 */
public class BaseOrder
{
    Long orderId;
    String orderNo;
    Date orderTime;
    String notifyUrl;

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public Date getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }

    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }
}
