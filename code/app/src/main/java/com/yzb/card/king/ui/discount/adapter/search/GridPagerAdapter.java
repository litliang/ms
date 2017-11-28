package com.yzb.card.king.ui.discount.adapter.search;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名：GridPagerAdapter
 * 作者：殷曙光
 * 日期：2016/8/5 14:40
 * 描述：含GridView的viewpager适配器，可以可控制gridView显示的列数和总数
 */
public class GridPagerAdapter<T> extends PagerAdapter {

    private int numColumns = 3;//gridView显示的列数
    private int pageSize = 9;//gridView显示的总数目
    private List<T> list;
    private OnGetHolderListener<T> listener;

    public GridPagerAdapter(List<T> list, OnGetHolderListener<T> listener) {
        this.list = list;
        this.listener = listener;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = UiUtils.inflate(R.layout.item_grid_pager);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setNumColumns(numColumns);
        AbsBaseListAdapter adapter = getAdapter(position);
        gridView.setAdapter(adapter);
        container.addView(view);
        return view;
    }

    /**
     * 填充每页的数据
     *
     * @author ysg
     * created at 2016/8/5 9:38
     */
    private AbsBaseListAdapter getAdapter(int position) {
        List<T> pageList = new ArrayList<>();
        for (int i = position * pageSize; i < (position + 1) * pageSize; i++) {
            if (i < list.size()) {
                pageList.add(list.get(i));
            }
        }
        GridAdapterAbs gridAdapter = new GridAdapterAbs(pageList);
        return gridAdapter;
    }

    @Override
    public int getCount() {
        if (list == null)
            return 0;
        int count = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
        return count;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    class GridAdapterAbs extends AbsBaseListAdapter<T> {

        public GridAdapterAbs(List<T> list) {
            super(list);
        }

        @Override
        protected Holder getHolder(int position) {
            return listener == null ? null : listener.onGetHolder();
        }
    }

    public interface OnGetHolderListener<T> {
        /**
         * 通过此Holder控制GridView每个item显示的内容
         *
         * @author ysg
         * created at 2016/8/5 14:41
         */
        Holder<T> onGetHolder();
    }
}