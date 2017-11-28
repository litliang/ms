package com.yzb.card.king.bean.common;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/23
 * 描  述：
 */
public class GoldTicketParam implements Serializable{

    private int issuePlatform;

    private int industryId;

    private long shopId;

    private long storeId;

    private long goodsId;

    public int getIssuePlatform()
    {
        return issuePlatform;
    }

    public void setIssuePlatform(int issuePlatform)
    {
        this.issuePlatform = issuePlatform;
    }

    public int getIndustryId()
    {
        return industryId;
    }

    public void setIndustryId(int industryId)
    {
        this.industryId = industryId;
    }

    public long getShopId()
    {
        return shopId;
    }

    public void setShopId(long shopId)
    {
        this.shopId = shopId;
    }

    public long getStoreId()
    {
        return storeId;
    }

    public void setStoreId(long storeId)
    {
        this.storeId = storeId;
    }

    public long getGoodsId()
    {
        return goodsId;
    }

    public void setGoodsId(long goodsId)
    {
        this.goodsId = goodsId;
    }
}
