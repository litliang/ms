package com.yzb.card.king.bean.user;

import java.io.Serializable;

/**
 * 类  名：账户信息
 * 作  者：Li Yubing
 * 日  期：2016/6/27
 * 描  述：记录登录用的积分、余额等信息  1余额 2红包 3礼品卡  4积分
 */
public class AccountInfo implements Serializable {
    /**
     * 账户id
     */
    private String accountId;
    /**
     *  余额
     */
    private String balance;
    /**
     * 收款金额
     */
    private String creditSum;
    /**
     * 锁定金额
     */
    private String lockBalance;
    /**
     * 付款金额
     */
    private String debitSum;
    /**
     * 全部金额
     */
    private String total ;

    public String getAccountId() {
        return accountId;
    }

    public String getDebitSum() {
        return debitSum;
    }

    public void setDebitSum(String debitSum) {
        this.debitSum = debitSum;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(String creditSum) {
        this.creditSum = creditSum;
    }

    public String getLockBalance() {
        return lockBalance;
    }

    public void setLockBalance(String lockBalance) {
        this.lockBalance = lockBalance;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
