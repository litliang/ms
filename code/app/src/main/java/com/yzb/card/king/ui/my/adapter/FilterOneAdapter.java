package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.gift.bean.GiftcardTypeBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @author gengqiyun
 * @date 2016.12.12
 */
public class FilterOneAdapter extends BaseListAdapter<GiftcardTypeBean>
{
    private GiftcardTypeBean selectedEntity;

    public FilterOneAdapter(Context context, List<GiftcardTypeBean> list)
    {
        super(context, list);
    }

    /**
     * 设置选中的；
     */
    public void setSelectedEntity(GiftcardTypeBean filterEntity)
    {
        this.selectedEntity = filterEntity;
        for (GiftcardTypeBean entity : getData())
        {
            entity.setIsSelected(!TextUtils.isEmpty(entity.getTypeId()) && entity.getTypeId().equals(selectedEntity.getTypeId()));
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_filter_one, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        GiftcardTypeBean entity = getItem(position);

        holder.tvTitle.setSelected(entity.isSelected());
        holder.iv_right.setVisibility(entity.isSelected() ? View.VISIBLE : View.GONE);

        holder.tvTitle.setText(entity.getTypeName());
        return convertView;
    }

    static class ViewHolder
    {
        @ViewInject(R.id.tv_title)
        TextView tvTitle;
        @ViewInject(R.id.iv_right)
        ImageView iv_right;
        View root;

        ViewHolder(View view)
        {
            root = view;
            x.view().inject(this, view);
        }
    }
}
