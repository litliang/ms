package com.yzb.card.king.ui.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.SearchDefaultBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

/**
 * 功能：搜索默认列表item中GridView适配器；
 *
 * @author:gengqiyun
 * @date: 2016/10/27
 */
public class SearchListGvAdapter extends BaseListAdapter<SearchDefaultBean.Child>
{
    public SearchListGvAdapter(Context context)
    {
        super(context);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_search_gridview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final SearchDefaultBean.Child child = getItem(position);
        TextView tvContent = (TextView) viewHolder.root;
        tvContent.setText(child.getName());
        return viewHolder.root;
    }

    public class ViewHolder
    {
        public final View root;

        public ViewHolder(View root)
        {
            this.root = root;
        }
    }
}
