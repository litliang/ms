package com.yzb.card.king.bean.travel;

import com.yzb.card.king.ui.app.bean.BaseBean;

import java.io.Serializable;

/**
 * 目的地选择右侧bean；
 *
 * @author gengqiyun
 * @date 16/11/20.
 */
public class FilterBean extends BaseBean implements Serializable, Cloneable
{
    private boolean isSelected;//
    private String type;//	String		类型 Y	1景点、4城市（4级）
    private long objId;//	Long	对象id	N
    private String objName;//	String	对象名称	N
    private String objLng;//BigDecimal	经度	Y
    private String objLat;//	BigDecimal	纬度 Y

    public boolean isSelected()
    {
        return isSelected;
    }


    public FilterBean(){

    }
    public FilterBean(long objId, String objName)
    {
        this.objId = objId;
        this.objName = objName;
    }

    @Override
    public FilterBean clone() throws CloneNotSupportedException
    {
        return (FilterBean) super.clone();
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public long getObjId()
    {
        return objId;
    }

    public void setObjId(long objId)
    {
        this.objId = objId;
    }

    public String getObjName()
    {
        return super.isStrEmpty(objName);
    }

    public void setObjName(String objName)
    {
        this.objName = objName;
    }

    public String getObjLng()
    {
        return objLng;
    }

    public void setObjLng(String objLng)
    {
        this.objLng = objLng;
    }

    public String getObjLat()
    {
        return objLat;
    }

    public void setObjLat(String objLat)
    {
        this.objLat = objLat;
    }
}
