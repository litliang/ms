package com.yzb.card.king.bean.giftcard;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：限时抢购
 * 作  者：Li Yubing
 * 日  期：2017/9/15
 * 描  述：
 */
public class FlashSaleInfoBean implements Serializable{
    /**
     * 活动类型(1商家活动；2银行活动；)
     */
    private int actType;
    /**
     * 活动id
     */
    private long actId;
    /**
     * 活动名称
     */
    private String actName;
    /**
     * 发放商家id
     */
    private long shopId;
    /**
     * 酒店id
     */
    private long hotelId;
    /**
     * 发放商家名称
     */
    private String shopName;
    /**
     * 发放商家联系电话
     */
    private String tel;
    /**
     * 银行id
     */
    private long bankId;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 限制卡种
     */
    private String limitArchives;
    /**
     * 限制卡等
     */
    private String limitRank;
    /**
     * 银行卡类型
     */
    private String bankType;
    /**
     * 有效期限描述
     */
    private String effDateDesc;
    /**
     * 剩余数量
     */
    private int residualQuantity;
    /**
     * 抢购截至时间(yyyy-MM-dd HH:mm:ss)
     */
    private String endTime;
    /**
     * 特惠价
     */
    private float onlinePrice;
    /**
     * 原价
     */
    private float storePrice;
    /**
     * 图片(使用,分隔)
     */
    private String photoUrls;
    /**
     * 礼遇
     */
    private String privilege;
    /**
     * 使用说明
     */
    private String useIntro;
    /**
     * 服务内容
     */
    private List<GiftsIncrementBean> giftsIncrementBeenList;

    public List<GiftsIncrementBean> getGiftsIncrementBeenList()
    {
        return giftsIncrementBeenList;
    }

    public void setGiftsIncrementBeenList(List<GiftsIncrementBean> giftsIncrementBeenList)
    {
        this.giftsIncrementBeenList = giftsIncrementBeenList;
    }

    public long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(long hotelId)
    {
        this.hotelId = hotelId;
    }

    public int getActType()
    {
        return actType;
    }

    public void setActType(int actType)
    {
        this.actType = actType;
    }

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

    public long getShopId()
    {
        return shopId;
    }

    public void setShopId(long shopId)
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

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public long getBankId()
    {
        return bankId;
    }

    public void setBankId(long bankId)
    {
        this.bankId = bankId;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getLimitArchives()
    {
        return limitArchives;
    }

    public void setLimitArchives(String limitArchives)
    {
        this.limitArchives = limitArchives;
    }

    public String getLimitRank()
    {
        return limitRank;
    }

    public void setLimitRank(String limitRank)
    {
        this.limitRank = limitRank;
    }

    public String getBankType()
    {
        return bankType;
    }

    public void setBankType(String bankType)
    {
        this.bankType = bankType;
    }

    public String getEffDateDesc()
    {
        return effDateDesc;
    }

    public void setEffDateDesc(String effDateDesc)
    {
        this.effDateDesc = effDateDesc;
    }

    public int getResidualQuantity()
    {
        return residualQuantity;
    }

    public void setResidualQuantity(int residualQuantity)
    {
        this.residualQuantity = residualQuantity;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public float getOnlinePrice()
    {
        return onlinePrice;
    }

    public void setOnlinePrice(float onlinePrice)
    {
        this.onlinePrice = onlinePrice;
    }

    public float getStorePrice()
    {
        return storePrice;
    }

    public void setStorePrice(float storePrice)
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

    public String getPrivilege()
    {
        return privilege;
    }

    public void setPrivilege(String privilege)
    {
        this.privilege = privilege;
    }

    public String getUseIntro()
    {
        return useIntro;
    }

    public void setUseIntro(String useIntro)
    {
        this.useIntro = useIntro;
    }
}
