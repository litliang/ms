package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.ShippingSpace;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * Created by dev on 2016/3/24.
 */
public class PlaneCwAdapter extends BaseAdapter {

    private List<ShippingSpace> list;
    private int listviewItem;
    private LayoutInflater layoutInflater = null;
    private int checkCw = 0;

    public PlaneCwAdapter(Context context, List<ShippingSpace> list, int listviewItem, int checkCw)
    {
        this.list = list;
        this.listviewItem = listviewItem;
        this.layoutInflater = LayoutInflater.from(context);
        this.checkCw = checkCw;
    }

    public void setCheckCw(int checkCw)
    {
        this.checkCw = checkCw;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    public ShippingSpace getShip(){
        return list.get(0);
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = layoutInflater.inflate(listviewItem, null);

            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.isCheck = (ImageView) convertView.findViewById(R.id.isCheck);

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(list.get(position).getQabinName());
        if (checkCw == position)
            viewHolder.isCheck.setVisibility(View.VISIBLE);
        else
            viewHolder.isCheck.setVisibility(View.GONE);

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        ImageView isCheck;
    }
}
