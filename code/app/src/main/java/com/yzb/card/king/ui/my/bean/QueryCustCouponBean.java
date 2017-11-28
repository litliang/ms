package com.yzb.card.king.ui.my.bean;

import java.io.Serializable;

/**
 * 类 名： 查询优惠券列表
 * 作 者： gaolei
 * 日 期：2017年01月11日
 * 描 述：查询优惠券列表
 */

public class QueryCustCouponBean implements Serializable {

    /**
     * industryName : 酒店
     * useTimeDesc : 0,0
     * actStatus : 4
     * uesStartDate : 2016-12-22
     * actId : 3
     * platformType : 3
     * cutAmount : 30
     * actName : 优惠券3
     * goodsIds : 188
     * fullAmount : 200
     * shopNames : 锦江之星
     * goodsNames : 酒店
     * gainTime : 2016-12-30
     * shopIds : 1
     * useDateDesc : 01:01:01至23:59:59,01:01:01至23:59:59
     * goodsInnerCodes : 1
     * uesEndDate : 2017-02-28
     * gain_quantity : 1
     */

    private String industryName;
    private String useTimeDesc;
    private String actStatus;
    private String uesStartDate;
    private int actId;
    private String platformType;
    private int cutAmount;
    private String actName;
    private String goodsIds;
    private int fullAmount;
    private String shopNames;
    private String goodsNames;
    private String gainTime;
    private String shopIds;
    private String useDateDesc;
    private String goodsInnerCodes;
    private String uesEndDate;
    private int gain_quantity;

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getUseTimeDesc() {
        return useTimeDesc;
    }

    public void setUseTimeDesc(String useTimeDesc) {
        this.useTimeDesc = useTimeDesc;
    }

    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public String getUesStartDate() {
        return uesStartDate;
    }

    public void setUesStartDate(String uesStartDate) {
        this.uesStartDate = uesStartDate;
    }

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public int getCutAmount() {
        return cutAmount;
    }

    public void setCutAmount(int cutAmount) {
        this.cutAmount = cutAmount;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(String goodsIds) {
        this.goodsIds = goodsIds;
    }

    public int getFullAmount() {
        return fullAmount;
    }

    public void setFullAmount(int fullAmount) {
        this.fullAmount = fullAmount;
    }

    public String getShopNames() {
        return shopNames;
    }

    public void setShopNames(String shopNames) {
        this.shopNames = shopNames;
    }

    public String getGoodsNames() {
        return goodsNames;
    }

    public void setGoodsNames(String goodsNames) {
        this.goodsNames = goodsNames;
    }

    public String getGainTime() {
        return gainTime;
    }

    public void setGainTime(String gainTime) {
        this.gainTime = gainTime;
    }

    public String getShopIds() {
        return shopIds;
    }

    public void setShopIds(String shopIds) {
        this.shopIds = shopIds;
    }

    public String getUseDateDesc() {
        return useDateDesc;
    }

    public void setUseDateDesc(String useDateDesc) {
        this.useDateDesc = useDateDesc;
    }

    public String getGoodsInnerCodes() {
        return goodsInnerCodes;
    }

    public void setGoodsInnerCodes(String goodsInnerCodes) {
        this.goodsInnerCodes = goodsInnerCodes;
    }

    public String getUesEndDate() {
        return uesEndDate;
    }

    public void setUesEndDate(String uesEndDate) {
        this.uesEndDate = uesEndDate;
    }

    public int getGain_quantity() {
        return gain_quantity;
    }

    public void setGain_quantity(int gain_quantity) {
        this.gain_quantity = gain_quantity;
    }
}
