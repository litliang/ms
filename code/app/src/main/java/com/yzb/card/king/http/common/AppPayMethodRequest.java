package com.yzb.card.king.http.common;

import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：通过平台服务器可查询到用户的付款排序方式以及默认的付款排序方式
 * 作  者：Li Yubing
 * 日  期：2017/1/18
 * 描  述：
 */
public class AppPayMethodRequest extends BaseRequest {

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * 接口名
     */
    private String serverName = CardConstant.CARD_HILIFE_APPPAYMETHOD;

    private String sessionId = AppConstant.sessionId;

    private  boolean ifHandlerSessionId = true;

    public AppPayMethodRequest(String sessionId){

        serverName = CardConstant.CARD_HILIFE_APPQUERYPAYMENT;//默认付款排序方式

        /**
         * 检测sessionId是登录者的sessionId做相应的处理sessionId处理，如果不是，则无需处理
         */
        if(sessionId==sessionId){

            ifHandlerSessionId = true;

        }else {
            ifHandlerSessionId = true;
            this.sessionId = sessionId;
        }
    }

    /**
     *
     * @param showBalancePay
     * @param showEnvelopPay
     * @param showGiftPay
     * @param showIntegralPay
     * @param cardType
     * @param payType
     * @param limitDay
     */
    public AppPayMethodRequest(String sessionId,String showBalancePay, String showEnvelopPay,String showGiftPay, String showIntegralPay,String cardType, String payType,String limitDay )
    {

        if(sessionId == null){
            this.sessionId = AppConstant.sessionId;

            ifHandlerSessionId = true;
        }else{

            this.sessionId = sessionId;

            ifHandlerSessionId = false;
        }

        param.put("showBalancePay", showBalancePay);

        param.put("showEnvelopPay", showEnvelopPay);

        param.put("showGiftPay",showGiftPay);

        param.put("showIntegralPay", showIntegralPay);

        if(cardType!=null){

            param.put("cardType", cardType);//暂时
        }

        if(payType!=null){

            param.put("payType", payType);//暂时
        }

        if(limitDay!=null){

            param.put("limitDay", limitDay);//暂时
        }

    }

    /**
     * 用户可支付的银行卡列表构造器
     * @param cardType 1借记卡 2信用卡
     * @param payType  1可支付 0不可支付
     * @param limitDay 1是 0否
     * @param goldTicketParam
     * @param orderId
     * @param goodsAmount
     */
    public AppPayMethodRequest(int cardType,int payType,long limitDay,long orderId,GoldTicketParam goldTicketParam,double goodsAmount){

        serverName = CardConstant.CARD_HILIFE_APPCARDLIST;

        if(cardType != -1){
            param.put("cardType", cardType);
        }

        if(payType != -1){
            param.put("payType", payType);
        }

        param.put("limitDay",limitDay);

        param.put("industryId", goldTicketParam.getIndustryId());

        param.put("shopId", goldTicketParam.getShopId());

        param.put("storeId", goldTicketParam.getStoreId());

        param.put("goodsId", goldTicketParam.getGoodsId());

        param.put("orderId", orderId);

        param.put("goodsAmount", goodsAmount);

    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        isIfSaveSessionId(ifHandlerSessionId);
        //发送post请求
        sendPostRequest(setParams(sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}

