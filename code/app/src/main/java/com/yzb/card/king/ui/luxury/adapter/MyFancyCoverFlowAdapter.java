package com.yzb.card.king.ui.luxury.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dalong.francyconverflow.FancyCoverFlowAdapter;
import com.yzb.card.king.R;
import com.yzb.card.king.util.CommonUtil;

import org.xutils.x;

/**
 * 水平滑动；
 */
public class MyFancyCoverFlowAdapter extends FancyCoverFlowAdapter
{
    private Context mContext;
    public String[] list;
    private LayoutInflater inflater;

    public MyFancyCoverFlowAdapter(Context context, String[] list)
    {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    @Override
    public View getCoverFlowItem(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_image, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        int imgWidth = CommonUtil.getScreenWidth(mContext) * 2 / 3;
        LinearLayout.LayoutParams vl = new LinearLayout.LayoutParams(imgWidth, imgWidth);
        holder.iv.setLayoutParams(vl);

        x.image().bind(holder.iv, getItem(position));
        return convertView;
    }

    @Override
    public int getCount()
    {
        return list == null ? 0 : list.length;
    }

    @Override
    public String getItem(int i)
    {
        return list == null ? null : list[i % list.length];
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    static class ViewHolder
    {
        public ImageView iv;

        public ViewHolder(View convertView)
        {
            iv = (ImageView) convertView.findViewById(R.id.iv);
        }
    }
}
