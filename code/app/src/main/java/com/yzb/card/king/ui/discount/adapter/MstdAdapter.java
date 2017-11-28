package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.discount.bean.MstdItemBean;
import com.yzb.card.king.util.CommonUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gengqiyun on 2016/4/18.
 * 美食天地；
 */
public class MstdAdapter extends BaseAdapter {
    private final int height;
    private Context context;
    private LayoutInflater inflater;
    private int imgWidth;
    private int imgHeight;
    private List<MstdItemBean> mstdItems;
    private float wHRate = 272 / 676.0f;

    public MstdAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        height = (int) ((CommonUtil.getScreenWidth(context) - CommonUtil.dip2px(context, 20)) * wHRate + 0.5f);
    }

    public void appendDataList(List<MstdItemBean> mstdItemBeans) {
        if (mstdItemBeans == null || mstdItemBeans.size() == 0) return;
        if (this.mstdItems == null) {
            this.mstdItems = new ArrayList<>();
        }
        this.mstdItems.addAll(mstdItemBeans);
        notifyDataSetChanged();
    }

    public void clear() {
        if (mstdItems != null) {
            mstdItems.clear();
            notifyDataSetChanged();
        }
    }

    public void setMeasure(int imgWidth, int imgHeight) {
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
    }

    @Override
    public int getCount() {
        return mstdItems == null ? 0 : mstdItems.size();
    }

    @Override
    public MstdItemBean getItem(int position) {
        return mstdItems == null ? null : mstdItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item_ms_td, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MstdItemBean mstdItemBean = getItem(position);
        viewHolder.ivpic.getLayoutParams().height = height;

        x.image().bind(viewHolder.ivpic,mstdItemBean.image);
        viewHolder.tvtitle.setText(mstdItemBean.title);

        return viewHolder.root;
    }

    public class ViewHolder {
        public final ImageView ivpic;
        public final TextView tvtitle;
        public final RelativeLayout rlroot;
        public final View root;

        public ViewHolder(View root) {
            ivpic = (ImageView) root.findViewById(R.id.iv_pic);
            tvtitle = (TextView) root.findViewById(R.id.tv_title);
            rlroot = (RelativeLayout) root.findViewById(R.id.rl_root);
            this.root = root;
        }
    }
}
