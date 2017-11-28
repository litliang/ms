package com.yzb.card.king.ui.bonus.model;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：领取红包；
 *
 * @author:gengqiyun
 * @date: 2016/12/30
 */
public class RecvBounsModel extends BaseModelImpl
{
    String orderId;

    public RecvBounsModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_receivebonus;
        orderId = String.valueOf(paramMap.get("orderId"));
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, orderId + "#" + data);
        }
    }
}
