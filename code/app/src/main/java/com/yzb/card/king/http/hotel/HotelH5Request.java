package com.yzb.card.king.http.hotel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.util.UiUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/15 10:25
 */
public class HotelH5Request
{
    private Context context;

    public HotelH5Request(Context context)
    {
        this.context = context;
    }

    public void sendRequest(long hotelId, int goodsType, int pageType){
        SimpleRequest request = new SimpleRequest("SelectHotelPage");
        Map<String ,Object> param = new HashMap<>();
        param.put("hotelId",hotelId);
        param.put("goodsType",goodsType);
        param.put("pageType",pageType);
        request.setParam(param);
        request.sendRequest(new CallBack());

    }

    class CallBack extends HttpCallBackImpl{


        @Override
        public void onSuccess(Object o)
        {
            Map<String,String> map = JSON.parseObject(String.valueOf(o),Map.class);
            String url = map.get("pageUrl");
            Intent intent = new Intent(context, WebViewClientActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("url",url);
            intent.putExtra(AppConstant.INTENT_BUNDLE,bundle);
            UiUtils.startActivity(intent);
        }

        @Override
        public void onFailed(Object o)
        {

        }
    }
}
