package com.yzb.card.king.ui.ticket.model.impl;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：付款成功后，更新订单状态；
 *
 * @author:gengqiyun
 * @date: 2016/10/15
 */
public class ActivityDeductionModel extends BaseModelImpl
{
    public ActivityDeductionModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_activitydeductionamount;
        this.paramMap = paramMap;
        sendRequest();
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, paramMap.get("orderId"));
        }
    }
}
