package com.yzb.card.king.ui.gift.bean;

import java.io.Serializable;

/**
 * 功能：常用收礼人
 *
 * @author:gengqiyun
 * @date: 2017/2/17
 */
public class FavorPayee implements Serializable
{
    private String photoUrl;

    private String creditName; //平台账户时为平台昵称，银行卡时为银行卡户主

    private String tradeAccount; //平台账户

    private boolean checked = false;

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public String getPhotoUrl()
    {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl)
    {
        this.photoUrl = photoUrl;
    }

    public String getCreditName()
    {
        return creditName;
    }

    public void setCreditName(String creditName)
    {
        this.creditName = creditName;
    }

    public String getTradeAccount()
    {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount)
    {
        this.tradeAccount = tradeAccount;
    }
}
