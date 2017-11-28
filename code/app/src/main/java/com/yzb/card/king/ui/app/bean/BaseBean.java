package com.yzb.card.king.ui.app.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2016/10/19
 */
public class BaseBean implements Serializable,Cloneable
{
    /**
     * 字符串判空；
     *
     * @param content
     * @return
     */
    protected String isStrEmpty(String content)
    {
        return TextUtils.isEmpty(content) ? "" : content;
    }


}
