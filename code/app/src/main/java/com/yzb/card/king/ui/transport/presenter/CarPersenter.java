package com.yzb.card.king.ui.transport.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.BusTypeBean;
import com.yzb.card.king.ui.transport.model.impl.CarImpl;
import com.yzb.card.king.ui.transport.model.impl.ICar;

import java.util.Map;

/**
 * 类名： CarPersenter
 * 作者： Lei Chao.
 * 日期： 2016-09-07
 * 描述：
 */
public class CarPersenter implements DataCallBack
{
    private BaseViewLayerInterface view;

    private ICar car;

    public CarPersenter(BaseViewLayerInterface carView)
    {
        this.view = carView;
        this.car = new CarImpl();
        car.setOnDataLoadFinish(this);
    }


    public void sendSelfRequest(Map<String, Object> param, String serviceName)
    {
        car.sendSelfDriveRequest(param, serviceName);
    }

    public void sendDailyRentRequest(Map<String, Object> params, String serviceName)
    {
        car.sendDailyRentRequest(params, serviceName);
    }



    @Override
    public void requestSuccess(Object o, int type)
    {
        view.callSuccessViewLogic(JSON.parseArray(String.valueOf(o), BusTypeBean.class),1);
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (o != null && o instanceof Map)
        {
            Map<String, String> map = (Map<String, String>) o;
            view.callFailedViewLogic(map.get(HttpConstant.SERVER_ERROR),1);
        }
    }
}