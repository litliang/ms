package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.travel.FilterBean;
import com.yzb.card.king.bean.travel.FilterTwoBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

import java.util.List;


/**
 * 功能：优惠券；
 *
 * @author:gengqiyun
 * @date: 2016/11/20
 */
public class CouponFiltLAdapter extends BaseListAdapter<CatalogueTypeBean> {
    private CatalogueTypeBean selectedEntity;

    public CouponFiltLAdapter(Context context)
    {
        super(context);
    }

    public void setSelectedEntity(CatalogueTypeBean filterEntity)
    {
        this.selectedEntity = filterEntity;

        if (getData() != null) {

            for (CatalogueTypeBean entity : getData()) {

                entity.setIfDefault(selectedEntity.getTypeCode().equals(entity.getTypeCode()));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_coupon_filter_left, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CatalogueTypeBean entity = getItem(position);

        holder.tvTitle.setText(entity.getTypeName());
        holder.tvTitle.setSelected(entity.isIfDefault());
        return convertView;
    }



    static class ViewHolder {
        TextView tvTitle;
        View root;

        ViewHolder(View view)
        {
            root = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }
}
