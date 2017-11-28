package com.yzb.card.king.ui.travel.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.travel.FromPlaceBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：旅游出发地列表
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public class TravelFromPlaceMImpl extends BaseModelImpl implements BaseModel
{
    public TravelFromPlaceMImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_querytravelplanplace;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<FromPlaceBean> fromPlaceBeans = JSON.parseArray(data, FromPlaceBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, fromPlaceBeans);
        }
    }
}
