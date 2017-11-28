package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/30
 * 描  述：
 */
public class CreditPayMentPop extends PopupWindow {


    private Activity activity;
    public PopupWindow popupWindow;
    private LinearLayout panel_back;


    public CreditPayMentPop(Activity activity)
    {
        this.activity = activity;
    }


    public void openPop()
    {
        if (popupWindow == null)
        {
            popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            initPopWindow(popupWindow, R.layout.credit_payment_pop);
        }
        initPopData();
        popupWindow.showAtLocation(popupWindow.getContentView(), Gravity.TOP, 0, 0);
    }

    private void initPopData()
    {
        View parentView = popupWindow.getContentView();
        panel_back = (LinearLayout) parentView.findViewById(R.id.panel_back);
        panel_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                popupWindow.dismiss();
            }
        });
    }



    private void initPopWindow(PopupWindow window, int contentViewId)
    {
        if (window != null)
        {
            window.setOutsideTouchable(true);
            window.setAnimationStyle(R.style.discount_popupwindow_animation);
            window.setFocusable(true);
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        if (window != null && contentViewId != 0)
        {
            window.setContentView(activity.getLayoutInflater().inflate(contentViewId, null));
        }
    }

}
