package com.yzb.card.king.ui.luxury.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.PjBean;

import java.util.List;
import java.util.Map;

/**
 * 功能：评价列表
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class VoteListModel implements BaseModel
{
    private BaseMultiLoadListener loadListener;

    public VoteListModel(BaseMultiLoadListener loadListener)
    {
        this.loadListener = loadListener;
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        String serviceName = String.valueOf(paramMap.get("serviceName"));
        paramMap.remove("serviceName");
        new SimpleRequest(serviceName, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onSuccess(String data)
            {
                List<PjBean> pjBeans = JSON.parseArray(data, PjBean.class);
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(event_tag, pjBeans);
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
