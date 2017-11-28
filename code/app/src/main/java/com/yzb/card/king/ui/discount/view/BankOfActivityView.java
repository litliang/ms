package com.yzb.card.king.ui.discount.view;

/**
 * 功能：优惠活动银行；
 *
 * @author:gengqiyun
 * @date: 2016/9/20
 */
public interface BankOfActivityView
{
    /**
     * @param data
     */
    void onLoadBankActivitySucess(Object data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onLoadBankActivityFail(String failMsg);
}
