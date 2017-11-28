package com.yzb.card.king.ui.appwidget.popup.model.impl;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.popup.ChannelLoadListener;
import com.yzb.card.king.ui.appwidget.popup.model.ChannelModel;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：频道；
 *
 * @author:gengqiyun
 * @date: 2016/9/6
 */
public class ChannelModelImpl implements ChannelModel
{
    private ChannelLoadListener loadListener;

    public ChannelModelImpl(ChannelLoadListener listener)
    {
        loadListener = listener;
    }

    @Override
    public void getChannels(Map<String, Object> paramMap)
    {
        new SimpleRequest(CardConstant.card_app_customerchannellist, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onSuccess(String data)
            {
                LogUtil.i("==频道-result==:" + data);
                List<ChildTypeBean> displayBeans = new ArrayList<>();
                List<ChildTypeBean> hideBeans = new ArrayList<>();

                if (!TextUtils.isEmpty(data))
                {
                    List<ChildTypeBean> beans = JSON.parseArray(data, ChildTypeBean.class);
                    if (beans != null && beans.size() > 0)
                    {
                        for (ChildTypeBean item : beans)
                        {
                            item.typeImage = ServiceDispatcher.getImageUrl(item.typeImage);
                            //显示；
                            if ("1".equals(item.status))
                            {
                                displayBeans.add(item);
                            } else
                            {
                                hideBeans.add(item);
                            }
                        }
                    }
                }
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(displayBeans, hideBeans);
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
