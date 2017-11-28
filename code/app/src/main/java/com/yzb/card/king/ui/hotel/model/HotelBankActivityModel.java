package com.yzb.card.king.ui.hotel.model;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.hotel.HotelBankActQueryRequest;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/28
 * 描  述：
 */
public class HotelBankActivityModel {

    private DataCallBack dataCallBack;

    public HotelBankActivityModel(DataCallBack dataCallBack){


        this.dataCallBack = dataCallBack;

    }

    /**
     * 发送查询查询银行优惠活动
     */
    public void quereyBankPreAcitivityAction(int cityId,String bankIds,int industryId,int actType,int effMonth,int pageStart,final  int type ){

        HotelBankActQueryRequest request =   new HotelBankActQueryRequest(cityId,bankIds,industryId,actType,effMonth,pageStart);

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
     * 发送银行优惠活动详情时间
     */
    public void quereyBankPreAcitivityDetailAction(int industryId,long shopId,long storeId,int goodsType,long goodsId,String startDate,final  int type ){

        HotelBankActQueryRequest request =   new HotelBankActQueryRequest(industryId,shopId,storeId,goodsType,goodsId,startDate);

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
     * 发送查询银行生活分期请求
     * @param cityId
     * @param bankIds
     * @param industryId
     * @param stage
     * @param pageStart
     * @param type
     */
    public void sendQueryBankLifeStageAction(int cityId,String bankIds,int industryId,int stage,int pageStart,final  int type){


        HotelBankActQueryRequest request =   new HotelBankActQueryRequest(cityId,bankIds,industryId,stage,pageStart);

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
     * 产品支持银行分期事件
     * @param industryId
     * @param shopId
     * @param storeId
     * @param goodsType
     * @param goodsId
     * @param goodsAmount
     */
    public void sendProductBankLifeStageAction(int industryId,long shopId,long storeId,int goodsType,long goodsId,double goodsAmount,final  int type){


        HotelBankActQueryRequest request =   new HotelBankActQueryRequest(industryId,shopId,storeId,goodsType,goodsId,goodsAmount);

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
