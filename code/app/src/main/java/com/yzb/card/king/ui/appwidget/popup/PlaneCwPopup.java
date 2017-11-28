package com.yzb.card.king.ui.appwidget.popup;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.ShippingSpace;
import com.yzb.card.king.ui.discount.adapter.PlaneCwAdapter;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/23
 * 描  述：
 */
public class PlaneCwPopup extends PopupWindow {
    private PopupWindow popupWindow;
    private Fragment activity;
    private int defaultPos = 0;
    private OnItemClickListener listener;
    private List<ShippingSpace> list;


    public PlaneCwPopup(Fragment activity, OnItemClickListener listener, List<ShippingSpace> list)
    {
        this.list = list;
        this.activity = activity;
        this.listener = listener;
    }



    public void openPop(View view)
    {
        if (popupWindow == null)
        {
            popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            initPopWindow(popupWindow, R.layout.popwindow_content_plane_cw);
        }
        initPopData(view);
        popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
    }


    private void initPopData(View view)
    {
        View cityContentView = popupWindow.getContentView();
        if (cityContentView != null)
        {
            ListView listView = (ListView) cityContentView.findViewById(R.id.listview);
            final TextView textView = (TextView) view;
            //listView.setSelector(getResources().getDrawable(R.drawable.arrow_down_menu_bg_selector))
            final PlaneCwAdapter adapter = new PlaneCwAdapter(activity.getActivity(), list,
                    R.layout.popwindow_content_plane_cw_item, defaultPos);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    defaultPos = position;
                    ShippingSpace space = list.get(position);
                    textView.setText(space.getQabinName());
                    listener.onItemClick(space);
                    adapter.setCheckCw(position);
                    popupWindow.dismiss();
                }
            });

            View filterBlack = cityContentView.findViewById(R.id.filterBlack);
            filterBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    popupWindow.dismiss();
                }
            });
        }

    }

    private void initPopWindow(PopupWindow window, int contentViewId)
    {
        if (window != null)
        {
            window.setOutsideTouchable(true);
            window.setAnimationStyle(R.style.popupwindow_animation_style);
            window.setFocusable(true);
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        if (window != null && contentViewId != 0)
        {
            window.setContentView(activity.getActivity().getLayoutInflater().inflate(contentViewId, null));
        }
    }

    public ShippingSpace getDefault()
    {
        return list.get(defaultPos);
    }

    public interface OnItemClickListener {
        void onItemClick(ShippingSpace shippingSpace);
    }
}
