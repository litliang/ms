package com.yzb.card.king.ui.transport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.other.activity.CalendarActivity;
import com.yzb.card.king.ui.other.constants.TypeConstants;
import com.yzb.card.king.ui.transport.adapter.TrainListAdapter;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 火车票列表
 * first modify  by gengqiyun on 2016/9/6. MVP优化；
 */
public class TrainListActivity extends BaseTransportActivity
{
    private Bundle bundle = null;
    private String startCityName = "";
    private String startCityId = "";
    private String endCityName = "";
    private String endCityId = "";
    private Date startDate = null;
    private String trainType = "";
    private TextView tvDate = null;
    private TrainListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected void refresh()
    {
        getDateList();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        CalendarManager cManager = CalendarManager.getInstance();
        if (null != cManager.getDate())
        {
            startDate = cManager.getDate();
            tvDate.setText(DateUtil.date2String(startDate, "MM-dd") + "    " + DateUtil.getDateExplain(startDate));
            cManager.clearData();
        }
    }

    private void initView()
    {
        super.initView(this);
        View v = View.inflate(this, R.layout.train_list_title_, null);
        setSecondTitle(v);

        tv_title_1.setClickable(false);
        tv_title_2.setClickable(false);

        // 初始化表头
        typeParentId = AppConstant.transport_id;
        typeId = TypeConstants.LushangjiaotongType.HUOCHEPIAO.getIndex() + "";
        typeChildId = typeId;
        tvFl.setText(TypeConstants.LushangjiaotongType.HUOCHEPIAO.getName());

        tvDate = (TextView) v.findViewById(R.id.tv_date);

        adapter = new TrainListAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(adapterCallBack);
    }

    /**
     * 适配器点击回调；
     */
    private TrainListAdapter.ICallBack adapterCallBack = new TrainListAdapter.ICallBack()
    {
        @Override
        public void callBack(int position)
        {
            Intent intent = new Intent(TrainListActivity.this, TrainDetailActivity.class);
            Map map = adapter.getDataList().get(position);
            map.put("startCityName", startCityName);
            map.put("startCityId", startCityId);
            map.put("endCityName", endCityName);
            map.put("endCityId", endCityId);
            map.put("startDate", DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE));
            map.put("trainType2", trainType);
            intent.putExtra("trainInfo", JSON.toJSONString(map));
            startActivity(intent);
        }
    };

    @Override
    protected void onSucess(boolean event_flag, String data)
    {
        if (event_flag)
        {
            adapter.clear();
        }
        List<Map> dataList = JSON.parseArray(data, Map.class);
        adapter.appendData(dataList);
        recyclerView.notifyData();
    }

    private void initData()
    {
        bundle = getIntent().getExtras();
        Bundle bundle2 = getIntent().getBundleExtra("hcp");
        if (null != bundle2)
        {
            bundle = new Bundle();

            startCityName = "北京";
            startCityId = "1";
            endCityName = "上海";
            endCityId = "2";
            startDate = DateUtil.addDay(new Date(), 0);
            trainType = "1";

            bundle.putString("startCityName", startCityName);
            bundle.putString("startCityId", startCityId);
            bundle.putString("endCityName", endCityName);
            bundle.putString("endCityId", endCityId);
            bundle.putString("startDate", DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE));
            bundle.putString("trainType", trainType);
        } else
        {
            if (null == bundle)
            {
                bundle = new Bundle();
            } else
            {
                if (bundle.containsKey("typeParentId"))
                {
                    typeParentId = bundle.getString("typeParentId", "");
                    LogUtil.i("接收到的typeParentId==" + typeParentId);
                }
                // 可能来自于美食或奢侈品的首页的轮播图item点击；小分类
                if (bundle.containsKey("typeId"))
                {
                    typeId = bundle.getString("typeId", "");
                    LogUtil.i("接收到的typeId==" + typeId);
                }
                startCityName = bundle.getString("startCityName", "");
                startCityId = bundle.getString("startCityId", "");
                endCityName = bundle.getString("endCityName", "");
                endCityId = bundle.getString("endCityId", "");
                if (StringUtils.isEmpty(bundle.getString("startDate", "")))
                {
                    startDate = new Date();
                } else
                {
                    startDate = DateUtil.string2Date(bundle.getString("startDate", ""), DateUtil.DATE_FORMAT_DATE);
                    trainType = bundle.getString("trainType", "1");
                }
            }
        }
        setTitle(startCityName, R.mipmap.icon_arrow_arrive, endCityName);
        tvDate.setText(DateUtil.getDate(startDate, 0) + "    " + DateUtil.getDateExplain(startDate));

        getDateList();
    }

    private void getDateList()
    {
        commonparam.clear();
        commonparam.put("startCityId", startCityId);
        commonparam.put("endCityId", endCityId);
        commonparam.put("startDate", DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE));
        commonparam.put("trainType", trainType);

        pageStart = 0;
        commonparam.put("pageStart", pageStart + "");
        commonparam.put("pageSize", pageSize);
        commonparam.put("sort", sort);
        commonparam.put("filter", filter);
        this.serviceName = CardConstant.transport_querytrainticket;
        refreshData();
    }

    /**
     * 前一天后一天的点击；
     */
    public void refresh(View v)
    {
        if (v.getId() == R.id.pre_layout)
        {
            if (0 != DateUtil.naturalDaysBetween(new Date(), startDate))
            {
                startDate = DateUtil.addDay(startDate, -1);
            }
        }
        if (v.getId() == R.id.next_layout)
        {
            startDate = DateUtil.addDay(startDate, 1);
        }
        tvDate.setText(DateUtil.getDate(startDate, 0) + "    " + DateUtil.getDateExplain(startDate));

        getDateList();
    }

    public void getDate(View v)
    {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
