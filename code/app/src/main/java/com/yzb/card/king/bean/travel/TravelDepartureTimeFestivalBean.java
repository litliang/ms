package com.yzb.card.king.bean.travel;

import java.io.Serializable;

/**
 * Created by sushuiku on 2016/11/23.
 */

public class TravelDepartureTimeFestivalBean implements Serializable {

    private long festivalId; //节日id
    private String festivalName;// 节日名称
    private String startDate;  // 节日开始日期
    private String endDate;   // 节日结束日期


    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public long getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(long festivalId) {
        this.festivalId = festivalId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
