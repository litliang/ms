package com.yzb.card.king.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/20
 * 描  述：父类--类型名
 */
public class CatalogueTypeBean implements Serializable{

    /**
     * 类别名称
     */
    private String typeName;
    /**
     * 类别编码
     */
    private String typeCode;

    private boolean mutualList = false;

    private boolean ifDefault = false;
    /**
     * 子类别列表
     */
    private List<SubItemBean> childList;


    public boolean isMutualList()
    {
        return mutualList;
    }

    public void setMutualList(boolean mutualList)
    {
        this.mutualList = mutualList;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public List<SubItemBean> getChildList() {
        return childList;
    }

    public void setChildList(List<SubItemBean> childList) {
        this.childList = childList;
    }

    public boolean isIfDefault() {
        return ifDefault;
    }

    public void setIfDefault(boolean ifDefault) {
        this.ifDefault = ifDefault;
    }
}
