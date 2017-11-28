package com.yzb.card.king.bean.ticket;

import android.text.TextUtils;

import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.ui.app.bean.BaseBean;
import com.yzb.card.king.util.DateUtil;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述：机票数据
 * 作者：殷曙光
 * 日期：2016/9/29 12:57
 */
@Table(name = "PlaneTicket")
public class PlaneTicket extends BaseBean implements Serializable
{
    @Column(name = "flightType")
    private int flightType;//航班类型（2航线，0直飞）
    @Column(name = "flightId")
    private String flightId;//航班id
    @Column(name = "timeSeres")
    private String timeSeres;//出发日期
    @Column(name = "arrDay")
    private String arrDay;//到达日
    @Column(name = "depTime")
    private String depTime;//起飞时间
    @Column(name = "arrTime")
    private String arrTime;//到达时间
    @Column(name = "sharedFlights")
    private String sharedFlights;//共享航空公司
    @Column(name = "depAirPort")
    private String depAirPort;//出发机场
    @Column(name = "arrAirPort")
    private String arrAirPort;//到达机场
    @Column(name = "flightNumber")
    private String flightNumber;//航班号
    @Column(name = "flyIngTime")
    private String flyIngTime;//飞行耗时
    @Column(name = "departureTerminal")
    private String departureTerminal;//出发地航站楼
    @Column(name = "acbIninfo")
    private String acbIninfo;//剩余票数
    @Column(name = "isFlightNumber")
    private String isFlightNumber;//共享航班号
    @Column(name = "acft")
    private String acft;//飞机机型
    @Column(name = "arrartureTerminal")
    private String arrartureTerminal;//到达地航站楼
    @Column(name = "operaCode")
    private String operaCode;//承运方公司
    @Column(name = "sharedFlightName")
    private String sharedFlightName; //共享航空公司名称
    @Column(name = "sharedFlightLogo")
    private String sharedFlightLogo; //共享航空公司logo;
    @Column(name = "storeName")
    private String storeName;//航空公司名称
    @Column(name = "shopLogo")
    private String shopLogo;//航空公司图标
    @Column(name = "baseCabinCode")
    private String baseCabinCode;//仓位
    @Column(name = "fareAdult")
    private float fareAdult;//成人票价
    @Column(name = "fareChd")
    private float fareChd;//儿童票价
    @Column(name = "fareBab")
    private float fareBab;//婴儿票价
    @Column(name = "stopCityContext")
    private String stopCityContext;
    @Column(name = "codeContext")
    private String codeContext;
    @Column(name = "stopTime")
    private String stopTime;
    @Column(name = "state")
    private String state = "0"; //0：正常 1： 改升   2：退票   3：改签

    /**
     * 优惠
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

    private List<PlaneTicket> flightList;
    /**
     * 航线类型；提交订单时使用；非接口返回；
     * 单程：1；
     * 往返：去:1,返：2；
     * 多程：全部为1
     */
    @Column(name = "routeType")
    private String routeType;
    @Column(name = "agentId")
    private String agentId;// 代理商id；
    /**
     * 成人，儿童，婴儿 各自的机建燃油费（包含税费）,目前机建燃油费==税费；
     */
    @Column(name = "fueltax")
    private float fueltax; //成人机建燃油费；
    @Column(name = "fueltaxChd")
    private float fueltaxChd; //儿童机建燃油费；
    @Column(name = "fueltaxBab")
    private float fueltaxBab; //婴儿机建燃油费；
    @Column(name = "ticketPriceId")
    private String ticketPriceId; //票价id；

    private List<BankInfo> istList;//银行优惠
    @Column(name = "startCity")
    private String startCity;
    @Column(name = "endCity")
    private String endCity;
    @Column(name = "seatId")
    private String seatId; //座位号id

    @Column(name = "depCity")
    private String depCity; //起飞城市名称
    @Column(name = "arrCity")
    private String arrCity; //目标城市名称
    private boolean shopStatus;//商家积分状态
    private boolean platformStatus;//平台积分状态

