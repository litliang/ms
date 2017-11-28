package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.FilterBean;
import com.yzb.card.king.bean.travel.FilterTwoBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

import java.util.List;


/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2016/11/20
 */
public class FilterLeftAdapter extends BaseListAdapter<FilterTwoBean>
{
    private FilterTwoBean selectedEntity;

    public FilterLeftAdapter(Context context)
    {
        super(context);
    }

    public void setSelectedEntity(FilterTwoBean filterEntity)
    {
        this.selectedEntity = filterEntity;
        if (getData() != null)
        {
            for (FilterTwoBean entity : getData())
            {
                entity.setSelected(entity.getRegionId() == selectedEntity.getRegionId());
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_filter_left, parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        FilterTwoBean entity = getItem(position);

        holder.tvTitle.setText(entity.getRegionName());
        holder.tvTitle.setSelected(entity.isSelected());
        return convertView;
    }

    /**
     * 通过左侧的id找到右侧的数据；
     *
     * @param selectCategory
     */
    public List<FilterBean> getRightDataByLeftId(FilterTwoBean selectCategory)
    {
        if (getData() != null && selectCategory != null)
        {
            long regionId = selectCategory.getRegionId();

            for (FilterTwoBean entity : getData())
            {
                if (entity.getRegionId() == regionId)
                {
                    return entity.getChildList();
                }
            }
        }
        return null;
    }

    static class ViewHolder
    {
        TextView tvTitle;
        View root;

        ViewHolder(View view)
        {
            root = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }
}
