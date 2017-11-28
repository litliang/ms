package com.yzb.card.king.ui.bonus.bean;

import java.io.Serializable;

/**
 * 类 名： 红包发送记录
 * 作 者： gaolei
 * 日 期：2017年01月06日
 * 描 述：红包明细
 */

public class RedBagSendBean implements Serializable{

    private int orderId;
    private String orderStatus;
    private String orderStatusDesc;
    private String orderAmount;
    private String orderTime;
    private String totalQuantity;
    private String receiveQuantity;
    private String themeName;
    private String closeImageCode;
    private String openImageCode;


    public String getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(String orderTime)
    {
        this.orderTime = orderTime;
    }

    public String getCloseImageCode()
    {
        return closeImageCode;
    }

    public void setCloseImageCode(String closeImageCode)
    {
        this.closeImageCode = closeImageCode;
    }

    public String getOpenImageCode()
    {
        return openImageCode;
    }

    public void setOpenImageCode(String openImageCode)
    {
        this.openImageCode = openImageCode;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public String getReceiveQuantity() {
        return receiveQuantity;
    }

    public void setReceiveQuantity(String receiveQuantity) {
        this.receiveQuantity = receiveQuantity;
    }
}
