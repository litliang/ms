package com.yzb.card.king.ui.hotel.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.HotelListBean;

import java.util.List;
import java.util.Map;

/**
 * 功能：酒店地图列表；
 *
 * @author:gengqiyun
 * @date: 2016/11/1
 */
public class HotelMapModel extends BaseModelImpl
{
    public HotelMapModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.hotel_queryhotelmaplist;
        this.paramMap = paramMap;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<HotelListBean> hotelListBeans = JSON.parseArray(data, HotelListBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, hotelListBeans);
        }
    }
}
