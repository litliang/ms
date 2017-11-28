package com.yzb.card.king.ui.app.presenter;

import com.yzb.card.king.ui.app.model.SearchInputModel;
import com.yzb.card.king.ui.app.view.SearchInputView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：搜索列表
 *
 * @author:gengqiyun
 * @date: 2016/10/31
 */
public class SearchListPresenter implements  BaseMultiLoadListener
{
    private SearchInputModel model;
    private SearchInputView view;

    public SearchListPresenter(SearchInputView view)
    {
        this.view = view;
        model = new SearchInputModel(this);
    }

    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onSearchInputSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onSearchInputFail(msg);
    }
}
