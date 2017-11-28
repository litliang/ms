package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.discount.bean.MstdDetailBean;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gengqiyun on 2016/4/18.
 * 美食天地；
 */
public class MstdDetailAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int imgWidth;
    private int imgHeight;
    private List<MstdDetailBean> mstdItemBeans;

    public MstdDetailAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setMeasure(int imgWidth, int imgHeight) {
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
    }

    public void appendDataList(List<MstdDetailBean> mstdItemBeans) {
        if (mstdItemBeans == null || mstdItemBeans.size() == 0) return;
        if (this.mstdItemBeans == null) {
            this.mstdItemBeans = new ArrayList<>();
        }
        this.mstdItemBeans.addAll(mstdItemBeans);
        notifyDataSetChanged();
    }

    public void clear() {
        if (mstdItemBeans != null) {
            mstdItemBeans.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mstdItemBeans == null ? 0 : mstdItemBeans.size();
    }

    @Override
    public MstdDetailBean getItem(int position) {
        return mstdItemBeans == null ? null : mstdItemBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item_mstd_detail, parent, false);

            viewHolder.tvname = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.ivfood = (ImageView) convertView.findViewById(R.id.iv_food);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imgWidth, imgHeight);
            viewHolder.ivfood.setLayoutParams(lp);

            viewHolder.tvintroduce = (TextView) convertView.findViewById(R.id.tv_introduce);
            viewHolder.tvfs = (TextView) convertView.findViewById(R.id.tv_fs);
            viewHolder.tvjg = (TextView) convertView.findViewById(R.id.tv_jg);
            viewHolder.ratingBar = (StarBar) convertView.findViewById(R.id.starBar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MstdDetailBean detailBean = getItem(position);
        viewHolder.tvname.setText(detailBean.storeName);

        x.image().bind(viewHolder.ivfood,detailBean.storePhoto);

        viewHolder.tvintroduce.setText("\u3000\u3000"+detailBean.storeTip);

        viewHolder.ratingBar.setStarMarkAndSore(detailBean.vote / 2);
        viewHolder.tvfs.setText(detailBean.vote + "分");

        viewHolder.tvjg.setText("￥" + detailBean.avgPrice + "/人");

        return convertView;
    }

    public class ViewHolder {
        public TextView tvname;
        public ImageView ivfood;
        public TextView tvintroduce;
        public TextView tvfs;
        public TextView tvjg;
        public StarBar ratingBar;
    }
}
