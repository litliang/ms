package com.yzb.card.king.bean.travel;

import java.io.Serializable;

/**
 * Created by 1 on 2016/11/24.
 */

public class TourTicketDetailContactsBean implements Serializable {

    private String contactName;  //联系人名字
    private String contactMobile; // 联系人电话
    private String contactEmail;  //联系人邮件


    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
