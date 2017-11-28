package com.yzb.card.king.ui.transport.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.transport.model.TrainOrderModel;

import java.util.List;
import java.util.Map;

/**
 * 功能：火车票提交订单
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class TrainOrderModelImpl extends BaseModelImpl implements TrainOrderModel
{
    public TrainOrderModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = "";
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<Map> list = JSON.parseArray(data, Map.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, list);
        }
    }
}
