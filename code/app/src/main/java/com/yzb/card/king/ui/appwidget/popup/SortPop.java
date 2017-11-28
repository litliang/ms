package com.yzb.card.king.ui.appwidget.popup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.luxury.activity.IMenuDataCallBack;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.ui.discount.fragment.PopZnpxInnerFragment;
import com.yzb.card.king.util.CommonUtil;

import java.util.List;

/**
 * 功能：
 * 智能排序的pop；
 *
 * @author:gengqiyun
 * @date: 2016/7/15
 */
public class SortPop {
    private static SortPop sortPop;
    private static PopupWindow window;
    private FragmentActivity context;
    private ISortPop callBack;
    private static List<CategoryBean> categoryBeans; // 分类列表；
    private String curSort; // 当前的排序状态；

    public static void setSortPop(SortPop sortPop) {
        SortPop.sortPop = sortPop;
    }

    public static void reset() {
        setSortPop(null);
        clear();
    }

    public SortPop setSortPopCallBack(ISortPop callBack) {
        this.callBack = callBack;
        return this;
    }

    public static void clear() {
        categoryBeans = null;
    }

    public interface ISortPop {
        void popDismiss();

        void onItemClick(String[] args);
    }

    public static boolean isPopShowing() {
        if (getSortPopPop() != null && getSortPopPop().isShowing()) {
            return true;
        }
        return false;
    }

    public static SortPop getInstance(FragmentActivity context) {

        synchronized (SortPop.class) {
            if (sortPop == null) {
                sortPop = new SortPop(context);
            }
        }
        return sortPop;
    }

    public static PopupWindow getSortPopPop() {
        return window;
    }

    public SortPop() {

    }

    public void showSortPopWindow(FragmentActivity context, View anchor) {
        if (anchor == null) {
            return;
        }
        this.context = context;
        if (window == null) {
            window = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            initPopWindow(window, R.layout.popwindow_content_znpx);
        }
        initZnpxViewData();
        window.showAsDropDown(anchor, 0, CommonUtil.dip2px(context, 0));

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (callBack != null) {
                    callBack.popDismiss();
                }
            }
        });
    }

    /**
     * 智能排序data初始化；
     */
    private void initZnpxViewData() {
        Fragment fragment = context.getSupportFragmentManager().findFragmentById(R.id.popZnpxInnerFragment);
        if (fragment instanceof PopZnpxInnerFragment) {
            PopZnpxInnerFragment popInnerFragment = (PopZnpxInnerFragment) fragment;

            popInnerFragment.setMenuDataCallBack(new IMenuDataCallBack() {
                @Override
                public void menuDataCallBack(Object... objects) {
                    String[] args = new String[2];

                    if (objects.length == 2) {
                        //分类的id；
                        args[0] = String.valueOf(objects[1]);
                        curSort = args[0];
                        args[1] = "智能排序";
                    } else if (objects.length == 4) {
                        args[0] = String.valueOf(objects[1]);
                        curSort = args[0];
                        args[1] = String.valueOf(objects[2]);
                    }
                    if (callBack != null) {
                        callBack.onItemClick(args);
                    }
                }
            });
            popInnerFragment.setCurSort(curSort);
            // 此处数据固定；
            popInnerFragment.setDataList();
        }
    }

    private void initPopWindow(PopupWindow window, int contentViewId) {
        if (window != null) {
            window.setOutsideTouchable(true);
            window.setAnimationStyle(R.style.popupwindow_animation_style);
        }

        if (window != null && contentViewId != 0) {
            window.setContentView(LayoutInflater.from(context).inflate(contentViewId, null));
        }
    }

    public SortPop(FragmentActivity context) {
        this.context = context;
    }

    public void dismissPopWindow() {
        if (window != null && window.isShowing() && context != null) {
            window.dismiss();
        }
    }
}
