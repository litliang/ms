package com.yzb.card.king.bean.my;

import com.yzb.card.king.ui.app.bean.BaseBean;

/**
 * 类  名：
 * 作  者：gengqiyun
 * 日  期：2017/1/10
 * 描  述：客户优惠券列表；
 */
public class CouponsHomeBean extends BaseBean
{
    private long actId; //	Long	活动id	N
    private String actName;    //	String	活动名称	N
    private String uesStartDate;    //	String	使用开始日期	N	yyyy-MM-dd
    private String uesEndDate;    //	String	使用结束日期	N	yyyy-MM-dd
    private String useDateDesc;    //	String		使用时段描述N	多个使用英文逗号分割
    private String useTimeDesc;    //	String	使用时间描述	N	多个使用英文逗号分割
    private boolean expireStatus;    //	String		到期状态 N	1即将到期；0未到期
    private String platformType;    //	String	活动平台	N	2平台活动；3商家活动；
    private String industryId;    //	Long	行业id	Y
    private String industryName;    //	String	行业名称	N
    private String shopIds;    //	String	商家ids	Y	平台时为0，多个使用英文逗号分割
    private String shopNames;    //	String		商家名称 Y	多个使用英文逗号分割
    private String goodsIds;        //String	商品ids	Y	多个使用英文逗号分割
    private String actStatus;        //优惠券状态  3未使用、4已使用、5过期
    private String goodsInnerCodes;    //	String	商品内部编码	Y	多个使用英文逗号分割
    private String goodsNames;        // String	商品名称	Y	多个使用英文逗号分割
    private long fullAmount;        //  BigDecimal	满额	N
    private long cutAmount;    //	BigDecimal	减额	N


    //优惠券状态；  3未使用、4已使用、5过期
    public static String STATUS_NO_USE = "3";
    public static String STATUS_USED = "4";
    public static String STATUS_DATED = "5";

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

    public String getUesStartDate()
    {
        return super.isStrEmpty(uesStartDate);
    }

    public String getActStatus()
    {
        return actStatus;
    }

    public void setActStatus(String actStatus)
    {
        this.actStatus = actStatus;
    }

    public void setUesStartDate(String uesStartDate)
    {
        this.uesStartDate = uesStartDate;
    }

    public String getUesEndDate()
    {
        return super.isStrEmpty(uesEndDate);
    }

    public void setUesEndDate(String uesEndDate)
    {
        this.uesEndDate = uesEndDate;
    }

    public String getUseDateDesc()
    {
        return super.isStrEmpty(useDateDesc);
    }

    public void setUseDateDesc(String useDateDesc)
    {
        this.useDateDesc = useDateDesc;
    }

    public String getUseTimeDesc()
    {
        return super.isStrEmpty(useTimeDesc);
    }

    public void setUseTimeDesc(String useTimeDesc)
    {
        this.useTimeDesc = useTimeDesc;
    }

    public boolean isExpireStatus()
    {
        return expireStatus;
    }

    public void setExpireStatus(boolean expireStatus)
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
        return super.isStrEmpty(industryName);
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
        return super.isStrEmpty(shopNames);
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
        return super.isStrEmpty(goodsNames);
    }

    public void setGoodsNames(String goodsNames)
    {
        this.goodsNames = goodsNames;
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
}
