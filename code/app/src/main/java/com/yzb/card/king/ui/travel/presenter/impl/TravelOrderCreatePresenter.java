package com.yzb.card.king.ui.travel.presenter.impl;

import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.travel.model.TravelOrderCreaterModel;
import com.yzb.card.king.ui.travel.model.impl.TravelOrderCreateModelImpl;
import com.yzb.card.king.ui.travel.view.TravelOrderCreateView;

import java.util.Map;

/**
 * 功能：旅游提交订单；
 *
 * @author:gengqiyun
 * @date: 2016/8/31
 */
public class TravelOrderCreatePresenter implements  BaseMultiLoadListener
{
    private TravelOrderCreaterModel model;
    private TravelOrderCreateView view;

    public TravelOrderCreatePresenter(TravelOrderCreateView view)
    {
        this.view = view;
        model = new TravelOrderCreateModelImpl(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.commit(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onCreateTravelOrderSucess(event_tag, (OrderOutBean) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onCreateTravelOrderFail(msg);
    }
}
