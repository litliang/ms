package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelNoticeBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

import java.util.List;


/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2016/11/14
 */
public class TravelNoticeAdapter extends BaseListAdapter<TravelNoticeBean>
{

    public TravelNoticeAdapter(Context context, List<TravelNoticeBean> noticeBeans)
    {
        super(context, noticeBeans);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_listview_notice, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TravelNoticeBean bean = getItem(position);
        viewHolder.tvContent.setText(bean.getFeeIntro());
        return viewHolder.root;
    }

    public class ViewHolder
    {
        public final TextView tvContent;
        public final View root;

        public ViewHolder(View root)
        {
            tvContent = (TextView) root.findViewById(R.id.tvContent);
            this.root = root;
        }
    }
}
