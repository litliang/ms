package com.yzb.card.king.bean.my;

import com.yzb.card.king.ui.app.bean.BaseBean;

/**
 * 类  名：
 * 作  者：gengqiyun
 * 日  期：2017/1/10
 * 描  述：客户优惠券列表；
 */
public class CouponShopBean extends BaseBean
{
    private long actId; //	Long	活动id	N
    private String actName;    //	String	活动名称	N
    private String shopId;    //	String	商家ids	Y	平台时为0，多个使用英文逗号分割
    private String shopName;    //	String		商家名称 Y	多个使用英文逗号分割
    private String industryId;    //	Long	行业id	Y
    private String industryName;    //	String	行业名称	N
    private String shopImageCode;    //	String	商家图片N
    private String shopRegion;    //	String	商家区域
    private float actHot;    //	String	热度
    private long fullAmount;        //  BigDecimal	满额	N
    private long cutAmount;    //	BigDecimal	减额	N
    private boolean receiveStatus;    //	1已领取；0未领取；


    public long getActId()
    {
        return actId;
    }

    public void setActId(long actId)
    {
        this.actId = actId;
    }

    public String getActName()
    {
        return actName;
    }

    public void setActName(String actName)
    {
        this.actName = actName;
    }

    public String getShopId()
    {
        return shopId;
    }

    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getIndustryId()
    {
        return industryId;
    }

    public void setIndustryId(String industryId)
    {
        this.industryId = industryId;
    }

    public String getIndustryName()
    {
        return industryName;
    }

    public void setIndustryName(String industryName)
    {
        this.industryName = industryName;
    }

    public String getShopImageCode()
    {
        return shopImageCode;
    }

    public void setShopImageCode(String shopImageCode)
    {
        this.shopImageCode = shopImageCode;
    }

    public String getShopRegion()
    {
        return shopRegion;
    }

    public void setShopRegion(String shopRegion)
    {
        this.shopRegion = shopRegion;
    }

    public float getActHot()
    {
        return actHot;
    }

    public void setActHot(float actHot)
    {
        this.actHot = actHot;
    }

    public long getFullAmount()
    {
        return fullAmount;
    }

    public void setFullAmount(long fullAmount)
    {
        this.fullAmount = fullAmount;
    }

    public long getCutAmount()
    {
        return cutAmount;
    }

    public void setCutAmount(long cutAmount)
    {
        this.cutAmount = cutAmount;
    }

    public boolean isReceiveStatus()
    {
        return receiveStatus;
    }

    public void setReceiveStatus(boolean receiveStatus)
    {
        this.receiveStatus = receiveStatus;
    }



}
