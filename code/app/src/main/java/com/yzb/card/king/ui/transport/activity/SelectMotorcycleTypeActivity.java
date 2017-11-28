package com.yzb.card.king.ui.transport.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.base.BaseFragmentActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.BusShopBean;
import com.yzb.card.king.ui.discount.bean.BusTypeBean;
import com.yzb.card.king.ui.manage.AddressSearchManager;
import com.yzb.card.king.ui.manage.RentCarLogicManager;
import com.yzb.card.king.ui.transport.adapter.SelectMotorcycleAdapter;
import com.yzb.card.king.ui.transport.presenter.CarPersenter;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择车型
 * Created by tarena on 2016/5/28.
 */
public class SelectMotorcycleTypeActivity extends BaseFragmentActivity implements SwipeRefreshLayout
        .OnRefreshListener,BaseViewLayerInterface
{

    public static String CLASS_NAME;

    private List<BusTypeBean> busTypeList;

    private SwipeRefreshLayout sRL;

    private SelectMotorcycleAdapter adapter;

    private int logicFlag;

    private HeadFootRecyclerView lvSelfDriveMotocycleType;

    private CarPersenter persenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        CLASS_NAME = SelectMotorcycleTypeActivity.this.getClass().getName();

        setContentView(R.layout.activity_select_motorcycle_type);

        logicFlag = getIntent().getIntExtra("logic_flag", SelfDriveLogicActivity.SELF_DRIVE_FLAG);

        registerBoradcastReceiver();

        initView();

        initData();
    }

    public void backAction(View view)
    {

        RentCarLogicManager.getInstance().setFlagBack(false);

        finish();

    }

    private void initData()
    {
        if (logicFlag == SelfDriveLogicActivity.SELF_DRIVE_FLAG)
        {//自驾租车

            sendSelfCardRequest();

        } else if (logicFlag == SelfDriveLogicActivity.DAIL_YRENT_FLAG)
        {//日包租车

            sendDailyRentRequest();
        }

    }

    private void sendSelfCardRequest()
    {
        Map<String, Object> param = new HashMap<>();
        long startTime = RentCarLogicManager.getInstance().getStartCurrentTime();
        long endTime = RentCarLogicManager.getInstance().getEndCurrentTime();
        param.put("cityId", RentCarLogicManager.getInstance().getPlaceId());//城市ID
        param.put("startDate", startTime);//取车时间
        param.put("endDate", endTime);//还车时间
        param.put("source", 4);
        BusShopBean busShopBean = (BusShopBean) RentCarLogicManager.getInstance().getO();
        param.put("storeId", busShopBean.storeId);//门店Id
        persenter.sendSelfRequest(param, CardConstant.CAR_TYPE_LIST_URL);
    }

    /**
     * 发送日租包車车型请求
     */
    private void sendDailyRentRequest()
    {

        Map<String, Object> param = new HashMap<>();
        long startTime = RentCarLogicManager.getInstance().getStartCurrentTime();
        long endTime = RentCarLogicManager.getInstance().getEndCurrentTime();
        param.put("cityId", AddressSearchManager.getInstance().getAddrId());//城市ID
        param.put("startDate", startTime);//取车时间
        param.put("endDate", endTime);//还车时间
        param.put("source", 5);
        BusShopBean busShopBean = (BusShopBean) RentCarLogicManager.getInstance().getO();
        param.put("storeId", busShopBean.storeId);//门店Id
        param.put("duration", RentCarLogicManager.getInstance().getUseBusDuration());
        persenter.sendDailyRentRequest(param, CardConstant.CAR_TYPE_LIST_URL);
    }

    /**
     * 初始化试图
     */
    private void initView()
    {
        persenter = new CarPersenter(this);

        final TextView titleName = (TextView) findViewById(R.id.titleName);

        titleName.setText(R.string.tv_title_select_motorcycle_type);

        this.busTypeList = new ArrayList<>();

        this.lvSelfDriveMotocycleType = (HeadFootRecyclerView) findViewById(R.id.lvSelfDriveMotocycleType);

        this.adapter = new SelectMotorcycleAdapter(this.busTypeList, this, logicFlag);

        this.lvSelfDriveMotocycleType.setLayoutManager(new LinearLayoutManager(this));

        this.lvSelfDriveMotocycleType.setAdapter(adapter);

        this.adapter.setOnOpenListener(new SelectMotorcycleAdapter.OnOpenClickListener()
        {
            @Override
            public void onOpen()
            {
                lvSelfDriveMotocycleType.notifyData();
            }
        });

        sRL = (SwipeRefreshLayout) findViewById(R.id.sRL);

        SwipeRefreshSettings.setAttrbutes(this, sRL);

        sRL.setOnRefreshListener(this);

        sRL.post(new Runnable()
        {
            @Override
            public void run()
            {
                sRL.setRefreshing(true);
            }
        });

    }

    @Override
    public void onRefresh()
    {
        initData();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unRegisterBoradcastReceiver();
    }

    /**
     * 注册广播接收器
     */
    public void registerBoradcastReceiver()
    {


        IntentFilter myIntentFilter = new IntentFilter();

        myIntentFilter.addAction(SelectMotorcycleTypeActivity.CLASS_NAME);
        // 注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    /**
     * 取消注册广播
     */
    public void unRegisterBoradcastReceiver()
    {

        if (mBroadcastReceiver != null)
        {

            unregisterReceiver(mBroadcastReceiver);
        }

    }

    /**
     * 广播接收器
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            String action = intent.getAction();

            if (action.equals(SelectMotorcycleTypeActivity.CLASS_NAME))
            {

                boolean isFinish = intent.getBooleanExtra("hasFinish", false);

                if (isFinish)
                {
                    finish();
                }
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK)
        {

            RentCarLogicManager.getInstance().setFlagBack(false);

        }

        return super.onKeyDown(keyCode, event);
    }



    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        List<BusTypeBean> busTypeLists = (List<BusTypeBean>) o;
        this.busTypeList.clear();
        this.busTypeList.addAll(busTypeLists);
        this.lvSelfDriveMotocycleType.notifyData();
        sRL.setRefreshing(false);
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }
}
