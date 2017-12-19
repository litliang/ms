package com.yzb.card.king.bean.common;

import java.io.Serializable;

/**
 * 类  名：基础优惠券类
 * 作  者：Li Yubing
 * 日  期：2017/8/23
 * 描  述：
 */
public class BaseCouponBean implements Serializable{
    /**
     * 活动id
     */
    private  long actId;

    /**
     * 发放平台  1平台；2商家
     */
    private String issuePlatform;
    /**
     * 优惠券明细id
     */
    private long itemId;
    /**
     * 优惠类型 1满减券；2折扣券；3抵扣券
     */
    private String couponType;
    /**
     * 活动名称
     */
    private String actName;
    /**
     * 活动描述
     */
    private String actDesc;
    /**
     * 有效开始日期
     */
    private String startDate;

    /**
     * 有效截至日期
     */
    private String endDate;
    /**
     * 满额  0不限;抵扣金额
     */
    private int fullAmount;
    /**
     * 减内容(满减时为减额；折扣时为折扣率)
     */
    private int cutAmount;
    /**
     * 购买金额
     */
    private  int cutValue;
    /**
     * 领取状态 (1已领取；0未领取)
     */
    private String receiveStatus;

    public long getActId()
    {
        return actId;
    }

    public void setActId(long actId)
    {
        this.actId = actId;
    }

    public String getIssuePlatform()
    {
        return issuePlatform;
    }

    public void setIssuePlatform(String issuePlatform)
    {
        this.issuePlatform = issuePlatform;
    }

    public long getItemId()
    {
        return itemId;
    }

    public void setItemId(long itemId)
    {
        this.itemId = itemId;
    }

    public String getCouponType()
    {
        return couponType;
    }

    public void setCouponType(String couponType)
    {
        this.couponType = couponType;
    }

    public String getActName()
    {
        return actName;
    }

    public void setActName(String actName)
    {
        this.actName = actName;
    }

    public String getActDesc()
    {
        return actDesc;
    }

    public void setActDesc(String actDesc)
    {
        this.actDesc = actDesc;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public int getFullAmount()
    {
        return fullAmount;
    }

    public void setFullAmount(int fullAmount)
    {
        this.fullAmount = fullAmount;
    }

    public int getCutValue()
    {
        return cutValue;
    }

    public int getCutAmount() {
        return cutAmount;
    }

    public void setCutAmount(int cutAmount) {
        this.cutAmount = cutAmount;
    }

    public void setCutValue(int cutValue)
    {
        this.cutValue = cutValue;
    }

    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }
}
