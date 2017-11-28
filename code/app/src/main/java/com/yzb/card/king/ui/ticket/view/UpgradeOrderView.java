package com.yzb.card.king.ui.ticket.view;


import com.yzb.card.king.bean.ticket.UpgradeOrderBean;

/**
 * 功能：查询可改签的订单详细（UpgradeOrderReq）
 *
 * @author:gengqiyun
 * @date: 2016/12/2
 */
public interface UpgradeOrderView
{
    /**
     * 获取成功；
     * @param accountBeans
     */
    void onGetUpgradeOrderSucess(UpgradeOrderBean accountBeans);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetUpgradeOrderFail(String failMsg);
}
