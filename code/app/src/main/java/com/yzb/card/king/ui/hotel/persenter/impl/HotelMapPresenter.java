package com.yzb.card.king.ui.hotel.persenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.hotel.model.impl.HotelMapModel;
import com.yzb.card.king.ui.hotel.view.HotelMapView;

import java.util.Map;

/**
 * 功能：酒店地图列表；
 *
 * @author:gengqiyun
 * @date: 2016/11/1
 */
public class HotelMapPresenter implements BaseMultiLoadListener
{
    private HotelMapModel model;
    private HotelMapView view;

    public HotelMapPresenter(HotelMapView view)
    {
        this.view = view;
        model = new HotelMapModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onQueryMapSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onQueryMapFail(msg);
    }
}
