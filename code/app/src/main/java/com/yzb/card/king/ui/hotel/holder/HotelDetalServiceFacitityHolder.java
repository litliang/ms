package com.yzb.card.king.ui.hotel.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelRoomInfoBean;
import com.yzb.card.king.ui.appwidget.SpecHeightGridView;
import com.yzb.card.king.ui.hotel.adapter.HotelInfoFacitityAdapter;
import com.yzb.card.king.ui.hotel.fragment.HotelRoomInfoFragment;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/9
 * 描  述：
 */
public class HotelDetalServiceFacitityHolder extends BaseViewHolder<HotelRoomInfoBean.RoomService> {//

    private Context context;


    private TextView tvBankName;

    private  SpecHeightGridView  gvFactivity;

    private LayoutInflater inflater;

    public HotelDetalServiceFacitityHolder(ViewGroup parent)
    {
        super(parent, R.layout.view_item_hotel_service_facitity);

        context = parent.getContext();

        inflater = LayoutInflater.from(context);
    }


    @Override
    public void setData(HotelRoomInfoBean.RoomService data)
    {

        tvBankName.setText(data.getTypeName());

       String[] itemNamesArray =  data.getItemNamesArray();

        String[] itemPhotoArray  = data.getItemPotoArray();

        HotelInfoFacitityAdapter hotelFacitityAdapter = new HotelInfoFacitityAdapter(inflater,itemNamesArray,itemPhotoArray);

        gvFactivity.setAdapter(hotelFacitityAdapter);
    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        tvBankName = findViewById(R.id.tvBankName);

        gvFactivity = findViewById(R.id.gvFactivity);

    }
}


