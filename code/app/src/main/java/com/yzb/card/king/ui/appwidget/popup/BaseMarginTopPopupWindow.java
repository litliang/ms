package com.yzb.card.king.ui.appwidget.popup;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;

import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

/**
 * 基础公共居于某控件顶部的popupWindow类
 */
public class BaseMarginTopPopupWindow extends PopupWindow{


    private View ppView;

    private int statusBarHeight;

    public BaseMarginTopPopupWindow(View ppView){

        this.ppView = ppView;
        setContentView(ppView);
        initView();

    }

    public BaseMarginTopPopupWindow(View ppView, int w, int h){

        this.ppView = ppView;
        setContentView(ppView);
        setWidth(w);
        setHeight(h);
        initView();
    }

    /**
     * 初始化位置按钮参数
     * @param viewInfo
     */
    public void initOnClickViewInfor(final View viewInfo){

        ViewTreeObserver vto2 = viewInfo.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewInfo.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                LogUtil.e("BBBB",viewInfo.getHeight()+"---23----3---"+ CommonUtil.dip2px(GlobalApp.getInstance(),160));

                ViewGroup.LayoutParams time14Lp =  viewInfo.getLayoutParams();
//
//                popupWindow = new PopupWindow(menuView, LinearLayout.LayoutParams.MATCH_PARENT,
//                        screenHeight- time14.getHeight()-time14Lp.bottomMargin-statusBarHeight, true);

            }
        });


    }

    /**
     * 初始化视图
     */
    private void initView(){

        AppBaseDataBean bean =GlobalApp.getInstance().getAppBaseDataBean();

         statusBarHeight = bean.getStatusBarHeight();
        // 产生背景变暗效果
        setTouchable(true);
        setOutsideTouchable(true);
        this.setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());

        ppView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }

    /**
     *  触发按钮居于屏幕底部，pp展示在此按钮的上面
     * @param view
     */
    public void showPositionTopByOnclickView(View view){

        showAtLocation(view, Gravity.TOP, 0,
                statusBarHeight);
    }

}
