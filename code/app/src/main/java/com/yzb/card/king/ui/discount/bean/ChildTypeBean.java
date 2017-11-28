package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * Created by dev on 2016/4/12.
 * "id": 11,
 "typeName": "徽菜",
 "parentId": 1,
 "typeImage": "2016042616243116040403",
 "leafNote": "1"
 */
public class ChildTypeBean implements Serializable{

    public String id; // 小分类的id；例如美食中的川菜；
    public String typeName; //名称；
    public String parentId;
    public String typeImage;
    public String status; //0:,1:显示；

    public ChildTypeBean(String id, String typeName)
    {
        this.id = id;
        this.typeName = typeName;
    }

    public ChildTypeBean(String id, String typeName, String parentId, String typeImage, String leafNote) {
        this.id = id;
        this.typeName = typeName;
        this.parentId = parentId;
        this.typeImage = typeImage;
    }

    public ChildTypeBean() {
    }
}
