package com.yzb.card.king.ui.appwidget.popup;

import android.support.annotation.NonNull;

import com.yzb.card.king.bean.hotel.FilterCollection;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.ui.appwidget.HotelFilterView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/28 10:07
 */
public class HotelLocationPop extends HotelFilterPop
{

    public static String cityId;


    @Override
    protected void resetBedList()
    {

    }
    @Override
    protected void addOtherData(List<FilterCollection> listData)
    {

    }
    @NonNull
    @Override
    protected String getServiceName()
    {
        return "QueryRegionPosition";
    }

    @Override
    protected Map<String, Object> getParam()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("industryId","8");
        param.put("cityId", cityId);
        return param;
    }

    @Override
    protected void setDate(List<FilterType> list)
    {
        HotelFilterView.data.setPositionList(list);
    }

    @NonNull
    @Override
    protected String getTitle()
    {
        return "位置区域";
    }
}
