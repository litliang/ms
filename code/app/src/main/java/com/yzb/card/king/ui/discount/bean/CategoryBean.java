package com.yzb.card.king.ui.discount.bean;

import java.util.List;

/**
 * Created by gengqiyun on 2016/5/3.
 * 分类出参；
 */
public class CategoryBean {
    public String id;
    public String typeName;
    public String count;
    public String travelCount;
    public String hotelCount;

    public List<Child> childList;

    public boolean isSelected;

    public class Child {
        public String id;
        public String typeName;
        public String parentId;
        public String typeImage;
        public boolean isSelected;
        public String count;
        public String travelCount;
        public String hotelCount;
        public List<GrandChild> grandChildList;

        /**
         * 三阶；
         */
        public class GrandChild {
            public String id;
            public String typeName;
            public String parentId;
            public String typeImage;
            public boolean isSelected;
            public String count;
            public String travelCount;
            public String hotelCount;
        }
    }

}
