package com.yzb.card.king.bean.travel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sushuiku on 2016/11/21.
 *
 * 旅游订单详情；
 */

public class TravelOrderDetailBean implements Serializable{

    private long orderId;
    private String orderNo;// 订单号
    private String orderTime; // 订单时间
    private String orderStatus; // 订单状态
    private String productImageUrl; // 产品图片；
    private String payType;   // 支付方式
    private double orderAmount; // 订单金额
    private List<TravelOrderDetailguestListBean> guestList;


    private TourTicketDetailContactsBean contacts;
    private TourTicketDetailProductsInfoBean productInfo;

    public String getProductImageUrl()
    {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl)
    {
        this.productImageUrl = productImageUrl;
    }

    public TourTicketDetailContactsBean getContacts() {
        return contacts;
    }

    public void setContacts(TourTicketDetailContactsBean contacts) {
        this.contacts = contacts;
    }

    public TourTicketDetailProductsInfoBean getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(TourTicketDetailProductsInfoBean productInfo) {
        this.productInfo = productInfo;
    }


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }



    public List<TravelOrderDetailguestListBean> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<TravelOrderDetailguestListBean> guestList) {
        this.guestList = guestList;
    }




}
