package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * Created by Timmy on 16/7/27.
 */
public class TourismApplicanBean implements Serializable {
    private String applicantName;
    private String applicantIDCard;

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantIDCard() {
        return applicantIDCard;
    }

    public void setApplicantIDCard(String applicantIDCard) {
        this.applicantIDCard = applicantIDCard;
    }
}
