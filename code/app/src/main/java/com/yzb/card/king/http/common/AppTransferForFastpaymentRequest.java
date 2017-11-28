package com.yzb.card.king.http.common;

import android.text.TextUtils;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.AppUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/2/8
 * 描  述：
 */
public class AppTransferForFastpaymentRequest extends BaseRequest {

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * 接口名
     */
    private String serverName = CardConstant.CARD_HILIFE_APPTRANSFERFORFASTPAYMENT;

    private String paySessionId ;

    /**
     *
     * @param debitSessionId  付款方sessionId
     * @param creditSessionId  收款方sessionId
     * @param orderNo      订单号
     * @param amount    订单金额
     * @param status  非浦发银行卡状态
     * @param debitType  付款方式
     * @param debitSortCode  付款银行关联码
     * @param token    他行消费鉴权返回
     * @param identifyingCode  验证码
     */
    public AppTransferForFastpaymentRequest(String debitSessionId,String creditSessionId,String orderNo,String amount,String status,String debitType,String debitSortCode,String token,String customerId,String identifyingCode,String flag,String url){

        paySessionId = debitSessionId;

        param.put("debitSessionId", debitSessionId);

        param.put("creditSessionId", creditSessionId);

        param.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));

        param.put("orderNo", orderNo);

        param.put("amount", amount);

        param.put("status", status);

        param.put("debitType", debitType);

        param.put("debitSortCode", debitSortCode);

        param.put("token", TextUtils.isEmpty(token)?"":token);

        param.put("customerId",TextUtils.isEmpty(customerId)?"":customerId);

        param.put("identifyingCode", identifyingCode);

        param.put("flag", flag);

        param.put("notifyUrl", url);

    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        isIfSaveSessionId(false);//无需更新sessionId
        //发送post请求
        sendPostRequest(setParams(paySessionId, serverName, AppConstant.UUID, param), callBack);
    }
}
