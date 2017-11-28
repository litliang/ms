package com.yzb.card.king.ui.hotel.model;

import com.yzb.card.king.bean.common.ActivityCouponParam;
import com.yzb.card.king.bean.common.ActivityDeductionStyleParam;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.common.CanReceiveCouponRequest;
import com.yzb.card.king.http.hotel.HotelRoomsPolicysPriceRequest;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/23
 * 描  述：
 */
public class GetCouponModel {

    private  DataCallBack dataCallBack;

    public GetCouponModel(DataCallBack callBack){

        this.dataCallBack = callBack;

    }

    /**
     * 可领取优惠券列表事件
     * @param issuePlatform
     * @param industryId
     * @param shopId
     * @param storeId
     * @param goodsId
     */
    public void sendGetCouponListAction(long issuePlatform,long industryId,long shopId,long storeId,long goodsId,final int type){

        CanReceiveCouponRequest request =   new CanReceiveCouponRequest( issuePlatform, industryId, shopId, storeId, goodsId);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    /**
     * 可使用优惠券列表表事件
     * @param issuePlatform
     * @param industryId
     * @param shopId
     * @param storeId
     * @param goodsId
     */
    public void sendCanUseCouponListAction(int issuePlatform,int industryId,long shopId,long storeId,long goodsId,final int type){

        CanReceiveCouponRequest request =   new CanReceiveCouponRequest( issuePlatform, industryId, shopId, storeId, goodsId);

        request.setCanUseCouponListUrl();

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 设置活动抵扣查询请求事项
     * @param industryId
     * @param shopId
     * @param storeId
     * @param goodsId
     */
    public void sendActivityDeductionInfoAction(long orderId,int industryId,long shopId,long storeId,long goodsId,final int type){

        CanReceiveCouponRequest request =   new CanReceiveCouponRequest( industryId, shopId, storeId, goodsId);

        request.setActivityDeductionInfoUrl(orderId);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    /**
     * 可领取优惠券列表事件
     * @param actId
     * @param type
     */
    public void sendReceiveCouponAction(long actId,final int type){

        CanReceiveCouponRequest request =   new CanReceiveCouponRequest( actId);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 活动抵扣检测
     * @param orderId
     * @param couponId
     * @param orderAmount
     * @param couponItemId
     * @param couponAmount
     * @param bonusAmount
     * @param giftcardAmount
     * @param type
     */
    public void activityDeductionCheckAction(long orderId,long couponId,long couponItemId,double couponAmount,double bonusAmount,double giftcardAmount,double orderAmount,final int type){

        CanReceiveCouponRequest request =   new CanReceiveCouponRequest( orderId,couponId,couponItemId,couponAmount,bonusAmount,giftcardAmount,orderAmount);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 活动抵扣金额事件
     * @param orderAmount
     * @param activityCouponParam
     * @param activityDeductionStyleParam
     * @param type
     */
    public void activityDeductionAmountAction(double orderAmount, ActivityCouponParam activityCouponParam, ActivityDeductionStyleParam activityDeductionStyleParam,final int type){

        CanReceiveCouponRequest request =   new CanReceiveCouponRequest( orderAmount,activityCouponParam,activityDeductionStyleParam);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 合作银行列表接口事件
     * @param pageStart
     * @param pageSize
     * @param type
     */
    public void queryCoopertiveBankAction(int  pageStart,int pageSize,final int type){

        CanReceiveCouponRequest request =   new CanReceiveCouponRequest( pageStart,pageSize);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     *  发送创建优惠券订单事件
     * @param actId
     * @param orderAmount
     * @param type
     */
    public void sendCreateCouponOrderAction(long actId, float orderAmount,final int type){

        CanReceiveCouponRequest request =   new CanReceiveCouponRequest( actId,orderAmount);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 更新礼品卡订单详情事件
     * @param orderId
     * @param orderAmount
     * @param orderTime
     * @param payType
     * @param payDetailId
     * @param type
     */
    public void updateCouponPayDetailAction(String orderId, double orderAmount, String orderTime, String payType, String payDetailId, final int type) {

        CanReceiveCouponRequest request =   new CanReceiveCouponRequest( orderId,  orderAmount,  orderTime,  payType,  payDetailId);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object o) {

                dataCallBack.requestSuccess(o,type);

            }

            @Override
            public void onFailed(Object o) {

                dataCallBack.requestFailedDataCall(o,type);

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
