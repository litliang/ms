package com.yzb.card.king.bean.travel;

import java.io.Serializable;

/**
 * Created by sushuiku on 2016/11/21.
 */

public class TravelOrderDetailguestListBean implements Serializable {

    private String guestName; // 申报人名称

    private String guestCertNo;//申报人身份证号

    public String getGuestCertNo() {
        return guestCertNo;
    }

    public void setGuestCertNo(String guestCertNo) {
        this.guestCertNo = guestCertNo;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
}
