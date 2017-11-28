package com.yzb.card.king.ui.integral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.integral.presenter.IntegralExchangePresenter;
import com.yzb.card.king.util.ViewUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 类  名：积分兑换
 * 作  者：Li Yubing
 * 日  期：2016/6/20
 * 描  述：
 */
@ContentView(R.layout.activty_integral_exchange)
public class IntegralExchangeActivity extends BaseActivity {

    //控件
    @ViewInject(R.id.titleName)
    private TextView titleName;

    @ViewInject(R.id.slideShowView1)
    private SlideShow1ItemView slideShowView1;

    //数据
    private float slide1_whrate = 432 / 1080.0f;

    private IntegralExchangePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        presenter = new IntegralExchangePresenter(null);

        presenter.sendUserIntegralRequest(this);

        initView();

    }

    private void initView()
    {

        slideShowView1.setParam(AppConstant.exchange_type_parentid, selectedCity.cityId,"1005");
        int  screenWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();
        ViewUtil.set(slideShowView1, screenWith, (int) (screenWith *
                slide1_whrate + 0.5));

        titleName.setText(R.string.pay_jfdh);
    }

    @Event(R.id.rlOne)
    private void rlOne(View v)
    {

        if (presenter.chechLogin(this)) {

            Intent oneIt = new Intent(this, ExchangeMileageTableActivity.class);

            startActivity(oneIt);
        }

    }

    @Event(R.id.rlTwo)
    private void rlTwo(View v)
    {

        if (presenter.chechLogin(this)) {

            Intent twoIt = new Intent(this, ExchangeYouka.class);

            startActivity(twoIt);
        }
    }

    @Event(R.id.rlThree)
    private void rlThree(View v)
    {
        if (presenter.chechLogin(this)) {

            Intent threeIt = new Intent(this, ExchangePhoneMoney.class);

            startActivity(threeIt);
        }

    }

    @Event(R.id.rlFour)
    private void rlFour(View v)
    {

        if (presenter.chechLogin(this)) {

            Intent fourIt = new Intent(this, ExchangeFlowActivity.class);

            startActivity(fourIt);
        }

    }

    @Event(R.id.rlFive)
    private void rlFive(View v)
    {

        if (presenter.chechLogin(this)) {

            Intent fiveIt = new Intent(this, ExchangeMoneyActivity.class);

            startActivity(fiveIt);
        }

    }
}
