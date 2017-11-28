package com.yzb.card.king.ui.travel.presenter.impl;

import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.app.base.BasePresenter;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.travel.model.impl.FlightBookMImpl;
import com.yzb.card.king.ui.travel.view.FlightBookView;

import java.util.Map;

/**
 * 功能：机票购票；
 *
 * @author:gengqiyun
 * @date: 2016/11/29
 */
public class FlightBookPImpl implements BasePresenter, BaseMultiLoadListener
{
    private BaseModel model;
    private FlightBookView view;

    public FlightBookPImpl(FlightBookView view)
    {
        this.view = view;
        model = new FlightBookMImpl(this);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onFlightBookSucess();
    }

    @Override
    public void onListenError(String msg)
    {
        view.onFlightBookFail(msg);
    }
}
