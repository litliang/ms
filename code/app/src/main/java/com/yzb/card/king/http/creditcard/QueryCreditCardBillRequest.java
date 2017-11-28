package com.yzb.card.king.http.creditcard;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：用户已绑定信用卡请求类
 * 作  者：Li Yubing
 * 日  期：2016/8/9
 * 描  述：
 */
public class QueryCreditCardBillRequest extends BaseRequest
{

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * 接口名
     */
    private String serverName = CardConstant.card_app_queryCreditBill;

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        //发送post请求
        sendPostRequest(setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}
