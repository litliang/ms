package com.yzb.card.king.ui.app.model;

import com.yzb.card.king.ui.app.bean.Connector;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/13 11:07
 */
public interface IConnectorMana
{
    void loadConn();

    void updateConn(Connector connector);

    void deleteConn(Connector connector);

    void setDefaultConn(Connector connector);

    void addConnector(Connector connector);
}
