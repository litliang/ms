package com.yzb.card.king.ui.credit.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/11 11:15
 * 描述：
 */
public class Bill implements Serializable{
    public Date transTime;
    public String transContent;
    public float transAmount;

    public int type;

    public Bill(Date transTime, String transContent, float transAmount) {
        this.transTime = transTime;
        this.transContent = transContent;
        this.transAmount = transAmount;
    }

    public Bill() {

    }
}
