package com.yzb.card.king.http.user;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 类  名：账户信息
 * 作  者：Li Yubing
 * 日  期：2016/7/12
 * 描  述：
 */
public class PersonInfoRequest extends BaseRequest {


    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * 接口名
     */
    private String serverName = CardConstant.card_app_personInfo;

    /**
     *
     */
    public PersonInfoRequest(){


    }

    @Override
    public void sendRequest(HttpCallBackData callBack) {

       // setIfEncryption(true);
        //发送post请求
        sendPostRequest( setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID,param),callBack);
    }
}
