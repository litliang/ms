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
 * 日  期：2016/9/9
 * 描  述：
 */
public class LoadDebitRiseListRequest extends BaseRequest {

    private String serverName= CardConstant.setting_invoicelist;
    Map<String,Object> params=new HashMap<>();



    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId,serverName,AppConstant.UUID,params),callBack);
    }
}
