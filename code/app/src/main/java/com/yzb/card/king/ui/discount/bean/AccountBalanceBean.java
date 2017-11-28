package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2016/7/4
 */
public class AccountBalanceBean implements Serializable
{

    private AccountBalanceBean accountBalanceBean;
    private String accountId;
    private String total; //全部金额
    private String balance;//余额
    private String creditSum; //收款金额
    private String debitSum; //付款金额
    private String lockBalance; //锁定金额

    private double personBonusBalance; //个人红包余额
    private double platformBonusBalance; //平台红包余额
    private double shopBonusBalance; //商家红包余额  

    public double getPersonBonusBalance()
    {
        return personBonusBalance;
    }

    public void setPersonBonusBalance(double personBonusBalance)
    {
        this.personBonusBalance = personBonusBalance;
    }

    public double getPlatformBonusBalance()
    {
        return platformBonusBalance;
    }

    public void setPlatformBonusBalance(double platformBonusBalance)
    {
        this.platformBonusBalance = platformBonusBalance;
    }

    public double getShopBonusBalance()
    {
        return shopBonusBalance;
    }

    public void setShopBonusBalance(double shopBonusBalance)
    {
        this.shopBonusBalance = shopBonusBalance;
    }

    public AccountBalanceBean getAccountBalanceBean()
    {
        return this.accountBalanceBean;
    }

    public void setAccountBalanceBean(AccountBalanceBean accountBalanceBean)
    {
        if (accountBalanceBean == null)
        {
            accountBalanceBean = new AccountBalanceBean();
        }
        this.accountBalanceBean = accountBalanceBean;
    }


    public String getAccountId()
    {

        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }

    public String getBalance()
    {
        return balance;
    }

    public void setBalance(String balance)
    {
        this.balance = balance;
    }

    public String getCreditSum()
    {
        return creditSum;
    }

    public void setCreditSum(String creditSum)
    {
        this.creditSum = creditSum;
    }

    public String getDebitSum()
    {
        return debitSum;
    }

    public void setDebitSum(String debitSum)
    {
        this.debitSum = debitSum;
    }

    public String getLockBalance()
    {
        return lockBalance;
    }

    public void setLockBalance(String lockBalance)
    {
        this.lockBalance = lockBalance;
    }
}
