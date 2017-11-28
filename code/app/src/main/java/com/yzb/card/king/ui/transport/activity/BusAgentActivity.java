package com.yzb.card.king.ui.transport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.transport.adapter.BusAgentListAdapter;
import com.yzb.card.king.ui.transport.bean.BusAgent;
import com.yzb.card.king.ui.transport.bean.BusTicket;
import com.yzb.card.king.util.DateUtil;

import java.util.List;

/**
 * 汽车票代理商；
 */
public class BusAgentActivity extends BaseTransportActivity
{
    public static final String BEAN = "bean";
    private BusTicket busTicket;
    /*==========secondTitle 组件===========*/
    private TextView tvStartDate;
    private TextView tvStartWeek;
    private TextView tvStartTime;
    private TextView tvStartCity;
    private TextView tvStartStation;
    private TextView tvEndCity;
    private TextView tvEndStation;
     /*===================================*/

    private BusAgentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData()
    {
        Intent intent = getIntent();
        busTicket = (BusTicket) intent.getSerializableExtra(BEAN);

        tvStartDate.setText(DateUtil.date2String(busTicket.departDate, "MM-dd"));
        tvStartWeek.setText("（" + DateUtil.getDateExplain(busTicket.departDate) + "）");
        tvStartTime.setText(busTicket.departTime);

        tvStartCity.setText(busTicket.startCityName);
        tvEndCity.setText(busTicket.endCityName);
        tvStartStation.setText(busTicket.departStationName);
        tvEndStation.setText(busTicket.reachStationName);

        setTitle(busTicket.startCityName, R.mipmap.icon_arrow_arrive, busTicket.endCityName);

        adapter = new BusAgentListAdapter(this);
        adapter.priceType = TYPE_HSZJ;
        adapter.setOnItemClickListener(adapterClickCallBack);

        recyclerView.setAdapter(adapter);
        getDataList();
    }

    /**
     * adapter item点击回调；
     */
    private BusAgentListAdapter.IAdapterClickCallBack adapterClickCallBack = new BusAgentListAdapter.IAdapterClickCallBack()
    {
        @Override
        public void callBack(int position)
        {
            if (UserManager.getInstance().isLogin())
            {
                BusAgent busAgent = adapter.getDataList().get(position);
                Intent intent = new Intent(BusAgentActivity.this, BusOrderActivity.class);
                intent.putExtra("busTicket", busTicket);
                intent.putExtra("busAgent", busAgent);
                startActivity(intent);
            } else
            {
                new GoLoginDailog(BusAgentActivity.this).show();
            }
        }
    };

    private void initView()
    {
        super.initView(this);
        tv_title_1.setClickable(false);
        tv_title_2.setClickable(false);

        View secondTitle = View.inflate(this, R.layout.transport_bus_agent_dtail, null);
        setSecondTitle(secondTitle);

        tvStartDate = (TextView) secondTitle.findViewById(R.id.tv_start_date);
        tvStartWeek = (TextView) secondTitle.findViewById(R.id.tv_start_week);
        tvStartTime = (TextView) secondTitle.findViewById(R.id.tv_startTime);
        tvStartCity = (TextView) secondTitle.findViewById(R.id.tv_start_city);
        tvStartStation = (TextView) secondTitle.findViewById(R.id.tv_start_station);
        tvEndCity = (TextView) secondTitle.findViewById(R.id.tv_end_city);
        tvEndStation = (TextView) secondTitle.findViewById(R.id.tv_end_station);

        setOptionVisibility(View.GONE);
    }

    public void getDataList()
    {
        commonparam.clear();
        commonparam.put("lineId", busTicket.lineId);

        commonparam.put("typeParentId", typeParentId);
        commonparam.put("typeChildId", typeId);
        commonparam.put("typeId", typeId);
        this.serviceName = CardConstant.transport_querybusmerchant;
        refreshData();
    }

    @Override
    protected void onSucess(boolean event_tag, String data)
    {
        if (event_tag)
        {
            adapter.clear();
        }
        List<BusAgent> dataList = JSON.parseArray(data, BusAgent.class);
        adapter.appendData(dataList);
        recyclerView.notifyData();
    }

    @Override
    protected void refresh()
    {
        getDataList();
    }

}
