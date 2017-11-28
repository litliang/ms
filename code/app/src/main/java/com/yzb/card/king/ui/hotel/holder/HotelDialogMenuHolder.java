package com.yzb.card.king.ui.hotel.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelMenuBean;
import com.yzb.card.king.ui.appwidget.SpecHeightGridView;
import com.yzb.card.king.ui.hotel.adapter.HotelDialogMenuItemAdapter;
import com.yzb.card.king.ui.hotel.adapter.HotelInfoFacitityAdapter;

import java.util.List;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：酒店菜单holder
 * 作  者：Li Yubing
 * 日  期：2017/8/10
 * 描  述：
 */
public class HotelDialogMenuHolder extends BaseViewHolder<HotelMenuBean> {//

    private Context context;


    private TextView tvBankName;

    private SpecHeightGridView gvFactivity;

    private LayoutInflater inflater;

    public HotelDialogMenuHolder(ViewGroup parent)
    {
        super(parent, R.layout.view_item_hotel_dialog_menu);

        context = parent.getContext();

        inflater = LayoutInflater.from(context);
    }


    @Override
    public void setData(HotelMenuBean data)
    {

        tvBankName.setText(data.getKey());

        List<HotelMenuBean.HotelChildMenuBean> value = data.getValue();

        HotelDialogMenuItemAdapter hotelFacitityAdapter = new HotelDialogMenuItemAdapter(inflater,value);

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


