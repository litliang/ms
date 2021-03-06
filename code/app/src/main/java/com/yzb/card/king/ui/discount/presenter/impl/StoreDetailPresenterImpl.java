package com.yzb.card.king.ui.discount.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.model.StoreDetailModel;
import com.yzb.card.king.ui.discount.model.impl.StoreDetailModelImpl;
import com.yzb.card.king.ui.discount.presenter.StoreDetailPresenter;

import java.util.Map;

/**
 * 功能：店铺详情
 *
 * @author:gengqiyun
 * @date: 2016/9/21
 */
public class StoreDetailPresenterImpl implements StoreDetailPresenter, BaseMultiLoadListener
{
    private StoreDetailModel model;
    private BaseViewLayerInterface view;

    public StoreDetailPresenterImpl(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new StoreDetailModelImpl(this);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(data,7);
    }

    @Override
    public void onListenError(String msg)
    {
     //   view.onLoadStoreDetailFail(msg);
        view.callFailedViewLogic(msg,7);
    }
}
