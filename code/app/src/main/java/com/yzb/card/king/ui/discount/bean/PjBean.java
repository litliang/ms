package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * @author gengqiyun
 * @date 2016/4/27.
 * 评价bean;
 */
public class PjBean implements Serializable
{
    /**
     * 评论id
     */
    private String voteId;
    /**
     * 详情id
     */
    private String detailId;
    /**
     * 评分
     */
    private float vote;
    /**
     * 评论内容
     */
    private String comment;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 评论人id
     */
    private String custId;
    /**
     * 是否已点赞
     */
    public int isLike;  //1:已赞，2：未赞；
    /**
     * 评论人昵称
     */
    private String nickName;
    /**
     * 评论人头像
     */
    public String custPicUrl;
    /**
     * 时间
     */
    private String createTime;
    /**
     * 浏览数
     */
    private int readNum = 0;
    /**
     * 赞次数
     */
    public int likeNum = 0;
    /**
     * 回复数
     */
    private int replyNum;
    /**
     * 评论图片
     */
    private String picUrls;
    /**
     * 1:本人；0：非本人；
     */
    private String peopleStatus;

    public String getPeopleStatus()
    {
        return peopleStatus;
    }

    public void setPeopleStatus(String peopleStatus)
    {
        this.peopleStatus = peopleStatus;
    }

    public float getVote()
    {
        return vote;
    }

    public void setVote(float vote)
    {
        this.vote = vote;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public int getReadNum()
    {
        return readNum;
    }

    public void setReadNum(int readNum)
    {
        this.readNum = readNum;
    }

    public int getLikeNum()
    {
        return likeNum;
    }

    public void setLikeNum(int likeNum)
    {
        this.likeNum = likeNum;
    }

    public String getVoteId()
    {
        return voteId;
    }

    public void setVoteId(String voteId)
    {
        this.voteId = voteId;
    }

    public String getDetailId()
    {
        return detailId;
    }

    public void setDetailId(String detailId)
    {
        this.detailId = detailId;
    }

    public String getCustPicUrl()
    {
        return custPicUrl;
    }

    public void setCustPicUrl(String custPicUrl)
    {
        this.custPicUrl = custPicUrl;
    }

    public String getPicUrls()
    {
        return picUrls;
    }

    public void setPicUrls(String picUrls)
    {
        this.picUrls = picUrls;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public int getIsLike()
    {
        return isLike;
    }

    public void setIsLike(int isLike)
    {
        this.isLike = isLike;
    }

    public int getReplyNum()
    {
        return replyNum;
    }

    public void setReplyNum(int replyNum)
    {
        this.replyNum = replyNum;
    }

    public String getGoodsName()
    {
        return goodsName;
    }

    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
    }

    public String getCustId()
    {
        return custId;
    }

    public void setCustId(String custId)
    {
        this.custId = custId;
    }
}
