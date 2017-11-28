package com.yzb.card.king.ui.transport.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.transport.model.QueryLineModel;
import com.yzb.card.king.ui.transport.model.impl.QueryLineModelImpl;
import com.yzb.card.king.ui.transport.presenter.QueryLinePresenter;
import com.yzb.card.king.ui.transport.view.QueryLineView;

import java.util.Map;

/**
 * 功能：航线
 *
 * @author:gengqiyun
 * @date: 2016/9/8
 */
public class QueryLinePresenterImpl implements QueryLinePresenter, BaseMultiLoadListener
{
    private QueryLineModel model;
    private QueryLineView view;

    public QueryLinePresenterImpl(QueryLineView view)
    {
        this.view = view;
        model = new QueryLineModelImpl(this);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onQueryLineSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onQueryLineFail(msg);
    }
}
