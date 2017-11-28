package com.yzb.card.king.ui.manage;

import java.util.Date;

/**
 * 类  名：日历搜索管理
 * 作  者：Jin jiayu
 * 日  期：2016/6/3
 * 描  述：
 */
public class CalendarManager {

    private static CalendarManager instance = null;

    /**
     * 选择的时间
     */
    private Date date = null;

    /**
     * 价格
     */
    private float price = 0;

    /**
     * 日期加载标记
     */
    private boolean  dateLoadFlag = true;

    private CalendarManager() {

    }


    public static CalendarManager getInstance() {

        if (instance == null) {

            instance = new CalendarManager();
        }

        return instance;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    /**
     * 清理缓存数据
     */
    public void clearData() {

        date = null;

        price = 0;

        dateLoadFlag = true;
    }

    public boolean isDateLoadFlag()
    {
        return dateLoadFlag;
    }

    public void setDateLoadFlag(boolean dateLoadFlag)
    {
        this.dateLoadFlag = dateLoadFlag;
    }
}
