package com.yzb.card.king.ui.bonus.bean;

import java.io.Serializable;

/**
 * 功能：红包主题；
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public class BounsThemeBean implements Serializable
{
    private String themeId;// Long	主题id	N
    private String themeName;//	String	主题名称	N
    private String closeImageCode;    //未拆开图片
    private String openImageCode;    //拆开图片
    private String blessWord; //	String	祝福语	Y

    private boolean isSelect;

    public boolean isSelect()
    {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect)
    {
        this.isSelect = isSelect;
    }

    public String getThemeId()
    {
        return themeId;
    }

    public void setThemeId(String themeId)
    {
        this.themeId = themeId;
    }

    public String getThemeName()
    {
        return themeName;
    }

    public void setThemeName(String themeName)
    {
        this.themeName = themeName;
    }

    public String getCloseImageCode()
    {
        return closeImageCode;
    }

    public void setCloseImageCode(String closeImageCode)
    {
        this.closeImageCode = closeImageCode;
    }

    public String getOpenImageCode()
    {
        return openImageCode;
    }

    public void setOpenImageCode(String openImageCode)
    {
        this.openImageCode = openImageCode;
    }

    public String getBlessWord()
    {
        return blessWord;
    }

    public void setBlessWord(String blessWord)
    {
        this.blessWord = blessWord;
    }
}
