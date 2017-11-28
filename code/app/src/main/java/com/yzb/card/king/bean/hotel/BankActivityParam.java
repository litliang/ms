package com.yzb.card.king.bean.hotel;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/28
 * 描  述：
 */
public class BankActivityParam {

   private int cityId;

    private  String bankIds;

    private  int industryId;

    private   int actType;

    private  int effMonth;

    private  int pageStart;

    private int stage = -1;

    public int getCityId()
    {
        return cityId;
    }

    public void setCityId(int cityId)
    {
        this.cityId = cityId;
    }

    public String getBankIds()
    {
        return bankIds;
    }

    public void setBankIds(String bankIds)
    {
        this.bankIds = bankIds;
    }

    public int getIndustryId()
    {
        return industryId;
    }

    public void setIndustryId(int industryId)
    {
        this.industryId = industryId;
    }

    public int getActType()
    {
        return actType;
    }

    public void setActType(int actType)
    {
        this.actType = actType;
    }

    public int getEffMonth()
    {
        return effMonth;
    }

    public void setEffMonth(int effMonth)
    {
        this.effMonth = effMonth;
    }

    public int getPageStart()
    {
        return pageStart;
    }

    public void setPageStart(int pageStart)
    {
        this.pageStart = pageStart;
    }

    public int getStage()
    {
        return stage;
    }

    public void setStage(int stage)
    {
        this.stage = stage;
    }
}
