package com.yzb.card.king.bean.order;

import android.text.TextUtils;


import java.io.Serializable;

/**
 * Created by Timmy on 16/7/21.
 */
public class OrderBean implements Serializable
{
    public static final int ORDER_TYPE_ALL = 0; //所有订单
    public static final int ORDER_TYPE_TRAIN = 1;      //火车票订单
    public static final int ORDER_TYPE_AIRCRAFT = 2;   //飞机票订单
    public static final int ORDER_TYPE_FERRY = 3;      //船票订单
    public static final int ORDER_TYPE_CAR = 4;        //汽车票订单
    public static final int ORDER_TYPE_TAXI = 5;       //叫车订单
    public static final int ORDER_TYPE_TOUR = 6;       //旅游订单
    public static final int ORDER_TYPE_HOTELS = 7;     //酒店订单
    public static final int ORDER_TYPE_LIPING = 8;  //礼品卡
    public static final int ORDER_TYPE_REDPACGE = 9;  //红包
    public static final int ORDER_TYPE_CHONGZHI = 10 ;//充值
    public static final int ORDRE_TYPE_ZHUANZHANG = 11;//转账
    public static final int ORDER_TYPE_TIXIAN = 12;//提现
    public static final int ORDER_TYPE_HUANKUAN = 13;//还款
    public static final int ORDER_TYPE_ROOMDING = 41;//餐厅
    public static final int ORDER_TYPE_JIUBAR = 42;//酒吧
    public static final int ORDER_TYPE_HUICHANG = 43;//会场
    public static final int ORDER_TYPE_SPA = 44;//SPA
    public static final int ORDER_TYPE_DATANGBA = 45;//大堂吧
    public static final int ORDER_TYPE_JIANSHENFANG = 46;//健身房
    public static final int ORDER_TYPE_YOUYONGCHI = 47;//游泳池
    public static final int ORDER_TYPE_KAQUANYI = 48;//卡权益
    public static final int ORDER_TYPE_XIANSHIQIANGGOU = 49;//限时抢购


    //订单状态
    public static final int ORDER_STATUS_CANCEL = 0;   //已取消
    public static final int ORDER_STATUS_NO_PAY = 1;   //待支付
    public static final int ORDER_STATUS_COMPLETE = 2;//已支付
    public static final int ORDER_STATUS_YIWANCHENG = 3;//已成交
    public static final int ORDER_STATUS_YITUIKUAN = 4; //已退款
    public static final int ORDER_STATUS_YIQUEREN = 5;//已确认
    public static final int ORDER_STATUS_QUERENSHIBAI = 6;//确认失败
    public static final int ORDER_STATUS_DAITUIKUAN = 8;//待退款
    public static final int ORDER_STATUS_DAIFANXIAN = 9;//待返现
    public static final int ORDER_STATUS_YIFANXIAN = 10;//已返现
    public static final int ORDER_STATUS_DAIQUEREN = 11;//待确认
    public static final int ORDER_STATUS_YIRUZHU = 12;//已入住
    public static final int ORDER_STATUS_YILIDIAN = 13;//已离店
    public static final int ORDER_STATUS_YUDINGWEIRUZHU = 14;//预订未入住
    public static final int ORDER_STATUS_YIGUOQI = 15;//已过期


    //18交易成功，19,购票失败
    public static final int ORDER_STATUS_TRADE_SUCESS = 18; //交易成功
    public static final int ORDER_STATUS_TRADE_FAIL = 19; //购票失败


    //订单操作状态
    public static final int ORDER_OPERATESTATUS_DELETE = 100;


    /**
     * 订单基本信息
     */

    private String orderId;//订单id

    private String orderNo;//订单金额

    private String orderAmount; //订单金额；

    //订单状态（1待付款；2已付款；3已取消；4删除；5退票；6改签；7退订；8已发货；9已收货；10已退货；
    // 11待发货；12待退款；13已结算；14已出票;15已支付；16交易失败；17已确认）
    private int orderStatus;

    private String orderTime; //订单创建时间；

    private int orderType;  //订单类型(1火车票；2机票；3船票；4汽车票；5叫车；6旅游；7酒店)

    private String refundType; //退款方式（1平台钱包；2原路退回；

