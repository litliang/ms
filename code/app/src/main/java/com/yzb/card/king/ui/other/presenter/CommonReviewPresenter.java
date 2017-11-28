package com.yzb.card.king.ui.other.presenter;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.other.model.CommonReviewModelImp;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/7
 * 描  述：
 */
public class CommonReviewPresenter implements DataCallBack{

    public static final int VOTE_LIST_CODE = 34000;

    public static final int VOTE_COUNT_CODE = 34001;

    public static final int VOTE_CREATE_CODE = 34002;

    public static final int VOTE_CREATE_REPLY_CODE = 34003;

    public static final int VOTE_CREATE_LIKE_REPLY_CODE = 34004;

    public static final int VOTE_REPLY_List_CODE = 34005;

    public static final int VOTE_DELETE_CODE = 34006;

    private  BaseViewLayerInterface baseViewLayerInterface;

    private CommonReviewModelImp commonReviewModelImp;

    public CommonReviewPresenter(BaseViewLayerInterface baseViewLayerInterface){

        this.baseViewLayerInterface = baseViewLayerInterface;

        commonReviewModelImp = new CommonReviewModelImp(this);
    }

    /**
     * 发送请求评论数据
     * @param industryId
     * @param storeId
     * @param goodsIdTwo
     * @param goodsThree
     * @param viteType
     * @param pageStart
     */
    public void sendReviewInfoListRequest(int industryId,long storeId,long goodsIdTwo,long goodsThree,String viteType,int pageStart){

        commonReviewModelImp.sendReviewInfoListAction( industryId, storeId, goodsIdTwo, goodsThree, viteType, pageStart,VOTE_LIST_CODE);

    }

    /**
     * 发送请求评论数量
     * @param industryId
     * @param storeId
     * @param goodsIdTwo
     * @param goodsThree
     */
    public void sendReviewCountRequest(int industryId,long storeId,long goodsIdTwo,long goodsThree){

        commonReviewModelImp.sendReviewCountAction(industryId, storeId, goodsIdTwo, goodsThree,VOTE_COUNT_CODE);
    }

    /**
     * 发送请求创建新评论
     * @param industryId
     * @param shopId
     * @param storeId
     * @param goodsIdTwo
     * @param goodsThree
     * @param goodsName
     * @param vote
     * @param comment
     * @param anonymous
     * @param picCodes
     */
    public void sendCreateReviewRequest(int industryId,long shopId,long storeId,long goodsIdTwo,long goodsThree,String goodsName,int vote,String comment,String anonymous,String picCodes,long orderId){

        commonReviewModelImp.sendCreateReviewAction(industryId, shopId, storeId, goodsIdTwo, goodsThree, goodsName, vote, comment, anonymous, picCodes, orderId,VOTE_CREATE_CODE);
    }

    /**
     * 发送请求添加评价回复
     * @param voteId
     * @param replyContent
     */
    public void sendCreateVoteReplyRequest(long voteId,String replyContent){

        commonReviewModelImp.sendCreateVoteReplyAction(voteId, replyContent,VOTE_CREATE_REPLY_CODE);
    }

    /**
     * 发送请求添加点赞回复
     * @param voteId
     * @param status
     */
    public void sendCreateLikeReplyRequest(long voteId,int  status){

        commonReviewModelImp.sendCreateLikeReplyAction(voteId,  status,VOTE_CREATE_LIKE_REPLY_CODE);
    }

    /**
     * 发送请求评价回复列表
     * @param voteId
     * @param pageStart
     */
    public void sendVoteReplyListRequest(long voteId,int  pageStart){

        commonReviewModelImp.sendVoteReplyListAction( voteId,  pageStart,VOTE_REPLY_List_CODE);
    }

    /**
     * 发送请求删除评价
     * @param voteId
     */
    public void sednDeleteVote(long voteId){

        commonReviewModelImp.sendDeleteVoteAction(voteId,VOTE_DELETE_CODE);
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
