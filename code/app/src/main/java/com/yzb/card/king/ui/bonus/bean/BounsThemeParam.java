package com.yzb.card.king.ui.bonus.bean;

import java.io.Serializable;

/**
 * 功能：红包主题参数；
 *
 * @author:gengqiyun
 * @date: 2017/2/6
 */
public class BounsThemeParam implements Serializable
{
    private String themeId;//
    private String bounsSender; //发送者昵称；

    private String themeName;//	String	主题名称	N
    private String issueImageCode;    //String	发送方图片	N
    private String receiveImageCode;    //String	接收方图片	N

    private String blessWord;//红包祝福语；
    private String bounsNum; //红包数量；
    private String bounsAmount;//红包金额；

    private String closeImageCode;    //未拆开图片
    private String openImageCode;    //拆开图片

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

    private boolean isRandom; //是否随机；

    public boolean isRandom()
    {
        return isRandom;
    }

    public void setIsRandom(boolean isRandom)
    {
        this.isRandom = isRandom;
    }

    public String getBounsSender()
    {
        return bounsSender;
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

    public String getIssueImageCode()
    {
        return issueImageCode;
    }

    public void setIssueImageCode(String issueImageCode)
    {
        this.issueImageCode = issueImageCode;
    }

    public String getReceiveImageCode()
    {
        return receiveImageCode;
    }

    public void setReceiveImageCode(String receiveImageCode)
    {
        this.receiveImageCode = receiveImageCode;
    }

    public void setBounsSender(String bounsSender)
    {
        this.bounsSender = bounsSender;
    }


    public String getBounsNum()
    {
        return bounsNum;
    }

    public void setBounsNum(String bounsNum)
    {
        this.bounsNum = bounsNum;
    }

    public String getBlessWord()
    {
        return blessWord;
    }

    public void setBlessWord(String blessWord)
    {
        this.blessWord = blessWord;
    }

    public String getBounsAmount()
    {
        return bounsAmount;
    }

    public void setBounsAmount(String bounsAmount)
    {
        this.bounsAmount = bounsAmount;
    }
}
