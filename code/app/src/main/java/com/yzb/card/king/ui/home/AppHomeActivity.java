package com.yzb.card.king.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.BMapManager;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FragmentMessageEvent;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.AppFactory;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.appwidget.popup.RedBagPopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.gift.activity.GiftCardHomeActivity;
import com.yzb.card.king.ui.my.MyIndexFragment;
import com.yzb.card.king.ui.my.activity.CouponsHomeActivity;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.ui.user.presenter.LoginPresenter;
import com.yzb.card.king.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * app首页；
 * Author : gengqiyun
 * Date  : 2016/6/16.
 */
@ContentView(R.layout.tab_home_page_new)
public class AppHomeActivity extends BaseActivity
{
    private List<Fragment> fragments;

    @ViewInject(R.id.tab_iv_xyk)
    private ImageView tabIvXyk;

    @ViewInject(R.id.tab_tv_xyk)
    private View tabTvXyk;

    @ViewInject(R.id.tab_iv_jfb)
    private ImageView tabIvJfb;

    @ViewInject(R.id.tab_tv_jfb)
    private View tabTvJfb;

    @ViewInject(R.id.tab_iv_shyh)
    private ImageView tabIvShyh;
    @ViewInject(R.id.tab_tv_shyh)
    private View tabTvShyh;

    @ViewInject(R.id.tab_iv_shfw)
    private ImageView tabIvShfw;
    @ViewInject(R.id.tab_tv_shfw)
    private View tabTvShfw;

    private RedBagPopup redBagPopup;
    private int index;

    private View contentView;

    private NationalCountryPresenter nPresenter;

    private int currentIndex;
    private int fragmentIndex;
    private FragmentMessageEvent currentEvent;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        GlobalVariable.industryId =1003;
        initView();
        updateBottomStyleByIndex(2, true);
        replaceFragment(2);

