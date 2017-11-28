package com.yzb.card.king.ui.ticket.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.PostFeeBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：邮费列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class GetPostFeeModel extends BaseModelImpl
{
    public GetPostFeeModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_querypostage;
        this.paramMap = paramMap;
        sendRequest();
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            List<PostFeeBean> bankInfos = JSON.parseArray(data, PostFeeBean.class);
            loadListener.onListenSuccess(true, bankInfos);
        }
    }
}
