package com.yzb.card.king.bean.ticket;

import java.io.Serializable;
import java.util.List;

/**
 * 类名： TicketDetailsBean
 * 作者： Lei Chao.
 * 日期： 2016-10-15
 * 描述： 机票详情bean
 */
public class TicketDetailsBean implements Serializable
{
    private List<OrderInfoBean> orderInfo; //订单信息
    private List<TicketsListBean> ticketsList; //机票信息

    private String flightType; //航班类型
    private String orderId; //订单id

    public List<OrderInfoBean> getOrderInfo()
    {
        return orderInfo;
    }

    public String getFlightType()
    {
        return flightType;
    }

    public void setFlightType(String flightType)
    {
        this.flightType = flightType;
    }

    public String getOrderId()
    {
        return orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public void setOrderInfo(List<OrderInfoBean> orderInfo)
    {
        this.orderInfo = orderInfo;
    }

    public List<TicketsListBean> getTicketsList()
    {
        return ticketsList;
    }

    public void setTicketsList(List<TicketsListBean> ticketsList)
    {
        this.ticketsList = ticketsList;
    }

    public static class OrderInfoBean
    {
        private String airwaysLogo;
        private String airwaysName;
        private String product;
        private String endCityName;
        private String endTerminal;
        private String endTime;
        private double orderAmount;
        private String orderId;
        private String orderNo; //航空订单号（往返时订单号一样则是航班公司组合产品，如不一样是平台组合产品）
        private String orderStatus;
        private long orderTime;
        private int reimbursementStatus;
        private String startCityName;
        private String startTerminal;
        private String startTime;
        private String toolName;

        private  boolean isSelect; //是否选中；app端使用；

        private String toolNumber;
        private String depAirport;
        private String arrAirport;
        private String acft;
        private String flyingTime;
        private List<Flight> flightList; //航班集合
        private List<Flight> retFlight; //改签前航班

        public List<Flight> getRetFlight()
        {
            return retFlight;
        }

        public boolean isSelect()
        {
            return isSelect;
        }

        public void setIsSelect(boolean isSelect)
        {
            this.isSelect = isSelect;
        }

        public void setRetFlight(List<Flight> retFlight)
        {
            this.retFlight = retFlight;
        }

        public List<Flight> getFlightList()
        {
            return flightList;
        }

        public void setFlightList(List<Flight> flightList)
        {
            this.flightList = flightList;
        }

        public static class Flight
        {
            private int flightType;//航班类型（2航线，0直飞）
            private String flightId;//航班id
            private String timeSeres;//出发日期
            private String arrDay;//到达日
            private String depTime;//起飞时间
            private String depCity;//起飞城市名称
            private String arrCity;//目标城市名称
            private String arrTime;//到达时间
            private String sharedFlights;//共享航空公司
            private String depAirPort;//出发机场
            private String arrAirPort;//到达机场
            private String flightNumber;//航班号
            private String flyIngTime;//飞行耗时
            private String departureTerminal;//出发地航站楼
            private String acbIninfo;//剩余票数
            private String isFlightNumber;//共享航班号
            private String acft;//飞机机型
            private String arrartureTerminal;//到达地航站楼
            private String operaCode;//承运方公司
            private String sharedFlightName; //共享航空公司名称
            private String sharedFlightLogo; //共享航空公司logo;
            private String storeName;//航空公司名称
            private String shopLogo;//航空公司图标
            private String baseCabinCode;//仓位
            private String stopCityContext; //停留城市
            private String codeContext; //停留机场名
            private String stopTime; //停留时间

            public int getFlightType()
            {
                return flightType;
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

            public String getTimeSeres()
            {
                return timeSeres;
            }

            public void setTimeSeres(String timeSeres)
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
                return depAirPort;
            }

            public void setDepAirPort(String depAirPort)
            {
                this.depAirPort = depAirPort;
            }

            public String getArrAirPort()
            {
                return arrAirPort;
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

            public String getFlyIngTime()
            {
                return flyIngTime;
            }

            public void setFlyIngTime(String flyIngTime)
            {
                this.flyIngTime = flyIngTime;
            }

            public String getDepartureTerminal()
            {
                return departureTerminal;
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
                return acft;
            }

            public void setAcft(String acft)
            {
                this.acft = acft;
            }

            public String getArrartureTerminal()
            {
                return arrartureTerminal;
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
                return storeName;
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

        public String getFlyingTime()
        {
            return flyingTime;
        }

        public void setFlyingTime(String flyingTime)
        {
            this.flyingTime = flyingTime;
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

        public String getAirwaysLogo()
        {
            return airwaysLogo;
        }

        public void setAirwaysLogo(String airwaysLogo)
        {
            this.airwaysLogo = airwaysLogo;
        }

        public String getAirwaysName()
        {
            return airwaysName;
        }

        public void setAirwaysName(String airwaysName)
        {
            this.airwaysName = airwaysName;
        }

        public String getProduct()
        {
            return product;
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

        public double getOrderAmount()
        {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount)
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

        public String getToolNumber()
        {
            return toolNumber;
        }

        public void setToolNumber(String toolNumber)
        {
            this.toolNumber = toolNumber;
        }
    }

    public static class TicketsListBean
    {
        private String guestIDCard; //旅客证件号；
        private String guestName; //旅客名称
        private String idType; //证件类型

        public String getGuestIDCard()
        {
            return guestIDCard;
        }

        public void setGuestIDCard(String guestIDCard)
        {
            this.guestIDCard = guestIDCard;
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
    }
}