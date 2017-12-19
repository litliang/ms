package com.yzb.card.king.ui.hotel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.bean.hotel.HotelProductObjectBean;
import com.yzb.card.king.bean.hotel.HotelRoomComboInfoBean;
import com.yzb.card.king.bean.hotel.HotelRoomInfoBean;
import com.yzb.card.king.ui.appwidget.SpecHeightGridView;
import com.yzb.card.king.ui.appwidget.appdialog.HotelProductRoomInfoFragmentDialog;
import com.yzb.card.king.ui.hotel.adapter.HotelInfoFacitityAdapter;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * 类  名：房间信息碎片
 * 作  者：Li Yubing
 * 日  期：2017/8/8
 * 描  述：
 */
public class HotelRoomInfoFragment extends Fragment implements View.OnClickListener {

    private SpecHeightGridView gvFactivity;

    private HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_hotel_room_combo_info, null);

        Bundle bundle = getArguments();

        HotelRoomComboInfoBean hotelRoomComboInfoBean = (HotelRoomComboInfoBean) bundle.get("data");

        TextView tvRoomNameInfo = (TextView) view.findViewById(R.id.tvRoomNameInfo);

        HotelRoomInfoBean hotelRoomInfoBean = hotelRoomComboInfoBean.getRoomInfo();

        tvRoomNameInfo.setText(hotelRoomInfoBean.getRoomsName() + " (" + hotelRoomComboInfoBean.getMealTypeDesc() + ")");

        TextView tvBedTypeMessage = (TextView) view.findViewById(R.id.tvBedTypeMessage);

        tvBedTypeMessage.setText(hotelRoomInfoBean.getBedTypeDesc());

        TextView roomAr = (TextView) view.findViewById(R.id.roomAr);

        roomAr.setText("面积：" + hotelRoomInfoBean.getAreaDesc());

        gvFactivity = (SpecHeightGridView) view.findViewById(R.id.gvFactivity);

        List<HotelRoomInfoBean.RoomService>  roomServiceList =  hotelRoomComboInfoBean.getRoomInfo().getBaseServiceList();

        HotelInfoFacitityAdapter hotelFacitityAdapter = new HotelInfoFacitityAdapter(inflater,roomServiceList);

        gvFactivity.setAdapter(hotelFacitityAdapter);

        view.findViewById(R.id.ivArrow).setOnClickListener(this);

        FavInfoBean hotelProductPre = hotelRoomComboInfoBean.getDisMap();

        LinearLayout llLifeStagen = (LinearLayout) view.findViewById(R.id.llLifeStage);
        llLifeStagen.setOnClickListener(this);

        String stageStatus = hotelProductPre.getStageStatus();

        if ("1".equals(stageStatus)) {

            llLifeStagen.setVisibility(View.VISIBLE);

            TextView tvBankStageDesc = (TextView) view.findViewById(R.id.tvBankStageDesc);

            tvBankStageDesc.setText(hotelProductPre.getStageDesc());

        } else {

            llLifeStagen.setVisibility(View.GONE);
        }

        LinearLayout llSuperGive = (LinearLayout) view.findViewById(R.id.llSuperGive);

        String giveContent = hotelRoomComboInfoBean.getGiveContent();

        if (!TextUtils.isEmpty(giveContent)) {

            TextView tvSuperGiveContent = (TextView) view.findViewById(R.id.tvSuperGiveContent);

            tvSuperGiveContent.setText(giveContent);

            llSuperGive.setVisibility(View.VISIBLE);
        } else {

            llSuperGive.setVisibility(View.GONE);
        }

        LinearLayout llBankFavorable = (LinearLayout) view.findViewById(R.id.llBankFavorable);

        llBankFavorable.setOnClickListener(this);

        if ("1".equals(hotelProductPre.getBankStatus())) {

            llBankFavorable.setVisibility(View.VISIBLE);

            TextView tvBankFavDesc = (TextView) view.findViewById(R.id.tvBankFavDesc);

            tvBankFavDesc.setText(hotelProductPre.getBankDesc());
        } else {

            llBankFavorable.setVisibility(View.GONE);
        }

        LinearLayout llReturnMoney = (LinearLayout) view.findViewById(R.id.llReturnMoney);

        if ("1".equals(hotelProductPre.getCashbackStatus())) {

            llReturnMoney.setVisibility(View.VISIBLE);
        } else {

            llReturnMoney.setVisibility(View.GONE);
        }

        LinearLayout llGetCoupon = (LinearLayout) view.findViewById(R.id.llGetCoupon);

        llGetCoupon.setOnClickListener(this);

        if ("1".equals(hotelProductPre.getCouponStatus())) {//优惠券

            llGetCoupon.setVisibility(View.VISIBLE);
        } else {

            llGetCoupon.setVisibility(View.GONE);
        }

        LinearLayout llGetCashCoupon = (LinearLayout) view.findViewById(R.id.llGetCashCoupon);

        llGetCashCoupon.setOnClickListener(this);

        if ("1".equals(hotelProductPre.getCouponStatus())) {//代金券

            llGetCashCoupon.setVisibility(View.VISIBLE);
        } else {

            llGetCashCoupon.setVisibility(View.GONE);
        }

        LinearLayout llMerchantIntegral = (LinearLayout) view.findViewById(R.id.llMerchantIntegral);

        llMerchantIntegral.setVisibility(View.GONE);

        LinearLayout llPayCancel = (LinearLayout) view.findViewById(R.id.llPayCancel);

        if(1==hotelRoomComboInfoBean.getPaymentType()){

            llPayCancel.setVisibility(View.GONE);

        }else {

            TextView tvCancelTypeMsg = (TextView) view.findViewById(R.id.tvCancelTypeMsg);

            llPayCancel.setVisibility(View.VISIBLE);
        }

        LinearLayout llRoomAddBedMeg = (LinearLayout) view.findViewById(R.id.llRoomAddBedMeg);

        if(TextUtils.isEmpty(hotelRoomInfoBean.getAddbedDesc())){

            llRoomAddBedMeg.setVisibility(View.GONE);
        }else {

            llRoomAddBedMeg.setVisibility(View.VISIBLE);

            TextView tvAddBed = (TextView) view.findViewById(R.id.tvAddBed);

            tvAddBed.setText(hotelRoomInfoBean.getAddbedDesc());
        }

        gvFactivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (dataCall != null)
                    dataCall.hotelServiceFacitity();
            }
        });

        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.ivArrow:
                if (dataCall != null)
                    dataCall.hotelServiceFacitity();

                break;

            case R.id.llGetCoupon://优惠券
                if (dataCall != null)
                    dataCall.getCouponAction();

                break;

            case R.id.llGetCashCoupon://代金券
                if (dataCall != null)
                    dataCall.getCashCouponAction();


                break;
            case R.id.llLifeStage:
                if (dataCall != null)
                    dataCall.hotelLifeStageAction();

                break;

            case R.id.llBankFavorable:
                if (dataCall != null)
                    dataCall.bankFavorableAction();

                break;
            default:
                break;
        }
    }

    public void setDataCall(HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall)
    {
        this.dataCall = dataCall;
    }


}
