package com.yzb.card.king.http.integral;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：常旅客计划列表
 * 作  者：Li Yubing
 * 日  期：2016/6/22
 * 描  述：
 */
public class FrequentPassengerPlanRequest extends BaseRequest implements HttpCallBackData {

    Map<String, Object> param = new HashMap<String, Object>();

    private DataCallBack callBack;

    /**
     * 接口名
     */
    private String serverName = CardConstant.INTEGRAL_FREQUENTPASSENGERPLAN;

    public FrequentPassengerPlanRequest(String airlineId,DataCallBack callBack){

        this.callBack = callBack;

        param.put("airlineId",airlineId);

    }

    public void execute()
    {

        sendPostRequest(setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID, param), this);

    }

    @Override
    public void onStart()
    {

    }

    @Override
    public void onSuccess(Object o)
    {
        Map<String, String> result = JSON.parseObject(o + "", Map.class);


        callBack.requestSuccess(result,-1);
    }

    @Override
    public void onFailed(Object o)
    {
        callBack.requestFailedDataCall(o,-1);


        ToastUtil.i(GlobalApp.getInstance().getContext(),o+"");
    }

    @Override
    public void onCancelled(Object o)
    {

    }

    @Override
    public void onFinished()
    {

    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {

    }
}
