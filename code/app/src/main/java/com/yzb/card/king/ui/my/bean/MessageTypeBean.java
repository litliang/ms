package com.yzb.card.king.ui.my.bean;

import java.io.Serializable;
import java.util.List;
/**
 * 类 名： 主消息
 * 作 者： gaolei
 * 日 期：2017年01月16日
 * 描 述：
 */
public class MessageTypeBean implements Serializable{


    private List<NewsMapListBean> newsMapList;

    public List<NewsMapListBean> getNewsMapList() {
        return newsMapList;
    }

    public void setNewsMapList(List<NewsMapListBean> newsMapList) {
        this.newsMapList = newsMapList;
    }

    public static class NewsMapListBean {
        /**
         * taskType : H
         * newsNmber : 2
         * optime : 2017-01-11 16:35:19
         * newsCommon : l
         * newsTitle : l
         */

        private String taskType;
        private int newsNmber;
        private String optime;
        private String newsCommon;
        private String newsTitle;

        public String getTaskType() {
            return taskType;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        public int getNewsNmber() {
            return newsNmber;
        }

        public void setNewsNmber(int newsNmber) {
            this.newsNmber = newsNmber;
        }

        public String getOptime() {
            return optime;
        }

        public void setOptime(String optime) {
            this.optime = optime;
        }

        public String getNewsCommon() {
            return newsCommon;
        }

        public void setNewsCommon(String newsCommon) {
            this.newsCommon = newsCommon;
        }

        public String getNewsTitle() {
            return newsTitle;
        }

        public void setNewsTitle(String newsTitle) {
            this.newsTitle = newsTitle;
        }
    }
}
