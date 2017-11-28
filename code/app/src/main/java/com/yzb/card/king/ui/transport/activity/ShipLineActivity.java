package com.yzb.card.king.ui.transport.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.transport.bean.ShipRoute;
import com.yzb.card.king.ui.transport.adapter.ShipLineAdapter;
import com.yzb.card.king.ui.transport.presenter.QueryLinePresenter;
import com.yzb.card.king.ui.transport.presenter.impl.QueryLinePresenterImpl;
import com.yzb.card.king.ui.transport.view.QueryLineView;
import com.yzb.card.king.util.SharePrefUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 航线 搜索；
 * gengqiyun  2016.9.8  MVP优化；
 */
public class ShipLineActivity extends BaseActivity implements View.OnClickListener, QueryLineView
{
    private static final String SP_ROUTE_HISTORY = "shipRouteHistory";
    private static ShipLineActivity mActivity;

    private ExpandableListView listView;
    private ShipLineAdapter adapter;
    private List<ShipRoute> dataList; //航线列表；
    private List<ShipRoute.Route> historyList; //历史纪录列表；

    public static ShipRoute.Route selectedRoute;
    private EditText etFilter;
    private int historySize = 10;
    private LinearLayout llHistory; //历史记录；
    private QueryLinePresenter queryLinePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_line);
        queryLinePresenter = new QueryLinePresenterImpl(this);
        initView();
        initData();
    }

    private void initView()
    {
        etFilter = (EditText) findViewById(R.id.etFilter);

        listView = (ExpandableListView) findViewById(R.id.expandableListView);
        adapter = new ShipLineAdapter(this, dataList, listView);
        adapter.setChildClickCallBack(childClickCallBack);
        listView.setAdapter(adapter);

        llHistory = (LinearLayout) findViewById(R.id.llHistory);

        findViewById(R.id.tvCancel).setOnClickListener(this);
        findViewById(R.id.tvSearchBtn).setOnClickListener(this);
        findViewById(R.id.llBack).setOnClickListener(this);
    }

    /**
     * 适配器点击回调；
     */
    private ShipLineAdapter.ICallBack childClickCallBack = new ShipLineAdapter.ICallBack()
    {
        @Override
        public void callBack(ShipRoute.Route route)
        {
            selectedRoute = route;
            addToLocalHistory(route);
            finish();
        }
    };

    private void initData()
    {
        historyList = new ArrayList<>();
        dataList = new ArrayList<>();
        initHistory();
        getDataList();
    }

    /**
     * 初始化历史记录
     */
    private void initHistory()
    {
        llHistory.removeAllViews();
        String historyJson = SharePrefUtil.getValueFromSp(this, SP_ROUTE_HISTORY, "[]");
        historyList = JSON.parseArray(historyJson, ShipRoute.Route.class);
        if (historyList != null)
        {
            View view;
            ShipRoute.Route route;
            LayoutInflater inflater = getLayoutInflater();

            for (int i = 0; i < historyList.size(); i++)
            {
                view = inflater.inflate(R.layout.history_item, null);
                route = historyList.get(i);
                TextView textView = (TextView) view.findViewById(R.id.textView);
                textView.setText(route.startCityName + "—" + route.endCityName);
                textView.setTag(i);
                view.setOnClickListener(historyItemClickListener);
                llHistory.addView(view);
            }
        }
    }

    /**
     * 历史记录item点击；
     */
    private View.OnClickListener historyItemClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            if (v.getTag() != null)
            {
                int historyIndex = (int) v.getTag();
                selectedRoute = historyList.get(historyIndex);
                addToLocalHistory(selectedRoute);
                finish();
            }
        }
    };

    @Override
    public void onQueryLineSucess(boolean event_tag, Object data)
    {
        if (data != null)
        {
            List<ShipRoute> list = (List<ShipRoute>) data;
            if (list != null && list.size() > 0)
            {
//                dataList.clear();
//                dataList.addAll(list);
//                adapter.notifyDataSetChanged();
                adapter.clear();
                adapter.appendData(list);
            }
        }
    }

    @Override
    public void onQueryLineFail(String failMsg)
    {
        toastCustom(R.string.app_no_data);
    }

    /**
     * 将历史储存在本地
     *
     * @param route
     */
    private void addToLocalHistory(ShipRoute.Route route)
    {
        List<ShipRoute.Route> removeList = new ArrayList<>();
        for (ShipRoute.Route history : historyList)
        {
            if (history.startCityId.equals(route.startCityId) &&
                    history.endCityId.equals(route.endCityId) &&
                    history.startCityName.equals(route.startCityName) &&
                    history.endCityName.equals(route.endCityName))
            {
                removeList.add(history);
            }
        }
        historyList.removeAll(removeList);
        historyList.add(0, route);
        while (historyList.size() > historySize)
        {
            historyList.remove(historySize - 1);
        }
        SharePrefUtil.saveToSp(this, SP_ROUTE_HISTORY, JSON.toJSONString(historyList));
    }


    public static ShipLineActivity getInstance()
    {
        if (mActivity == null)
        {
            return new ShipLineActivity();
        } else
        {
            return mActivity;
        }
    }

    public void getDataList()
    {
        Map<String, Object> param = new HashMap<>();
        //入参；
        queryLinePresenter.loadData(true, param);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvSearchBtn://搜索按钮
                startSearch();
                break;
            case R.id.tvCancel://取消按钮
                cancelRequest();
                break;
            case R.id.llBack://返回
                finish();
                break;
        }
    }

    /**
     * 取消按钮
     */
    private void cancelRequest()
    {
        String text = etFilter.getText().toString().trim();
        if (isEmpty(text))
        {
            finish();
        } else
        {
            etFilter.setText("");
        }
    }

    /**
     * 执行搜索
     */
    private void startSearch()
    {

    }

}
