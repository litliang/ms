package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.travel.FilterBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

/**
 * 功能：优惠券；
 *
 * @author:gengqiyun
 * @date: 2016/11/20
 */
public class CouponFiltRAdapter extends BaseListAdapter<SubItemBean> {


    public CouponFiltRAdapter(Context context)
    {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_coupon_filter_right, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SubItemBean entity = getItem(position);

        holder.tvTitle.setText(entity.getFilterName());

        holder.tvTitle.setSelected(entity.isDefault());


        return holder.root;
    }



    static class ViewHolder {
        public View root;
        public TextView tvTitle;

        ViewHolder(View view)
        {
            this.root = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }
}
