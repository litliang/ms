package com.yzb.card.king.ui.transport.model.impl;

import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.transport.model.CommonModel;

import java.util.Map;

/**
 * 功能：机票
 *
 * @author:gengqiyun
 * @date: 2016/9/6
 */
public class CommonModelImpl extends BaseModelImpl implements CommonModel
{
    public CommonModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = String.valueOf(paramMap.get("serviceName"));
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
