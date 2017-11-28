package com.yzb.card.king.bean.my;

/**
 * 功能：账户信息；
 *
 * @author:gengqiyun
 * @date: 2016/12/28
 */
public class AccountInfoBean
{
    private float balance;//BigDecimal	钱包余额	Y
    private float giftcardBalance;//	BigDecimal		礼品卡余额Y
    private float personBonusBalance;    //BigDecimal	个人红包余额	Y
    private float platformBonusBalance;//	BigDecimal	平台红包余额	Y
    private float platformPoints;    //BigDecimal	平台积分	Y
    private float shopPoints;    //BigDecimal	商家积分	Y
    private float annualRateBalance;//BigDecimal	钱包余额年化率	Y
    private float preProfit;    //BigDecimal	钱包余额昨日收益	Y
    private float totalProfit;//BigDecimal	钱包余额累计收益	Y
    private float bonusAnnualRateBalance;    //BigDecimal	红包余额年化率	Y
    private float bonusPreProfit;//	BigDecimal	红包余额昨日收益	Y
    private float bonusTotalProfit; //	BigDecimal	红包余额累计收益	Y

    public float getBalance()
    {
        return balance;
    }

    public void setBalance(float balance)
    {
        this.balance = balance;
    }

    public float getGiftcardBalance()
    {
        return giftcardBalance;
    }

    public void setGiftcardBalance(float giftcardBalance)
    {
        this.giftcardBalance = giftcardBalance;
    }

    public float getPersonBonusBalance()
    {
        return personBonusBalance;
    }

    public void setPersonBonusBalance(float personBonusBalance)
    {
        this.personBonusBalance = personBonusBalance;
    }

    public float getPlatformBonusBalance()
    {
        return platformBonusBalance;
    }

    public void setPlatformBonusBalance(float platformBonusBalance)
    {
        this.platformBonusBalance = platformBonusBalance;
    }

    public float getPlatformPoints()
    {
        return platformPoints;
    }

    public void setPlatformPoints(float platformPoints)
    {
        this.platformPoints = platformPoints;
    }

    public float getShopPoints()
    {
        return shopPoints;
    }

    public void setShopPoints(float shopPoints)
    {
        this.shopPoints = shopPoints;
    }

    public float getAnnualRateBalance()
    {
        return annualRateBalance;
    }

    public void setAnnualRateBalance(float annualRateBalance)
    {
        this.annualRateBalance = annualRateBalance;
    }

    public float getPreProfit()
    {
        return preProfit;
    }

    public void setPreProfit(float preProfit)
    {
        this.preProfit = preProfit;
    }

    public float getTotalProfit()
    {
        return totalProfit;
    }

    public void setTotalProfit(float totalProfit)
    {
        this.totalProfit = totalProfit;
    }

    public float getBonusAnnualRateBalance()
    {
        return bonusAnnualRateBalance;
    }

    public void setBonusAnnualRateBalance(float bonusAnnualRateBalance)
    {
        this.bonusAnnualRateBalance = bonusAnnualRateBalance;
    }

    public float getBonusPreProfit()
    {
        return bonusPreProfit;
    }

    public void setBonusPreProfit(float bonusPreProfit)
    {
        this.bonusPreProfit = bonusPreProfit;
    }

    public float getBonusTotalProfit()
    {
        return bonusTotalProfit;
    }

    public void setBonusTotalProfit(float bonusTotalProfit)
    {
        this.bonusTotalProfit = bonusTotalProfit;
    }
}
