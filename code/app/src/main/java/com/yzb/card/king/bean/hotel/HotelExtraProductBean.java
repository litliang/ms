package com.yzb.card.king.bean.hotel;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/5
 * 描  述：包括 餐厅、3酒吧、5SPA、6大堂吧、9健身房、10游泳池
 */
public class HotelExtraProductBean implements Serializable {

    /**
     * 商品id
     */
    private long goodsId;
    /**
     * 商品名称
     *
     */
    private String goodsName;
    /**
     * 餐厅类型
     */
    private String restaurantTypeDesc;
    /**
     * 营业开始时间
     */
    private String businessStartHours;
    /**
     * 营业结束时间
     */
    private String businessEndHours;
    /**
     * 最低价格
     */
    private int minPrice;
    /**
     * 图片
     */
    private String photoUrls;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 楼层描述
     */
    private String floorDesc;
    /**
     * 商品介绍
     */
    private String goodsIntro;
    /**
     * 是否有包厢
     */
    private String balconyStatus;

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

    public String getRestaurantTypeDesc()
    {
        return restaurantTypeDesc;
    }

    public void setRestaurantTypeDesc(String restaurantTypeDesc)
    {
        this.restaurantTypeDesc = restaurantTypeDesc;
    }

    public String getBusinessStartHours()
    {
        return businessStartHours;
    }

    public void setBusinessStartHours(String businessStartHours)
    {
        this.businessStartHours = businessStartHours;
    }

    public String getBusinessEndHours()
    {
        return businessEndHours;
    }

    public void setBusinessEndHours(String businessEndHours)
    {
        this.businessEndHours = businessEndHours;
    }

    public int getMinPrice()
    {
        return minPrice;
    }

    public void setMinPrice(int minPrice)
    {
        this.minPrice = minPrice;
    }

    public String getPhotoUrls()
    {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls)
    {
        this.photoUrls = photoUrls;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getFloorDesc()
    {
        return floorDesc;
    }

    public void setFloorDesc(String floorDesc)
    {
        this.floorDesc = floorDesc;
    }

    public String getGoodsIntro()
    {
        return goodsIntro;
    }

    public void setGoodsIntro(String goodsIntro)
    {
        this.goodsIntro = goodsIntro;
    }

    public String getBalconyStatus()
    {
        return balconyStatus;
    }

    public void setBalconyStatus(String balconyStatus)
    {
        this.balconyStatus = balconyStatus;
    }
}
