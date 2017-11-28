package com.yzb.card.king.ui.luxury.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.luxury.model.VoteModel;
import com.yzb.card.king.ui.luxury.model.impl.VoteModelImpl;

import java.util.Map;

/**
 * 功能：点赞
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class VotePresenter implements  BaseMultiLoadListener
{
    private VoteModel model;
    private BaseViewLayerInterface view;

    public VotePresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new VoteModelImpl(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(data,3);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,3);
    }
}
