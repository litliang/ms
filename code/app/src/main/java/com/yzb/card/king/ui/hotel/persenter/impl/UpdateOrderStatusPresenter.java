package com.yzb.card.king.ui.hotel.persenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.model.impl.UpdateOrderStatusModel;

import java.util.Map;

/**
 * 功能：更新订单状态；
 *
 * @author:gengqiyun
 * @date: 2016/10/31
 */
public class UpdateOrderStatusPresenter implements BaseMultiLoadListener
{
    private UpdateOrderStatusModel model;
    private BaseViewLayerInterface view;

    public UpdateOrderStatusPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new UpdateOrderStatusModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(data,1);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,1);
    }

}
