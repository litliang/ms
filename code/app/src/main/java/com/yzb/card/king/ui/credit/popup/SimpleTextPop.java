package com.yzb.card.king.ui.credit.popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/1
 */

public class SimpleTextPop extends PopupWindow
{
    private View mRoot;
    private Activity activity;
    private TextView tvTitle;
    private TextView tvEnsure;
    private TextView tvContent;

    public SimpleTextPop(Activity activity)
    {
        this.activity = activity;
        initView();
        initData();
        init();
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.pop_simple_text);
        tvTitle = (TextView) mRoot.findViewById(R.id.tvTitle);
        tvContent = (TextView) mRoot.findViewById(R.id.tvContent);
        tvEnsure = (TextView) mRoot.findViewById(R.id.tvEnsure);
    }

    private void initData()
    {
        tvEnsure.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }

    public void setData(String title, String content)
    {
        tvTitle.setText(title);
        tvContent.setText(content);
    }

    private void init()
    {
        this.setContentView(mRoot);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        this.setBackgroundDrawable(dw);
        this.setFocusable(false);
        this.setTouchable(true);
        setOutsideTouchable(true);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y)
    {
        setBg(0.7f);
        super.showAtLocation(parent, gravity, x, y);

    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff)
    {
        setBg(0.7f);
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void dismiss()
    {
        super.dismiss();
        setBg(1f);
    }

    private void setBg(float bgAlpha)
    {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }
}
