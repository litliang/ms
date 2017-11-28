package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.util.photoutils.BitmapUtil;

/**
 * Created by yinsg on 2016/5/26.
 */
public class SlideButtonVertical extends View
{
    private Bitmap background;
    private Bitmap button;
    private ToggleState state = ToggleState.close;
    private boolean isMoving = false;
    private int centerY;
    private Bitmap resizeBg;
    private Bitmap resizeBt;

    private String closeText = "送机";
    private String openText = "接机";

    public SlideButtonVertical(Context context)
    {
        super(context);
        init();
    }

    public SlideButtonVertical(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SlideButtonVertical(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        background = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_check_bg_grey_vertical);
        button = BitmapFactory.decodeResource(getResources(), R.mipmap.button_round_red);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        float width = getMeasuredWidth();
        float height = getMeasuredHeight();

        if (background != null && button != null)
        {
            float bgHeight = background.getHeight();
            float bgWidth = background.getWidth();
            float btHeight = button.getHeight();
            float btWidth = button.getWidth();
            float deWidth = 0f;
            float deHeight = 0f;
            int padding = 8;

            float scaleWidth = width / bgWidth;
            float scaleHeight = height / bgHeight;
            int bgScaleHeight = (int) (bgHeight * scaleHeight);
            int bgScaleWidth = (int) (bgWidth * scaleWidth);
            int btScaleHeight = bgScaleWidth - padding;
            int btScaleWidth = bgScaleWidth - padding;

            int scaleDeWidth = (int) (deWidth * scaleWidth);
            int scaleDeHeight = (int) (deHeight * scaleHeight);

            resizeBg = Bitmap.createScaledBitmap(background, bgScaleWidth, bgScaleHeight, true);
            resizeBt = Bitmap.createScaledBitmap(button, btScaleWidth, btScaleHeight, true);
            float left = (bgScaleWidth - btScaleWidth) / 2 + scaleDeWidth;

            canvas.drawBitmap(resizeBg, 0, 0, new Paint());
            Paint textPaint = new Paint();
            textPaint.setColor(Color.WHITE);
            float textSize = bgScaleWidth / 4;
            textPaint.setTextSize(textSize);

            float textLeft = (bgScaleWidth - textPaint.measureText(closeText)) / 2;
            canvas.drawText(closeText, textLeft, btScaleHeight / 2, textPaint);
            canvas.drawText(openText, textLeft, bgScaleHeight - btScaleHeight / 2 + textSize / 2, textPaint);

            if (state == ToggleState.close)
            {
                resizeBt = createDrawable(closeText);
            } else
            {
                resizeBt = createDrawable(openText);
            }
            if (isMoving)
            {
                float top = centerY - btScaleHeight / 2;
                if (top < 0 - scaleDeHeight) top = 0 - scaleDeHeight;
                if (top > bgScaleHeight - btScaleHeight + scaleDeHeight)
                    top = bgScaleHeight - btScaleHeight + scaleDeHeight;
                canvas.drawBitmap(resizeBt, left, top, new Paint());
            } else
            {
                if (state == ToggleState.close)
                {
                    resizeBt = createDrawable(closeText);
                    canvas.drawBitmap(resizeBt, left, 0 - scaleDeHeight, new Paint());
                } else
                {
                    resizeBt = createDrawable(openText);
                    canvas.drawBitmap(resizeBt, left, bgScaleHeight - btScaleHeight + scaleDeHeight, new Paint());
                }
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        centerY = (int) event.getY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                isMoving = true;
                break;
            case MotionEvent.ACTION_MOVE:
                isMoving = true;
                break;
            case MotionEvent.ACTION_UP:
                isMoving = false;
                if (Math.abs(centerY - event.getY()) < 8)
                {//点击事件
                    if (state == ToggleState.open)
                    {
                        state = ToggleState.close;
                    } else
                    {
                        state = ToggleState.open;
                    }
                    if (listener != null)
                    {
                        listener.onToggleStateChange(state);
                    }
                } else
                {
                    ToggleState flag;
                    if (centerY < resizeBg.getHeight() / 2)
                    {
                        flag = ToggleState.close;
                    } else
                    {
                        flag = ToggleState.open;
                    }
                    if (flag != state)
                    {
                        state = flag;
                        if (listener != null)
                        {
                            listener.onToggleStateChange(state);
                        }
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    private Bitmap createDrawable(String letter)
    {
        int width = resizeBt.getWidth();
        int height = width;
        Bitmap imgTemp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imgTemp);
        Paint paint = new Paint(); // 建立画笔
        paint.setDither(true);
        paint.setFilterBitmap(true);
        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect(0, 0, width, height);
        canvas.drawBitmap(resizeBt, src, dst, paint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        float textSize = 20.0f;
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD); // 采用默认的宽度
        textPaint.setColor(Color.WHITE);

        canvas.drawText(String.valueOf(letter), (width - textPaint.measureText(letter)) / 2, height / 2 + textSize / 2 - 5,
                textPaint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return imgTemp;

    }


    public void setButtonResource(int resourceId)
    {
        button = BitmapFactory.decodeResource(getResources(), resourceId);
    }

    public void setToggleState(ToggleState state)
    {
        this.state = state;
        if (listener != null)
        {
            listener.onToggleStateChange(state);
        }
    }

    public ToggleState getState()
    {
        return state;
    }

    public enum ToggleState
    {
        open, close
    }

    private OnToggleStateChangeListener listener;

    public void setOnToggleStateChangeListener(OnToggleStateChangeListener listener)
    {
        this.listener = listener;
    }

    public interface OnToggleStateChangeListener
    {
        void onToggleStateChange(ToggleState state);
    }

    public void setCloseText(String closeText)
    {
        this.closeText = closeText;
    }

    public void setOpenText(String openText)
    {
        this.openText = openText;
    }
}
