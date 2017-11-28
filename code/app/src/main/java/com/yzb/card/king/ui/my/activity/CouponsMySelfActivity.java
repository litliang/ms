package com.yzb.card.king.ui.my.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.bonus.fragment.RedBagReceiverFragment;
import com.yzb.card.king.ui.bonus.fragment.RedBagSendFragment;
import com.yzb.card.king.ui.my.fragment.DaijinquanFragment;
import com.yzb.card.king.ui.my.fragment.YouhuiquanFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 我的券包
 * Created by 玉兵 on 2017/10/29.
 */
@ContentView(R.layout.activity_my_coupons)
public class CouponsMySelfActivity extends BaseActivity implements View.OnClickListener{


    @ViewInject(R.id.tvDaijinquan)
    private TextView tvDaijinquan;

    @ViewInject(R.id.tvYouhuiquan)
    private TextView tvYouhuiquan;

    @ViewInject(R.id.ivOne)
    private ImageView ivOne;

    @ViewInject(R.id.ivTwo)
    private ImageView ivTwo;

    private YouhuiquanFragment youhuiquanFragment;

    private DaijinquanFragment daijinquanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initFragment();

    }

    private void initFragment() {

        youhuiquanFragment = YouhuiquanFragment.getInstance("youhuiquan");

        daijinquanFragment = DaijinquanFragment.getInstance("daijinquan");

        FragmentManager manager = getFragmentManager();

        FragmentTransaction transation = manager.beginTransaction();

        transation.replace(R.id.flOne, youhuiquanFragment);

        transation.commit();
    }


    //优惠券
    public void sendYouhuiquanClick() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transation = manager.beginTransaction();
        transation.replace(R.id.flOne, youhuiquanFragment);
        transation.commit();
    }

    //代金券
    public void receiveDaijinquanClick() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transation = manager.beginTransaction();
        transation.replace(R.id.flOne, daijinquanFragment);
        transation.commit();
    }

    private void initView() {

        setTitleNmae("我的券包");

        tvDaijinquan.setOnClickListener(this);

        tvYouhuiquan.setOnClickListener(this);

        tvYouhuiquan.setTextColor(getResources().getColor(R.color.color_970105));
        tvDaijinquan.setTextColor(getResources().getColor(R.color.color_2a2a2a));
        ivOne.setBackgroundColor(getResources().getColor(R.color.color_970105));
        ivTwo.setBackgroundColor(getResources().getColor(R.color.white));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tvYouhuiquan:

                tvYouhuiquan.setTextColor(getResources().getColor(R.color.color_970105));
                tvDaijinquan.setTextColor(getResources().getColor(R.color.color_2a2a2a));
                ivOne.setBackgroundColor(getResources().getColor(R.color.color_970105));
                ivTwo.setBackgroundColor(getResources().getColor(R.color.white));

                sendYouhuiquanClick();
                break;

            case R.id.tvDaijinquan:

                tvDaijinquan.setTextColor(getResources().getColor(R.color.color_970105));

                tvYouhuiquan.setTextColor(getResources().getColor(R.color.color_2a2a2a));

                ivTwo.setBackgroundColor(getResources().getColor(R.color.color_970105));

                ivOne.setBackgroundColor(getResources().getColor(R.color.white));

                receiveDaijinquanClick();

                break;


        }
    }
}
