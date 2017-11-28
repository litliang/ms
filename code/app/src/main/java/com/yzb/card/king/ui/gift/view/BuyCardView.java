package com.yzb.card.king.ui.gift.view;


import com.yzb.card.king.bean.ticket.OrderOutBean;

/**
 * 功能：礼品卡 购买心意e卡
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public interface BuyCardView
{
    void onBuyMindECardSuc(OrderOutBean outBean);

    void onBuyMindECardFail(String failMsg);
}
