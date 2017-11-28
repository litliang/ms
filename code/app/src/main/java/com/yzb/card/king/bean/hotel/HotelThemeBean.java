package com.yzb.card.king.bean.hotel;

import java.io.Serializable;

/**
 * 类  名：酒店主题类
 * 作  者：Li Yubing
 * 日  期：2016/7/14
 * 描  述：
 */
public class HotelThemeBean implements Serializable{

    private  long themeId;

    private String themeName;

    private String themeIntro;

    private  int browseCount;

    private  String bgImageUrl;


    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeIntro() {
        return themeIntro;
    }

    public void setThemeIntro(String themeIntro) {
        this.themeIntro = themeIntro;
    }

    public long getThemeId()
    {
        return themeId;
    }

    public void setThemeId(long themeId)
    {
        this.themeId = themeId;
    }

    public int getBrowseCount()
    {
        return browseCount;
    }

    public void setBrowseCount(int browseCount)
    {
        this.browseCount = browseCount;
    }

    public String getBgImageUrl() {
        return bgImageUrl;
    }

    public void setBgImageUrl(String bgImageUrl) {
        this.bgImageUrl = bgImageUrl;
    }
}
