package com.yzb.card.king.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.sys.AppFactory;
import com.yzb.card.king.ui.appwidget.MoreFunctionPublicTitleView;
import com.yzb.card.king.ui.base.BaseCityChangeActivity;
import com.yzb.card.king.ui.discount.DiscountFragmentCallBack;
import com.yzb.card.king.ui.hotel.activtiy.HotelHomeActivity;
import com.yzb.card.king.ui.ticket.activity.AirTicketHomeActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 添加一级频道碎片，并管理
 * 如美食、旅游、奢侈品、机票、电影、酒店、陆上交通、城市生活、太极生活
 */
@ContentView(R.layout.activity_channel_main)
public class ChannelMainActivity extends BaseCityChangeActivity implements DiscountFragmentCallBack
{
    @ViewInject(R.id.viewTitle)
    private View viewTitle;

    @ViewInject(R.id.viewTitleTwo)
    private View viewTitleTwo;

    @ViewInject(R.id.viewTitleThree)
    private View viewTitleThree;

    @ViewInject(R.id.llParent)
    private LinearLayout llParent;

    @ViewInject(R.id.tab_tv_shyh)
    private TextView tab_tv_shyh;

    @ViewInject(R.id.tab_iv_shyh)
    private ImageView tab_iv_shyh;

    public static String CLASS_NAME;

    private List<Fragment> fragments;

    private int pagetype = 0;

    public int curPage = 0; // 当前的大分类页面；恢复现场时使用；

    private ChildTypeBean selectedBean = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        CLASS_NAME = ChannelMainActivity.this.getClass().getName();

        registerBoradcastReceiver();

