package com.yzb.card.king.ui.transport.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.transport.model.SeatListModel;

import java.util.List;
import java.util.Map;

/**
 * 功能：火车票座位列表
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class SeatListModelImpl extends BaseModelImpl implements SeatListModel
{
    public SeatListModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.transport_queryseatlist;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<Map> list = JSON.parseArray(data, Map.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, list);
        }
    }
}
