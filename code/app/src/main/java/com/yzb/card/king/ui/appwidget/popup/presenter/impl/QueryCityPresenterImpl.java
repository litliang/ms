package com.yzb.card.king.ui.appwidget.popup.presenter.impl;

import com.yzb.card.king.ui.appwidget.popup.model.QueryCityModel;
import com.yzb.card.king.ui.appwidget.popup.model.impl.QueryCityModelImpl;
import com.yzb.card.king.ui.appwidget.popup.presenter.QueryCitysPresenter;
import com.yzb.card.king.ui.appwidget.popup.view.QueryCityView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.other.bean.IPlace;
import com.yzb.card.king.ui.other.listeners.QueryPlaceListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：城市查询；
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class QueryCityPresenterImpl implements QueryCitysPresenter, BaseMultiLoadListener,
        QueryPlaceListener<IPlace>
{
    private QueryCityModel model;

    private QueryCityView view;

    public QueryCityPresenterImpl(QueryCityView view)
    {
        this.view = view;

        model = new QueryCityModelImpl(this, this);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        model.getCities();
    }

    @Override
    public void queryCity(String currentRegion, String key)
    {
        model.queryCity(currentRegion, key);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetCitysSucess(data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetCitysFail(msg);
    }


    public void sumHotCity(int industryId, String cityId)
    {
        model.hotCityStatistics(industryId, cityId);
    }

    @Override
    public void onQuerySuccess(Map<String, List<IPlace>> places)
    {
        view.onQuerySuccess(places);
    }

    @Override
    public void onQueryFail()
    {
        view.onQueryFail();
    }
}
