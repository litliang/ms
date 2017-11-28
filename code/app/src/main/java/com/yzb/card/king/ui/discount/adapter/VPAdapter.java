package com.yzb.card.king.ui.discount.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by gqy on 2016/4/14.
 */
public class VPAdapter extends FragmentPagerAdapter {
    private final FragmentManager mFragmentManager;
    private String[] titles = null;
    private List<Fragment> mListFragments = null;
    public boolean[] fragmentsUpdateFlag = {false, false, false, false, false, false, false}; // 是否需要更新；

    public VPAdapter(FragmentManager fm, List<Fragment> mListFragments) {
        super(fm);
        mFragmentManager = fm;
        this.mListFragments = mListFragments;
    }

    /**
     * FragmentPagerAdapter里在根据getItemId(int position)来判断当前position里Fragment是否存在，
     * 如果存在，则不会创建亦不会更新，那么要让FragmentPagerAdapter的更新生效，那在getItemId(int)里
     * 根据数据返回一个唯一的数据ID，当FragmentPagerAdapter更新时，数据ID改变了，那么Fragment就会调用
     * getItem(int)去获取新Fragment，达到更新效果
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return mListFragments == null ? 0 : mListFragments.get(position).hashCode();
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public void setFragments(List<Fragment> fragments) {
        this.mListFragments = fragments;
    }

    @Override
    public int getCount() {
        return null != mListFragments ? mListFragments.size() : 0;
    }

    @Override
    public Fragment getItem(int index) {
        if (mListFragments != null && index > -1 && mListFragments.size() > index) {
            return mListFragments.get(index);
        } else {
            return null;
        }
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        //得到缓存的fragment
//        Fragment fragment = (Fragment) super.instantiateItem(container, position);
//        //得到tag，这点很重要
//        String fragmentTag = fragment.getTag();
//
//        //需要更新；
//        if (fragmentsUpdateFlag[position]) {
//            FragmentTransaction ft = mFragmentManager.beginTransaction();
//            //移除旧的fragment
//            ft.remove(fragment);
//            //换成新的fragment
//            fragment = mListFragments.get(position);
//            //添加新fragment时必须用前面获得的tag，这点很重要
//            if (fragment.isAdded() == false) {
//                ft.add(container.getId(), fragment, fragmentTag);
//                ft.attach(fragment);
//                ft.commit();
//            }
//            //复位更新标志
//            fragmentsUpdateFlag[position] = false;
//        }
//
//        return fragment;
//    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles != null ? titles[position] : "";
    }
}
