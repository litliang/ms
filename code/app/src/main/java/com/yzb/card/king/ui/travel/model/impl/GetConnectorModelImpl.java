package com.yzb.card.king.ui.travel.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.travel.model.GetConnectorModel;
import com.yzb.card.king.ui.app.bean.Connector;

import java.util.List;
import java.util.Map;

/**
 * 功能：获取默认 联系人；
 *
 * @author:gengqiyun
 * @date: 2016/8/31
 */
public class GetConnectorModelImpl extends BaseModelImpl implements GetConnectorModel
{
    public GetConnectorModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.DISCOUNT_CONTACTSLIST;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            List<Connector> connectors = JSON.parseArray(data, Connector.class);
            loadListener.onListenSuccess(event_tag, connectors);
        }
    }
}
