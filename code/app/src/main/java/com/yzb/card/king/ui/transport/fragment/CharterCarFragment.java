package com.yzb.card.king.ui.transport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jamgle.pickerviewlib.view.TimePickerView;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.activity.AddressSearchActivity;
import com.yzb.card.king.ui.manage.AddressSearchManager;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.manage.RentCarLogicManager;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.transport.activity.SelfDriveLogicActivity;
import com.yzb.card.king.ui.appwidget.picks.SelfDrivePickTime;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.ToastUtil;

import java.util.Date;

/**
 * 日租包车碎片
 * Created by dev on 2016/5/25.
 */
public class CharterCarFragment extends Fragment implements View.OnClickListener
{
    //页面参数
    private String city;
    private String cityId;
    private String startPlace;
    private float duration = 0.5f;
    //组件
    private LinearLayout llStartTime, llstartPlace, llCity;
    private TextView tvCity, tvStartPlace, tvStartTime, tvDuration;
    private ImageView ivAdd, ivReduce;
    private Button btnSearch;

    /*
     *常量
     */
    private final int MAX_DAY_NUM = 10;

    private SelfDrivePickTime sDPTime;

    private String startTimeStr;

    private long setStartTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_transport_charter, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData()
    {
            String defaultCity = GlobalApp.getSelectedCity().cityName;


        city = defaultCity;

        cityId = GlobalApp.getSelectedCity().cityId;

        tvCity.setText(defaultCity);

        //默认
        long startCurrentTime = System.currentTimeMillis();

        setStartTime = startCurrentTime;

        long endCurrentTime = startCurrentTime + 24 * 60 * 60 * 1000;

        sDPTime = new SelfDrivePickTime(getContext(), startCurrentTime, endCurrentTime, TimePickerView.Type
                .MONTH_DAY_WEEK_HOUR_MIN);

        sDPTime.setTime(new Date());
        sDPTime.setCyclic(false);
        sDPTime.setCancelable(true);
        sDPTime.setSinglePickTime(R.string.tv_dailyrent_use_bus_date);
        //时间选择后回调
        sDPTime.setOnTimeSelectListener(new SelfDrivePickTime.OnTimeSelectListener()
        {

            @Override
            public void onTimeSelect(long startTime, long endTime)
            {

                setStartTime = startTime;
                //设置用车日期
                startTimeStr = Utils.toData(startTime, 5);

                tvStartTime.setText(startTimeStr);

            }

            @Override
            public void onCancel()
            {

            }
        });


    }

    private void initView(View view)
    {
        llCity = (LinearLayout) view.findViewById(R.id.ll_city);
        llCity.setOnClickListener(this);

        llStartTime = (LinearLayout) view.findViewById(R.id.ll_startTime);
        llStartTime.setOnClickListener(this);

        llstartPlace = (LinearLayout) view.findViewById(R.id.ll_start_place);
        llstartPlace.setOnClickListener(this);

        tvStartTime = (TextView) view.findViewById(R.id.tv_startTime);
        tvCity = (TextView) view.findViewById(R.id.tv_city);
        tvStartPlace = (TextView) view.findViewById(R.id.tv_start_place);
        tvDuration = (TextView) view.findViewById(R.id.tv_duration);

        btnSearch = (Button) view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

        ivAdd = (ImageView) view.findViewById(R.id.iv_add);
        ivAdd.setOnClickListener(this);
        ivReduce = (ImageView) view.findViewById(R.id.iv_reduce);
        ivReduce.setOnClickListener(this);

    }

    public static CharterCarFragment newInstance()
    {

        Bundle args = new Bundle();

        CharterCarFragment fragment = new CharterCarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.ll_startTime://用车时间
                //开启时间滚轮
                sDPTime.show();
                break;
            case R.id.ll_start_place://出发地


                Intent intent1 = new Intent(getActivity(), AddressSearchActivity.class);

                intent1.putExtra("source", "address");

                intent1.putExtra("cityId", cityId);

                intent1.putExtra("city", GlobalApp.getSelectedCity().cityName);

                startActivity(intent1);

                break;
            case R.id.ll_city://所在城市
                Intent intent = new Intent();
                intent.setClass(getActivity(), SelectPlaceActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_add://增加用车时长

                handlerUseTime(true);

                break;
            case R.id.iv_reduce://减少用车时长

                handlerUseTime(false);
                break;
            case R.id.btn_search://确定按钮

                if (checkData())
                {

                    RentCarLogicManager.getInstance().setDailyRentStartTime(setStartTime);

                    RentCarLogicManager.getInstance().setUseBusDuration(duration);

                    Intent intentShop = new Intent(getContext(), SelfDriveLogicActivity.class);

                    intentShop.putExtra("stepIndex", 2);

                    intentShop.putExtra("logic_flag", SelfDriveLogicActivity.DAIL_YRENT_FLAG);

                    startActivity(intentShop);

                }


                break;
            default:
                break;
        }
    }

    /**
     * 检查数据
     *
     * @return
     */
    private boolean checkData()
    {

        boolean flag = true;

        int toastId = 0;

        if (TextUtils.isEmpty(startPlace))
        {

            flag = false;

            toastId = R.string.tv_dailyrent_input_depart_place;

        } else if (TextUtils.isEmpty(startTimeStr))
        {

            flag = false;

            toastId = R.string.tv_dailyrent_input_use_time;

        }

        if (!flag)
        {

            ToastUtil.i(getContext(), getString(toastId));

        }

        return flag;


    }

    private int day = 0;

    /**
     * 控制用车时长 (单位为天)
     *
     * @param flag true表示添加，false表示减少
     */
    private void handlerUseTime(boolean flag)
    {

        // 获取用车时间

        Log.i("LCZ", Utils.toData(setStartTime, 15));
        if (flag)
        {
            if (duration < MAX_DAY_NUM)
            {//用车时长最大为10
                duration = duration + 1;
                tvDuration.setText((int) duration + "");

            }
        } else
        {

            duration = duration - 1;
            if (duration < 1.0f)
            {
                duration = 0.5f;
                tvDuration.setText(duration + "");
            } else
            {
                tvDuration.setText((int) duration + "");
            }
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();

        //获取选择的出发地信息

        if (StringUtils.isEmpty(AddressSearchManager.getInstance().getStreet()))
        {
            startPlace = AddressSearchManager.getInstance().getAddrName();
        } else
        {
            startPlace = AddressSearchManager.getInstance().getStreet();
        }

        tvStartPlace.setText(startPlace);
//        AddressSearchManager.getInstance().setIfSelectAddress(false);
        AddressSearchManager.getInstance().clearData();
//        if (AddressSearchManager.getInstance().isIfSelectAddress() && !TextUtils.isEmpty(AddressSearchManager.getInstance().getAddrName()))
//        {
//
//            startPlace = AddressSearchManager.getInstance().getAddrName();
//
//            tvStartPlace.setText(startPlace);
//
//            AddressSearchManager.getInstance().setIfSelectAddress(false);
//        }

        if (!StringUtils.isEmpty(CitySelectManager.getInstance().getPlaceId()))
        {
            city = CitySelectManager.getInstance().getPlaceName();
            cityId = CitySelectManager.getInstance().getPlaceId();
            tvCity.setText(CitySelectManager.getInstance().getPlaceName());

            CitySelectManager.getInstance().clearData();
        }

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        RentCarLogicManager.getInstance().clearAllData();

        AddressSearchManager.getInstance().clearData();
    }
}
