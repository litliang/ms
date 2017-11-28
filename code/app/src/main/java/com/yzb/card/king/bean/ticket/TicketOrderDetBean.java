package com.yzb.card.king.bean.ticket;

import android.text.TextUtils;

import com.yzb.card.king.ui.app.bean.BaseBean;
import com.yzb.card.king.util.DateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能：机票退票详情；
 *
 * @author:gengqiyun
 * @date: 2016/12/1
 */
public class TicketOrderDetBean implements Serializable
{
    private List<OrderInfoBean> orderInfo; //订单信息
    private String flightType; //航班类型  （单程：OW；往返：RT；多段：MT）
    private int orderId; //订单id
    private String ordersNo_hi; //嗨生活订单编码；
    private String orderAmount;//总订单价格
    private int orderStatus;//总订单状态

    public String getOrdersNo_hi()
    {
        return ordersNo_hi;
    }

    public void setOrdersNo_hi(String ordersNo_hi)
    {
        this.ordersNo_hi = ordersNo_hi;
    }

    public List<OrderInfoBean> getOrderInfo()
    {
        return orderInfo;
    }

    public void setOrderInfo(List<OrderInfoBean> orderInfo)
    {
        this.orderInfo = orderInfo;
    }

    public String getFlightType()
    {
        return flightType;
    }

    public void setFlightType(String flightType)
    {
        this.flightType = flightType;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
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

    public static class OrderInfoBean extends BaseBean implements Serializable, Cloneable
    {
        public String airwaysLogo;
        public String airwaysName; //航空公司名称
        public String product;
        public String endCityName;
        public String endTerminal;
        public String endTime;
        public float orderAmount;
        public String carrierName;
        public String flighType;
        public String orderId;
        public String orderNo; //航空订单号（往返时订单号一样则是航班公司组合产品，如不一样是平台组合产品）
        public String orderStatus;
        public String reflightid;
        public String reticketPriceid;
        public long orderTime;
        public int reimbursementStatus;  //报销凭证状态；1：是；0：否；
        public String startCityName;
        public String startTerminal;
        public String startTime;
        public String departureTerminal; //出发地航站楼
        public String arrartureTerminal; //到达地航站楼

        public String stopCityContext; //停留城市
        public String depAirPort; //起始机场名称
        public String codeContext; //停留机场名
        public String arrAirPort; //到达机场名称
        public String storeName; //航空公司名称

        public String toolName;
        public String shippingSpace;
        public String basecabin_code;


        public boolean isSelect; //是否选中；app端使用；

        public String toolNumber; //航班号；
        public String depAirport;
        public String shopLogo; //航空公司logo
        public String arrAirport;
        public String flightNumber; //航班号
        public int sturt; //状态（0：正常 1： 改升   2：退票   3：改签   ，4购票成功，5购票失败）
        public String acft;
        public String flyingTime;
        public String routeType;

        public boolean orignalState; //原 状态，是否选中；app端使用；
        public boolean newState; //新 状态  是否选中；app端使用；

        public String number;//东航航班号

        public String ticketpriceId;//机票价格id

        public List<MyFlight> retFlight;
        public List<TicketsListBean> ticketsList; //新改签乘机人集合
        public List<TicketsListBean> retticketsList; //原乘机人集合
        public List<MyFlight> flightList; //航班集合

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTicketpriceId() {
            return ticketpriceId;
        }

        public void setTicketpriceId(String ticketpriceId) {
            this.ticketpriceId = ticketpriceId;
        }

        public boolean isOrignalState()
        {
            return orignalState;
        }

        public void setOrignalState(boolean orignalState)
        {
            this.orignalState = orignalState;
        }

        public boolean isNewState()
        {
            return newState;
        }

        public void setNewState(boolean newState)
        {
            this.newState = newState;
        }

        public String getCarrierName()
        {
            return carrierName;
        }

        public void setCarrierName(String carrierName)
        {
            this.carrierName = carrierName;
        }

        public List<TicketsListBean> getRetticketsList()
        {
            return retticketsList;
        }

        public void setRetticketsList(List<TicketsListBean> retticketsList)
        {
            this.retticketsList = retticketsList;
        }

        @Override
        public OrderInfoBean clone() throws CloneNotSupportedException
        {
            return (OrderInfoBean) super.clone();
        }

        public String getFlighType()
        {
            return flighType;
        }

        public void setFlighType(String flighType)
        {
            this.flighType = flighType;
        }

        public String getReflightid()
        {
            return reflightid;
        }

        public void setReflightid(String reflightid)
        {
            this.reflightid = reflightid;
        }

        public String getReticketPriceid()
        {
            return reticketPriceid;
        }

        public void setReticketPriceid(String reticketPriceid)
        {
            this.reticketPriceid = reticketPriceid;
        }

        public String getRouteType()
        {
            return routeType;
        }

        public void setRouteType(String routeType)
        {
            this.routeType = routeType;
        }

        public List<String> getTransitCities()
        {
            List<String> list = new ArrayList<>();
            if (flightList != null && flightList.size() > 1)
            {
                for (int i = 0; i < flightList.size() - 1; i++)
                {
                    list.add(flightList.get(i).getArrCity());
                }
            }
            return list;
        }

        /**
         * 有1个中转时获取中转时长；
         *
         * @return
         */
        public String getTransLen()
        {
            if (flightList != null && flightList.size() == 2)
            {
                String startTime = null;
                String endTime = null;
                for (int i = 0; i < flightList.size(); i++)
                {
                    if (i == 0)
                    {
                        startTime = flightList.get(0).getArrTime();
                    } else if (i == 1)
                    {
                        endTime = flightList.get(1).getDepTime();
                    }
                }
                if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime))
                {
                    Date startDate = DateUtil.string2Date(startTime, DateUtil.DATE_FORMAT_HHMM);
                    Date endDate = DateUtil.string2Date(endTime, DateUtil.DATE_FORMAT_HHMM);
                    long l = endDate.getTime() - startDate.getTime();
                    long day = l / (24 * 60 * 60 * 1000);
                    long hour = (l / (60 * 60 * 1000) - day * 24);
                    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                    long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                    StringBuilder sb = new StringBuilder("");

                    sb.append((day > 0 ? (day * 24 + hour) + "h" : hour + "h"));
                    sb.append(min + "m");
                    return sb.toString();
                }
            }
            return null;
        }


