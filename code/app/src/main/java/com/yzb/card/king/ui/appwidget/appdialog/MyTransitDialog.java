package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baidu.mapapi.search.core.RouteLine;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.hotel.adapter.RouteLineAdapter;

import java.util.List;

/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2016/8/13
 */
public class MyTransitDialog extends Dialog
{
    private List<? extends RouteLine> mtransitRouteLines;
    private ListView transitRouteList;
    private RouteLineAdapter mTransitAdapter;
    private OnDialogItemClickListener onItemInDlgClickListener;

    public MyTransitDialog(Context context, int theme)
    {
        super(context, theme);
    }

    public MyTransitDialog(Context context, List<? extends RouteLine> transitRouteLines, RouteLineAdapter.Type type)
    {
        this(context, 0);
        mtransitRouteLines = transitRouteLines;
        mTransitAdapter = new RouteLineAdapter(context, mtransitRouteLines, type);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_transit);
        transitRouteList = (ListView) findViewById(R.id.transitList);
        transitRouteList.setDivider(null);
        transitRouteList.setSelector(new ColorDrawable(Color.TRANSPARENT));
        transitRouteList.setAdapter(mTransitAdapter);

        transitRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (onItemInDlgClickListener != null)
                {
                    onItemInDlgClickListener.onItemClick(position);
                }
                dismiss();

            }
        });
    }

    public void setOnDialogItemClickLinster(OnDialogItemClickListener itemListener)
    {
        onItemInDlgClickListener = itemListener;
    }

    /**
     * 响应Dialog中的List item 点击
     */
    public interface OnDialogItemClickListener
    {

        void onItemClick(int position);
    }
}
