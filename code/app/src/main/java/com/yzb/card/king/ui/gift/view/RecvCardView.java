package com.yzb.card.king.ui.gift.view;


/**
 * 功能：领取礼品卡
 *
 * @author:gengqiyun
 * @date: 2016/12/29
 */
public interface RecvCardView
{
    void onRecvCardSuc(String orderId);

    void onRecvCardFail(String failMsg);
}
