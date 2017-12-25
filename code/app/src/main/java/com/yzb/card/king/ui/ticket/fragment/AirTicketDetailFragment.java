package com.yzb.card.king.ui.ticket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.bean.ticket.FlightDetailBean;

/**
 * 类  名：航空机票详情
 * 作  者：Li Yubing
 * 日  期：2017/9/22
 * 描  述：
 */
public class AirTicketDetailFragment extends Fragment implements View.OnClickListener{

    private TicketDetailFragmentDialog.TicketDialogInterface parentDataCallAction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.view_air_ticket_detail, null);

        view.findViewById(R.id.llLeft).setOnClickListener(this);

        view.findViewById(R.id.llBackChange).setOnClickListener(this);




        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        TextView tvAgent = (TextView) view.findViewById(R.id.tvAgent);

       Bundle bundle = getArguments();

        FlightDetailBean.TicketPriceInfoBean bean = (FlightDetailBean.TicketPriceInfoBean) bundle.getSerializable("data");

        String storeName = bundle.getString("storeName");

        String flightNumber = bundle.getString("flightNumber");

        tvTitle.setText(storeName+flightNumber+bean.getQabinName());

        tvAgent.setText(bean.getAgent());

        FavInfoBean hotelProductPre = bean.getDisMap();
        //代金券
        LinearLayout llGetCoupon = (LinearLayout) view.findViewById(R.id.llGetCoupon);
        llGetCoupon .setOnClickListener(this);
        String  cashCouponStatus =  hotelProductPre.getCashCouponStatus();

        if("1".equals(cashCouponStatus)){

            llGetCoupon.setVisibility(View.VISIBLE);
        }else {
            llGetCoupon.setVisibility(View.GONE);
        }

        //生活分期
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

        //银行优惠
        LinearLayout llBankFavorable = (LinearLayout) view.findViewById(R.id.llBankFavorable);

        llBankFavorable.setOnClickListener(this);

        if ("1".equals(hotelProductPre.getBankStatus())) {

            llBankFavorable.setVisibility(View.VISIBLE);

            TextView tvBankFavDesc = (TextView) view.findViewById(R.id.tvBankFavDesc);

            tvBankFavDesc.setText(hotelProductPre.getBankDesc());
        } else {

            llBankFavorable.setVisibility(View.GONE);
        }

        return view;
    }


    public void setParentDataCallAction(TicketDetailFragmentDialog.TicketDialogInterface parentDataCallAction)
    {
        this.parentDataCallAction = parentDataCallAction;
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId()){

            case R.id.llLeft:

                parentDataCallAction.backAction(0);

                break;

            case R.id.llBackChange:

                parentDataCallAction.getBackChangeAction();

                break;

            case R.id.llGetCoupon:

                parentDataCallAction.getCouponAction();

                break;

            case R.id.llBankFavorable:

                parentDataCallAction.bankFavorableAction();
                break;

            case R.id.llLifeStage:

                parentDataCallAction.lifeStageAction();

                break;
            default:
                break;

        }


    }
}
