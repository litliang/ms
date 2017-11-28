package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/25
 * 描  述：
 */
public class BaseHotelOrderInfoBean implements Serializable{

    /**
     *  类型  (1房间、2餐厅、3酒吧、5SPA、6大堂吧、7卡权益、8限时抢购、9健身房、10游泳池)
     */
    private String hotelType;
    /**
     * 酒店id
     */
    private long hotelId;
    /**
     * 酒店名称
     */
    private String hotelName;
    /**
     * 门店id
     */
    private long storeId;
    /**
     * 内部商品id
     */
    private long innerGoodsId;
    /**
     * 商品id
     */
    private long goodsId;
    /**
     * 商品名称  如房间名称、餐厅名称、卡权益名称
     */
    private String goodsName;
    /**
     * 套餐id
     */
    private String policysId;
    /**
     * 套餐名称
     */
    private String policysName;
    /**
     * 酒店地址
     */
    private String hotelAddr;

    /**
     * 经度
     */
    private double lng;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 酒店电话
     */
    private String tel;
    /**
     * 城市id
     */
    private long cityId;

    /**
     * 评价状态(1已评价；0未评价；)
     */
    private  int voteStatus;

    public int getVoteStatus()
    {
        return voteStatus;
    }

    public void setVoteStatus(int voteStatus)
    {
        this.voteStatus = voteStatus;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public long getCityId()
    {
        return cityId;
    }

    public void setCityId(long cityId)
    {
        this.cityId = cityId;
    }

    public String getHotelAddr()
    {
        return hotelAddr;
    }

    public void setHotelAddr(String hotelAddr)
    {
        this.hotelAddr = hotelAddr;
    }

    public String getHotelType()
    {
        return hotelType;
    }

    public void setHotelType(String hotelType)
    {
        this.hotelType = hotelType;
    }

    public long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(long hotelId)
    {
        this.hotelId = hotelId;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
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

    public String getPolicysId()
    {
        return policysId;
    }

    public void setPolicysId(String policysId)
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

    public long getStoreId()
    {
        return storeId;
    }

    public void setStoreId(long storeId)
    {
        this.storeId = storeId;
    }

    public long getInnerGoodsId()
    {
        return innerGoodsId;
    }

    public void setInnerGoodsId(long innerGoodsId)
    {
        this.innerGoodsId = innerGoodsId;
    }
}
