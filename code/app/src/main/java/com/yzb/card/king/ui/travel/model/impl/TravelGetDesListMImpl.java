package com.yzb.card.king.ui.travel.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.travel.FilterTwoBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * 功能：旅游目的地列表
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public class TravelGetDesListMImpl extends BaseModelImpl implements BaseModel
{
    public TravelGetDesListMImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_queryregioncity;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<FilterTwoBean> filterTwoBeans = JSON.parseArray(data, FilterTwoBean.class);

        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, filterTwoBeans);
        }
    }
}
