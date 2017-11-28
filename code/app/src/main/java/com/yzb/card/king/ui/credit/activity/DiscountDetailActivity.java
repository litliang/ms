package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.credit.bean.DiscountInfo;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.luxury.activity.MsDetailActivity;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.util.DateUtil;

public class DiscountDetailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout headerLeft;
    private TextView tvCardOnline;
    private TextView tvGoConsume;
    private DiscountInfo discountInfo;
    private TextView tvCardName;
    private TextView tvBankName;
    private TextView tvCardType;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvCity;
    private TextView tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initData();

    }

    private void initView() {
        setContentView(R.layout.activity_discount_detail);
        setHeader(R.mipmap.icon_back_white, "优惠讯息");
        headerLeft = (LinearLayout) findViewById(R.id.headerLeft);

        tvCardOnline = (TextView) findViewById(R.id.tvCardOnline);
        tvGoConsume = (TextView) findViewById(R.id.tvGoConsume);

        tvCardName = (TextView) findViewById(R.id.tvCardName);
        tvBankName = (TextView) findViewById(R.id.tvBankName);
        tvCardType = (TextView) findViewById(R.id.tvCardType);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvDetail = (TextView) findViewById(R.id.tvDetail);

        headerLeft.setOnClickListener(this);
        tvCardOnline.setOnClickListener(this);
        tvGoConsume.setOnClickListener(this);
    }

    private void initData() {
        discountInfo = (DiscountInfo) getIntent().getSerializableExtra("data");

        /*=========2016.6.16添加判空  author:gengqiyun============*/
        if (discountInfo == null) {
            return;
        }

        String type = discountInfo.activityType;
        if("美食诱惑".equals(type)){
            tvGoConsume.setVisibility(View.VISIBLE);
        }

        tvCardName.setText(discountInfo.activityName);
        tvBankName.setText(discountInfo.bankName);
        tvCardType.setText(discountInfo.features);
        tvStartTime.setText(DateUtil.date2String(discountInfo.startDate, "yyyy/MM/dd"));
        tvEndTime.setText(DateUtil.date2String(discountInfo.endDate, "yyyy/MM/dd"));
        tvCity.setText(discountInfo.activityCityName);
        tvDetail.setText(Html.fromHtml(discountInfo.detail));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeft:
                finish();
                break;
            case R.id.tvCardOnline:
                startActivity(new Intent(this, CreditOnlineCardActivity.class));
                break;
            case R.id.tvGoConsume:
                String id = discountInfo.storeId;
                if(!TextUtils.isEmpty(id)){
                    Bundle bundle = new Bundle();
                    bundle.putString("id",id);
                   readyGoWithBundle(DiscountDetailActivity.this,MsDetailActivity.class,bundle);
                }else {
                    readyGo(DiscountDetailActivity.this,ChannelMainActivity.class);
                }
                break;
        }
    }


}
