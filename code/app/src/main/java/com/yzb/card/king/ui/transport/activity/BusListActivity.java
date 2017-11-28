package com.yzb.card.king.ui.transport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.other.activity.CalendarActivity;
import com.yzb.card.king.ui.other.constants.TypeConstants;
import com.yzb.card.king.ui.transport.adapter.BusListAdapter;
import com.yzb.card.king.ui.transport.bean.BusTicket;
import com.yzb.card.king.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * 汽车票列表；
 * first modify  by gengqiyun on 2016/9/7. MVP优化；
 */
public class BusListActivity extends BaseTransportActivity implements View.OnClickListener
{
    private String departCityId = "1";
    private String reachCityId = "1";
    private String departCityName = "";
    private String reachCityName = "";
    private Date departDate = new Date();
    private String pageSize = "15";
    private String pageStart = "0";
    private String departStationId = "1";
    private String reachStationId = "1";
    private TextView tvStartDate, tvStartWeek;
    private BusListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    public void initView()
    {
        super.initView(this);
        View v = View.inflate(this, R.layout.bus_list_second_title, null);
        setSecondTitle(v);
        tv_title_1.setClickable(false);
        tv_title_2.setClickable(false);

        tvStartDate = (TextView) v.findViewById(R.id.tv_start_date);
        tvStartWeek = (TextView) v.findViewById(R.id.tv_start_week);

        v.findViewById(R.id.next_layout).setOnClickListener(this);
        v.findViewById(R.id.pre_layout).setOnClickListener(this);
        v.findViewById(R.id.ll_calendar).setOnClickListener(this);
    }

    private void initData()
    {
        // 初始化表头
        typeParentId = TypeConstants.FirstType.LUSHANGJIAOTONG.getIndex() + "";
        typeId = TypeConstants.LushangjiaotongType.QICHEPIAO.getIndex() + "";
        typeChildId = TypeConstants.LushangjiaotongType.QICHEPIAO.getIndex() + "";
        tvFl.setText(TypeConstants.LushangjiaotongType.QICHEPIAO.getName());

        Intent intent = getIntent();
        departCityId = intent.getStringExtra("departCityId");
        reachCityId = intent.getStringExtra("reachCityId");
        departCityName = intent.getStringExtra("departCityName");
        reachCityName = intent.getStringExtra("reachCityName");
        departDate = (Date) intent.getSerializableExtra("departDate");

        setTitle(departCityName, R.mipmap.icon_arrow_arrive, reachCityName);

        adapter = new BusListAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(adapterClickCallBack);
        setDate(departDate);
        getDateList();
    }

    /**
     * 适配器item点击；
     */
    private BusListAdapter.IAdapterClickCallBack adapterClickCallBack = new BusListAdapter.IAdapterClickCallBack()
    {
        @Override
        public void callBack(int position)
        {
            Intent intent = new Intent(BusListActivity.this, BusAgentActivity.class);
            BusTicket busTicket = adapter.getDataList().get(position);
            busTicket.departDate = departDate;
            busTicket.startCityName = departCityName;
            busTicket.endCityName = reachCityName;
            intent.putExtra(BusAgentActivity.BEAN, busTicket);
            startActivity(intent);
        }
    };

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.pre_layout:
                if (departDate.getTime() > (new Date()).getTime())
                {
                    departDate = DateUtil.addDay(departDate, -1);
                    setDate(departDate);
                    getDateList();
                }
                break;
            case R.id.next_layout:
                departDate = DateUtil.addDay(departDate, 1);
                setDate(departDate);
                getDateList();
                break;
            case R.id.ll_calendar:
                Intent intent = new Intent(this, CalendarActivity.class);
                intent.putExtra("type", CalendarActivity.TYPE_NO_PRICE);
                startActivity(intent);
                break;
        }
    }

    /**
     * 汽车列表
     */
    public void getDateList()
    {
        commonparam.clear();
        commonparam.put("departCityId", departCityId);
        commonparam.put("departDate", DateUtil.date2String(departDate));
        commonparam.put("reachCityId", reachCityId);

        commonparam.put("typeParentId", typeParentId);
        commonparam.put("typeChildId", typeChildId);
        commonparam.put("typeId", typeId);

        commonparam.put("reachStationId", reachStationId);
        commonparam.put("departStationId", departStationId);
        commonparam.put("pageStart", pageStart);
        commonparam.put("pageSize", pageSize);
        commonparam.put("sort", sort);
        commonparam.put("filter", filter);

        this.serviceName = CardConstant.transport_buslinelist;
        refreshData();
    }

    @Override
    protected void onSucess(boolean event_tag, String data)
    {
        if (event_tag)
        {
            adapter.clear();
        }
        List<BusTicket> dataList = JSON.parseArray(data, BusTicket.class);
        adapter.appendData(dataList);
        recyclerView.notifyData();
    }

    @Override
    protected void refresh()
    {
        getDateList();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (null != CalendarManager.getInstance().getDate())
        {
            departDate = CalendarManager.getInstance().getDate();
            setDate(departDate);

            CalendarManager.getInstance().clearData();
        }
    }

    private void setDate(Date departDate)
    {
        tvStartDate.setText(DateUtil.date2String(departDate, "MM-dd"));
        tvStartWeek.setText(DateUtil.getDateExplain(departDate));
    }
}
