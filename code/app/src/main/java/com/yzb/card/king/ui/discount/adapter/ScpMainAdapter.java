package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.discount.bean.ShopBean;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.ViewUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gengqiyun on 2016/4/12.
 * 奢侈品首页 适配器；
 */
public class ScpMainAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private float imgWhRate = 288 / 720.0f;
    private int imgWidth = 0;
    private int imgHeight = 0;
    private List<ShopBean> shopBeans;

    public ScpMainAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        imgWidth = CommonUtil.getScreenWidth(context);
        imgHeight = (int) (imgWidth * imgWhRate + 0.5f);
    }

    public void appendDataList(List<ShopBean> shopBeans) {
        if (shopBeans == null || shopBeans.size() == 0) return;

        if (this.shopBeans == null) {
            this.shopBeans = new ArrayList<>();
        }
        this.shopBeans.addAll(shopBeans);
        notifyDataSetChanged();
    }

    public void clear() {
        if (shopBeans != null) {
            shopBeans.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return shopBeans == null ? 0 : shopBeans.size();
    }

    @Override
    public ShopBean getItem(int position) {
        return shopBeans == null ? null : shopBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item_scp_main, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ViewUtil.set(viewHolder.ivpic, imgWidth, imgHeight);

        ShopBean shopBean = getItem(position);
        x.image().bind(viewHolder.ivpic,shopBean.photo);
        viewHolder.tv_name.setText(shopBean.name);
        return viewHolder.root;
    }

    public class ViewHolder {
        public final ImageView ivpic;
        public final View root;
        public TextView tv_name;

        public ViewHolder(View root) {
            ivpic = (ImageView) root.findViewById(R.id.iv_pic);
            tv_name = (TextView) root.findViewById(R.id.tv_name);
            this.root = root;
        }
    }
}