    public int getFlightType()
    {
        if (("" + flightType).equals(""))
        {
            return 0;
        }
        return flightType;
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

    /**
     * 是否是国际航班；
     *
     * @return
     */
    public boolean isNationalFlight()
    {
        //是否有国际城市
        if (endCity != null && startCity != null)
        {
            if ("2".equals(endCity) || "2".equals(startCity))
            {
                return true;
            }
        }
        return false;
    }

    public void setFlightType(int flightType)
    {
        this.flightType = flightType;
    }

    public String getTicketPriceId()
    {
        return super.isStrEmpty(ticketPriceId);
    }

    public String getRouteType()
    {
        return super.isStrEmpty(routeType);
    }

    public void setRouteType(String routeType)
    {
        this.routeType = routeType;
    }

    public void setTicketPriceId(String ticketPriceId)
    {
        this.ticketPriceId = ticketPriceId;
    }

    public String getStartCity()
    {
        return super.isStrEmpty(startCity);
    }

    public void setStartCity(String startCity)
    {
        this.startCity = startCity;
    }

    public String getEndCity()
    {
        return super.isStrEmpty(endCity);
    }

    public void setEndCity(String endCity)
    {
        this.endCity = endCity;
    }

    public String getSeatId()
    {
        return seatId;
    }

    public void setSeatId(String seatId)
    {
        this.seatId = seatId;
    }

    public String getFlightId()
    {
        return flightId;
    }

    public String getAgentId()
    {
        return agentId;
    }

    public void setAgentId(String agentId)
    {
        this.agentId = agentId;
    }

    public void setFlightId(String flightId)
    {
        this.flightId = flightId;
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

    public String getState()
    {
        return super.isStrEmpty(state);
    }

    public void setState(String state)
    {
        this.state = state;
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

    public String getStopCityContext()
    {
        return super.isStrEmpty(stopCityContext);
    }

    public void setStopCityContext(String stopCityContext)
    {
        this.stopCityContext = stopCityContext;
    }

    public String getCodeContext()
    {
        return super.isStrEmpty(codeContext);
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

    public String getTimeSeres()
    {
        return super.isStrEmpty(timeSeres);
    }

    public void setTimeSeres(String timeSeres)
    {
        this.timeSeres = timeSeres;
    }

    public String getArrDay()
    {
        return super.isStrEmpty(arrDay);
    }

    public void setArrDay(String arrDay)
    {
        this.arrDay = arrDay;
    }

    public String getDepTime()
    {
        return super.isStrEmpty(depTime);
    }

    public String getSharedFlightName()
    {
        return super.isStrEmpty(sharedFlightName);
    }

    public void setSharedFlightName(String sharedFlightName)
    {
        this.sharedFlightName = sharedFlightName;
    }

    public String getIsFlightNumber()
    {
        return super.isStrEmpty(isFlightNumber);
    }

    public void setIsFlightNumber(String isFlightNumber)
    {
        this.isFlightNumber = isFlightNumber;
    }

    public String getSharedFlightLogo()
    {
        return super.isStrEmpty(sharedFlightLogo);
    }

    public void setSharedFlightLogo(String sharedFlightLogo)
    {
        this.sharedFlightLogo = sharedFlightLogo;
    }

    public void setDepTime(String depTime)
    {
        this.depTime = depTime;
    }

    public String getArrTime()
    {
        return super.isStrEmpty(arrTime);
    }

    public void setArrTime(String arrTime)
    {
        this.arrTime = arrTime;
    }

    public String getSharedFlights()
    {
        return super.isStrEmpty(sharedFlights);
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
        return super.isStrEmpty(flightNumber);
    }

    public void setFlightNumber(String flightNumber)
    {
        this.flightNumber = flightNumber;
    }

    public String getFlyIngTime()
    {
        return super.isStrEmpty(flyIngTime);
    }

    public void setFlyIngTime(String flyIngTime)
    {
        this.flyIngTime = flyIngTime;
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
        return super.isStrEmpty(acbIninfo);
    }

    public void setAcbIninfo(String acbIninfo)
    {
        this.acbIninfo = acbIninfo;
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

    public String getBaseCabinCode()
    {
        return super.isStrEmpty(baseCabinCode);
    }

    public void setBaseCabinCode(String baseCabinCode)
    {
        this.baseCabinCode = baseCabinCode;
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

    public void setFareBab(float fareBab)
    {
        this.fareBab = fareBab;
    }

    public List<BankInfo> getIstList()
    {
        return istList;
    }

    public void setIstList(List<BankInfo> istList)
    {
        this.istList = istList;
    }


    public List<PlaneTicket> getFlightList()
    {
        return flightList;
    }

    public void setFlightList(List<PlaneTicket> flightList)
    {
        this.flightList = flightList;
    }

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

    public boolean isShopStatus()
    {
        return shopStatus;
    }

    public void setShopStatus(boolean shopStatus)
    {
        this.shopStatus = shopStatus;
    }

    public boolean isPlatformStatus()
    {
        return platformStatus;
    }

    public void setPlatformStatus(boolean platformStatus)
    {
        this.platformStatus = platformStatus;
    }

//     private FlightDetailBean.TicketPriceInfoBean.PachageBean aPackage;
//
//    public void setPackage(FlightDetailBean.TicketPriceInfoBean.PachageBean aPackage) {
//        this.aPackage = aPackage;
//    }
//
//    public  FlightDetailBean.TicketPriceInfoBean.PachageBean getApackage(){
//
//        return  aPackage;
//    }




}
