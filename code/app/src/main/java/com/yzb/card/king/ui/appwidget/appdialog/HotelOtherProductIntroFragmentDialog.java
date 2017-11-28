package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelExtraProductBean;
import com.yzb.card.king.ui.hotel.fragment.HotelOtherProductIntroFragment;
import com.yzb.card.king.ui.hotel.fragment.HotelOtherProductIntroNoMenuFragment;
import com.yzb.card.king.ui.hotel.fragment.HotelProductMenuFragment;
import com.yzb.card.king.util.LogUtil;

/**
 * 类  名：酒店其它产品简介
 * 作  者：Li Yubing
 * 日  期：2017/8/10
 * 描  述：酒店详情内容---餐厅、酒吧、大堂吧、SPA、健身房、游泳池产品简介
 */
public class HotelOtherProductIntroFragmentDialog extends DialogFragment {

    /**
     * 无菜单信息
     */
    public static final String HOTEL_PRODUCT_NO_MENU = "no_menu";

    /**
     * 菜单信息
     */
    public static final String HOTEL_PRODUCT_MENU = "menu";

    private Hotel.GoodsType goodsType;

    private HotelExtraProductBean headerItem;

    private  Bundle bundle;

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout(dm.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_fragment_dialog_other_intro, container, false);
        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        String   tag = getTag();

         bundle = getArguments();

         headerItem = (HotelExtraProductBean) bundle.get("HotelExtraProductBean");

        goodsType = (Hotel.GoodsType) bundle.get("goodsType");


        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        if(HOTEL_PRODUCT_NO_MENU.equals(tag)){

            HotelOtherProductIntroNoMenuFragment  fragment = new HotelOtherProductIntroNoMenuFragment();

            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.contentll, fragment)
                    .commit();

        }else {

            HotelOtherProductIntroFragment    fragment = new HotelOtherProductIntroFragment();

            fragment.setDataCall(hotelDialogInterface);

            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.contentll, fragment)
                    .commit();
        }



        return view;
    }

    private  HotelDialogInterface hotelDialogInterface = new HotelDialogInterface() {
        @Override
        public void backAction()
        {
            if(bundle == null){

                return;
            }

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            HotelOtherProductIntroFragment    fragment = new HotelOtherProductIntroFragment();

            fragment.setDataCall(hotelDialogInterface);

            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.contentll, fragment)
                    .commit();
        }

        @Override
        public void menuAction()
        {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            HotelProductMenuFragment    fragment = new HotelProductMenuFragment();

            fragment.setDataCall(hotelDialogInterface);

            Bundle bundle = new Bundle();

            bundle.putString("goodsType",goodsType.getGoodsTypeCode());

            bundle.putString("menuId",headerItem.getGoodsId()+"");

            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.contentll, fragment)
                    .commit();
        }
    };


    public interface HotelDialogInterface {
        /**
         * 返回到上一级碎片
         */
        public void backAction();

        /**
         * 产品菜单
         */
        public void menuAction();


    }
}
