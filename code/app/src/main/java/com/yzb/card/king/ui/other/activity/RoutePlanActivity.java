package com.yzb.card.king.ui.other.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.adapter.VPAdapter;
import com.yzb.card.king.ui.travel.fragment.BikingRouteFragment;
import com.yzb.card.king.ui.travel.fragment.BusRouteFragment;
import com.yzb.card.king.ui.travel.fragment.DrivingRouteFragment;
import com.yzb.card.king.ui.travel.fragment.WalkingRouteFragment;
import com.yzb.card.king.ui.appwidget.UnScrollViewPager;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图模式--路线规划；
 *
 * @author gengqiyun
 * @date 2016.8.13
 */
public class RoutePlanActivity extends BaseActivity implements View.OnClickListener,
        DrivingRouteFragment.OnFragmentInteractionListener
{
    public static final String START_CITY = "START_CITY";
    public static final String START_AREA = "START_AREA";
    public static final String END_CITY = "END_CITY";
    public static final String END_AREA = "END_AREA";
    public static final String ARG_LAT = "ARG_LAT";//纬度；
    public static final String ARG_LNG = "ARG_LNG";//经度
    private static final String TITLE = "TITLE";
    public static final String ARG_CITY_ID = "ARG_CITY_ID";
    private TextView tv_title;

    private String startCity; //出发市；
    private String startArea; //出发地；
    private String endCity; //目的市；
    private String endArea; //目的地；
    private double lat; //纬度；
    private double lng; //经度；
    private String cityId; //商家的cityId；用于判断商家和用户是否同城；

    private String title;
    private UnScrollViewPager viewPager;
    private TabLayout tabLayout;
    private boolean isLocalCity = false;//是否同城；
    private String[] tabTitles = null;
    private  List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);
        recvIntentData();
        assignViews();
    }

    private void recvIntentData()
    {
        Intent intent = getIntent();
        startCity = intent.getStringExtra(START_CITY);
        startArea = intent.getStringExtra(START_AREA);
        endCity = intent.getStringExtra(END_CITY);
        endArea = intent.getStringExtra(END_AREA);

        lat = intent.getDoubleExtra(ARG_LAT, 0);
        lng = intent.getDoubleExtra(ARG_LNG, 0);
        cityId = intent.getStringExtra(ARG_CITY_ID);
        title = intent.getStringExtra(TITLE);

        String cityId = GlobalApp.getSelectedCity().cityId;
        if (!TextUtils.isEmpty(cityId))
        {
            isLocalCity = cityId.equals(this.cityId) ? true : false;
        }
        LogUtil.i("接收到的lat=" + lat + ",lng=" + lng + ",cityId=" + cityId);
    }

    private void assignViews()
    {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(TextUtils.isEmpty(title) ? "路线规划" : title);
        findViewById(R.id.panel_back).setOnClickListener(this);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (UnScrollViewPager) findViewById(R.id.viewPager);

         fragments = new ArrayList<>();
        //驾车；
        fragments.add(DrivingRouteFragment.newInstance("", ""));
        //公交；
        //如果始末地点同城，显示公交选项，否则隐藏；
        if (isLocalCity)
        {
            startCity = endCity = GlobalApp.getPositionedCity().cityName;

            fragments.add(BusRouteFragment.newInstance("", ""));
            tabTitles = getResources().getStringArray(R.array.route_plan_type1);
        } else
        {
            tabTitles = getResources().getStringArray(R.array.route_plan_type2);
        }
        //步行；
        fragments.add(WalkingRouteFragment.newInstance("", ""));
        //骑行；
        fragments.add(BikingRouteFragment.newInstance("", ""));

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), fragments);

        vpAdapter.setTitles(tabTitles);

        viewPager.setAdapter(vpAdapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {

                if(position == 1 && isLocalCity){

                   if( fragments.get(position) instanceof BusRouteFragment ){

                       BusRouteFragment tempFragment = (BusRouteFragment) fragments.get(position);

                       tempFragment.showRouteDialog();

                   }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panel_back:
                finish();
                break;
        }
    }

    public double getStoreLng()
    {
        return lng;
    }

    @Override
    public double getStoreLat()
    {
        return lat;
    }

    @Override
    public String getStoreCityId()
    {
        return cityId;
    }

    //Fragment中要获取的城市；
    @Override
    public String getStartCity()
    {
        return startCity;
    }

    @Override
    public String getStartArea()
    {
        return startArea;
    }

    @Override
    public String getEndCity()
    {
        return endCity;
    }

    @Override
    public String getEndArea()
    {
        return endArea;
    }
}
