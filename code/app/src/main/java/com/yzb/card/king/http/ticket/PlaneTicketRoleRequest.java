package com.yzb.card.king.http.ticket;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/5
 * 描  述：
 */
public class PlaneTicketRoleRequest extends BaseRequest {

    private String serverName= CardConstant.card_app_queryexitmodificationinfo;

    Map<String,Object> params=new HashMap<>();

    public PlaneTicketRoleRequest(){
        params.put("id","1");
    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId,serverName,AppConstant.UUID,params),callBack);
    }
}