        public String getAirwaysLogo()
        {
            return airwaysLogo;
        }

        public List<MyFlight> getRetFlight()
        {
            return retFlight;
        }

        public void setRetFlight(List<MyFlight> retFlight)
        {
            this.retFlight = retFlight;
        }

        public String getDepartureTerminal()
        {
            return departureTerminal;
        }

        public void setDepartureTerminal(String departureTerminal)
        {
            this.departureTerminal = departureTerminal;
        }

        public String getArrartureTerminal()
        {
            return arrartureTerminal;
        }

        public String getStoreName()
        {
            return storeName;
        }

        public void setStoreName(String storeName)
        {
            this.storeName = storeName;
        }

        public void setArrartureTerminal(String arrartureTerminal)
        {
            this.arrartureTerminal = arrartureTerminal;
        }

        public String getShopLogo()
        {
            return shopLogo;
        }

        public void setShopLogo(String shopLogo)
        {
            this.shopLogo = shopLogo;
        }

        public String getFlightNumber()
        {
            return flightNumber;
        }

        public void setFlightNumber(String flightNumber)
        {
            this.flightNumber = flightNumber;
        }

        public String getStopCityContext()
        {
            return stopCityContext;
        }

        public void setStopCityContext(String stopCityContext)
        {
            this.stopCityContext = stopCityContext;
        }

        public String getDepAirPort()
        {
            return depAirPort;
        }

        public void setDepAirPort(String depAirPort)
        {
            this.depAirPort = depAirPort;
        }

        public String getCodeContext()
        {
            return codeContext;
        }

        public void setCodeContext(String codeContext)
        {
            this.codeContext = codeContext;
        }

        public String getArrAirPort()
        {
            return arrAirPort;
        }

        public void setArrAirPort(String arrAirPort)
        {
            this.arrAirPort = arrAirPort;
        }

        public String getShippingSpace()
        {
            return shippingSpace;
        }

        public void setShippingSpace(String shippingSpace)
        {
            this.shippingSpace = shippingSpace;
        }

