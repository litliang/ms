package com.yzb.card.king.ui.appwidget.popup.presenter.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.ui.appwidget.popup.model.ICategory;
import com.yzb.card.king.ui.appwidget.popup.model.impl.CategoryImpl;
import com.yzb.card.king.ui.appwidget.popup.view.CategoryPopView;
import com.yzb.card.king.http.HttpCallBackImpl;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/2 16:20
 */
public class CategPopPresenter
{
    private ICategory model;
    private CategoryPopView view;
    private Object Map;


    public CategPopPresenter(CategoryPopView view)
    {
        this.view = view;
        model = new CategoryImpl(new CallBack());
        init();
    }

    private void init(){
        java.util.Map<String, Object> paramMap = view.getParam();
        model.loadData(paramMap);
    }

    class CallBack extends HttpCallBackImpl{
        @Override
        public void onSuccess(Object o)
        {
            List<CategoryBean> dataList = JSON.parseArray(String.valueOf(o), CategoryBean.class);
            view.setDataList(dataList);
        }

        @Override
        public void onFailed(Object o)
        {
        }
    }
}
