package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.yzb.card.king.R;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DensityUtil;

/**
 * @author yinsg
 * @date 2015/3/9
 */
public class RangeSeekBar extends ViewGroup {
    private Drawable mThumbDrawable;
    // private Drawable mThumbPlaceDrawable;

    private ThumbView mThumbLeft;   //左游标
    private ThumbView mThumbRight;  //右游标
    private int mProgressBarHeight;     //进度条的高度
    private int mThumbPlaceHeight;      //游标的高度

    private int mMaxValue = 100;   //分成100份，每一小格占2份

    private int mLeftValue;     //左游标  数值    (100分之多少)   例如：1就是 1/100
    private int mRightValue = mMaxValue;  //右游标  数值    (100分之多少)

    private int mLeftLimit;     //游标左边的限制坐标
    private int mRightLimit;        //游标右边的限制坐标
    private int proPaddingLeftAndRight;     //进度条左右的padding 等于游标图标宽度的一半
    private int mProBaseline;       //进度条top  坐标

    private int leftX, rightX;

    private int mThumbHight;

    private static final int PART_ITEM = 5;//半小 占的分数
    private float mPartWidth;   //每一小份的宽度

    public static final int SHORTLINE_HEIGHT = 5; //短线的高度 （画刻度时会有长短线）
    public static final int LONGLINE_HEIGHT = 20; //长线的高度

    public static final int RULE_HEIGHT_DP = 20;  //尺子的高度  dp
    public static int RULE_HEIGHT_PX;

    private String degs[] = {"0", "150", "300", "500", "800", "不限"};      //尺子上标记刻度值
    private String unitStr = "￥";     //尺子标记单位

    private int gray = Color.parseColor("#afafaf");

    private OnRangeChangeListener mOnRangeChangeListener;       //当左右任意一个游标改变时，回调接口


    public void setValues(int leftX, int rightX) {
        this.leftX = leftX;
        this.rightX = rightX;

    }

    private int priceToPartWith(int price) {
        int position = 0;
        if (price < 300) {
            position = 2 * price / 15;
        } else if (price < 500) {
            position = 10 + price / 10;
        } else if (price < 800) {
            position = 80 / 3 + price / 15;
        } else if (price < Integer.MAX_VALUE) {
            position = 2 * price / 45 + 400 / 9;
        } else if (price == Integer.MAX_VALUE) {
            position = 100;
        }
//        int proValue = mMaxValue * (view.getCenterX() - mLeftLimit) / (mRightLimit - mLeftLimit);

        return position * (mRightLimit - mLeftLimit) / mMaxValue + mLeftLimit;
    }

    public int get0position() {
        return mLeftLimit;
    }

    public int get300position() {
        return 41 * (mRightLimit - mLeftLimit) / mMaxValue + mLeftLimit;
    }

    public int getLeftX() {
        return mThumbLeft.getCenterX();
    }

    public int getRightX() {
        return mThumbRight.getCenterX();
    }


    public interface OnRangeChangeListener {
        void onRangeChange(int leftValue, int rightValue);
    }

    public RangeSeekBar(Context context) {
        this(context, null);
    }

    public RangeSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RangeSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setBackgroundDrawable(new BitmapDrawable());
        //换算px
        RULE_HEIGHT_PX = CommonUtil.dip2px(context, RULE_HEIGHT_DP);
        mProgressBarHeight = CommonUtil.dip2px(context, 1);

        mThumbDrawable = getResources().getDrawable(R.mipmap.slide_button);
        //  mThumbPlaceDrawable = getResources().getDrawable(R.mipmap.rod_place_icon);

        mThumbPlaceHeight = CommonUtil.dip2px(context, 40);
        mProBaseline = RULE_HEIGHT_PX + mThumbPlaceHeight;

