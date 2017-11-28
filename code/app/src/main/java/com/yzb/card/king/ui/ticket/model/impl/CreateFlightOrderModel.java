package com.yzb.card.king.ui.ticket.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：生成机票订单；
 *
 * @author:gengqiyun
 * @date: 2016/9/30
 */
public class CreateFlightOrderModel extends BaseModelImpl
{
    public CreateFlightOrderModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    public void commit(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_flightorder_create;
        this.paramMap = paramMap;
        sendRequest();
    }

    @Override
    protected void afterSuccess(String data)
    {
        OrderOutBean orderOutBean = JSON.parseObject(data, OrderOutBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, orderOutBean);
        }
    }

    @Override
    protected int getTimeOut()
    {
        return 30 * 1000;
    }
}
