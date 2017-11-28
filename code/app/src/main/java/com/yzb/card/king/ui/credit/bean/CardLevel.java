package com.yzb.card.king.ui.credit.bean;

import com.yzb.card.king.ui.credit.interfaces.IOnlineCardPop;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/22
 */

public class CardLevel implements IOnlineCardPop
{
    private Long typeValueCode;
    private String typeValueName;

    public CardLevel()
    {
    }

    public CardLevel(Long typeValueCode, String typeValueName)
    {
        this.typeValueCode = typeValueCode;
        this.typeValueName = typeValueName;
    }

    public Long getTypeValueCode()
    {
        return typeValueCode;
    }

    public void setTypeValueCode(Long typeValueCode)
    {
        this.typeValueCode = typeValueCode;
    }

    public String getTypeValueName()
    {
        return typeValueName;
    }

    public void setTypeValueName(String typeValueName)
    {
        this.typeValueName = typeValueName;
    }

    @Override
    public String name()
    {
        return typeValueName;
    }

    @Override
    public Long id()
    {
        return typeValueCode;
    }
}
