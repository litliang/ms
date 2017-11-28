package com.yzb.card.king.ui.ticket.model.impl;


import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：确认退票；
 *
 * @author:gengqiyun
 * @date: 2016/10/15
 */
public class BouncRefundModel extends BaseModelImpl
{
    public BouncRefundModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_bouncrefund;
        this.paramMap = paramMap;
        sendRequest();
    }

    @Override
    protected int getTimeOut()
    {
        return 100 * 1000;
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, data);
        }
    }

}
