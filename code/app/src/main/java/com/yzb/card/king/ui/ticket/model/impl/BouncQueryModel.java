package com.yzb.card.king.ui.ticket.model.impl;


import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.BouncQueryBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：退票查询；
 *
 * @author:gengqiyun
 * @date: 2016/10/15
 */
public class BouncQueryModel extends BaseModelImpl
{
    public BouncQueryModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_bouncquery;
        this.paramMap = paramMap;
        sendRequest();
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            BouncQueryBean bouncQueryBean = JSON.parseObject(data, BouncQueryBean.class);
            loadListener.onListenSuccess(true, bouncQueryBean);
        }
    }

    @Override
    protected int getTimeOut()
    {
        return 100 * 1000;
    }
}
