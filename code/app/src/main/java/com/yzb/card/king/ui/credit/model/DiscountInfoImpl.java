package com.yzb.card.king.ui.credit.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.credit.bean.DiscountInfo;
import com.yzb.card.king.ui.credit.model.IDiscountInfo;

import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/14 11:42
 */
public class DiscountInfoImpl implements IDiscountInfo
{
    private HttpCallBackData callBack;

    public DiscountInfoImpl(HttpCallBackData callBack)
    {
        this.callBack = callBack;
    }

    @Override
    public void loadData(Map<String, Object> param)
    {
        SimpleRequest request = new SimpleRequest(CardConstant.QUERY_CREDIT_ACTIVITY_LIST,param);//
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                List<DiscountInfo> list = JSON.parseArray((String) o, DiscountInfo.class);
                callBack.onSuccess(list);
            }

            @Override
            public void onFailed(Object o)
            {
                callBack.onFailed(o);
            }
        });
    }
}
