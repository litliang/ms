package com.yzb.card.king.ui.ticket.view;


/**
 * 功能：特惠付款-->付款；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public interface DiscountPayView
{
    /**
     * 支付成功；
     */
    void onPaySucess();

    /**
     * 支付失败；
     *
     * @param failMsg 错误消息；
     */
    void onPayFail(String failMsg);
}
