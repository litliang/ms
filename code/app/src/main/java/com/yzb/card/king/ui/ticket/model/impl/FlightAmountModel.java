package com.yzb.card.king.ui.ticket.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.TicketAmountBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：机票详情查询；
 *
 * @author:gengqiyun
 * @date: 2016/11/29
 */
public class FlightAmountModel extends BaseModelImpl
{
    public FlightAmountModel(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.transport_flightamount;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected int getTimeOut()
    {
        return 60 * 1000;
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<TicketAmountBean> filterTwoBeans = JSON.parseArray(data, TicketAmountBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, filterTwoBeans);
        }
    }
}
