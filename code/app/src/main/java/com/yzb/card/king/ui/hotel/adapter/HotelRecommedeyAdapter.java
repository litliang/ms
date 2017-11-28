package com.yzb.card.king.ui.hotel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.ExpandableTextView;
import com.yzb.card.king.util.ValidatorUtil;
import com.yzb.wallet.gridpasswordview.Util;

import org.xutils.x;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/29
 * 描  述：
 */
public class HotelRecommedeyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater layoutInflater;

    private int llHeight;

    private List<HotelBean> list;

    private Context context;


    private int etvWidth;

    public HotelRecommedeyAdapter(Context context, List<HotelBean> list)
    {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;

        llHeight = (screenWidth - Util.dp2px(context, 15)) / 2;
    }

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void setOnItemClickListener(int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHold(layoutInflater.inflate(R.layout.hotel_adapter_recommendey_hotel, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        final MyViewHold myViewHold = (MyViewHold) holder;


        if (holder instanceof MyViewHold) {
            HotelBean bean = (HotelBean) list.get(position);
            myViewHold.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    int position = myViewHold.getLayoutPosition();
                    onItemClickListener.setOnItemClickListener(position);
                }
            });


            if (!TextUtils.isEmpty(bean.getHotelName())) {

                myViewHold.tvHotelName.setText(bean.getHotelName());
            }

            myViewHold.tvDistance.setText("距离市中心" + bean.getCenterDis() + "m");

            myViewHold.tvHotelPrice.setText("¥"+bean.getMinPrice());

            if (!TextUtils.isEmpty(bean.getDefaultImgUrl())) {
                if (ValidatorUtil.isUrl(bean.getDefaultImgUrl())) {
                    x.image().bind(myViewHold.ivHotelImage, bean.getDefaultImgUrl());
                } else {
                    x.image().bind(myViewHold.ivHotelImage, ServiceDispatcher.getImageUrl(bean.getDefaultImgUrl()));
                }

            }
        }
    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }


    class MyViewHold extends RecyclerView.ViewHolder {


        TextView tvHotelPrice;

        TextView tvHotelName;

        ImageView ivHotelImage;

        TextView tvDistance;


        public MyViewHold(View itemView)
        {
            super(itemView);


            tvHotelPrice = (TextView) itemView.findViewById(R.id.tvHotelPrice);

            tvHotelName = (TextView) itemView.findViewById(R.id.tvHotelName);

            ivHotelImage = (ImageView) itemView.findViewById(R.id.ivHotelImage);

            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);


        }
    }
}
