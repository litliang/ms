package com.yzb.card.king.ui.travel.presenter.impl;

import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.app.base.BasePresenter;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.travel.model.impl.TravelLineMImpl;
import com.yzb.card.king.ui.travel.view.TravelLineView;

import java.util.List;
import java.util.Map;

/**
 * 功能：旅游线路列表
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public class TravelLinePresenter implements BasePresenter, BaseMultiLoadListener
{
    private BaseModel model;
    private TravelLineView view;

    public TravelLinePresenter(TravelLineView view)
    {
        this.view = view;
        model = new TravelLineMImpl(this);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetTravelLineSucess(event_tag, (List<TravelLineBean>) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetTravelLineFail(msg);
    }
}
