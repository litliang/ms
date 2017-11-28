package com.yzb.card.king.bean.ticket;


import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.ui.app.bean.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：航班详情；
 *
 * @author:gengqiyun
 * @date: 2016/9/26
 */
public class FlightDetailBean extends BaseBean implements Serializable
{
    private float price; //价格；
    private float jijianranyou; //税；
    private String shopName; //代理商名称；
    private String shopId; //代理商id;

    private String depTime; //起飞时间
    private String arrTime; //到达时间

    private String depAirPort; //起始机场信息
    private String arrAirPort; //到达机场信息

    private String departureTerminal; //出发航站楼
    private String arrartureTerminal; //目的航站楼

    private String isFlightNumber; //共享航班号
    private String flightNumber; //航班号
    private String arrDay; //到达日
    private String timeSeres; //出发日
    private int flightId; //航班id
    private String acft; //机型

    private String transitCity;//中转城市
    private String stopCityContext;//停留城市
    private String codeContext;//停留机场名
    private String stopTime;//停留时间

    private List<PlaneTicket> flightList; //航班集合
    private String flightType;//航班类型（2航线，0直飞）

    private String sharedFlightName; //共享航空公司名称
    private String sharedFlightLogo; //共享航空公司logo;
    private String operaCode; //承运方航空公司(共享航空公司);
    private String storeName; //航空公司名称
    private String shopLogo; //航空公司logo

    private String carrierId;
    private String isInter; //国际国内区分
    private String flyingTime; //飞行耗时
    private int isCodeShareairline;
    private List<?> istList;
    private List<?> ptActivity; //活动
    private List<TicketPriceInfoBean> ticketPriceInfo; //机票信息



    public String getDepartureTerminal()
    {
        return super.isStrEmpty(departureTerminal);
    }

    public void setDepartureTerminal(String departureTerminal)
    {
        this.departureTerminal = departureTerminal;
    }

    public String getArrartureTerminal()
    {
        return super.isStrEmpty(arrartureTerminal);
    }

    public void setArrartureTerminal(String arrartureTerminal)
    {
        this.arrartureTerminal = arrartureTerminal;
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
        return super.isStrEmpty(stopTime);
    }

    public void setStopTime(String stopTime)
    {
        this.stopTime = stopTime;
    }

    public List<PlaneTicket> getFlightList()
    {
        return flightList;
    }

    public void setFlightList(List<PlaneTicket> flightList)
    {
        this.flightList = flightList;
    }

    public String getFlightType()
    {
        return super.isStrEmpty(flightType);
    }

    public void setFlightType(String flightType)
    {
        this.flightType = flightType;
    }

    public String getIsFlightNumber()
    {
        return super.isStrEmpty(isFlightNumber);
    }

    public void setIsFlightNumber(String isFlightNumber)
    {
        this.isFlightNumber = isFlightNumber;
    }

    public String getSharedFlightName()
    {
        return super.isStrEmpty(sharedFlightName);
    }

    public void setSharedFlightName(String sharedFlightName)
    {
        this.sharedFlightName = sharedFlightName;
    }

    public String getSharedFlightLogo()
    {
        return super.isStrEmpty(sharedFlightLogo);
    }

    public void setSharedFlightLogo(String sharedFlightLogo)
    {
        this.sharedFlightLogo = sharedFlightLogo;
    }

    public String getTransitCity()
    {
        return super.isStrEmpty(transitCity);
    }

    public void setTransitCity(String transitCity)
    {
        this.transitCity = transitCity;
    }

    public String getStopCityContext()
    {
        return super.isStrEmpty(stopCityContext);
    }

    public void setStopCityContext(String stopCityContext)
    {
        this.stopCityContext = stopCityContext;
    }

    public String getOperaCode()
    {
        return super.isStrEmpty(operaCode);
    }

    public void setOperaCode(String operaCode)
    {
        this.operaCode = operaCode;
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
        return super.isStrEmpty(shopLogo);
    }

    public void setShopLogo(String shopLogo)
    {
        this.shopLogo = shopLogo;
    }

    public String getIsInter()
    {
        return super.isStrEmpty(isInter);
    }

    public void setIsInter(String isInter)
    {
        this.isInter = isInter;
    }

    public List<?> getPtActivity()
    {
        return ptActivity;
    }

