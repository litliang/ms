package com.yzb.card.king.ui.app.interfaces;

import android.content.Context;
import android.text.InputType;
import android.text.method.DigitsKeyListener;

import com.yzb.card.king.R;

/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2016/7/1
 * 输入英文字母的listener；
 */
public class CardDigitsKeyListener extends DigitsKeyListener {
    private Context context;

    public CardDigitsKeyListener(Context context) {
        this.context = context;
    }

    @Override
    public int getInputType() {
        return InputType.TYPE_TEXT_VARIATION_FILTER;
    }

    @Override
    protected char[] getAcceptedChars() {
        return context.getString(R.string.setting_eng_letters).toCharArray();
    }
}
