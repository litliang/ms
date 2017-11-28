package com.yzb.card.king.http.giftcard;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/11
 * 描  述：
 */
public class GiftCombonRequest extends BaseRequest {


    Map<String, Object> params = new HashMap<>();

    private String serviceName = CardConstant.SELECTCARDRIGHTSINFO_IT;

    /**
     * 查询卡权益详情请求构造器
     * @param actId
     */
    public GiftCombonRequest(long actId){

        serviceName = CardConstant.SELECTCARDRIGHTSINFO_IT;

        params.put("actId", actId);

    }

    /**
     * 限时抢购详情
     */
    public void setFlashSalseServiceName(){

        serviceName = CardConstant.SELECTFLASHSALEINFO_IT;
    }

    /**
     * 查询礼品套餐增值项请求构造器
     * @param actId
     * @param flag
     */
    public GiftCombonRequest(long actId,int goodsType, boolean flag){

        serviceName = CardConstant.QUERYGIFTSINCREMENTITEMS_IT;

        params.put("actId", actId);

        params.put("goodsType", goodsType);

    }

    /**
     * 查询商品适用门店列表请求构造器
     * @param actId
     * @param goodsType
     */
    public GiftCombonRequest(long actId, int goodsType){

        serviceName = CardConstant.QUERYGOODSAPPLYSTORE_IT;

        params.put("actId", actId);

        params.put("goodsType", goodsType);

    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {

        sendPostRequest(setParams(AppConstant.sessionId, serviceName, AppConstant.UUID, params), callBack);

    }
}
