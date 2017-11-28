package com.yzb.card.king.ui.appwidget.popup.model.impl;

import com.yzb.card.king.ui.appwidget.popup.model.INearby;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：附近弹窗数据层
 * 作者：殷曙光
 * 日期：2016/8/30 18:01
 */
public class NearbyImpl implements INearby
{
    private HttpCallBackData callBack;

    public NearbyImpl(HttpCallBackData callBack)
    {
        this.callBack = callBack;
    }

    @Override
    public void loadDataByParentId(String parentId)
    {
        SimpleRequest simpleRequest = new SimpleRequest("serviceName");
        Map<String, Object> param = new HashMap<>();
        param.put("parentId",parentId);
        simpleRequest.setParam(param);
        simpleRequest.sendRequest(callBack);
    }
}