    private String payMethod; //付款方式（1钱包余额；2信用卡；3储蓄卡；）

    private String payDetailId; //付款方式明细id

    private String shopIds; //商家id（多个英文逗号分隔）

    private String carrierNames;//商家名称

    private String goodsName;//商品名称

    private String notifyUrl; //回调url；

    private TravelOrderBean travelInfo;//旅游信息

    private BaseHotelOrderInfoBean hotelInfo;//酒店信息

    private GiftcardInfo giftcardInfo; //礼品卡信息

    private ReenvelopeInfo bonusInfo;//红包信息

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

    public String getOrderAmount()
    {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount)
    {
        this.orderAmount = orderAmount;
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

    public int getOrderType()
    {
        return orderType;
    }

    public void setOrderType(int orderType)
    {
        this.orderType = orderType;
    }

    public String getRefundType()
    {
        return refundType;
    }

    public void setRefundType(String refundType)
    {
        this.refundType = refundType;
    }

    public String getPayMethod()
    {
        return payMethod;
    }

    public void setPayMethod(String payMethod)
    {
        this.payMethod = payMethod;
    }

    public String getPayDetailId()
    {
        return payDetailId;
    }

    public void setPayDetailId(String payDetailId)
    {
        this.payDetailId = payDetailId;
    }

    public String getShopIds()
    {
        return shopIds;
    }

    public void setShopIds(String shopIds)
    {
        this.shopIds = shopIds;
    }

    public String getCarrierNames()
    {
        return carrierNames;
    }

    public String getGoodsName()
    {
        return goodsName;
    }

    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
    }

    public void setCarrierNames(String carrierNames)
    {
        this.carrierNames = carrierNames;
    }

    public TravelOrderBean getTravelInfo()
    {
        return travelInfo;
    }

    public void setTravelInfo(TravelOrderBean travelInfo)
    {
        this.travelInfo = travelInfo;
    }

    public BaseHotelOrderInfoBean getHotelInfo()
    {
        return hotelInfo;
    }

    public void setHotelInfo(BaseHotelOrderInfoBean hotelInfo)
    {
        this.hotelInfo = hotelInfo;
    }

    public GiftcardInfo getGiftcardInfo()
    {
        return giftcardInfo;
    }

    public void setGiftcardInfo(GiftcardInfo giftcardInfo)
    {
        this.giftcardInfo = giftcardInfo;
    }

    public ReenvelopeInfo getBonusInfo()
    {
        return bonusInfo;
    }

