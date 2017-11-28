package com.yzb.card.king.ui.discount.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.film.activity.SeatSaleActivity;
import com.yzb.card.king.ui.manage.UserManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilmEventsAdapter extends BaseAdapter {

    private List<Map> data;
    private LayoutInflater layoutInflater = null;
    private Activity activity;
    private Bundle bundle;

    public FilmEventsAdapter(Activity activity, Bundle bundle) {
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        this.bundle = bundle;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Map<String, Object> getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        // 重复使用convertView 减少对象创建 防止每次都要实例化布局
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_film_events, null);

            // 利用ViewHolder保存组件,只需查找一次组件
            viewHolder = new ViewHolder();

            viewHolder.bgnTime = (TextView) convertView.findViewById(R.id.bgnTime);
            viewHolder.endTime = (TextView) convertView.findViewById(R.id.endTime);
            viewHolder.language = (TextView) convertView.findViewById(R.id.language);
            viewHolder.filmEffect = (TextView) convertView.findViewById(R.id.filmEffect);
            viewHolder.hallName = (TextView) convertView.findViewById(R.id.hallName);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.byBtn = (Button) convertView.findViewById(R.id.buyBtn);

            // 标记ViewHouder
            convertView.setTag(viewHolder);
        } else {

            // convertView存在则直接获取
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Map<String, Object> map = getItem(position);

        viewHolder.bgnTime.setText(String.valueOf(map.get("bgnTime")));
        viewHolder.endTime.setText(String.valueOf(map.get("endTime")));
        viewHolder.language.setText(String.valueOf(map.get("filmLanguages")));
        viewHolder.filmEffect.setText(String.valueOf(map.get("filmEffect")));
        viewHolder.hallName.setText(String.valueOf(map.get("hallName")));
        viewHolder.price.setText(new BigDecimal(String.valueOf(map.get("price"))).setScale(0) + "");

        final String hallId = String.valueOf(map.get("hallId"));
        final String hallName = String.valueOf(map.get("hallName"));
        final String bgnTime = String.valueOf(map.get("bgnTime"));
        final String filmLanguages = String.valueOf(map.get("filmLanguages"));
        final String filmEffect = String.valueOf(map.get("filmEffect"));
        final String price = String.valueOf(map.get("price"));

        viewHolder.byBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UserManager.getInstance().isLogin()) {
                    Intent intent = new Intent();
                    bundle.putString("bgnTime", bgnTime);
                    bundle.putString("filmLanguages", filmLanguages);
                    bundle.putString("filmEffect", filmEffect);
                    bundle.putString("hallId", hallId);
                    bundle.putString("hallName", hallName);
                    bundle.putString("price", price);
                    bundle.putString("filmEffect", filmEffect);

                    intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
                    intent.setClass(activity, SeatSaleActivity.class);
                    activity.startActivityForResult(intent, 0);
                } else {
                    new GoLoginDailog(activity).show();
                }
            }
        });

        return convertView;
    }

    // 用于保存第一次查找的组件，避免下次重复查找
    static class ViewHolder {
        TextView bgnTime;
        TextView endTime;
        TextView language;
        TextView filmEffect;
        TextView hallName;
        TextView price;
        Button byBtn;
    }

    public void appendDataList(List<Map> data) {
        if (data == null || data.size() == 0) return;
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        if (this.data != null) {
            this.data.clear();
            notifyDataSetChanged();
        }
    }

    public List<Map> getDataList() {
        return this.data;
    }

}
