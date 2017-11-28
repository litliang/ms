package com.yzb.card.king.ui.appwidget.popup.presenter.impl;

import com.yzb.card.king.ui.appwidget.popup.model.VoteCountModel;
import com.yzb.card.king.ui.appwidget.popup.model.impl.VoteCountModelImpl;
import com.yzb.card.king.ui.appwidget.popup.presenter.VoteCountPresenter;
import com.yzb.card.king.ui.appwidget.popup.view.VoteCountView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：评论数量；
 *
 * @author:gengqiyun
 * @date: 2016/9/5
 */
public class VoteCountPresenterImpl implements VoteCountPresenter, BaseMultiLoadListener
{
    private VoteCountModel model;
    private VoteCountView view;

    public VoteCountPresenterImpl(VoteCountView view)
    {
        this.view = view;
        model = new VoteCountModelImpl(this);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        model.getVoteCount(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetVoteCountSucess(false, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetVoteCountFail(msg);
    }

}
