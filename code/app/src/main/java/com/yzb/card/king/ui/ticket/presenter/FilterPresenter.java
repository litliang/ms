package com.yzb.card.king.ui.ticket.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.ticket.model.impl.FilterImpl;
import com.yzb.card.king.ui.ticket.model.IFilter;
import com.yzb.card.king.ui.ticket.view.FilterView;

import java.util.Map;

/**
 * 类名： FilterPresenter
 * 作者： Lei Chao.
 * 日期： 2016-10-11
 * 描述： 获取删选数据
 */
public class FilterPresenter implements DataCallBack
{

    private FilterView view;
    private IFilter iFilter;

    public FilterPresenter(FilterView view)
    {
        this.view = view;
        this.iFilter = new FilterImpl();
        this.iFilter.setOnDataLoadFinish(this);
    }

    public void sendFilterRequest(Map<String, Object> params, String serviceName)
    {
        this.iFilter.sendFilterDataRequest(params, serviceName);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        view.getFilter(JSON.parseArray(String.valueOf(o), Map.class));
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (o instanceof Map)
        {
            Map<String, String> map = (Map<String, String>) o;
            view.callFailedViewLogic(map.get(HttpConstant.SERVER_ERROR),1);
        }
    }
}