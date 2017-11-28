package com.yzb.card.king.ui.transport.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.BusShopBean;
import com.yzb.card.king.ui.transport.model.IShopeSelect;
import com.yzb.card.king.ui.transport.model.impl.ShopeSelectImpl;

import java.util.Map;

/**
 * 类名： ShopeSelectPersenter
 * 作者： Lei Chao.
 * 日期： 2016-09-07
 * 描述：
 */
public class ShopeSelectPersenter implements DataCallBack
{
    private BaseViewLayerInterface view;

    private IShopeSelect shopeSelect;

    public ShopeSelectPersenter(BaseViewLayerInterface shopeSelectView)
    {
        this.view = shopeSelectView;
        shopeSelect = new ShopeSelectImpl();
        shopeSelect.setOnDataLoadFinish(this);
    }



    public void sendShopeSelectRequest(Map<String, Object> params, String serviceName)
    {
        shopeSelect.sendShopeSelectRequest(params, serviceName);
    }



    @Override
    public void requestSuccess(Object o, int type)
    {
        view.callSuccessViewLogic(JSON.parseArray(String.valueOf(o), BusShopBean.class),1);
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