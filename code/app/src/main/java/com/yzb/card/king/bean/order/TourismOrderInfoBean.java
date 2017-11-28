package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * Created by Timmy on 16/7/27.
 */
public class TourismOrderInfoBean implements Serializable{
    private String contactsMobile;
    private String contactsName;
    private String orderAmount;
    private String orderId;
    private String orderNo;
    private String orderStatus;
    private String orderTime;
    private String toolName;
    private String toolNumber;

    public String getContactsMobile() {
        return contactsMobile;
    }

    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolNumber() {
        return toolNumber;
    }

    public void setToolNumber(String toolNumber) {
        this.toolNumber = toolNumber;
    }
}
