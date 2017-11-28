package com.yzb.card.king.ui.hotel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * Created by dev on 2016/5/18.
 */
public class HotelPicAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    public HotelPicAdapter(FragmentManager manager, List<Fragment> list){
        super(manager);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

}
