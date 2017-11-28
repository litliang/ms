package com.yzb.card.king.ui.travel.presenter.impl;

import com.yzb.card.king.bean.travel.TravelProduDetailBean;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.app.base.BasePresenter;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.travel.model.impl.TravelProductDetMImpl;
import com.yzb.card.king.ui.travel.view.TravelProductDetView;

import java.util.Map;

/**
 * 功能：旅游产品详情
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public class TravelProductDetPresenter implements BaseMultiLoadListener
{
    private BaseModel model;
    private TravelProductDetView view;

    public TravelProductDetPresenter(TravelProductDetView view)
    {
        this.view = view;
        model = new TravelProductDetMImpl(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onProductDetSucess(event_tag, (TravelProduDetailBean) data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onProductDetFail(msg);
    }

}
