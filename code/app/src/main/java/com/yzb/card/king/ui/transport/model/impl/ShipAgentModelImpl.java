package com.yzb.card.king.ui.transport.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.transport.bean.ShipAgent;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.transport.model.ShipAgentModel;

import java.util.List;
import java.util.Map;

/**
 * 功能：船票代理商
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class ShipAgentModelImpl extends BaseModelImpl implements ShipAgentModel
{
    public ShipAgentModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.transport_queryshipseatlist;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<ShipAgent> list = JSON.parseArray(data, ShipAgent.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, list);
        }
    }
}
