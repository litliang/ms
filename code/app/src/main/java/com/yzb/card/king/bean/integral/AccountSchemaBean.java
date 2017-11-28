package com.yzb.card.king.bean.integral;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/22
 * 描  述：
 */
public class AccountSchemaBean implements Serializable {

    private String cardIntegral = "0";//卡王积分

    private String couponCount ="0";//优惠券数量



    private String giftCardCount="0";//礼品卡数量

    private String shopOrderCount="0";//商家代收订单数量


    public String getCardIntegral() {
        return cardIntegral;
    }

    public void setCardIntegral(String cardIntegral) {
        this.cardIntegral = cardIntegral;
    }

    public String getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(String couponCount) {
        this.couponCount = couponCount;
    }

    public String getGiftCardCount() {
        return giftCardCount;
    }

    public void setGiftCardCount(String giftCardCount) {
        this.giftCardCount = giftCardCount;
    }

    public String getShopOrderCount() {
        return shopOrderCount;
    }

    public void setShopOrderCount(String shopOrderCount) {
        this.shopOrderCount = shopOrderCount;
    }
}
