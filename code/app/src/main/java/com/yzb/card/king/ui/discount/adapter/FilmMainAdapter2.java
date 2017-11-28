package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.luxury.activity.AdapterItemClickCallBack;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.ImageUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gengqiyun on 2016/5/18.
 * 电影-首页 适配器；
 */
public class FilmMainAdapter2 extends BaseAdapter
{
    private final Location city;
    private Context context;
    private LayoutInflater inflater;
    private List<StoreBean> storeBeans;
    private AdapterItemClickCallBack callBack;

    private ImageOptions imageOptions;

    public FilmMainAdapter2(Context context)
    {
        this.context = context;
        city = GlobalApp.getSelectedCity();
        inflater = LayoutInflater.from(context);

        imageOptions = new ImageOptions.Builder()
                //.setSize(imageWith, imageHeight)
                .setRadius(DensityUtil.dip2px(57 / 2))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .build();
    }

    public void appendDataList(List<StoreBean> storeBeans)
    {
        if (storeBeans == null || storeBeans.size() == 0) return;
        if (this.storeBeans == null)
        {
            this.storeBeans = new ArrayList<>();
        }
        this.storeBeans.addAll(storeBeans);
        notifyDataSetChanged();
    }

    public List<StoreBean> getDataList()
    {
        return this.storeBeans;
    }

    public void clear()
    {
        if (storeBeans != null)
        {
            storeBeans.clear();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount()
    {
        return storeBeans == null ? 0 : storeBeans.size();
    }

    @Override
    public StoreBean getItem(int position)
    {
        return storeBeans == null ? null : storeBeans.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listview_item_film_main2, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        StoreBean storeBean = getItem(position);

        if (!TextUtils.isEmpty(storeBean.storePhoto))
        {
            x.image().bind(viewHolder.ivpic, storeBean.storePhoto, imageOptions);
        }

        String storeName = TextUtils.isEmpty(storeBean.storeName) ? "" : storeBean.storeName;
        viewHolder.tvtitle.setText(storeName);


        viewHolder.tvyxl.setText("月销" + storeBean.orderCount + "笔");

        //0.5*分数=要显示的刻度；
        viewHolder.ratingBar.setStarMarkAndSore(storeBean.vote / 2);

        viewHolder.tvfs.setText("" + storeBean.vote + "分");

        if (!TextUtils.isEmpty(storeBean.recentEvents))
        {
            viewHolder.tv_jqcc.setVisibility(View.VISIBLE);
            viewHolder.tv_jqcc.setText("近期场次\u3000" + storeBean.recentEvents);
        } else
        {
            viewHolder.tv_jqcc.setVisibility(View.GONE);
        }

        // init优惠列表；
        viewHolder.panelyh.removeAllViews();
        List<StoreBean.ActivityList> activityList = storeBean.activityList;

        if (activityList == null || activityList.size() == 0)
        {
            viewHolder.panelyh.setVisibility(View.GONE);
            viewHolder.tv_empty_hd.setVisibility(View.VISIBLE);
        } else
        {
            viewHolder.panelyh.setVisibility(View.VISIBLE);
            viewHolder.tv_empty_hd.setVisibility(View.GONE);
            View view = null;
            StoreBean.ActivityList activityItem;

            for (int i = 0, j = activityList.size(); i < j; i++)
            {
                view = inflater.inflate(R.layout.store_yh_item, null);
                activityItem = activityList.get(i);
                //view.findViewById(R.id.iv_0).setBackgroundResource(ImageUtils.getBankImage(activityItem.bankId));
                ((ImageView) view.findViewById(R.id.iv_0)).setImageBitmap(AppUtils.getImageFromAssets(context,
                        ImageUtils.getBankName((long) activityItem.bankId)));
                String yhsm = TextUtils.isEmpty(activityItem.otherTitle) ? "" : activityItem.otherTitle;
                ((TextView) view.findViewById(R.id.tv_yhsm)).setText(yhsm);

                (view.findViewById(R.id.iv_xian)).setVisibility(View.VISIBLE);
                viewHolder.panelyh.addView(view);
            }
        }

        //两点距离，单位为：米,转换错误时返回-1.
        double distance = DistanceUtil.getDistance(new LatLng(city.latitude, city.longitude),
                new LatLng(storeBean.lat, storeBean.lng));

        if (distance < 1000)
        {
            viewHolder.tvjl.setText(String.format("%.1f", distance) + "m");
        } else if (distance > 1000 && distance <= 100000)
        {
            viewHolder.tvjl.setText(String.format("%.1f", distance / 1000) + "km");
        } else if (distance > 100000)
        {
            viewHolder.tvjl.setText(">100km");
        }

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (callBack != null)
                {
                    callBack.itemClickCallBack(position);
                }
            }
        });

//        // 最后一项，隐藏divider；
//        if (position == storeBeans.size() - 1) {
//            viewHolder.footer_divider.setVisibility(View.GONE);
//        } else {
//            viewHolder.footer_divider.setVisibility(View.VISIBLE);
//        }
        return viewHolder.root;
    }

    public void setAdapterItemClickCallBack(AdapterItemClickCallBack callBack)
    {
        this.callBack = callBack;
    }

    public class ViewHolder
    {
        public final ImageView ivpic;
        public final TextView tvtitle;
        public final TextView tvyxl;
        public final StarBar ratingBar;
        public final TextView tvfs;
        public final TextView tvjg;
        public final LinearLayout panelyh;
        public final TextView tvjl;
        public final TextView tv_empty_hd;
        public final View root;
        public final View footer_divider;
        public final TextView tv_jqcc;

        public ViewHolder(View root)
        {
            ivpic = (ImageView) root.findViewById(R.id.iv_pic);
            tvtitle = (TextView) root.findViewById(R.id.tv_title);
            tvyxl = (TextView) root.findViewById(R.id.tv_yxl);
            ratingBar = (StarBar) root.findViewById(R.id.starBar);
            tv_jqcc = (TextView) root.findViewById(R.id.tv_jqcc);
            tvfs = (TextView) root.findViewById(R.id.tv_fs);
            tv_empty_hd = (TextView) root.findViewById(R.id.tv_empty_hd);
            tvjg = (TextView) root.findViewById(R.id.tv_jg);
            panelyh = (LinearLayout) root.findViewById(R.id.panel_yh);
            tvjl = (TextView) root.findViewById(R.id.tv_jl);
            footer_divider = root.findViewById(R.id.footer_divider);
            this.root = root;
        }
    }
}
