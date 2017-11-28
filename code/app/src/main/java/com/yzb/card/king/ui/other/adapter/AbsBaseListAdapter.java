package com.yzb.card.king.ui.other.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/15 15:57
 * 描述：
 */
public abstract class AbsBaseListAdapter<T> extends BaseAdapter {
    private List<T> list;

    public AbsBaseListAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {

        if (list != null){
            int size = list.size();
            return size;
        }

        else{
            return 0;
        }

    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder<T> holder;

        if (convertView == null) {

            holder = getHolder(position);

        } else {

            holder = (Holder<T>) convertView.getTag();

        }

        T string = list.get(position);

        holder.setPosition(position);

        holder.setData(string);

        return holder.getConvertView();
    }

    protected abstract Holder getHolder(int position);

    public List<T> getList()
    {
        return list;
    }

}
