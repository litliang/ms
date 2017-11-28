package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/3/13 15:01
 */
public class DiscountView extends LinearLayout
{
    private int icon;
    private String centerText;
    private String tailText;
    private ImageView ivIcon;
    private TextView tvCenterText;
    private TextView tvTailText;

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public String getCenterText()
    {
        return centerText;
    }

    public void setCenterText(String centerText)
    {
        this.centerText = centerText;
    }

    public String getTailText()
    {
        return tailText;
    }

    public void setTailText(String tailText)
    {
        this.tailText = tailText;
    }

    public DiscountView(Context context)
    {
        super(context);
        init();
    }

    public DiscountView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        getAttrs(context, attrs);
        init();
    }

    protected void getAttrs(Context context, AttributeSet attrs)
    {
        TypedArray typeArr = context.obtainStyledAttributes(attrs, R.styleable.DiscountView);
        icon = typeArr.getResourceId(R.styleable.DiscountView_ico, 0);
        centerText = typeArr.getString(R.styleable.DiscountView_centerText);
        tailText = typeArr.getString(R.styleable.DiscountView_tailText);
        typeArr.recycle();
    }

    public DiscountView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        initView();
        setData();
    }

    protected void initView()
    {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        View root = View.inflate(getContext(), R.layout.bank_discount_item,this);
        ivIcon = (ImageView) root.findViewById(R.id.ivIcon);
        tvCenterText = (TextView) root.findViewById(R.id.tvCenterText);
        tvTailText = (TextView) root.findViewById(R.id.tvTailText);
    }

    protected void setData()
    {
        ivIcon.setBackgroundResource(icon);
        tvCenterText.setText(centerText);
        tvTailText.setText(tailText);
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        ivIcon.setEnabled(enabled);
        tvCenterText.setEnabled(enabled);
        tvTailText.setText(enabled ? tailText : "暂无优惠");
        tvTailText.setEnabled(enabled);
    }
}
