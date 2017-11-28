package com.yzb.card.king.bean.ticket;

import java.io.Serializable;

/**
 * Created by sushuiku on 2016/11/9.
 */

public class PlaneTicketRebookBean implements Serializable {

    private String orderNo;
    private String airOrderNo;
    private String payAmount;


    public String getAirOrderNo() {
        return airOrderNo;
    }

    public void setAirOrderNo(String airOrderNo) {
        this.airOrderNo = airOrderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }


}
