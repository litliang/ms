package com.yzb.card.king.bean.ticket;

import com.yzb.card.king.ui.app.bean.BaseBean;

/**
 * 功能：代理商优惠券；
 *
 * @author:gengqiyun
 * @date: 2016/10/12
 */
public class CustCouponBean extends BaseBean
{
    private String actId; //优惠券id
    private String actName; //优惠券名称；

    private String startTime; //使用开始日期；
    private String endTime; //使用结束日期；
    private String useDateDesc; //使用时段描述；
    private String useTimeDesc; //使用时间描述；
    private String expireStatus; //1即将到期；0未到期；
    private String platformType;//2平台活动；3商家活动；
    private String industryId;//行业id
    private String industryName;//行业名称
    private String shopIds;//商家ids 平台时为0，多个使用英文逗号分割
    private String shopNames;//商家名称
    private String goodsIds;//商品ids
    private String goodsInnerCodes;//商品内部编码
    private String goodsNames;//商品名称
    private String itemId;//商品名称
    private double fullAmount; //满额
    private double cutAmount; //减额
    private int grantCondition; //发放条件 1不限；2满额

    public String getActId()
    {
        return actId;
    }

    public String getItemId()
    {
        return itemId;
    }

    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }

    public int getGrantCondition()
    {
        return grantCondition;
    }

    public void setGrantCondition(int grantCondition)
    {
        this.grantCondition = grantCondition;
    }

    public void setActId(String actId)
    {
        this.actId = actId;
    }

    public String getActName()
    {
        return actName;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public void setActName(String actName)
    {
        this.actName = actName;
    }

    public String getUseDateDesc()
    {
        return useDateDesc;
    }

    public void setUseDateDesc(String useDateDesc)
    {
        this.useDateDesc = useDateDesc;
    }

    public String getUseTimeDesc()
    {
        return useTimeDesc;
    }

    public void setUseTimeDesc(String useTimeDesc)
    {
        this.useTimeDesc = useTimeDesc;
    }

    public String getExpireStatus()
    {
        return expireStatus;
    }

    public void setExpireStatus(String expireStatus)
    {
        this.expireStatus = expireStatus;
    }

    public String getPlatformType()
    {
        return platformType;
    }

    public void setPlatformType(String platformType)
    {
        this.platformType = platformType;
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

    public String getShopIds()
    {
        return shopIds;
    }

    public void setShopIds(String shopIds)
    {
        this.shopIds = shopIds;
    }

    public String getShopNames()
    {
        return shopNames;
    }

    public void setShopNames(String shopNames)
    {
        this.shopNames = shopNames;
    }

    public String getGoodsIds()
    {
        return goodsIds;
    }

    public void setGoodsIds(String goodsIds)
    {
        this.goodsIds = goodsIds;
    }

    public String getGoodsInnerCodes()
    {
        return goodsInnerCodes;
    }

    public void setGoodsInnerCodes(String goodsInnerCodes)
    {
        this.goodsInnerCodes = goodsInnerCodes;
    }

    public String getGoodsNames()
    {
        return goodsNames;
    }

    public void setGoodsNames(String goodsNames)
    {
        this.goodsNames = goodsNames;
    }

    public double getFullAmount()
    {
        return fullAmount;
    }

    public void setFullAmount(double fullAmount)
    {
        this.fullAmount = fullAmount;
    }

    public double getCutAmount()
    {
        return cutAmount;
    }

    public void setCutAmount(double cutAmount)
    {
        this.cutAmount = cutAmount;
    }
}
