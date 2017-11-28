package com.yzb.card.king.ui.ticket.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.DiscountIntegralBean;

import java.util.List;
import java.util.Map;

/**
 * 功能：机票红包列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/13
 */
public class BankIntegralModel extends BaseModelImpl
{
    public BankIntegralModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_card_integral_list;
        this.paramMap = paramMap;
        sendRequest();
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            List<DiscountIntegralBean> mIntegralList = JSON.parseArray(data, DiscountIntegralBean.class);
            loadListener.onListenSuccess(true, mIntegralList);
        }
    }
}
