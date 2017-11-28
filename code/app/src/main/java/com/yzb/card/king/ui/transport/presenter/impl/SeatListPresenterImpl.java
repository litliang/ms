package com.yzb.card.king.ui.transport.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.transport.model.SeatListModel;
import com.yzb.card.king.ui.transport.model.impl.SeatListModelImpl;
import com.yzb.card.king.ui.transport.presenter.SeatListPresenter;

import java.util.Map;

/**
 * 功能：火车票座位列表
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class SeatListPresenterImpl implements SeatListPresenter, BaseMultiLoadListener
{
    private SeatListModel model;
    private BaseViewLayerInterface view;

    public SeatListPresenterImpl(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new SeatListModelImpl(this);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic( data,1);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,1);
    }
}
