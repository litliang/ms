package com.yzb.card.king.ui.luxury.view;

import com.yzb.card.king.ui.discount.bean.BankBean;

import java.util.List;

/**
 * 功能：银行列表；
 *
 * @author:gengqiyun
 * @date: 2016/9/23
 */
public interface BankMenuView
{
    /**
     */
    void onLoadBankMenuSucess(List<BankBean> myBanks, List<BankBean> allBanks);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onLoadBankMenuFail(String failMsg);
}
