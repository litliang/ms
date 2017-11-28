package com.yzb.card.king.ui.travel.presenter.impl;

import com.yzb.card.king.bean.travel.FromPlaceBean;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.travel.model.impl.TravelFromPlaceMImpl;
import com.yzb.card.king.ui.travel.view.TravelFromPlaceView;

import java.util.List;
import java.util.Map;

/**
 * 功能：旅游出发地列表
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public class TravelFromPlacePresenter implements  BaseMultiLoadListener
{
    private BaseModel model;
    private TravelFromPlaceView view;

    public TravelFromPlacePresenter(TravelFromPlaceView view)
    {
        this.view = view;
        model = new TravelFromPlaceMImpl(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetFromPlaceSucess(event_tag, (List<FromPlaceBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetFromPlaceFail(msg);
    }
}
