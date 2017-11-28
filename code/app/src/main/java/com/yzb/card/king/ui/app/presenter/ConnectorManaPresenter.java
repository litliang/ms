package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.model.IConnectorMana;
import com.yzb.card.king.ui.app.model.ConnectorManaImpl;
import com.yzb.card.king.ui.app.view.ConnectorManaView;

import java.util.List;

/**
 * 描述：联系人管理提供者类
 *
 * 作者：殷曙光
 * 日期：2016/9/13 9:12
 */
public class ConnectorManaPresenter
{
    private ConnectorManaView view;
    private IConnectorMana model;
    public ConnectorManaPresenter(ConnectorManaView view)
    {
        this.view = view;
        this.model = new ConnectorManaImpl(new CallBack());
    }

    public void loadConn()
    {
        model.loadConn();
    }

    public void addConnector(Connector connector)
    {
        model.addConnector(connector);
    }

    public void setDefaultConn(Connector connector)
    {
        model.setDefaultConn(connector);
    }

    public void deleteConn(Connector connector)
    {
        model.deleteConn(connector);

    }
    public void updateConn(Connector connector)
    {
        model.updateConn(connector);
    }

    private class CallBack extends HttpCallBackImpl{

        @Override
        public void onSuccess(Object o)
        {
            view.onLoadSuccess((List<Connector>)o);
        }

        @Override
        public void onFailed(Object o)
        {
            view.onLoadFailed((String) o);
        }
    }
}
