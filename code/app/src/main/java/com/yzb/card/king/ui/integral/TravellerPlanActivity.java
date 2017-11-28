package com.yzb.card.king.ui.integral;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.ExchangeIntegralListBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.popup.TravellerPlanPopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.integral.presenter.IntegralExchangePresenter;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 类  名：旅客计划
 * 作  者：Li Yubing
 * 日  期：2016/6/21
 * 描  述：
 */
@ContentView(R.layout.activity_traveller_plan_activity)
public class TravellerPlanActivity extends BaseActivity {
    //控件
    @ViewInject(R.id.titleName)
    private TextView titleName;

    @ViewInject(R.id.rlVip)
    private RelativeLayout rlVip;

    @ViewInject(R.id.llTwo)
    private LinearLayout llTwo;

    @ViewInject(R.id.ivImage)
    private ImageView ivImage;

    @ViewInject(R.id.tvName)
    private TextView tvName;

    @ViewInject(R.id.etVipNumber)
    private EditText etVipNumber;

    @ViewInject(R.id.llParent)
    private LinearLayout llParent;

    private ExchangeIntegralListBean travellerPlanBean = null;

    private String airlineId;

    private IntegralExchangePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new IntegralExchangePresenter(null);

        airlineId = getIntent().getStringExtra("airlineId");

        initView();

        initData();
    }

    private void initData() {


        presenter.sendFrequentPassengerPlanRequest(airlineId,this);

    }


    private void initView() {

        titleName.setText(R.string.integral_title_travler_plan);

        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        LinearLayout.LayoutParams rlVipLp = (LinearLayout.LayoutParams) rlVip.getLayoutParams();

        rlVipLp.topMargin = screenHeight * 704 / 1921;

        rlVip.setLayoutParams(rlVipLp);

        LinearLayout.LayoutParams llTwoLp = (LinearLayout.LayoutParams) llTwo.getLayoutParams();

        llTwoLp.topMargin = screenHeight * 364 / 1921;

        llTwo.setLayoutParams(llTwoLp);

        llParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AppUtils.hideSoftWindow(TravellerPlanActivity.this,etVipNumber);
                return false;
            }
        });
    }

    @Event(R.id.llTravellerPlanArrow)
    private void llTravellerPlanArrow(View v) {

        TravellerPlanPopup allCustomerPopup = new TravellerPlanPopup((View) llTwo, TravellerPlanActivity.this, new
                TravellerPlanPopup.PriorityListener() {


            @Override
            public void refreshPriorityUI(ExchangeIntegralListBean bean) {

                travellerPlanBean = bean;

                tvName.setText(bean.getPlanName());

                if (!TextUtils.isEmpty(bean.getImageCode())) {
                    x.image().bind(ivImage,ServiceDispatcher.getImageUrl(bean.getImageCode()));
                }

            }
        });

        allCustomerPopup.create(airlineId);

    }

    public void travelerAction(View view) {

        if (travellerPlanBean != null) {

            Intent in = new Intent();
            in.putExtra("data", travellerPlanBean);
            setResult(1001, in);
            finish();

        } else {

            ToastUtil.i(this, "请选择旅客计划");
        }


    }

}
