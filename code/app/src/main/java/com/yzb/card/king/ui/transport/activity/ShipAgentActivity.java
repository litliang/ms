package com.yzb.card.king.ui.transport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.transport.adapter.ShipAgentAdapter;
import com.yzb.card.king.ui.transport.bean.ShipAgent;
import com.yzb.card.king.ui.transport.bean.ShipTicket;
import com.yzb.card.king.ui.transport.presenter.ShipAgentPresenter;
import com.yzb.card.king.ui.transport.presenter.impl.ShipAgentPresenterImpl;
import com.yzb.card.king.ui.transport.view.ShipAgentView;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 船票代理商；
 * first modify  by gengqiyun on 2016/9/7. MVP优化；
 */
public class ShipAgentActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ShipAgentView
{
    public static final String BEAN = "bean";
    private ShipTicket shipTicket;
    private ShipAgentAdapter adapter;

    private TextView tvShipName, tvStartTime, tvStartStation, tvEndTime, tvEndStation;
    private HeadFootRecyclerView recyclerView;
    private Map<String, Object> params = null;  //请求参数Map；
    private int pageStart = 0;
    private SwipeRefreshLayout swipeRefresh;
    private ShipAgentPresenter shipAgentPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_agent);
        shipAgentPresenter = new ShipAgentPresenterImpl(this);
        initView();
        initData();
    }

    private void initView()
    {
        tvShipName = (TextView) findViewById(R.id.tvShipName);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvStartStation = (TextView) findViewById(R.id.tvStartStation);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        tvEndStation = (TextView) findViewById(R.id.tvEndStation);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        recyclerView = (HeadFootRecyclerView) findViewById(R.id.seatListView);

        //设置布局管理器；
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ShipAgentAdapter(this);
        recyclerView.setIsEnale(true);
        //加载更多
        recyclerView.setOnLoadMoreListener(loadMoreListener);
        adapter.setOnAdvanceListener(advanceListener);
        recyclerView.setAdapter(adapter);
    }

    private void initData()
    {
        Serializable s = getIntent().getSerializableExtra(BEAN);
        if (s != null)
        {
            shipTicket = (ShipTicket) s;
        }
        tvShipName.setText(shipTicket.shipName);
        tvStartTime.setText(shipTicket.startTime);
        tvStartStation.setText(shipTicket.startShipName);
        tvEndTime.setText(shipTicket.endTime);
        tvEndStation.setText(shipTicket.endShipName);

        getDateList(true);
    }

    private HeadFootRecyclerView.OnLoadMoreListener loadMoreListener = new HeadFootRecyclerView.OnLoadMoreListener()
    {
        @Override
        public void loadMoreListener()
        {
            getDateList(false);
        }
    };

    @Override
    public void onRefresh()
    {
        pageStart = 0;
        getDateList(true);
    }

    @Override
    public void onLoadShipAgentListSucess(boolean event_tag, Object data)
    {
        swipeRefresh.setRefreshing(false);
        if (data != null)
        {
            pageStart++;
            List<ShipAgent> dataList = (List<ShipAgent>) data;
            if (event_tag)
            {
                adapter.clear();
            }
            adapter.appendData(dataList);
            recyclerView.notifyData();
        }
    }

    private Handler handler = new Handler();

    @Override
    public void onLoadShipAgentListFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);
        toastCustom(R.string.app_no_data);
    }

    private void getDateList(final boolean event_flag)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        params.clear();
        params.put("shipId", shipTicket.voyageId);
        params.put("pageStart", pageStart);
        params.put("pageSize", 15);
        LogUtil.i("船票座位列表请求参数-param:" + JSON.toJSONString(params));

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                shipAgentPresenter.loadData(event_flag, params);
            }
        }, 100);
    }

    public void back(View view)
    {
        this.finish();
    }

    /**
     * 预定监听事件
     */
    private ShipAgentAdapter.OnAdvanceListener advanceListener = new ShipAgentAdapter.OnAdvanceListener()
    {

        @Override
        public void onAdvance(int parentPosition, int childPosition)
        {
            if (UserManager.getInstance().isLogin())
            {
                Intent intent = new Intent(ShipAgentActivity.this, ShipOrderActivity.class);
                ShipAgent shipAgent = adapter.getDataList().get(parentPosition);
                ShipAgent.Supplier supplier = shipAgent.supplierList.get(childPosition);
                intent.putExtra("shipAgent", shipAgent);
                intent.putExtra("supplier", supplier);
                intent.putExtra("shipTicket", shipTicket);
                startActivity(intent);
            } else
            {
                new GoLoginDailog(ShipAgentActivity.this).show();
            }
        }
    };

}
