package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/1/18
 * 描  述：
 */
public class FastPaymentOrderBean implements Serializable{
    /**
     * 订单id
     */
    private long orderId;
    /**
     * 订单时间
     */
    private String  orderTime;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 回调方法
     */
    private String notifyUrl;
    /**
     * 订单状态说明
     */
    private String orderStatusDesc;
    /**
     * 订单金额
     */
    private String orderAmount;
    /**
     * 支付方式说明
     */
    private String paymentDesc;
    /**
     * activity的标志
     */
    private String markActivity;

    public String getMarkActivity()
    {
        return markActivity;
    }

    public void setMarkActivity(String markActivity)
    {
        this.markActivity = markActivity;
    }

    public long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(long orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(String orderTime)
    {
        this.orderTime = orderTime;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }

    public String getOrderStatusDesc()
    {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc)
    {
        this.orderStatusDesc = orderStatusDesc;
    }

    public String getOrderAmount()
    {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount)
    {
        this.orderAmount = orderAmount;
    }

    public String getPaymentDesc()
    {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc)
    {
        this.paymentDesc = paymentDesc;
    }
}
