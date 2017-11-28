package com.yzb.card.king.ui.my.enu;

/**
 * 描述：订单类型
 * 作者：殷曙光
 * 日期：2017/2/15 18:04
 */
public enum OrderType {
    TRAIN("1", "火车票"),
    PLANE("2", "机票"),
    SHIP("3", "船票"),
    CAR("4", "汽车票"),
    TAXI("5", "叫车"),
    TRAVEL("6", "旅游"),
    HOTEL("7", "酒店"),
    GIFT_CARD("8", "礼品卡"),
    REG_BAG("9", "紅包"),
    CHARGE("10", "充值"),
    TRANSFER("11", "转账"),
    CASH("12", "提现"),
    REPAYMENT("13", "还款"),
    //    FULL_REPAY("14","全额代还"),
//    VALID_CARD("15","卡片驗證");
    FULL_REPAY("19", "全额代还"),
    VALID_CARD("20", "卡片驗證");

    private String value;
    private String name;

    OrderType(String value, String name)
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
