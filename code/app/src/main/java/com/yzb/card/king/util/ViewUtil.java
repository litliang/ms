package com.yzb.card.king.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;

/**
 * Created by gengqiyun on 2016/5/21.
 */
public class ViewUtil
{
    /**
     * 获取Listview的高度，
     *
     * @param listView
     * @return
     */
    public static int setListViewHeightBasedOnChildren(ListView listView)
    {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
        {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++)
        { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        return params.height;
    }

    /**
     * RecyclerView属性设置；
     *
     * @param context
     * @param recyclerView
     */
    public static void recyclerViewSetting1(Context context, HeadFootRecyclerView recyclerView)
    {
        //不需要分割线；
        recyclerView.setNeedDivider(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setIsEnale(true);
    }

    /**
     * 获取listView的高度
     *
     * @author ysg
     * created at 2016/7/15 19:43
     */
    public static int getListViewHeight(ListView list, int itemNum)
    {
        ListAdapter listAdapter = list.getAdapter();
        itemNum = listAdapter.getCount() < itemNum ? listAdapter.getCount() : itemNum;
        if (listAdapter == null)
        {
            return 0;
        }
        int totalHeight = 0;
        int listViewWidth = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();//listView在布局时的宽度
        int widthSpec = View.MeasureSpec.makeMeasureSpec(listViewWidth, View.MeasureSpec.AT_MOST);
        for (int i = 0; i < itemNum; i++)
        {
            View listItem = listAdapter.getView(i, null, list);
            if (listItem != null)
            {
                listItem.measure(widthSpec, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        totalHeight = totalHeight + (list.getDividerHeight() * (listAdapter.getCount() - 1)) - 1;
        return totalHeight;
    }

    public static void setListViewHeight(ListView listView, int height)
    {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = height;
        listView.setLayoutParams(params);
    }

    /**
     * 显示密码；
     */
    public static void showPwdText(EditText et)
    {
        et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        et.setSelection(et.getText().toString().length());
    }

    public static void setListViewHeight(ListView listView)
    {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = getListViewHeight(listView,listView.getAdapter().getCount());
        listView.setLayoutParams(params);
    }

    public static void set( View view, int width, int height) {
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(width, height);
            } else {
                layoutParams.width = width;
                layoutParams.height = height;
            }
            view.setLayoutParams(layoutParams);
        }
    }
}
