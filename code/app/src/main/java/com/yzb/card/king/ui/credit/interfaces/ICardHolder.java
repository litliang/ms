package com.yzb.card.king.ui.credit.interfaces;

import com.yzb.card.king.ui.credit.bean.CreditCard;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/25
 */

public interface ICardHolder<T>
{
    void goNextPage(T data);
    //绑卡
    void bindCard(T data);
     //解绑
    void unBindCard(T data);
}
