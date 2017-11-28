package com.yzb.card.king.ui.ticket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
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

        view.findViewById(R.id.llGetCoupon).setOnClickListener(this);

        view.findViewById(R.id.llBankFavorable).setOnClickListener(this);

        view.findViewById(R.id.llLifeStage).setOnClickListener(this);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        TextView tvAgent = (TextView) view.findViewById(R.id.tvAgent);

       Bundle bundle = getArguments();

        FlightDetailBean.TicketPriceInfoBean bean = (FlightDetailBean.TicketPriceInfoBean) bundle.getSerializable("data");

        String storeName = bundle.getString("storeName");

        String flightNumber = bundle.getString("flightNumber");

        tvTitle.setText(storeName+flightNumber+bean.getQabinName());

        tvAgent.setText(bean.getAgent());

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
