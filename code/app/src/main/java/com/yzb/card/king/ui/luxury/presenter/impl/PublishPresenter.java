package com.yzb.card.king.ui.luxury.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.luxury.model.impl.PublishModel;

import java.util.Map;

/**
 * 功能：发布评论
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class PublishPresenter implements BaseMultiLoadListener
{
    private PublishModel model;
    private BaseViewLayerInterface view;

    public PublishPresenter(BaseViewLayerInterface view)
    {
        this.view = view;

        model = new PublishModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(true, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(data,8);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,8);
    }
}
