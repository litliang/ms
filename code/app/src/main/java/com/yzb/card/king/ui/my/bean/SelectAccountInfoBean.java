package com.yzb.card.king.ui.my.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类 名： 查询账单信息
 * 作 者： gaolei
 * 日 期：2017年01月11日
 * 描 述：查询账单信息
 */

public class SelectAccountInfoBean implements Serializable {

    /**
     * platformBonusBalance : 0
     * shopPoints : 0
     * bonusAnnualRateBalance : 0
     * bonusPreProfit : 0
     * balance : 0
     * totalProfit : 0
     * personBonusBalance : 0
     * bonusTotalProfit : 0
     * annualRateBalance : 0
     * giftcardBalance : 0
     * platformPoints : 0
     * preProfit : 0
     */

    private BigDecimal platformBonusBalance;
    private BigDecimal shopPoints;
    private BigDecimal bonusAnnualRateBalance;
    private BigDecimal bonusPreProfit;
    private BigDecimal balance;
    private BigDecimal totalProfit;
    private BigDecimal personBonusBalance;
    private BigDecimal bonusTotalProfit;
    private BigDecimal annualRateBalance;
    private BigDecimal giftcardBalance;
    private BigDecimal platformPoints;
    private BigDecimal preProfit;

    public BigDecimal getPlatformBonusBalance() {
        return platformBonusBalance;
    }

    public void setPlatformBonusBalance(BigDecimal platformBonusBalance) {
        this.platformBonusBalance = platformBonusBalance;
    }

    public BigDecimal getShopPoints() {
        return shopPoints;
    }

    public void setShopPoints(BigDecimal shopPoints) {
        this.shopPoints = shopPoints;
    }

    public BigDecimal getBonusAnnualRateBalance() {
        return bonusAnnualRateBalance;
    }

    public void setBonusAnnualRateBalance(BigDecimal bonusAnnualRateBalance) {
        this.bonusAnnualRateBalance = bonusAnnualRateBalance;
    }

    public BigDecimal getBonusPreProfit() {
        return bonusPreProfit;
    }

    public void setBonusPreProfit(BigDecimal bonusPreProfit) {
        this.bonusPreProfit = bonusPreProfit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getPersonBonusBalance() {
        return personBonusBalance;
    }

    public void setPersonBonusBalance(BigDecimal personBonusBalance) {
        this.personBonusBalance = personBonusBalance;
    }

    public BigDecimal getBonusTotalProfit() {
        return bonusTotalProfit;
    }

    public void setBonusTotalProfit(BigDecimal bonusTotalProfit) {
        this.bonusTotalProfit = bonusTotalProfit;
    }

    public BigDecimal getAnnualRateBalance() {
        return annualRateBalance;
    }

    public void setAnnualRateBalance(BigDecimal annualRateBalance) {
        this.annualRateBalance = annualRateBalance;
    }

    public BigDecimal getGiftcardBalance() {
        return giftcardBalance;
    }

    public void setGiftcardBalance(BigDecimal giftcardBalance) {
        this.giftcardBalance = giftcardBalance;
    }

    public BigDecimal getPlatformPoints() {
        return platformPoints;
    }

    public void setPlatformPoints(BigDecimal platformPoints) {
        this.platformPoints = platformPoints;
    }

    public BigDecimal getPreProfit() {
        return preProfit;
    }

    public void setPreProfit(BigDecimal preProfit) {
        this.preProfit = preProfit;
    }
}
