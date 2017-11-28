package com.yzb.card.king.ui.transport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.jamgle.pickerviewlib.view.TimePickerView;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.activity.AddressSearchActivity;
import com.yzb.card.king.ui.manage.AddressSearchManager;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.transport.activity.ShuttlePlaneTypeActivity;
import com.yzb.card.king.ui.transport.bean.SpecialCar;
import com.yzb.card.king.ui.appwidget.picks.SelfDrivePickTime;
import com.yzb.card.king.ui.discount.fragment.BaseFragment;
import com.yzb.card.king.ui.manage.RentCarLogicManager;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.appwidget.SlideButtonVertical;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.RegexUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by dev on 2016/5/25.
 * 接送机
 * gengqiyun 2016.9.9  代码优化；
 */
public class ShuttlePlaneFragment extends BaseFragment implements View.OnClickListener
{
    private String startPlace = "";
    private String endPlace = "";
    private Date useCarTime; //用车时间；

    private EditText etFlightNumber; //航班号；
    private SlideButton toggleLoadBaggage; //托运行李；
    private SlideButtonVertical slideShuttleAirpoart; //接机/送机  true:打开，接机；false：关闭，送机；

    private View llHeaderPart; //航班号，是否托运行李块；

    private TextView tvPlaceTxt;
    private TextView tvAirportTerminal; //航站楼；
    private TextView tvPlaceInput;
    private TextView tvUsecarTime; //用车时间；
    private View arrowView; //出发地，目的地对调view；

    private SelfDrivePickTime sDPTime;

    private Animation rotateStartAnim; //开始旋转动画；
    private Animation rotateEndAnim;//结束旋转动画；

    private int SHUTTLE_MAIN_ADDRESS = 1;   //出发地/目的地；
    private int SHUTTLE_MAIN_TERMINAL = 2;  //航站楼
    /**
     * 当前操作类型；SHUTTLE_MAIN_ADDRESS 或 SHUTTLE_MAIN_TERMINAL
     */
    private int currrentType = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_transport_shuttleplane, container, false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        etFlightNumber = (EditText) view.findViewById(R.id.et_flight_number);
        etFlightNumber.addTextChangedListener(flightNumberTxtWatcher);
        etFlightNumber.setOnFocusChangeListener(flightNumberFocusChangeListener);
        //行李托运；
        toggleLoadBaggage = (SlideButton) view.findViewById(R.id.toggle_load_baggage);

        //接机/送机  切换；
        slideShuttleAirpoart = (SlideButtonVertical) view.findViewById(R.id.slide_shuttle_airpoart);
        slideShuttleAirpoart.setOnToggleStateChangeListener(shuttlePToggleListener);

        ////航站楼panel；
        view.findViewById(R.id.ll_airport_terminal).setOnClickListener(this);
        view.findViewById(R.id.ll_end_place).setOnClickListener(this);
        view.findViewById(R.id.ll_usecar_time).setOnClickListener(this);

        llHeaderPart = view.findViewById(R.id.ll_go_panel);
        llHeaderPart.setOnClickListener(this);

        //出发地或目的地 左侧文本；
        tvPlaceTxt = (TextView) view.findViewById(R.id.tv_place_txt);

        //出发地或目的地输入区；
        tvPlaceInput = (TextView) view.findViewById(R.id.tv_place_input);
        //航站楼
        tvAirportTerminal = (TextView) view.findViewById(R.id.tv_airport_terminal);

        //用车时间；
        tvUsecarTime = (TextView) view.findViewById(R.id.tv_usecar_time);
        //搜索；
        view.findViewById(R.id.btn_search).setOnClickListener(this);
        arrowView = view.findViewById(R.id.iv_arrow);

        rotateStartAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.shuttle_rotate_0_180);
        rotateStartAnim.setFillAfter(true);
        rotateEndAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.shuttle_rotate_180_360);
        rotateEndAnim.setFillAfter(true);

        initTimeWheelView();

        slideShuttleAirpoart.setToggleState(SlideButtonVertical.ToggleState.close);
    }

    /**
     * 初始化时间选择器；
     */
    private void initTimeWheelView()
    {
        long startCurrentTime = System.currentTimeMillis();
        long endCurrentTime = startCurrentTime + 24 * 60 * 60 * 1000;
        sDPTime = new SelfDrivePickTime(getContext(), startCurrentTime, endCurrentTime,
                TimePickerView.Type.MONTH_DAY_WEEK_HOUR_MIN);
        sDPTime.setTitle(getActivity().getResources().getString(R.string.shuttle_depart_time));
        sDPTime.setLlTimeLayoutVisibility(View.GONE);
        sDPTime.setTime(new Date());
        sDPTime.setCyclic(false);
        sDPTime.setCancelable(true);
        sDPTime.setOnTimeSelectListener(timeSelectListener);
    }

    /**
     * 时间选择器回调
     */
    private SelfDrivePickTime.OnTimeSelectListener timeSelectListener = new SelfDrivePickTime.OnTimeSelectListener()
    {
        @Override
        public void onTimeSelect(long startTime, long endTime)
        {
            RentCarLogicManager.getInstance().setStartCurrentTime(startTime);
            RentCarLogicManager.getInstance().setEndCurrentTime(endTime);
            setSelfDriveTimeFrame(startTime, endTime);
        }

        @Override
        public void onCancel()
        {
        }
    };

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_airport_terminal:  //航站楼
                currrentType = SHUTTLE_MAIN_TERMINAL;
                Intent airpoartIntent = new Intent(getActivity(), AddressSearchActivity.class);
                airpoartIntent.putExtra("source", "terminal");
                airpoartIntent.putExtra("city", GlobalApp.getSelectedCity().cityName);
                startActivity(airpoartIntent);
                break;
            case R.id.ll_end_place:  //出发地或目的地
                currrentType = SHUTTLE_MAIN_ADDRESS;
                Intent placeIntent = new Intent(getActivity(), AddressSearchActivity.class);
                placeIntent.putExtra("source", "address");
                placeIntent.putExtra("city", GlobalApp.getSelectedCity().cityName);
                startActivity(placeIntent);
                break;
            case R.id.ll_usecar_time://用车时间
                sDPTime.show();
                break;
            case R.id.btn_search:  //搜索；
                startSearch();
                break;
        }
    }

    /**
     * 接机/送机切换；
     * true:打开，接机；false：关闭，送机；
     */
    private SlideButtonVertical.OnToggleStateChangeListener shuttlePToggleListener = new SlideButtonVertical.OnToggleStateChangeListener()
    {
        @Override
        public void onToggleStateChange(SlideButtonVertical.ToggleState state)
        {
            boolean flag = slideShuttleAirpoart.getState() == SlideButtonVertical.ToggleState.open ? true : false;
            arrowView.startAnimation(flag ? rotateStartAnim : rotateEndAnim);
            llHeaderPart.setVisibility(flag ? View.VISIBLE : View.GONE);
            tvPlaceTxt.setText(flag ? getString(R.string.shuttle_tv_place) : getString(R.string.shuttle_train_chufadi));
            tvPlaceInput.setHint(flag ? getString(R.string.shuttle_select_place) : getString(R.string.shuttle_train_chufadihint));
        }
    };
    /**
     * 航班号焦点改变监听；
     */
    private View.OnFocusChangeListener flightNumberFocusChangeListener = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                startSearch();
            }
        }
    };

    /**
     * 航班号输入监听；
     */
    private MyTextWatcher flightNumberTxtWatcher = new MyTextWatcher()
    {
        @Override
        public void afterTextChange(String s)
        {
            //最多6位；
            if (s.length() > 6)
            {
                s = s.substring(0, 6);
                etFlightNumber.setText(s);
            }
            //符合条件；联网查询；
            if (RegexUtil.validFlightNumber(s))
            {
                toastCustom("该公司无此航班号");
            }
        }
    };

    public static ShuttlePlaneFragment newInstance()
    {

        Bundle args = new Bundle();

        ShuttlePlaneFragment fragment = new ShuttlePlaneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 开始搜索；
     */
    private void startSearch()
    {
        //true:打开，接机；false：关闭，送机；
        boolean airpoartFlag = slideShuttleAirpoart.getState() == SlideButtonVertical.ToggleState.open ? true : false;
        //航班号；
        String flightNumber = etFlightNumber.getText().toString().trim();
        //true:托运；false：非；
        boolean isLoadBaggage = toggleLoadBaggage.getState() == SlideButton.ToggleState.open ? true : false;

        // 航站楼；
        String airportTerminal = tvAirportTerminal.getText().toString().trim();
        // 出发地/目的地；
        String place = tvPlaceInput.getText().toString().trim();

        //接机,航班号不能为空
        if (airpoartFlag && isEmpty(flightNumber))
        {
            toastCustom("航班号不能为空");
            return;
        }
        if (isEmpty(airportTerminal))
        {
            toastCustom("航站楼不能为空");
            return;
        }
        if (isEmpty(place))
        {
            toastCustom(airpoartFlag ? "目的地不能为空" : "出发地不能为空");
            return;
        }
        if (useCarTime == null)
        {
            toastCustom("用车时间不能为空");
            return;
        }
        goNextActivity();
    }

    private void goNextActivity()
    {
        Intent intent = new Intent(getActivity(), ShuttlePlaneTypeActivity.class);
        SpecialCar specialCar = new SpecialCar();
        specialCar.startDate = useCarTime;
        specialCar.startPlace = startPlace;
        specialCar.endPlace = endPlace;
        specialCar.source = 1;
        intent.putExtra("specialCar", specialCar);
        startActivity(intent);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateAddressByType();
    }

    /**
     * 获取AddressSearchManager中的数据，并更新；
     */
    private void updateAddressByType()
    {
        AddressSearchManager asm = AddressSearchManager.getInstance();
        //出发地/目的地  选择；
        if (currrentType == SHUTTLE_MAIN_ADDRESS)
        {
            endPlace = isEmpty(asm.getStreet()) ? asm.getAddrName() : asm.getStreet();
            tvPlaceInput.setText(endPlace);
        } else if (currrentType == SHUTTLE_MAIN_TERMINAL)
        {
            startPlace = isEmpty(asm.getStreet()) ? asm.getAddrName() : asm.getStreet();
            tvAirportTerminal.setText(startPlace);
        }
        asm.clearData();
    }

    /**
     * 设置自驾租车时段
     *
     * @param startTime
     * @param endTime
     */
    private void setSelfDriveTimeFrame(long startTime, long endTime)
    {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(startTime);
        int startmonth = startCalendar.get(Calendar.MONTH) + 1;
        int startday = startCalendar.get(Calendar.DAY_OF_MONTH);
        int starthours = startCalendar.get(Calendar.HOUR_OF_DAY);
        int startminute = startCalendar.get(Calendar.MINUTE);

        StringBuffer startSb = new StringBuffer();
        startSb.append(startmonth).append("月").append(startday).append("日 ").append(starthours < 10 ? "0" + starthours : starthours).append(":").append(startminute);
        tvUsecarTime.setText(startSb.toString());
        this.useCarTime = DateUtil.string2Date(DateUtil.long2String(startTime, DateUtil.DATE_FORMAT_DATE_TIME));
        //startSearch();
    }
}
