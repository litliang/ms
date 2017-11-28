package com.yzb.card.king.ui.transport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jamgle.pickerviewlib.view.TimePickerView;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.activity.AddressSearchActivity;
import com.yzb.card.king.ui.transport.activity.PriceEvaluateActivity;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.transport.activity.ShuttlePlaneTypeActivity;
import com.yzb.card.king.ui.transport.bean.SpecialCar;
import com.yzb.card.king.ui.appwidget.picks.SelfDrivePickTime;
import com.yzb.card.king.ui.manage.AddressSearchManager;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.UiUtils;

import java.util.Date;

/**
 * Created by dev on 2016/5/25.
 * 专车出行
 */
public class SpecialCarFragment extends Fragment implements View.OnClickListener
{
    private static final int START_ADDRESS = 1;
    private static final int END_ADDRESS = 2;
    //页面参数
    private Date startDate;
    private String startCity = "";
    private String endCity = "";
    private float price = 10.0f;
    //组件
    private LinearLayout llStartTime, llStartCity, llEndCity;
    private RelativeLayout rlPrice;
    private TextView tvStartTime, tvStartCity, tvEndCity, tvPrice;
    private Button btnSearch;
    private SelfDrivePickTime sDPTime;
    private long startCurrentTime;
    private long endCurrentTime;
    private String title = "出发时间";

    private int currentReq;
    private SpecialCar specialCar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_transport_special, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData()
    {
        specialCar = new SpecialCar();
        startDate = new Date();

        tvStartTime.setText("现在用车");
        tvPrice.setText(String.valueOf(price));
        tvStartCity.setText(startCity);
        tvEndCity.setText(endCity);

        sDPTime = new SelfDrivePickTime(getContext(), startCurrentTime, endCurrentTime
                , TimePickerView.Type.MONTH_DAY_WEEK_HOUR_MIN);

        sDPTime.setTime(new Date());
        sDPTime.setCyclic(false);
        sDPTime.setCancelable(true);
        sDPTime.setTitle(title);
        sDPTime.setLlTimeLayoutVisibility(View.GONE);
        //时间选择后回调
        sDPTime.setOnTimeSelectListener(new SelfDrivePickTime.OnTimeSelectListener()
        {

            @Override
            public void onTimeSelect(long startTime, long endTime)
            {
                startDate.setTime(startTime);
                setStartTime();
            }

            @Override
            public void onCancel()
            {

            }
        });
    }

    private void initView(View view)
    {
        llStartCity = (LinearLayout) view.findViewById(R.id.ll_start_city);
        llStartCity.setOnClickListener(this);

        llStartTime = (LinearLayout) view.findViewById(R.id.ll_startTime);
        llStartTime.setOnClickListener(this);

        llEndCity = (LinearLayout) view.findViewById(R.id.ll_end_city);
        llEndCity.setOnClickListener(this);

        rlPrice = (RelativeLayout) view.findViewById(R.id.rl_price);
        rlPrice.setOnClickListener(this);

        tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
        tvStartCity = (TextView) view.findViewById(R.id.tv_start_city);
        tvEndCity = (TextView) view.findViewById(R.id.tv_end_city);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);

        btnSearch = (Button) view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

    }

    public static SpecialCarFragment newInstance()
    {

        Bundle args = new Bundle();

        SpecialCarFragment fragment = new SpecialCarFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_startTime://用车时间
                sDPTime.show();
                break;
            case R.id.ll_start_city://出发地
                currentReq = START_ADDRESS;
                getPalace();
                break;
            case R.id.ll_end_city://目的地
                currentReq = END_ADDRESS;
                getPalace();
                break;
            case R.id.rl_price://价格预估
                goEvaluate();
                break;
            case R.id.btn_search://确定按钮
                search();
                break;
        }
    }

    /**
     * 跳转车价预估界面
     *
     * @author ysg
     * created at 2016/6/1 16:38
     */
    private void goEvaluate()
    {
        Intent intent = new Intent(getContext(), PriceEvaluateActivity.class);
        startActivity(intent);
    }

    /**
     * 去车型选择页面
     *
     * @author ysg
     * created at 2016/6/1 11:25
     */
    private void search()
    {

        if (!validDate()) return;

        Intent intent = new Intent(getContext(), ShuttlePlaneTypeActivity.class);
        specialCar.startDate = startDate;
        specialCar.startPlace = startCity;
        specialCar.endPlace = endCity;
        specialCar.source = 3;
        intent.putExtra("specialCar", specialCar);
        startActivity(intent);
    }

    private boolean validDate()
    {
        boolean flag = true;
        if (startDate == null)
        {
            UiUtils.shortToast("请选择用车时间");
            flag = false;
        }
        if (TextUtils.isEmpty(startCity))
        {
            UiUtils.shortToast("请选择出发地");
            flag = false;
        }
        if (TextUtils.isEmpty(endCity))
        {
            UiUtils.shortToast("请选择目的地");
            flag = false;
        }
        return flag;
    }

    private void getPalace()
    {
        Intent intent = new Intent(getActivity(), AddressSearchActivity.class);
        intent.putExtra("source", "address");
        intent.putExtra("city", GlobalApp.getSelectedCity().cityName);
        startActivity(intent);
    }

    private void setStartTime()
    {

        tvStartTime.setText(DateUtil.date2String(startDate, "MM-dd HH:mm"));
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (currentReq == START_ADDRESS)
        {
            if (StringUtils.isEmpty(AddressSearchManager.getInstance().getStreet()))
            {
                startCity = AddressSearchManager.getInstance().getAddrName();
            } else
            {
                startCity = AddressSearchManager.getInstance().getStreet();
            }
            tvStartCity.setText(startCity);
        } else
        {
            if (StringUtils.isEmpty(AddressSearchManager.getInstance().getStreet()))
            {
                endCity = AddressSearchManager.getInstance().getAddrName();
            } else
            {
                endCity = AddressSearchManager.getInstance().getStreet();
            }
            tvEndCity.setText(endCity);
        }

        AddressSearchManager.getInstance().clearData();
    }
}
