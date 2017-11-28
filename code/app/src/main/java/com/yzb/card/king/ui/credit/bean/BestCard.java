package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/18 15:05
 */
public class BestCard implements Serializable
{
    long archivesId;
    String name;
    String photo;
    String cardWanted;
    String title;
    String intro;
    int num;
    String url;

    public long getArchivesId()
    {
        return archivesId;
    }

    public void setArchivesId(long archivesId)
    {
        this.archivesId = archivesId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public String getCardWanted()
    {
        return cardWanted;
    }

    public void setCardWanted(String cardWanted)
    {
        this.cardWanted = cardWanted;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getIntro()
    {
        return intro;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
