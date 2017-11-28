package com.yzb.card.king.ui.bonus.view;


/**
 * 功能：领取红包；
 *
 * @author:gengqiyun
 * @date: 2016/12/30
 */
public interface RecvBounsView
{
    void onRecvBounsSuc(String orderId);

    void onRecvBounsFail(String failMsg);
}
