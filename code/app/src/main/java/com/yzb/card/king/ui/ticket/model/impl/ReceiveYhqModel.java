package com.yzb.card.king.ui.ticket.model.impl;

import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：商家红包列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/13
 */
public class ReceiveYhqModel extends BaseModelImpl
{
    private String orderId; //领取的优惠券的id；

    public ReceiveYhqModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = String.valueOf(paramMap.get("serviceName"));
        this.orderId = String.valueOf(paramMap.get("actId"));
        this.paramMap = paramMap;

        this.paramMap.remove("serviceName");
        sendRequest();
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, orderId);
        }
    }
}
