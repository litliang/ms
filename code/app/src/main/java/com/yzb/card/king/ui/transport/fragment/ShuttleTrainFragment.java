package com.yzb.card.king.ui.transport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jamgle.pickerviewlib.view.TimePickerView;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.activity.AddressSearchActivity;
import com.yzb.card.king.ui.manage.AddressSearchManager;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.transport.activity.ShuttlePlaneTypeActivity;
import com.yzb.card.king.ui.transport.bean.SpecialCar;
import com.yzb.card.king.ui.appwidget.picks.SelfDrivePickTime;
import com.yzb.card.king.ui.appwidget.SlideButtonVertical;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.ToastUtil;

import java.util.Date;

/**
 * Created by ysg on 2016/5/25.
 */
public class ShuttleTrainFragment extends Fragment implements View.OnClickListener
{
    // 页面参数
    private String startPlace;
    private String endPlace;
    private Date startDate;
    //组件
    private LinearLayout llStartPlace, llEndPlace, llStartTime;
    private TextView tvPlace, tvStartPlace, tvEndPlace, tvStartTime;
    private ImageView ivArrow;

    private SlideButtonVertical btnShuttle;

    private String closeText = "送车";
    private String openText = "接车";

    private int SHUTTLE_MAIN_ADDRESS = 1;
    private int SHUTTLE_MAIN_TERMINAL = 2;
    private int currrentType = SHUTTLE_MAIN_ADDRESS;
    private SelfDrivePickTime sDPTime;
    private long startCurrentTime;
    private long endCurrentTime;
    private String title = "出发时间";
    private String trainStationId;
    private boolean isBack = false;
    private boolean isSendTrain = true;

    private Animation operatingAnim0To180;
    private Animation operatingAnim180To360;

    private Button btnSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_transport_shuttletrain, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData()
    {
        tvStartPlace.setText(startPlace);
        tvEndPlace.setText(endPlace);
        tvStartTime.setText(DateUtil.date2String(startDate, "MM-dd HH:mm"));
    }

    private void initView(View view)
    {

        llStartPlace = (LinearLayout) view.findViewById(R.id.ll_start_place);
        llStartPlace.setOnClickListener(this);

        llEndPlace = (LinearLayout) view.findViewById(R.id.ll_end_place);
        llEndPlace.setOnClickListener(this);

        llStartTime = (LinearLayout) view.findViewById(R.id.ll_startTime);
        llStartTime.setOnClickListener(this);

        tvPlace = (TextView) view.findViewById(R.id.tvPlace);
        tvStartPlace = (TextView) view.findViewById(R.id.tv_start_place);
        tvEndPlace = (TextView) view.findViewById(R.id.tv_end_place);
        tvStartTime = (TextView) view.findViewById(R.id.tv_startTime);

        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);

        btnShuttle = (SlideButtonVertical) view.findViewById(R.id.btn_shuttle);
        btnShuttle.setCloseText(closeText);
        btnShuttle.setOpenText(openText);
        btnShuttle.setOnToggleStateChangeListener(new SlideButtonVertical.OnToggleStateChangeListener()
        {
            @Override
            public void onToggleStateChange(SlideButtonVertical.ToggleState state)
            {
                changeState(state);
            }
        });

        btnSearch = (Button) view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

        sDPTime = new SelfDrivePickTime(getContext(), startCurrentTime, endCurrentTime, TimePickerView.Type.MONTH_DAY_WEEK_HOUR_MIN);
        sDPTime.setTime(new Date());
        sDPTime.setCyclic(false);
        sDPTime.setCancelable(true);
        sDPTime.setTitle(title);
        sDPTime.setLlTimeLayoutVisibility(View.GONE);
        sDPTime.setOnTimeSelectListener(new SelfDrivePickTime.OnTimeSelectListener()
        {

            @Override
            public void onTimeSelect(long startTime, long endTime)
            {
                startDate.setTime(startTime);
                setStartTime();
                toNextActivity();
            }

            @Override
            public void onCancel()
            {

            }
        });

        operatingAnim0To180 = AnimationUtils.loadAnimation(getActivity(), R.anim.shuttle_rotate_0_180);
        operatingAnim0To180.setFillAfter(true);

