package com.yzb.card.king.ui.transport.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.transport.model.ShipAgentModel;
import com.yzb.card.king.ui.transport.model.impl.ShipAgentModelImpl;
import com.yzb.card.king.ui.transport.presenter.ShipAgentPresenter;
import com.yzb.card.king.ui.transport.view.ShipAgentView;

import java.util.Map;

/**
 * 功能：船票代理商
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class ShipAgentPresenterImpl implements ShipAgentPresenter, BaseMultiLoadListener
{
    private ShipAgentModel model;
    private ShipAgentView view;

    public ShipAgentPresenterImpl(ShipAgentView view)
    {
        this.view = view;
        model = new ShipAgentModelImpl(this);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onLoadShipAgentListSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onLoadShipAgentListFail(msg);
    }
}
