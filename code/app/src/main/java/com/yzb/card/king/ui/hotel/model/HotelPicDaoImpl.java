package com.yzb.card.king.ui.hotel.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/31
 * 描  述：
 */
public class HotelPicDaoImpl implements IHotelSellingPointDetail {

    private DataCallBack dataCallBack;

    public HotelPicDaoImpl(DataCallBack dataCallBack)
    {
        this.dataCallBack = dataCallBack;
    }

    @Override
    public void selectHotelService(Map<String, Object> map, String service)
    {
        new SimpleRequest(service, map).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                dataCallBack.requestSuccess(o, IHotelSellingPointDetail.HOTEL_PIC);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o, IHotelSellingPointDetail.HOTEL_PIC);
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
