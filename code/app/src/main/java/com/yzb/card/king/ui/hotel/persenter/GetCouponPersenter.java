package com.yzb.card.king.ui.hotel.persenter;

import com.yzb.card.king.bean.common.ActivityCouponParam;
import com.yzb.card.king.bean.common.ActivityDeductionStyleParam;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.model.GetCouponModel;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/23
 * 描  述：
 */
public class GetCouponPersenter implements DataCallBack{

    public static final  int GETCOUPONLIST_CODE = 80001;//查询代金券

    public static final int RECEIVECOUPON_CODE = 80002;//领取优惠券

    public static final int CANUSECOUPONLIST_CODE = 80003;//可使用优惠券列表

    public static final int ACTIVITYDEDUCTIONCHECK_CODE = 80004;//活动抵扣检测

    public static final int ACTIVITYDEDUCTIONAMOUNT_CODE = 80005;//活动抵扣金额

    public static final int QUERYCOOPERATIVEBANK_CODE = 80006;//合作银行列表

    public static final int CREATECOUPONORDER_CODE = 80007;//优惠券折扣券创建订单接口

    public static final int UPDATECOUPONPAYDETAIL_CODE = 80008;//优惠券订单详情更新

    public static final int ACTIVITYDEDUCTIONINFO_CODE = 80009;//活动抵扣查询

    public static final int DELETECOUONORDER_CODE = 80010;//删除优惠订单

    private BaseViewLayerInterface baseViewLayerInterface;

    private GetCouponModel getCouponModel;

    public GetCouponPersenter(BaseViewLayerInterface baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;

        getCouponModel = new GetCouponModel(this);

    }

    /**
     * 发送可领取优惠券列表
     * @param issuePlatform
     * @param industryId
     * @param shopId
     * @param storeId
     * @param goodsId
     */
    public void sendGetCouponListRequest(int issuePlatform,int industryId,long shopId,long storeId,long goodsId){

        getCouponModel.sendGetCouponListAction(issuePlatform, industryId, shopId, storeId, goodsId,GETCOUPONLIST_CODE);
    }

    /**
     * 发送可使用优惠券列表请求
     * @param issuePlatform
     * @param industryId
     * @param shopId
     * @param storeId
     * @param goodsId
     */
    public void sendCanUseCouponListAction(int issuePlatform,int industryId,long shopId,long storeId,long goodsId){

        getCouponModel.sendCanUseCouponListAction(issuePlatform, industryId, shopId, storeId, goodsId,CANUSECOUPONLIST_CODE);
    }
    /**
     * 发送活动抵扣查询请求
     * @param orderId
     * @param industryId
     * @param shopId
     * @param storeId
     * @param goodsId
     */
    public void sendActivityDeductionInfoRequest(long orderId,int industryId,long shopId,long storeId,long goodsId){

        getCouponModel.sendActivityDeductionInfoAction(orderId,industryId, shopId, storeId, goodsId,ACTIVITYDEDUCTIONINFO_CODE);
    }


    /**
     * 发送领取优惠券请求
     * @param actId
     */
    public void sendReceiveCouponRequest(long actId){

        getCouponModel.sendReceiveCouponAction( actId,RECEIVECOUPON_CODE);
    }

    /**
     * 发送活动抵扣检测请求
     * @param param
     */
    public void sendActivityDeductionCheckRequest(ActivityCouponParam param){

        getCouponModel.activityDeductionCheckAction(param.getOrderId(),param.getCouponId(),param.getCouponItemId(),param.getCouponAmount(),param.getBonusAmount(),param.getGiftcardAmount(),param.getOrderAmount(), ACTIVITYDEDUCTIONCHECK_CODE);

    }

    /**
     * 发送活动抵扣金额请求
     * @param orderAmount
     * @param activityCouponParam
     * @param activityDeductionStyleParam
     */
    public void sendActivityDeductionAmountRequest(double orderAmount, ActivityCouponParam activityCouponParam, ActivityDeductionStyleParam activityDeductionStyleParam){

        getCouponModel.activityDeductionAmountAction(orderAmount,activityCouponParam,activityDeductionStyleParam,ACTIVITYDEDUCTIONAMOUNT_CODE);
    }

    /**
     * 发送活动抵扣金额请求
     * @param pageStart
     * @param pageSize
     */
    public void sendQueryCoopertiveBankActionRequest(int  pageStart,int pageSize){

        getCouponModel.queryCoopertiveBankAction(pageStart,pageSize,QUERYCOOPERATIVEBANK_CODE);
    }

    /**
     * 发送创建优惠券请求
     * @param actId
     * @param orderAmount
     */
    public void sendCreateCouponOrderRequest(long actId, float orderAmount){

        getCouponModel.sendCreateCouponOrderAction(actId,orderAmount,CREATECOUPONORDER_CODE);
    }

    /**
     * 发送更新优惠券订单详情请求
     * @param orderId
     * @param orderAmount
     * @param orderTime
     * @param payType
     * @param payDetailId
     */
    public void updateCouponPayDetailRequest(String orderId, double orderAmount, String orderTime, String payType, String payDetailId) {

        getCouponModel.updateCouponPayDetailAction( orderId,  orderAmount,  orderTime,  payType,  payDetailId,UPDATECOUPONPAYDETAIL_CODE);
    }

    /**
     * 删除优惠券订单请求
     * @param orderId
     */
    public void delteCouponOrderRequest(long orderId){

        getCouponModel.delOrderInfoAction(orderId,DELETECOUONORDER_CODE);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        baseViewLayerInterface.callSuccessViewLogic(o,type);
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {

        baseViewLayerInterface.callFailedViewLogic(o,type);
    }


}
