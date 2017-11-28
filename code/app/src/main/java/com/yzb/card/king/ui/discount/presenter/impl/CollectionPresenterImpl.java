package com.yzb.card.king.ui.discount.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.model.CollectionModel;
import com.yzb.card.king.ui.discount.model.impl.CollectionModelImpl;
import com.yzb.card.king.ui.discount.presenter.CollectionPresenter;

import java.util.Map;

/**
 * 功能：店铺详情
 *
 * @author:gengqiyun
 * @date: 2016/9/21
 */
public class CollectionPresenterImpl implements CollectionPresenter, BaseMultiLoadListener
{
    private CollectionModel model;
    private BaseViewLayerInterface view;

    public CollectionPresenterImpl(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new CollectionModelImpl(this);
    }

    @Override
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
