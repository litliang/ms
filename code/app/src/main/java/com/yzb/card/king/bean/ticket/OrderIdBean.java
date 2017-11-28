package com.yzb.card.king.bean.ticket;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：平台订单号集合；
 *
 * @author:gengqiyun
 * @date: 2016/12/4
 */
public class OrderIdBean implements Serializable
{
    private String orderTime; //服务器时间；
    private String orderIds; //订单平台总订单id;
    private String orderNo; //订单平台总订单号；
    private List<OrderIds> orderIdList; //平台订单号集合

    public String getOrderNo()
    {
        return orderNo;
    }

    public String getOrderIds()
    {
        return orderIds;
    }

    public void setOrderIds(String orderIds)
    {
        this.orderIds = orderIds;
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

    public static class OrderIds implements Serializable
    {
        private String orderId; //平台订单号（支付,如不需要支付，此值为空）
        private String orderNo; //航空公司订单号
        private float totalPrice; //订单金额
        private boolean needToPay; //是否需要支付

        public String getOrderId()
        {
            return orderId;
        }

        public void setOrderId(String orderId)
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

        public float getTotalPrice()
        {
            return totalPrice;
        }

        public void setTotalPrice(float totalPrice)
        {
            this.totalPrice = totalPrice;
        }

        public boolean isNeedToPay()
        {
            return needToPay;
        }

        public void setNeedToPay(boolean needToPay)
        {
            this.needToPay = needToPay;
        }
    }

    public List<OrderIds> getOrderIdList()
    {
        return orderIdList;
    }

    public void setOrderIdList(List<OrderIds> orderIdList)
    {
        this.orderIdList = orderIdList;
    }
}
