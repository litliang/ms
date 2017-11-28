package com.yzb.card.king.ui.travel.presenter.impl;

import com.yzb.card.king.bean.travel.DateBean;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.app.base.BasePresenter;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.travel.model.impl.TravelGetDatesMImpl;
import com.yzb.card.king.ui.travel.view.TravelGetDatesView;

import java.util.List;
import java.util.Map;

/**
 * 功能：查询旅游线路出发日期；
 *
 * @author:gengqiyun
 * @date: 2016/11/24
 */
public class TravelGetDatesPImpl implements BasePresenter, BaseMultiLoadListener
{
    private BaseModel model;
    private TravelGetDatesView view;

    public TravelGetDatesPImpl(TravelGetDatesView view)
    {
        this.view = view;
        model = new TravelGetDatesMImpl(this);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetDatesSucess(event_tag, (List<DateBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetDatesFail(msg);
    }
}
