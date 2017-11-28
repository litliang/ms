package com.yzb.card.king.ui.bonus.activity;

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

/**
 * 类 名： 红包收发记录
 * 作 者： gaolei
 * 日 期：2016年12月26日
 * 描 述：红包收发记录
 */
public class RedSendAndRecActivity extends BaseActivity implements View.OnClickListener {

    private RedBagSendFragment sendFragment;
    private RedBagReceiverFragment recevierFragment;
    private TextView receiver_txt;
    private TextView send_txt;

    private ImageView ivOne;
    private ImageView ivTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_send_and_rec);
        initView();
        initFragment();

    }

    private void initFragment() {
        sendFragment = RedBagSendFragment.getInstance("send");
        recevierFragment = RedBagReceiverFragment.getInstance("recevier");
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transation = manager.beginTransaction();
        transation.replace(R.id.red_sendorreceiver_framelayout, sendFragment);
        transation.commit();
    }

    private void initView() {

        receiver_txt = (TextView) findViewById(R.id.red_bag_receiver_txt);
        receiver_txt.setOnClickListener(this);
        send_txt = (TextView) findViewById(R.id.red_bag_send_txt);
        send_txt.setOnClickListener(this);

        ivOne = (ImageView) findViewById(R.id.ivOne);
        ivTwo = (ImageView) findViewById(R.id.ivTwo);

        send_txt.setTextColor(getResources().getColor(R.color.color_d8c07d));
        receiver_txt.setTextColor(getResources().getColor(R.color.color_333333));
        ivOne.setBackgroundColor(getResources().getColor(R.color.color_d8c07d));
        ivTwo.setBackgroundColor(getResources().getColor(R.color.white));
    }

    //发红包
    public void sendRedBagClick(View view) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transation = manager.beginTransaction();
        transation.replace(R.id.red_sendorreceiver_framelayout, sendFragment);
        transation.commit();
    }

    //收红包
    public void receiveRedBagClick(View view) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transation = manager.beginTransaction();
        transation.replace(R.id.red_sendorreceiver_framelayout, recevierFragment);
        transation.commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.red_bag_send_txt:
                send_txt.setTextColor(getResources().getColor(R.color.color_d8c07d));
                receiver_txt.setTextColor(getResources().getColor(R.color.color_333333));
                ivOne.setBackgroundColor(getResources().getColor(R.color.color_d8c07d));
                ivTwo.setBackgroundColor(getResources().getColor(R.color.white));
                sendRedBagClick(v);
                break;
            case R.id.red_bag_receiver_txt:
                send_txt.setTextColor(getResources().getColor(R.color.color_333333));
                receiver_txt.setTextColor(getResources().getColor(R.color.color_d8c07d));
                ivTwo.setBackgroundColor(getResources().getColor(R.color.color_d8c07d));
                ivOne.setBackgroundColor(getResources().getColor(R.color.white));
                receiveRedBagClick(v);
                break;

        }
    }
}
