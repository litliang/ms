package com.yzb.card.king.ui.discount.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by gengqiyun on 2016/4/27.
 */
public class VpBaseAdapter extends PagerAdapter {
    List<View> viewList = null;
    private String[] titles = {"我的银行", "所有银行"};

    public void setViewList(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return viewList == null ? 0 : viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
