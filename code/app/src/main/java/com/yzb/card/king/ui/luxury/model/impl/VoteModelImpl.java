package com.yzb.card.king.ui.luxury.model.impl;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.luxury.model.VoteModel;

import java.util.Map;

/**
 * 功能：点赞
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class VoteModelImpl implements VoteModel
{
    private BaseMultiLoadListener loadListener;

    public VoteModelImpl(BaseMultiLoadListener loadListener)
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
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(true, paramMap);
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
