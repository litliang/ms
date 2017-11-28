package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * Created by dev on 2016/5/9.
 */
public class SideBar extends View {
    public static final String TAG = SideBar.class.getName();
    private static final int MAX_SIZE = 28;
    private List<String> letters;
    private int textColor = getResources().getColor(R.color.ticket_red);

    private OnLetterTouchListener letterTouchListener;

    public int getTextColor()
    {
        return textColor;
    }

    public void setTextColor(int textColor)
    {
        this.textColor = textColor;
    }

    public SideBar(Context context)
    {
        super(context);
        init(context);
    }

    public SideBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context)
    {
    }

    /**
     * 每一项的高度
     */
    private float itemHeight = -1;

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (letters == null || letters.size() <= 0) {
            return;
        }
        if (itemHeight == -1) {
            itemHeight = getHeight() / MAX_SIZE;
        }
        //初始化画笔
        Paint paint = new Paint();
        paint.setTextSize(itemHeight / 3 * 2);
        //字体颜色
        paint.setColor(getTextColor());
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        //创建一张包含所有列表的图
        Canvas mCanvas = new Canvas();
        Bitmap letterBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        // Log.v(TAG, "width,height" + getMeasuredWidth() + ":" +getMeasuredHeight());
        mCanvas.setBitmap(letterBitmap);
        float widthCenter = getMeasuredWidth() / 2.0F;
        //画字符上图片中
        for (int i = 0; i < letters.size(); i++) {
            LogUtil.e("qazwsx", "-----" + letters.get(i));
            mCanvas.drawText(letters.get(i), widthCenter - paint.measureText(letters.get(i)) / 2,
                    itemHeight * i + itemHeight, paint);
        }
        if (letterBitmap != null) {//图片不为空就画图
            canvas.drawBitmap(letterBitmap, 0, 0, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        super.onTouchEvent(event);
        if (letterTouchListener == null || letters == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int position = (int) (event.getY() / itemHeight);
                if (position >= 0 && position < letters.size()) {
                    letterTouchListener.onLetterTouch(letters.get(position), position);
                }
                return true;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_UP:
                letterTouchListener.onActionUp();
                return true;
        }
        return false;
    }

    /**
     * 设置显示在边栏上的字母
     *
     * @param letters
     */
    public void setShowString(List<String> letters)
    {
        if (this.letters != null) {
            this.letters.clear();

            this.letters.addAll(letters);
        } else {

            this.letters = letters;

        }
        invalidate();
    }

    /**
     * 设置点击某个字母的时候
     *
     * @param listener
     */
    public void setOnLetterTouchListener(OnLetterTouchListener listener)
    {
        this.letterTouchListener = listener;
    }

    public interface OnLetterTouchListener {
        /**
         * 某个字母被按下的时候
         *
         * @param letter
         * @param position
         */
        public void onLetterTouch(String letter, int position);

        /**
         * 触控手指离开的时候
         */
        public void onActionUp();
    }
}
