package com.yzb.card.king.ui.luxury.model.impl;


import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.FilterBean;
import com.yzb.card.king.ui.luxury.model.FilterMenuModel;

import java.util.List;
import java.util.Map;

/**
 * 功能：过滤条件列表
 *
 * @author:gengqiyun
 * @date: 2016/9/23
 */
public class FilterMenuModelImpl implements FilterMenuModel
{
    private BaseMultiLoadListener loadListener;

    public FilterMenuModelImpl(BaseMultiLoadListener loadListener)
    {
        this.loadListener = loadListener;
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        new SimpleRequest(CardConstant.card_app_shopfilterquery, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onSuccess(String data)
            {
                List<FilterBean> filterBeanList = JSON.parseArray(data, FilterBean.class);
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(true, filterBeanList);
                }
            }

            @Override
            public void onFail(String failMsg)
            {
                if (loadListener != null)
                {
                    loadListener.onListenError(failMsg);
                }
            }
        }).sendPostRequest();
    }
}
