package com.yzb.card.king.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.BusOrderInfoBean;
import com.yzb.card.king.bean.order.GuestBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.Utils;

import java.util.List;

/**
 * Created by Timmy on 16/7/26.
 */
public class BusTicketsDetaildActivity extends BaseActivity
{
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_tickets_detail);

        initData();
    }

    public void initData() {
        mGson = new Gson();
        final String orderId = getIntent().getStringExtra("orderId");
        if (StringUtils.isEmpty(orderId)) {
            return;
        }
    }

    public void initView(List<GuestBean> guests, BusOrderInfoBean busOrderInfoBean) {
        if (guests == null || guests.size() == 0 || busOrderInfoBean == null) {
            return;
        }
        GuestBean guestBean = guests.get(0);
        TextView startDate = (TextView) findViewById(R.id.tv_start_date);
        String departure = getString(R.string.departure);
        departure = String.format(departure, Utils.toData(busOrderInfoBean.getStartTime(), 1));
        startDate.setText(departure);
        TextView startCity = (TextView) findViewById(R.id.tv_start_city);
        startCity.setText(busOrderInfoBean.getStartCityName());
        TextView endCity = (TextView) findViewById(R.id.tv_end_city);
        endCity.setText(busOrderInfoBean.getEndCityName());
        TextView startStation = (TextView) findViewById(R.id.tv_start_station);
        startStation.setText(busOrderInfoBean.getStartStation());
        TextView endStation = (TextView) findViewById(R.id.tv_end_station);
        endStation.setText(busOrderInfoBean.getEndStation());
        TextView guestName = (TextView) findViewById(R.id.tv_guest_name);
        guestName.setText(guestBean.getGuestName());
        TextView idCard = (TextView) findViewById(R.id.id_card);
        idCard.setText(guestBean.getGuestIDCard());
    }

    public void goBack(View view) {
        finish();
    }
}
