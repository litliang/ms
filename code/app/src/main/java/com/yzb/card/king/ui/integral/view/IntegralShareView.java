package com.yzb.card.king.ui.integral.view;

import com.yzb.card.king.bean.integral.IntegralShareLinkman;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 15:48
 */
public interface IntegralShareView
{
    void onContactsLoadSuc(List<IntegralShareLinkman> o);

    void onContactLoadFailed(Object o);
}
