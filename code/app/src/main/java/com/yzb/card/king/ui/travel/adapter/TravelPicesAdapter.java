package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yzb.card.king.R;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/21
 * 描  述：
 */
public class TravelPicesAdapter extends BaseAdapter {

    private Context context;

    private String[] priceName;

    private LayoutInflater inflater;

    private int pos;

    public TravelPicesAdapter(Context context, String[] priceName)
    {
        this.context = context;
        this.priceName = priceName;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return priceName.length;
    }

    @Override
    public Object getItem(int i)
    {
        return priceName[i];
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder vh = null;
        if (view == null)
        {
            vh = new ViewHolder();
            view = inflater.inflate(R.layout.travel_price_item, null);
            vh.txView = (TextView) view.findViewById(R.id.tx);

            view.setTag(vh);
        } else
        {
            vh = (ViewHolder) view.getTag();
        }
        vh.txView.setText(priceName[i]);
        if (pos == i)
        {
            vh.txView.setEnabled(true);
        } else
        {
            vh.txView.setEnabled(false);
        }
        return view;
    }

    public class ViewHolder {
        TextView txView;
    }

    public void setPos(int position)
    {
        this.pos = position;
        notifyDataSetChanged();
    }
}
