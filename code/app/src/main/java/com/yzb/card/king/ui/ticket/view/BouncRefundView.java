package com.yzb.card.king.ui.ticket.view;


/**
 * 功能：确认退票；
 *
 * @author:gengqiyun
 * @date: 2016/12/2
 */
public interface BouncRefundView
{
    /**
     * 获取成功；
     */
    void onBouncRefundSucess();

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onBouncRefundFail(String failMsg);
}
