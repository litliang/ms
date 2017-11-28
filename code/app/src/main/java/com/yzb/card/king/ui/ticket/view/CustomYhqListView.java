package com.yzb.card.king.ui.ticket.view;


import com.yzb.card.king.bean.ticket.CustCouponBean;

import java.util.List;

/**
 * 功能：客户优惠券列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/15
 */
public interface CustomYhqListView
{
    /**
     * 获取成功；
     */
    void onGetCustomYhqListSucess(List<CustCouponBean> accountBeans);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetCustomYhqListFail(String failMsg);
}
