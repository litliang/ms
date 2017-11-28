package com.yzb.card.king.bean.hotel;

import com.yzb.card.king.bean.ticket.FilterType;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/26 11:27
 */
public class FilterCollection
{

    private String type;
    private String name;
    private List<FilterType> childList;
    private boolean expand;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<FilterType> getChildList()
    {
        return childList;
    }

    public void setChildList(List<FilterType> childList)
    {
        this.childList = childList;
    }

    public boolean isExpand()
    {
        return expand;
    }

    public void setExpand(boolean expand)
    {
        this.expand = expand;
    }


    public FilterCollection()
    {
    }

    public FilterCollection(String type, String name, List<FilterType> childList, boolean expand)
    {
        this.type = type;
        this.name = name;
        this.childList = childList;
        this.expand = expand;
    }

    public FilterCollection copy()
    {
        return new FilterCollection(type,name,childList,expand);
    }
}