        mThumbLeft = new ThumbView(getContext());
        mThumbLeft.setRangeSeekBar(this);
        mThumbLeft.setImageDrawable(mThumbDrawable);
        mThumbRight = new ThumbView(getContext());
        mThumbRight.setRangeSeekBar(this);
        mThumbRight.setImageDrawable(mThumbDrawable);
        mThumbHight = mThumbDrawable.getIntrinsicHeight() / 2;
        //measureView(mThumbLeft);

        addView(mThumbLeft);
        addView(mThumbRight);
        mThumbLeft.setOnThumbListener(new ThumbView.OnThumbListener() {
            @Override
            public void onThumbChange(int i) {
                mLeftValue = i;
                if (mOnRangeChangeListener != null) {
                    mOnRangeChangeListener.onRangeChange(mLeftValue, mRightValue);
                }

                int centerXLeft = mThumbLeft.getCenterX();
                int centerXRight = mThumbRight.getCenterX();

                if(centerXRight==mRightLimit){
                    mThumbLeft.setLimit(mLeftLimit, centerXRight-mThumbHight);
                }else{
                    mThumbLeft.setLimit(mLeftLimit, centerXRight);
                }

                mThumbRight.setLimit(centerXLeft,mRightLimit);


            }
        });
        mThumbRight.setOnThumbListener(new ThumbView.OnThumbListener() {
            @Override
            public void onThumbChange(int i) {
                mLeftValue = i;
                if (mOnRangeChangeListener != null) {
                    mOnRangeChangeListener.onRangeChange(mLeftValue, mRightValue);
                }

                int centerXLeft = mThumbLeft.getCenterX();
                int centerXRight = mThumbRight.getCenterX();
                mThumbLeft.setLimit(mLeftLimit, centerXRight);
                mThumbRight.setLimit(centerXLeft,mRightLimit);

            }
        });
    }

    public void setOnRangeChangeListener(OnRangeChangeListener mOnRangeChangeListener) {
        this.mOnRangeChangeListener = mOnRangeChangeListener;
    }

//    private void measureView(View view){
//        ViewGroup.LayoutParams params=view.getLayoutParams();
//
//        if(params==null){
//            params=new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        }
//
//        int widthSpec=ViewGroup.getChildMeasureSpec(0,0,params.width);
//
//        int heightSpec;
//        if(params.height>0){
//            heightSpec=MeasureSpec.makeMeasureSpec(params.height,MeasureSpec.EXACTLY);
//        }else{
//            heightSpec=MeasureSpec.makeMeasureSpec(params.height,MeasureSpec.UNSPECIFIED);
//        }
//
//        view.measure(widthSpec,heightSpec);
//    }

    /**
     * 画尺子
     *
     * @param canvas
     */
    protected void drawProgressBar(Canvas canvas) {


        //画背景
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(gray);
        Rect rect = new Rect(mLeftLimit, mProBaseline, mRightLimit, mProBaseline + mProgressBarHeight);
        canvas.drawRect(rect, paint);

        //画进度
        paint.setColor(getResources().getColor(R.color.ticket_red));
        rect = new Rect(mThumbLeft.getCenterX(), mProBaseline, mThumbRight.getCenterX(), mProBaseline + mProgressBarHeight);
        canvas.drawRect(rect, paint);


    }

    /**
     * 画刻度尺
     *
     * @param canvas
     */
    protected void drawRule(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(gray);
        paint.setTextSize(CommonUtil.dip2px(getContext(), 12));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);

        //一次遍历两份,绘制的位置都是在奇数位置
        for (int i = 0; i <= mMaxValue; i += 20) {

            float degX = mLeftLimit + i * mPartWidth;
            int centerXLeft = mThumbLeft.getCenterX();
            int centerXRight = mThumbRight.getCenterX();
            if (degX >= centerXLeft && degX <= centerXRight) {
                paint.setColor(getResources().getColor(R.color.ticket_red));
            } else {
                paint.setColor(gray);
            }
            int degY;
            degY = mProBaseline - CommonUtil.dip2px(getContext(), LONGLINE_HEIGHT);
            canvas.drawText(unitStr + degs[(i / 20)], degX, degY - CommonUtil.dip2px(getContext(), 10), paint);
            canvas.drawPoint(degX, degY, paint);
        }
    }

    /**
     * 画 Thumb 位置的数值
     */
