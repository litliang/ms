package com.yzb.card.king.ui.ticket.view;


import com.yzb.card.king.ui.discount.bean.DiscountIntegralBean;

import java.util.List;

/**
 * 功能：银行积分列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/13
 */
public interface BankIntegralView
{
    /**
     * 获取成功；
     *
     * @param yhqBeanList 银行积分列表；
     */
    void onGetBankIntegralSucess(List<DiscountIntegralBean> yhqBeanList);

    /**
     * 获取失败；
     *
     * @param failMsg 错误消息；
     */
    void onGetBankIntegralFail(String failMsg);
}
