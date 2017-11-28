package com.yzb.card.king.ui.order.presenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.order.model.OrderServeImpl;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/16
 * 描  述：
 */
public class AppOrderServePreseter implements DataCallBack{

    public static final  int HOTELORDERSSERVICES_CODE = 10000;//酒店订单服务列表

    public static final  int ORDERSPAYFINISHINFO_CODE = 10001;//酒店订单服务列表

    private BaseViewLayerInterface baseViewLayerInterface;

    private OrderServeImpl impl;

    public AppOrderServePreseter(BaseViewLayerInterface baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;

        impl = new OrderServeImpl(this);

    }

    /**
     * 发送酒店订单服务列表请求
     */
    public void sendHotelOrdersServicesRequest(){


        impl.hotelOrdersServicesAction(HOTELORDERSSERVICES_CODE);
    }

    /**
     * 发送酒店订单服务列表请求
     */
    public void sendOrdersPayFinishInfoRequest(long orderId){


        impl.ordersPayFinishInfoAction(orderId,ORDERSPAYFINISHINFO_CODE);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        baseViewLayerInterface.callSuccessViewLogic(o,type);
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {

        baseViewLayerInterface.callFailedViewLogic(o,type);
    }
}
