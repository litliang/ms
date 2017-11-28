package com.yzb.card.king.ui.order.model;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.hotel.HotelBankActQueryRequest;
import com.yzb.card.king.http.order.OrderServeRequest;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/16
 * 描  述：
 */
public class OrderServeImpl {


    private DataCallBack dataCallBack;

    public OrderServeImpl(DataCallBack dataCallBack){

        this.dataCallBack = dataCallBack;

    }

    /**
     * 酒店订单服务列表事件
     */
    public void hotelOrdersServicesAction(final  int type ){

        OrderServeRequest request =   new OrderServeRequest();

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
     * 订单支付完成信息
     */
    public void ordersPayFinishInfoAction(long orderId,final  int type ){

        OrderServeRequest request =   new OrderServeRequest(orderId);

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
