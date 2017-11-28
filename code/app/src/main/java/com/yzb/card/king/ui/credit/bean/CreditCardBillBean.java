package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/11
 * 描  述：
 */
public class CreditCardBillBean implements Serializable {

    public String fullNo;//完整卡號 4581 2387 8946 4654\",\"
    public String dueDate;//最後还款日期\":\"2016-06-25\",\"
    public String bgImageCode;//图片Code\":\"\",\"
    public String bankName;//银行名称\":\"交通银行\",\"
    public String bankLogoCode;//银行logo Code;\":\"\",\"
    public double lastLimit;//可用额度\":1424.00,\"
    public String dueDay;//最后还款日\":13,\"
    public String billDay;//账单日"
    public String expendDate;//出账日\":\"2016-06-15\",\"
    public String bankId;//银行Id\":\"3\",\"
    public double billAmount;//账单金额（已出账单）\":0.00,\"
    public double fullLimit;//全部额度":1424.00,\"
    public String billId;//订单Id\":314,\"
    public String cardholderName;//持卡人名称\":\"桂阳\",\"
    public String status;//订单状态//\":\"未出账\"}
    public int adjustStatus;//额度调整状态（0未申请1已申请）
    public String freeDay="";//免息天数
    public double minPaymentAmount;
    public String creditId;//信用卡ID
    public String sortNo;//卡号后4位
    public String paymentAmount;
    public String billCycle;//账单周期
    public String autopayId;//自动还款id
    public String remindId;//
    public int dueDay2;//账单日

    public String settingStatus; //自动还款设置（0未设置，1已设置，2未启用）
    public String fullStatus;//是否全额还款（1是，0否）

    public String feeStatus;//是否设置免年费(1设置,2不设置)
    public String feeDay;//年费收取日(月/日)
    public String feeType;//年费类型(1满足条件免年费,2固定年费)
    public String minPos;//最低刷卡次数(0为未选中，其余数字代表选中之后的值)
    public String amountPos;//刷卡总金额(0为未选中，其余数字代表选中之后的值)

    public String feeFix;//固定年费值；

    public String remindStatus;//  提醒设置（0未设置，1已设置，2未启用）

    public String getAutopayId() {
        return autopayId;
    }

    public String getRemindId() {
        return remindId;
    }

    public void setRemindId(String remindId) {
        this.remindId = remindId;
    }

    public void setAutopayId(String autopayId) {
        this.autopayId = autopayId;
    }

    public String getFullNo() {
        return fullNo;
    }

    public void setFullNo(String fullNo) {
        this.fullNo = fullNo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBgImageCode() {
        return bgImageCode;
    }

    public void setBgImageCode(String bgImageCode) {
        this.bgImageCode = bgImageCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankLogoCode() {
        return bankLogoCode;
    }

    public void setBankLogoCode(String bankLogoCode) {
        this.bankLogoCode = bankLogoCode;
    }

    public double getLastLimit() {
        return lastLimit;
    }

    public void setLastLimit(double lastLimit) {
        this.lastLimit = lastLimit;
    }

    public String getDueDay() {
        return dueDay;
    }

    public void setDueDay(String dueDay) {
        this.dueDay = dueDay;
    }

    public String getExpendDate() {
        return expendDate;
    }

    public void setExpendDate(String expendDate) {
        this.expendDate = expendDate;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public double getFullLimit() {
        return fullLimit;
    }

    public void setFullLimit(double fullLimit) {
        this.fullLimit = fullLimit;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAdjustStatus() {
        return adjustStatus;
    }

    public void setAdjustStatus(int adjustStatus) {
        this.adjustStatus = adjustStatus;
    }


    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getFreeDay() {
        return freeDay;
    }

    public void setFreeDay(String freeDay) {
        this.freeDay = freeDay;
    }



    public String getBillCycle() {
        return billCycle;
    }

    public void setBillCycle(String billCycle) {
        this.billCycle = billCycle;
    }
}
