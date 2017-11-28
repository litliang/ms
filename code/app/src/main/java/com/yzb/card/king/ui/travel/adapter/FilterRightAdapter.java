package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.FilterBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

import java.util.List;

/**
 * 功能：目的地搜索；
 *
 * @author:gengqiyun
 * @date: 2016/11/20
 */
public class FilterRightAdapter extends BaseListAdapter<FilterBean>
{
    private FilterBean selectedEntity;

    public FilterRightAdapter(Context context)
    {
        super(context);
    }

    /**
     * 设置选中的bean；
     *
     * @param filterEntity
     */
    public void setSelectedEntity(FilterBean filterEntity)
    {
        this.selectedEntity = filterEntity;
        for (FilterBean entity : getData())
        {
            entity.setIsSelected(entity.getObjId() == selectedEntity.getObjId());
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_filter_right, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        FilterBean entity = getItem(position);

        holder.tvTitle.setText(entity.getObjName());
        holder.tvTitle.setSelected(entity.isSelected());

        return holder.root;
    }

    static class ViewHolder
    {
        public View root;
        public TextView tvTitle;

        ViewHolder(View view)
        {
            this.root = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }
}
