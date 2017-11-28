package com.yzb.card.king.http.user;

import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.manage.UserManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/22
 * 描  述：
 */
public class LogoutRequest extends BaseRequest {

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * 接口名
     */
    private String serverName = CardConstant.USER_LOGOUT;


    public LogoutRequest( ){

        param.put("customerType", UserManager.getInstance().getUserBean().getCustomerType());
        param.put("customerMod","2");
    }



    @Override
    public void sendRequest(HttpCallBackData callBack) {
        //发送post请求
        sendPostRequest( setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID,param),callBack);
    }

}
