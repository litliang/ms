package com.yzb.card.king.bean.user;

import java.io.Serializable;

/**
 * 类  名：会员等级
 * 作  者：Li Yubing
 * 日  期：2016/12/22
 * 描  述：
 */
public class LevelInfoBean implements Serializable{

    /**
     * 会员等级id
     */
    private long levelid;
    /**
     * 会员等级名称
     */
    private String levelName;
    /**
     * 会员等级logo
     */
    private String  levelLogo;

    public long getLevelid()
    {
        return levelid;
    }

    public void setLevelid(long levelid)
    {
        this.levelid = levelid;
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
}
