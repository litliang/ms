package com.yzb.card.king.ui.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/19 15:25
 */
public class LevelPageData implements Serializable
{
    List<Level> levelList;

    private int percent;

    public List<Level> getLevelList()
    {
        return levelList;
    }

    public void setLevelList(List<Level> levelList)
    {
        this.levelList = levelList;
    }

    public int getPercent()
    {
        return percent;
    }

    public void setPercent(int percent)
    {
        this.percent = percent;
    }
}
