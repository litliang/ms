package com.yzb.card.king.http.common;

import android.text.TextUtils;

import com.yzb.card.king.bean.common.ActivityCouponParam;
import com.yzb.card.king.bean.common.ActivityDeductionStyleParam;
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
 * 日  期：2017/8/23
 * 描  述：查询优惠券、领取优惠券、查询可用优惠券、活动抵扣检测、创建购买折扣券订单
 */
public class CanReceiveCouponRequest extends BaseRequest {

    private String serverName = CardConstant.APP_CANRECEIVECOUPON_LIST;

    Map<String, Object> params = new HashMap<>();

    /**
     *   我的优惠券和代金券   构造器
     * @param issuePlatform
     * @param industryId
     * @param shopId
     * @param storeId
     * @param goodsId
     */
    public CanReceiveCouponRequest(long issuePlatform, long industryId, long shopId, long storeId, long goodsId)
    {


        params.put("industryId", industryId);
        params.put("shopId", shopId);
        params.put("storeId", storeId);
        params.put("goodsId", goodsId);

    }


    public CanReceiveCouponRequest( long industryId, long shopId, long storeId, long goodsId)
    {
        params.put("industryId", industryId);
        params.put("shopId", shopId);
        params.put("storeId", storeId);
        params.put("goodsId", goodsId);

    }

    public CanReceiveCouponRequest( long orderId,String serverName)
    {

        this.serverName = serverName;
        params.put("orderId", orderId);

    }

    public CanReceiveCouponRequest(String orderId, double orderAmount, String orderTime, String payType, String payDetailId) {

        serverName = CardConstant.APP_UPDATECOUPONPAYDETAIL_IF;
        params.put("orderId", orderId);
        params.put("orderAmount", orderAmount);
        params.put("orderTime",  orderTime);
        params.put("payMethod", payType);
        params.put("cardNo",  payDetailId);
    }


    /**
     *
     * @param actId
     * @param orderAmount
     */
    public CanReceiveCouponRequest(long actId, float orderAmount)
    {
        serverName = CardConstant.APP_CREATECOUPONORDER_IF;
        params.put("actId", actId);
        params.put("orderAmount", orderAmount);
        params.put("createIp",  AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));

    }

    /**
     * @param orderId        必传递
     * @param couponId       必传递
     * @param orderAmount    必传递
     * @param couponItemId
     * @param couponAmount
     * @param bonusAmount
     * @param giftcardAmount
     */
    public CanReceiveCouponRequest(long orderId, long couponId, long couponItemId, double couponAmount, double bonusAmount, double giftcardAmount, double orderAmount)
    {

        serverName = CardConstant.APP_ACTIVITYDEDUCTIONCHECK_HTTP;

        params.put("orderId", orderId);

        if (couponId != -1) {
            params.put("couponId", couponId);
        }
        if (couponItemId != -1) {
            params.put("couponItemId", couponItemId);
        }
        params.put("couponAmount", couponAmount);

        params.put("bonusAmount", bonusAmount);

        params.put("giftcardAmount", giftcardAmount);

        params.put("orderAmount", orderAmount);

    }

    /**
     *
     * @param orderAmount
     * @param activityCouponParam
     * @param activityDeductionStyleParam
     */
    public CanReceiveCouponRequest(double orderAmount, ActivityCouponParam activityCouponParam, ActivityDeductionStyleParam activityDeductionStyleParam)
    {

        serverName = CardConstant.APP_ACTIVITYDEDUCTIONAMOUNT_HTTP;

        params.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));

        params.put("orderId", activityCouponParam.getOrderId());

        if (activityCouponParam.getCouponId() != -1) {
            params.put("couponId", activityCouponParam.getCouponId());
        }
        if (activityCouponParam.getCouponItemId() != -1) {
            params.put("couponItemId", activityCouponParam.getCouponItemId());
        }

        if(activityCouponParam.getCouponAmount() !=0){
            params.put("couponAmount", activityCouponParam.getCouponAmount());
        }

        params.put("bonusAmount", activityCouponParam.getBonusAmount());

        params.put("giftcardAmount", activityCouponParam.getGiftcardAmount());

        params.put("refundType", activityDeductionStyleParam.getRefundType());

        params.put("payType", activityDeductionStyleParam.getPayType());

        if(!TextUtils.isEmpty(activityDeductionStyleParam.getPayDetailId())){
            params.put("payDetailId", activityDeductionStyleParam.getPayDetailId());
        }

        if(activityDeductionStyleParam.getBankActId() != -1){
            params.put("bankActId", activityDeductionStyleParam.getBankActId());
        }


        if(activityDeductionStyleParam.getBankStageId() != -1){
            params.put("bankStageId", activityDeductionStyleParam.getBankStageId());
        }


        params.put("orderAmount", orderAmount);

    }

    /**
     * 设置可使用优惠券列表url
     */
    public void setCanUseCouponListUrl(long issuePlatform)
    {

        if(issuePlatform == 1){//代金券
            serverName = CardConstant.APP_CANUSECASHCOUPONLIST_LIST;
        }else if(issuePlatform == 2){//优惠券
            serverName = CardConstant.card_app_canusecouponlist;
        }
    }
    /**
     * 设置活动抵扣查询url
     */
    public void setActivityDeductionInfoUrl(long orderId)
    {

        serverName = CardConstant.APP_ACTIVITYDEDUCTIONINFO_IF;

        params.put("orderId", orderId);

    }


    public CanReceiveCouponRequest(long actId)
    {
        serverName = CardConstant.card_app_receivecoupon;
        params.put("actId", actId);

    }

    public CanReceiveCouponRequest(int  pageStart,int pageSize)
    {
        serverName = CardConstant.QUERYCOOPERATIVEBANK_LIST;
        params.put("pageStart", pageStart);
        params.put("pageSize", pageSize);

    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, params), callBack);
    }
}
