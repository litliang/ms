package com.yzb.card.king.ui.order.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.ticket.activity.RefundTicketRuleActivity;
import com.yzb.card.king.ui.ticket.activity.TicketAgentActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 玉兵 on 2017/10/29.
 */

public class EndorseTicketInfoAdapter extends BaseListAdapter<TicketOrderDetBean.OrderInfoBean> implements View.OnClickListener {

    public EndorseTicketInfoAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        MyViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_endorse_ticket_info, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        final TicketOrderDetBean.OrderInfoBean bean = getItem(position);

        //原；
        List<TicketOrderDetBean.MyFlight> orignalFlights = bean.getRetFlight();

        TicketOrderDetBean.MyFlight myFlightOne = orignalFlights.get(0);

        long timeSeres = myFlightOne.getTimeSeres();

        String startDateStr = Utils.toData(timeSeres, 14);

        String startDateStr1 = DateUtil.getWeek(Utils.toData(timeSeres, 4));

        holder.flightDate.setText(startDateStr + "  周" + startDateStr1);

        holder.start_city.setText(myFlightOne.getDepCity());

        holder.end_city.setText(myFlightOne.getArrCity());

        holder.flightDate.setText(myFlightOne.getFlyingTime());

        holder.tv_flightNumber.setText(myFlightOne.getFlightNumber());

        holder.ll_discount.setTag(bean.getTicketpriceId());


        //新；
        List<TicketOrderDetBean.MyFlight> newFlight =  bean.getFlightList();

        TicketOrderDetBean.MyFlight myNewFlightOne = newFlight.get(0);

        long timeSeresOne = myNewFlightOne.getTimeSeres();

        String startDateStrOne = Utils.toData(timeSeresOne, 14);

        String startDateStr1One = DateUtil.getWeek(Utils.toData(timeSeresOne, 4));

        holder.flightDateOne.setText(startDateStrOne + "  周" + startDateStr1One);

        holder.start_cityOne.setText(myNewFlightOne.getDepCity());

        holder.end_cityOne.setText(myNewFlightOne.getArrCity());

        holder.tv_flightNumberOne.setText(myFlightOne.getFlightNumber());

        holder.flightDateOne.setText(myFlightOne.getFlyingTime());

        holder.ll_discountOne.setTag(bean.getTicketpriceId());


        holder.tvPiaohao.setText("票号："+bean.getNumber());

        holder.ll_discountOne.setOnClickListener(this);

        holder.ll_discount.setOnClickListener(this);

        StringBuffer sb = new StringBuffer();

        List<TicketOrderDetBean.TicketsListBean>  ticketsListBeenList = bean.getRetticketsList();

        int size = ticketsListBeenList.size();

        for(int i = 0 ; i< size ; i++){

            TicketOrderDetBean.TicketsListBean ticketsListBean = ticketsListBeenList.get(i);

            sb.append(ticketsListBean.getGuestName());

            if(i == size - 1){

            }else {

                sb.append(",");
            }
        }

        holder.tvChengjiRen.setText(sb);

        return convertView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ll_discount:

            case R.id.ll_discountOne:
                String priceId = (String) v.getTag();
                Intent intent = new Intent(mContext, RefundTicketRuleActivity.class);
                intent.putExtra("priceId", priceId);
                mContext.startActivity(intent);

                break;

        }
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.flightDate)
        public TextView flightDate;

        @ViewInject(R.id.start_city)
        public TextView start_city;

        @ViewInject(R.id.end_city)
        public TextView end_city;

        @ViewInject(R.id.tv_flightNumber)
        public TextView tv_flightNumber;

        @ViewInject(R.id.timeLength)
        public TextView timeLength;

        @ViewInject(R.id.ll_discount)
        public LinearLayout ll_discount;


        ////////////////////////////////////

        @ViewInject(R.id.flightDateOne)
        public TextView flightDateOne;

        @ViewInject(R.id.start_cityOne)
        public TextView start_cityOne;

        @ViewInject(R.id.end_cityOne)
        public TextView end_cityOne;

        @ViewInject(R.id.tv_flightNumberOne)
        public TextView tv_flightNumberOne;

        @ViewInject(R.id.timeLengthOne)
        public TextView timeLengthOne;

        @ViewInject(R.id.ll_discountOne)
        public LinearLayout ll_discountOne;

        //////////////////////

        @ViewInject(R.id.tvChengjiRen)
        public TextView tvChengjiRen;

        @ViewInject(R.id.tvPiaohao)
        public TextView tvPiaohao;



        public View root;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.root = itemView;
            x.view().inject(this, itemView);
        }
    }
}
