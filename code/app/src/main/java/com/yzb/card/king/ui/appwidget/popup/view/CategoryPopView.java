package com.yzb.card.king.ui.appwidget.popup.view;

import com.yzb.card.king.ui.discount.bean.CategoryBean;

import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/2 16:23
 */
public interface CategoryPopView
{
    void setDataList(List<CategoryBean> dataList);

    Map<String,Object> getParam();
}
