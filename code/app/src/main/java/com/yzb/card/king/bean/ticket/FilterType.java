package com.yzb.card.king.bean.ticket;

/**
 * 描述：筛选中的选项 数据类
 * 作者：殷曙光
 * 日期：2016/10/10 19:53
 */
public class FilterType implements IFilterPopItem
{
    private String name;
    //上级类型
    private String type;
    private String code;
    //子类型
    private String childType;

    private double lng;
    private double lat;
    private String starDate;

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getStarDate()
    {
        return starDate;
    }

    public void setStarDate(String starDate)
    {
        this.starDate = starDate;
    }

    private String endDate;

    private boolean selected;

    public FilterType()
    {
    }

    public FilterType(String name, String type, String code)
    {
        this.name = name;
        this.type = type;
        this.code = code;
    }

    public FilterType(String name, String type,String childType,String code)
    {
        this.name = name;
        this.type = type;
        this.childType = childType;
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public String getChildType()
    {
        return childType;
    }

    public void setChildType(String childType)
    {
        this.childType = childType;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }
}
