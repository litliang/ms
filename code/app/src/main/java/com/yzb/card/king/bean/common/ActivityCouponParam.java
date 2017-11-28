package com.yzb.card.king.bean.common;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/30
 * 描  述：
 */
public class ActivityCouponParam implements Serializable{

    private long orderId;

    private long couponId =-1;

    private double orderAmount;

    private long couponItemId =-1;

    private double couponAmount;

    private double bonusAmount = 0;

    private double giftcardAmount = 0;

    public ActivityCouponParam(long orderId, double orderAmount)
    {
        this.orderId = orderId;
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

    public long getCouponId()
    {
        return couponId;
    }

    public void setCouponId(long couponId)
    {
        this.couponId = couponId;
    }

    public double getOrderAmount()
    {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount)
    {
        this.orderAmount = orderAmount;
    }

    public long getCouponItemId()
    {
        return couponItemId;
    }

    public void setCouponItemId(long couponItemId)
    {
        this.couponItemId = couponItemId;
    }

    public double getCouponAmount()
    {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount)
    {
        this.couponAmount = couponAmount;
    }

    public double getBonusAmount()
    {
        return bonusAmount;
    }

    public void setBonusAmount(double bonusAmount)
    {
        this.bonusAmount = bonusAmount;
    }

    public double getGiftcardAmount()
    {
        return giftcardAmount;
    }

    public void setGiftcardAmount(double giftcardAmount)
    {
        this.giftcardAmount = giftcardAmount;
    }
}
