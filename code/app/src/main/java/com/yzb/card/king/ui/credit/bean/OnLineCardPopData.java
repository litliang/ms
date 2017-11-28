package com.yzb.card.king.ui.credit.bean;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/29
 */

public class OnLineCardPopData
{
    private List<Bank> bankList;
    private List<CardType> purposeList;
    private List<CardLevel> rankList;

    public List<Bank> getBankList()
    {
        return bankList;
    }

    public void setBankList(List<Bank> bankList)
    {
        this.bankList = bankList;
    }

    public List<CardType> getPurposeList()
    {
        return purposeList;
    }

    public void setPurposeList(List<CardType> purposeList)
    {
        this.purposeList = purposeList;
    }

    public List<CardLevel> getRankList()
    {
        return rankList;
    }

    public void setRankList(List<CardLevel> rankList)
    {
        this.rankList = rankList;
    }
}
