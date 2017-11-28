package com.yzb.card.king.http.integral;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.integral.IntegralShareLinkman;
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
 * 类  名：新增联系人
 * 作  者：Li Yubing
 * 日  期：2016/6/27
 * 描  述：
 */
public class ContactsCreateRequest extends BaseRequest implements HttpCallBackData {

    Map<String, Object> param = new HashMap<String, Object>();

    private DataCallBack callBack;

    /**
     * 接口名
     */
    private String serverName = CardConstant.INTEGRAL_CONTACTSCREATE;

    public ContactsCreateRequest(String type, IntegralShareLinkman man, DataCallBack callBack)
    {

        this.callBack = callBack;

        param.put("type", type);

        param.put("nickName", man.getNickName());


        param.put("mobile", man.getMobile());

        param.put("relationship", man.getRelationship());

    }


    public void execute()
    {

        sendPostRequest(setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID, param), this);

    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {

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
        ToastUtil.i(GlobalApp.getInstance().getContext(), o + "");
    }

    @Override
    public void onCancelled(Object o)
    {

    }

    @Override
    public void onFinished()
    {

    }
}