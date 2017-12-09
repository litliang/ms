package com.yzb.card.king.bean.my;

import java.io.Serializable;

/**
 * 类  名：优惠券、代金券解析类
 * 作  者：Li Yubing
 * 日  期：2017/10/20
 * 描  述：
 */
public class CouponInfoBean implements Serializable{

    //券id
    private long actId;
    //门店id
    private long storeId;
    //门店名称
    private String storeName;
    //门店图片
    private String storeImageUrl;
    //门店所在区县
    private String districtName;
    //热度
    private  float hot;
    //优惠类型	1满减券；2折扣券；3抵扣券；
    private int couponType;
    //满额	 0不限
    private float fullAmount;
    //减额	 如70，满减时为减额（70元）；折扣时为7折
    private  float cutAmount;
    //领取状态 1已领取；0未领取；
    private int receiveStatus;
    //优惠券名称
    private String couponName;
    //优惠券使用开始日期
    private String startDate;
    //优惠券使用截至日期
    private String endDate;
     //代金券名称
    private String actName;

    private String useRule;

    public String getUseRule() {
        return useRule;
    }

    public void setUseRule(String useRule) {
        this.useRule = useRule;
    }

    public long getActId() {
        return actId;
    }

    public void setActId(long actId) {
        this.actId = actId;
    }

    public long getStoreId()
    {
        return storeId;
    }

    public void setStoreId(long storeId)
    {
        this.storeId = storeId;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public String getStoreImageUrl()
    {
        return storeImageUrl;
    }

    public void setStoreImageUrl(String storeImageUrl)
    {
        this.storeImageUrl = storeImageUrl;
    }

    public String getDistrictName()
    {
        return districtName;
    }

    public void setDistrictName(String districtName)
    {
        this.districtName = districtName;
    }

    public float getHot() {
        return hot;
    }

    public void setHot(float hot) {
        this.hot = hot;
    }

    public int getCouponType()
    {
        return couponType;
    }

    public void setCouponType(int couponType)
    {
        this.couponType = couponType;
    }

    public float getFullAmount()
    {
        return fullAmount;
    }

    public void setFullAmount(float fullAmount)
    {
        this.fullAmount = fullAmount;
    }

    public float getCutAmount()
    {
        return cutAmount;
    }

    public void setCutAmount(float cutAmount)
    {
        this.cutAmount = cutAmount;
    }

    public int getReceiveStatus()
    {
        return receiveStatus;
    }

    public void setReceiveStatus(int receiveStatus)
    {
        this.receiveStatus = receiveStatus;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }
}
