package com.yzb.card.king.ui.appwidget.popup.presenter.impl;

import com.yzb.card.king.ui.appwidget.popup.model.CategoryModel;
import com.yzb.card.king.ui.appwidget.popup.model.impl.CategoryModelImpl;
import com.yzb.card.king.ui.appwidget.popup.presenter.CategoryPresenter;
import com.yzb.card.king.ui.appwidget.popup.view.CategoryView;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：分类菜单；
 *
 * @author:gengqiyun
 * @date: 2016/9/6
 */
public class CategoryPresenterImpl implements CategoryPresenter, BaseMultiLoadListener
{
    private CategoryModel model;
    private CategoryView view;

    public CategoryPresenterImpl(CategoryView view)
    {
        this.view = view;
        model = new CategoryModelImpl(this);
    }



    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        model.getCategorys(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.onGetCategorySucess(data);
    }

    @Override
    public void onListenError(String msg)
    {
        view.onGetCategoryFail(msg);
    }

}
