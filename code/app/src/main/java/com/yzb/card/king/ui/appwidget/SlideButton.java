package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yzb.card.king.R;

/**
 * Created by yinsg on 2016/5/25.
 */
public class SlideButton extends View
{
    private Bitmap background;
    private Bitmap backgroundDefault;
    private Bitmap button;
    private ToggleState state = ToggleState.close;
    private boolean isMoving = false;
    private int centerX;
    private Bitmap resizeDbg;
    private OnToggleViewUpdateListener updateListener;
    private boolean enable = true;

    public SlideButton(Context context)
    {
        super(context);
        init();
    }

    public SlideButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SlideButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }


    private int bgIconResId = R.mipmap.icon_check_bg_red;
    private int bgIconDefaultResId = R.mipmap.icon_check_bg_grey;
    private int buttonResId = R.mipmap.icon_check_round;


    /**
     * 設置背景资源；
     *
     * @param bgIconResId
     */
    public void setBgIconResId(int bgIconResId)
    {
        this.bgIconResId = bgIconResId;
        background = BitmapFactory.decodeResource(getResources(), bgIconResId);
        invalidate();
    }

    private void init()
    {
        background = BitmapFactory.decodeResource(getResources(), bgIconResId);
        backgroundDefault = BitmapFactory.decodeResource(getResources(), bgIconDefaultResId);
        button = BitmapFactory.decodeResource(getResources(), buttonResId);
        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                invalidate();
            }
        });
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
            float deWidth = 4f;
            float deHeight = 0f;

            float scaleWidth = width / bgWidth;
            float scaleHeight = height / bgHeight;
            int bgScaleHeight = (int) (bgHeight * scaleHeight);
            int bgScaleWidth = (int) (bgWidth * scaleWidth);
            int btScaleHeight = (int) (btHeight * scaleHeight);
            int btScaleWidth = (int) (btWidth * scaleHeight);

            int scaleDeWidth = (int) (deWidth * scaleWidth);
            int scaleDeHeight = (int) (deHeight * scaleHeight);

            resizeDbg = Bitmap.createScaledBitmap(backgroundDefault, bgScaleWidth, bgScaleHeight, true);
            Bitmap resizeBg = Bitmap.createScaledBitmap(background, bgScaleWidth, bgScaleHeight, true);
            Bitmap resizeBt = Bitmap.createScaledBitmap(button, btScaleWidth, btScaleHeight, true);
            float top = (bgScaleHeight - btScaleHeight) / 2 + scaleDeHeight;

            if (state == ToggleState.close)
            {
                canvas.drawBitmap(resizeDbg, 0, 0, new Paint());
            } else
            {
                canvas.drawBitmap(resizeBg, 0, 0, new Paint());
            }
            if (isMoving)
            {
                float left = centerX - btScaleWidth / 2;
                if (left < 0 - scaleDeWidth) left = 0 - scaleDeWidth;
                if (left > bgScaleWidth - btScaleWidth + scaleDeWidth)
                    left = bgScaleWidth - btScaleWidth + scaleDeWidth;

                canvas.drawBitmap(resizeBt, left, top, new Paint());
            } else
            {
                if (state == ToggleState.close)
                {
                    canvas.drawBitmap(resizeBt, 0 - scaleDeWidth, top, new Paint());
                } else
                {
                    canvas.drawBitmap(resizeBt, bgScaleWidth - btScaleWidth + scaleDeWidth, top, new Paint());
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

    public void setEnable(boolean enable)
    {
        this.enable = enable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (enable)
        {
            centerX = (int) event.getX();
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
                    if (Math.abs(centerX - event.getX()) < 8)
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

                        if (updateListener != null)
                        {
                            updateListener.onToggleStateChange(this, state);
                        }
                    } else
                    {//滑动事件
                        ToggleState flag;
                        if (centerX < resizeDbg.getWidth() / 2)
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

                            if (updateListener != null)
                            {
                                updateListener.onToggleStateChange(this, state);
                            }
                        }
                    }
                    break;
            }
            invalidate();
            return true;
        } else
        {
            return false;
        }

    }

    public void setToggleState(ToggleState state)
    {
        this.state = state;
        if (this.listener != null)
        {
            this.listener.onToggleStateChange(state);
        }

        if (updateListener != null)
        {
            updateListener.onToggleStateChange(this, state);
        }
        invalidate();
    }

    public ToggleState getState()
    {
        return state;
    }

    /**
     * 切换状态；
     */
    public void toggleState()
    {
        this.state = getState() == ToggleState.open ? ToggleState.close : ToggleState.open;
        invalidate();
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

    public void setToggleViewUpdateListener(OnToggleViewUpdateListener listener)
    {
        this.updateListener = listener;
    }

    /**
     * 回调view的监听器；
     */
    public interface OnToggleViewUpdateListener
    {
        void onToggleStateChange(SlideButton view, ToggleState state);
    }
}
