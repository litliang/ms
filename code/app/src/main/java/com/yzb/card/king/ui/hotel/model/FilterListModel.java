package com.yzb.card.king.ui.hotel.model;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.hotel.FilterListRequest;
import com.yzb.card.king.ui.hotel.persenter.FilterListPersenter;

/**
 * Created by 玉兵 on 2017/7/28.
 */

public class FilterListModel {

    private DataCallBack dataCallBack;

    public FilterListModel(DataCallBack dataCallBack) {

        this.dataCallBack = dataCallBack;
    }


    public void sendFilterListRequest(String industryId,String industryFun,String funType,String addrId){

        FilterListRequest request = new FilterListRequest(industryId,industryFun,funType,addrId);

        request.sendRequest(new HttpCallBackData(){
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,-1);

            }

            @Override
            public void onFailed(Object o) {
                dataCallBack.requestFailedDataCall(o,-1);
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
     * 发送酒店关键字搜索请求
     * @param cityName
     * @param keyWord
     */
    public void sendHotelKeywordSearchRequest(String cityName,String keyWord){

        FilterListRequest request = new FilterListRequest(cityName,keyWord);

        request.sendRequest(new HttpCallBackData(){
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,-4444);

            }

            @Override
            public void onFailed(Object o) {
                dataCallBack.requestFailedDataCall(o,-4444);
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
