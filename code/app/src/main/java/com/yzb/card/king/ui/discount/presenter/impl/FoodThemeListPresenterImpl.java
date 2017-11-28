package com.yzb.card.king.ui.discount.presenter.impl;

import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.model.impl.FoodThemeListModelImpl;
import com.yzb.card.king.ui.discount.view.FoodThemeListView;

import java.util.Map;

/**
 * 功能：美食推荐主题
 *
 * @author:gengqiyun
 * @date: 2016/9/20
 */
public class FoodThemeListPresenterImpl implements BaseModel, BaseMultiLoadListener
{
    private BaseModel model;
    private FoodThemeListView view;

    public FoodThemeListPresenterImpl(FoodThemeListView view)
    {
        this.view = view;
        model = new FoodThemeListModelImpl(this);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        model.loadData(event_tag, paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onLoadFoodThemeListSucess(event_tag, data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onLoadFoodThemeListFail(msg);
    }
}