        operatingAnim180To360 = AnimationUtils.loadAnimation(getActivity(), R.anim.shuttle_rotate_180_360);
        operatingAnim180To360.setFillAfter(true);
    }

    public static ShuttleTrainFragment newInstance()
    {

        Bundle args = new Bundle();

        ShuttleTrainFragment fragment = new ShuttleTrainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_search:
                toNextActivity();
                break;
            case R.id.ll_start_place://火车站
                chooseTerminal();
                break;
            case R.id.ll_end_place://目的地
                choosePlace();
                break;
            case R.id.ll_startTime://用车时间
                chooseTime();
                break;
        }
    }

    //选择时间
    private void chooseTime()
    {
        startDate = new Date();

        sDPTime.show();
    }

    private void setStartTime()
    {
        tvStartTime.setText(DateUtil.date2String(startDate, "MM-dd HH:mm"));
    }

    //目的地
    private void choosePlace()
    {
        currrentType = SHUTTLE_MAIN_ADDRESS;
        Intent intent = new Intent(getActivity(), AddressSearchActivity.class);
        intent.putExtra("source", "address");
        intent.putExtra("city", GlobalApp.getSelectedCity().cityName);
        startActivity(intent);
    }

    //选择火车站
    private void chooseTerminal()
    {
        currrentType = SHUTTLE_MAIN_TERMINAL;
        Intent intent = new Intent(getActivity(), AddressSearchActivity.class);
        intent.putExtra("source", "terminal");
        intent.putExtra("city", GlobalApp.getSelectedCity().cityName);
        startActivity(intent);
    }

    //接送车改变事件
    private void changeState(SlideButtonVertical.ToggleState state)
    {
        if (state == SlideButtonVertical.ToggleState.open)
        {//接车
            ivArrow.startAnimation(operatingAnim0To180);

            isSendTrain = false;

            tvPlace.setText(getResources().getString(R.string.shuttle_tv_place));
            tvEndPlace.setHint(getResources().getString(R.string.shuttle_select_place));
        } else
        {
            ivArrow.startAnimation(operatingAnim180To360);

            isSendTrain = true;

            tvPlace.setText(getResources().getString(R.string.shuttle_train_chufadi));
            tvEndPlace.setHint(getResources().getString(R.string.shuttle_train_chufadihint));
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (currrentType == SHUTTLE_MAIN_ADDRESS)
        {
            if (StringUtils.isEmpty(AddressSearchManager.getInstance().getStreet()))
            {
                endPlace = AddressSearchManager.getInstance().getAddrName();
                tvEndPlace.setText(endPlace);
            } else
            {
                endPlace = AddressSearchManager.getInstance().getStreet();
                tvEndPlace.setText(endPlace);
            }
        } else
        {
            trainStationId = "1";
            if (StringUtils.isEmpty(AddressSearchManager.getInstance().getStreet()))
            {
                startPlace = AddressSearchManager.getInstance().getAddrName();
                tvStartPlace.setText(startPlace);
            } else
            {
                startPlace = AddressSearchManager.getInstance().getStreet();
                tvStartPlace.setText(startPlace);
            }
        }
        AddressSearchManager.getInstance().clearData();
    }

    private void toNextActivity()
    {
        if (isBack)
        {
            isBack = false;
            return;
        }
        if (StringUtils.isEmpty(startPlace))
        {
            ToastUtil.i(this.getActivity(),R.string.shuttle_train_stationhint);
            return;
        }

        if (StringUtils.isEmpty(endPlace))
        {
            ToastUtil.i(this.getActivity(),R.string.shuttle_train_chufadihint);
            return;
        }

        if (null == startDate)
        {
            ToastUtil.i(this.getActivity(),R.string.tv_dailyrent_input_use_time);
            return;
        }


        Intent intent = new Intent(getActivity(), ShuttlePlaneTypeActivity.class);
        SpecialCar specialCar = new SpecialCar();
        specialCar.source = 2;
        specialCar.startDate = startDate;
        if (isSendTrain)
        {
            specialCar.startPlace = startPlace;
            specialCar.endPlace = endPlace;
        } else
        {
            specialCar.endPlace = startPlace;
            specialCar.startPlace = endPlace;
        }
        intent.putExtra("specialCar", specialCar);
        intent.putExtra("trainStationId", trainStationId);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1:
                isBack = true;
                break;
        }
    }
}
