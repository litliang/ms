package com.yzb.card.king.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.GuestBean;
import com.yzb.card.king.bean.order.TrainOrderInfoBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.Utils;

import java.util.List;

/**
 * 火车票订单
 * Created by Timmy on 16/7/23.
 */
public class TrainTicketsDetailActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_tickets_detail);
        initData();
    }

    private void initData() {
        final String orderId = getIntent().getStringExtra("orderId");
        if (StringUtils.isEmpty(orderId)) {
            return;
        }
    }

    private void initView(List<GuestBean> guests, TrainOrderInfoBean trainOrderInfoBean) {
        if (guests == null || guests.size() == 0 || trainOrderInfoBean == null) {
            return;
        }
        GuestBean guestBean = guests.get(0);
        TextView startCity = (TextView) findViewById(R.id.tv_start_city);
        startCity.setText(trainOrderInfoBean.getStartCityName());
        TextView endCity = (TextView) findViewById(R.id.tv_end_city);
        endCity.setText(trainOrderInfoBean.getEndCityName());
        TextView startTime = (TextView) findViewById(R.id.tv_start_time);
        startTime.setText(Utils.toData(trainOrderInfoBean.getStartTime(), 3));
        TextView endTime = (TextView) findViewById(R.id.tv_end_time);
        endTime.setText(Utils.toData(trainOrderInfoBean.getEndTime(), 3));
        TextView startDate = (TextView) findViewById(R.id.tv_start_date);
        startDate.setText(Utils.toData(trainOrderInfoBean.getStartTime(), 14));
        TextView endDate = (TextView) findViewById(R.id.tv_end_date);
        endDate.setText(Utils.toData(trainOrderInfoBean.getEndTime(), 14));
        TextView guestName = (TextView) findViewById(R.id.tv_guest_name);
        guestName.setText(guestBean.getGuestName());
        TextView guestId = (TextView) findViewById(R.id.tv_guest_id);
        guestId.setText(getString(R.string.id_card) + guestBean.getGuestIDCard());
        TextView seatNum = (TextView) findViewById(R.id.seat_number);
        String seat = getResources().getString(R.string.seat);
        seat = String.format(seat, guestBean.getCarriage(), guestBean.getSeatNo());
        seatNum.setText(seat);
        TextView ticketCheck = (TextView) findViewById(R.id.tv_check_port);
        String checkPort = getString(R.string.check_port);
        checkPort = String.format(checkPort, guestBean.getTicketCheck());
        ticketCheck.setText(checkPort);
        TextView ticketNo = (TextView) findViewById(R.id.tv_tickets_no);
        String checkIn = getString(R.string.check_in);
        checkIn = String.format(checkIn, trainOrderInfoBean.getToolNumber());
        ticketNo.setText(checkIn);
        TextView siteTv = (TextView) findViewById(R.id.site_tv);
        siteTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.site_tv) {    //经停信息

        }
    }

    public void goBack(View view) {
        finish();
    }
}