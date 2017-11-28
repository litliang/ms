package com.yzb.card.king.ui.hotel.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelThemeBean;
import com.yzb.card.king.bean.ticket.FlightDynamicsBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.ValidatorUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：今日推荐酒店适配器
 * 作  者：Li Yubing
 * 日  期：2016/7/14
 * 描  述：
 */
public class HotelTodayRecommendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private LayoutInflater layoutInflater;

    private List<HotelThemeBean> list = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public Object getItem(int index)
    {
        return list.get(index);
    }

    public HotelTodayRecommendedAdapter(Context context)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void addNewList(List<HotelThemeBean> list)
    {

        this.list.clear();

        this.list.addAll(list);

        notifyDataSetChanged();
    }

    public void addMoreList(List<HotelThemeBean> list)
    {


        this.list.addAll(list);

        notifyDataSetChanged();
    }


    /**
     * 访问推荐酒店列表成功后数量+1
     *
     * @param position
     */
    public void setCount(int position)
    {
        list.get(position).setBrowseCount(list.get(position).getBrowseCount() + 1);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        ViewHolder viewHolder = new ViewHolder(layoutInflater.inflate(R.layout.hotel_adapter_hotel_today_recommended, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        ViewHolder viewHold = (ViewHolder) holder;

        viewHold.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (onItemClickListener != null) {

                    onItemClickListener.setOnItemClickListener(position);
                }

            }
        });

        HotelThemeBean bean = list.get(position);

        if (!TextUtils.isEmpty(bean.getBgImageUrl())) {

            x.image().bind(viewHold.ivThemeImage, ServiceDispatcher
                    .getImageUrl(bean.getBgImageUrl()));

        }

        if (!TextUtils.isEmpty(bean.getThemeName())) {

            viewHold.tvThemeName.setText(bean.getThemeName());

        }
        viewHold.tvBrownsCount.setText(UiUtils.getString(R.string.hotel_browe_count, bean.getBrowseCount()));

    }


    @Override
    public int getItemCount()
    {
        return  list.size();
    }


    public void clearData()
    {

        if (this.list != null) {
            this.list.clear();
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlTodayHotelRecommend;

        ImageView ivThemeImage;

        TextView tvThemeName;

        TextView tvBrownsCount;

        View convertView;

        public ViewHolder(View convertView)
        {
            super(convertView);
            int heightPixels = context.getResources().getDisplayMetrics().heightPixels;

            int imageHeight = heightPixels * 220 / 1080;


            this.convertView = convertView;
            ivThemeImage = (ImageView) convertView.findViewById(R.id.ivThemeImage);

            tvBrownsCount = (TextView) convertView.findViewById(R.id.tvBrownsCount);

            tvThemeName = (TextView) convertView.findViewById(R.id.tvThemeName);

            rlTodayHotelRecommend = (RelativeLayout) convertView.findViewById(R.id.rlTodayHotelRecommend);

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rlTodayHotelRecommend.getLayoutParams();

            lp.height = imageHeight;

            rlTodayHotelRecommend.setLayoutParams(lp);

//            RelativeLayout.LayoutParams iamgeLp = (RelativeLayout.LayoutParams) ivThemeImage.getLayoutParams();
//
//            iamgeLp.height = imageHeight;
//
//            iamgeLp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//
//            ivThemeImage.setLayoutParams(iamgeLp);
//
//            ivThemeImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void setOnItemClickListener(int postion);
    }
}
