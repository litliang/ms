package com.yzb.card.king.bean.hotel;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/19
 * 描  述：床型
 */
public class BedTypeBean {

    private String bedTypeName;

    private int bedTypeValue;

    private boolean selectedFlag = false;

    public String getBedTypeName()
    {
        return bedTypeName;
    }

    public void setBedTypeName(String bedTypeName)
    {
        this.bedTypeName = bedTypeName;
    }

    public int getBedTypeValue()
    {
        return bedTypeValue;
    }

    public void setBedTypeValue(int bedTypeValue)
    {
        this.bedTypeValue = bedTypeValue;
    }

    public boolean isSelectedFlag()
    {
        return selectedFlag;
    }

    public void setSelectedFlag(boolean selectedFlag)
    {
        this.selectedFlag = selectedFlag;
    }
}
