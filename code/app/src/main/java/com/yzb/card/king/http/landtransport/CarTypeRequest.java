package com.yzb.card.king.http.landtransport;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： CarTypeRequest
 * 作者： Lei Chao.
 * 日期： 2016-09-05
 * 描述：
 */
public class CarTypeRequest extends BaseRequest
{

    Map<String, Object> map = new HashMap<String, Object>();
    private String mServiceName = "";


    public CarTypeRequest(Map<String, Object> prarms, String serviceName)
    {
        this.map = prarms;
        this.mServiceName = serviceName;
    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, this.mServiceName, AppConstant.UUID, map), callBack);
    }
}