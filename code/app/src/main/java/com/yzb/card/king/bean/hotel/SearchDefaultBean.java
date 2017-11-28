package com.yzb.card.king.bean.hotel;

import com.yzb.card.king.ui.app.bean.BaseBean;

import java.util.List;

/**
 * 功能：搜索默认出参；
 *
 * @author:gengqiyun
 * @date: 2016/10/31
 */
public class SearchDefaultBean extends BaseBean
{
    private String type;//类型
    private String code;//类型编码
    private String name;//类型名称
    private String photoCode;//类别图片

    private List<Child> childList;//类别子项


    private boolean expand = false; //是否伸展；true：展开状态；false：闭合状态；

    public boolean isExpand()
    {
        return expand;
    }

    public void setExpand(boolean expand)
    {
        this.expand = expand;
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

    public String getName()
    {
        return super.isStrEmpty(name);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhotoCode()
    {
        return photoCode;
    }

    public void setPhotoCode(String photoCode)
    {
        this.photoCode = photoCode;
    }

    public List<Child> getChildList()
    {
        return childList;
    }

    public void setChildList(List<Child> childList)
    {
        this.childList = childList;
    }


    public static class Child extends BaseBean
    {
        private String type;//上级类型
        private String code;//子类型编码
        private String name;//子类型名称
        private String childType;//子类型
        private double lng;//子类型经度
        private double lat;//子类型纬度

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

        public String getName()
        {
            return super.isStrEmpty(name);
        }

        public void setName(String name)
        {
            this.name = name;
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
}
