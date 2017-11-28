package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * 类  名：商品信息
 * 作  者：Li Yubing
 * 日  期：2017/9/8
 * 描  述：
 */
public class GoodsInfoBean implements Serializable{
    /**
     * 活动id
     */
    private long actId;
    /**
     * 商品id
     */
    private  long goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 套餐id
     */
    private long policysId;
    /**
     * 套餐名称
     */
    private String policysName;
    /**
     * 商家id
     */
    private long shopId;
    /**
     * 商家联系电话
     */
    private String shopTel;
    /**
     * 适用人数说明
     */
    private String limitPersonDesc;
    /**
     * 有效日期说明
     */
    private String effDateDesc;

    private String effMonthDesc;
    /**
     * 主卡名称
     */
    private String giftsName;
    /**
     * 生效日期
     */
    private String effDate;
    /**
     * 1门店自取；2邮寄
     */
    private String receiveType;
    /**
     * 活动类型(1商家活动；2银行活动；)
     */
    private int actType;
    /**
     * 商家名称
     */
    private String shopName;
    /**
     * 门店id
     */
    private long storeId;
    /**
     * 银行id
     */
    private long bankId;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 活动简称
     */
    private String actShortName;
    /**
     * 活动名称
     */
    private String actName;

    public String getActName()
    {
        return actName;
    }

    public void setActName(String actName)
    {
        this.actName = actName;
    }

    public String getActShortName()
    {
        return actShortName;
    }

    public void setActShortName(String actShortName)
    {
        this.actShortName = actShortName;
    }

    public int getActType()
    {
        return actType;
    }

    public void setActType(int actType)
    {
        this.actType = actType;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public long getStoreId()
    {
        return storeId;
    }

    public void setStoreId(long storeId)
    {
        this.storeId = storeId;
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

    public long getActId()
    {
        return actId;
    }

    public void setActId(long actId)
    {
        this.actId = actId;
    }

    public String getReceiveType()
    {
        return receiveType;
    }

    public void setReceiveType(String receiveType)
    {
        this.receiveType = receiveType;
    }

    public String getEffDate()
    {
        return effDate;
    }

    public void setEffDate(String effDate)
    {
        this.effDate = effDate;
    }

    public String getGiftsName()
    {
        return giftsName;
    }

    public void setGiftsName(String giftsName)
    {
        this.giftsName = giftsName;
    }

    public long getGoodsId()
    {
        return goodsId;
    }

    public void setGoodsId(long goodsId)
    {
        this.goodsId = goodsId;
    }

    public String getGoodsName()
    {
        return goodsName;
    }

    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
    }

    public long getPolicysId()
    {
        return policysId;
    }

    public void setPolicysId(long policysId)
    {
        this.policysId = policysId;
    }

    public String getPolicysName()
    {
        return policysName;
    }

    public void setPolicysName(String policysName)
    {
        this.policysName = policysName;
    }

    public long getShopId()
    {
        return shopId;
    }

    public void setShopId(long shopId)
    {
        this.shopId = shopId;
    }

    public String getShopTel()
    {
        return shopTel;
    }

    public void setShopTel(String shopTel)
    {
        this.shopTel = shopTel;
    }

    public String getLimitPersonDesc()
    {
        return limitPersonDesc;
    }

    public void setLimitPersonDesc(String limitPersonDesc)
    {
        this.limitPersonDesc = limitPersonDesc;
    }

    public String getEffMonthDesc()
    {
        return effMonthDesc;
    }

    public void setEffMonthDesc(String effMonthDesc)
    {
        this.effMonthDesc = effMonthDesc;
    }

    public String getEffDateDesc()
    {
        return effDateDesc;
    }

    public void setEffDateDesc(String effDateDesc)
    {
        this.effDateDesc = effDateDesc;
    }
}
