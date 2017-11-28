package com.yzb.card.king.util;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * 功能：文本块的点击监听；
 *
 * @author:gengqiyun
 * @date: 2016/9/30
 */
public abstract class TextClickableSpan extends ClickableSpan
{

    @Override
    public void onClick(View widget)
    {
        onClick();
    }

    @Override
    public void updateDrawState(TextPaint ds)
    {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }

    public abstract void onClick();
}
