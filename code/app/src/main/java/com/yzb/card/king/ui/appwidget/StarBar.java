package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.util.LogUtil;

import java.math.BigDecimal;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/12/26
 * 描  述：
 */
public class StarBar extends View {

    private int starDistance = 0; //星星间距

    private int starCount = 5;  //星星个数

    private int starSize;     //星星高度大小，星星一般正方形，宽度等于高度

    private int starW =-1;//星星宽度

    private float starMark = 0.0F;   //评分星星

    private Bitmap starFillBitmap; //亮星星

    private Drawable starEmptyDrawable; //暗星星

    private OnStarChangeListener onStarChangeListener;//监听星星变化接口

    private Paint paint;         //绘制星星画笔

    private boolean integerMark = false;//整数和浮点型

    private boolean isIndicator = false;//是否开始手势绘制功能

    private boolean ifSameSquare = false;//星星宽高是否接近正方形

    private float whScale ;//宽高比

    private boolean  halfStart = false;

    public  void isHalfStart(boolean halfStart){
        this.halfStart = halfStart;
    }

    public StarBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public StarBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化UI组件
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs)
    {
        setClickable(true);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        this.ifSameSquare = mTypedArray.getBoolean(R.styleable.RatingBar_ifSameSquare,false);
        this.isIndicator = mTypedArray.getBoolean(R.styleable.RatingBar_isIndicator, false);
        this.starDistance = (int) mTypedArray.getDimension(R.styleable.RatingBar_starDistance, 0);
        this.starSize = (int) mTypedArray.getDimension(R.styleable.RatingBar_starSize, 20);
        this.whScale =   mTypedArray.getFloat(R.styleable.RatingBar_whScale,1);
        //计算图像的宽度，计算出的宽度有小数进1
        float temp = starSize * whScale;
        BigDecimal b1 = new BigDecimal(temp);
        this.starW = b1.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        this.starCount = mTypedArray.getInteger(R.styleable.RatingBar_starCount, 5);
        this.starEmptyDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starEmpty);
        this.starFillBitmap = drawableToBitmap(mTypedArray.getDrawable(R.styleable.RatingBar_starFill),starW,starSize);
        mTypedArray.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(starFillBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
    }

    /**
     * 设置是否需要整数评分
     *
     * @param integerMark
     */
    public void setIntegerMark(boolean integerMark)
    {
        this.integerMark = integerMark;
    }

    /**
     * 设置显示的星星的分数
     *
     * @param mark
     */
    public void setStarMark(float mark)
    {
        if (integerMark) {
            starMark = (int) Math.ceil(mark);
        } else {
            starMark = Math.round(mark * 10) * 1.0f / 10;

            if(halfStart){
                String str = String.valueOf(starMark);

                String[] a = str.split("\\.");

                System.out.println(a.length);

                int q = Integer.parseInt(a[0]);

                int w = Integer.parseInt(a[1]);

                if(w==5 ){

                }else if(w>0&&w<5){

                    w = 0;
                }else if(w>5&&w<8){

                    w =5 ;

                }else if(w>=8){

                    w=0 ;
                    q = q+1;
                }

                String abc = q+"."+w;


                starMark = Float.parseFloat(abc);
            }
        }
        if (this.onStarChangeListener != null) {
            this.onStarChangeListener.onStarChange(starMark);  //调用监听接口
        }
        invalidate();
    }

    /**
     * 设置星星属于与分数一致
     */
    public void setStarMarkAndSore(float mark)
    {
        BigDecimal b = new BigDecimal(mark);
        this.starCount = b.setScale(0, BigDecimal.ROUND_UP).intValue();
        setStarMark(mark);

    }

    /**
     * 获取显示星星的数目
     *
     * @return starMark
     */
    public float getStarMark()
    {
        return starMark;
    }


    /**
     * 定义星星点击的监听接口
     */
    public interface OnStarChangeListener {
        void onStarChange(float mark);
    }

    /**
     * 设置监听
     *
     * @param onStarChangeListener
     */
    public void setOnStarChangeListener(OnStarChangeListener onStarChangeListener)
    {
        this.onStarChangeListener = onStarChangeListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
 //      int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int width = starSize * starCount + starDistance * (starCount - 1);
//
//        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,widthMode);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (ifSameSquare) {

            setMeasuredDimension(starSize * starCount + starDistance * (starCount - 1), starSize);

        } else {

            setMeasuredDimension(starW * starCount + starDistance * (starCount - 1), starSize);

        }

    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if (starFillBitmap == null || starEmptyDrawable == null) {

            return;
        }
        //绘制底色
        for (int i = 0; i < starCount; i++) {

            if (ifSameSquare) {

                starEmptyDrawable.setBounds((starDistance + starSize) * i, 0, (starDistance + starSize) * i + starSize, starSize);

            } else {

                int left = starW * i + starDistance * i;

                int right = (i + 1) * starW + starDistance * i;

                starEmptyDrawable.setBounds(left, 0, right, starSize);
            }

            starEmptyDrawable.draw(canvas);
        }
        //绘制星星评分图像
        if (starMark > 1) {

            if (ifSameSquare) {

                canvas.drawRect(0, 0, starSize, starSize, paint);

            } else {

                canvas.drawRect(0, 0, starW, starSize, paint);
            }

            if (starMark - (int) (starMark) == 0) {
                for (int i = 1; i < starMark; i++) {

                    if (ifSameSquare) {

                        canvas.translate(starDistance + starSize, 0);

                        canvas.drawRect(0, 0, starSize, starSize, paint);

                    } else {

                        canvas.translate(starDistance + starW, 0);

                        canvas.drawRect(0, 0, starW, starSize, paint);
                    }

                }

            } else {

                for (int i = 1; i < starMark - 1; i++) {

                    if (ifSameSquare) {

                        canvas.translate(starDistance + starSize, 0);

                        canvas.drawRect(0, 0, starSize, starSize, paint);

                    } else {

                        canvas.translate(starDistance + starW, 0);

                        canvas.drawRect(0, 0, starW, starSize, paint);
                    }
                }
                if (ifSameSquare) {

                    canvas.translate(starDistance + starSize, 0);

                    canvas.drawRect(0, 0, starSize * (Math.round((starMark - (int) (starMark)) * 10) * 1.0f / 10), starSize, paint);

                }else{
                    canvas.translate(starDistance + starW, 0);

                    canvas.drawRect(0, 0, starW * (Math.round((starMark - (int) (starMark)) * 10) * 1.0f / 10), starSize, paint);
                }
            }
        } else {

            if (ifSameSquare) {

                canvas.drawRect(0, 0, starSize * starMark, starSize, paint);

            } else {

                canvas.drawRect(0, 0, starW * starMark, starSize, paint);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        if (!isIndicator) {
            return super.onTouchEvent(event);
        }
        int x = (int) event.getX();
        if (x < 0) x = 0;
        if (x > getMeasuredWidth()) x = getMeasuredWidth();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                setStarMark(x * 1.0f / (getMeasuredWidth() * 1.0f / starCount));

                break;
            }
            case MotionEvent.ACTION_MOVE: {

                setStarMark(x * 1.0f / (getMeasuredWidth() * 1.0f / starCount));

                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable,int newW,int newH)
    {
        if (drawable == null) return null;

        if(newW<=0){

            newW = newH;
        }
        if(newH<=0){
            newH= newW;
        }

        Bitmap bitmap = Bitmap.createBitmap(newW, newH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, newW, newH);
        drawable.draw(canvas);
        return bitmap;
    }
}
