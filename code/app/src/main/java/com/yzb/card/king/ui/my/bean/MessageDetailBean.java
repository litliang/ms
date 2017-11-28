package com.yzb.card.king.ui.my.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 类 名： 详细消息
 * 作 者： gaolei
 * 日 期：2017年01月16日
 * 描 述：
 */

public class MessageDetailBean implements Serializable{

    private List<SubNewsMapListBean> subNewsMapList;

    public List<SubNewsMapListBean> getSubNewsMapList() {
        return subNewsMapList;
    }

    public void setSubNewsMapList(List<SubNewsMapListBean> subNewsMapList) {
        this.subNewsMapList = subNewsMapList;
    }

    public static class SubNewsMapListBean {
        /**
         * newsId : 3
         * taskType : l
         * newsCommon : l
         * optime : 2017-01-1116: 35: 19
         * newsTitle : l
         * materialId : 3
         * key : 53EC7998683A4AE6
         * taskSturt : 1
         */

        private int newsId;
        private String taskType;
        private String newsCommon;
        private String optime;
        private String newsTitle;
        private int materialId;
        private String key;
        private String taskSturt;

        public int getNewsId() {
            return newsId;
        }

        public void setNewsId(int newsId) {
            this.newsId = newsId;
        }

        public String getTaskType() {
            return taskType;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        public String getNewsCommon() {
            return newsCommon;
        }

        public void setNewsCommon(String newsCommon) {
            this.newsCommon = newsCommon;
        }

        public String getOptime() {
            return optime;
        }

        public void setOptime(String optime) {
            this.optime = optime;
        }

        public String getNewsTitle() {
            return newsTitle;
        }

        public void setNewsTitle(String newsTitle) {
            this.newsTitle = newsTitle;
        }

        public int getMaterialId() {
            return materialId;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getTaskSturt() {
            return taskSturt;
        }

        public void setTaskSturt(String taskSturt) {
            this.taskSturt = taskSturt;
        }
    }
}
