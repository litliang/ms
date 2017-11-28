package com.yzb.card.king.bean.travel;

import com.yzb.card.king.ui.app.bean.BaseBean;

import java.util.List;

/**
 * 功能：旅游产品详情；
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public class TravelProduDetailBean extends BaseBean
{
    private String productId;//产品id
    private String productName;//产品名称
    private String productImageUrl;    //产品图片	多个使用英文逗号分割
    private String fromPlace;//实际出发地
    private String agentId;// 旅行社id
    private String agentName;//旅行社名称
    private String marketPrice;    //市场价		最低的特惠价对应的市场价
    private String minPrice;        //特惠价	产品中最低有效价格
    private String lineId;    //线路id
    private String labels;    //	当地特色	多个使用英文逗号分割
    private String productType;//产品类型
    private float vote;    //评分 默认8分

    private int travelDays;    //出行天数
    private String bankStatus;    //银行优惠状态	1有；0无
    private String platformPointsDesc; //平台积分描述
    private String shopPointsDesc;//商家积分描述
    private String couponStatus;    //优惠券状态	1有；0无
    private String bonusStatus;    //红包状态	1有；0无
    private int voteQuantity;    //评价数量
    private boolean collectionStatus;    //收藏状态	true已收藏、false未收藏
    private String characteristicUrls;    //	产品特色	多个使用英文逗号分割
    private List<TravelNoticeBean> feeIncludeList;//费用包含
    private List<TravelNoticeBean> oneselfFeeList;//自理费用

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return super.isStrEmpty(productName);
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public List<TravelNoticeBean> getFeeIncludeList()
    {
        return feeIncludeList;
    }

    public void setFeeIncludeList(List<TravelNoticeBean> feeIncludeList)
    {
        this.feeIncludeList = feeIncludeList;
    }

    public List<TravelNoticeBean> getOneselfFeeList()
    {
        return oneselfFeeList;
    }

    public void setOneselfFeeList(List<TravelNoticeBean> oneselfFeeList)
    {
        this.oneselfFeeList = oneselfFeeList;
    }

    public String getProductImageUrl()
    {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl)
    {
        this.productImageUrl = productImageUrl;
    }

    public String getFromPlace()
    {
        return super.isStrEmpty(fromPlace);
    }

    public void setFromPlace(String fromPlace)
    {
        this.fromPlace = fromPlace;
    }

    public String getAgentId()
    {
        return agentId;
    }

    public void setAgentId(String agentId)
    {
        this.agentId = agentId;
    }

    public String getAgentName()
    {
        return agentName;
    }

    public void setAgentName(String agentName)
    {
        this.agentName = agentName;
    }

    public String getMarketPrice()
    {
        return super.isStrEmpty(marketPrice);
    }

    public void setMarketPrice(String marketPrice)
    {
        this.marketPrice = marketPrice;
    }

    public String getMinPrice()
    {
        return super.isStrEmpty(minPrice);
    }

    public void setMinPrice(String minPrice)
    {
        this.minPrice = minPrice;
    }

    public String getLineId()
    {
        return lineId;
    }

    public void setLineId(String lineId)
    {
        this.lineId = lineId;
    }

    public String getLabels()
    {
        return super.isStrEmpty(labels);
    }

    public void setLabels(String labels)
    {
        this.labels = labels;
    }

    public String getProductType()
    {
        return super.isStrEmpty(productType);
    }

    public void setProductType(String productType)
    {
        this.productType = productType;
    }

    public float getVote()
    {
        return vote;
    }

    public void setVote(float vote)
    {
        this.vote = vote;
    }

    public int getTravelDays()
    {
        return travelDays;
    }

    public void setTravelDays(int travelDays)
    {
        this.travelDays = travelDays;
    }

    public String getBankStatus()
    {
        return super.isStrEmpty(bankStatus);
    }

    public void setBankStatus(String bankStatus)
    {
        this.bankStatus = bankStatus;
    }

    public String getPlatformPointsDesc()
    {
        return super.isStrEmpty(platformPointsDesc);
    }

    public void setPlatformPointsDesc(String platformPointsDesc)
    {
        this.platformPointsDesc = platformPointsDesc;
    }

    public String getShopPointsDesc()
    {
        return super.isStrEmpty(shopPointsDesc);
    }

    public void setShopPointsDesc(String shopPointsDesc)
    {
        this.shopPointsDesc = shopPointsDesc;
    }

    public String getCouponStatus()
    {
        return super.isStrEmpty(couponStatus);
    }

    public void setCouponStatus(String couponStatus)
    {
        this.couponStatus = couponStatus;
    }

    public String getBonusStatus()
    {
        return super.isStrEmpty(bonusStatus);
    }

    public void setBonusStatus(String bonusStatus)
    {
        this.bonusStatus = bonusStatus;
    }

    public int getVoteQuantity()
    {
        return voteQuantity;
    }

    public void setVoteQuantity(int voteQuantity)
    {
        this.voteQuantity = voteQuantity;
    }

    public boolean isCollectionStatus()
    {
        return collectionStatus;
    }

    public void setCollectionStatus(boolean collectionStatus)
    {
        this.collectionStatus = collectionStatus;
    }

    public String getCharacteristicUrls()
    {
        return characteristicUrls;
    }

    public void setCharacteristicUrls(String characteristicUrls)
    {
        this.characteristicUrls = characteristicUrls;
    }
}
