package com.yzb.card.king.ui.luxury.presenter.impl;

import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.app.base.BasePresenter;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.luxury.model.impl.ReplylistModelImpl;
import com.yzb.card.king.ui.luxury.view.ReplylistView;

import java.util.Map;

/**
 * 功能：评价列表
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class ReplylistPresenter implements BaseMultiLoadListener, BasePresenter
{
    private BaseModel model;
    private ReplylistView view;

    public ReplylistPresenter(ReplylistView view)
    {
        this.view = view;
        model = new ReplylistModelImpl(this);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onLoadVoteReplylistSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onLoadVoteReplylistFail(msg);
    }
}
