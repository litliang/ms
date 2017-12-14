package com.yzb.card.king.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.util.DialogUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SetStutasUtils;
import com.yzb.card.king.ui.appwidget.popup.model.QueryCityModel;
import com.yzb.card.king.ui.appwidget.popup.model.impl.QueryCityModelImpl;
import com.yzb.card.king.ui.home.AppHomeActivity;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.util.SharedPreferencesUtils;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import java.util.Calendar;

import cn.jpush.android.api.JPushInterface;

/**
 * 类  名：欢迎页面
 * 作  者：Li Yubing
 * 日  期：2016/8/9
 * 描  述：
 */
public class WelcomeActivity extends Activity implements View.OnClickListener, BaseViewLayerInterface {

    QueryCityModel queryCityModel;

    private ImageView ivWelcome;
    private TextView numTxt;
    private int count = 4;
    private Animation animation;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        if (!(ServiceDispatcher.base_url_api.contains("115") || ServiceDispatcher.base_url_api.contains("117"))) {
//
//        } else {
////            if(SharedPreferencesUtils.getParam(this,"init","false").toString().equals("false")) {
////                SharedPreferences sp = getSharedPreferences(
////                        AppConstant.FILE_PLACE_NAME, Context.MODE_PRIVATE |Context.MODE_MULTI_PROCESS);
////                sp.edit().putString("init","true");
//
//                DialogUtil.getInstance().showDialog(this, "", "使用测试版？", new DialogUtil.DialogCallBack() {
//                    @Override
//                    public void yes() {
//                        super.yes();
//
//                    }
//
//                    @Override
//                    public void no() {
//                        super.no();
//                        ServiceDispatcher.setBase_url_api(ServiceDispatcher.base_url_api_115);
//                        ServiceDispatcher.reset();
//
//                    }
//                });
//
////            }
//        }
        SetStutasUtils.StatusBarLightMode(this);
        NationalCountryPresenter nPresenter = new NationalCountryPresenter();
        nPresenter.setBaseViewLayerInterface(this);
        //发送国内城市数据请求
        nPresenter.sendRequest("1");

        queryCityModel = new QueryCityModelImpl(null, null);

        queryCityModel.getCities();

        ivWelcome = (ImageView) findViewById(R.id.ivWelcome);

        numTxt = (TextView) findViewById(R.id.welcome_textView);

        findViewById(R.id.welcome_pass).setOnClickListener(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.animation_welcome_txt);

        myHandler.sendEmptyMessageDelayed(0, 1000);

        ivWelcome.setBackgroundResource(R.mipmap.welcome_new_year);

        GlobalApp.getInstance().initApp();

    }
    Thread thread;
    //欢迎页面倒计时
    private int getCount() {
        count--;
        if (count == 0) {
            if (flag == 0) {
                DialogUtil.getInstance().closeDialog();
                Intent intent = new Intent(WelcomeActivity.this, AppHomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
        return count;
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        MobclickAgent.onResume(this);
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            numTxt.setText(getCount() + "");
            myHandler.sendEmptyMessageDelayed(0, 1000);
            animation.reset();
            numTxt.startAnimation(animation);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(WelcomeActivity.this, AppHomeActivity.class);//AppHomeActivity
        startActivity(intent);
        flag = 1;
        finish();
    }

    @Override
    public void callSuccessViewLogic(Object o, int type) {

        //查询地理信息
        if (type == NationalCountryPresenter.SUCUESS_QUERY_CHIAN_INFO_CODE) {
            GlobalApp.getInstance().queryCityInfoByCityName();
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {

    }
}
