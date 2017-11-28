package com.yzb.card.king.ui.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/19 15:27
 */
public class Level implements Serializable
{
    Long levelId;
    String levelName;
    String levelLogo;
    List<LevelGridItem> privilegeList;
    List<LevelCondition> conditionList;
    public Long getLevelId()
    {
        return levelId;
    }

    public void setLevelId(Long levelId)
    {
        this.levelId = levelId;
    }

    public String getLevelName()
    {
        return levelName;
    }

    public void setLevelName(String levelName)
    {
        this.levelName = levelName;
    }

    public String getLevelLogo()
    {
        return levelLogo;
    }

    public void setLevelLogo(String levelLogo)
    {
        this.levelLogo = levelLogo;
    }

    public List<LevelGridItem> getPrivilegeList()
    {
        return privilegeList;
    }

    public void setPrivilegeList(List<LevelGridItem> privilegeList)
    {
        this.privilegeList = privilegeList;
    }

    public List<LevelCondition> getConditionList()
    {
        return conditionList;
    }

    public void setConditionList(List<LevelCondition> conditionList)
    {
        this.conditionList = conditionList;
    }
}
