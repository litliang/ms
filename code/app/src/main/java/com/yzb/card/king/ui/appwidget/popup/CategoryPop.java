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
import com.yzb.card.king.ui.discount.fragment.PopFlInnerFragment;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * 功能：
 * 分类的pop；
 *
 * @author:gengqiyun
 * @date: 2016/7/15
 */
public class CategoryPop {
    private static CategoryPop categoryPop;
    private static PopupWindow window;
    public FragmentActivity context;
    private ICategoryPop callBack;
    private static List<CategoryBean> categoryBeans; // 分类列表；
    private String typeParentId;
    private String typeId;
    private String typeGrandParentId;

    public CategoryPop setTypeId(String typeId) {
        this.typeId = typeId;
        return this;
    }

    public CategoryPop setTypeParentId(String typeParentId) {
        this.typeParentId = typeParentId;
        return this;
    }

    public CategoryPop setTypeGrandParentId(String typeGrandParentId) {
        this.typeGrandParentId = typeGrandParentId;
        return this;
    }

    public void dismissPopWindow() {
        if (window != null && window.isShowing() && context != null) {
            LogUtil.i("CategoryPop window=" + window);
            window.dismiss();
            typeParentId = null;
            typeId = null;
        }
    }

    public static void reset() {
        setCategoryPop(null);
        clear();
    }

    public static void setCategoryPop(CategoryPop categoryPop) {
        CategoryPop.categoryPop = categoryPop;
    }

    public static void clear() {
        categoryBeans = null;
    }

    public CategoryPop setCategoryPopCallBack(ICategoryPop callBack) {
        this.callBack = callBack;
        return this;
    }


    public CategoryPop setCategoryBeans(List<CategoryBean> categoryBeans) {
        CategoryPop.categoryBeans = categoryBeans;
        return this;
    }

    public interface ICategoryPop {
        void popDismiss();

        void onItemClick(String[] args);
    }

    public static boolean isPopShowing() {
        if (getCategoryPopPop() != null && getCategoryPopPop().isShowing()) {
            return true;
        }
        return false;
    }

    public static CategoryPop getInstance(FragmentActivity context) {

        synchronized (CategoryPop.class) {
            if (categoryPop == null) {
                categoryPop = new CategoryPop(context);
            }
        }
        return categoryPop;
    }

    public static PopupWindow getCategoryPopPop() {
        return window;
    }

    public CategoryPop() {

    }

    /**
     * 分类popupwindow；
     */
    public void showCategoryPopWindow(FragmentActivity context, View anchor) {
        if (anchor == null || context == null) {
            return;
        }
        this.context = context;
        LogUtil.i("当前分类的typeParentId=" + typeParentId + ",typeId=" + typeId);

        if (window == null) {
            window = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            initPopWindow(window, R.layout.popwindow_content_fl);
        }
        initFlViewData();
        window.showAsDropDown(anchor, 0, CommonUtil.dip2px(context, 0f));

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (callBack != null) {
                    callBack.popDismiss();
                }
            }
        });
    }

    private static Fragment fragment;

    private void initFlViewData() {
        if (fragment == null) {
            fragment = context.getSupportFragmentManager().findFragmentById(R.id.popFlInnerFragment);
        }

        if (fragment instanceof PopFlInnerFragment) {
            PopFlInnerFragment popInnerFragment = (PopFlInnerFragment) fragment;
            popInnerFragment.setMenuDataCallBack(new IMenuDataCallBack() {
                @Override
                public void menuDataCallBack(Object... objects) {
                    if (objects != null && callBack != null) {
                        if (objects[1] instanceof String[]) {
                            callBack.onItemClick((String[]) objects[1]);
                        }
                    }
                }
            });
            //设置当前选中的大/小分类的id，一边标红；
            popInnerFragment.setGrandParentId(typeGrandParentId);
            popInnerFragment.setParentId(typeParentId);
            popInnerFragment.setCurentFlId(typeId);
            popInnerFragment.setDataList(categoryBeans);
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

    public CategoryPop(FragmentActivity context) {
        this.context = context;
    }
}
