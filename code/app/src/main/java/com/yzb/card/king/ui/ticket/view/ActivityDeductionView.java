package com.yzb.card.king.ui.ticket.view;


/**
 * 功能：特惠付款-->付款成功后，更新订单状态；
 *
 * @author:gengqiyun
 * @date: 2016/10/15
 */
public interface ActivityDeductionView
{
    /**
     * 成功；
     *
     * @param orderId 操作成功的订单的id；
     */
    void onUpdateOrderStatusSucess(String orderId);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onUpdateOrderStatusFail(String failMsg);
}
