package com.yzb.card.king.ui.hotel.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.bean.hotel.HotelBean;
import com.yzb.card.king.bean.hotel.HotelProductObjectBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.activtiy.HotelProductInfoActivity;
import com.yzb.wallet.loading.indicators.LineScaleIndicator;

import org.xutils.x;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：
 */
public class HotelProductHolder extends BaseViewHolder<HotelProductObjectBean> implements View.OnClickListener{

    private LinearLayout llOne;

    private Context context;

    private ImageView tvHotelImage,ivLifeStages;

    private TextView tvHotelName,tvBackMoney,tvHotelVote,tvHoteVoteMessage,tvHotelType,tvHotelCityDistance,tvBankPre,tvQuan,tvQiang,tvKaquanyi,tvTodayLeftRoom,tvNewsMessage,tvHotelRoomPrice;

    private int searchAddrType;

    public HotelProductHolder(ViewGroup parent,int searchAddrType)
    {
        super(parent, R.layout.view_hotel_product_item);

        context = parent.getContext();

        this.searchAddrType = searchAddrType;
    }

    @Override
    public void setData(HotelProductObjectBean data)
    {
        super.setData(data);

        tvHotelName.setText(data.getHotelName());

        FavInfoBean pre = data.getDisMap();

        if("1".equals(pre.getCashbackStatus()) || "1".equals(pre.getMinusStatus())){

            tvBackMoney.setVisibility(View.VISIBLE);

            if("1".equals(pre.getCashbackStatus())){
                tvBackMoney.setText("返");
            }else if("1".equals(pre.getMinusStatus())){

                tvBackMoney.setText("减");
            }

        }else {

            tvBackMoney.setVisibility(View.INVISIBLE);
        }


        if("1".equals(pre.getStageStatus())){

            ivLifeStages.setVisibility(View.VISIBLE);
        }else {

            ivLifeStages.setVisibility(View.GONE);
        }


        if("1".equals(pre.getBankStatus())){

            tvBankPre.setVisibility(View.VISIBLE);
        }else {

            tvBankPre.setVisibility(View.GONE);
        }


        if("1".equals(pre.getTicketStatus())){

            tvQuan.setVisibility(View.VISIBLE);
        }else {

            tvQuan.setVisibility(View.GONE);
        }

        if("1".equals(pre.getFlashsaleStatus())){

            tvQiang.setVisibility(View.VISIBLE);
        }else {

            tvQiang.setVisibility(View.GONE);
        }

        if("1".equals(pre.getGiftsStatus())){

            tvKaquanyi.setVisibility(View.VISIBLE);
        }else {

            tvKaquanyi.setVisibility(View.GONE);
        }

        if("1".equals(pre.getLeftStatus())){

            tvTodayLeftRoom.setVisibility(View.VISIBLE);
        }else {

            tvTodayLeftRoom.setVisibility(View.GONE);
        }

        tvHotelVote.setText(data.getVote()+"");

        HoteUtil.hotelVoteMessage(data.getVote(),tvHoteVoteMessage);

        tvHotelType.setText(data.getBrandTypeDesc());

        if(searchAddrType == 1){

            tvHotelCityDistance.setText("距离我"+data.getDis()+"米");

        }else {

            tvHotelCityDistance.setText("距离市中心"+data.getDis()+"米");
        }



        tvNewsMessage.setText(data.getLastReserve());

        tvHotelRoomPrice.setText(data.getMinPrice()+"");

        x.image().bind(tvHotelImage, ServiceDispatcher.getImageUrl(data.getDefaultImgUrl()));

        llOne.setTag(data);
    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        llOne = findViewById(R.id.llOne);

        tvHotelImage = findViewById(R.id.tvHotelImage);

        ivLifeStages = findViewById(R.id.ivLifeStages);


        tvHotelName = findViewById(R.id.tvHotelName);

        tvBackMoney = findViewById(R.id.tvBackMoney);

        tvHotelVote = findViewById(R.id.tvHotelVote);

        tvHoteVoteMessage = findViewById(R.id.tvHoteVoteMessage);

        tvHotelType = findViewById(R.id.tvHotelType);

        tvHotelCityDistance = findViewById(R.id.tvHotelCityDistance);

        tvBankPre = findViewById(R.id.tvBankPre);

        tvQuan = findViewById(R.id.tvQuan);
        tvQiang = findViewById(R.id.tvQiang);

        tvKaquanyi = findViewById(R.id.tvKaquanyi);

        tvTodayLeftRoom = findViewById(R.id.tvTodayLeftRoom);

        tvNewsMessage = findViewById(R.id.tvNewsMessage);

        tvHotelRoomPrice = findViewById(R.id.tvHotelRoomPrice);

        llOne.setOnClickListener(this);
    }



    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.llOne:

                if(v.getTag() == null){

                    return;
                }

                HotelProductObjectBean data = (HotelProductObjectBean) v.getTag();

                Intent intent = new Intent(context, HotelProductInfoActivity.class);
                intent.putExtra("hotelId", data.getHotelId() + "");
                context.startActivity(intent);

                break;
            default:
                break;

        }
    }
}
