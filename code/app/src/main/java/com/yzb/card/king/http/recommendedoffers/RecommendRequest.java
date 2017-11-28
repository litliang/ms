package com.yzb.card.king.http.recommendedoffers;

import android.text.TextUtils;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： RecommendRequest
 * 作者： Lei Chao.
 * 日期： 2016-08-08
 * 描述：
 */
public class RecommendRequest extends BaseRequest {

    Map<String, Object> map = new HashMap<String, Object>();
    private String serviceName = CardConstant.card_app_discount_recommend;

    public RecommendRequest(String cityId) {
        map.put("cityId", cityId);
        map.put("pageStart", "0");
        map.put("pageSize", String.valueOf(AppConstant.MAX_PAGE_NUM));
    }

    public RecommendRequest(String cityId,String pageStart,String pageSize) {
        map.put("cityId", cityId);
        map.put("pageStart", pageStart);
        map.put("pageSize", pageSize);
    }

    /**
     * 礼品卡推荐列表
     * @param recommendPage  1商户优惠首页；2礼品卡首页；
     * @param ecardType  1心意卡；2礼品e卡
     */
    public RecommendRequest(String recommendPage,String ecardType) {

        serviceName = CardConstant.CARD_APP_QUERYGIFTCARDRECOMMEND;
        map.put("recommendPage", recommendPage);
        if(!TextUtils.isEmpty(ecardType)){
            map.put("ecardType", ecardType);
        }

    }

    @Override
    public void sendRequest(HttpCallBackData callBack) {
        sendPostRequest(setParams(AppConstant.sessionId, serviceName, AppConstant.UUID, map), callBack);
    }
}