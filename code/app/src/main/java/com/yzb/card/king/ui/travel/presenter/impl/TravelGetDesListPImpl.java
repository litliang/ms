package com.yzb.card.king.ui.travel.presenter.impl;

import com.yzb.card.king.bean.travel.FilterTwoBean;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.app.base.BasePresenter;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.travel.model.impl.TravelGetDesListMImpl;
import com.yzb.card.king.ui.travel.view.TravelGetDesListView;

import java.util.List;
import java.util.Map;

/**
 * 功能：旅游目的地列表
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public class TravelGetDesListPImpl implements BasePresenter, BaseMultiLoadListener
{
    private BaseModel model;
    private TravelGetDesListView view;

    public TravelGetDesListPImpl(TravelGetDesListView view)
    {
        this.view = view;
        model = new TravelGetDesListMImpl(this);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetDesListSucess(event_tag, (List<FilterTwoBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetDesListFail(msg);
    }
}
