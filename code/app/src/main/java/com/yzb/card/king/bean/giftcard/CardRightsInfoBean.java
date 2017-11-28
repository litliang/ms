package com.yzb.card.king.bean.giftcard;

import java.io.Serializable;

/**
 * 类  名：卡权益详情
 * 作  者：Li Yubing
 * 日  期：2017/9/11
 * 描  述：
 */
public class CardRightsInfoBean implements Serializable{
    /**
     * 活动id
     */
    private long actId;
    /**
     * 酒店id
     */
    private long hotelId;
    /**
     * 商家id
     */
    private long shopId;
    /**
     * 主卡图片
     */
    private String cardPhoto;
    /**
     * 主卡名称
     */
    private String giftsName;
    /**
     * 有效期限描述
     */
    private String effMonthDesc;
    /**
     * 主卡权益(多个使用####分隔)
     */
    private String giftsPower;
    /**
     * 商家
     */
    private String shopName;
    /**
     * 特惠价
     */
    private int onlinePrice;
    /**
     * 原价
     */
    private int storePrice;
    /**
     * 商品图片
     */
    private String photoUrls;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 酒店自取状态(1可自取；2不可自取；)
     */
    private String selfPick;
    /**
     * 商家邮寄状态(1商家邮寄；2商家不邮寄；)
     */
    private String shopMail;
    /**
     * 条款和使用条件
     */
    private String useCondition;
    /**
     * 使用说明
     */
    private String userIntro;

    public long getActId()
    {
        return actId;
    }

    public void setActId(long actId)
    {
        this.actId = actId;
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

    public String getCardPhoto()
    {
        return cardPhoto;
    }

    public String getPhotoUrls()
    {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls)
    {
        this.photoUrls = photoUrls;
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

    public String getEffMonthDesc()
    {
        return effMonthDesc;
    }

    public void setEffMonthDesc(String effMonthDesc)
    {
        this.effMonthDesc = effMonthDesc;
    }

    public String getGiftsPower()
    {
        return giftsPower;
    }

    public void setGiftsPower(String giftsPower)
    {
        this.giftsPower = giftsPower;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
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

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getSelfPick()
    {
        return selfPick;
    }

    public void setSelfPick(String selfPick)
    {
        this.selfPick = selfPick;
    }

    public String getShopMail()
    {
        return shopMail;
    }

    public void setShopMail(String shopMail)
    {
        this.shopMail = shopMail;
    }

    public String getUseCondition()
    {
        return useCondition;
    }

    public void setUseCondition(String useCondition)
    {
        this.useCondition = useCondition;
    }

    public String getUserIntro()
    {
        return userIntro;
    }

    public void setUserIntro(String userIntro)
    {
        this.userIntro = userIntro;
    }
}
