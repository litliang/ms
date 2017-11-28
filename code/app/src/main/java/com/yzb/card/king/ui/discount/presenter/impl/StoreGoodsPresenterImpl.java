package com.yzb.card.king.ui.discount.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.model.StoreGoodsModel;
import com.yzb.card.king.ui.discount.model.impl.StoreGoodsModelImpl;
import com.yzb.card.king.ui.discount.presenter.StoreGoodsPresenter;
import com.yzb.card.king.ui.discount.view.StoreGoodsView;

import java.util.Map;

/**
 * 功能：店铺商品列表
 *
 * @author:gengqiyun
 * @date: 2016/9/21
 */
public class StoreGoodsPresenterImpl implements StoreGoodsPresenter, BaseMultiLoadListener
{
    private StoreGoodsModel model;
    private StoreGoodsView view;

    public StoreGoodsPresenterImpl(StoreGoodsView view)
    {
        this.view = view;
        model = new StoreGoodsModelImpl(this);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onLoadStoreGoodsSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onLoadStoreGoodsFail(msg);
    }
}
