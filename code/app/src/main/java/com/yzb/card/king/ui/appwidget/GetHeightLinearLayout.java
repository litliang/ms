package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.util.LogUtil;

/**
 * 功能：动态获取高度的LinearLayout；
 *
 * @author:gengqiyun
 * @date: 2016/8/25
 */
public class GetHeightLinearLayout extends LinearLayout {
    private IHeightChangeListener heightChangeListener;

    public void setHeightChangeListener(IHeightChangeListener heightChangeListener) {
        this.heightChangeListener = heightChangeListener;
    }

    public GetHeightLinearLayout(Context context) {
        super(context);
    }

    public GetHeightLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (heightChangeListener != null) {
            LogUtil.i("GetHeightLinearLayout==" + h);
            heightChangeListener.heiChange(h);
        }
    }

    public interface IHeightChangeListener {
        void heiChange(int newHeight);
    }
}
