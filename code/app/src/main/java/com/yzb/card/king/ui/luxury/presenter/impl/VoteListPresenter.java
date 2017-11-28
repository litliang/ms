package com.yzb.card.king.ui.luxury.presenter.impl;

import com.yzb.card.king.ui.app.base.BasePresenter;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.luxury.model.impl.VoteListModel;
import com.yzb.card.king.ui.luxury.view.VoteListView;

import java.util.Map;

/**
 * 功能：评价列表
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class VoteListPresenter implements BaseMultiLoadListener, BasePresenter
{
    private VoteListModel model;
    private VoteListView view;

    public VoteListPresenter(VoteListView view)
    {
        this.view = view;
        model = new VoteListModel(this);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onLoadVoteListSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onLoadVoteListFail(msg);
    }
}
