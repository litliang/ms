package com.yzb.card.king.ui.luxury.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.luxury.model.impl.KeywordsModel;

import java.util.Map;

/**
 * 功能：门店关键字列表
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class KeywordsPresenter implements BaseMultiLoadListener
{
    private KeywordsModel model;
    private BaseViewLayerInterface view;

    public KeywordsPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new KeywordsModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
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
