package com.yzb.card.king.bean.ticket;

import com.yzb.card.king.ui.app.bean.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：可改签的订单详细
 *
 * @author:gengqiyun
 * @date: 2016/12/3
 */
public class UpgradeOrderBean implements Cloneable, Serializable
{
    private List<AvResult> avResultList;  //订单航班信息（size大于1，此订单下有字段航班需要改签）
    private int size; //航班选择次数

    public List<AvResult> getAvResultList()
    {
        return avResultList;
    }

    public void setAvResultList(ArrayList<AvResult> avResultList)
    {
        this.avResultList = avResultList;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    @Override
    public UpgradeOrderBean clone() throws CloneNotSupportedException
    {
        return (UpgradeOrderBean) super.clone();
    }

    /**
     * 结果列表；
     */
    public static class AvResult implements Serializable
    {
        private List<TripFlight> tripFlightList; //航班相关信息（航班列表）
        private String depCityName;//出发城市名称
        private String arrCityName;//到达城市名称
        private String arrCityCode;//到达三字码
        private String depCityCode;//出发三字码
        private String orderNo;//订单号
        private boolean isInter;//是否国际航班
        private List<OrignalFlight> changeAirOriDestList;//原航班信息
        private List<String> paxInfoList;//用户要改升的乘机人列表

        public List<TripFlight> getTripFlightList()
        {
            return tripFlightList;
        }

        public void setTripFlightList(List<TripFlight> tripFlightList)
        {
            this.tripFlightList = tripFlightList;
        }

        public String getDepCityName()
        {
            return depCityName;
        }

        public void setDepCityName(String depCityName)
        {
            this.depCityName = depCityName;
        }

        public String getArrCityName()
        {
            return arrCityName;
        }

        public void setArrCityName(String arrCityName)
        {
            this.arrCityName = arrCityName;
        }

        public String getArrCityCode()
        {
            return arrCityCode;
        }

        public void setArrCityCode(String arrCityCode)
        {
            this.arrCityCode = arrCityCode;
        }

        public String getDepCityCode()
        {
            return depCityCode;
        }

        public void setDepCityCode(String depCityCode)
        {
            this.depCityCode = depCityCode;
        }

        public String getOrderNo()
        {
            return orderNo;
        }

        public void setOrderNo(String orderNo)
        {
            this.orderNo = orderNo;
        }

        public boolean getIsInter()
        {
            return isInter;
        }

        public void setIsInter(boolean isInter)
        {
            this.isInter = isInter;
        }

        public List<OrignalFlight> getChangeAirOriDestList()
        {
            return changeAirOriDestList;
        }

        public void setChangeAirOriDestList(List<OrignalFlight> changeAirOriDestList)
        {
            this.changeAirOriDestList = changeAirOriDestList;
        }

        public List<String> getPaxInfoList()
        {
            return paxInfoList;
        }

        public void setPaxInfoList(List<String> paxInfoList)
        {
            this.paxInfoList = paxInfoList;
        }
    }

    /**
     * 航班相关信息（航线列表）
     */
    public static class TripFlight implements Serializable
    {
        private List<FlightBean> flightList;

        public List<FlightBean> getFlightList()
        {
            return flightList;
        }

        public void setFlightList(List<FlightBean> flightList)
        {
            this.flightList = flightList;
        }
    }

    /**
     * 航班列表(如果有中转，则该list长度大于1)
     */
    public static class FlightBean extends BaseBean implements Cloneable, Serializable
    {
        private String dstDate;//到达日期（yyyyMMdd）
        private String orgDate;//出发日期(yyyyMMdd)
        private String orgTime;//出发时间（HH:mm）
        private String dstTime;//到达时间（HH:mm）
        private String orgTerminal;//出发航站楼

        private String depAirPort; //起始机场信息
        private String arrAirPort; //到达机场信息

        private String codeShareCarriar;//共享航班号
        private String airplaneStyle;//机型
        private String dstAirportCode;//到达机场三字码
        private String orgAirportCode;//出发机场三字码
        private String stop;//出发机场三字码
        private String flyIngTime;//飞行时长；(HH:mm)
        private String flightNo;//航班号
        private String carrier;//承运人
        private List<CabinBean> cabinList;//航班舱位信息
        private String dstTerminal;//到达航站楼
        private String codeShare;//是否为共享航班  7:有；8：没有；
        private String meal;//是否有餐食

        //=============以下参数作为辅助使用；便于根据仓等组合不同的航班FlightBean=================
        private String cabinCode;//舱位code
        private float price;//当前舱位成人价格
        private float chdPrice;//儿童价格
        private float infPrice;//婴儿价格
        private float fueltax;//成人税费
        private float fueltaxChd;//儿童税费
        private float fueltaxBad;//婴儿税费

        public String getFlyIngTime()
        {
            return flyIngTime;
        }

        public void setFlyIngTime(String flyIngTime)
        {
            this.flyIngTime = flyIngTime;
        }

        public String getCabinCode()
        {
            return cabinCode;
        }

        public void setCabinCode(String cabinCode)
        {
            this.cabinCode = cabinCode;
        }

        public float getPrice()
        {
            return price;
        }


        public String getArrAirPort()
        {
            return super.isStrEmpty(arrAirPort);
        }

        public void setArrAirPort(String arrAirPort)
        {
            this.arrAirPort = arrAirPort;
        }

        public String getDepAirPort()
        {
            return super.isStrEmpty(depAirPort);
        }

        public void setDepAirPort(String depAirPort)
        {
            this.depAirPort = depAirPort;
        }

        @Override
        public FlightBean clone() throws CloneNotSupportedException
        {
            return (FlightBean) super.clone();
        }

        public void setPrice(float price)
        {
            this.price = price;
        }

        public float getChdPrice()
        {
            return chdPrice;
        }

        public void setChdPrice(float chdPrice)
        {
            this.chdPrice = chdPrice;
        }

        public float getInfPrice()
        {
            return infPrice;
        }

        public void setInfPrice(float infPrice)
        {
            this.infPrice = infPrice;
        }

        public float getFueltax()
        {
            return fueltax;
        }

        public void setFueltax(float fueltax)
        {
            this.fueltax = fueltax;
        }

        public float getFueltaxChd()
        {
            return fueltaxChd;
        }

        public void setFueltaxChd(float fueltaxChd)
        {
            this.fueltaxChd = fueltaxChd;
        }

        public float getFueltaxBad()
        {
            return fueltaxBad;
        }

        public void setFueltaxBad(float fueltaxBad)
        {
            this.fueltaxBad = fueltaxBad;
        }

        public String getDstDate()
        {
            return dstDate;
        }

        public void setDstDate(String dstDate)
        {
            this.dstDate = dstDate;
        }

        public String getOrgDate()
        {
            return orgDate;
        }

        public void setOrgDate(String orgDate)
        {
            this.orgDate = orgDate;
        }

        public String getOrgTime()
        {
            return orgTime;
        }

        public void setOrgTime(String orgTime)
        {
            this.orgTime = orgTime;
        }

        public String getDstTime()
        {
            return dstTime;
        }

        public void setDstTime(String dstTime)
        {
            this.dstTime = dstTime;
        }

        public String getOrgTerminal()
        {
            return super.isStrEmpty(orgTerminal);
        }

        public void setOrgTerminal(String orgTerminal)
        {
            this.orgTerminal = orgTerminal;
        }

        public String getCodeShareCarriar()
        {
            return codeShareCarriar;
        }

        public void setCodeShareCarriar(String codeShareCarriar)
        {
            this.codeShareCarriar = codeShareCarriar;
        }

        public String getAirplaneStyle()
        {
            return airplaneStyle;
        }

        public void setAirplaneStyle(String airplaneStyle)
        {
            this.airplaneStyle = airplaneStyle;
        }

        public String getDstAirportCode()
        {
            return dstAirportCode;
        }

        public void setDstAirportCode(String dstAirportCode)
        {
            this.dstAirportCode = dstAirportCode;
        }

        public String getOrgAirportCode()
        {
            return orgAirportCode;
        }

        public void setOrgAirportCode(String orgAirportCode)
        {
            this.orgAirportCode = orgAirportCode;
        }

        public String getStop()
        {
            return stop;
        }

        public void setStop(String stop)
        {
            this.stop = stop;
        }

        public String getFlightNo()
        {
            return flightNo;
        }

        public void setFlightNo(String flightNo)
        {
            this.flightNo = flightNo;
        }

        public String getCarrier()
        {
            return carrier;
        }

        public void setCarrier(String carrier)
        {
            this.carrier = carrier;
        }

        public List<CabinBean> getCabinList()
        {
            return cabinList;
        }

        public void setCabinList(List<CabinBean> cabinList)
        {
            this.cabinList = cabinList;
        }

        public String getDstTerminal()
        {
            return super.isStrEmpty(dstTerminal);
        }

        public void setDstTerminal(String dstTerminal)
        {
            this.dstTerminal = dstTerminal;
        }

        public String getCodeShare()
        {
            return codeShare;
        }

        public void setCodeShare(String codeShare)
        {
            this.codeShare = codeShare;
        }

        public String getMeal()
        {
            return meal;
        }

        public void setMeal(String meal)
        {
            this.meal = meal;
        }
    }


    /**
     * 航班舱位信息
     */
    public static class CabinBean implements Serializable
    {
        private String cabinCode;//舱位code
        private float price;//当前舱位成人价格
        private float chdPrice;//儿童价格
        private float infPrice;//婴儿价格
        private float fueltax;//成人税费
        private float fueltaxChd;//儿童税费
        private float fueltaxBad;//婴儿税费

        public String getCabinCode()
        {
            return cabinCode;
        }

        public void setCabinCode(String cabinCode)
        {
            this.cabinCode = cabinCode;
        }

        public float getPrice()
        {
            return price;
        }

        public void setPrice(float price)
        {
            this.price = price;
        }

        public float getChdPrice()
        {
            return chdPrice;
        }

        public void setChdPrice(float chdPrice)
        {
            this.chdPrice = chdPrice;
        }

        public float getInfPrice()
        {
            return infPrice;
        }

        public void setInfPrice(float infPrice)
        {
            this.infPrice = infPrice;
        }

        public float getFueltax()
        {
            return fueltax;
        }

        public void setFueltax(float fueltax)
        {
            this.fueltax = fueltax;
        }

        public float getFueltaxChd()
        {
            return fueltaxChd;
        }

        public void setFueltaxChd(float fueltaxChd)
        {
            this.fueltaxChd = fueltaxChd;
        }

        public float getFueltaxBad()
        {
            return fueltaxBad;
        }

        public void setFueltaxBad(float fueltaxBad)
        {
            this.fueltaxBad = fueltaxBad;
        }
    }

    /**
     * 原航班信息；
     */
    public static class OrignalFlight implements Serializable
    {
        private String arrivalCode;//到达机场或城市三字码
        private String codeType;//三字码类型（机场三字码：0; 城市三字码：1）
        private String departDate;//航班日期
        private String departureCode;//出发机场或城市三字码
        private String odNumber;// 0：第一段，1：第二段，依次进行
        private String oriDepartDate;//原始航段出发日期,格式:yyyyMMdd
        private List<FlightBean> changeFlightCabinDtoList;

        public List<FlightBean> getChangeFlightCabinDtoList()
        {
            return changeFlightCabinDtoList;
        }

        public void setChangeFlightCabinDtoList(List<FlightBean> changeFlightCabinDtoList)
        {
            this.changeFlightCabinDtoList = changeFlightCabinDtoList;
        }

        public String getArrivalCode()
        {
            return arrivalCode;
        }

        public void setArrivalCode(String arrivalCode)
        {
            this.arrivalCode = arrivalCode;
        }

        public String getCodeType()
        {
            return codeType;
        }

        public void setCodeType(String codeType)
        {
            this.codeType = codeType;
        }

        public String getDepartDate()
        {
            return departDate;
        }

        public void setDepartDate(String departDate)
        {
            this.departDate = departDate;
        }

        public String getDepartureCode()
        {
            return departureCode;
        }

        public void setDepartureCode(String departureCode)
        {
            this.departureCode = departureCode;
        }

        public String getOdNumber()
        {
            return odNumber;
        }

        public void setOdNumber(String odNumber)
        {
            this.odNumber = odNumber;
        }

        public String getOriDepartDate()
        {
            return oriDepartDate;
        }

        public void setOriDepartDate(String oriDepartDate)
        {
            this.oriDepartDate = oriDepartDate;
        }
    }
}
