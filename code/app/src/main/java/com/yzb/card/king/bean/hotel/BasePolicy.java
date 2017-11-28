package com.yzb.card.king.bean.hotel;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/3/13 16:06
 */
public class BasePolicy implements Serializable
{
    private boolean couponStatus;    //优惠券状态（1有；0无）
    private boolean bankStatus;    //银行优惠状态（1有；0无）
    private String lastReserve;
    private String policysPhotoUrls;
    private String policysName;
    private Long policysId;
    private Float policysPrice;
    private Float retailPrice;    //门市价
    private String paymentType; //1到店付,2在线付；
    private Long shopId;
    private String shopType;//1平台商家；2代理商；
    private String useDateDesc;
    private String effDateDesc;
    private String useTimeDesc;
    private String cancelStatus;
    private List<SumPoint> pointsList;

    public List<SumPoint> getPointsList()
    {
        return pointsList;
    }

    public void setPointsList(List<SumPoint> pointsList)
    {
        this.pointsList = pointsList;
    }

    public String getPolicysPhotoUrls()
    {
        return policysPhotoUrls;
    }

    public void setPolicysPhotoUrls(String policysPhotoUrls)
    {
        this.policysPhotoUrls = policysPhotoUrls;
    }

    public boolean getCouponStatus()
    {
        return couponStatus;
    }

    public void setCouponStatus(boolean couponStatus)
    {
        this.couponStatus = couponStatus;
    }

    public boolean getBankStatus()
    {
        return bankStatus;
    }

    public void setBankStatus(boolean bankStatus)
    {
        this.bankStatus = bankStatus;
    }

    public String getLastReserve()
    {
        return lastReserve;
    }

    public void setLastReserve(String lastReserve)
    {
        this.lastReserve = lastReserve;
    }

    public String getPolicysName()
    {
        return policysName;
    }

    public void setPolicysName(String policysName)
    {
        this.policysName = policysName;
    }

    public Long getPolicysId()
    {
        return policysId;
    }

    public void setPolicysId(Long policysId)
    {
        this.policysId = policysId;
    }

    public Float getPolicysPrice()
    {
        return policysPrice;
    }

    public void setPolicysPrice(Float policysPrice)
    {
        this.policysPrice = policysPrice;
    }

    public Float getRetailPrice()
    {
        return retailPrice;
    }

    public void setRetailPrice(Float retailPrice)
    {
        this.retailPrice = retailPrice;
    }

    public String getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }

    public Long getShopId()
    {
        return shopId;
    }

    public void setShopId(Long shopId)
    {
        this.shopId = shopId;
    }

    public String getShopType()
    {
        return shopType;
    }

    public void setShopType(String shopType)
    {
        this.shopType = shopType;
    }

    public String getUseDateDesc()
    {
        return useDateDesc;
    }

    public void setUseDateDesc(String useDateDesc)
    {
        this.useDateDesc = useDateDesc;
    }

    public String getEffDateDesc()
    {
        return effDateDesc;
    }

    public void setEffDateDesc(String effDateDesc)
    {
        this.effDateDesc = effDateDesc;
    }

    public String getUseTimeDesc()
    {
        return useTimeDesc;
    }

    public void setUseTimeDesc(String useTimeDesc)
    {
        this.useTimeDesc = useTimeDesc;
    }

    public String getCancelStatus()
    {
        return cancelStatus;
    }

    public void setCancelStatus(String cancelStatus)
    {
        this.cancelStatus = cancelStatus;
    }
}
