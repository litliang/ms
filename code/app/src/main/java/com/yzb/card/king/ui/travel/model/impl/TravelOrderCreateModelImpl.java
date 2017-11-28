package com.yzb.card.king.ui.travel.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.travel.model.TravelOrderCreaterModel;

import java.util.Map;

/**
 * 功能：获取默认 联系人；
 *
 * @author:gengqiyun
 * @date: 2016/8/31
 */
public class TravelOrderCreateModelImpl extends BaseModelImpl implements TravelOrderCreaterModel
{
    public TravelOrderCreateModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void commit(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_travelordercreate;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, JSON.parseObject(data, OrderOutBean.class));
        }
    }
}
