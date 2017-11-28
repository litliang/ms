package com.yzb.card.king.http.order;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：订单服务
 * 作  者：Li Yubing
 * 日  期：2017/9/16
 * 描  述：
 */
public class OrderServeRequest extends BaseRequest{

    private String serverName= CardConstant.ORDER_HOTELORDERSSERVICES;

    Map<String,Object> param =new HashMap<>();

    /**
     * 酒店订单服务构造器
     */
    public OrderServeRequest(){

        serverName= CardConstant.ORDER_HOTELORDERSSERVICES;

    }
    /**
     * 酒店订单服务构造器
     */
    public OrderServeRequest(long orderId){

        serverName= CardConstant.ORDER_ORDERSPAYFINISHINFO;

        param.put("orderId",orderId);

    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId,serverName,AppConstant.UUID,param),callBack);
    }
}
