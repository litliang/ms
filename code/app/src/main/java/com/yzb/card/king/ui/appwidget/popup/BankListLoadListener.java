package com.yzb.card.king.ui.appwidget.popup;

import com.yzb.card.king.ui.discount.bean.BankBean;

import java.util.List;

/**
 * 银行卡列表MVP回调；
 *
 * @author gengqiyun
 * @date 2016/9/23
 */
public interface BankListLoadListener
{
    /**
     * @param myBanks  我的银行；
     * @param allBanks 所有银行；
     */
    void onListenSuccess(List<BankBean> myBanks, List<BankBean> allBanks);

    void onListenError(String msg);
}
