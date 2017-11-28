package com.yzb.card.king.ui.transport.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.transport.model.TrainOrderModel;
import com.yzb.card.king.ui.transport.model.impl.TrainOrderModelImpl;
import com.yzb.card.king.ui.transport.view.TrainOrderView;

import java.util.Map;

/**
 * 功能：火车票提交订单
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class TrainOrderPresenterImpl implements TrainOrderPresenter, BaseMultiLoadListener
{
    private TrainOrderModel model;
    private TrainOrderView view;

    public TrainOrderPresenterImpl(TrainOrderView view)
    {
        this.view = view;
        model = new TrainOrderModelImpl(this);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onCreateOrderSucess(data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onCreateOrderFail(msg);
    }
}
