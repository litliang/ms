package com.yzb.card.king.ui.other.model;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.common.CanReceiveCouponRequest;
import com.yzb.card.king.http.common.CommonReviewRequest;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/7
 * 描  述：
 */
public class CommonReviewModelImp {

    private DataCallBack dataCallBack;

    public CommonReviewModelImp( DataCallBack dataCallBack){

        this.dataCallBack = dataCallBack;
    }

    /**
     * 评论信息列表事件
     * @param industryId  行业id
     * @param storeId  门店id
     * @param goodsIdTwo  二级商品id
     * @param goodsThree  三级商品id
     * @param voteType  评价分类
     * @param pageStart  起始条目
     */
    public void sendReviewInfoListAction(int industryId,long storeId,long goodsIdTwo,long goodsThree,String voteType,int pageStart,final int type){

        CommonReviewRequest request =   new CommonReviewRequest(  industryId, storeId, goodsIdTwo, goodsThree, voteType, pageStart);

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
     *  评论数量
     * @param industryId  行业id
     * @param storeId  门店id
     * @param goodsIdTwo  二级商品id
     * @param goodsThree  三级商品id
     * @param type
     */
    public void sendReviewCountAction(int industryId,long storeId,long goodsIdTwo,long goodsThree,final int type){

        CommonReviewRequest request =   new CommonReviewRequest(  industryId, storeId, goodsIdTwo, goodsThree);

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
     * 发布评论事件
     * @param industryId  行业id
     * @param shopId  商家id
     * @param storeId 评价产品id
     * @param goodsIdTwo  二级商品id
     * @param goodsThree  三级商品id
     * @param goodsName  商品名称
     * @param vote  评分
     * @param comment  点评内容
     * @param anonymous  是否匿名点评(１是；０否)
     * @param picCodes  评论图片(多个图片使用 ,分割)
     * @param type
     */
    public void sendCreateReviewAction(int industryId,long shopId,long storeId,long goodsIdTwo,long goodsThree,String goodsName,int vote,String comment,String anonymous,String picCodes,long orderId,final int type){

        CommonReviewRequest request =   new CommonReviewRequest( industryId, shopId, storeId, goodsIdTwo, goodsThree, goodsName, vote, comment, anonymous, picCodes,orderId);

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
     * 添加评论回复事件
     * @param voteId
     * @param replyContent
     * @param type
     */
    public void sendCreateVoteReplyAction(long voteId,String replyContent,final int type){

        CommonReviewRequest request =   new CommonReviewRequest(  voteId, replyContent);

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
     * 添加点赞回复事件
     * @param voteId
     * @param status
     * @param type
     */
    public void sendCreateLikeReplyAction(long voteId,int  status,final int type){

        CommonReviewRequest request =   new CommonReviewRequest(  voteId,  status);

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
     * 评价回复列表事件
     * @param voteId
     * @param pageStart
     * @param type
     */
    public void sendVoteReplyListAction(long voteId,int  pageStart,final int type){

        CommonReviewRequest request =   new CommonReviewRequest(  voteId,  pageStart,false);

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
     * 删除评价事件
     * @param voteId
     * @param type
     */
    public void sendDeleteVoteAction(long voteId,final int type){

        CommonReviewRequest request =   new CommonReviewRequest( voteId);

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
