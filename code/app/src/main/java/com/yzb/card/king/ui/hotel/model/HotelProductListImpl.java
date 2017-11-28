package com.yzb.card.king.ui.hotel.model;

import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.hotel.HotelProductListRequest;
import com.yzb.card.king.http.hotel.QueryGiftsListRequest;
import com.yzb.card.king.http.hotel.QueryLeftHotelListRequest;

/**
 * Created by 玉兵 on 2017/7/31.
 */

public class HotelProductListImpl {

    private DataCallBack dataCallBack;

    public  HotelProductListImpl(DataCallBack dataCallBack){

        this.dataCallBack = dataCallBack;

    }

    /**
     * 发送酒店地图产品事件
     * @param hotelParam
     * @param type
     */
    public void sendHotelProductMapAction(HotelProductListParam hotelParam, final int type){
        HotelProductListRequest request =   new HotelProductListRequest();
        request.setHotelMapProductInterfaceData(hotelParam);
       request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 酒店产品列表请求事件
     * @param hotelParam
     * @param type
     */
    public void sendHotelProductListRequest(HotelProductListParam hotelParam, final int type){

        HotelProductListRequest request =   new HotelProductListRequest(hotelParam);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 发送今日甩房请求
     * @param hotelParam
     * @param type
     */
    public void sendLeftHotelRoomRequest(HotelProductListParam hotelParam, final int type){

        QueryLeftHotelListRequest request =   new QueryLeftHotelListRequest(hotelParam);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 卡权益事件
     * @param hotelParam
     * @param type
     */
    public void queryGiftListAction(HotelProductListParam hotelParam, final int type){

        QueryGiftsListRequest request =   new QueryGiftsListRequest(hotelParam);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 限时抢购事件
     * @param hotelParam
     * @param type
     */
    public void queryFlashSaleAction(HotelProductListParam hotelParam, final int type){

        QueryGiftsListRequest request =   new QueryGiftsListRequest(hotelParam);

        request.setQueryFlashsaleListServer();

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
