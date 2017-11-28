package com.yzb.card.king.ui.manage;


import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.bean.ticket.FlightManager;

import java.util.List;
import java.util.Map;

/**
 * 类名： FilterManager
 * 作者： Lei Chao.
 * 日期： 2016-10-14
 * 描述： 筛选条件
 */
public class FilterManager {

    private static FilterManager manager = null;

    private Flight currentFlight;

    private  List< com.yzb.card.king.bean.ticket.FilterType> selectedConditionList;

    private FilterManager()
    {

    }

    public Flight getCurrentFlight()
    {
        return currentFlight;
    }

    public void setCurrentFlight(Flight currentFlight)
    {
        this.currentFlight = currentFlight;
    }


    public static FilterManager getInstance()
    {
        if (manager == null) {
            manager = new FilterManager();
        }
        return manager;
    }


    public List<FilterType> getSelectedConditionList()
    {
        return selectedConditionList;
    }

    public void setSelectedConditionList(List<FilterType> selectedConditionList)
    {
        this.selectedConditionList = selectedConditionList;
    }

    /**
     * 清理缓存数据
     */
    public void clearData(){

        if(selectedConditionList != null){

            selectedConditionList.clear();
        }
        selectedConditionList = null;

        currentFlight = null;
    }
}