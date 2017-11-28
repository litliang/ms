package com.yzb.card.king.ui.ticket.model.impl;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.ticket.model.ISingleList;
import com.yzb.card.king.ui.ticket.presenter.SingleListPresenter;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/5 18:16
 */
public class SingleListImpl implements ISingleList
{
    private SingleListPresenter.RequestCallback callBack;

    public SingleListImpl(SingleListPresenter.RequestCallback callBack)
    {
        this.callBack = callBack;
    }

    @Override
    public void loadHeaderPrice(Map<String, Object> param)
    {
        final Date depDay = DateUtil.string2Date(String.valueOf(param.get("depDay")), DateUtil.DATE_FORMAT_DATE);
        SimpleRequest request = new SimpleRequest(CardConstant.card_app_query_air_leastamount, param);
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                List<Map> mapList = JSON.parseArray(String.valueOf(o), Map.class);
                Map map = mapList.get(0);
                String prePrice, todayPrice, nextPrice;
                if (isToday(depDay))
                {
                    prePrice = "";
                    todayPrice = getPrice(map.get("1"));
                    nextPrice = getPrice(map.get("2"));
                } else
                {
                    prePrice = getPrice(map.get("1"));
                    todayPrice = getPrice(map.get("2"));
                    nextPrice = getPrice(map.get("3"));
                }
                if (callBack != null)
                {
                    callBack.onSuccess(prePrice, todayPrice, nextPrice);
                }
            }

            @Override
            public void onFailed(Object o)
            {

            }
        });
    }

    private String getPrice(Object object)
    {
        int price = floatStr2int(object);
        String priceStr = "";
        if (price != 0)
        {
            priceStr = UiUtils.getString(R.string.ticket_price, price);
        }
        return priceStr;
    }

    private int floatStr2int(Object object)
    {
        int value = 0;
        if (object != null)
        {
            String str = String.valueOf(object);
            if (!TextUtils.isEmpty(str) && !"null".equals(str))
            {
                value = (int) Float.parseFloat(str);
            }
        }
        return value;
    }

    private boolean isToday(Date date)
    {
        String str1 = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE);
        String str2 = DateUtil.date2String(new Date(), DateUtil.DATE_FORMAT_DATE);
        return str2.equals(str1);
    }

    @Override
    public void saveList(List<PlaneTicket> dataList)
    {

    }
}
