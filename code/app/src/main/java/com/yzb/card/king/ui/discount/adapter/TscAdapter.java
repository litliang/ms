package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.ExpandableTextView;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.discount.bean.TscBean;
import com.yzb.card.king.ui.appwidget.FlowLayout;
import com.yzb.card.king.ui.appwidget.TagAdapter;
import com.yzb.card.king.ui.appwidget.TagFlowLayout;
import com.yzb.card.king.util.CommonUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gengqiyun on 2016/4/18.
 * 特色菜；
 */
public class TscAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int imgWidth;
    private int imgHeight;
    private List<TscBean> tscBeans;
    private float wHRate = 272 / 676.0f;
    private int height = 0;

    public TscAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        height = (int) ((CommonUtil.getScreenWidth(context) - CommonUtil.dip2px(context, 20)) * wHRate + 0.5f);
    }

    public void appendDataList(List<TscBean> mstdItemBeans) {
        if (mstdItemBeans == null || mstdItemBeans.size() == 0) return;
        if (this.tscBeans == null) {
            this.tscBeans = new ArrayList<>();
        }
        this.tscBeans.addAll(mstdItemBeans);
        notifyDataSetChanged();
    }

    public void clear() {
        if (tscBeans != null) {
            tscBeans.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return tscBeans == null ? 0 : tscBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public TscBean getItem(int position) {
        return tscBeans == null ? null : tscBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        TscBean tscBean = getItem(position);

        if (getItemViewType(position) == 0 && position == 0) {
            convertView = inflater.inflate(R.layout.listview_header_mstd_detail, parent, false);
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
            iv.getLayoutParams().height = height;
            x.image().bind(iv,tscBean.dishImage);

            ExpandableTextView tvDescription = (ExpandableTextView) convertView.findViewById(R.id.tv_description);
            tvDescription.setText("\u3000\u3000" + tscBean.description);
            return convertView;
        }

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item_ms_tsc, parent, false);
            viewHolder.tvname = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.ivfood = (ImageView) convertView.findViewById(R.id.iv_food);
            viewHolder.tvintroduce = (TextView) convertView.findViewById(R.id.tv_introduce);
            viewHolder.tv_tj = (TextView) convertView.findViewById(R.id.tv_tj);
            viewHolder.flowlayout = (TagFlowLayout) convertView.findViewById(R.id.flowlayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvname.setText(tscBean.dishName);
        viewHolder.ivfood.getLayoutParams().height = height;
        x.image().bind(viewHolder.ivfood,tscBean.dishImage);

        viewHolder.tvintroduce.setText("\u3000\u3000" + tscBean.description);

        final List<TscBean.StoreList> storeList = tscBean.storeList;

        if (storeList == null || storeList.size() == 0) {
            viewHolder.flowlayout.setVisibility(View.GONE);
            viewHolder.tv_tj.setVisibility(View.GONE);
        } else {
            viewHolder.flowlayout.setVisibility(View.VISIBLE);
            viewHolder.tv_tj.setVisibility(View.VISIBLE);

            viewHolder.flowlayout.setAdapter(new TagAdapter<TscBean.StoreList>(storeList) {
                @Override
                public View getView(FlowLayout parent, int position, TscBean.StoreList store) {
                    View view = inflater.inflate(R.layout.item_flow_layout, viewHolder.flowlayout, false);
                    TextView tv_location = (TextView) view.findViewById(R.id.tv_location);
                    tv_location.setText(store.storeName);
                    return view;
                }
            });
            viewHolder.flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
//                    //tag 的点击事件；
//                    Intent intent = new Intent(context, MapModelActivity.class);
//                    ArrayList<StoreBean> itemStore = new ArrayList<>();
//
//                    //将选中的门店StoreList实体转化为StoreBean；以便统一处理；
//                    TscBean.StoreList clickStore = storeList.get(position);
//                    itemStore.add(new StoreBean(clickStore.storeId, clickStore.storeName,
//                            clickStore.lat, clickStore.lng, clickStore.vote, clickStore.avgPrice));
//
//                    intent.putExtra("typeId", clickStore.shopTypeId);
//                    intent.putExtra("typeName", clickStore.typeName);
//                    intent.putExtra("typeParentId", clickStore.parentId);
//                    intent.putExtra("typeGrandParentId", clickStore.grandParentId);
//                    intent.putExtra("listData", itemStore);
//                    context.startActivity(intent);
                    return true;
                }
            });

        }
        return convertView;
    }

    public class ViewHolder {
        public TextView tvname;
        public ImageView ivfood;
        public TextView tvintroduce;
        public TagFlowLayout flowlayout;
        public TextView tv_tj;
    }
}
