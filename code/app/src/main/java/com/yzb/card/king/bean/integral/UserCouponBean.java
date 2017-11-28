package com.yzb.card.king.bean.integral;

import java.io.Serializable;

/**
 * 类  名：优惠券
 * 作  者：Li Yubing
 * 日  期：2016/6/23
 * 描  述：
 */
public class UserCouponBean implements Serializable {

    private String photo;

    private String  storeName;

    private double amount;

    private double value;

    private String hot;

    private String regionName;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}


