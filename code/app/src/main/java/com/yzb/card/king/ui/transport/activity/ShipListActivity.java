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
import com.yzb.card.king.ui.transport.adapter.ShipListAdapter;
import com.yzb.card.king.ui.transport.bean.ShipTicket;
import com.yzb.card.king.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * 陆上交通-->船票；
 * Created by yinsg on 2016/5/30.
 * first modify  by gengqiyun on 2016/9/6. MVP优化；
 */
public class ShipListActivity extends BaseTransportActivity implements View.OnClickListener
{
    private String startCityName;
    private String endCityName;
    private String startCityId;
    private String endCityId;
    private Date startDate;

    private TextView tvStartDate, tvStartWeek;
    private ShipListAdapter adapter;

    @Override
    protected void onStart()
    {
        super.onStart();

        if (null != CalendarManager.getInstance().getDate())
        {
            startDate = CalendarManager.getInstance().getDate();
            setDate(startDate);

            CalendarManager.getInstance().clearData();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initView();
        intData();
    }

    private void initView()
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


    private void intData()
    {
        Intent intent = getIntent();
        startCityName = intent.getStringExtra("startCityName");
        endCityName = intent.getStringExtra("endCityName");
        startCityId = intent.getStringExtra("startCityId");
        endCityId = intent.getStringExtra("endCityId");
        startDate = (Date) intent.getSerializableExtra("startDate");

        setTitle(startCityName, R.mipmap.icon_arrow_arrive, endCityName);

        // 初始化表头
        typeParentId = TypeConstants.FirstType.LUSHANGJIAOTONG.getIndex() + "";
        typeId = TypeConstants.LushangjiaotongType.CUANPIAO.getIndex() + "";
        typeChildId = TypeConstants.LushangjiaotongType.CUANPIAO.getIndex() + "";
        tvFl.setText(TypeConstants.LushangjiaotongType.CUANPIAO.getName());

        adapter = new ShipListAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(adapterClickCallBack);
        setDate(startDate);
        getShipList();
    }


    /**
     * adapter item点击；
     */
    private ShipListAdapter.ICallBack adapterClickCallBack = new ShipListAdapter.ICallBack()
    {

        @Override
        public void callBack(int position)
        {
            Intent intent = new Intent(ShipListActivity.this, ShipAgentActivity.class);
            ShipTicket shipTicket = adapter.getDataList().get(position);
            shipTicket.startDate = startDate;
            shipTicket.startCityName = startCityName;
            shipTicket.endCityName = endCityName;
            intent.putExtra(ShipAgentActivity.BEAN, shipTicket);
            startActivity(intent);
        }
    };

    public void getShipList()
    {
        commonparam.clear();
        commonparam.put("startCityId", startCityId);
        commonparam.put("startDate", DateUtil.date2String(startDate));
        commonparam.put("endCityId", endCityId);

        commonparam.put("typeParentId", typeParentId);
        commonparam.put("typeChildId", typeChildId);
        commonparam.put("typeId", typeId);

        commonparam.put("pageStart", pageStart + "");
        commonparam.put("pageSize", pageSize);
        commonparam.put("sort", sort);
        commonparam.put("filter", filter);
        this.serviceName = CardConstant.transport_queryshiplist;
        refreshData();
    }

    @Override
    protected void onSucess(boolean event_tag, String data)
    {
        List<ShipTicket> dataList = JSON.parseArray(data, ShipTicket.class);
        if (event_tag)
        {
            adapter.clear();
        }
        adapter.appendData(dataList);
        recyclerView.notifyData();
    }

    @Override
    protected void refresh()
    {
        getShipList();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.pre_layout:
                if (startDate.getTime() > (new Date()).getTime())
                {
                    startDate = DateUtil.addDay(startDate, -1);
                    setDate(startDate);
                    getShipList();
                }
                break;
            case R.id.next_layout:
                startDate = DateUtil.addDay(startDate, 1);
                setDate(startDate);
                getShipList();
                break;
            case R.id.ll_calendar:
                Intent intent = new Intent(this, CalendarActivity.class);
                intent.putExtra(CalendarActivity.TYPE, CalendarActivity.TYPE_NO_PRICE);
                startActivity(intent);
                break;
        }
    }

    private void setDate(Date departDate)
    {
        tvStartDate.setText(DateUtil.date2String(departDate, "MM-dd"));
        tvStartWeek.setText(DateUtil.getDateExplain(departDate));
    }

}
