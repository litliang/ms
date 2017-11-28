package com.yzb.card.king.ui.ticket.view;


/**
 * 功能：机票优惠券列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/12
 */
public interface DiscountView
{
    /**
     * 获取成功；
     *
     * @param req_flag
     * @param data 优惠券或红包列表；
     */
    void onGetDiscountSucess(String req_flag, Object data);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetDiscountFail(String failMsg);
}
