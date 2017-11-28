package com.yzb.card.king.http.integral;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：获取信用卡对象
 * 作  者：Li JianQiang
 * 日  期：2016/8/16
 * 描  述：
 */
public class SelectCardRequest extends BaseRequest {

    Map<String, Object> param = new HashMap<String, Object>();

    private String serverName=CardConstant.CARD_APP_CREDIT_BILL_DETAIL;

    public SelectCardRequest(int id) {

        param.put("type", 2);

        param.put("detailId", id);

    }

    @Override
    public void sendRequest(HttpCallBackData callBack) {
        sendPostRequest(setParams(AppConstant.sessionId,serverName,AppConstant.UUID,param),callBack);
    }
}
