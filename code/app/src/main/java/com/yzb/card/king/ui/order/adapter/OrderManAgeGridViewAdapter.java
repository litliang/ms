package com.yzb.card.king.ui.order.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.util.LogUtil;

/**
 * Created by sushuiku on 2016/11/19.
 */

public class OrderManAgeGridViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private String text[];
    private int selectPostion;

    public OrderManAgeGridViewAdapter(Context context, String text[])
    {
        this.mContext = context;
        this.text = text;
        mInflater = LayoutInflater.from(mContext);

    }

    public void setSelected( int selectPostion){

        this.selectPostion = selectPostion;

    }

    public void setNewDataArray(String text[], int selectPostion)
    {

        this.text = text;

        this.selectPostion = selectPostion;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener mListener;

    public void setListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

    @Override
    public int getCount()
    {
        return text.length;
    }

    @Override
    public Object getItem(int position)
    {
        return text[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.order_manage_gridview_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textview11);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(text[position]);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mListener.onClick(position);

            }
        });
        // 设置选择背景
        if (selectPostion == position) {
            holder.textView.setPressed(true);
        } else {
            holder.textView.setPressed(false);
        }
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }

}
