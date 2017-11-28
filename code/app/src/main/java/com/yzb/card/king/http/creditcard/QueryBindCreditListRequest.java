package com.yzb.card.king.http.creditcard;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/30
 * 描  述：绑定信用卡列表
 */
public class QueryBindCreditListRequest extends BaseRequest {


    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * 接口名
     */
    private String serverName = CardConstant.card_app_queryCreditBill;

    /**
     *
     */
    public QueryBindCreditListRequest(){

    }

    @Override
    public void sendRequest(HttpCallBackData callBack) {
        //发送post请求
        sendPostRequest( setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID,param),callBack);
    }
}
