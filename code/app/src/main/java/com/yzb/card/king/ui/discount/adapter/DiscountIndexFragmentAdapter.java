package com.yzb.card.king.ui.discount.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.travel.bean.DiscountRecommendBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;

import org.xutils.x;

import java.util.List;

/**
 * Created by injun on 2016/6/24.
 */
public class DiscountIndexFragmentAdapter extends BaseAdapter {

    private List<DiscountRecommendBean> data;

    private Activity activity;

    private LayoutInflater inflater;


    public DiscountIndexFragmentAdapter(Activity context, List<DiscountRecommendBean> recommendBeanList) {
        this.activity = context;
        this.data = recommendBeanList;
        inflater = LayoutInflater.from(activity);
    }


    @Override
    public int getCount() {
        return data.size() ==0 ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data == null ? null : data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.discount_commend_listvview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.discount_commend_listview_item_image);
            viewHolder.tvRemarkName = (TextView) view.findViewById(R.id.tvRemarkName);
            viewHolder.ivHomeImage = (ImageView) view.findViewById(R.id.ivHomeImage);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (this.data == null) {
            viewHolder.imageView.setImageResource(R.mipmap.no_data);
            viewHolder.ivHomeImage.setVisibility(View.GONE);
            viewHolder.tvRemarkName.setText("");
        } else {
            DiscountRecommendBean map = (DiscountRecommendBean) getItem(i);
           // String imgURL = ServiceDispatcher.getImageUrl( map.getImageCode());//ServiceDispatcher.url_image + "getImg/" + map.getImageCode() + "/0";
          //  x.image().bind( viewHolder.imageView, imgURL);

            viewHolder.imageView.setImageResource(map.getImageCodeId());
            viewHolder.ivHomeImage.setVisibility(View.VISIBLE);
            viewHolder.tvRemarkName.setText(map.getCategoryName());

            int index = i%2;
            if(index == 0){

                viewHolder.ivHomeImage.setBackgroundResource(R.mipmap.bg_home_tuijian_two);
            }else {
                viewHolder.ivHomeImage.setBackgroundResource(R.mipmap.bg_home_tuijian_one);
            }

        }
        return view;
    }

    public List<DiscountRecommendBean> getDataList() {
        return this.data;
    }

    public void addData(List<DiscountRecommendBean> discountRecommendBeen) {
        this.data.clear();
        this.data.addAll(discountRecommendBeen);
        notifyDataSetChanged();
    }

    public void appendData(List<DiscountRecommendBean> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.data.clear();
        this.notifyDataSetChanged();
    }

    static class ViewHolder {

        ImageView imageView;

        TextView tvRemarkName;

        ImageView ivHomeImage;

    }

}


