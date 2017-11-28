package com.yzb.card.king.ui.appwidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Gallery;

/**
 * Created by Timmy on 16/5/26.
 */
public class CustomGallery extends Gallery {

    public CustomGallery(Context context) {
        this(context, null);
    }

    public CustomGallery(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
    }

    @SuppressLint("NewApi")
    public void setBlurBackgroundDrawable(Drawable background, int defaultId) {
        if (Build.VERSION.SDK_INT >= 14) {
            setBackground(background);
        } else {
            setBackgroundResource(defaultId);
        }
    }
}