        public String getBasecabin_code()
        {
            return basecabin_code;
        }

        public void setBasecabin_code(String basecabin_code)
        {
            this.basecabin_code = basecabin_code;
        }

        public int getSturt()
        {
            return sturt;
        }

        public void setSturt(int sturt)
        {
            this.sturt = sturt;
        }

        public void setAirwaysLogo(String airwaysLogo)
        {
            this.airwaysLogo = airwaysLogo;
        }

        public String getAirwaysName()
        {
            return super.isStrEmpty(airwaysName);
        }

        public void setAirwaysName(String airwaysName)
        {
            this.airwaysName = airwaysName;
        }

        public String getProduct()
        {
            return super.isStrEmpty(product);
        }

        public void setProduct(String product)
        {
            this.product = product;
        }

        public String getEndCityName()
        {
            return endCityName;
        }

        public void setEndCityName(String endCityName)
        {
            this.endCityName = endCityName;
        }

        public String getEndTerminal()
        {
            return endTerminal;
        }

        public void setEndTerminal(String endTerminal)
        {
            this.endTerminal = endTerminal;
        }

        public String getEndTime()
        {
            return endTime;
        }

        public void setEndTime(String endTime)
        {
            this.endTime = endTime;
        }

        public float getOrderAmount()
        {
            return orderAmount;
        }

        public void setOrderAmount(float orderAmount)
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

        public String getOrderStatus()
        {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus)
        {
            this.orderStatus = orderStatus;
        }

        public long getOrderTime()
        {
            return orderTime;
        }

        public void setOrderTime(long orderTime)
        {
            this.orderTime = orderTime;
        }

        public int getReimbursementStatus()
        {
            return reimbursementStatus;
        }

        public void setReimbursementStatus(int reimbursementStatus)
        {
            this.reimbursementStatus = reimbursementStatus;
        }

        public String getStartCityName()
        {
            return startCityName;
        }

        public void setStartCityName(String startCityName)
        {
            this.startCityName = startCityName;
        }

        public String getStartTerminal()
        {
            return startTerminal;
        }

        public void setStartTerminal(String startTerminal)
        {
            this.startTerminal = startTerminal;
        }

        public String getStartTime()
        {
            return startTime;
        }

        public void setStartTime(String startTime)
        {
            this.startTime = startTime;
        }

        public String getToolName()
        {
            return toolName;
        }

        public void setToolName(String toolName)
        {
            this.toolName = toolName;
        }

        public boolean isSelect()
        {
            return isSelect;
        }

        public void setIsSelect(boolean isSelect)
        {
            this.isSelect = isSelect;
        }

        public String getToolNumber()
        {
            return toolNumber;
        }

        public void setToolNumber(String toolNumber)
        {
            this.toolNumber = toolNumber;
        }

        public String getDepAirport()
        {
            return depAirport;
        }

        public void setDepAirport(String depAirport)
        {
            this.depAirport = depAirport;
        }

        public String getArrAirport()
        {
            return arrAirport;
        }

        public void setArrAirport(String arrAirport)
        {
            this.arrAirport = arrAirport;
        }

        public String getAcft()
        {
            return acft;
        }

        public void setAcft(String acft)
        {
            this.acft = acft;
        }

        public String getFlyingTime()
        {
            return flyingTime;
        }

        public void setFlyingTime(String flyingTime)
        {
            this.flyingTime = flyingTime;
        }

        public List<TicketsListBean> getTicketsList()
        {
            return ticketsList;
        }

        public void setTicketsList(List<TicketsListBean> ticketsList)
        {
            this.ticketsList = ticketsList;
        }

        public List<MyFlight> getFlightList()
        {
            return flightList;
        }

