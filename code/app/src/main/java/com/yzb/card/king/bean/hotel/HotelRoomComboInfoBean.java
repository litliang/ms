package com.yzb.card.king.bean.hotel;

import com.yzb.card.king.bean.common.FavInfoBean;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/4
 * 描  述：
 */
public class HotelRoomComboInfoBean implements Serializable{
    /**
     * 套餐id
     */
    private long policysId;
    /**
     * 商家id
     */
    private long shopId;
    /**
     * 用餐类型  0不含早、1单早、2双早
     */
    private int mealType;
    /**
     * 用餐类型描述
     */
    private String mealTypeDesc;
    /**
     * 付款方式  1到店付、2在线支付、3担保支付
     */
    private int paymentType;
    /**
     * 取消方式（1、免费取消 2、限时取消 3、付费取消）
     */
    private int cancelType;
    /**
     * 付款方式描述
     */
    private String paymentTypeDesc;
    /**
     * 是否直销  1是；0否；
     */
    private int directStatus;
    /**
     * 商家名称
     */
    private String shopName;
    /**
     * 超值赠送描述
     */
    private String giveContent;
    /**
     * 套餐价格
     */
    private float policysPrice;
    /**
     * 甩房价格
     */
    private int leftPrice;
    /**
     * 返现额
     */
    private float backPrice;
    /**
     * 立减额
     */
    private float minusPrice;
    /**
     * 剩余数量
     */
    private int residualQuantity;
    /**
     * 最新预定说明
     */
    private String lastReserve;
    /**
     * 优惠信息
     */
    private FavInfoBean disMap;
    /**
     * 酒店名称
     */
    private String hotelName;
    /**
     * 酒店id
     */
    private  long hotelId;
    /**
     * 酒店房间对象
     */
    private HotelRoomInfoBean roomInfo;
    /**
     * 入店日期
     */
    private String arrDate;

    /**
     * 离店日期
     */
    private String depDate;

    public int getCancelType()
    {
        return cancelType;
    }

    public void setCancelType(int cancelType)
    {
        this.cancelType = cancelType;
    }

    public float getMinusPrice()
    {
        return minusPrice;
    }

    public void setMinusPrice(float minusPrice)
    {
        this.minusPrice = minusPrice;
    }

    public long getPolicysId()
    {
        return policysId;
    }

    public void setPolicysId(long policysId)
    {
        this.policysId = policysId;
    }

    public long getShopId()
    {
        return shopId;
    }

    public void setShopId(long shopId)
    {
        this.shopId = shopId;
    }

    public int getMealType()
    {
        return mealType;
    }

    public void setMealType(int mealType)
    {
        this.mealType = mealType;
    }

    public String getMealTypeDesc()
    {
        return mealTypeDesc;
    }

    public void setMealTypeDesc(String mealTypeDesc)
    {
        this.mealTypeDesc = mealTypeDesc;
    }

    public int getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(int paymentType)
    {
        this.paymentType = paymentType;
    }

    public String getPaymentTypeDesc()
    {
        return paymentTypeDesc;
    }

    public void setPaymentTypeDesc(String paymentTypeDesc)
    {
        this.paymentTypeDesc = paymentTypeDesc;
    }

    public int getDirectStatus()
    {
        return directStatus;
    }

    public void setDirectStatus(int directStatus)
    {
        this.directStatus = directStatus;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getGiveContent()
    {
        return giveContent;
    }

    public void setGiveContent(String giveContent)
    {
        this.giveContent = giveContent;
    }

    public float getPolicysPrice()
    {
        return policysPrice;
    }

    public void setPolicysPrice(float policysPrice)
    {
        this.policysPrice = policysPrice;
    }

    public int getLeftPrice()
    {
        return leftPrice;
    }

    public void setLeftPrice(int leftPrice)
    {
        this.leftPrice = leftPrice;
    }

    public float getBackPrice()
    {
        return backPrice;
    }

    public void setBackPrice(float backPrice)
    {
        this.backPrice = backPrice;
    }

    public int getResidualQuantity()
    {
        return residualQuantity;
    }

    public void setResidualQuantity(int residualQuantity)
    {
        this.residualQuantity = residualQuantity;
    }

    public String getLastReserve()
    {
        return lastReserve;
    }

    public void setLastReserve(String lastReserve)
    {
        this.lastReserve = lastReserve;
    }

    public FavInfoBean getDisMap()
    {
        return disMap;
    }

    public void setDisMap(FavInfoBean disMap)
    {
        this.disMap = disMap;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
    }

    public String getArrDate()
    {
        return arrDate;
    }

    public void setArrDate(String arrDate)
    {
        this.arrDate = arrDate;
    }

    public String getDepDate()
    {
        return depDate;
    }

    public void setDepDate(String depDate)
    {
        this.depDate = depDate;
    }


    public HotelRoomInfoBean getRoomInfo()
    {
        return roomInfo;
    }

    public void setRoomInfo(HotelRoomInfoBean roomInfo)
    {
        this.roomInfo = roomInfo;
    }

    public long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(long hotelId)
    {
        this.hotelId = hotelId;
    }

    private String goodsType;

    public void setGoodsType(String goodsType)
    {
        this.goodsType = goodsType;
    }

    public String getGoodsType()
    {
        return goodsType;
    }
}
