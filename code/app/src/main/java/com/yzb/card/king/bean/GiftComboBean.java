package com.yzb.card.king.bean;

import java.io.Serializable;

/**
 * 类  名：礼品卡套餐
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：
 */
public class GiftComboBean implements Serializable {

    public boolean ifHomeGiftComboFlag = true;
    /**
     * 活动id
     */
    private long actId;
    /**\
     * 卡权益所属酒店id
     */
    private long hotelId;
    /**
     * 发放商家id
     */
    private long shopId;
    /**
     * 活动名称
     */
    private String actName;
    /**
     * 有效期限描述（卡权益）
     */
    private String effMonthDesc;
    /**
     * 有效期限描述（限时抢购）
     */
    private String effDateDesc;
    /**
     * 适用门店说明
     */
    private String storeDesc;
    /**
     * 抢购截至时间
     */
    private String endTime;
    /**
     * 线上价
     */
    private int onlinePrice;
    /**
     * 门店价
     */
    private int storePrice;
    /**
     * 图片
     */
    private String photoUrls;
    /**
     * 主卡图片
     */
    private String cardPhoto;
    /**
     * 主卡名称
     */
    private String giftsName;
    /**
     * 商家名称
     */
    private String shopName;

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

    public String getEffMonthDesc()
    {
        return effMonthDesc;
    }

    public void setEffMonthDesc(String effMonthDesc)
    {
        this.effMonthDesc = effMonthDesc;
    }

    public String getEffDateDesc() {
        return effDateDesc;
    }

    public void setEffDateDesc(String effDateDesc) {
        this.effDateDesc = effDateDesc;
    }

    public String getStoreDesc()
    {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc)
    {
        this.storeDesc = storeDesc;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public int getOnlinePrice()
    {
        return onlinePrice;
    }

    public void setOnlinePrice(int onlinePrice)
    {
        this.onlinePrice = onlinePrice;
    }

    public int getStorePrice()
    {
        return storePrice;
    }

    public void setStorePrice(int storePrice)
    {
        this.storePrice = storePrice;
    }

    public String getPhotoUrls()
    {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls)
    {
        this.photoUrls = photoUrls;
    }

    public String getCardPhoto()
    {
        return cardPhoto;
    }

    public void setCardPhoto(String cardPhoto)
    {
        this.cardPhoto = cardPhoto;
    }

    public String getGiftsName()
    {
        return giftsName;
    }

    public void setGiftsName(String giftsName)
    {
        this.giftsName = giftsName;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(long hotelId)
    {
        this.hotelId = hotelId;
    }

    public long getShopId()
    {
        return shopId;
    }

    public void setShopId(long shopId)
    {
        this.shopId = shopId;
    }
}
