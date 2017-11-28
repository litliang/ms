package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.FilterTwoBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

/**
 * 功能：优惠券搜索 筛选底部左侧；
 *
 * @author:gengqiyun
 * @date: 2016/11/20
 */
public class CouponFilterLeftAdapter extends BaseListAdapter<FilterTwoBean>
{
    private FilterTwoBean selectedEntity;

    public CouponFilterLeftAdapter(Context context)
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
            convertView = mInflater.inflate(R.layout.item_coupon_filter_left, parent, false);
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
