package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;

/**
 * Created by Timmy on 16/7/9.
 */
public class PlaneTicketsView extends View {
    private String startTime;
    private String endTime;
    private String startCity;
    private String endCity;
    private String companyName;
    private int copanyLogo;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private Paint mPaint;
    private int textSize;

    public PlaneTicketsView(Context context) {
        super(context, null);
    }

    public PlaneTicketsView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public PlaneTicketsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(long startTime, long endTime, String startCity, String endCity, String companyName, int copanyLogoId, int paddingTop, int paddingBottom, int paddingLeft, int paddingRight,int textSize) {
        this.paddingTop = UiUtils.dp2px(paddingTop);
        this.paddingBottom = UiUtils.dp2px(paddingBottom);
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        this.startTime = Utils.toData(startTime, 3);
        this.endTime = Utils.toData(endTime, 3);
        this.startCity = startCity;
        this.endCity = endCity;
        this.companyName = companyName;
        this.copanyLogo = copanyLogoId;
        this.textSize = textSize;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(UiUtils.sp2px(getContext(), 15));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPaint == null) {
            return;
        }
        int viewPadding = UiUtils.dp2px(10);
        float radius = UiUtils.dp2px(3);

        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.LEFT);
        //draw startTime
        canvas.drawText(startTime, paddingLeft, paddingTop, mPaint);

        //draw line
        mPaint.setColor(getResources().getColor(R.color.ticket_gray4));
        mPaint.setStyle(Paint.Style.FILL);
        float startTimeWidth = mPaint.measureText(startTime);
        int lineWith = UiUtils.dp2px(2);
        float lineLeft = paddingLeft + startTimeWidth + (viewPadding * 2);
        float lineTop = paddingTop;
        float lineRight = lineLeft + lineWith;
        float lineBottom = getHeight() - paddingBottom - radius - mPaint.getTextSize();
        RectF lineRectf = new RectF(lineLeft, lineTop, lineRight, lineBottom);
        canvas.drawRect(lineRectf, mPaint);

        //draw circle
        mPaint.setStyle(Paint.Style.FILL);
        float cx = lineLeft + lineWith / 2;
        float cy = paddingTop;
        canvas.drawCircle(cx, cy, radius, mPaint);
        canvas.drawCircle(cx, getHeight() - paddingBottom - radius - mPaint.getTextSize(), radius, mPaint);

        mPaint.setColor(Color.BLACK);
        //draw endTime
        mPaint.setStyle(Paint.Style.STROKE);
        float endTimeX = paddingLeft;
        float endTimeY = getHeight() - paddingBottom - radius;
        canvas.drawText(endTime, endTimeX, endTimeY, mPaint);

        //draw startLocation
        float startCityX = lineRight + (viewPadding * 2);
        float startCityY = paddingTop;
        canvas.drawText(startCity, startCityX, startCityY, mPaint);

        //draw endLocation
        float endCityX = lineRight + viewPadding;
        float endCityY = getHeight() - paddingBottom - radius;
        canvas.drawText(endCity, endCityX, endCityY, mPaint);

        //draw companName
        mPaint.setTextAlign(Paint.Align.RIGHT);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        textPaint.setTextSize(UiUtils.sp2px(getContext(), 14));
        int companLeft = getWidth() - paddingRight - UiUtils.dp2px(60);
        int companRight = getWidth() - paddingRight;
        Rect companRect = new Rect(companLeft, paddingTop, companRight, UiUtils.dp2px(10));
        StaticLayout layout = new StaticLayout(companyName, textPaint, companRect.width(), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        canvas.translate(companRect.left, companRect.top);
        layout.draw(canvas);

        //draw logo
//        Bitmap logoBt = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_air_logo);
//        canvas.drawBitmap(logoBt, getWidth() - logoBt.getWidth(), lineTop, mPaint);

        invalidate();
    }
}