package com.yzb.card.king.ui.credit.model;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.credit.bean.AdjustCard;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/12
 * 描  述：
 */
public class AdjustLimitDaoImpl implements IAdjustLimit
{

    private DataCallBack dataCallBack;

    public AdjustLimitDaoImpl(DataCallBack dataCallBack)
    {
        this.dataCallBack = dataCallBack;
    }

    @Override
    public void queryCreditCard(Map<String, Object> map)
    {
        SimpleRequest simpleRequest = new SimpleRequest(CardConstant.card_app_credit_list, map);
        simpleRequest.sendRequest(new HttpCallBackData()
        {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                List<AdjustCard> list = JSONArray.parseArray(String.valueOf(o), AdjustCard.class);
                dataCallBack.requestSuccess(list, IAdjustLimit.QUERYCREDITCARD_CODE);
            }

            @Override
            public void onFailed(Object o)
            {

                dataCallBack.requestFailedDataCall(o, IAdjustLimit.QUERYCREDITCARD_CODE);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }

}
