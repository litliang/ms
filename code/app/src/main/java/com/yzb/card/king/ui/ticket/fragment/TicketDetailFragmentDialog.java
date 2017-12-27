package com.yzb.card.king.ui.ticket.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
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
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.ticket.FlightDetailBean;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.fragment.BankFavorableFragment;
import com.yzb.card.king.ui.hotel.fragment.BankLifeStageFragment;
import com.yzb.card.king.ui.hotel.fragment.GetCouponFragment;
import com.yzb.card.king.util.Utils;

/**
 * 类  名：机票详情
 * 作  者：Li Yubing
 * 日  期：2017/9/22
 * 描  述：
 */
public class TicketDetailFragmentDialog  extends DialogFragment implements View.OnClickListener{


    private  AirTicketDetailFragment fragmentOne;

    private   Bundle bundle;

    private   FlightDetailBean.TicketPriceInfoBean bean;

    private Handler dialogHandler;

    private int position;

    private boolean ifShowTicketDetail;//true 展示机票详情，false 展示退改签

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
        View view = inflater.inflate(R.layout.view_fragment_dialog_ticket_detail_parent, container, false);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);

        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        bundle = getArguments();

         bean = (FlightDetailBean.TicketPriceInfoBean) bundle.getSerializable("data");

        position = bundle.getInt("position");

        TextView tvRoomComboPrice = (TextView) view.findViewById(R.id.tvRoomComboPrice);

        tvRoomComboPrice.setText(Utils.subZeroAndDot((bean.getFareAdult() + bean.getFueltaxAdult())+""));

        ifShowTicketDetail = bundle.getBoolean("ifShowTicketDetail");

        if(ifShowTicketDetail){

            fragmentOne = new AirTicketDetailFragment();

            fragmentOne.setArguments(bundle);

            fragmentOne.setParentDataCallAction(ticketDialogInterface);

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.contentll, fragmentOne)
                    .commit();
        }else {

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            TicketBackChangeFragment twoFragment = new TicketBackChangeFragment();

            twoFragment.setTickeDetaiDataCall(ticketDialogInterface);

            twoFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.contentll, twoFragment).commit();

        }

        return  view;
    }


    private TicketDialogInterface ticketDialogInterface = new TicketDialogInterface() {
        @Override
        public void backAction( int type)
        {
            if(type == 0 ){
                dismiss();
            }else {

                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.contentll, fragmentOne)
                        .commit();
            }

        }

        @Override
        public void getBackChangeAction()
        {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            TicketBackChangeFragment twoFragment = new TicketBackChangeFragment();

            twoFragment.setTickeDetaiDataCall(ticketDialogInterface);

            twoFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.contentll, twoFragment).commit();
        }

        @Override
        public void getCouponAction()
        {

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            GetCouponFragment twoFragment = new GetCouponFragment();

            Bundle bundle = new Bundle();


            int issuePlatform =  bundle.getInt("issuePlatform");

            int industryId =  bundle.getInt("industryId");

            long shopId =  bundle.getLong("shopId");

            long storeId =  bundle.getLong("storeId");

            long goodsId =  bundle.getLong("goodsId");

            twoFragment.setArguments(bundle);

            twoFragment.setTicketDetailDataCall(ticketDialogInterface);

            fragmentTransaction.replace(R.id.contentll, twoFragment).commit();

        }

        @Override
        public void lifeStageAction()
        {

            int industryId =  bundle.getInt("industryId");

            long shopId =  bundle.getLong("shopId");

            long goodsId =  bundle.getLong("goodsId");

            GoldTicketParam goldTicketParam = new GoldTicketParam();

            goldTicketParam.setIndustryId(industryId);

            goldTicketParam.setShopId(shopId);

            goldTicketParam.setGoodsId(goodsId);

            BankLifeStageFragment twoFragment = new BankLifeStageFragment();

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            Bundle bundle = new Bundle();

            bundle.putSerializable("goldTicketParam",goldTicketParam);

            bundle.putInt("goodsType",-1);

            bundle.putDouble("productMoney",Double.parseDouble(Utils.subZeroAndDot((bean.getFareAdult() + bean.getFueltaxAdult())+"")));

            twoFragment.setArguments(bundle);

            twoFragment.setTicketDetailDataCall(ticketDialogInterface);

            fragmentTransaction.replace(R.id.contentll, twoFragment).commit();
        }

        @Override
        public void bankFavorableAction()
        {

            String startDate = bundle.getString("startDate");

            int industryId =  bundle.getInt("industryId");

            long shopId =  bundle.getLong("shopId");

            long goodsId =  bundle.getLong("goodsId");

            GoldTicketParam goldTicketParam = new GoldTicketParam();

            goldTicketParam.setIndustryId(industryId);

            goldTicketParam.setShopId(shopId);

            goldTicketParam.setGoodsId(goodsId);

            BankFavorableFragment twoFragment = new BankFavorableFragment();

            Bundle bundle = new Bundle();

            bundle.putSerializable("goldTicketParam",goldTicketParam);

            bundle.putInt("goodsType",-1);

            bundle.putString("startDate",startDate);
           // bundle.putString("startDate",startDate);

            twoFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            twoFragment.setTicketDetailDataCall(ticketDialogInterface);

            fragmentTransaction.replace(R.id.contentll, twoFragment).commit();
        }
    };

    @Override
    public void onClick(View v)
    {
        switch ( v.getId()){

            case R.id.tvConfirm:

                if(dialogHandler != null){

                    dialogHandler.sendEmptyMessage(position);
                }

                break;

            default:
                break;

        }
    }

    public void setDialogHandler(Handler dialogHandler)
    {
        this.dialogHandler = dialogHandler;
    }


    public interface TicketDialogInterface {
        /**
         * 返回到上一级碎片
         */
        public void backAction(int type);
        /**
         * 退改详情
         */
        public void getBackChangeAction();

        /**
         * 领券中心（代金券）
         */
        public void getCouponAction();
        /**
         * 生活分期
         */
        public void lifeStageAction();

        /**
         * 银行优惠
         */
        public void bankFavorableAction();


    }

}
