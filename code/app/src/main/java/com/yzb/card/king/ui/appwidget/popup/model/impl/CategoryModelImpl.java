package com.yzb.card.king.ui.appwidget.popup.model.impl;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.popup.model.CategoryModel;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * 功能：分类菜单；
 *
 * @author:gengqiyun
 * @date: 2016/9/6
 */
public class CategoryModelImpl implements CategoryModel
{
    private BaseMultiLoadListener loadListener;

    public CategoryModelImpl(BaseMultiLoadListener listener)
    {
        loadListener = listener;
    }

    @Override
    public void getCategorys(Map<String, Object> paramMap)
    {
        new SimpleRequest(CardConstant.card_app_TypeCondition, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onSuccess(String data)
            {
                LogUtil.i("分类条件result:" + data);
                List<CategoryBean> categoryBeans = null;
                if (!TextUtils.isEmpty(data))
                {
                    categoryBeans = JSON.parseArray(data, CategoryBean.class);
                }
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(true, categoryBeans);
                }
            }

            @Override
            public void onFail(String failMsg)
            {
                if (loadListener != null)
                {
                    loadListener.onListenError(failMsg);
                }
            }
        }).sendPostRequest();
    }
}
