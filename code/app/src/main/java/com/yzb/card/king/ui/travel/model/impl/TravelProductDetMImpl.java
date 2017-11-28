package com.yzb.card.king.ui.travel.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.travel.TravelProduDetailBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：旅游产品详情
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public class TravelProductDetMImpl extends BaseModelImpl implements BaseModel
{
    public TravelProductDetMImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_selecttravelproductinfo;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, JSON.parseObject(data, TravelProduDetailBean.class));
        }
    }
}
