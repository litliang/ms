package com.yzb.card.king.http.user;

import android.text.TextUtils;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：验证码和验证验证码请求
 * 作  者：Li Yubing
 * 日  期：2016/6/22
 * 描  述：
 */
public class IdentifyingCodeRequest extends BaseRequest {

    Map<String, Object> param = new HashMap<String, Object>();

    public static String TYPE_REGISTER = "1";
    public static String TYPE_RESET_PWD = "2";
    public static String TYPE_SEND_NEW_PWD = "3";
    public static String TYPE_ADD_CREDIT = "4";
    public static String TYPE_VALID_MOBILE = "5";

    /**
     * 接口名
     */
    private String serverName;

    private String codeLength = null;

    public void setCodeLength(String codeLength){

        this.codeLength =  codeLength;
    }

    /**
     * 发送验证嘛
     *
     * @param mobile
     * @param custType
     * @param type
     */
    public IdentifyingCodeRequest(String mobile, String custType, String type)
    {

        serverName = CardConstant.card_api_identifyingCode;

        param.put("mobile", mobile);

        param.put("custType", custType);

        param.put("type", type);


    }

    /**
     * 验证验证码
     *
     * @param smsCode
     * @param mobile
     * @param custType
     * @param type
     */
    public IdentifyingCodeRequest(String smsCode, String mobile, String custType, String type)
    {

        serverName = CardConstant.card_api_provingIdCode;

        param.put("mobile", mobile);

        param.put("custType", custType);

        param.put("type", type);

        param.put("smsCode", smsCode);

    }


    /**
     *  其它银行验证码发送器
     */
    public IdentifyingCodeRequest()
    {

        serverName = CardConstant.CARD_HILIFE_APPQUICKPRECONSUME;



    }

    public void setRequestData(String  sortCode,String amount,String orderNo ){
        param.put("sortCode", sortCode);

        param.put("amount", amount);

        param.put("orderNo",  orderNo);
    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {

        if(CardConstant.card_api_identifyingCode.equals(serverName)){

            if(codeLength != null){
                param.put("codeLength", codeLength);
            }

        }

        //发送post请求
        sendPostRequest(setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}
