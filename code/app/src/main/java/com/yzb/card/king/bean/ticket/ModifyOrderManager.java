package com.yzb.card.king.bean.ticket;

import android.text.TextUtils;

import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.LogUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 描述：机票改签管理；
 * 作者：gengqiyun
 * 日期：2016/12/5
 */
public class ModifyOrderManager implements Serializable
{
    // 所有需要改签的航班列表；
    private Set<UpgradeOrderBean.AvResult> results = new HashSet<>();


    //当前航线类型
    private String currentLine; //（单程：OW；往返：RT；多段：MT）

    //用户已经预定的机票数据
    private Set<UpgradeOrderBean.FlightBean> flightBeans = new HashSet<>();
    //用户当前选择的航班；
    private UpgradeOrderBean.FlightBean flightBean;
    //来自改签详情页的航班列表，主要用于展示航班改签列表的原航班；
    private List<TicketOrderDetBean.OrderInfoBean> orinalOrders = new ArrayList<>();
    private OrderBean orderBean; //订单信息； 特惠付款时使用；
    private String flightType; //航班类型  （单程：OW；往返：RT；多段：MT）

    public String getFlightType()
    {
        return flightType;
    }

    /**
     * 添加改签时查询出的结果；
     */
    public void addResult(UpgradeOrderBean.AvResult avResult)
    {
        if (avResult != null)
        {
            results.add(avResult);
        }
    }

    public void addResult(List<UpgradeOrderBean.AvResult> avResultList)
    {
        if (avResultList != null)
        {
            results.addAll(avResultList);
        }
    }

    /**
     * 判断是否是组合套餐；
     *
     * @return
     */
    public boolean isCombineProducts()
    {
        //往返；判断组合套餐情况；
        if (AppConstant.TYPE_ROUND.equals(flightType) && orinalOrders != null && orinalOrders.size() == 2)
        {
            String firstOrderNo = orinalOrders.get(0).getOrderNo();
            String secondOrderNo = orinalOrders.get(1).getOrderNo();
            if (!TextUtils.isEmpty(firstOrderNo) && firstOrderNo.equals(secondOrderNo))
            {
                LogUtil.i("航空订单号相同,是组合套餐 firstOrderNo=" + firstOrderNo);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前的航班；
     *
     * @return
     */
    public TicketOrderDetBean.OrderInfoBean getCurrentOrderInfo()
    {
        int index = flightBeans.size();
        int index2 = orinalOrders.size();
        if (index < index2)
        {
            TicketOrderDetBean.OrderInfoBean orderInfoBean = orinalOrders.get(index);
            return orderInfoBean;
        }
        return null;
    }


    public String getCurrentLine()
    {
        return currentLine;
    }

    public void setCurrentLine(String currentLine)
    {
        this.currentLine = currentLine;
    }

    /**
     * 添加航班；
     *
     * @param flightBean
     */
    public void addFlightBean(UpgradeOrderBean.FlightBean flightBean)
    {
        flightBeans.add(flightBean);
    }

    public Set<UpgradeOrderBean.FlightBean> getFlightBeans()
    {
        return flightBeans;
    }


    /**
     * 移除已改签的机票；
     */
    public void removeLastFlight()
    {
        if (flightBeans.size() > 0)
        {
            Iterator<UpgradeOrderBean.FlightBean> iterator = flightBeans.iterator();
            UpgradeOrderBean.FlightBean lastFlightBean = null;
            //找到最后一个；
            while (iterator.hasNext())
            {
                lastFlightBean = iterator.next();
            }
            flightBeans.remove(lastFlightBean);
        }
    }

    public void setFlightBeans(Set<UpgradeOrderBean.FlightBean> flightBeans)
    {
        this.flightBeans = flightBeans;
    }

    public void setOrinalOrders(List<TicketOrderDetBean.OrderInfoBean> orderInfos)
    {
        if (orderInfos != null && orderInfos.size() > 0)
        {
            orinalOrders.clear();
            orinalOrders.addAll(orderInfos);
        }
    }

    public List<TicketOrderDetBean.OrderInfoBean> getOrinalOrders()
    {
        return orinalOrders;
    }

    public void setOrderBean(OrderBean orderBean)
    {
        this.orderBean = orderBean;
    }

    public OrderBean getOrderBean()
    {
        return orderBean;
    }


    public void setFlightType(String flightType)
    {
        this.flightType = flightType;
    }

    public Set<UpgradeOrderBean.AvResult> getResults()
    {
        return results;
    }

}
