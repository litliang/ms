package com.yzb.card.king.ui.discount.model.impl;


import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.model.CollectionModel;

import java.util.Map;

/**
 * 功能：店铺详情
 *
 * @author:gengqiyun
 * @date: 2016/9/21
 */
public class CollectionModelImpl extends BaseModelImpl implements CollectionModel
{
    public CollectionModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, data);
        }
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_collections;
        super.loadData(event_tag, paramMap);
    }
}
