package com.yzb.card.king.ui.my.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.my.CouponNearbyBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：优惠券附近筛选条件；
 *
 * @author:gengqiyun
 * @date: 2017/1/17
 */
public class CouponNearbyModel extends BaseModelImpl
{
    public CouponNearbyModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.HOTEL_FILTER_LIST;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, JSON.parseObject(data, CouponNearbyBean.class));
        }
    }
}
