package com.yzb.card.king.http.common;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/7
 * 描  述：
 */
public class CommonReviewRequest extends BaseRequest{

    private String serverName= CardConstant.CARD_APP_VOTELIST;

    Map<String,Object> param =new HashMap<>();

    /**
     * 评论列表请求构造器
     * @param industryId  行业id
     * @param storeId  门店id
     * @param goodsIdTwo  二级商品id
     * @param goodsThree  三级商品id
     * @param voteType  评价分类
     * @param pageStart  起始条目
     */
    public CommonReviewRequest(int industryId,long storeId,long goodsIdTwo,long goodsThree,String voteType,int pageStart){
        serverName = CardConstant.CARD_APP_VOTELIST;
        // 公共参数
        param.put("industryId", industryId);
        param.put("storeId", storeId);
        if(goodsIdTwo != -1){
            param.put("goodsIdTwo", goodsIdTwo);
        }
        if( goodsThree != -1){
            param.put("goodsThree", goodsThree);
        }
        param.put("voteType", voteType);
        param.put("pageStart", pageStart);
        param.put("pageSize", AppConstant.MAX_PAGE_NUM);

    }

    /**
     * 评价数量请求构造器
     * @param industryId  行业id
     * @param storeId  门店id
     * @param goodsIdTwo  二级商品id
     * @param goodsThree  三级商品id
     */
    public CommonReviewRequest(int industryId,long storeId,long goodsIdTwo,long goodsThree){
        serverName = CardConstant.CARD_APP_VOTECOUNT;
        // 公共参数
        param.put("industryId", industryId);
        param.put("storeId", storeId);
        if(goodsIdTwo != -1){
            param.put("goodsIdTwo", goodsIdTwo);
        }
        if( goodsThree != -1){
            param.put("goodsThree", goodsThree);
        }
    }

    /**
     * 发表评论请求构造器
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
     */
    public CommonReviewRequest(int industryId,long shopId,long storeId,long goodsIdTwo,long goodsThree,String goodsName,int vote,String comment,String anonymous,String picCodes ,long orderId){
        serverName = CardConstant.CARD_APP_CREATEVOTE;
        // 公共参数
        param.put("industryId", industryId);
        param.put("shopId", shopId);
        param.put("storeId", storeId);
        if(goodsIdTwo != -1){
            param.put("goodsIdTwo", goodsIdTwo);
        }
        if( goodsThree != -1){
            param.put("goodsThree", goodsThree);
        }
        param.put("goodsName", goodsName);
        param.put("vote", vote);
        param.put("comment", comment);
        param.put("anonymous", anonymous);
        param.put("picCodes", picCodes);
        param.put("orderId",orderId);
    }

    /**
     * 添加评论回复请求构造器
     * @param voteId
     * @param replyContent
     */
    public CommonReviewRequest(long voteId,String replyContent){

        serverName = CardConstant.CARD_APP_CREATEVOTEREPLY;
        // 公共参数
        param.put("voteId", voteId);
        param.put("replyContent", replyContent);
    }

    /**
     * @param voteId    评论id
     * @param status    状态 (1点赞；2取消赞；)
     */
    public CommonReviewRequest(long voteId,int  status){

        serverName = CardConstant.CARD_APP_CREATELIKEREPLY;
        // 公共参数
        param.put("voteId", voteId);
        param.put("status", status);
    }

    /**
     * 评价回复列表
     * @param voteId
     * @param pageStart
     */
    public CommonReviewRequest(long voteId,int  pageStart, boolean flag){

        serverName = CardConstant.CARD_APP_VOTEREPLYLIST;
        // 公共参数
        param.put("voteId", voteId);
        param.put("pageStart", pageStart);
        param.put("pageSize", AppConstant.MAX_PAGE_NUM);
    }

    /**
     * 删除评价
     * @param voteId
     */
    public CommonReviewRequest(long voteId){

        serverName = CardConstant.card_delete_vote;
        // 公共参数
        param.put("voteId", voteId);
    }


    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        sendPostRequest(setParams(AppConstant.sessionId,serverName,AppConstant.UUID,param),callBack);
    }
}
