package com.yzb.card.king.ui.appwidget.popup.model.impl;

import com.yzb.card.king.ui.appwidget.popup.model.ICategory;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;

import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/7 17:50
 */
public class CategoryImpl implements ICategory
{

    public CategoryImpl(HttpCallBackData callBack)
    {
        this.callBack = callBack;
    }

    private HttpCallBackData callBack;

    @Override
    public void loadData(Map<String,Object> paramMap)
    {
        SimpleRequest request = new SimpleRequest(CardConstant.card_app_TypeCondition, paramMap);
        request.sendRequest(callBack);
    }
}
