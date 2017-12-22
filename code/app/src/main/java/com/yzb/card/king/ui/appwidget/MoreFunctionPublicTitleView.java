package com.yzb.card.king.ui.appwidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.popup.ChannelPopWindow;
import com.yzb.card.king.ui.discount.activity.SearchDiscountActivity;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.activity.MessageActivity;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.user.LoginActivity;

import static com.yzb.card.king.util.UiUtils.startActivity;

/**
 * 类  名：多功能公共标题视图类
 * 作  者：Li Yubing
 * 日  期：2016/8/16
 * 描  述：实现城市选择器、频道筛选器、标题、用户中心、搜索
 */
public class MoreFunctionPublicTitleView implements View.OnClickListener {

    /**
     * CITY_CHANEEL:显示频道、城市
     * NO_CHANEEL:不显示频道
     */
    public enum Type {
        CITY_CHANNEL, NO_CHANNEL
    }//

    /**
     * 用户中心
     */
    private LinearLayout llParent;

    /**
     * 搜索功能
     */
    private LinearLayout llSearch;


    private Activity activity;

    private TextView tvCityName;

    private TextView middleTitle;

    private TextView tvModelName;

    private String cityId;

    private ChannelPopWindow channelPopWindow;

    private ImageView arrowDownType;

    private String indist = "";
    /**
     * 城市视图
     */
    private View headerLeft;
    /**
     * 模块筛选器视图
     */
    private View viewModle;

    private View view;
    /**
     * 特殊句柄，主要用来关闭当前页面
     */
    private Handler handler;

    private boolean ifUpdateCity = false;
    ///
    /// 第二标题视图控件
    ///
    private View seconedTitleView;

    private ImageView ivRight;

    private TextView titleName;
    //
    //第三标题视图控件
    //
    private View threeTitleView;

    private TextView tvCityName1;

    public MoreFunctionPublicTitleView(Activity activity, View view, MoreFunctionPublicTitleView.Type type)
    {

        this.activity = activity;

        this.view = view;

        initView(view, type);
    }

    public MoreFunctionPublicTitleView(Activity activity, View view)
    {

        this.activity = activity;

        this.view = view;

        tvCityName = (TextView) view.findViewById(R.id.tvCityName);
        Location city = GlobalApp.getSelectedCity();
        if (city != null) {
            cityId = city.cityId;
            tvCityName.setText(TextUtils.isEmpty(city.cityName) ? city.msg : city.cityName);
        }


        view.findViewById(R.id.headerLeft).setOnClickListener(this);

        view.findViewById(R.id.llSearch).setOnClickListener(this);

        view.findViewById(R.id.llMessage).setOnClickListener(this);
    }

    public MoreFunctionPublicTitleView()
    {

    }

    /**
     * 设置第二个标题视图
     *
     * @param titleView
     */
    public void setSeconedTitleView(View titleView)
    {

        seconedTitleView = titleView;

        titleName = (TextView) titleView.findViewById(R.id.titleName);

        ivRight = (ImageView) titleView.findViewById(R.id.ivRight);

        ivRight.setImageResource(R.mipmap.icon_top_menu);

        titleView.findViewById(R.id.llRight).setOnClickListener(this);

        TextView tvRight = (TextView) titleView.findViewById(R.id.tvRight);

        ivRight.setOnClickListener(this);

        tvRight.setVisibility(View.GONE);


    }

    /**
     * 设置第三个标题试图
     *
     * @param titleView
     */
    public void setThreeTitleView(View titleView)
    {

        threeTitleView = titleView;

        tvCityName1 = (TextView) titleView.findViewById(R.id.tvCityName);

        titleView.findViewById(R.id.ivRightLl).setOnClickListener(this);

        titleView.findViewById(R.id.llSearch).setOnClickListener(this);

        titleView.findViewById(R.id.headerLeft).setOnClickListener(this);

        Location city = GlobalApp.getSelectedCity();
        if (GlobalApp.getSelectedCity() != null)
            cityId = city.cityId;

        tvCityName1.setText(TextUtils.isEmpty(city.cityName) ? city.msg : city.cityName);
    }

    /**
     * 设置第二个标题名
     */
    public void setSeconedTitleName(String seconedTitleName)
    {

        if (titleName != null) {

            titleName.setText(seconedTitleName);
        }
    }

    private void initView(View view, MoreFunctionPublicTitleView.Type type)
    {

        llParent = (LinearLayout) view.findViewById(R.id.llParent);

        llParent.setOnClickListener(this);

        llParent.setVisibility(View.GONE);

        llSearch = (LinearLayout) view.findViewById(R.id.llSearch);

        llSearch.setOnClickListener(this);

        headerLeft = view.findViewById(R.id.headerLeft);

        headerLeft.setOnClickListener(this);

        tvCityName = (TextView) headerLeft.findViewById(R.id.tv_type);

        middleTitle = (TextView) view.findViewById(R.id.middleTitle);

        viewModle = view.findViewById(R.id.viewModle);

        viewModle.setOnClickListener(this);

        tvModelName = (TextView) viewModle.findViewById(R.id.tv_type);

        arrowDownType = (ImageView) viewModle.findViewById(R.id.arrow_down_type);

        if (type == Type.CITY_CHANNEL) {

            viewModle.setVisibility(View.VISIBLE);

        } else if (type == Type.NO_CHANNEL) {

            viewModle.setVisibility(View.GONE);

        }

        Location city = GlobalApp.getSelectedCity();
        if (GlobalApp.getSelectedCity() != null)
            cityId = city.cityId;
        tvCityName.setText(TextUtils.isEmpty(city.cityName) ? city.msg : city.cityName);
    }

