package com.yzb.card.king.ui.discount.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.Goods;
import com.yzb.card.king.ui.discount.model.StoreGoodsModel;

import java.util.List;
import java.util.Map;

/**
 * 功能：店铺商品列表
 *
 * @author:gengqiyun
 * @date: 2016/9/21
 */
public class StoreGoodsModelImpl extends BaseModelImpl implements StoreGoodsModel
{
    public StoreGoodsModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_storegoodslist;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<Goods> goodsList = JSON.parseArray(data, Goods.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, goodsList);
        }
    }
}
