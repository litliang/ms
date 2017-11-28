package com.yzb.card.king.ui.ticket.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.LowPriceBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.ticket.model.impl.FindLowImpl;
import com.yzb.card.king.ui.ticket.model.IFindLow;
import com.yzb.card.king.ui.ticket.view.FindLowView;

import java.util.Map;

/**
 * 类名： FindLowPresenter
 * 作者： Lei Chao.
 * 日期： 2016-10-11
 * 描述：
 */
public class FindLowPresenter implements DataCallBack {

    private IFindLow iFindLow;
    private FindLowView view;

    public FindLowPresenter(FindLowView view)
    {
        this.view = view;
        this.iFindLow = new FindLowImpl();
        this.iFindLow.setOnDataLoadFinish(this);
    }

    public void sendFindLowRequest(Map<String, Object> params, String serviceName)
    {
        this.iFindLow.sendFindLowRequest(params, serviceName);
    }

    public void sendFindLowRoundRequest(Map<String, Object> params, String serviceName)
    {
        this.iFindLow.sendRoundLowRequest(params, serviceName);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == IFindLow.SING_TYPE)
        {
            view.getLowPrice(JSON.parseArray(String.valueOf(o), LowPriceBean.class));
        } else if (type == IFindLow.ROUND_TYPE)
        {
            view.getRound(JSON.parseArray(String.valueOf(o), LowPriceBean.class));
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (o != null && o instanceof Map)
        {
            Map<String, String> map = (Map<String, String>) o;
            view.callFailedViewLogic(map.get(HttpConstant.SERVER_CODE),1);
        }
    }
}