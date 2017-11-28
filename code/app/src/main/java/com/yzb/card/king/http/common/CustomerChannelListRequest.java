package com.yzb.card.king.http.common;

import com.sina.weibo.sdk.api.share.Base;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：个人频道请求类
 * 作  者：Li Yubing
 * 日  期：2016/9/1
 * 描  述：
 */
public class CustomerChannelListRequest extends BaseRequest{

    private String serverName= CardConstant.card_app_customerchannellist;

    Map<String,Object> params=new HashMap<>();

    public CustomerChannelListRequest(String parentId,String category)
    {
        params.put("parentId", parentId);
        params.put("category", category); // 经度；
    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId,serverName,AppConstant.UUID,params),callBack);
    }
}
