package com.yzb.card.king.ui.ticket.view;


import com.yzb.card.king.bean.ticket.TicketAmountBean;
import com.yzb.card.king.ui.discount.bean.AccountBean;

import java.util.List;

/**
 * 功能：机票详情查询；
 *
 * @author:gengqiyun
 * @date: 2016/11/29
 */
public interface FlightAmountView
{
    /**
     * 获取成功；
     *
     * @param accountBeans 账号列表；
     */
    void onGetFlightAmountSucess(boolean event_tag, List<TicketAmountBean> accountBeans);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetFlightAmountFail(String failMsg);
}