//    protected void drawRodPlaceValue(Canvas canvas, ThumbView thumbView) {
//        int centerX = thumbView.getCenterX();
//        Paint paint = new Paint();
//        BitmapDrawable bd = (BitmapDrawable) mThumbPlaceDrawable;
//        canvas.drawBitmap(bd.getBitmap(), centerX - mThumbPlaceDrawable.getIntrinsicWidth() / 2, 0, paint);
//
//        paint.setColor(Color.WHITE);
//        paint.setTextAlign(Paint.Align.CENTER);
//        paint.setTextSize(30);
//        canvas.drawText(geneareThumbValue(thumbView) + "", centerX, mThumbDrawable.getIntrinsicHeight() / 2, paint);
//    }

    //onLayout调用后执行的函数
    private void onLayoutPrepared() {
        mThumbLeft.setCenterX(leftX);
        if (rightX == 0) rightX = get300position();
        mThumbRight.setCenterX(rightX);
    }

    private int geneareThumbValue(ThumbView view) {
        // 这里只是计算了100之多少的值，需要自行转换成刻度上的值
        int proValue = mMaxValue * (view.getCenterX() - mLeftLimit) / (mRightLimit - mLeftLimit);

        if (proValue < 20) {
            proValue = (int) (150 * proValue / 20.0);
        } else if (proValue < 40) {
            proValue = (int) (150 + (proValue - 20) * 150 / 20.0);
        } else if (proValue < 60) {
            proValue = (int) (300 + (proValue - 40) * 200 / 20.0);
        } else if (proValue < 80) {
            proValue = (int) (500 + (proValue - 60) * 300 / 20.0);
        } else if (proValue < 100) {
            proValue = (int) (800 + (proValue - 80) * 450 / 20.0);
        } else if (proValue == 100) {
            proValue = Integer.MAX_VALUE;
        }
        return proValue;
    }

    public int getMinPrice() {
        return geneareThumbValue(mThumbLeft);
    }

    public int getMaxPrice() {
        return geneareThumbValue(mThumbRight);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureChildren(widthMeasureSpec, heightMeasureSpec);    //测量子控件
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        proPaddingLeftAndRight = mThumbLeft.getMeasuredWidth() * 2;
        mLeftLimit = proPaddingLeftAndRight;
        mRightLimit = mWidth - proPaddingLeftAndRight;

        //位置标记的高度+尺子的刻度高度+尺子的高度+游标的高度
        setMeasuredDimension(mWidth, mThumbPlaceHeight + RULE_HEIGHT_PX + mProgressBarHeight + mThumbLeft.getMeasuredHeight());
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgressBar(canvas);
        drawRule(canvas);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int heightSum = 0;

        heightSum += mThumbPlaceHeight;
//
        heightSum += RULE_HEIGHT_PX;
//
        heightSum += mProgressBarHeight / 2;
//
        heightSum -= mThumbLeft.getMeasuredHeight() / 2;
        mPartWidth = (mRightLimit - mLeftLimit) / (float) mMaxValue;   //计算一份所占的宽度  一定要用float

        mThumbLeft.setLimit(mLeftLimit, mRightLimit);    //设置可以移动的范围
        mThumbLeft.layout(0, heightSum, mThumbLeft.getMeasuredWidth(), heightSum + mThumbLeft.getMeasuredHeight());      //设置在父布局的位置
        mThumbRight.setLimit(mLeftLimit, mRightLimit);
        mThumbRight.layout(0, heightSum, mThumbLeft.getMeasuredWidth(), heightSum + mThumbLeft.getMeasuredHeight());

        onLayoutPrepared();     //layout调用后调用的方法，比如设置thumb limit
    }
}