    public void setHandler(Handler hander)
    {

        this.handler = hander;
    }

    /**
     * 设置城市名
     *
     * @param cityName
     */
    public void setCityName(String cityName)
    {

        if (tvCityName != null) {
            tvCityName.setText(cityName);
        }

        if (tvCityName1 != null) {
            tvCityName1.setText(cityName);
        }

    }

    /**
     * 获取城市名
     *
     * @return
     */
    public String getCityName()
    {
        String cityNameStr = "";

        if (tvCityName != null) {
            cityNameStr = tvCityName.getText().toString().trim();
        }


        if (tvCityName1 != null) {
            cityNameStr = tvCityName1.getText().toString().trim();
        }
        return cityNameStr;
    }

    public String getCiytId()
    {
        return cityId;
    }

    /**
     * 设置标题名
     *
     * @param titleName
     */
    public void setTitleName(String titleName)
    {

        middleTitle.setText(titleName);

    }

    /**
     * 设置模块名称
     *
     * @param modeName
     */
    public void setModelName(String modeName)
    {

        tvModelName.setText(modeName);
    }

    /**
     * 更新全局此城市信息
     */
    public void updateAppCity()
    {

        if (ifUpdateCity) {

            ifUpdateCity = false;

            String cityName = CitySelectManager.getInstance().getPlaceName();

            String cityId = CitySelectManager.getInstance().getPlaceId();

            GlobalApp.getInstance().setSelectedCity(cityId, cityName);

            if (tvCityName != null) {
                tvCityName.setText(cityName);
            }
            if (tvCityName1 != null) {
                tvCityName1.setText(cityName);
            }

            this.cityId = cityId;
        }

    }

    public void setIndist(String indist)
    {
        this.indist = indist;
    }

    /**
     * 是否显示城市和搜索按钮
     *
     * @param ifShow true:显示；false：隐藏
     */
    public void ifShowCitySearchControl(boolean ifShow)
    {

        if (ifShow) {

            showControls();

        } else {

            hiddenControls();
        }

    }

    /**
     * 隐藏搜素和城市查询入口
     */
    public void hiddenControls()
    {

        headerLeft.setVisibility(View.GONE);

        llSearch.setVisibility(View.GONE);
    }

    /**
     * 显示搜索和城市查询入口
     */
    public void showControls()
    {

        headerLeft.setVisibility(View.VISIBLE);

        llSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId()) {

            case R.id.llParent:

                break;
            case R.id.llSearch://搜索功能

                if (indist.equals(AppConstant.hotel_id)) {
//                    Intent intent = new Intent();
//                    intent.setClass(activity, HotelSearchActivity.class);
//                    intent.putExtra(HotelSearchActivity.INDUSTRYID, AppConstant.hotel_id);
//                    activity.startActivity(intent);
                } else {

                    activity.startActivity(new Intent(activity, SearchDiscountActivity.class));
                }
                break;
            case R.id.headerLeft://城市选择器

                ifUpdateCity = true;
                activity.startActivity(new Intent(activity, SelectPlaceActivity.class));

                break;

            case R.id.ivRightLl:
            case R.id.viewModle://模块筛选器
            case R.id.ivRight:
            case R.id.llRight:

                channelPopWindow = new ChannelPopWindow(handler, activity);

                if (v.getId() == R.id.viewModle) {

                    channelPopWindow.showAsDropDown(view);

                } else if (v.getId() == R.id.llRight || v.getId() == R.id.ivRight) {

                    if (seconedTitleView != null) {

                        //居右
                        channelPopWindow.showAsDropDown(seconedTitleView, channelPopWindow.getWidth(), 0);

                    }

                } else if (v.getId() == R.id.ivRightLl) {
                    if (threeTitleView != null) {
                        //居右
                        channelPopWindow.showAsDropDown(threeTitleView, channelPopWindow.getWidth(), 0);
                    }
                }

                startInverse(arrowDownType, R.anim.rotate_animation_start);

                channelPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss()
                    {
                        startInverse(arrowDownType, R.anim.rotate_animation_end);
                    }
                });

                break;
            case R.id.llMessage:

                if (UserManager.getInstance().isLogin()) {
                    startActivity(new Intent(activity, MessageActivity.class));
                } else {

                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                }

                break;

            default:
                break;

        }
    }

    /**
     * 显示频道pp
     */
    public void showChannelPopWindow(View view, int tyep, Handler handler, Activity activity)
    {

        ChannelPopWindow channelPopWindow = new ChannelPopWindow(handler, activity);

        if (tyep == 1) {

            channelPopWindow.showAsDropDown(view, channelPopWindow.getWidth(), 0);
        } else {

            channelPopWindow.showAsDropDown(view);
        }


        channelPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss()
            {
            }
        });

    }


    /**
     * 执行旋转动画；
     *
     * @param view
     * @param animationResId
     */
    public void startInverse(View view, int animationResId)
    {
        view.startAnimation(AnimationUtils.loadAnimation(GlobalApp.getInstance().getContext(), animationResId));
    }
}
