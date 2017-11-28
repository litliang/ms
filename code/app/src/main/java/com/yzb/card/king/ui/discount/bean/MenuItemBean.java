package com.yzb.card.king.ui.discount.bean;

import java.util.List;

/**
 * Created by gengqiyun on 2016/4/18.
 */
public class MenuItemBean {
    public String id;
    public String name;
    public String parentId;

    public List<MenuItemBean> innerList;

    /**
     * 是否被选中；只在客户端使用；
     */
    public boolean isSelected = false;

    public MenuItemBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public MenuItemBean() {

    }
}
