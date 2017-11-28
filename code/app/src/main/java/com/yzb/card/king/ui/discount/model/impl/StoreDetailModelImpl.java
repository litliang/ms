package com.yzb.card.king.ui.discount.model.impl;


import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.ShopDetail;
import com.yzb.card.king.ui.discount.model.StoreDetailModel;

import java.util.Map;

/**
 * 功能：店铺详情
 *
 * @author:gengqiyun
 * @date: 2016/9/21
 */
public class StoreDetailModelImpl extends BaseModelImpl implements StoreDetailModel
{
    public StoreDetailModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    protected void afterSuccess(String data)
    {
        ShopDetail shopDetail = JSON.parseObject(data, ShopDetail.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, shopDetail);
        }
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_storedetail;
        super.loadData(event_tag, paramMap);
    }
}
