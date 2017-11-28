package com.yzb.card.king.http.common;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/1/18
 * 描  述：
 */
public class CreateFastPaymentOrderRequest extends BaseRequest {

    Map<String, Object> param = new HashMap<String, Object>();

    /**
     * 接口名
     */
    private String serverName = CardConstant.CARD_HILIFE_CREATEFASTPAYMENTORDER;

    /**
     *
     * @param
     * @param amount  生成方金额
     * @param createTime  生成时间
     * @param flag  生成方收付方向
     * @param customerType  生成方账户类型
     * @param provideParamsde  二维码生成方参数
     * @param consumeParamsde  二维码扫描方参数
     */
    public CreateFastPaymentOrderRequest( String amount, String createTime, String flag, String customerType, String provideParamsde, String consumeParamsde,String provideAccount)
    {

        param.put("amount", amount);

        param.put("createTime", createTime);

        param.put("flag", flag);

        param.put("provideAccount", provideAccount);

        param.put("customerType", customerType);

        param.put("provideParamsde", provideParamsde);

        param.put("consumeParamsde", consumeParamsde);


    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        //发送post请求
        sendPostRequest(setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID, param), callBack);
    }
}

