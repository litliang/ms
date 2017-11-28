package com.yzb.card.king.bean.integral;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/23.
 */
public class RemindBean implements Serializable {


    /**
     * remindId : 2
     * remindStatus : 1
     * photo :
     * title : 积分到期
     * content : 积分到期
     * remindType : 5
     */

    private int remindId;
    private String remindStatus;
    private String photo;
    private String title;
    private String content;
    private String remindType;
    private int detailId;
    private String creditNo;

    public String getCreditNo() {
        return creditNo;
    }

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    private String cardholderName;

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getRemindId() {
        return remindId;
    }

    public void setRemindId(int remindId) {
        this.remindId = remindId;
    }

    public String getRemindStatus() {
        return remindStatus;
    }

    public void setRemindStatus(String remindStatus) {
        this.remindStatus = remindStatus;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemindType() {
        return remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }
}
