package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 功能：可获取高度的LinearLayout；
 *
 * @author:gengqiyun
 * @date: 2016/11/24
 */
public class MeasureHeightLinearLayout extends LinearLayout
{
    public MeasureHeightLinearLayout(Context context)
    {
        super(context);
    }

    public MeasureHeightLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if (callBack != null)
        {
            callBack.heightChange(h);
        }
    }

    private ICallBack callBack;

    public void setCallBack(ICallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface ICallBack
    {
        void heightChange(int newHeight);
    }
}
