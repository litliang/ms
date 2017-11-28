package com.yzb.card.king.ui.appwidget.popup.model.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.appwidget.popup.model.VoteCountModel;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.LogUtil;

import java.util.Map;

/**
 * 功能：评论数量；
 *
 * @author:gengqiyun
 * @date: 2016/9/5
 */
public class VoteCountModelImpl implements VoteCountModel
{
    private BaseMultiLoadListener loadListener;

    public VoteCountModelImpl(BaseMultiLoadListener listener)
    {
        loadListener = listener;
    }

    @Override
    public void getVoteCount(Map<String, Object> paramMap)
    {
        String serviceName = String.valueOf(paramMap.get("serviceName"));
        paramMap.remove("serviceName");

        new SimpleRequest(serviceName, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onSuccess(String data)
            {
                LogUtil.i("评价次数-result:" + data);

                if (loadListener != null)
                {
                    JSONObject jsonObject = JSON.parseObject(data);
                    loadListener.onListenSuccess(true, jsonObject);
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