        public void setFlightList(List<MyFlight> flightList)
        {
            this.flightList = flightList;
        }
    }

    public static class MyFlight extends BaseBean implements Serializable, Cloneable
    {
        public int flightType;//航班类型（2航线，0直飞）
        public String flightId;//航班id
        public long timeSeres;//起飞日期
        public String arrDay;//到达日期
        public String carrierId;//航空公司id；
        public int isInter;//到达日
        public int isCodeShareairline;//
        public String depTime;//起飞时间
        public String depCity;//起飞城市名称
        public String arrCity;//目标城市名称
        public String arrTime;//到达时间
        public String sharedFlights;//共享航空公司
        public String depAirPort;//出发机场
        public String arrAirPort;//到达机场
        public String flightNumber;//航班号
        public String departureTerminal;//出发地航站楼
        public String acbIninfo;//剩余票数
        public String isFlightNumber;//共享航班号
        public String acft;//飞机机型
        public String arrartureTerminal;//到达地航站楼
        public String operaCode;//承运方公司
        public String sharedFlightName; //共享航空公司名称
        public String sharedFlightLogo; //共享航空公司logo;
        public String storeName;//航空公司名称
        public String shopLogo;//航空公司图标
        public String baseCabinCode;//仓位
        public String stopCityContext; //停留城市
        public String codeContext; //停留机场名
        public String stopTime; //停留时间
        public String basecabin_code; // 仓等编码；
        public String flyingTime; //飞行时间
        public String product; //仓等中文名称
        public String sccarrName; //市场方航空公司名称
        public String sjcarrName; //实际承运方航空公司名称

        public String getProduct()
        {
            return product;
        }

        @Override
        public MyFlight clone() throws CloneNotSupportedException
        {
            return (MyFlight) super.clone();
        }

        public void setProduct(String product)
        {
            this.product = product;
        }

        public String getSccarrName()
        {
            return sccarrName;
        }

        public void setSccarrName(String sccarrName)
        {
            this.sccarrName = sccarrName;
        }

        public String getSjcarrName()
        {
            return sjcarrName;
        }

        public void setSjcarrName(String sjcarrName)
        {
            this.sjcarrName = sjcarrName;
        }

        public String getFlyingTime()
        {
            return flyingTime;
        }

        public void setFlyingTime(String flyingTime)
        {
            this.flyingTime = flyingTime;
        }

        public int getFlightType()
        {
            return flightType;
        }

        public String getBasecabin_code()
        {
            return basecabin_code;
        }

        public void setBasecabin_code(String basecabin_code)
        {
            this.basecabin_code = basecabin_code;
        }

        public String getCarrierId()
        {
            return carrierId;
        }

        public void setCarrierId(String carrierId)
        {
            this.carrierId = carrierId;
        }

        public int getIsCodeShareairline()
        {
            return isCodeShareairline;
        }

        public void setIsCodeShareairline(int isCodeShareairline)
        {
            this.isCodeShareairline = isCodeShareairline;
        }

        public int getIsInter()

        {
            return isInter;
        }

        public void setIsInter(int isInter)
        {
            this.isInter = isInter;
        }

        public void setFlightType(int flightType)
        {
            this.flightType = flightType;
        }

        public String getFlightId()
        {
            return flightId;
        }

        public void setFlightId(String flightId)
        {
            this.flightId = flightId;
        }

        public long getTimeSeres()
        {
            return timeSeres;
        }

        public void setTimeSeres(long timeSeres)
        {
            this.timeSeres = timeSeres;
        }

        public String getArrDay()
        {
            return arrDay;
        }

        public void setArrDay(String arrDay)
        {
            this.arrDay = arrDay;
        }

        public String getDepTime()
        {
            return depTime;
        }

        public void setDepTime(String depTime)
        {
            this.depTime = depTime;
        }

        public String getDepCity()
        {
            return depCity;
        }

        public void setDepCity(String depCity)
        {
            this.depCity = depCity;
        }

        public String getArrCity()
        {
            return arrCity;
        }

        public void setArrCity(String arrCity)
        {
            this.arrCity = arrCity;
        }

        public String getArrTime()
        {
            return arrTime;
        }

        public void setArrTime(String arrTime)
        {
            this.arrTime = arrTime;
        }

        public String getSharedFlights()
        {
            return sharedFlights;
        }

        public void setSharedFlights(String sharedFlights)
        {
            this.sharedFlights = sharedFlights;
        }

        public String getDepAirPort()
        {
            return super.isStrEmpty(depAirPort);
        }

        public void setDepAirPort(String depAirPort)
        {
            this.depAirPort = depAirPort;
        }

        public String getArrAirPort()
        {
            return super.isStrEmpty(arrAirPort);
        }

        public void setArrAirPort(String arrAirPort)
        {
            this.arrAirPort = arrAirPort;
        }

        public String getFlightNumber()
        {
            return flightNumber;
        }

        public void setFlightNumber(String flightNumber)
        {
            this.flightNumber = flightNumber;
        }

        public String getDepartureTerminal()
        {
            return super.isStrEmpty(departureTerminal);
        }

        public void setDepartureTerminal(String departureTerminal)
        {
            this.departureTerminal = departureTerminal;
        }

        public String getAcbIninfo()
        {
            return acbIninfo;
        }

        public void setAcbIninfo(String acbIninfo)
        {
            this.acbIninfo = acbIninfo;
        }

        public String getIsFlightNumber()
        {
            return isFlightNumber;
        }

        public void setIsFlightNumber(String isFlightNumber)
        {
            this.isFlightNumber = isFlightNumber;
        }

        public String getAcft()
        {
            return super.isStrEmpty(acft);
        }

        public void setAcft(String acft)
        {
            this.acft = acft;
        }

        public String getArrartureTerminal()
        {
            return super.isStrEmpty(arrartureTerminal);
        }

        public void setArrartureTerminal(String arrartureTerminal)
        {
            this.arrartureTerminal = arrartureTerminal;
        }

        public String getOperaCode()
        {
            return operaCode;
        }

        public void setOperaCode(String operaCode)
        {
            this.operaCode = operaCode;
        }

        public String getSharedFlightName()
        {
            return sharedFlightName;
        }

        public void setSharedFlightName(String sharedFlightName)
        {
            this.sharedFlightName = sharedFlightName;
        }

        public String getSharedFlightLogo()
        {
            return sharedFlightLogo;
        }

        public void setSharedFlightLogo(String sharedFlightLogo)
        {
            this.sharedFlightLogo = sharedFlightLogo;
        }

        public String getStoreName()
        {
            return super.isStrEmpty(storeName);
        }

        public void setStoreName(String storeName)
        {
            this.storeName = storeName;
        }

        public String getShopLogo()
        {
            return shopLogo;
        }

        public void setShopLogo(String shopLogo)
        {
            this.shopLogo = shopLogo;
        }

        public String getBaseCabinCode()
        {
            return baseCabinCode;
        }

        public void setBaseCabinCode(String baseCabinCode)
        {
            this.baseCabinCode = baseCabinCode;
        }

        public String getStopCityContext()
        {
            return stopCityContext;
        }

        public void setStopCityContext(String stopCityContext)
        {
            this.stopCityContext = stopCityContext;
        }

        public String getCodeContext()
        {
            return codeContext;
        }

        public void setCodeContext(String codeContext)
        {
            this.codeContext = codeContext;
        }

        public String getStopTime()
        {
            return stopTime;
        }

        public void setStopTime(String stopTime)
        {
            this.stopTime = stopTime;
        }
    }

    public static class TicketsListBean implements Serializable, Cloneable
    {
        public String guestIDCard; //旅客证件号；
        public String guestName; //旅客名称
        public String idType; //证件类型
        public String sturt; //状态（0：正常 1： 改升   2：退票   3：改签   ，4购票成功，5购票失败）
        public boolean isSelect; //是否选中；app端使用；

        public String getGuestIDCard()
        {
            return guestIDCard;
        }

        @Override
        public TicketsListBean clone() throws CloneNotSupportedException
        {
            return (TicketsListBean) super.clone();
        }

        public void setGuestIDCard(String guestIDCard)
        {
            this.guestIDCard = guestIDCard;
        }

        public String getSturt()
        {
            return sturt;
        }

        public void setSturt(String sturt)
        {
            this.sturt = sturt;
        }

        public String getGuestName()
        {
            return guestName;
        }

        public void setGuestName(String guestName)
        {
            this.guestName = guestName;
        }

        public String getIdType()
        {
            return idType;
        }

        public void setIdType(String idType)
        {
            this.idType = idType;
        }

        public boolean isSelect()
        {
            return isSelect;
        }

        public void setIsSelect(boolean isSelect)
        {
            this.isSelect = isSelect;
        }
    }
}
