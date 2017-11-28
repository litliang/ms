package com.yzb.card.king.ui.app.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.yzb.card.king.R;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类名：酒店订单详情弹窗
 *
 * @author ：gengqiyun
 * @date 2016.10.31
 */
public class HotelOrderDetailPopup extends PopupWindow
{
    private View rootView;
    private Context context;
    private ListView listView;
    private SimpleAdapter adapter;

    public HotelOrderDetailPopup(Context context)
    {
        this.context = context;
        initView();
    }

    public void setData(List<Map<String, String>> orderListData)
    {
        if (orderListData == null)
        {
            orderListData = new ArrayList<>();
        }
        adapter = new SimpleAdapter(context, orderListData, R.layout.popwindow_content_plane_order_item,
                new String[]{"title", "details", "price"},
                new int[]{R.id.title, R.id.details, R.id.price});
        listView.setAdapter(adapter);
    }

    private void initView()
    {
        rootView = UiUtils.inflate(R.layout.popwindow_content_plane_order);
        setContentView(rootView);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        setOutsideTouchable(true);
        setAnimationStyle(R.style.popupwindow_animation_style);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        listView = (ListView) rootView.findViewById(R.id.listview);
        //订单详情window中的灰色区域；
        View filterBlack = rootView.findViewById(R.id.filterBlack);
        filterBlack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }
}
