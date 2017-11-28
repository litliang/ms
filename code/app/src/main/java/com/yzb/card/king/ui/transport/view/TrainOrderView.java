package com.yzb.card.king.ui.transport.view;

/**
 * 功能：火车票提交订单
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public interface TrainOrderView
{
    /**
     * @param data
     */
    void onCreateOrderSucess(Object data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onCreateOrderFail(String failMsg);
}
