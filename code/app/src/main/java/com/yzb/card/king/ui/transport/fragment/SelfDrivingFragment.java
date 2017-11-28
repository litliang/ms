package com.yzb.card.king.ui.transport.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jamgle.pickerviewlib.view.TimePickerView;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.manage.AddressSearchManager;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.discount.bean.BusShopBean;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.appwidget.picks.SelfDrivePickTime;
import com.yzb.card.king.ui.manage.RentCarLogicManager;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.transport.activity.SelectMotorcycleTypeActivity;
import com.yzb.card.king.ui.transport.activity.SelfDriveLogicActivity;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.ToastUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * 自驾租车碎片
 * Created by dev on 2016/5/25.
 */
public class SelfDrivingFragment extends Fragment
{
    //控件
    private TextView tvSelectAddress, tvSelectCity;//取车地址

    private LinearLayout llSetDate;//设置日期

    private TextView searchFj;
    private TextView tvSelectShopAddress;

    private LinearLayout locationLayout;

    private boolean isGetCar;
    private boolean isBackCar;

    private SelfDrivePickTime sDPTime;

    private TextView tvStartDate, tvStartWeek, tvStartTime, tvEndDate, tvEndTime, tvEndWeek;

    private Button btnOk;


    private boolean reSetTimeFlag = false;
    private TextView tvSelectCityBack;
    private LinearLayout find_fj;
    private Integer cityId;
    private String cityName;
    private String cityAddress;
    private String cityDisktrict;
    private String cityStreet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_transport_slefdrive, container, false);

        initView(view);

        return view;
    }

    private void initView(View view)
    {

        searchFj = (TextView) view.findViewById(R.id.search_fj);

        tvStartDate = (TextView) view.findViewById(R.id.tvStartDate);

        tvStartWeek = (TextView) view.findViewById(R.id.tvStartWeek);

        tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);

        tvEndDate = (TextView) view.findViewById(R.id.tvEndDate);

        tvEndTime = (TextView) view.findViewById(R.id.tvEndTime);

        tvEndWeek = (TextView) view.findViewById(R.id.tvEndWeek);

        LinearLayout getBusAddressLL = (LinearLayout) view.findViewById(R.id.getBusAddressLL);

        LinearLayout backBusAddressLL = (LinearLayout) view.findViewById(R.id.backBusAddressLL);

        LinearLayout llLookoutShop = (LinearLayout) view.findViewById(R.id.llLookoutShop);

        LinearLayout llSetDate = (LinearLayout) view.findViewById(R.id.llSetDate);

        btnOk = (Button) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(myListener);

        getBusAddressLL.setOnClickListener(myListener);

        backBusAddressLL.setOnClickListener(myListener);

        llLookoutShop.setOnClickListener(myListener);

        llSetDate.setOnClickListener(myListener);

        tvSelectAddress = (TextView) view.findViewById(R.id.tvSelectAddress);
        tvSelectShopAddress = (TextView) view.findViewById(R.id.tvSelectShopAddress);

        tvSelectCity = (TextView) view.findViewById(R.id.tvSelectCity_get);
        tvSelectCityBack = (TextView) view.findViewById(R.id.tvSelectCity_back);

        locationLayout = (LinearLayout) view.findViewById(R.id.location_layout);
        locationLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                currentLocation();
            }
        });
        //显示当前用户所在城市信息
        //tvSelectCity.setText(GlobalApp.getInstance().getCityName());

        if (!TextUtils.isEmpty(GlobalApp.getSelectedCity().cityId) && GlobalApp.getSelectedCity().cityId.length() > 0)
        {
            RentCarLogicManager.getInstance().setPlaceId(Integer.parseInt(GlobalApp.getSelectedCity().cityId));
        }


        RentCarLogicManager.getInstance().setPlaceName(cityName);


        //默认
        long startCurrentTime = System.currentTimeMillis();

        long endCurrentTime = startCurrentTime + 24 * 60 * 60 * 1000;

        //
        RentCarLogicManager.getInstance().setStartCurrentTime(startCurrentTime);

        RentCarLogicManager.getInstance().setEndCurrentTime(endCurrentTime);

        setSelfDriveTimeFrame(startCurrentTime, endCurrentTime);

        sDPTime = new SelfDrivePickTime(getContext(), startCurrentTime, endCurrentTime, TimePickerView.Type.MONTH_DAY_WEEK_HOUR_MIN);

        sDPTime.setTime(new Date());
        sDPTime.setCyclic(false);
        sDPTime.setCancelable(true);
        //时间选择后回调
        sDPTime.setOnTimeSelectListener(new SelfDrivePickTime.OnTimeSelectListener()
        {

            @Override
            public void onTimeSelect(long startTime, long endTime)
            {

                reSetTimeFlag = true;

                RentCarLogicManager.getInstance().setStartCurrentTime(startTime);

                RentCarLogicManager.getInstance().setEndCurrentTime(endTime);

                setSelfDriveTimeFrame(startTime, endTime);


            }

            @Override
            public void onCancel()
            {

            }
        });

        currentLocation();
    }

    /**
     * 设置自驾租车时段
     *
     * @param startTime
     * @param endTime
     */
    private void setSelfDriveTimeFrame(long startTime, long endTime)
    {

        String[] weekOfDays = getActivity().getResources().getStringArray(R.array.week_name);

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(startTime);
        int startmonth = startCalendar.get(Calendar.MONTH) + 1;
        int startday = startCalendar.get(Calendar.DAY_OF_MONTH);
        int starthours = startCalendar.get(Calendar.HOUR_OF_DAY);
        int startminute = startCalendar.get(Calendar.MINUTE);
        int startw = startCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (startw < 0)
        {
            startw = 0;
        }
        String startweekStr = weekOfDays[startw];

        StringBuffer startSb = new StringBuffer();
        startSb.append(startmonth).append("月").append(startday).append("日");
        tvStartDate.setText(startSb.toString());
        tvStartWeek.setText(startweekStr);
        tvStartTime.setText(starthours + ":" + startminute);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(endTime);
        int endmonth = endCalendar.get(Calendar.MONTH) + 1;
        int endday = endCalendar.get(Calendar.DAY_OF_MONTH);
        int endhours = endCalendar.get(Calendar.HOUR_OF_DAY);
        int endminute = endCalendar.get(Calendar.MINUTE);
        int endw = endCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (endw < 0)
        {
            endw = 0;
        }
        String endweekStr = weekOfDays[endw];
        StringBuffer endSb = new StringBuffer();
        endSb.append(endmonth).append("月").append(endday).append("日");
        tvEndDate.setText(endSb.toString());
        tvEndWeek.setText(endweekStr);
        tvEndTime.setText(endhours + ":" + endminute);
    }

    public static SelfDrivingFragment newInstance()
    {

        Bundle args = new Bundle();

        SelfDrivingFragment fragment = new SelfDrivingFragment();

        fragment.setArguments(args);

        return fragment;
    }

    private String cityNumber;
    private View.OnClickListener myListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            switch (v.getId())
            {
                case R.id.btnOk:

                    toNextActivity();

                    break;
                case R.id.backBusAddressLL:
                    Intent intent1 = new Intent(getContext(), SelectPlaceActivity.class);
                    intent1.putExtra("cityId", GlobalApp.getSelectedCity().cityId);
                    intent1.putExtra("sign", GlobalApp.getSelectedCity().cityName);
                    isBackCar = true;
                    isGetCar = false;
                    startActivity(intent1);
                    break;
                case R.id.getBusAddressLL://取车地址选择
                    Intent intent = new Intent(getContext(), SelectPlaceActivity.class);
                    intent.putExtra("cityId", GlobalApp.getSelectedCity().cityId);
                    intent.putExtra("sign", GlobalApp.getSelectedCity().cityName);
                    isGetCar = true;
                    isBackCar = false;
                    startActivity(intent);
                    break;
                case R.id.llLookoutShop://寻找附近门店

                    toNextActivity();

//                    if (RentCarLogicManager.getInstance().getO() == null || reSetTimeFlag) {
//
//                        reSetTimeFlag = false;

//                    } else {
//
//                        Intent intent1 = new Intent(getContext(), SelectMotorcycleTypeActivity.class);
//
//                        intent1.putExtra("logic_flag",SelfDriveLogicActivity.SELF_DRIVE_FLAG);
//
//                        startActivity(intent1);
//                    }


                    break;
                case R.id.llSetDate://设置日期
                    //开启日期选择器
                    sDPTime.show();

                    break;
                case R.id.ivFareEstimateEnd:
                case R.id.ivFareEstimateStart:
                    break;
                default:
                    break;
            }

        }

    };

    private void toNextActivity()
    {

        if (StringUtils.isEmpty(tvSelectCityBack.getText().toString().trim()))
        {
            ToastUtil.i(this.getActivity(),R.string.choice_car_location);
            return;
        }

        Intent intentShop = new Intent(getContext(), SelfDriveLogicActivity.class);

        intentShop.putExtra("stepIndex", 2);
        intentShop.putExtra("address", cityDisktrict + cityStreet + cityNumber);
        startActivity(intentShop);
    }

    private void currentLocation()
    {
        Location city = GlobalApp.getPositionedCity();
        cityId = Integer.valueOf(city.cityId);
        cityName = city.cityName;
        cityAddress = city.address;
        cityNumber = city.streetNumber;
        cityDisktrict = city.district;
        cityStreet = city.street;
        Log.i("LCZ", "  定位之后  " + city.district + city.street + city.streetNumber);
        tvSelectShopAddress.setText(city.district + city.street + city.streetNumber);
        tvSelectAddress.setText(cityName);
        tvSelectCity.setText(cityName);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        Log.i("LCZ", "onResume = === " + AddressSearchManager.getInstance().isIfSelectAddress() + "");
        if (AddressSearchManager.getInstance().isIfSelectAddress())
        {
            searchFj.setTextColor(Color.BLACK);
            Log.i("LCZ", AddressSearchManager.getInstance().isIfSelectAddress() + "");
            String placeName = AddressSearchManager.getInstance().getAddrName();
            searchFj.setText(placeName);
            RentCarLogicManager.getInstance().setPlaceName(placeName);
        }

    }

    @Override
    public void onStart()
    {
        super.onStart();

        Log.i("LCZ", " 选择城市之后 执行了");

        if (!StringUtils.isEmpty(CitySelectManager.getInstance().getPlaceId()))
        {
            String placeName = CitySelectManager.getInstance().getPlaceName();

            int placeId = Integer.parseInt(CitySelectManager.getInstance().getPlaceId());

            RentCarLogicManager.getInstance().setPlaceId(placeId);

            RentCarLogicManager.getInstance().setPlaceName(placeName);

            CitySelectManager.getInstance().clearData();

            if (isGetCar)
            {
                tvSelectCity.setText(placeName);
            } else
            {
                tvSelectCityBack.setText(placeName);
            }

        }
        if (RentCarLogicManager.getInstance().getO() != null)
        {

            BusShopBean busShopBean = (BusShopBean) RentCarLogicManager.getInstance().getO();

            tvSelectShopAddress.setText(busShopBean.storeAddr);

            if (RentCarLogicManager.getInstance().isFlagBack())
            {

                RentCarLogicManager.getInstance().setFlagBack(false);

                activityHander.sendEmptyMessageDelayed(0, 500);
            }


        }


        if (RentCarLogicManager.getInstance().isModfyFlag())
        {

            RentCarLogicManager.getInstance().setModfyFlag(false);

            setSelfDriveTimeFrame(RentCarLogicManager.getInstance().getStartCurrentTime(), RentCarLogicManager.getInstance().getEndCurrentTime());

            sDPTime.resetFramTime(RentCarLogicManager.getInstance().getStartCurrentTime(), RentCarLogicManager.getInstance().getEndCurrentTime());

        }


    }

    private Handler activityHander = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            Intent intent1 = new Intent(getContext(), SelectMotorcycleTypeActivity.class);

            intent1.putExtra("logic_flag", SelfDriveLogicActivity.SELF_DRIVE_FLAG);

            startActivity(intent1);
        }
    };


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //清理自驾租车业务的缓存数据
        RentCarLogicManager.getInstance().clearAllData();
    }
}
