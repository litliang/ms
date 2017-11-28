package com.yzb.card.king.ui.ticket.view;

import com.yzb.card.king.bean.ticket.TicketOrderDetBean;

/**
 * 功能：退票订单详情；
 *
 * @author:gengqiyun
 * @date: 2016/12/1
 */
public interface TicketOrderDetailView
{
    /**
     * 获取成功；
     */
    void onGetOrderDetailSucess(TicketOrderDetBean yhqBeanList);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetOrderDetailFail(String failMsg);
}
