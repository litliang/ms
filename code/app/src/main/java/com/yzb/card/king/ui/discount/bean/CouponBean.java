package com.yzb.card.king.ui.discount.bean;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by injun on 2016/6/24.
 * <p/>
 * 优惠券实体类
 */
public class CouponBean implements Serializable {


    /**
     * batchName : 满100-30
     * batchId : 2
     */

    private String batchName;
    private int batchId;
    private float batchAmount;
    /**
     * 优惠类型(1满减券；2折扣券)
     */
    private int couponType;

    public float getBatchAmount()
    {
        return batchAmount;
    }

    public void setBatchAmount(float batchAmount)
    {
        this.batchAmount = batchAmount;
    }

    public int getCouponType()
    {
        return couponType;
    }

    public void setCouponType(int couponType)
    {
        this.couponType = couponType;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }
}
