package com.yzb.card.king.bean.integral;

import java.io.Serializable;

/**
 * 类  名：兑换积分明细类
 * 作  者：Li Yubing
 * 日  期：2016/6/24
 * 描  述：
 */
public class ExchangeIntegralBean implements Serializable {

    private String ruleId;

    private String ruleName;

    private String point;

    private String exchangeResult;

    private String bankId;

    private String planId;

    private String cash;

    private String moblie;

    private String flow;

    private String telFare;

    private String oil;

    private String status;

    private String addressId;

    private String postage;

    private String number;

    private String mileage;

    private String PPFId;

    private long platformIntegralNum = 1000;


    public long getPlatformIntegralNum() {
        return platformIntegralNum;
    }

    public void setPlatformIntegralNum(long platformIntegralNum) {
        this.platformIntegralNum = platformIntegralNum;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getPPFId() {
        return PPFId;
    }

    public void setPPFId(String PPFId) {
        this.PPFId = PPFId;
    }

    public String getOil() {
        return oil;
    }

    public void setOil(String oil) {
        this.oil = oil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTelFare() {
        return telFare;
    }

    public void setTelFare(String telFare) {
        this.telFare = telFare;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getExchangeResult() {
        return exchangeResult;
    }

    public void setExchangeResult(String exchangeResult) {
        this.exchangeResult = exchangeResult;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}
