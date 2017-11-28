package com.yzb.card.king.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/25
 * 描  述：
 */
public class ProductBaseOrderBean implements Serializable {

    /**
     * 订单id
     */
    private long orderId;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 订单创建时间
     */
    private String orderCreateTime;
    /**
     * 订单状态(0已取消、1未支付、2已支付、3已完成、4已退款、100删除)
     */
    private int orderStatus;
    /**
     * 核销码
     */
    private String verificationCode;
    /**
     * 房间总金额
     */
    private double roomsTotalAmount;
    /**
     * 实付金额
     */
    private double actualAmount;
    /**
     * 商品数量
     */
    private int goodsQuantity;
    /**
     * 订单联系人
     */
    private String contactName;
    /**
     * 订单联系电话
     */
    private String contactTel;
    /**
     * 订单备注
     */
    private String orderRemark;
    /**
     * 回调方法
     */
    private String notifyUrl;
    /**
     * 入住日期
     */
    private String arrDate;
    /**
     * 离店日期
     */
    private String depDate;
    /**
     * 旅客名称(多个使用,分隔)
     */
    private String guestNames;
    /**
     * 评价状态(1已评价；0未评价；)
     */
    private String voteStatus;
    /**
     * 订单金额（用户支付）
     */
    private double orderAmount;
    /**
     * 返现金额
     */
    private double cashbackAmount;
    /**
     * 供应商联系电话
     */
    private String shopTel;
    /**
     * 担保金额
     */
    private double guaranteeAmount;

    /**
     * 付款方式(1、到店付 2、在线付 3、担保支付)
     */
    private String paymentType;
    /**
     * 取消方式（免费取消、限时取消、付费取消）
     */
    private String cancelType;
    /**
     * 发票状态 （1已开发票；0未开发票 ）
     */
    private int invoiceStatus = 0;//
    /**
     * 订单操作时间
     */
    private List<OrderOptTime> optTimeList;

    private long shopId;

    public String getVerificationCode()
    {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode)
    {
        this.verificationCode = verificationCode;
    }

    public List<OrderOptTime> getOptTimeList()
    {
        return optTimeList;
    }

    public void setOptTimeList(List<OrderOptTime> optTimeList)
    {
        this.optTimeList = optTimeList;
    }

    public String getCancelType()
    {
        return cancelType;
    }

    public void setCancelType(String cancelType)
    {
        this.cancelType = cancelType;
    }

    public String getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }

    public String getVoteStatus()
    {
        return voteStatus;
    }

    public void setVoteStatus(String voteStatus)
    {
        this.voteStatus = voteStatus;
    }

    public double getCashbackAmount()
    {
        return cashbackAmount;
    }

    public void setCashbackAmount(double cashbackAmount)
    {
        this.cashbackAmount = cashbackAmount;
    }

    public String getShopTel()
    {
        return shopTel;
    }

    public void setShopTel(String shopTel)
    {
        this.shopTel = shopTel;
    }

    public double getGuaranteeAmount()
    {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(double guaranteeAmount)
    {
        this.guaranteeAmount = guaranteeAmount;
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

    public int getInvoiceStatus()
    {
        return invoiceStatus;
    }

    public void setInvoiceStatus(int invoiceStatus)
    {
        this.invoiceStatus = invoiceStatus;
    }

    public int getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public double getRoomsTotalAmount()
    {
        return roomsTotalAmount;
    }

    public void setRoomsTotalAmount(double roomsTotalAmount)
    {
        this.roomsTotalAmount = roomsTotalAmount;
    }

    public double getActualAmount()
    {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount)
    {
        this.actualAmount = actualAmount;
    }

    public int getGoodsQuantity()
    {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity)
    {
        this.goodsQuantity = goodsQuantity;
    }

    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    public String getContactTel()
    {
        return contactTel;
    }

    public void setContactTel(String contactTel)
    {
        this.contactTel = contactTel;
    }

    public String getOrderRemark()
    {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark)
    {
        this.orderRemark = orderRemark;
    }

    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }

    public String getArrDate()
    {
        return arrDate;
    }

    public void setArrDate(String arrDate)
    {
        this.arrDate = arrDate;
    }

    public String getDepDate()
    {
        return depDate;
    }

    public void setDepDate(String depDate)
    {
        this.depDate = depDate;
    }

    public String getGuestNames()
    {
        return guestNames;
    }

    public void setGuestNames(String guestNames)
    {
        this.guestNames = guestNames;
    }


    public long getShopId()
    {
        return shopId;
    }

    public void setShopId(long shopId)
    {
        this.shopId = shopId;
    }

    public static class OrderOptTime implements Serializable{

        private String optName;

        private String optTime;

        public String getOptName()
        {
            return optName;
        }

        public void setOptName(String optName)
        {
            this.optName = optName;
        }

        public String getOptTime()
        {
            return optTime;
        }

        public void setOptTime(String optTime)
        {
            this.optTime = optTime;
        }
    }
}
