package com.yzb.card.king.ui.credit.bean;

import com.yzb.card.king.ui.credit.interfaces.IOnlineCardPop;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/22
 */
public class CardType implements IOnlineCardPop
{
    private Long id;
    private String name;
    private String intro;

    public CardType()
    {
    }

    public CardType(Long id, String name, String intro)
    {
        this.id = id;
        this.name = name;
        this.intro = intro;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIntro()
    {
        return intro;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    @Override
    public String name()
    {
        return name;
    }

    @Override
    public Long id()
    {
        return id;
    }
}
