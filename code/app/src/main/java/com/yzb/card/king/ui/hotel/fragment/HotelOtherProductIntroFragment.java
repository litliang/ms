package com.yzb.card.king.ui.hotel.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelExtraProductBean;
import com.yzb.card.king.ui.appwidget.appdialog.HotelOtherProductIntroFragmentDialog;

/**
 * 类  名：酒店其它产品简介
 * 作  者：Li Yubing
 * 日  期：2017/8/10
 * 描  述：
 */
public class HotelOtherProductIntroFragment extends HotelOtherProductIntroNoMenuFragment implements View.OnClickListener{

    private  TextView tvDiningRoomMenu;

    private HotelOtherProductIntroFragmentDialog.HotelDialogInterface hotelDialogInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_hotel_other_product_intro,null);

        initOneView(view);

        Bundle bundle = getArguments();

       if(bundle.containsKey("HotelExtraProductBean")){

           HotelExtraProductBean   headerItem = (HotelExtraProductBean) bundle.get("HotelExtraProductBean");

           String floorDesc = bundle.getString("floorDesc");

           initOneData(headerItem);

           Hotel.GoodsType   goodsType = (Hotel.GoodsType) bundle.get("goodsType");
           initType(goodsType);
       }


        return view;
    }

    private void initOneData(HotelExtraProductBean headerItem)
    {

        initData(headerItem);

        tvDiningRoomMenu.setText(headerItem.getGoodsName()+"菜单");
    }

    public void initOneView(View view)
    {
        initView(view);
        view.findViewById(R.id.viewMenu).setOnClickListener(this);
        tvDiningRoomMenu = (TextView) view.findViewById(R.id.tvDiningRoomMenu);

    }

    public void setDataCall(HotelOtherProductIntroFragmentDialog.HotelDialogInterface hotelDialogInterface){

        this.hotelDialogInterface = hotelDialogInterface;

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.viewMenu:

                if(hotelDialogInterface != null){

                    hotelDialogInterface.menuAction();
                }

                break;

            default:
                break;
        }
    }
}
