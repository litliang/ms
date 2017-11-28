package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.util.LogUtil;

/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2016/8/1
 */
public class GetHeightTextView extends TextView {
    private IHeightChangeListener heightChangeListener;

    public void setHeightChangeListener(IHeightChangeListener heightChangeListener) {
        this.heightChangeListener = heightChangeListener;
    }

    public GetHeightTextView(Context context) {
        super(context);
    }

    public GetHeightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (heightChangeListener != null) {
            LogUtil.i("GetHeightTextView==" + h);
            heightChangeListener.heiChange(h);
        }
    }

    public interface IHeightChangeListener {
        void heiChange(int newHeight);
    }
}
