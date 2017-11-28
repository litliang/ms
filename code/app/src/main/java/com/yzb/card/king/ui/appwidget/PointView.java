package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.SumPoint;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DensityUtil;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/3/14 12:21
 */
public class PointView extends DiscountView
{
    private int icon1;
    private int icon2;
    private ImageView img1;
    private ImageView img2;

    public int getIcon1()
    {
        return icon1;
    }

    public void setIcon1(int icon1)
    {
        this.icon1 = icon1;
    }

    public int getIcon2()
    {
        return icon2;
    }

    public void setIcon2(int icon2)
    {
        this.icon2 = icon2;
    }

    public PointView(Context context)
    {
        super(context);
    }

    public PointView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getAttrs(Context context, AttributeSet attrs)
    {
        super.getAttrs(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PointView);
        icon1 = typedArray.getResourceId(R.styleable.PointView_ico1, R.drawable.platform_selector);
        icon2 = typedArray.getResourceId(R.styleable.PointView_ico2, R.drawable.shop_selector);
    }

    @Override
    protected void initView()
    {
        super.initView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = CommonUtil.dip2px(getContext(), 3);
        img1 = new ImageView(getContext());
        img1.setLayoutParams(params);
        img2 = new ImageView(getContext());
        img2.setLayoutParams(params);
//        addView(img2, 2);
//        addView(img1, 2);
    }

    @Override
    protected void setData()
    {
        super.setData();
        img1.setBackgroundResource(icon1);
        img2.setBackgroundResource(icon2);
    }

    public void setEnabled(List<SumPoint> list)
    {
        super.setEnabled(list != null && list.size() > 0);
        setStatus(list);
    }

    private void setStatus(List<SumPoint> list)
    {
        boolean platform = false;
        boolean shop = false;
        if (list != null && list.size() > 0)
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (1 == list.get(i).getPlatformType())addView(img1);
                else if (2 == list.get(i).getPlatformType())addView(img2);
            }
        }
        img1.setEnabled(platform);
        img2.setEnabled(shop);
    }
}
