package com.yzb.card.king.ui.luxury.presenter.impl;

import com.yzb.card.king.ui.appwidget.popup.BankListLoadListener;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.luxury.model.BankMenuModel;
import com.yzb.card.king.ui.luxury.model.impl.BankMenuModelImpl;
import com.yzb.card.king.ui.luxury.presenter.BankMenuPresenter;
import com.yzb.card.king.ui.luxury.presenter.CategoryMenuPresenter;
import com.yzb.card.king.ui.luxury.view.BankMenuView;

import java.util.List;
import java.util.Map;

/**
 * 功能：银行列表
 *
 * @author:gengqiyun
 * @date: 2016/9/23
 */
public class BankMenuPresenterImpl implements BankMenuPresenter, BankListLoadListener
{
    private BankMenuModel model;
    private BankMenuView view;

    public BankMenuPresenterImpl(BankMenuView view)
    {
        this.view = view;
        model = new BankMenuModelImpl(this);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(List<BankBean> myBanks, List<BankBean> allBanks)
    {
        view.onLoadBankMenuSucess(myBanks, allBanks);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onLoadBankMenuFail(msg);
    }
}
