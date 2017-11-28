package com.yzb.card.king.http.user;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.AppUtils;

import org.xutils.common.util.MD5;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：登录请求类
 * 作  者：Li Yubing
 * 日  期：2016/6/22
 * 描  述：
 */
public class LoginRequest extends BaseRequest {

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * 接口名
     */
    private String serverName = CardConstant.USER_LOGIN;

    /**
     * @param account
     * @param passwd
     * @param type    1:密码验证；2：验证码验证
     */
    public LoginRequest(String account, String passwd, int type)
    {

        param.put("account", account);

        param.put("type", type+"");

        param.put("passwd",type ==1? MD5.md5(passwd) :passwd);

        param.put("loginIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));

        param.put("customerMod", "2");//1微信；2嗨生活；

//        String sysCode = null;
//        if( GlobalApp.regIdList.size()>1){
//             sysCode = GlobalApp.regIdList.get(0).get("regId");
//        }
//
//        if (TextUtils.isEmpty(sysCode)) {
//
//            sysCode = JPushInterface.getRegistrationID(GlobalApp.getInstance().getContext());
//        }
//        param.put("sysCode", sysCode);

    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        //发送post请求
        sendPostRequest(setParams("", serverName, AppConstant.UUID, param), callBack);
    }
}
