package com.yzb.card.king.bean.hotel;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/15 12:07
 */
public class HbDialogParam
{
    private String shopIds;
    private String goodsIds;
    private int platformType;
    private int activityItem;
    private String goodsCodes; //商品code码；

    public HbDialogParam(){}

    public HbDialogParam(String shopIds, String goodsIds, int platformType, int activityItem, String goodsCodes)
    {
        this.shopIds = shopIds;
        this.goodsIds = goodsIds;
        this.platformType = platformType;
        this.activityItem = activityItem;
        this.goodsCodes = goodsCodes;
    }

    public String getShopIds()
    {
        return shopIds;
    }

    public void setShopIds(String shopIds)
    {
        this.shopIds = shopIds;
    }

    public String getGoodsIds()
    {
        return goodsIds;
    }

    public void setGoodsIds(String goodsIds)
    {
        this.goodsIds = goodsIds;
    }

    public String getGoodsCodes()
    {
        return goodsCodes;
    }

    public void setGoodsCodes(String goodsCodes)
    {
        this.goodsCodes = goodsCodes;
    }

    public int getPlatformType()
    {
        return platformType;
    }

    public void setPlatformType(int platformType)
    {
        this.platformType = platformType;
    }

    public int getActivityItem()
    {
        return activityItem;
    }

    public void setActivityItem(int activityItem)
    {
        this.activityItem = activityItem;
    }
}
