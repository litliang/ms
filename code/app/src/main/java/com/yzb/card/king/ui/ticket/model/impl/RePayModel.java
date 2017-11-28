package com.yzb.card.king.ui.ticket.model.impl;


import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：改签后需要支付接口；
 *
 * @author:gengqiyun
 * @date: 2016/12/4
 */
public class RePayModel extends BaseModelImpl
{
    public RePayModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_repay;
        this.paramMap = paramMap;
        sendRequest();
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, data);
        }
    }

    @Override
    protected int getTimeOut()
    {
        return 60 * 1000;
    }
}