        //自动登录
        new LoginPresenter(this).sendPersonInfoRequest();
//        checkLocationPermission();
        nPresenter = new NationalCountryPresenter();
        //发送国际城市数据请求
        nPresenter.sendRequest("2");
        //注册事件
        EventBus.getDefault().register(this);
    }

    private void initView()
    {
        contentView = getWindow().getDecorView();

        fragments = AppFactory.getHomeTabFragmentList();

    }

    //信用卡
    @Event(R.id.rl_xyk)
    private void rlXykAction(View v)
    {
        setRedBagState(v.getId());
        updateBottomStyleByIndex(0, true);
        replaceFragment(0);
    }

    //积分包
    @Event(R.id.rl_jfb)
    private void rlJfbAction(View v)
    {
        setRedBagState(v.getId());
        updateBottomStyleByIndex(1, true);
        replaceFragment(1);
    }

    //商户优惠
    @Event(R.id.rl_shyh)
    private void rlShyhAction(View v)
    {
        setRedBagState(v.getId());
        updateBottomStyleByIndex(2, true);
        replaceFragment(2);

    }

    //我的
    @Event(R.id.rl_shfw)
    private void rlShfwAction(View v)
    {
        if (isLogin())
        {
            setRedBagState(v.getId());
            updateBottomStyleByIndex(3, true);
            replaceFragment(3);
        } else
        {

            Intent intent = new Intent(AppHomeActivity.this, LoginActivity.class);
            startActivityForResult(intent, 101);
        }

    }

    @Override
    protected void onDestroy()
    {
        BMapManager.destroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void mainEventThread(FragmentMessageEvent event)
    {
        this.currentEvent = event;
        fragmentIndex = event.getFragmentIndex();

        boolean flag = false;
        int id = -1;
        if (fragmentIndex == 2 || fragmentIndex == 3)
        {
            flag = true;
            if (fragmentIndex == 2)
            {
                id = R.id.rl_shyh;
            }
        }
        updateBottomStyleByIndex(fragmentIndex, flag);
        setRedBagState(id);
        replaceFragment(fragmentIndex);
    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event)
//    {
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//        {
//            if (currentEvent != null)
//            {
//                if (currentEvent.getFragmentIndex() == 4)
//                {
//                    LogUtil.i("礼品卡商城");
//                    if (currentEvent.getTag()) {
//                        FragmentMessageEvent event1 = new FragmentMessageEvent();
//                        event1.setFragmentIndex(2);
//                        EventBus.getDefault().post(event1);
//                    }else{
//                        startActivity(new Intent(this, GiftCardHomeActivity.class));
//                    }
//                    return true;
//                } else if (currentEvent.getFragmentIndex() == 5) //优惠券商城；
//                {
//                    //商户优惠首页进入；
//                    if (currentEvent.getTag())
//                    {
//                        LogUtil.i("商户优惠首页进入优惠券商城");
//                        FragmentMessageEvent event1 = new FragmentMessageEvent();
//                        event1.setFragmentIndex(2);
//                        EventBus.getDefault().post(event1);
//                    } else
//                    {
//                        LogUtil.i("优惠券列表进入优惠券商城");
//                        startActivity(new Intent(this, CouponsHomeActivity.class));
//                    }
//                    return true;
//                }
//            }
//        }
//        return super.onKeyUp(keyCode, event);
//    }

    private void setRedBagState(int id)
    {
        if (id == R.id.rl_shyh)
        {//显示
            if (redBagPopup == null)
            {
                redBagPopup = new RedBagPopup(AppHomeActivity.this, contentView, this);
            }
            redBagPopup.showAtLocation(contentView, Gravity.RIGHT, 0, 0);
        } else
        {//隐藏
            if (redBagPopup != null && redBagPopup.isShowing())
            {
                redBagPopup.dismiss();
            }
        }
    }

    /**
     * 页面切换；
     *
     * @param index
     */
    private void setCurrentPage(int index)
    {
        switch (index)
        {
            case 0:
                setRedBagState(R.id.rl_xyk);
                updateBottomStyleByIndex(0, true);
                replaceFragment(0);
                break;
            case 1:
                setRedBagState(R.id.rl_jfb);
                updateBottomStyleByIndex(1, true);
                replaceFragment(1);
                break;
            case 2:
                setRedBagState(R.id.rl_shyh);
                updateBottomStyleByIndex(2, true);
                replaceFragment(2);
                break;
            case 3:
                if (isLogin())
                {
                    setRedBagState(R.id.rl_shfw);
                    updateBottomStyleByIndex(3, true);
                    replaceFragment(3);
                } else
                {

                    Intent intent = new Intent(AppHomeActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 101);
                }
                break;
        }
    }

    /**
     * 更新底部的样式；
     *
     * @param index
     */
    private void updateBottomStyleByIndex(int index, boolean closedFlag)
    {
        clearAllActiveIvTv();

        if (closedFlag)
        {

            switch (index)
            {
                case 0:
                    tabIvXyk.setBackgroundResource(R.mipmap.icon_xyk_yellow);
                    tabTvXyk.setSelected(true);
                    break;
                case 1:
                    tabIvJfb.setBackgroundResource(R.mipmap.icon_jfb_yellow);
                    tabTvJfb.setSelected(true);
                    break;
                case 2:
                    tabIvShyh.setBackgroundResource(R.mipmap.icon_shyh_yellow);
                    tabTvShyh.setSelected(true);
                    break;
                case 3:
                    tabIvShfw.setBackgroundResource(R.mipmap.icon_wd_yellow);
                    tabTvShfw.setSelected(true);
                    break;
            }

        }
    }

    private void clearAllActiveIvTv()
    {
        tabIvXyk.setBackgroundResource(R.mipmap.icon_xyk_white);
        tabIvJfb.setBackgroundResource(R.mipmap.icon_jfb_white);
        tabIvShyh.setBackgroundResource(R.mipmap.icon_shyh_white);
        tabIvShfw.setBackgroundResource(R.mipmap.icon_wd_white);

        tabTvXyk.setSelected(false);
        tabTvJfb.setSelected(false);
        tabTvShyh.setSelected(false);
        tabTvShfw.setSelected(false);
    }


    /**
     * Fragment替换；
     *
     * @param index
     */
    private void replaceFragment(int index)
    {
        if (index >= 0 && index < fragments.size())
        {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            Fragment fragment = fragments.get(index);

            fragmentTransaction.replace(R.id.container, fragment);

            fragmentTransaction.commitAllowingStateLoss();

        }
    }


    // 恢复Activity实例的状态；
    public void onRestoreInstanceState(Bundle paramBundle)
    {
        this.index = paramBundle.getInt("index");
        replaceFragment(index);
        super.onRestoreInstanceState(paramBundle);
    }

    public void onSaveInstanceState(Bundle paramBundle)
    {
        paramBundle.putInt("index", this.index);
        super.onSaveInstanceState(paramBundle);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        // 来自于美食首页的跳转；
        int pagetype = intent.getIntExtra("pagetype_from_msmain", -1);
        if (pagetype >= 0 && pagetype <= 3)
        {
            setCurrentPage(pagetype);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
        {
            case AppConstant.LOGIN_ACCESS:
                if (isLogin())
                {

                    if (currentIndex == 3)
                    {//刷新页面
                        MyIndexFragment fragment = (MyIndexFragment) fragments.get(3);

                        fragment.initData();
                    } else
                    {
                        setRedBagState(1);
                        updateBottomStyleByIndex(3, true);
                        replaceFragment(3);
                    }

                    if (isLogin())
                    {
                        setRedBagState(1);
                        updateBottomStyleByIndex(3, true);
                        replaceFragment(3);
                    }
                    break;
                }
        }
    }


    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        setRedBagState(R.id.rl_shyh);
    }
}
