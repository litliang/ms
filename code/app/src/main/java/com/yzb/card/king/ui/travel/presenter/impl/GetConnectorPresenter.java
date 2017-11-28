package com.yzb.card.king.ui.travel.presenter.impl;

import android.text.TextUtils;

import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.travel.model.GetConnectorModel;
import com.yzb.card.king.ui.travel.model.impl.GetConnectorModelImpl;
import com.yzb.card.king.ui.travel.view.GetDefaultConnectorView;

import java.util.List;
import java.util.Map;

/**
 * 功能：获取默认 联系人；
 *
 * @author:gengqiyun
 * @date: 2016/8/31
 */
public class GetConnectorPresenter implements  BaseMultiLoadListener
{
    private GetConnectorModel model;
    private GetDefaultConnectorView view;

    public GetConnectorPresenter(GetDefaultConnectorView view)
    {
        this.view = view;
        model = new GetConnectorModelImpl(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetConnectorSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetConnectorFail(msg);
    }

    public Connector getDefaultConnector(List<Connector> connectors)
    {
        if (connectors == null)
        {
            return null;
        }
        List<Connector> connectorList = connectors;
        for (int i = 0; i < connectorList.size(); i++)
        {
            if (connectorList.get(i).isDefault)
            {
                return connectorList.get(i);
            }
        }
        return null;
    }

    public Connector updateConnector(List<Connector> connectorList, Connector connector)
    {
        if (connector != null && connectorList != null)
        {
            String id = connector.getId();
            for (int i = 0; i < connectorList.size(); i++)
            {
                if (!TextUtils.isEmpty(id) && id.equals(connectorList.get(i).getId()))
                {
                    return connectorList.get(i);
                }
            }
        }
        return null;
    }
}
