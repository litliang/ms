package com.yzb.card.king.ui.order.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.ReasonForChangeBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.order.model.IReasonForChange;
import com.yzb.card.king.ui.order.model.ReasonForChangeImpl;
import com.yzb.card.king.ui.order.view.ReasonForChangeView;

import java.util.Map;

/**
 * 类名： ReasonForChangePresenter
 * 作者： Lei Chao.
 * 日期： 2016-10-12
 * 描述：退改签原因
 */
public class ReasonForChangePresenter implements DataCallBack
{

    private IReasonForChange iReasonForChange;
    private ReasonForChangeView view;


    public ReasonForChangePresenter(ReasonForChangeView view)
    {
        this.iReasonForChange = new ReasonForChangeImpl(this);
        this.view = view;
    }


    /**
     * 退改签原因请求
     *
     * @param params
     * @param serviceName
     */
    public void sendReasonForChangeReqeust(Map<String, Object> params, String serviceName)
    {
        this.iReasonForChange.sendReasonForChangeRequest(params, serviceName);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        view.getReasonFroChange(JSON.parseArray(String.valueOf(o), ReasonForChangeBean.class));
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (o instanceof Map)
        {
            Map<String, String> mao = (Map<String, String>) o;
            view.callFailedViewLogic(mao.get(HttpConstant.SERVER_ERROR),1);
        }
    }
}