package com.yzb.card.king.ui.gift.modle;

import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：赠送礼品卡；
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public class GiveCardModel extends BaseModelImpl
{
    public GiveCardModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = String.valueOf(paramMap.get("serviceName"));

        paramMap.remove("serviceName");

        super.loadData(event_tag, paramMap);
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
