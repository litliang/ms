package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.bonus.activity.BounsHomeActivity;
import com.yzb.card.king.ui.manage.UserManager;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/13 12:10
 * 描述：
 */
public class RedBagPopup extends FixedPopupWindow
{
    private Context context;
    private View mRoot;
    private View parentView;
    private Activity activity;
    private int currentX;
    private int currentY;
    private int startX;
    private int startY;
    private int mScreenX = 0;
    private int mScreenY = 0;
    private int dx;
    private int dy;

    public RedBagPopup(Context context, View parentView, Activity activity)
    {
        super(context);
        this.context = context;
        this.parentView = parentView;
        this.activity = activity;
        initView();
        initData();
        init();
    }

    private void initView()
    {

        mRoot = View.inflate(this.context, R.layout.popwindow_red_bag, null);
        mRoot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (UserManager.getInstance().isLogin())
                {
                    Intent intent = new Intent();
                    intent.setClass(v.getContext(), BounsHomeActivity.class);//  BounsCreateActivity
                    v.getContext().startActivity(intent);
                } else
                {
                    new GoLoginDailog(context).show();
                }
            }
        });
        mRoot.setOnTouchListener(new View.OnTouchListener()
        {
            boolean flag = false;

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        dx = (int) event.getRawX() - startX + mScreenX;
                        dy = startY - (int) event.getRawY() + mScreenY;
                        update(-dx, -dy, -1, -1);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(mScreenX - dx) < 8 && Math.abs(mScreenY - dy) < 8)
                        {
                            flag = false;
                        } else
                        {
                            flag = true;
                        }
                        mScreenX = dx;
                        mScreenY = dy;
                        break;

                }
                return flag;
            }
        });

    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y)
    {
        super.showAtLocation(parent, gravity, x, y);
        mScreenX = 0;
        mScreenY = 0;
    }

    private void initData()
    {
        currentX = 0;
        currentY = 0;
    }

    private void init()
    {
        this.setContentView(mRoot);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setTouchable(true);
        //
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
