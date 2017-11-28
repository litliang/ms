package com.yzb.card.king.ui.appwidget.popup.presenter.impl;

import com.yzb.card.king.ui.discount.bean.MenuItemBean;
import com.yzb.card.king.ui.appwidget.popup.model.ISort;
import com.yzb.card.king.ui.appwidget.popup.model.impl.SortImpl;
import com.yzb.card.king.ui.appwidget.popup.view.SortPopView;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/7 18:28
 */
public class SortPresenter
{
    private SortPopView view;
    private ISort model;
    public SortPresenter(SortPopView view)
    {
        this.view = view;
        model = new SortImpl();
        init();
    }

    private void init()
    {
        List<MenuItemBean> dataList = model.getData();
        view.setDataList(dataList);
    }
}
