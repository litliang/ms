package com.yzb.card.king.ui.ticket.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.popup.CalendarPop;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.ui.ticket.fragment.PlaneMyConcernFragment;
import com.yzb.card.king.ui.ticket.fragment.PlaneNumFragment;
import com.yzb.card.king.ui.ticket.fragment.PlaneStarEndFragment;

import java.util.Date;


public class PlaneSeachActivity extends BaseActivity implements View.OnClickListener {

    private PlaneNumFragment planeNumFragment;

    private PlaneStarEndFragment planeStarEndFragment;

    private PlaneMyConcernFragment planeMyConcernFragment;

    private LinearLayout iv_back;

    private TextView hbh, qjd, mycoll;

    public static int currentIndex = 0;

    private FrameLayout content;

    private View lineOne,lineTwo,lineThree;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_seach);
        initViews(); //初始化界面，并设置3个tab的监听
        setTabSelection(currentIndex); //第一次启动时开启第0个tab

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        setTabSelection(currentIndex); //第一次启动时开启第0个tab
    }

    /*
      * 根据传入的index，来设置开启的tab页面
      * @param index
      */
    private void setTabSelection(int index)
    {
        //清理之前的所有状态
        clearSelection();
        //开启一个Fragment事务
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        //隐藏所有的fragment，防止有多个界面显示在界面上
        hideFragments(transaction);
        switch (index) {
            case 0:
                hbh.setTextColor(Color.parseColor("#44698e"));//#82858b
                lineOne.setVisibility(View.VISIBLE);
                //如果为空，则创建一个添加到界面上

                if (planeNumFragment == null) {

                    planeNumFragment = new PlaneNumFragment();

                    transaction.add(R.id.content, planeNumFragment);
                } else {
                    //如果不为空，则直接将它显示出来
                    transaction.show(planeNumFragment);
                }
                break;
            case 1:
                qjd.setTextColor(Color.parseColor("#44698e"));//#82858b
                lineTwo.setVisibility(View.VISIBLE);
                //如果为空，则创建一个添加到界面上
                if (planeStarEndFragment == null) {
                    planeStarEndFragment = new PlaneStarEndFragment();
                    transaction.add(R.id.content, planeStarEndFragment);
                } else {
                    //如果不为空，则直接将它显示出来
                    transaction.show(planeStarEndFragment);
                }
                break;
            case 2:
                mycoll.setTextColor(Color.parseColor("#44698e"));//#82858b
                lineThree.setVisibility(View.VISIBLE);
                //如果为空，则创建一个添加到界面上
                if (planeMyConcernFragment == null) {
                    planeMyConcernFragment = new PlaneMyConcernFragment();
                    transaction.add(R.id.content, planeMyConcernFragment);
                } else {
                    transaction.show(planeMyConcernFragment);
                }
                break;
        }
        transaction.commit();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        if (planeNumFragment != null) {
            transaction.remove(planeNumFragment);
        }
        if (planeStarEndFragment != null) {
            transaction.remove(planeStarEndFragment);
        }
        if (planeMyConcernFragment != null) {
            transaction.remove(planeMyConcernFragment);
        }
    }

    /*
         * 隐藏所有的fragment
         * @param transaction
         *     用于对fragment进行操作的事务
         */
    private void hideFragments(FragmentTransaction transaction)
    {
        if (planeNumFragment != null) {
            transaction.hide(planeNumFragment);
        }
        if (planeStarEndFragment != null) {
            transaction.hide(planeStarEndFragment);
        }
        if (planeMyConcernFragment != null) {
            transaction.hide(planeMyConcernFragment);
        }
    }

    /*
     * 清理之前的所有状态
     */
    private void clearSelection()
    {
        hbh.setTextColor(Color.parseColor("#282828"));//#82858b
        qjd.setTextColor(Color.parseColor("#282828"));
        mycoll.setTextColor(Color.parseColor("#282828"));
        hbh.setBackgroundColor(Color.TRANSPARENT);
        qjd.setBackgroundColor(Color.TRANSPARENT);
        mycoll.setBackgroundColor(Color.TRANSPARENT);
        lineThree.setVisibility(View.INVISIBLE);
        lineTwo.setVisibility(View.INVISIBLE);
        lineOne.setVisibility(View.INVISIBLE);
    }



    /*
     * 初始化界面，并设置3个tab的监听
     */
    private void initViews()
    {

        content = (FrameLayout) findViewById(R.id.content);
        hbh = (TextView) findViewById(R.id.hbh);
        qjd = (TextView) findViewById(R.id.qjd);
        mycoll = (TextView) findViewById(R.id.mycoll);

        iv_back = (LinearLayout) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        hbh.setOnClickListener(this);
        qjd.setOnClickListener(this);
        mycoll.setOnClickListener(this);

        lineOne = findViewById(R.id.lineOne);

        lineTwo = findViewById(R.id.lineTwo);

        lineThree = findViewById(R.id.lineThree);


    }

    /*
     * 点击3个tab时的监听
     * @param v
     *     3个控件的view
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.hbh:
                currentIndex = 0;
                setTabSelection(currentIndex);
                break;
            case R.id.qjd:
                currentIndex = 1;
                setTabSelection(currentIndex);
                break;
            case R.id.mycoll:
                currentIndex = 2;
                setTabSelection(currentIndex);
                break;
            default:
                break;
        }
    }
}
