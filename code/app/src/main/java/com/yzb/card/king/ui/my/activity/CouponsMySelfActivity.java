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
public class CouponsMySelfActivity extends BaseActivity  {


    private YouhuiquanFragment youhuiquanFragment;

    private DaijinquanFragment daijinquanFragment;

    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initFragment();

    }

    private void initFragment() {

        youhuiquanFragment = YouhuiquanFragment.getInstance("youhuiquan");

        daijinquanFragment = DaijinquanFragment.getInstance("daijinquan");

        if (type == 1) {

            sendYouhuiquanClick();

        } else if (type == 2) {

            receiveDaijinquanClick();
        }
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

        if (getIntent().hasExtra("titleName")) {

            setTitleNmae(getIntent().getStringExtra("titleName"));

            type = getIntent().getIntExtra("type", 1);

        } else {

            setTitleNmae("我的券包");
        }


    }

}