package com.yzb.card.king.ui.other.listeners;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 描述：控制小数位数，最多两位小数
 * 作者：殷曙光
 * 日期：2017/1/11 11:53
 */
public class MoneyFormatWatcher implements TextWatcher
{
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }

    @Override
    public void afterTextChanged(Editable s)
    {
        String temp = s.toString();
//        after(temp);
        int posDot = temp.indexOf(".");
        if (posDot > 0)
            if (temp.length() - posDot - 1 > 2)
            {
                s.delete(posDot + 3, posDot + 4);
            }
        if(temp.length()>0)
        {
            submitAble(Float.parseFloat(temp) > 0);
        }else {
            submitAble(false);
        }
    }

    public void submitAble(boolean submitAble)
    {

    }

//    public void after(String s)
//    {
//    }

}
