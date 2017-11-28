package com.yzb.card.king.ui.travel.bean;

import java.io.Serializable;

/**
 * 类  名：旅游选择条件
 * 作  者：Li JianQiang
 * 日  期：2016/11/21
 * 描  述：
 */
public class TravelSearchCriteriaBean implements Serializable {

    private String starCity;  //出发地

    private String endCity;     //目的地

    public String getEndCityType()
    {
        return endCityType;
    }

    public void setEndCityType(String endCityType)
    {
        this.endCityType = endCityType;
    }

    private String endCityType; //目的地类型

    private String theEarliest; //最早时间

    private String theEndTime;  //最晚时间

    private String singlBudget;  //单人预算

    private String daysCount; //出行天数

    private String singlBudgetMax;

    public String getDaysCountMax()
    {
        return daysCountMax;
    }

    public void setDaysCountMax(String daysCountMax)
    {
        this.daysCountMax = daysCountMax;
    }

    public String getSinglBudgetMax()
    {
        return singlBudgetMax;
    }

    public void setSinglBudgetMax(String singlBudgetMax)
    {
        this.singlBudgetMax = singlBudgetMax;
    }

    private String daysCountMax;
    /**
     * 1景点、4城市
     */
    private String arrType;
    /**
     * 类型对应id（景点ID、城市id）
     */
    private String arrDetailId;

    public String getDaysCount()
    {
        return daysCount;
    }

    public void setDaysCount(String daysCount)
    {
        this.daysCount = daysCount;
    }

    public String getSinglBudget()
    {
        return singlBudget;
    }

    public void setSinglBudget(String singlBudget)
    {
        this.singlBudget = singlBudget;
    }

    public String getTheEndTime()
    {
        return theEndTime;
    }

    public void setTheEndTime(String theEndTime)
    {
        this.theEndTime = theEndTime;
    }

    public String getTheEarliest()
    {
        return theEarliest;
    }

    public void setTheEarliest(String theEarliest)
    {
        this.theEarliest = theEarliest;
    }

    public String getArrType()
    {
        return arrType;
    }

    public void setArrType(String arrType)
    {
        this.arrType = arrType;
    }

    public String getArrDetailId()
    {
        return arrDetailId;
    }

    public void setArrDetailId(String arrDetailId)
    {
        this.arrDetailId = arrDetailId;
    }

    public String getEndCity()
    {
        return endCity;
    }

    public void setEndCity(String endCity)
    {
        this.endCity = endCity;
    }

    public String getStarCity()
    {
        return starCity;
    }

    public void setStarCity(String starCity)
    {
        this.starCity = starCity;
    }

    /**
     * 单人预算价的进度条值
     */
    private int budgetPriceProValue =0;
    /**
     * 旅游天数的进度值
     */
    private int travelDaysValue = 0;

    public int getBudgetPriceProValue()
    {
        return budgetPriceProValue;
    }

    public void setBudgetPriceProValue(int budgetPriceProValue)
    {
        this.budgetPriceProValue = budgetPriceProValue;
    }

    public int getTravelDaysValue()
    {
        return travelDaysValue;
    }

    public void setTravelDaysValue(int travelDaysValue)
    {
        this.travelDaysValue = travelDaysValue;
    }

    private String startStr;

    private String endStr;

    public String getStartStr()
    {
        return startStr;
    }

    public void setStartStr(String startStr)
    {
        this.startStr = startStr;
    }

    public String getEndStr()
    {
        return endStr;
    }

    public void setEndStr(String endStr)
    {
        this.endStr = endStr;
    }
}
