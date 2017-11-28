package com.yzb.card.king.ui.hotel.model;

import com.yzb.card.king.bean.hotel.HotelOrderParam;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.hotel.HotelOrderCreateRequest;
import com.yzb.card.king.http.hotel.HotelProductListRequest;
import com.yzb.card.king.http.hotel.HotelRoomsPolicysPriceRequest;
import com.yzb.card.king.http.hotel.QueryGoodsPlusRequest;
import com.yzb.card.king.http.hotel.RightsOrderCreateRequest;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/14
 * 描  述：
 */
public class HotelProductOrderModel {

    private DataCallBack dataCallBack;

    public  HotelProductOrderModel(DataCallBack dataCallBack){

        this.dataCallBack = dataCallBack;

    }

    /**
     *  查询房间保留时间
     * @param policysId
     * @param arrDate
     * @param type
     */
    public void sendHotelRoomPolicysTimeAction(String policysId, String arrDate, final int type){

        HotelRoomsPolicysPriceRequest request =   new HotelRoomsPolicysPriceRequest(policysId,arrDate,2);

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
     *  查询房间套餐剩余数量
     * @param policysId
     * @param arrDate
     * @param type
     */
    public void sendHotelRoomPolicysNuberAction(String policysId, String arrDate, final int type){

        HotelRoomsPolicysPriceRequest request =   new HotelRoomsPolicysPriceRequest(policysId,arrDate,1);

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
     *  酒店房间价格套餐
     * @param policysId
     * @param arrDate
     * @param depDate
     * @param type
     */
    public void sendHotelRoomPolicysPriceAction(String policysId, String arrDate,String depDate,int goodsQuantity, final int type){

        HotelRoomsPolicysPriceRequest request =   new HotelRoomsPolicysPriceRequest(policysId,arrDate,depDate,goodsQuantity);

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
     * 创建房间订单事件
     * @param hotelOrderParam
     * @param type
     */
    public void createRoomOrderAction(HotelOrderParam hotelOrderParam,final  int type){

        HotelOrderCreateRequest request =   new HotelOrderCreateRequest(hotelOrderParam);

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
     * 创建其它商品订单事件
     * @param hotelOrderParam
     * @param type
     */
    public void createOtherGoodsOrderAction(HotelOrderParam hotelOrderParam, final  int type)
    {

        HotelOrderCreateRequest request =   new HotelOrderCreateRequest(hotelOrderParam,-1);

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
     * 查询超值加购
     * @param industryId
     * @param type
     */
    public void queryGoodsPlusAction(String  industryId, final  int type)
    {

        QueryGoodsPlusRequest request =   new QueryGoodsPlusRequest(industryId);

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
     * 卡权益下单事件
     * @param hotelOrderParam
     * @param type
     */
    public void rightsOrderCreateAction(HotelOrderParam hotelOrderParam, final  int type)
    {

        RightsOrderCreateRequest request =   new RightsOrderCreateRequest(hotelOrderParam);

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
     * 限时抢购下单事件
     * @param hotelOrderParam
     * @param type
     */
    public void flashSaleCreateAction(HotelOrderParam hotelOrderParam, final  int type)
    {

        RightsOrderCreateRequest request =   new RightsOrderCreateRequest(hotelOrderParam,true);

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
