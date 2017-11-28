package com.yzb.card.king.ui.my.bean;

import com.yzb.card.king.ui.credit.bean.Bank;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/11 16:56
 */
public class SelectBankGroup implements Serializable
{
    String letter;
    List<Bank> bankList;

    public String getLetter()
    {
        return letter;
    }

    public void setLetter(String letter)
    {
        this.letter = letter;
    }

    public List<Bank> getBankList()
    {
        return bankList;
    }

    public void setBankList(List<Bank> bankList)
    {
        this.bankList = bankList;
    }
}
