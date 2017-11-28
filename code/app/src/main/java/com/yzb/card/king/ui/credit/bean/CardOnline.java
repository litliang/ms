package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/22
 */
public class CardOnline implements Serializable
{
    private long id;

    private long createTime;

    private long bankId;

    private String status;
    //办卡用途id
    private long purposeId;
    //申请人数
    private int num;
    //卡片名称
    private String name;
    //卡片背景图
    private String photo;
    //积分说明
    private String pointIntro;
    //活动标题
    private String activityTitle;
    //活动说明
    private String activityIntro;
    //发卡组织
    private String origin;
    //卡免息期(天)
    private int gracePeriod;
    //卡等级
    private String cardRank;
    //年费政策
    private String feePolicy;
    //卡服务
    private String cardService;
    //
    private String cardQuestion;
    //申请办卡url
    private String url;
    //办卡进度url
    private String urlSchedule;
    //分期费率
    private List<CreditRate> creditRateStage;

    public void setId(long id)
    {
        this.id = id;
    }

    public long getId()
    {
        return id;
    }

    public long getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(long createTime)
    {
        this.createTime = createTime;
    }

    public long getBankId()
    {
        return bankId;
    }

    public void setBankId(long bankId)
    {
        this.bankId = bankId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public long getPurposeId()
    {
        return purposeId;
    }

    public void setPurposeId(long purposeId)
    {
        this.purposeId = purposeId;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public String getPointIntro()
    {
        return pointIntro;
    }

    public void setPointIntro(String pointIntro)
    {
        this.pointIntro = pointIntro;
    }

    public String getActivityTitle()
    {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle)
    {
        this.activityTitle = activityTitle;
    }

    public String getActivityIntro()
    {
        return activityIntro;
    }

    public void setActivityIntro(String activityIntro)
    {
        this.activityIntro = activityIntro;
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public int getGracePeriod()
    {
        return gracePeriod;
    }

    public void setGracePeriod(int gracePeriod)
    {
        this.gracePeriod = gracePeriod;
    }

    public String getCardRank()
    {
        return cardRank;
    }

    public void setCardRank(String cardRank)
    {
        this.cardRank = cardRank;
    }

    public String getFeePolicy()
    {
        return feePolicy;
    }

    public void setFeePolicy(String feePolicy)
    {
        this.feePolicy = feePolicy;
    }

    public String getCardService()
    {
        return cardService;
    }

    public void setCardService(String cardService)
    {
        this.cardService = cardService;
    }

    public String getCardQuestion()
    {
        return cardQuestion;
    }

    public void setCardQuestion(String cardQuestion)
    {
        this.cardQuestion = cardQuestion;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrlSchedule()
    {
        return urlSchedule;
    }

    public void setUrlSchedule(String urlSchedule)
    {
        this.urlSchedule = urlSchedule;
    }

    public List<CreditRate> getCreditRateStage()
    {
        return creditRateStage;
    }

    public void setCreditRateStage(List<CreditRate> creditRateStage)
    {
        this.creditRateStage = creditRateStage;
    }
}
