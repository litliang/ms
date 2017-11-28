package com.yzb.card.king.ui.luxury.presenter.impl;

import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.luxury.model.FilterMenuModel;
import com.yzb.card.king.ui.luxury.model.impl.FilterMenuModelImpl;
import com.yzb.card.king.ui.luxury.presenter.FilterMenuPresenter;
import com.yzb.card.king.ui.luxury.view.FilterMenuView;

import java.util.Map;

/**
 * 功能：过滤条件列表
 *
 * @author:gengqiyun
 * @date: 2016/9/23
 */
public class FilterMenuPresenterImpl implements FilterMenuPresenter, BaseMultiLoadListener
{
    private FilterMenuModel model;
    private FilterMenuView view;

    public FilterMenuPresenterImpl(FilterMenuView view)
    {
        this.view = view;
        model = new FilterMenuModelImpl(this);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onLoadFilterMenuSucess(data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onLoadFilterMenuFail(msg);
    }
}
