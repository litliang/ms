package com.yzb.card.king.ui.app.bean;

import java.io.Serializable;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/21 19:41
 * 描述：
 */
public class AppSettingItem implements Serializable
{
    public String type;
    public int preDay;

    public String getName(){
        String typeName = "";
        switch (type)
        {
            case "1":
                typeName = "信用卡还款";
                break;
            case "2":
                typeName = "积分到期";
                break;
            case "3":
                typeName = "优惠活动";
                break;
            case "4":
                typeName = "社交活动";
                break;
        }
        return typeName;
    }
}
