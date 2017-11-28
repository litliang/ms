package com.yzb.card.king.ui.ticket.model.impl;


import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.OrderIdBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：改签确认；
 *
 * @author:gengqiyun
 * @date: 2016/10/15
 */
public class ReBookModel extends BaseModelImpl
{
    public ReBookModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_rebook;
        this.paramMap = paramMap;
        sendRequest();
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            OrderIdBean orderIdBeans = JSON.parseObject(data, OrderIdBean.class);
            loadListener.onListenSuccess(true, orderIdBeans);
        }
    }

    @Override
    protected int getTimeOut()
    {
        return 60 * 1000;
    }
}
