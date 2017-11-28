package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/27 10:41
 * 描述：
 */
public class TextCycleItem implements Serializable{

    /**
     * themeName : 1折特卖
     * themeId : 1
     */

    private String themeName;
    private int themeId;

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }
}
