package com.yzb.card.king.ui.appwidget;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2016/6/7
 */
public class MyTextWatcher implements TextWatcher
{
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        textChange(s.toString().trim());
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        afterTextChange(s.toString().trim());
    }

    public void textChange(String s)
    {

    }

    public void afterTextChange(String text)
    {

    }

}
