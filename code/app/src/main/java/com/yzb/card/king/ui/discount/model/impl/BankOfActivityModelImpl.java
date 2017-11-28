package com.yzb.card.king.ui.discount.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.discount.model.BankOfActivityModel;

import java.util.List;
import java.util.Map;

/**
 * 功能：优惠活动银行
 *
 * @author:gengqiyun
 * @date: 2016/9/20
 */
public class BankOfActivityModelImpl extends BaseModelImpl implements BankOfActivityModel
{
    public BankOfActivityModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_bankofactivity;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<BankBean> bankBeans = JSON.parseArray(data, BankBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, bankBeans);
        }
    }
}
