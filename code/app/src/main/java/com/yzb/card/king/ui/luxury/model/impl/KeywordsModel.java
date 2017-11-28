package com.yzb.card.king.ui.luxury.model.impl;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：门店关键字列表
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class KeywordsModel extends BaseModelImpl
{
    public KeywordsModel(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(boolean event_tag, final Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.CARD_APP_KEYWORDSLIST;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            if (loadListener != null)
            {
                loadListener.onListenSuccess(true, data);
            }
        }
    }
}
