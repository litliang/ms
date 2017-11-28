package com.yzb.card.king.ui.ticket.model.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.bean.ticket.GoodActivityBean;
import com.yzb.card.king.bean.ticket.ShopGoodsActivityBean;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.interfaces.DiscountListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：机票优惠券列表；
 *
 * @author:gengqiyun
 * @date: 2016/10/12
 */
public class DiscountModel
{
    private DiscountListener loadListener;

    public DiscountModel(DiscountListener listener)
    {
        this.loadListener = listener;
    }

    public void commit(final Map<String, Object> paramMap)
    {
        final String flag = String.valueOf(paramMap.get("flag"));
        paramMap.remove("flag");
        new SimpleRequest(CardConstant.transport_querygoodsactivity, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onSuccess(String data)
            {
                if (data != null)
                {
                    //flag:  入参中的请求类型；值类型参考DiscountListener中req开头的的变量值；

                    //多个门店的优惠列表；每个门店的优惠列表又是一个List;
                    JSONArray jsonArray = JSON.parseArray(data);
                    List<ShopGoodsActivityBean> shopGoodsActivityBeans = new ArrayList<>();
                    List<GoodActivityBean> goodActivityBeans;
                    if (jsonArray != null && jsonArray.size() > 0)
                    {
                        for (int i = 0; i < jsonArray.size(); i++)
                        {
                            goodActivityBeans = JSON.parseArray(jsonArray.get(i).toString(), GoodActivityBean.class);
                            shopGoodsActivityBeans.add(new ShopGoodsActivityBean(goodActivityBeans));
                        }
                    }
                    loadListener.onListenSuccess(flag, shopGoodsActivityBeans);
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
