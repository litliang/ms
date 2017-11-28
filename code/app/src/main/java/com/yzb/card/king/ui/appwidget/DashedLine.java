package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.yzb.card.king.R;

import org.xutils.common.util.DensityUtil;

/**
 * 画垂直虚线
 * Created by ZQiang94 on 2015/12/20.
 */
public class DashedLine extends View
{

    public DashedLine(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.color_68c1fe));
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, DensityUtil.dip2px(47));

        int distance = DensityUtil.dip2px(2);
        PathEffect effects = new DashPathEffect(new float[]{distance, distance, distance, distance}, 0);
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }
}