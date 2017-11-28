package com.yzb.card.king.ui.ticket.presenter;

import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.ticket.model.impl.RePayModel;

import java.util.Map;

/**
 * 功能：改签后需要支付接口；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class RePayPresenter implements BaseMultiLoadListener
{
    private RePayModel model;

    public RePayPresenter()
    {
        model = new RePayModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
    }

    @Override
    public void onListenError(String msg)
    {
    }
}
