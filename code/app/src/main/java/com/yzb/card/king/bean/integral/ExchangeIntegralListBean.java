package com.yzb.card.king.bean.integral;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/24
 * 描  述：
 */
public class ExchangeIntegralListBean implements Serializable {






    private String planId;

    private String planName;

    private String imageCode;
    private List<ExchangeIntegralBean> ruleList;


    public List<ExchangeIntegralBean> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<ExchangeIntegralBean> ruleList) {
        this.ruleList = ruleList;
    }


    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }
}
