package com.yzb.card.king.bean.food;

import java.io.Serializable;

/**
 * 类  名：美食主题类
 * 作  者：Li Yubing
 * 日  期：2016/7/20
 * 描  述：
 */
public class FoodThemeBean implements Serializable{

    private  String themeId;

    private String themeName;

    private String themeIntro;

    private  String browseCount;

    private  String bgImage;

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

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

    public String getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(String browseCount) {
        this.browseCount = browseCount;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }
}