    public void setPtActivity(List<?> ptActivity)
    {
        this.ptActivity = ptActivity;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public float getJijianranyou()
    {
        return jijianranyou;
    }

    public void setJijianranyou(float jijianranyou)
    {
        this.jijianranyou = jijianranyou;
    }

    public String getShopName()
    {
        return super.isStrEmpty(shopName);
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getShopId()
    {
        return super.isStrEmpty(shopId);
    }

    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }

    public String getDepTime()
    {
        return super.isStrEmpty(depTime);
    }

    public void setDepTime(String depTime)
    {
        this.depTime = depTime;
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
        return super.isStrEmpty(flightNumber);
    }

    public void setFlightNumber(String flightNumber)
    {
        this.flightNumber = flightNumber;
    }

    public String getArrDay()
    {
        return super.isStrEmpty(arrDay);
    }

    public void setArrDay(String arrDay)
    {
        this.arrDay = arrDay;
    }

    public int getFlightId()
    {
        return flightId;
    }

    public void setFlightId(int flightId)
    {
        this.flightId = flightId;
    }

    public String getDepAirPort()
    {
        return super.isStrEmpty(depAirPort);
    }

    public void setDepAirPort(String depAirPort)
    {
        this.depAirPort = depAirPort;
    }

    public String getAcft()
    {
        return super.isStrEmpty(acft);
    }

    public void setAcft(String acft)
    {
        this.acft = acft;
    }

    public String getTimeSeres()
    {
        return super.isStrEmpty(timeSeres);
    }

    public void setTimeSeres(String timeSeres)
    {
        this.timeSeres = timeSeres;
    }

    public String getCarrierId()
    {
        return super.isStrEmpty(carrierId);
    }

    public void setCarrierId(String carrierId)
    {
        this.carrierId = carrierId;
    }

    public String getFlyingTime()
    {
        return super.isStrEmpty(flyingTime);
    }

    public void setFlyingTime(String flyingTime)
    {
        this.flyingTime = flyingTime;
    }

    public String getArrTime()
    {
        return super.isStrEmpty(arrTime);
    }

    public void setArrTime(String arrTime)
    {
        this.arrTime = arrTime;
    }

    public int getIsCodeShareairline()
    {
        return isCodeShareairline;
    }

    public void setIsCodeShareairline(int isCodeShareairline)
    {
        this.isCodeShareairline = isCodeShareairline;
    }

    public List<?> getIstList()
    {
        return istList;
    }

    public void setIstList(List<?> istList)
    {
        this.istList = istList;
    }

    public List<TicketPriceInfoBean> getTicketPriceInfo()
    {
        return ticketPriceInfo;
    }

    public void setTicketPriceInfo(List<TicketPriceInfoBean> ticketPriceInfo)
    {
        this.ticketPriceInfo = ticketPriceInfo;
    }

    /**
     * 获取中转城市；
     *
     * @return
     */
    public List<String> getTransitCities()
    {
        List<String> list = new ArrayList<>();
        if (flightList != null && flightList.size() > 1)
        {
            for (int i = 0; i < flightList.size() - 1; i++)
            {
                list.add(flightList.get(i).getEndCity());
            }
        }
        return list;
    }

    public static class TicketPriceInfoBean extends BaseBean
    {
        private String agentId;//代理商id；
        private String agentLogo;//代理商logo
        private String flightId;//航班id；
        private String agent;//代理商名称；

        private String ticketPriceId;//票价id；
        private float fareAdult; //成人票价；
        private float fareChd; //儿童票价；
        private float fareBab; //婴儿票价；
        private String qabinName; //仓等名称；

        private String acbIninfo; //余票；

        private float fueltaxAdult; //成人机建燃油费；
        private float fueltaxChd; //儿童机建燃油费；
        private float fueltaxBab; //婴儿机建燃油费；
       // private PachageBean pachage; //机票对应的所有套餐

        /**
         * 优惠信息
         */
        private FavInfoBean disMap;

        public FavInfoBean getDisMap()
        {
            return disMap;
        }

        public void setDisMap(FavInfoBean disMap)
        {
            this.disMap = disMap;
        }

        public String getFlightId()
        {
            return super.isStrEmpty(flightId);
        }

        public void setFlightId(String flightId)
        {
            this.flightId = flightId;
        }

        public String getAgentId()
        {
            return super.isStrEmpty(agentId);
        }

        public String getAgentLogo()
        {
            return super.isStrEmpty(agentLogo);
        }

        public String getQabinName()
        {
            return super.isStrEmpty(qabinName);
        }

        public void setQabinName(String qabinName)
        {
            this.qabinName = qabinName;
        }

        public void setAgentLogo(String agentLogo)
        {
            this.agentLogo = agentLogo;
        }

        public String getTicketPriceId()
        {
            return super.isStrEmpty(ticketPriceId);
        }

        public void setTicketPriceId(String ticketPriceId)
        {
            this.ticketPriceId = ticketPriceId;
        }

        public float getFareAdult()
        {
            return fareAdult;
        }

        public void setFareAdult(float fareAdult)
        {
            this.fareAdult = fareAdult;
        }

        public float getFareChd()
        {
            return fareChd;
        }

        public void setFareChd(float fareChd)
        {
            this.fareChd = fareChd;
        }

        public float getFareBab()
        {
            return fareBab;
        }

        public String getAcbIninfo()
        {
            return super.isStrEmpty(acbIninfo);
        }

        public void setAcbIninfo(String acbIninfo)
        {
            this.acbIninfo = acbIninfo;
        }

        public void setFareBab(float fareBab)
        {
            this.fareBab = fareBab;
        }

        public float getFueltaxAdult()
        {
            return fueltaxAdult;
        }

        public void setFueltaxAdult(float fueltaxAdult)
        {
            this.fueltaxAdult = fueltaxAdult;
        }

        public float getFueltaxChd()
        {
            return fueltaxChd;
        }

        public void setFueltaxChd(float fueltaxChd)
        {
            this.fueltaxChd = fueltaxChd;
        }

        public float getFueltaxBab()
        {
            return fueltaxBab;
        }

        public void setFueltaxBab(float fueltaxBab)
        {
            this.fueltaxBab = fueltaxBab;
        }

        public void setAgentId(String agentId)
        {
            this.agentId = agentId;
        }

        public String getAgent()
        {
            return super.isStrEmpty(agent);
        }

        public void setAgent(String agent)
        {
            this.agent = agent;
        }

//        public PachageBean getPachage()
//        {
//            return pachage;
//        }
//
//        public void setPachage(PachageBean pachage)
//        {
//            this.pachage = pachage;
//        }

//
//        /**
//         * 机票对应的套餐
//         */
//        public static class PachageBean extends BaseBean
//        {
//            private String disStatus; //优惠券状态 1:显示；0:不显示；
//            private String bankStatus; //银行优惠状态 1:显示；0:不显示；
//            private String discountInfo;//折扣
//            private String intStatus;//积分状态  1:显示；0:不显示；
//            private String intName;//积分名称；
//            private String discountName;//折扣名称
//            private String discountStatus;//折扣状态  1:显示；0:不显示；
//            private String discountId;//折扣id
//            private String bouStatus;//红包状态  1:显示；0:不显示；
//
//            /**
//             * 是否有优惠券；
//             *
//             * @return
//             */
//            public boolean hasDiscount()
//            {
//                return "1".equals(disStatus);
//            }
//
//            /**
//             * 是否有红包；
//             *
//             * @return
//             */
//            public boolean hasBonus()
//            {
//                return "1".equals(bouStatus);
//            }
//
//            public String getIntName()
//            {
//                return super.isStrEmpty(intName);
//            }
//
//            public void setIntName(String intName)
//            {
//                this.intName = intName;
//            }
//
//            /**
//             * 是否有积分；
//             *
//             * @return
//             */
//            public boolean hasintegral()
//            {
//                return "1".equals(intStatus);
//            }
//
//            /**
//             * 是否有银行优惠；
//             *
//             * @return
//             */
//            public boolean hasbankCoupon()
//            {
//                return "1".equals(bankStatus);
//            }
//
//            public String getBankStatus()
//            {
//                return super.isStrEmpty(bankStatus);
//            }
//
//            public void setBankStatus(String bankStatus)
//            {
//                this.bankStatus = bankStatus;
//            }
//
//            public String getDisStatus()
//            {
//                return super.isStrEmpty(disStatus);
//            }
//
//            public void setDisStatus(String disStatus)
//            {
//                this.disStatus = disStatus;
//            }
//
//            public String getDiscountInfo()
//            {
//                return super.isStrEmpty(discountInfo);
//            }
//
//            public void setDiscountInfo(String discountInfo)
//            {
//                this.discountInfo = discountInfo;
//            }
//
//            public String getIntStatus()
//            {
//                return super.isStrEmpty(intStatus);
//            }
//
//            public void setIntStatus(String intStatus)
//            {
//                this.intStatus = intStatus;
//            }
//
//            public String getDiscountName()
//            {
//                return super.isStrEmpty(discountName);
//            }
//
//            public void setDiscountName(String discountName)
//            {
//                this.discountName = discountName;
//            }
//
//            public String getDiscountStatus()
//            {
//                return super.isStrEmpty(discountStatus);
//            }
//
//            public void setDiscountStatus(String discountStatus)
//            {
//                this.discountStatus = discountStatus;
//            }
//
//            public String getDiscountId()
//            {
//                return super.isStrEmpty(discountId);
//            }
//
//            public void setDiscountId(String discountId)
//            {
//                this.discountId = discountId;
//            }
//
//            public String getBouStatus()
//            {
//                return super.isStrEmpty(bouStatus);
//            }
//
//            public void setBouStatus(String bouStatus)
//            {
//                this.bouStatus = bouStatus;
//            }
//        }
    }
}
