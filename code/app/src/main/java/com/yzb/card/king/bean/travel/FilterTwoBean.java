package com.yzb.card.king.bean.travel;

import com.yzb.card.king.ui.app.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 目的地选择左侧bean；
 *
 * @author gengqiyun
 * @date 16/11/20.
 */
public class FilterTwoBean  extends BaseBean implements Serializable,Cloneable
{
    private long regionId;//	Long	区域id		N
    private String regionName;//	String	区域名称	N

    private List<FilterBean> childList;

    private boolean isSelected;

    private FilterBean selectedFilterEntity; //右侧选中的bean；

    public FilterBean getSelectedFilterEntity()
    {
        return selectedFilterEntity;
    }

    public FilterTwoBean(){

    }

    public void setSelectedFilterEntity(FilterBean selectedFilterEntity)
    {
        this.selectedFilterEntity = selectedFilterEntity;
    }

    public FilterTwoBean(long regionId, String regionName)
    {
        this.regionId = regionId;
        this.regionName = regionName;
    }

    @Override
    public FilterTwoBean clone() throws CloneNotSupportedException
    {
        return (FilterTwoBean) super.clone();
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    public long getRegionId()
    {
        return regionId;
    }

    public void setRegionId(long regionId)
    {
        this.regionId = regionId;
    }

    public String getRegionName()
    {
        return super.isStrEmpty(regionName);
    }

    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }

    public List<FilterBean> getChildList()
    {
        return childList;
    }

    public void setChildList(List<FilterBean> childList)
    {
        this.childList = childList;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }
}
