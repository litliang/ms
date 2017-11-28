package com.yzb.card.king.ui.app.view;

import com.yzb.card.king.ui.app.bean.Connector;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/13 9:11
 */
public interface ConnectorManaView
{
    void onLoadSuccess(List<Connector> connectors);

    void onLoadFailed(String s);
}
