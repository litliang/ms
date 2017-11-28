package com.yzb.card.king.bean.travel;

import java.io.Serializable;

/**
 * 类  名：旅游分类信息
 * 作  者：Li Yubing
 * 日  期：2016/11/28
 * 描  述：
 */
public class TravelCategorySBean implements Serializable {
    /**
     * 对象id
     */
    private Long id;
    /**
     * 对象名称
     */
    private String objName;
    /**
     * 对象类型
     */
    private  String objType;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getObjName()
    {
        return objName;
    }

    public void setObjName(String objName)
    {
        this.objName = objName;
    }

    public String getObjType()
    {
        return objType;
    }

    public void setObjType(String objType)
    {
        this.objType = objType;
    }
}
