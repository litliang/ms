package com.yzb.card.king.ui.appwidget.popup.model.impl;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.popup.ChannelLoadListener;
import com.yzb.card.king.ui.appwidget.popup.model.ChannelUpdateModel;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：频道更新；
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class ChannelUpdateModelImpl implements ChannelUpdateModel
{
    private ChannelLoadListener loadListener;

    public ChannelUpdateModelImpl(ChannelLoadListener listener)
    {
        loadListener = listener;
    }

    @Override
    public void updateChannels(Map<String, Object> paramMap)
    {
        new SimpleRequest(CardConstant.card_app_customerchannelupdate, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onSuccess(String data)
            {
                LogUtil.i("==频道-result==:" + data);
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(null, null);
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
