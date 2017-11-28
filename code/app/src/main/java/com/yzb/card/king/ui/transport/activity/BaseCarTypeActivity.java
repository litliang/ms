package com.yzb.card.king.ui.transport.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.base.BaseFragmentActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.transport.bean.SpecialCar;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.transport.adapter.CarTypeAdapter;
import com.yzb.card.king.ui.transport.presenter.ShuttlePlanePersenter;
import com.yzb.card.king.util.SwipeRefreshSettings;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseCarTypeActivity extends BaseFragmentActivity implements SwipeRefreshLayout.OnRefreshListener,BaseViewLayerInterface
{

    List<SpecialCar> busShopList;
    private CarTypeAdapter adapter;
    private SwipeRefreshLayout sRL;
    private SpecialCar specialCar;
    private LinearLayout llDistanceAndDuration;
    private TextView tvDistance;
    private TextView tvDuration;

    private int distance = 13;
    private int duration = 43;
    HeadFootRecyclerView cycleTypeListView;

    private ShuttlePlanePersenter planePersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_motorcycle_type);
        initView();
        initData();
        sendRequest();
    }

    protected void initView()
    {

        llDistanceAndDuration = (LinearLayout) findViewById(R.id.llDistanceAndDuration);
        llDistanceAndDuration.setVisibility(View.VISIBLE);

        tvDistance = (TextView) findViewById(R.id.tvDistance);

        tvDuration = (TextView) findViewById(R.id.tvDuration);

        TextView titleName = (TextView) findViewById(R.id.titleName);

        titleName.setText(R.string.tv_title_select_motorcycle_type);

        busShopList = new ArrayList<>();

        cycleTypeListView = (HeadFootRecyclerView) findViewById(R.id.lvSelfDriveMotocycleType);

        adapter = new CarTypeAdapter(this, busShopList);
        adapter.setGloabeSepcail(getParamObj());
        adapter.setOnOpenListener(new CarTypeAdapter.OnOpenClickListener()
        {
            @Override
            public void onOpen()
            {
                cycleTypeListView.notifyData();
            }
        });

        cycleTypeListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cycleTypeListView.setAdapter(adapter);


        sRL = (SwipeRefreshLayout) findViewById(R.id.sRL);

        SwipeRefreshSettings.setAttrbutes(this, sRL);

        sRL.setOnRefreshListener(this);
    }

    protected void initData()
    {
        tvDistance.setText(String.valueOf(distance));
        tvDuration.setText(String.valueOf(duration));
        specialCar = getParamObj();
        planePersenter = new ShuttlePlanePersenter(this);
    }

    protected void sendRequest()
    {
        sRL.post(new Runnable()
        {
            @Override
            public void run()
            {
                sRL.setRefreshing(true);
            }
        });
        planePersenter.sendCardTypeRequest(setParams(specialCar, false), CardConstant.SPECIAL_CAR_TYPE_URL);
    }


    protected abstract SpecialCar getParamObj();

    protected abstract Map<String, Object> setParams(SpecialCar specialCar, boolean isLoadMore);

    protected abstract List<SpecialCar> onResult(Object result);

    @Override
    public void onRefresh()
    {
        sendRequest();
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        this.busShopList.clear();
        this.busShopList.addAll(onResult(o));
        cycleTypeListView.notifyData();
        sRL.setRefreshing(false);
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        Toast.makeText(BaseCarTypeActivity.this, o+"", Toast.LENGTH_SHORT).show();
        sRL.setRefreshing(false);
    }
}
