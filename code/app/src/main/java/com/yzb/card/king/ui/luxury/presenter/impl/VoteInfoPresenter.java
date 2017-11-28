package com.yzb.card.king.ui.luxury.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.luxury.model.VoteInfoModel;
import com.yzb.card.king.ui.luxury.model.impl.VoteInfoModelImpl;

import java.util.Map;

/**
 * 功能：评价详情
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class VoteInfoPresenter implements  BaseMultiLoadListener
{
    private VoteInfoModel model;
    private BaseViewLayerInterface view;

    public VoteInfoPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new VoteInfoModelImpl(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(data,2);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,2);
    }
}
