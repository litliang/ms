package com.yzb.card.king.ui.ticket.model.impl;


import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：退票订单详情；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class TicketOrderDetailModel extends BaseModelImpl
{
    public TicketOrderDetailModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.ORDER_PLAIN_DETAIL;
        this.paramMap = paramMap;
        sendRequest();
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            TicketOrderDetBean detBean = JSON.parseObject(data, TicketOrderDetBean.class);
            loadListener.onListenSuccess(event_tag, detBean);
        }
    }
}
