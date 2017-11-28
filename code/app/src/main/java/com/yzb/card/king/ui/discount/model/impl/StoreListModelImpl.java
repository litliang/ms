package com.yzb.card.king.ui.discount.model.impl;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.discount.model.StoreListModel;

import java.util.List;
import java.util.Map;

/**
 * 功能：美食筛选列表
 *
 * @author:gengqiyun
 * @date: 2016/9/20
 */
public class StoreListModelImpl extends BaseModelImpl implements StoreListModel
{
    public StoreListModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = String.valueOf(paramMap.get("serviceName"));
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<StoreBean> storeBeans = JSON.parseArray(data, StoreBean.class);
        if (storeBeans != null && storeBeans.size() > 0)
        {
            for (StoreBean item : storeBeans)
            {
                if (!TextUtils.isEmpty(item.storePhoto))
                {
                    item.storePhoto = ServiceDispatcher.getImageUrl(item.storePhoto);
                }
            }
        }
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, storeBeans);
        }
    }
}
