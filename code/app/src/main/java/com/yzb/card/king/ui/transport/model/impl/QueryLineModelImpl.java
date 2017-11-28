package com.yzb.card.king.ui.transport.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.transport.bean.ShipRoute;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.transport.model.QueryLineModel;

import java.util.List;
import java.util.Map;

/**
 * 功能：航线
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class QueryLineModelImpl extends BaseModelImpl implements QueryLineModel
{
    public QueryLineModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.transport_queryline;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<ShipRoute> list = JSON.parseArray(data, ShipRoute.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, list);
        }
    }
}
