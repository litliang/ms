package com.yzb.card.king.ui.travel.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.travel.model.GetConnectorModel;

import java.util.List;
import java.util.Map;

/**
 * 功能：机票购票
 *
 * @author:gengqiyun
 * @date: 2016/11/29
 */
public class FlightBookMImpl extends BaseModelImpl implements GetConnectorModel
{
    public FlightBookMImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.transport_flightbooking;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            List<Connector> connectors = JSON.parseArray(data, Connector.class);
            loadListener.onListenSuccess(event_tag, connectors);
        }
    }

    @Override
    protected int getTimeOut()
    {
        return 60 * 1000;
    }
}
