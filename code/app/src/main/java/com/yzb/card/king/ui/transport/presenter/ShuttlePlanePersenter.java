package com.yzb.card.king.ui.transport.presenter;

import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.transport.model.IShuttlePlane;
import com.yzb.card.king.ui.transport.model.impl.ShuttlePlaneImpl;

import java.util.Map;

/**
 * 类名： ShuttlePlanePersenter
 * 作者： Lei Chao.
 * 日期： 2016-09-05
 * 描述：
 */
public class ShuttlePlanePersenter implements DataCallBack
{
    private BaseViewLayerInterface view;

    private IShuttlePlane plane;


    public ShuttlePlanePersenter(BaseViewLayerInterface shuttleView)
    {
        this.view = shuttleView;
        plane = new ShuttlePlaneImpl();
        plane.setOnDataLoadFinish(this);
    }

    public void sendCardTypeRequest(Map<String, Object> params, String serviceName)
    {
        plane.sendCardTypeRequest(params, serviceName);
    }



    @Override
    public void requestSuccess(Object o, int type)
    {
        view.callSuccessViewLogic(o,1);
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {

        if (type == 1)
        {
            if (o != null && o instanceof Map)
            {
                Map<String, String> map = (Map<String, String>) o;
                view.callFailedViewLogic(map.get(HttpConstant.SERVER_ERROR),1);
            }
        }
    }
}