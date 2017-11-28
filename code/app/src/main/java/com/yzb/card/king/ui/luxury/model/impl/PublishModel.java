package com.yzb.card.king.ui.luxury.model.impl;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：发布评论
 *
 * @author:gengqiyun
 * @date: 2016/9/22
 */
public class PublishModel extends BaseModelImpl
{
    public PublishModel(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(boolean event_tag, final Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.CARD_APP_CREATEVOTE;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, data);
        }
    }
}
