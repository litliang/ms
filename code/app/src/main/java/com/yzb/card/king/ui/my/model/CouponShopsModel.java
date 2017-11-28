package com.yzb.card.king.ui.my.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.bean.my.CouponShopBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：优惠券对应商家；
 *
 * @author:gengqiyun
 * @date: 2017/1/12
 */
public class CouponShopsModel extends BaseModelImpl
{
    public CouponShopsModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.query_all_coupon;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<CouponInfoBean> beans = JSON.parseArray(data, CouponInfoBean.class);

        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, beans);
        }
    }
}
