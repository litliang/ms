package com.yzb.card.king.ui.travel.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.travel.DateBean;
import com.yzb.card.king.bean.travel.FromPlaceBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：查询旅游线路出发日期
 *
 * @author:gengqiyun
 * @date: 2016/11/24
 */
public class TravelGetDatesMImpl extends BaseModelImpl implements BaseModel
{
    public TravelGetDatesMImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_querylinedepdate;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<DateBean> fromPlaceBeans = JSON.parseArray(data, DateBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, fromPlaceBeans);
        }
    }
}