    public void setBonusInfo(ReenvelopeInfo bonusInfo)
    {
        this.bonusInfo = bonusInfo;
    }

    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }


    /**
     * 红包订单信息
     */
    public static class ReenvelopeInfo
    {
        /**
         * 主题名称
         */
        private String themeName = "Hi Life";
        /**
         * 总数量
         */
        private int totalQuantity;
        /**
         * 祝福语
         */
        private String blessWord;
        /**
         * 领取数量
         */
        private String receiveQuantity;

        public String getThemeName()
        {
            return themeName;
        }

        public void setThemeName(String themeName)
        {
            this.themeName = themeName;
        }

        public int getTotalQuantity()
        {
            return totalQuantity;
        }

        public void setTotalQuantity(int totalQuantity)
        {
            this.totalQuantity = totalQuantity;
        }

        public String getBlessWord()
        {
            return blessWord;
        }

        public void setBlessWord(String blessWord)
        {
            this.blessWord = blessWord;
        }

        public String getReceiveQuantity()
        {
            return receiveQuantity;
        }

        public void setReceiveQuantity(String receiveQuantity)
        {
            this.receiveQuantity = receiveQuantity;
        }
    }

    /**
     * 礼品卡信息；
     */
    public static class GiftcardInfo
    {
        private int totalQuantity;//总份数
        private int receiveQuantity;//
        private String typeName;
        private int category; // 1、实体卡 2、心意卡
        private String productId;
        private String blessWord;
        private String imageCode;
        private String surplusAmount; //剩余金额；

        public String getSurplusAmount()
        {
            return surplusAmount;
        }

        public void setSurplusAmount(String surplusAmount)
        {
            this.surplusAmount = surplusAmount;
        }

        public String getProductId()
        {
            return productId;
        }

        public void setProductId(String productId)
        {
            this.productId = productId;
        }

        public String getBlessWord()
        {
            return blessWord;
        }

        public void setBlessWord(String blessWord)
        {
            this.blessWord = blessWord;
        }

        public String getImageCode()
        {
            return imageCode;
        }

        public void setImageCode(String imageCode)
        {
            this.imageCode = imageCode;
        }

        public int getTotalQuantity()
        {
            return totalQuantity;
        }


        public void setTotalQuantity(int totalQuantity)
        {
            this.totalQuantity = totalQuantity;
        }

        public int getReceiveQuantity()
        {
            return receiveQuantity;
        }

        public void setReceiveQuantity(int receiveQuantity)
        {
            this.receiveQuantity = receiveQuantity;
        }

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }


        public int getCategory()
        {
            return category;
        }

        public void setCategory(int category)
        {
            this.category = category;
        }
    }

    /**
     * 旅游订单
     */
    public class TravelOrderBean implements Serializable
    {
        private String productImageUrl; //旅游产品图片；
        private String productTypeDesc;
        private String agentIds;
        private String productId;
        private String endDate;
        private String startDate;
        private String productName;
        private String goodsIds;

        public String getGoodsIds()
        {
            return goodsIds;
        }

        public void setGoodsIds(String goodsIds)
        {
            this.goodsIds = goodsIds;
        }

        public String getProductImageUrl()
        {
            return productImageUrl;
        }

        public void setProductImageUrl(String productImageUrl)
        {
            this.productImageUrl = productImageUrl;
        }

        public String getProductName()
        {
            return productName;
        }

        public void setProductName(String productName)
        {
            this.productName = productName;
        }

        public String getStartDate()
        {
            return startDate;
        }

        public void setStartDate(String startDate)
        {
            this.startDate = startDate;
        }

        public String getEndDate()
        {
            return endDate;
        }

        public void setEndDate(String endDate)
        {
            this.endDate = endDate;
        }

        public String getProductId()
        {
            return productId;
        }

        public void setProductId(String productId)
        {
            this.productId = productId;
        }

        public String getAgentIds()
        {
            return agentIds;
        }

        public void setAgentIds(String agentIds)
        {
            this.agentIds = agentIds;
        }

        public String getProductTypeDesc()
        {
            return productTypeDesc;
        }

        public void setProductTypeDesc(String productTypeDesc)
        {
            this.productTypeDesc = productTypeDesc;
        }


    }


    /****************************************************************************/


    private String endCityName;
    private String flightType = null;  //航班类型；
    private int detailId;
    private String startCityName;
    private String toolNumber;
    private String carType;
    private String endAddr;
    private String productImageUrl;
    private String hotelImageUrl; //酒店封面图；
    private String startAddr;
    private String product;
    private String basecabinCodes;
    private String endCityNames;
    private String endTimeses;
    private String fareInforses;
    private String flightnumbers;  // 航班号，（多个英文逗号分隔）
    private String routeType;
    private String goodIds; //商品id（多个英文逗号分隔）
    private String startCityNames;
    private String startTimes;
    private String sturts; //状态（0：正常 1： 改升   2：退票   3：改签）
    private String timeSereses;
    private String hotelName;
    private String hotelId;
    private String hotelAddr;
    private String detailCount;
    private String courseName;
    private int hotelType;  //类型（1房间；2餐厅；3酒吧；4会场；5运动；）
    private String floorDesc;
    private int timeLenght;
    private String detailName;
    private String startTime;
    private String endTime;
    private String carrierLogos;


    public String getHotelImageUrl()
    {
        return hotelImageUrl;
    }

    public void setHotelImageUrl(String hotelImageUrl)
    {
        this.hotelImageUrl = hotelImageUrl;
    }

    public String getProductImageUrl()
    {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl)
    {
        this.productImageUrl = productImageUrl;
    }

    public String getFlightType()
    {
        return flightType;
    }

    public void setFlightType(String flightType)
    {
        this.flightType = flightType;
    }

    public String getProduct()
    {
        return product;
    }

    public void setProduct(String product)
    {
        this.product = product;
    }




    public String getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(String hotelId)
    {
        this.hotelId = hotelId;
    }


    public String getDetailName()
    {
        return detailName;
    }

    public void setDetailName(String detailName)
    {
        this.detailName = detailName;
    }

    public int getTimeLenght()
    {
        return timeLenght;
    }

    public void setTimeLenght(int timeLenght)
    {
        this.timeLenght = timeLenght;
    }

    public String getFloorDesc()
    {
        return floorDesc;
    }

    public void setFloorDesc(String floorDesc)
    {
        this.floorDesc = floorDesc;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public int getHotelType()
    {
        return hotelType;
    }

    public void setHotelType(int hotelType)
    {
        this.hotelType = hotelType;
    }

    public String getDetailCount()
    {
        return detailCount;
    }

    public void setDetailCount(String detailCount)
    {
        this.detailCount = detailCount;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
    }

    public String getHotelAddr()
    {
        return hotelAddr;
    }

    public void setHotelAddr(String hotelAddr)
    {
        this.hotelAddr = hotelAddr;
    }

    public int getDetailId()
    {
        return detailId;
    }

    public void setDetailId(int detailId)
    {
        this.detailId = detailId;
    }

    public String getGoodIds()
    {
        return goodIds;
    }

    public void setGoodIds(String goodIds)
    {
        this.goodIds = goodIds;
    }

    public String getCarType()
    {
        return carType;
    }

    public void setCarType(String carType)
    {
        this.carType = carType;
    }

    public String getEndAddr()
    {
        return endAddr;
    }

    public void setEndAddr(String endAddr)
    {
        this.endAddr = endAddr;
    }

    public String getStartAddr()
    {
        return startAddr;
    }

    public void setStartAddr(String startAddr)
    {
        this.startAddr = startAddr;
    }

    public String getEndCityName()
    {
        return endCityName;
    }

    public void setEndCityName(String endCityName)
    {
        this.endCityName = endCityName;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getStartCityName()
    {
        return startCityName;
    }

    public void setStartCityName(String startCityName)
    {
        this.startCityName = startCityName;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getToolNumber()
    {
        return toolNumber;
    }

    public void setToolNumber(String toolNumber)
    {
        this.toolNumber = toolNumber;
    }

    public String getBasecabinCodes()
    {
        return basecabinCodes;
    }

    public void setBasecabinCodes(String basecabinCodes)
    {
        this.basecabinCodes = basecabinCodes;
    }

    public String getCarrierLogos()
    {
        return carrierLogos;
    }

    public void setCarrierLogos(String carrierLogos)
    {
        this.carrierLogos = carrierLogos;
    }

    public String getEndCityNames()
    {
        return endCityNames;
    }

    public void setEndCityNames(String endCityNames)
    {
        this.endCityNames = endCityNames;
    }

    public String getEndTimeses()
    {
        return endTimeses;
    }

    public void setEndTimeses(String endTimeses)
    {
        this.endTimeses = endTimeses;
    }

    public String getFareInforses()
    {
        return TextUtils.isEmpty(fareInforses) ? "0" : fareInforses;
    }

    public void setFareInforses(String fareInforses)
    {
        this.fareInforses = fareInforses;
    }

    public String getFlightnumbers()
    {
        return flightnumbers;
    }

    public void setFlightnumbers(String flightnumbers)
    {
        this.flightnumbers = flightnumbers;
    }

    public String getRouteType()
    {
        return routeType;
    }

    public void setRouteType(String routeType)
    {
        this.routeType = routeType;
    }

    public String getStartCityNames()
    {
        return startCityNames;
    }

    public void setStartCityNames(String startCityNames)
    {
        this.startCityNames = startCityNames;
    }

    public String getStartTimes()
    {
        return startTimes;
    }

    public void setStartTimes(String startTimes)
    {
        this.startTimes = startTimes;
    }

    public String getSturts()
    {
        return sturts;
    }

    public void setSturts(String sturts)
    {
        this.sturts = sturts;
    }

    public String getTimeSereses()
    {
        return timeSereses;
    }

    public void setTimeSereses(String timeSereses)
    {
        this.timeSereses = timeSereses;
    }
}
