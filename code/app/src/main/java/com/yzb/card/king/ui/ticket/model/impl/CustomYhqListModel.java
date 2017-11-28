package com.yzb.card.king.ui.ticket.model.impl;


import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.CustCouponBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：客户优惠券列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/15
 */
public class CustomYhqListModel extends BaseModelImpl
{
    public CustomYhqListModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_canusecouponlist;
        this.paramMap = paramMap;
        sendRequest();
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            List<CustCouponBean> yhqBeans = JSON.parseArray(data, CustCouponBean.class);
            loadListener.onListenSuccess(true, yhqBeans);
        }
    }

}
