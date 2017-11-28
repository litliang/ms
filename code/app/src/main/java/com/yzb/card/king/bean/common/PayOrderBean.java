package com.yzb.card.king.bean.common;

import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.Utils;

import java.io.Serializable;

/**
 * 类  名：下單成功，訂單付款信息
 * 作  者：Li Yubing
 * 日  期：2017/8/14
 * 描  述：
 */
public class PayOrderBean implements Serializable{

    private String orderNo;
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private String orderTime;

    private double orderAmount;

    private  long orderId;

    private String notifyUrl;

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
        this.orderTime = Utils.toData( Utils.toTimestamp(orderTime,1),16);

    }

    public double getOrderAmount()
    {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount)
    {
        this.orderAmount = orderAmount;
    }

    public long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(long orderId)
    {
        this.orderId = orderId;
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
