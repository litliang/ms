package com.yzb.card.king.http.integral;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：收藏请求
 * 作  者：Li JianQiang
 * 日  期：2016/6/22
 * 描  述：
 */
public class ShopStopRequest extends BaseRequest {

    private String serverName=CardConstant.INTEGRAL_COLLECTIONSLIST;

    Map<String, Object> param = new HashMap<String, Object>();

    public ShopStopRequest(String type) {
        param.put("category", type);

    }


    @Override
    public void sendRequest(HttpCallBackData callBack) {
        sendPostRequest(setParams(AppConstant.sessionId,serverName,AppConstant.UUID,param),callBack);
    }
}
