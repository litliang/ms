package com.yzb.card.king.ui.ticket.view;



/**
 * 功能：领取优惠券；
 *
 * @author:gengqiyun
 * @date: 2016/10/13
 */
public interface ReceiveYhqView
{
    /**
     * 获取成功；
     *
     * @param yhqId 领取成功的优惠券id；
     */
    void onReceiveYhqSucess(String yhqId);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onReceiveYhqFail(String failMsg);
}
