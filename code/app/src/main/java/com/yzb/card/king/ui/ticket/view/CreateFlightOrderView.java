package com.yzb.card.king.ui.ticket.view;


import com.yzb.card.king.bean.ticket.PlaneTicket;

import java.util.List;

/**
 * 功能：生成机票订单
 *
 * @author:gengqiyun
 * @date: 2016/9/30
 */
public interface CreateFlightOrderView
{
    /**
     * 生成订单成功；
     *
     * @param orderId 订单id；
     */
    void onCreateOrderSucess(Object orderId);

    /**
     * 生成订单失败；
     *
     * @param failMsg 错误消息；
     */
    void onCreateOrderFail(String failMsg);

    /**
     * 获取航班列表；
     *
     * @return
     */
    List<PlaneTicket> getHbListData();
}
