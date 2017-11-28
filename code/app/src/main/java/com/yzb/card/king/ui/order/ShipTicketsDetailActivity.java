package com.yzb.card.king.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.GuestBean;
import com.yzb.card.king.bean.order.ShipOrderInfoBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.Utils;

import java.util.List;

/**
 * Created by Timmy on 16/7/26.
 */
public class ShipTicketsDetailActivity extends BaseActivity {
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_tickets_detail);
        initData();
    }

    private void initData() {
        mGson = new Gson();
        final String orderId = getIntent().getStringExtra("orderId");
        if (StringUtils.isEmpty(orderId)) {
            return;
        }
    }

    public void initView(List<GuestBean> guests, ShipOrderInfoBean shipOrderInfoBean) {
        if (guests == null || guests.size() == 0 || shipOrderInfoBean == null) {
            return;
        }
        GuestBean guestBean = guests.get(0);
        TextView startCity = (TextView) findViewById(R.id.tv_start_city);
        startCity.setText(shipOrderInfoBean.getStartCityName());
        TextView endCity = (TextView) findViewById(R.id.tv_end_city);
        endCity.setText(shipOrderInfoBean.getEndCityName());
        TextView startTime = (TextView) findViewById(R.id.tv_start_time);
        startTime.setText(Utils.toData(shipOrderInfoBean.getStartTime(), 3));
        TextView endTime = (TextView) findViewById(R.id.tv_end_time);
        endTime.setText(Utils.toData(shipOrderInfoBean.getEndTime(), 3));
        TextView startDate = (TextView) findViewById(R.id.tv_start_date);
        startDate.setText(Utils.toData(shipOrderInfoBean.getStartTime(), 4));
        TextView endDate = (TextView) findViewById(R.id.tv_end_date);
        endDate.setText(Utils.toData(shipOrderInfoBean.getEndTime(), 4));
        TextView toolName = (TextView) findViewById(R.id.tv_tool_name);
        toolName.setText(shipOrderInfoBean.getToolName());
        TextView toolNum = (TextView) findViewById(R.id.tv_tool_num);
        toolNum.setText(shipOrderInfoBean.getToolNumber());
        TextView guestName = (TextView) findViewById(R.id.tv_guest_name);
        guestName.setText(guestBean.getGuestName());
        TextView idCard = (TextView) findViewById(R.id.tv_id_card);
        idCard.setText(guestBean.getGuestIDCard());
        TextView contactsMobile = (TextView) findViewById(R.id.tv_contacts_mobile);
        contactsMobile.setText(shipOrderInfoBean.getContactsMobile());
    }

    public void goBack(View view) {
        finish();
    }
}
