package com.yzb.card.king.ui.discount.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.food.FoodThemeBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：美食推荐主题
 *
 * @author:gengqiyun
 * @date: 2016/9/20
 */
public class FoodThemeListModelImpl extends BaseModelImpl implements BaseModel
{
    public FoodThemeListModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.RECOMMEND_FOOD_THEME_LIST_URL;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<FoodThemeBean> list = JSON.parseArray(data, FoodThemeBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, list);
        }
    }
}
