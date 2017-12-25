package com.yzb.card.king.bean;

import java.io.Serializable;

/**
 * 搜索结果展示类
 * Created by 玉兵 on 2017/7/31.
 */

public class SearchReusultBean implements Serializable{

    /**
     * 类别名称
     */
    private String typeName;

    /**
     * 类别编码
     */
    private String typeCode;

    private boolean ifProcduct = false;
    /**
     * 搜索项id
     */
    private String searchId;
    /**
     * 搜索项名称
     */
    private String searchName;

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
        if("HotelName".equals(typeCode)){

            ifProcduct = true;

        }else {

            ifProcduct = false;

        }
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public boolean isIfProcduct() {
        return ifProcduct;
    }

    public void setIfProcduct(boolean ifProcduct) {
        this.ifProcduct = ifProcduct;
    }
}