        if (getIntent().hasExtra("data"))
        {

            selectedBean = (ChildTypeBean) getIntent().getSerializableExtra("data");
        }
        assignViews();
        // 上次保存的页面； // 0:美食，1：奢侈品；2：电影；3：机票；4：酒店；5：旅游；11：城市活动
        if (savedInstanceState != null)
        {
            curPage = savedInstanceState.getInt("curPage", 0);

            replaceFragment(curPage);

        } else
        {
            replaceFragment(pagetype);
        }
        tab_tv_shyh.setSelected(true);
        tab_iv_shyh.setBackgroundResource(R.mipmap.icon_shyh_yellow);
    }

    private void assignViews()
    {
        fragments = AppFactory.getAllOneChannel();

        Intent intent = getIntent();
        pagetype = intent.getIntExtra("pagetype", 0);

        moreFunctionPublicTitleView = new MoreFunctionPublicTitleView(ChannelMainActivity.this, viewTitle,
                MoreFunctionPublicTitleView.Type.CITY_CHANNEL);

        moreFunctionPublicTitleView.hiddenControls();

        //第二个标题view
        moreFunctionPublicTitleView.setSeconedTitleView(viewTitleTwo);
        //设置第三个标题
        moreFunctionPublicTitleView.setThreeTitleView(viewTitleThree);

    }

    public void setIndest(String indest)
    {
        moreFunctionPublicTitleView.setIndist(indest);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        //美食三级分类进入；
        pagetype = intent.getIntExtra("page", 0);
        replaceFragment(pagetype);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putInt("curPage", curPage);
        super.onSaveInstanceState(outState);
    }

    /**
     * Fragment替换，此处用add方式添加Fragment；
     */
    private void replaceFragment(int index)
    {

        LogUtil.e("XYYYYY","replaceFragment="+index);
        moreFunctionPublicTitleView.setTitleName("");

        if (index >= 0 && index < fragments.size())
        {

            hideAllFragment();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("" + index);

            if (fragment == null)
            {
                getSupportFragmentManager().beginTransaction().add(R.id.container,
                        fragments.get(index), "" + index).commitAllowingStateLoss();
            } else
            {
                getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();
            }
        }
        if (selectedBean != null)
        {

            moreFunctionPublicTitleView.setModelName(selectedBean.typeName);

        }
        /**
         * 特殊处理，当index=3,则隐藏搜索和城市按钮
         */

        if (index == 0 || index == 3)
        {//当index = 0 时，则显示旅游条目

            viewTitle.setVisibility(View.GONE);

            viewTitleTwo.setVisibility(View.VISIBLE);

            viewTitleThree.setVisibility(View.GONE);

            moreFunctionPublicTitleView.setSeconedTitleName(selectedBean.typeName);

        } else if (index == 4)
        {
            llParent.setVisibility(View.GONE);

            viewTitle.setVisibility(View.GONE);

            viewTitleTwo.setVisibility(View.GONE);

            viewTitleThree.setVisibility(View.VISIBLE);

        } else
        {
            moreFunctionPublicTitleView.ifShowCitySearchControl(true);

            viewTitle.setVisibility(View.VISIBLE);

            viewTitleTwo.setVisibility(View.GONE);

            viewTitleThree.setVisibility(View.GONE);

        }

    }

    /**
     * 隐藏所有的Fragment；
     */
    private void hideAllFragment()
    {
        if (fragments == null || fragments.size() == 0)
        {
            return;
        }
        for (int i = 0; i < fragments.size(); i++)
        {
            Fragment fragment = fragments.get(i);

            if (fragment != null && fragment.isVisible())
            {
                getSupportFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
            }
        }
    }

    public String returnCityName()
    {
        return moreFunctionPublicTitleView.getCityName();
    }

    public String returnCityId()
    {
        return moreFunctionPublicTitleView.getCiytId();
    }

    @Event(R.id.rl_xyk)
    private void rlXykAction(View view)
    {
        Intent intent0 = new Intent(this, AppHomeActivity.class);
        intent0.putExtra("pagetype_from_msmain", 0);
        startActivity(intent0);
    }

    @Event(R.id.rl_jfb)
    private void rlJfbAction(View view)
    {
        Intent intent1 = new Intent(this, AppHomeActivity.class);
        intent1.putExtra("pagetype_from_msmain", 1);
        startActivity(intent1);
        finish();
    }

    @Event(R.id.rl_shyh)
    private void rlShyhAction(View view)
    {
        Intent intent2 = new Intent(this, AppHomeActivity.class);
        intent2.putExtra("pagetype_from_msmain", 2);
        startActivity(intent2);
        finish();
    }

    @Event(R.id.rl_shfw)
    private void rlShfwAction(View view)
    {
        Intent intent3 = new Intent(this, AppHomeActivity.class);
        intent3.putExtra("pagetype_from_msmain", 3);
        startActivity(intent3);
        finish();
    }

    /**
     * 返回事件方法
     *
     * @param view
     */
    public void backAction(View view)
    {
        //回到上一页
        finish();
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unRegisterBoradcastReceiver();
    }

    /**
     * 注册广播接收器
     */
    public void registerBoradcastReceiver()
    {

        IntentFilter myIntentFilter = new IntentFilter();

        myIntentFilter.addAction(CLASS_NAME);
        // 注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    /**
     * 取消注册广播
     */
    public void unRegisterBoradcastReceiver()
    {

        if (mBroadcastReceiver != null)
        {

            unregisterReceiver(mBroadcastReceiver);
        }

    }

    /**
     * 广播接收器
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            String action = intent.getAction();

            if (action.equals(CLASS_NAME))
            {

                if (intent.hasExtra("data"))
                {
                    Object o = intent.getSerializableExtra("data");

                    ChildTypeBean    selectedBeanT = (ChildTypeBean) o;

                   int  pagetypet = AppFactory.channelIdToFragmentIndex(selectedBeanT.id);

                    if(pagetypet != -1){

                        GlobalVariable.industryId = Integer.parseInt(selectedBeanT.id);

                        if(pagetypet == 2){//酒店

                            startActivity(new Intent(ChannelMainActivity.this, HotelHomeActivity.class));

                            finish();

                        }
                        else if(pagetypet == 1){//机票

                            startActivity(new Intent(ChannelMainActivity.this, AirTicketHomeActivity.class));

                            finish();
                        }
                        else{
                            selectedBean = selectedBeanT;

                            pagetype = pagetypet;

                            replaceFragment(pagetype);
                        }


                    }else{
                        ToastUtil.i(ChannelMainActivity.this,"敬请期待");
                    }

                }

            }
        }
    };

    @Override
    public MoreFunctionPublicTitleView getTitleView()
    {
        return moreFunctionPublicTitleView;
    }

}