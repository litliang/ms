package com.yzb.card.king.jpush.bean;

import java.util.List;

/**
 * 类 名： Jpush推送标签bean
 * 作 者： gaolei
 * 日 期：2017年01月17日
 * 描 述：Jpush推送标签bean
 */

public class JpushTagsBean {

    /**
     * tagMapList : [{"tagList":"h","tagType":"2"}]
     * id : 6789654384819
     */

    private String id;
    private List<TagMapListBean> tagMapList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TagMapListBean> getTagMapList() {
        return tagMapList;
    }

    public void setTagMapList(List<TagMapListBean> tagMapList) {
        this.tagMapList = tagMapList;
    }

    public static class TagMapListBean {
        /**
         * tagList : h
         * tagType : 2
         */

        private String tagList;
        private String tagType;

        public String getTagList() {
            return tagList;
        }

        public void setTagList(String tagList) {
            this.tagList = tagList;
        }

        public String getTagType() {
            return tagType;
        }

        public void setTagType(String tagType) {
            this.tagType = tagType;
        }
    }
}
