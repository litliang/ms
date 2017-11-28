package com.yzb.card.king.ui.gift.bean;

/**
 * 功能：礼品卡面额
 *
 * @author:gengqiyun
 * @date: 2016/12/18
 */
public class GiftcardLimitBean implements Cloneable
{
    private int limit; //面额；
    private boolean isSelect; //是否选中；
    private boolean isUserDefined; //是否是自定义；
    private int sheetNum; //张数；

    public int getLimit()
    {
        return limit;
    }

    public boolean isUserDefined()
    {
        return isUserDefined;
    }

    public void setIsUserDefined(boolean isUserDefined)
    {
        this.isUserDefined = isUserDefined;
    }

    @Override
    public GiftcardLimitBean clone() throws CloneNotSupportedException
    {
        return (GiftcardLimitBean) super.clone();
    }

    public int getSheetNum()
    {
        return sheetNum;
    }

    public void setSheetNum(int sheetNum)
    {
        this.sheetNum = sheetNum;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public boolean isSelect()
    {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect)
    {
        this.isSelect = isSelect;
    }
}
