package com.yzb.card.king.bean.ticket;

import com.yzb.card.king.ui.app.bean.BaseBean;

/**
 * 功能：生成订单成功后的出参；
 *
 * @author:gengqiyun
 * @date: 2016/10/17
 */
public class OrderOutBean extends BaseBean
{
    private String orderId; //订单id；
    private String orderTime; //服务器时间  格式yyyy-MM-dd hh:mm:ss；
    private String orderNo; //订单号；
    private String notifyUrl; //回调url,钱包付款时使用；
    private double orderAmount;//订单金额

    public double getOrderAmount()
    {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount)
    {
        this.orderAmount = orderAmount;
    }

    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(String orderTime)
    {
        this.orderTime = orderTime;
    }

    public String getOrderId()
    {
        return super.isStrEmpty(orderId);
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
}
