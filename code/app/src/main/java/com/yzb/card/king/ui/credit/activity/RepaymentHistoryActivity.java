package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.adapter.PayMentHistoryAdapter;
import com.yzb.card.king.ui.credit.presenter.RepaymentHistoryPresenter;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 还款记录
 */
public class RepaymentHistoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, BaseViewLayerInterface {

    private SwipeRefreshLayout sRl;

    private HeadFootRecyclerView listView;

    private PayMentHistoryAdapter adapter;

    private LinearLayout panel_back;

    private long creditId;

    private int pageStart = 0;

    private RepaymentHistoryPresenter present;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayment_history);
        reciviceIntent();
        initView();
    }

    private void reciviceIntent()
    {
        Intent i = getIntent();
        if (i != null) {
            creditId = i.getLongExtra("creditId", 0);
        }
    }

    private void initView()
    {
        sRl = (SwipeRefreshLayout) findViewById(R.id.srl);
        SwipeRefreshSettings.setAttrbutes(this, sRl);
        panel_back = (LinearLayout) findViewById(R.id.panel_back);
        present = new RepaymentHistoryPresenter(this);
        panel_back.setOnClickListener(this);
        sRl.setOnRefreshListener(this);
        adapter = new PayMentHistoryAdapter(this);
        listView = (HeadFootRecyclerView) findViewById(R.id.paymentHistory);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setIsEnale(true);
        listView.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener()
            {
                loadMore();
            }
        });
        getData();
    }

    private void getData()
    {
        pageStart = 0;
        Map<String, Object> param = new HashMap<>();
        param.put("creditId", creditId);
        param.put("pageStart", pageStart);
        param.put("pageSize", 12);
        present.getListInfo(param, CardConstant.CREDIT_HISTORY, 0);
    }

    @Override
    public void onRefresh()
    {
        getData();
    }

    private void loadMore()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("creditId", creditId);
        param.put("pageStart", pageStart);
        param.put("pageSize", 12);
        present.getListInfo(param, CardConstant.CREDIT_HISTORY, 0);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.panel_back:
                finish();
                break;
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == 0) {
            List<Map> info = JSON.parseArray(String.valueOf(o), Map.class);
            if (info.size() == 12) {
                pageStart = adapter.getItemCount();
                listView.notifyData();
            } else {
                listView.calByNewNum(info.size());
            }
            adapter.setList(info);
            sRl.setRefreshing(false);
        } else if (type == 1) {
            List<Map> info = JSON.parseArray(String.valueOf(o), Map.class);
            adapter.setOldList(info);
            if (info.size() == 12) {
                pageStart = adapter.getItemCount();
                listView.notifyData();
            } else {
                listView.calByNewNum(info.size());
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if (!TextUtils.isEmpty(o + "")) {
            if (pageStart == 0) {

                sRl.setRefreshing(false);

            } else {

                listView.calByNewNum(0);
            }
        }

    }
}
