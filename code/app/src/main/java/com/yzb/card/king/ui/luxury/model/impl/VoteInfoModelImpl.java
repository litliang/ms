package com.yzb.card.king.ui.luxury.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.PjBean;
import com.yzb.card.king.ui.luxury.model.VoteInfoModel;

import java.util.Map;

/**
 * 功能：评价详情
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class VoteInfoModelImpl implements VoteInfoModel
{
    private BaseMultiLoadListener loadListener;

    public VoteInfoModelImpl(BaseMultiLoadListener loadListener)
    {
        this.loadListener = loadListener;
    }

    @Override
    public void loadData(final Map<String, Object> paramMap)
    {
        String serviceName = String.valueOf(paramMap.get("serviceName"));
        paramMap.remove("serviceName");
        new SimpleRequest(serviceName, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onSuccess(String data)
            {
                PjBean pjBean = JSON.parseObject(data, PjBean.class);
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(true, pjBean);
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
