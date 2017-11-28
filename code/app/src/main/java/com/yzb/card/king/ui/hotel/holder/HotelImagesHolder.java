package com.yzb.card.king.ui.hotel.holder;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.LogUtil;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/24
 * 描  述：
 */
public class HotelImagesHolder extends BaseViewHolder<String> implements View.OnClickListener{

    private Context context;

    private ImageView tvImage;

    private Handler handler;

    private TextView tvSpreadMore;

    private boolean ifSpread = false;

    public HotelImagesHolder(ViewGroup parent,Handler handler)
    {
        super(parent, R.layout.view_item_hotel_images);

        context = parent.getContext();

        this.handler = handler;
    }

    private int spreadSize;

    public HotelImagesHolder(ViewGroup parent,Handler handler,boolean ifSpread ,int spreadSize)
    {
        super(parent, R.layout.view_item_hotel_images);

        context = parent.getContext();

        this.handler = handler;

        this.ifSpread = ifSpread;

        this.spreadSize = spreadSize;
    }


    @Override
    public void setData(String data)
    {

        Glide.with(context).load(ServiceDispatcher.getImageUrl(data)).into(tvImage);

        if(ifSpread && spreadSize - 1== getAdapterPosition()){

            tvSpreadMore.setVisibility(View.VISIBLE);

        }else {

            tvSpreadMore.setVisibility(View.GONE);

        }

    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        tvImage = findViewById(R.id.tvImage);

        tvImage.setOnClickListener(this);

        tvSpreadMore = findViewById(R.id.tvSpreadMore);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.tvImage:

                int index  = getAdapterPosition();

                if(ifSpread && index == spreadSize-1){

                    index = -1;//通知展开图片数据
                }

                handler.sendEmptyMessage(index);

                break;

            default:
                break;
        }
    }
}
