package com.yzb.card.king.ui.discount.bean;

import java.util.List;

/**
 * Created by gengqiyun on 2016/4/27.
 */
public class MsglBean {
    public int id;
    public String cityId;
    public String description;
    public String status;
    public String createTime;
    public boolean isSelected;

    public List<ArticleList> articleList;

    public static class ArticleList {
        public int id;
        public String overviewId;
        public String description;
        public String title;
        public String content;
        public String status;
        public String seq;
        public String createTime;
        public boolean isSelected;
    }

}
