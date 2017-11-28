package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.hotel.ExtraGoodsComboBean;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelExtraProductBean;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.hotel.activtiy.HotelOtherProductOrderActivity;
import com.yzb.card.king.ui.hotel.fragment.BankFavorableFragment;
import com.yzb.card.king.ui.hotel.fragment.BankLifeStageFragment;
import com.yzb.card.king.ui.hotel.fragment.GetCouponFragment;
import com.yzb.card.king.ui.hotel.fragment.HotelDiningRoomFragment;
import com.yzb.card.king.ui.hotel.fragment.HotelOtherProductInfoIntroFragment;
import com.yzb.card.king.ui.hotel.fragment.HotelProductMenuFragment;
import com.yzb.card.king.ui.hotel.fragment.ServiceFacitityFragment;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.Utils;

/**
 * 类  名：酒店产品详情简介
 * 作  者：Li Yubing
 * 日  期：2017/8/10
 * 描  述：
 */
public class HotelOtherProductInfoIntroFragmentDialog extends DialogFragment implements View.OnClickListener{

    private HotelOtherProductInfoIntroFragment fragmentOne;

    private Hotel.GoodsType goodsType;

    private ExtraGoodsComboBean item;

    private  Bundle bundle;

    @Override
    public void onStart() {
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
        View view = inflater.inflate(R.layout.view_fragment_dialog_hotel_other_product_info_intro, container, false);
        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        bundle = getArguments();

        item = (ExtraGoodsComboBean) bundle.get("ExtraGoodsComboBean");

        goodsType = (Hotel.GoodsType) bundle.get("goodsType");

        fragmentOne = new HotelOtherProductInfoIntroFragment();

        fragmentOne.setDataCall(hotelDialogInterface);

        fragmentOne.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.contentll, fragmentOne)
                .commit();

        initView(view);


        return view;
    }

    private void initView(View view)
    {
        view.findViewById(R.id.btOne).setOnClickListener(this);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);

        TextView tvRoomComboPrice = (TextView) view.findViewById(R.id.tvRoomComboPrice);

        tvRoomComboPrice.setText(item.getOnlinePrice()+"");

        TextView tvRturnMoney = (TextView) view.findViewById(R.id.tvRturnMoney);

        String storePrice = Utils.subZeroAndDot(item.getStorePrice()+"");

       String tempStr = "门店价       ¥";

       // int startIndex = tempStr.length();

        SpannableString ss = new SpannableString(tempStr+storePrice);

        ss.setSpan(new StrikethroughSpan(), ss.length()-storePrice.length(),ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvRturnMoney.setText(ss);

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvConfirm:
                //检查是否登录
                if (UserManager.getInstance().isLogin()) {

                    Intent intent = new Intent(getContext(),HotelOtherProductOrderActivity.class);

                    intent.putExtra("comboBean",item);

                    intent.putExtra("goodsType",goodsType);

                    getContext().startActivity(intent);

                }else {

                    new GoLoginDailog(this.getContext()).create().show();
                }

                break;
            case R.id.btOne:


                break;

            default:
                break;
        }

    }

    private HotelProductRoomInfoFragmentDialog.HotelDialogInterface hotelDialogInterface = new HotelProductRoomInfoFragmentDialog.HotelDialogInterface() {
        @Override
        public void backAction()
        {

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.contentll, fragmentOne)
                    .commit();
        }

        @Override
        public void hotelServiceFacitity()
        {
//            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//
//            ServiceFacitityFragment twoFragment = new ServiceFacitityFragment();
//
//            twoFragment.setDataCall(hotelDialogInterface);
//
//            fragmentTransaction.replace(R.id.contentll, twoFragment).commit();
        }

        @Override
        public void getCouponAction()
        {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            GetCouponFragment twoFragment = new GetCouponFragment();


            Bundle bundle = new Bundle();
            //发放平台
            int issuePlatform = 0;
            //行业id
            int industryId =  GlobalVariable.industryId;

            bundle.putInt("issuePlatform",issuePlatform);

            bundle.putInt("industryId",industryId);

            bundle.putLong("shopId",item.getShopId());

            bundle.putLong("storeId",item.getHotelId());

            bundle.putLong("goodsId",item.getPolicysId());

            twoFragment.setArguments(bundle);

            twoFragment.setDataCall(hotelDialogInterface);

            fragmentTransaction.replace(R.id.contentll, twoFragment).commit();
        }

        @Override
        public void hotelLifeStageAction()
        {
            BankLifeStageFragment twoFragment = new BankLifeStageFragment();

            Bundle bundle = new Bundle();
            //行业id
            int industryId =  GlobalVariable.industryId;

            long shopId = item.getShopId();

            long storeId = item.getHotelId();

            int goodsTypeInt =Integer.parseInt(  goodsType.getGoodsTypeCode());

            long goodsId = item.getPolicysId();

            double money = item.getOnlinePrice();

            GoldTicketParam goldTicketParam = new GoldTicketParam();

            goldTicketParam.setIndustryId(industryId);

            goldTicketParam.setShopId(shopId);

            goldTicketParam.setStoreId(storeId);

            goldTicketParam.setGoodsId(goodsId);

            bundle.putDouble("productMoney",money);

            bundle.putInt("goodsType",goodsTypeInt);

            bundle.putSerializable("goldTicketParam",goldTicketParam);

            twoFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            twoFragment.setDataCall(hotelDialogInterface);

            fragmentTransaction.replace(R.id.contentll, twoFragment).commit();
        }

        @Override
        public void bankFavorableAction()
        {
            BankFavorableFragment twoFragment = new BankFavorableFragment();

            Bundle bundle = new Bundle();
            //行业id
            int industryId =  GlobalVariable.industryId;

            long shopId = item.getShopId();

            long storeId = item.getHotelId();

            int goodsTypeInt =Integer.parseInt(  goodsType.getGoodsTypeCode());

            long goodsId = item.getPolicysId();

            GoldTicketParam goldTicketParam = new GoldTicketParam();

            goldTicketParam.setIndustryId(industryId);

            goldTicketParam.setShopId(shopId);

            goldTicketParam.setStoreId(storeId);

            goldTicketParam.setGoodsId(goodsId);

            bundle.putInt("goodsType",goodsTypeInt);

            bundle.putSerializable("goldTicketParam",goldTicketParam);

            twoFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            twoFragment.setDataCall(hotelDialogInterface);

            fragmentTransaction.replace(R.id.contentll, twoFragment).commit();
        }

        @Override
        public void productMenuAction()
        {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            HotelProductMenuFragment fragment = new HotelProductMenuFragment();

            fragment.setDataCall(hotelDialogInterface);

            Bundle bundle = new Bundle();

            bundle.putString("goodsType",goodsType.getGoodsTypeCode());

            bundle.putString("menuId",item.getPolicysId()+"");

            bundle.putBoolean("goodsComboFlag",true);

            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.contentll, fragment)
                    .commit();
        }
    };

}
