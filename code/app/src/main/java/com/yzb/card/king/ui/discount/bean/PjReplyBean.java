package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * Created by gengqiyun on 2016/4/27.
 * 评价回复；
 */
public class PjReplyBean implements Serializable
{
    /**
     * 回复id
     */
    private String replyId;
    /**
     * 回复时间
     */
    private String createTime; //格式：2017-02-06
    /**
     * 回复内容
     */
    private String replyContent;
    /**
     * 回复人昵称
     */
    private String nickName;
    /**
     * 回复人头像
     */
    private String custPicUrl;

    public String getReplyId()
    {
        return replyId;
    }

    public void setReplyId(String replyId)
    {
        this.replyId = replyId;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getReplyContent()
    {
        return replyContent;
    }

    public void setReplyContent(String replyContent)
    {
        this.replyContent = replyContent;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getCustPicUrl()
    {
        return custPicUrl;
    }

    public void setCustPicUrl(String custPicUrl)
    {
        this.custPicUrl = custPicUrl;
    }
}
