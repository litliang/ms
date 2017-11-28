package com.yzb.card.king.bean.ticket;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/11 11:12
 */
public class SortType implements IFilterPopItem
{
    private String code;
    private String name;

    private int sortIndex = 0;

    public SortType()
    {
    }

    public SortType(String code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public SortType(String code, String name,int sortIndex)
    {
        this.code = code;
        this.name = name;
        this.sortIndex = sortIndex;
    }

    public int getSortIndex()
    {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex)
    {
        this.sortIndex = sortIndex;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
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

//    @Override
//    public String getItemName()
//    {
//        return name;
//    }
}
