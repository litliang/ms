package com.yzb.card.king.ui.ticket.view;


import com.yzb.card.king.ui.discount.bean.AccountBalanceBean;


/**
 * 功能：查询红包账号和礼品卡余额；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public interface AccountBalanceView
{
    /**
     * 获取成功；
     *
     * @param accountBeans 账号列表；
     */
    void onGetAccountBalanceSucess(AccountBalanceBean accountBeans);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetAccountBalanceFail(String failMsg);
}
