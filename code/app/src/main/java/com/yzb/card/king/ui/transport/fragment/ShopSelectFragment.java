package com.yzb.card.king.ui.transport.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jamgle.pickerviewlib.view.TimePickerView;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.appwidget.RecyclerViewUnderLine;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.other.activity.AddressSearchActivity;
import com.yzb.card.king.ui.manage.AddressSearchManager;
import com.yzb.card.king.ui.manage.RentCarLogicManager;
import com.yzb.card.king.ui.transport.activity.SelfDriveLogicActivity;
import com.yzb.card.king.ui.discount.bean.BusShopBean;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.appwidget.picks.SelfDrivePickTime;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.transport.adapter.ShopeSelectAdatper;
import com.yzb.card.king.ui.transport.presenter.ShopeSelectPersenter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门店选择
 * Created by tarena on 2016/5/27.
 */
public class ShopSelectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BaseViewLayerInterface
{

    private SwitchFragmentCallBack callBack;//碎片传递接口

    private SwipeRefreshLayout swipeRefresh;

    private TextView tvStartTime, tvEndTime;

    private TextView tvAddress;

    private ShopeSelectAdatper adapter;


    private LinearLayout llDistanceFarNear, llPriceHighLow;

    private SelfDrivePickTime sDPTime;


    private int currentSort = 1;

    private List<BusShopBean> busShopBeanList;

    private HeadFootRecyclerView lvShopSelect;

    private int logicFlag = SelfDriveLogicActivity.SELF_DRIVE_FLAG;

    private ShopeSelectPersenter persenter;
    private String address = "";

    private LinearLayout poiSearch;

    public ShopSelectFragment()
    {


    }

    public void setCallBack(SwitchFragmentCallBack callBack)
    {
        this.callBack = callBack;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_shopselect, null);

        Bundle bundle = getArguments();

        if (bundle != null)
        {

            logicFlag = bundle.getInt("logic_flag");
            address = bundle.getString("address");
        }

        initView(view);

        initData();

        return view;
    }

    private void initData()
    {
        swipeRefresh.setRefreshing(true);

        currentSort = 1;

        sendRequest(currentSort, false);
    }

    private void initView(View view)
    {

        persenter = new ShopeSelectPersenter(this);
        this.busShopBeanList = new ArrayList<>();

        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        if (TextUtils.isEmpty(address))
        {
            tvAddress.setText(" 约70家");
        } else
        {
            tvAddress.setText(address + " 约70家");
        }


        llDistanceFarNear = (LinearLayout) view.findViewById(R.id.llDistanceFarNear);

        llPriceHighLow = (LinearLayout) view.findViewById(R.id.llPriceHighLow);

        llPriceHighLow.setOnClickListener(myListener);

        llDistanceFarNear.setOnClickListener(myListener);
        poiSearch = (LinearLayout) view.findViewById(R.id.poi_search);
        poiSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ShopSelectFragment.this.getActivity()
                        , AddressSearchActivity.class);
                intent.putExtra("source", "noGPS");
                intent.putExtra("city", GlobalApp.getPositionedCity().cityName);
                startActivity(intent);
            }
        });
