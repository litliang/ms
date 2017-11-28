package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * Created by Timmy on 16/7/25.
 */
public class HotelOrderInfoBean implements Serializable
{
    private long arriveTime;
    private long departureTime;
    private String hotelAddr;//酒店地址
    private String hotelName;//酒店名称
    private String hotelStatus;
    private double lat;
    private double lng;
    private long orderAmount;
    private String orderId;
    private String orderNo;
    private int orderStatus;
    private String orderTime;
    private String toolName;
    private String guestName;
    private String toolNumber;
    private String roomName;
    private String bedType;
    private String tel;
    private String hotelId;//酒店id
    private int breakfastStatus;
    private String cityId;
    private String barCode;
    private String notifyUrl;//回调url；
    private int hotelType;//类型（1房间；2餐厅；3酒吧；4会场；5运动；）
    private String floorDesc;//楼层描述
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String detailCount;//订购数量
    private String carrierNames;//代理商名称
    private String timeLenght;//时长
    private String shopIds;//代理商 商家id
    private String goodIds;//商品id
    private String detailName;//类型对应名称
    private String hotelImageUrl;//酒店图片
    private String goodsId;//商品id
    private String detailId;//类型对应id
    private String shopId;//商家id
    private String shopType;//商家类型
    private String shopLogo;//商家logo
    private  String shopName;//商家名称
    private String goodsCode;//商品code


    public String getGoodsId()
    {
        return goodsId;
    }

    public void setGoodsId(String goodsId)
    {
        this.goodsId = goodsId;
    }

    public String getDetailId()
    {
        return detailId;
    }

    public void setDetailId(String detailId)
    {
        this.detailId = detailId;
    }

    public String getShopId()
    {
        return shopId;
    }

    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }

    public String getShopType()
    {
        return shopType;
    }

    public void setShopType(String shopType)
    {
        this.shopType = shopType;
    }

    public String getShopLogo()
    {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo)
    {
        this.shopLogo = shopLogo;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getGoodsCode()
    {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode)
    {
        this.goodsCode = goodsCode;
    }

    /**
     * 商品内部编码
     */
    private long goodsInnerCode;

    public long getGoodsInnerCode()
    {
        return goodsInnerCode;
    }

    public void setGoodsInnerCode(long goodsInnerCode)
    {
        this.goodsInnerCode = goodsInnerCode;
    }

    public String getDetailName()
    {
        return detailName;
    }

    public void setDetailName(String detailName)
    {
        this.detailName = detailName;
    }

    public String getHotelImageUrl()
    {
        return hotelImageUrl;
    }

    public void setHotelImageUrl(String hotelImageUrl)
    {
        this.hotelImageUrl = hotelImageUrl;
    }

    public String getGoodIds()
    {
        return goodIds;
    }

    public void setGoodIds(String goodIds)
    {
        this.goodIds = goodIds;
    }

    public String getShopIds()
    {
        return shopIds;
    }

    public void setShopIds(String shopIds)
    {
        this.shopIds = shopIds;
    }

    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }

    public int getHotelType()
    {
        return hotelType;
    }

    public void setHotelType(int hotelType)
    {
        this.hotelType = hotelType;
    }


    public String getFloorDesc()
    {
        return floorDesc;
    }

    public void setFloorDesc(String floorDesc)
    {
        this.floorDesc = floorDesc;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getDetailCount()
    {
        return detailCount;
    }

    public void setDetailCount(String detailCount)
    {
        this.detailCount = detailCount;
    }

    public String getCarrierNames()
    {
        return carrierNames;
    }

    public void setCarrierNames(String carrierNames)
    {
        this.carrierNames = carrierNames;
    }

    public String getTimeLenght()
    {
        return timeLenght;
    }

    public void setTimeLenght(String timeLenght)
    {
        this.timeLenght = timeLenght;
    }

    // 酒店类型：hoteltype
    public static final int HOTELTYPE_ROOM = 1;//房间
    public static final int HOTELTYPE_CANTING = 2;//餐厅
    public static final int HOTELTYPE_JIUBA = 3;//酒吧
    public static final int HOTELTYPE_HUICHANG = 4;//会场
    public static final int HOTELTYPE_YUNDONG = 5;//运动
    public static final int HOTELTYPE_CHABA = 6;//茶吧
    public static final int HOTELTYPE_DALIBAO = 7;//大礼包
    public static final int HOTELTYPE_SPA = 8;//SPA
    public static final int HOTELTYPE_JIANSHENFANG = 9;//健身房

    //早餐有无：breakfastStatus
    public static final int BREAKFAST_YES = 1;
    public static final int BREAKFAST_NO = 0;

    public String getBarCode()
    {
        return barCode;
    }

    public void setBarCode(String barCode)
    {
        this.barCode = barCode;
    }

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public int getBreakfastStatus()
    {
        return breakfastStatus;
    }

    public void setBreakfastStatus(int breakfastStatus)
    {
        this.breakfastStatus = breakfastStatus;
    }

    public long getDepartureTime()
    {
        return departureTime;
    }

    public void setDepartureTime(long departureTime)
    {
        this.departureTime = departureTime;
    }

    public String getGuestName()
    {
        return guestName;
    }

    public void setGuestName(String guestName)
    {
        this.guestName = guestName;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    public String getBedType()
    {
        return bedType;
    }

    public void setBedType(String bedType)
    {
        this.bedType = bedType;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(String hotelId)
    {
        this.hotelId = hotelId;
    }


    public long getArriveTime()
    {
        return arriveTime;
    }

    public void setArriveTime(long arriveTime)
    {
        this.arriveTime = arriveTime;
    }

    public String getHotelAddr()
    {
        return hotelAddr;
    }

    public void setHotelAddr(String hotelAddr)
    {
        this.hotelAddr = hotelAddr;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
    }

    public String getHotelStatus()
    {
        return hotelStatus;
    }

    public void setHotelStatus(String hotelStatus)
    {
        this.hotelStatus = hotelStatus;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    public long getOrderAmount()
    {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount)
    {
        this.orderAmount = orderAmount;
    }

    public String getOrderId()
    {
        return orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public int getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(String orderTime)
    {
        this.orderTime = orderTime;
    }

    public String getToolName()
    {
        return toolName;
    }

    public void setToolName(String toolName)
    {
        this.toolName = toolName;
    }

    public String getToolNumber()
    {
        return toolNumber;
    }

    public void setToolNumber(String toolNumber)
    {
        this.toolNumber = toolNumber;
    }
}
