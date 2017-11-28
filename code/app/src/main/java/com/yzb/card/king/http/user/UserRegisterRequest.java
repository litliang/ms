package com.yzb.card.king.http.user;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;

import org.xutils.common.util.MD5;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：用户注册请求
 * 作  者：Li Yubing
 * 日  期：2016/8/5
 * 描  述：
 */
public class UserRegisterRequest extends BaseRequest {
    /**
     * 接口名
     */
    private String serverName = CardConstant.USER_REGISTER;

    Map<String, Object> param = new HashMap<String, Object>();
    /**
     *
     * @param mobile
     * @param passwd
     * @param identifyingCode
     */
    public UserRegisterRequest(String mobile,String passwd ,String identifyingCode) {

        param.put("account", mobile);

        param.put("passwd", MD5.md5(passwd));

        param.put("identifyingCode", identifyingCode);

       // param.put("customerType", customerType);

        param.put("customerMod", "2");
    }

    @Override
    public void sendRequest(HttpCallBackData callBack) {

        //发送post请求
        sendPostRequest( setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID,param),callBack);
    }
}