//        etSearch = (EditText) view.findViewById(R.id.etSearch);
//
//        etSearch.setFocusable(false);
//
//        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
//        {
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
//            {
//                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent
//                        .KEYCODE_ENTER))
//                {
//                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context
//                            .INPUT_METHOD_SERVICE);
//
//                    imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
//
//                    sendRequest(currentSort, true);
//
//                    return true;
//                }
//                return false;
//            }
//        });
//        view.findViewById(R.id.ibSearch).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                if (etSearch.getText().toString().length() > 0)
//                {
//
//                    sendRequest(currentSort, true);
//                }
//
//            }
//        });

        lvShopSelect = (HeadFootRecyclerView) view.findViewById(R.id.lvShopSelect);

        adapter = new ShopeSelectAdatper(this.busShopBeanList, ShopSelectFragment.this.getActivity());
        lvShopSelect.setLayoutManager(new LinearLayoutManager(ShopSelectFragment.this.getActivity(), LinearLayoutManager.VERTICAL, false));
        lvShopSelect.setAdapter(adapter);
        lvShopSelect.addItemDecoration(new RecyclerViewUnderLine(this.getActivity(), LinearLayoutManager.VERTICAL));
        adapter.setmOnItemClickListener(onItemClickListener);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.sRL);

        SwipeRefreshSettings.setAttrbutes(getActivity(), swipeRefresh);

        swipeRefresh.setOnRefreshListener(this);

        tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);

        tvEndTime = (TextView) view.findViewById(R.id.tvEndTime);

        LinearLayout llResetTime = (LinearLayout) view.findViewById(R.id.llResetTime);

        llResetTime.setOnClickListener(myListener);

        setUiTime();

    }

    private void setUiTime()
    {

        long startTime = 0L;

        long endTime = 0L;
        if (logicFlag == SelfDriveLogicActivity.SELF_DRIVE_FLAG)
        {

            //设置自驾租车时间
            startTime = RentCarLogicManager.getInstance().getStartCurrentTime();

            endTime = RentCarLogicManager.getInstance().getEndCurrentTime();

            String startTimeStr = Utils.toData(startTime, 7);

            String endTimeStr = Utils.toData(endTime, 7);

            tvStartTime.setText(getString(R.string.tv_selfdrive_get) + " " + startTimeStr);

            tvEndTime.setText(getString(R.string.tv_selfdrive_back) + " " + endTimeStr);

            tvEndTime.setVisibility(View.VISIBLE);

        } else if (logicFlag == SelfDriveLogicActivity.DAIL_YRENT_FLAG)
        {

            startTime = RentCarLogicManager.getInstance().getDailyRentStartTime();

            String startTimeStr = Utils.toData(startTime, 7);

            tvStartTime.setText(getString(R.string.tv_selfdrive_get) + " " + startTimeStr);

            tvEndTime.setVisibility(View.GONE);
        }


        sDPTime = new SelfDrivePickTime(getActivity(), startTime, endTime, TimePickerView.Type.MONTH_DAY_WEEK_HOUR_MIN);

        sDPTime.setTime(new Date());

        sDPTime.setCyclic(false);

        if (logicFlag == SelfDriveLogicActivity.SELF_DRIVE_FLAG)
        {

        } else if (logicFlag == SelfDriveLogicActivity.DAIL_YRENT_FLAG)
        {

            sDPTime.setSinglePickTime(R.string.tv_dailyrent_use_bus_date);
        }

        sDPTime.setCancelable(true);
        //时间选择后回调
        sDPTime.setOnTimeSelectListener(new SelfDrivePickTime.OnTimeSelectListener()
        {

            @Override
            public void onTimeSelect(long startTime, long endTime)
            {

                RentCarLogicManager.getInstance().setModfyFlag(true);

                if (logicFlag == SelfDriveLogicActivity.SELF_DRIVE_FLAG)
                {

                    String startTimeStr = Utils.toData(startTime, 7);
                    String endTimeStr = Utils.toData(endTime, 7);

                    tvStartTime.setText(getString(R.string.tv_selfdrive_get) + " " + startTimeStr);

                    tvEndTime.setText(getString(R.string.tv_selfdrive_back) + " " + endTimeStr);

                    RentCarLogicManager.getInstance().setStartCurrentTime(startTime);

                    RentCarLogicManager.getInstance().setEndCurrentTime(endTime);

                } else if (logicFlag == SelfDriveLogicActivity.DAIL_YRENT_FLAG)
                {

                    String startTimeStr = Utils.toData(startTime, 7);

                    tvStartTime.setText(getString(R.string.tv_selfdrive_get) + " " + startTimeStr);

                    RentCarLogicManager.getInstance().setDailyRentStartTime(startTime);
                }

            }

            @Override
            public void onCancel()
            {

            }
        });
    }

    ShopeSelectAdatper.OnItemClickListener onItemClickListener = new ShopeSelectAdatper.OnItemClickListener()
    {
        @Override
        public void onItemClick(Object o)
        {
            if (logicFlag == SelfDriveLogicActivity.SELF_DRIVE_FLAG)
            {
                //if (logicFlag == SelfDriveLogicActivity.SELF_DRIVE_FLAG) {

                callBack.transmitFragmentIndex(3);//关闭SelfDriveLogicActivity

                RentCarLogicManager.getInstance().setO(o);
            } else if (logicFlag == SelfDriveLogicActivity.DAIL_YRENT_FLAG)
            {
                //} else if (logicFlag == SelfDriveLogicActivity.DAIL_YRENT_FLAG) {

                callBack.transmitFragmentIndex(3);//进入车辆选择
                Log.i("LCZ", "进入车辆选择?");

                RentCarLogicManager.getInstance().setO(o);
            }
        }
    };

    /**
     * 按钮监听器
     */
    private View.OnClickListener myListener = new View.OnClickListener()
    {


        @Override
        public void onClick(View v)
        {

            switch (v.getId())
            {

                case R.id.llDistanceFarNear://距离远近
                    currentSort = 1;

                    sendRequest(currentSort, true);

                    break;
                case R.id.llPriceHighLow://价格高低

                    currentSort = 2;

                    sendRequest(currentSort, true);
                    break;
                case R.id.llResetTime://设置时间

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

                    sDPTime.show();
                    break;
                default:
                    break;
            }


        }
    };

    @Override
    public void onResume()
    {
        LogUtil.LCi("    返回了   ShopSelectFragment  ");
        super.onResume();
        if (StringUtils.isEmpty(AddressSearchManager.getInstance().getStreet()))
        {
            address = AddressSearchManager.getInstance().getAddrName();
        } else
        {
            address = AddressSearchManager.getInstance().getStreet();
        }
        if (StringUtils.isEmpty(address)) return;
        tvAddress.setText(address + " 约70家");
        sendRequest(currentSort, true);
        AddressSearchManager.getInstance().clearData();
    }

    @Override
    public void onRefresh()
    {

        sendRequest(currentSort, false);

    }





    /**
     * 发送请求
     * 排序  1：距离；2：价格由低到高；3：价格由高到低；
     */
    private void sendRequest(final int stort, boolean falg)
    {

        Location city = GlobalApp.getSelectedCity();
        Map<String, Object> param = new HashMap<>();
        long startTime = 0;

        long endTime = 0;
        if (logicFlag == SelfDriveLogicActivity.SELF_DRIVE_FLAG)
        {

            startTime = RentCarLogicManager.getInstance().getStartCurrentTime();
            endTime = RentCarLogicManager.getInstance().getEndCurrentTime();

        } else if (logicFlag == SelfDriveLogicActivity.DAIL_YRENT_FLAG)
        {
            startTime = RentCarLogicManager.getInstance().getDailyRentStartTime();

            endTime = startTime + (long) RentCarLogicManager.getInstance().getUseBusDuration() * 24 * 60 * 60
                    * 1000;

        }
        param.put("cityId", RentCarLogicManager.getInstance().getPlaceId());//城市ID
        param.put("startTime", startTime);//取车时间
        param.put("endTime", endTime);//还车时间
        param.put("address", address);//地址
        param.put("lng", city.longitude);//经度
        param.put("lat", city.latitude);//纬度
        param.put("sort", stort);//排序  1：距离；2：价格由低到高；3：价格由高到低；

        persenter.sendShopeSelectRequest(param, CardConstant.RENTAL_CAR_STORE_LIST_URL);

    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        List<BusShopBean> shopBeen = (List<BusShopBean>) o;
        this.busShopBeanList.clear();
        this.busShopBeanList.addAll(shopBeen);
        this.lvShopSelect.notifyData();

        this.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        Toast.makeText(ShopSelectFragment.this.getActivity(), o+"", Toast.LENGTH_SHORT).show();
    }
}
