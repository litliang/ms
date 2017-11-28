package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/16
 * 描  述：
 */
public class BaseOrderServeBean implements Serializable{

    private long orderId;

    private float orderAmount;

    private int orderStatus;
    /**
     * 付款方式(1到店付、2在线支付、3担保支付)
     */
    private int paymentType;
    /**
     * 取消方式(1免费取消、2限时取消、3付费取消)
     */
    private int cancelType;
    /**
     * 发票状态(1已开发票；0未开发票；)
     */
    private int invoiceStatus;

    private String notifyUrl;

    private double lng;

    private double lat;

    private long cityId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单创建时间
     */
    private String orderCreateTime;

    private String tel;

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getOrderCreateTime()
    {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime)
    {
        this.orderCreateTime = orderCreateTime;
    }

    public long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(long orderId)
    {
        this.orderId = orderId;
    }

    public float getOrderAmount()
    {
        return orderAmount;
    }

    public void setOrderAmount(float orderAmount)
    {
        this.orderAmount = orderAmount;
    }

    public int getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public long getCityId()
    {
        return cityId;
    }

    public void setCityId(long cityId)
    {
        this.cityId = cityId;
    }

    public int getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(int paymentType)
    {
        this.paymentType = paymentType;
    }

    public int getCancelType()
    {
        return cancelType;
    }

    public void setCancelType(int cancelType)
    {
        this.cancelType = cancelType;
    }

    public int getInvoiceStatus()
    {
        return invoiceStatus;
    }

    public void setInvoiceStatus(int invoiceStatus)
    {
        this.invoiceStatus = invoiceStatus;
    }
}
