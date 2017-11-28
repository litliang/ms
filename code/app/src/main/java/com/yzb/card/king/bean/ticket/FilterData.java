package com.yzb.card.king.bean.ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：过滤器数据
 * 作者：殷曙光
 * 日期：2016/10/10 19:51
 */
public class FilterData
{
    private List<FilterType> filterTypes;
    //银行优惠筛选条件;0,非银行优惠；1，关联个人银行优惠；2.更多银行优惠
    private FilterType disBank = new FilterType("银行优惠", "bankStruts", "0");
    private FilterType directFlight;//直飞优先筛选条件
    private SortType time;//时间排序
    private SortType price;//价格排序

    private List<OnDataChangeListener> listeners = new ArrayList<>();


    public void reset()
    {
        filterTypes = null;
        disBank = new FilterType("银行优惠", "bankStruts", "0");
        directFlight = null;
        time = null;//时间排序
        price = null;
        listeners.clear();
    }

    public List<FilterType> getFilterTypes()
    {
        return filterTypes;
    }

    public List<IFilterPopItem> getFilterList()
    {
        List<IFilterPopItem> list = new ArrayList<>();
        if (filterTypes != null)
        {
            list.addAll(filterTypes);
        }
        if (directFlight != null)
        {
            list.add(directFlight);
        }
        List<IFilterPopItem> temp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
        {
            if ("不限".equals(list.get(i).getName()))
            {
                temp.add(list.get(i));
            }
        }
        list.removeAll(temp);
        return list;
    }

    public void setFilterTypes(List<FilterType> filterTypes)
    {
        this.filterTypes = filterTypes;
        invokeListener();
    }

    public FilterType getDirectFlight()
    {
        if (directFlight == null)
        {
            directFlight = new FilterType("不限", "flytype", "0");
        }
        return directFlight;
    }

    public void setDirectFlight(FilterType directFlight)
    {
        if (directFlight != null)
        {
            setSortType(directFlight, null, null);
        }
    }

    public SortType getTime()
    {
        if (time == null)
        {
            time = new SortType("0", "时间");
        }
        return time;
    }

    public void setTime(SortType time)
    {
        if (time != null)
        {
            setSortType(null, time, null);
        }
    }

    public SortType getPrice()
    {
        if (price == null)
        {
            price = new SortType("0", "价格");
        }
        return price;
    }

    public void setPrice(SortType price)
    {
        if (price != null)
        {
            setSortType(null, null, price);
        }
    }

    private void invokeListener()
    {
        for (int i = 0; i < listeners.size(); i++)
        {
            listeners.get(i).onDataChanged();
        }
    }

    public void setBankDisCode(String code)
    {
        disBank.setCode(code);
        for (int i = 0; i < listeners.size(); i++)
        {
            listeners.get(i).onBankChanged(!"0".equals(code));
        }
    }

    public String getDisBankCode()
    {
        return disBank.getCode();
    }

    private void setSortType(FilterType directFlight, SortType time, SortType price)
    {
        this.directFlight = directFlight;
        this.time = time;
        this.price = price;
        invokeListener();
    }


    public void setListener(OnDataChangeListener listener)
    {
        if (listener != null)
        {
            this.listeners.add(listener);
        }
    }

    public void removeListener(OnDataChangeListener listener)
    {
        if (listener != null)
        {
            listeners.remove(listener);
        }
    }

    public interface OnDataChangeListener
    {
        void onDataChanged();

        void onBankChanged(boolean selected);
    }
}
