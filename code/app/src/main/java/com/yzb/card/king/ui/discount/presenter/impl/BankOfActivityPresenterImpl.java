package com.yzb.card.king.ui.discount.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.model.BankOfActivityModel;
import com.yzb.card.king.ui.discount.model.impl.BankOfActivityModelImpl;
import com.yzb.card.king.ui.discount.presenter.BankOfActivityPresenter;
import com.yzb.card.king.ui.discount.view.StoreListView;

import java.util.Map;

/**
 * 功能：优惠活动银行
 *
 * @author:gengqiyun
 * @date: 2016/9/20
 */
public class BankOfActivityPresenterImpl implements BankOfActivityPresenter, BaseMultiLoadListener
{
    private BankOfActivityModel model;
    private StoreListView view;

    public BankOfActivityPresenterImpl(StoreListView view)
    {
        this.view = view;
        model = new BankOfActivityModelImpl(this);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onLoadListDataSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onLoadListDataFail(msg);
    }
}
