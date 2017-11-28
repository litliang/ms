package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.hotel.HotelRoomComboInfoBean;
import com.yzb.card.king.bean.hotel.HotelRoomInfoBean;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.hotel.activtiy.HotelRoomOrderActivity;
import com.yzb.card.king.ui.hotel.fragment.BankFavorableFragment;
import com.yzb.card.king.ui.hotel.fragment.BankLifeStageFragment;
import com.yzb.card.king.ui.hotel.fragment.HotelRoomFragment;
import com.yzb.card.king.ui.hotel.fragment.HotelRoomInfoFragment;
import com.yzb.card.king.ui.hotel.fragment.ServiceFacitityFragment;
import com.yzb.card.king.ui.hotel.fragment.GetCouponFragment;
import com.yzb.card.king.ui.manage.UserManager;

/**
 * 类  名：酒店房间信息dialog
 * 作  者：Li Yubing
 * 日  期：2017/8/8
 * 描  述：
 */
public class HotelProductRoomInfoFragmentDialog extends DialogFragment implements View.OnClickListener {

    private HotelRoomInfoFragment fragmentOne;

    private   HotelRoomComboInfoBean hotelRoomComboInfoBean;

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
        View view = inflater.inflate(R.layout.view_fragment_dialog, container, false);
        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        Bundle bundle =   getArguments();

         hotelRoomComboInfoBean = (HotelRoomComboInfoBean) bundle.get("data");

        //
        TextView tvPayTypeMsg = (TextView) view.findViewById(R.id.tvPayTypeMsg);

        int payType = hotelRoomComboInfoBean.getPaymentType();

        if(1 == payType){
            tvPayTypeMsg.setText("到店付");
        }else  if(2==payType){
            tvPayTypeMsg.setText("在线支付");
        }else  if(3==payType){
            tvPayTypeMsg.setText("担保支付");
        }

        TextView tvRoomComboPrice = (TextView) view.findViewById(R.id.tvRoomComboPrice);

        String leftStatus = hotelRoomComboInfoBean.getDisMap().getLeftStatus();

        TextView tvRturnMoney = (TextView) view.findViewById(R.id.tvRturnMoney);

        if("1".equals(leftStatus)){

            tvRoomComboPrice.setText(hotelRoomComboInfoBean.getLeftPrice()+"");

            tvRturnMoney.setVisibility(View.GONE);

        }else {

            tvRoomComboPrice.setText(hotelRoomComboInfoBean.getPolicysPrice()+"");

            String chshbackStatus =hotelRoomComboInfoBean.getDisMap().getCashbackStatus();

            if("1".equals(chshbackStatus)){
                tvRturnMoney.setVisibility(View.VISIBLE);

                tvRturnMoney.setText("¥"+hotelRoomComboInfoBean.getPolicysPrice()+"返"+hotelRoomComboInfoBean.getBackPrice());

            }else {
                tvRturnMoney.setVisibility(View.GONE);
            }
        }

        fragmentOne = new HotelRoomInfoFragment();

        fragmentOne.setArguments(bundle);

        fragmentOne.setDataCall(hotelDialogInterface);

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
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvConfirm:

                //检查是否登录
                if (UserManager.getInstance().isLogin()) {

                    Intent intent = new Intent(getContext(), HotelRoomOrderActivity.class);

                    intent.putExtra("HotelRoomComboInfoBean",hotelRoomComboInfoBean);

                    startActivity(intent);

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

    private HotelDialogInterface hotelDialogInterface = new HotelDialogInterface() {
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
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            ServiceFacitityFragment twoFragment = new ServiceFacitityFragment();

            HotelRoomInfoBean hotelRoomInfoBean = hotelRoomComboInfoBean.getRoomInfo();

            Bundle bundle = new Bundle();

            bundle.putSerializable("roomInfoData",hotelRoomInfoBean);

            twoFragment.setArguments(bundle);

            twoFragment.setDataCall(hotelDialogInterface);

            fragmentTransaction.replace(R.id.contentll, twoFragment).commit();
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

            bundle.putLong("shopId",hotelRoomComboInfoBean.getShopId());

            bundle.putLong("storeId",hotelRoomComboInfoBean.getHotelId());

            bundle.putLong("goodsId",hotelRoomComboInfoBean.getRoomInfo().getRoomsId());

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

            long shopId = hotelRoomComboInfoBean.getShopId();

            long storeId = hotelRoomComboInfoBean.getHotelId();

            int goodsType =Integer.parseInt( hotelRoomComboInfoBean.getGoodsType());

            long goodsId = hotelRoomComboInfoBean.getRoomInfo().getRoomsId();

            double money = hotelRoomComboInfoBean.getPolicysPrice();

            GoldTicketParam goldTicketParam = new GoldTicketParam();

            goldTicketParam.setIndustryId(industryId);

            goldTicketParam.setShopId(shopId);

            goldTicketParam.setStoreId(storeId);

            goldTicketParam.setGoodsId(goodsId);

            bundle.putDouble("productMoney",money);

            bundle.putInt("goodsType",goodsType);

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

            long shopId = hotelRoomComboInfoBean.getShopId();

            long storeId = hotelRoomComboInfoBean.getHotelId();

            int goodsType =Integer.parseInt( hotelRoomComboInfoBean.getGoodsType());

            long goodsId = hotelRoomComboInfoBean.getRoomInfo().getRoomsId();

            GoldTicketParam goldTicketParam = new GoldTicketParam();

            goldTicketParam.setIndustryId(industryId);

            goldTicketParam.setShopId(shopId);

            goldTicketParam.setStoreId(storeId);

            goldTicketParam.setGoodsId(goodsId);

            bundle.putInt("goodsType",goodsType);

            bundle.putSerializable("goldTicketParam",goldTicketParam);

            twoFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            twoFragment.setDataCall(hotelDialogInterface);

            fragmentTransaction.replace(R.id.contentll, twoFragment).commit();
        }

        @Override
        public void productMenuAction()
        {

        }
    };



    public interface HotelDialogInterface {
        /**
         * 返回到上一级碎片
         */
        public void backAction();

        /**
         * 酒店服务设施
         */
        public void hotelServiceFacitity();

        /**
         * 领券中心
         */
        public void getCouponAction();
        /**
         * 生活分期
         */
        public void hotelLifeStageAction();

        /**
         * 银行优惠
         */
        public void bankFavorableAction();

        /**
         * 菜单
         */
        public void productMenuAction();
    }
}
