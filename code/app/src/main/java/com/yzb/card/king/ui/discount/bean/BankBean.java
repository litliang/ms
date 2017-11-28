package com.yzb.card.king.ui.discount.bean;

/**
 * Created by gengqiyun on 2016/4/27.
 */
public class BankBean {
    public Long id;
    public String bankName;
    public String bankCode;
    public String status;
    public Long bankId;
    public String isCard; //是否已有该银行卡（1、有 2、没有）

    public boolean isSelected; // 是否选中，只在客户端使用；

    public BankBean(Long bankId, String bankName) {
        this.bankId = bankId;
        this.bankName = bankName;
    }

    public BankBean() {

    }


}
