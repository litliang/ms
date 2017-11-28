package com.yzb.card.king.ui.travel.view;

import com.yzb.card.king.bean.ticket.OrderOutBean;

/**
 * 功能：旅游提交订单；
 *
 * @author:gengqiyun
 * @date: 2016/8/31
 */
public interface TravelOrderCreateView
{
    /**
     * 成功；
     *
     * @param orderData 订单数据；
     */
    void onCreateTravelOrderSucess(boolean event_tag, OrderOutBean orderData);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onCreateTravelOrderFail(String failMsg);
}
