package com.yzb.card.king.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.TaxiCouponBean;
import com.yzb.card.king.bean.order.TaxiOrderInfoBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.appwidget.PlaneTicketsView;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;

import java.util.List;

/**
 * Created by Timmy on 16/7/26.
 */
public class TaxiTicketsDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi__tickets_detail);
        initData();
    }

    private void initData() {
        final String orderId = getIntent().getStringExtra("orderId");
        if (StringUtils.isEmpty(orderId)) {
            return;
        }
    }

    private void initView(List<TaxiCouponBean> coupons, TaxiOrderInfoBean taxiOrderInfoBean) {
        TextView orderAmount = (TextView) findViewById(R.id.tv_order_amount);
        orderAmount.setText(taxiOrderInfoBean.getOrderAmount());
        TextView carType = (TextView) findViewById(R.id.tv_car_type);
        carType.setText(taxiOrderInfoBean.getCarType());
        TextView customerCount = (TextView) findViewById(R.id.luggate_total);
        customerCount.setText("x" + taxiOrderInfoBean.getCustomerCount());
        TextView luggateCount = (TextView) findViewById(R.id.luggate_total);
        luggateCount.setText(taxiOrderInfoBean.getLuggageCount());
        TextView arrivalsTime = (TextView) findViewById(R.id.arrivals_time);
        arrivalsTime.setText(taxiOrderInfoBean.getToolName() + Utils.toData(taxiOrderInfoBean.getOrderTime(), 3));
        PlaneTicketsView planeView = (PlaneTicketsView) findViewById(R.id.plane_view);
        int padding = UiUtils.dp2px(10);
        int textSize = UiUtils.sp2px(this, 18);
        planeView.init(0, 0, taxiOrderInfoBean.getStartAddr(), taxiOrderInfoBean.getEndAddr(), "", 0, padding, 0, padding, padding, textSize);
        TextView couponAmount = (TextView) findViewById(R.id.coupon_price);
        TextView coupon = (TextView) findViewById(R.id.tv_coupon);
        if (coupons != null && coupons.size() > 0) {
            TaxiCouponBean taxiCouponBean = coupons.get(0);
            coupon.setText(taxiCouponBean.getCouponName());
            couponAmount.setText(taxiCouponBean.getAmount());
        }
        TextView contactsPerson = (TextView) findViewById(R.id.tv_taxi_person);
        String booking = getString(R.string.booking_car);
        booking = String.format(booking, taxiOrderInfoBean.getContactsName(), taxiOrderInfoBean.getContactsMobile());
        contactsPerson.setText(booking);
    }

    public void goBack(View view) {
        finish();
    }
}
