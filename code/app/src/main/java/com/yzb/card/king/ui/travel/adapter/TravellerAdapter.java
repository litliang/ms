package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

import java.util.List;

/**
 * 旅游活动报名-->旅客信息列表适配器
 */
public class TravellerAdapter extends BaseListAdapter<PassengerInfoBean>
{
    public TravellerAdapter(Context context, List<PassengerInfoBean> data)
    {
        super(context, data);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_list_traveller, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PassengerInfoBean pib = getItem(position);
        viewHolder.travellerName.setText(pib.name);
        //性别；
        viewHolder.travellerGender.setText(TextUtils.isEmpty(pib.sex) ? "" : ("1".equals(pib.sex) ? "男" : "女"));
        viewHolder.travellerType.setText(pib.getPersonType());
        viewHolder.divider.setVisibility(position != mList.size() - 1 ? View.VISIBLE : View.GONE);

        //删除；
        viewHolder.travellerCut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    static class ViewHolder
    {
        TextView travellerName;
        TextView travellerGender;
        View divider;
        TextView travellerType;
        TextView travellerCut;

        public ViewHolder(View convertView)
        {
            travellerName = (TextView) convertView.findViewById(R.id.travellerName);
            travellerGender = (TextView) convertView.findViewById(R.id.travellerGender);
            travellerType = (TextView) convertView.findViewById(R.id.travellerType);
            travellerCut = (TextView) convertView.findViewById(R.id.travellerCut);
            divider = convertView.findViewById(R.id.divider);
        }
    }
}
