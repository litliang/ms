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
 * 类  名：积分分享
 * 作  者：Li Yubing
 * 日  期：2016/6/23
 * 描  述：
 */
public class PointShareRequest extends BaseRequest implements HttpCallBackData {

    Map<String, Object> param = new HashMap<String, Object>();

    private DataCallBack callBack;

    /**
     * 接口名
     */
    private String serverName = CardConstant.INTEGRAL_POINTSHARE;

    public PointShareRequest(String nickName,String mobile,String relationship,String point,DataCallBack callBack){

        this.callBack = callBack;


        param.put("nickName", nickName);

        param.put("mobile", mobile);

        param.put("relationship",relationship);

        param.put("point", point);
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
        callBack.requestFailedDataCall(o+"",-1);


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
