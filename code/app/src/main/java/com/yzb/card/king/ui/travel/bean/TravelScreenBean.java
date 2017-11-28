package com.yzb.card.king.ui.travel.bean;

import android.widget.SeekBar;

import com.yzb.card.king.bean.ticket.FilterType;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：筛选参数
 * 作  者：Li JianQiang
 * 日  期：2016/11/24
 * 描  述：
 */
public class TravelScreenBean extends  TravelSearchCriteriaBean   {

    private String singlBudget;  //单人预算

    private String singlBudgetMax;

    private String agentIds;

    public boolean isSelect()
    {
        return isSelect;
    }

    public void setSelect(boolean select)
    {
        isSelect = select;
    }

    private boolean isSelect;

    private List<FilterType> filterTypes;  //选中供应商和特色项

    public String getLabelIds()
    {
        return labelIds;
    }

    public void setLabelIds(String labelIds)
    {
        this.labelIds = labelIds;
    }

    public String getAgentIds()
    {
        return agentIds;
    }

    public void setAgentIds(String agentIds)
    {
        this.agentIds = agentIds;
    }

    private String labelIds;

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

    private String daysCount;       //出行天数

    private String elaryDate; //最早出发时间

    public String getLateDate()
    {
        return lateDate;
    }

    public void setLateDate(String lateDate)
    {
        this.lateDate = lateDate;
    }

    public String getElaryDate()
    {
        return elaryDate;
    }

    public void setElaryDate(String elaryDate)
    {
        this.elaryDate = elaryDate;
    }

    private String lateDate;    //最晚出发时间

    public String getSinglBudget()
    {
        return singlBudget;
    }

    public void setSinglBudget(String singlBudget)
    {
        this.singlBudget = singlBudget;
    }

    public String getDaysCount()
    {
        return daysCount;
    }

    public void setDaysCount(String daysCount)
    {
        this.daysCount = daysCount;
    }

    public List<FilterType> getFilterTypes()
    {
        return filterTypes;
    }

    public void setFilterTypes(List<FilterType> filterTypes)
    {
        this.filterTypes = filterTypes;
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
