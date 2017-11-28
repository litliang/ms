package com.yzb.card.king.ui.my.enu;

/**
 * 描述：账户类型
 * 作者：殷曙光
 * 日期：2017/1/13 16:35
 */
public enum AccountType
{
    FLASH_PAY("0","快捷支付"),
    CASH("1","现金"),
    RED_BAG("2","红包"),
    GIFT_CARD("3","礼品卡"),
    PLATFORM_POINT("4","平台积分"),
    SHOP_POINT("5","商家积分");

    private String value;
    private String name;

    AccountType(String value, String name)
    {
        this.value = value;
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
