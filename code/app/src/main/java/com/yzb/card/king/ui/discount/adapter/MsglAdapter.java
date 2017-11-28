package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.discount.bean.MsglBean;

import java.util.List;

/**
 * Created by gengqiyun on 2016/4/29.
 */
public class MsglAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    public List<MsglBean.ArticleList> itemBeans;
    public MsglBean msglBean;

    public void setDataList(MsglBean msglBean) {
        this.msglBean = msglBean;

        if (msglBean != null) {
            this.itemBeans = msglBean.articleList;
            msglBean.isSelected = true;
        }
    }

    public void clear() {
        if (itemBeans != null) {
            itemBeans.clear();
            notifyDataSetChanged();
        }
    }

    public MsglAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (msglBean == null) {
            return 0;
        } else {
            return itemBeans == null ? 1 : itemBeans.size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return msglBean;
        } else {
            return itemBeans == null ? null : itemBeans.get(position - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expand_view_row, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.iv_left = (ImageView) convertView.findViewById(R.id.iv_left);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Object obj = getItem(position);
        if (obj instanceof MsglBean && position == 0) {
            MsglBean msglBean = (MsglBean) obj;
            viewHolder.tv_title.setText("美食概览");
            viewHolder.tv_content.setText("\u3000\u3000" + msglBean.description);

            if (msglBean.isSelected) {
                viewHolder.iv_left.setBackgroundResource(R.drawable.bg_cen_yellow);
            } else {
                viewHolder.iv_left.setBackgroundResource(R.drawable.bg_cen_grey);
            }
        } else {
            MsglBean.ArticleList articleBean = (MsglBean.ArticleList) obj;
            viewHolder.tv_title.setText(articleBean.title);
            viewHolder.tv_content.setText("\u3000\u3000" + articleBean.content);

            if (articleBean.isSelected) {
                viewHolder.iv_left.setBackgroundResource(R.drawable.bg_cen_yellow);
            } else {
                viewHolder.iv_left.setBackgroundResource(R.drawable.bg_cen_grey);
            }
        }
        return convertView;
    }

    class ViewHolder {
        public TextView tv_title;
        public TextView tv_content;
        public ImageView iv_left;
    }
}
