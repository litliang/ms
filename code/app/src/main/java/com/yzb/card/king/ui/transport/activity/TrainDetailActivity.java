package com.yzb.card.king.ui.transport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.transport.adapter.TrainSeatListAdapter;
import com.yzb.card.king.ui.transport.presenter.SeatListPresenter;
import com.yzb.card.king.ui.transport.presenter.impl.SeatListPresenterImpl;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 火车票详情；
 * 第一次修改：gengqiyun  2016.9.7
 */
public class TrainDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener
        , SwipeRefreshLayout.OnRefreshListener,BaseViewLayerInterface
{
    private Map trainInfo = null;

    private TextView tvStartCityName = null;
    private TextView tvEndCityName = null;

    private List<Map> seatListData = null; //座位数据；
    private ListView seatListView = null;
    private TrainSeatListAdapter seatListAdapter = null;

    private int dataPosition = 0;
    private View lastView = null;
    private SwipeRefreshLayout swipeRefresh;
    private SeatListPresenter seatListPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_detail);

        seatListPresenter = new SeatListPresenterImpl(this);
        initView();
        getDateList();
    }

    private void initView()
    {
        String trainInfoJson = getIntent().getStringExtra("trainInfo");
        if (!isEmpty(trainInfoJson))
        {
            trainInfo = JSON.parseObject(trainInfoJson, Map.class);
        }
        initTrainInfo();

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        tvStartCityName = (TextView) findViewById(R.id.tvStartCityName);
        tvEndCityName = (TextView) findViewById(R.id.tvEndCityName);
        findViewById(R.id.panel_bookingNotice).setOnClickListener(this);

        tvStartCityName.setText(String.valueOf(trainInfo.get("startCityName")));
        tvEndCityName.setText(String.valueOf(trainInfo.get("endCityName")));

        seatListData = new ArrayList<>();
        seatListView = (ListView) findViewById(R.id.seatListView);
        seatListAdapter = new TrainSeatListAdapter(this, seatListData, advanceOnClickListener);
        seatListView.setAdapter(seatListAdapter);
        seatListView.setOnItemClickListener(this);
    }

    /**
     * adapter预定监听；
     */
    View.OnClickListener advanceOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (UserManager.getInstance().isLogin())
            {
                String price = (String) v.getTag(R.id.train_seat_price);
                trainInfo.put("price", price);
                Map map = seatListData.get(dataPosition);
                trainInfo.put("seatName", map.get("seatName"));
                Intent intent = new Intent(TrainDetailActivity.this, TrainOrderActivity.class);
                intent.putExtra("trainInfo", JSON.toJSONString(trainInfo));
                Map supplier = (Map) v.getTag();
                intent.putExtra("supplier", JSON.toJSONString(supplier));
                startActivity(intent);
            } else
            {
                new GoLoginDailog(TrainDetailActivity.this).show();
            }
        }
    };

    private void resetArrow(View view)
    {
        LinearLayout agentLayout = (LinearLayout) view.findViewById(R.id.agentLayout);
        ImageView imgItem = (ImageView) view.findViewById(R.id.imgItem);
        agentLayout.setTag(0);
        imgItem.setBackgroundResource(R.mipmap.train_seat_icon_down);
        agentLayout.setVisibility(View.GONE);
    }

    public void initTrainInfo()
    {
        TextView tvTrainDate = (TextView) findViewById(R.id.tvTrainDate);
        TextView tvTimeLength = (TextView) findViewById(R.id.tvTimeLength);
        TextView tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        TextView tvTrainNumber = (TextView) findViewById(R.id.tvTrainNumber);
        TextView tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        TextView tvStartTrainName = (TextView) findViewById(R.id.tvStartTrainName);
        TextView tvTrainType = (TextView) findViewById(R.id.tvTrainType);
        TextView tvEndTrainName = (TextView) findViewById(R.id.tvEndTrainName);

        tvTrainDate.setText(String.valueOf(trainInfo.get("startDate")).substring(5) + "  "
                + getResources().getString(R.string.zhou) + DateUtil.getWeek(String.valueOf(trainInfo.get("startDate"))));
        tvTimeLength.setText(String.valueOf(trainInfo.get("timeLength")));
        tvStartTime.setText(String.valueOf(trainInfo.get("startTime")).substring(11, 16));
        tvTrainNumber.setText(String.valueOf(trainInfo.get("trainNumber")));
        tvEndTime.setText(String.valueOf(trainInfo.get("endTime")).substring(11, 16));
        tvStartTrainName.setText(String.valueOf(trainInfo.get("startTrainName")));
        tvTrainType.setText(String.valueOf(trainInfo.get("trainType")));
        tvEndTrainName.setText(String.valueOf(trainInfo.get("endTrainName")));
    }

    @Override
    public void onRefresh()
    {
        getDateList();
    }

    private void getDateList()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("trainId", String.valueOf(trainInfo.get("trainId")));
        param.put("startDate", String.valueOf(trainInfo.get("startDate")));
        param.put("pageStart", "0");
        param.put("pageSize", "15");
        LogUtil.i("火车票座位列表请求参数-param:" + JSON.toJSONString(param));
        seatListPresenter.loadData(true, param);
    }



    public void back(View view)
    {
        this.finish();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panel_bookingNotice:  //预定须知
                Bundle bundle = new Bundle();
                bundle.putString("category", "1");
                bundle.putString("titleName", getString(R.string.transport_book_need_know));
                readyGoWithBundle(this, WebViewClientActivity.class, bundle);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        dataPosition = position;
        if (lastView != null && lastView != view)
        {
            resetArrow(lastView);
        }
        lastView = view;
        LinearLayout agentLayout = (LinearLayout) view.findViewById(R.id.agentLayout);
        ImageView imgItem = (ImageView) view.findViewById(R.id.imgItem);
        int tag = (int) agentLayout.getTag();
        if (0 == tag)
        {
            agentLayout.setTag(1);
            imgItem.setBackgroundResource(R.mipmap.train_seat_icon_up);
            agentLayout.setVisibility(View.VISIBLE);
        } else
        {
            agentLayout.setTag(0);
            imgItem.setBackgroundResource(R.mipmap.train_seat_icon_down);
            agentLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void callSuccessViewLogic(Object data, int type)
    {
        swipeRefresh.setRefreshing(false);
        if (data != null)
        {
            List<Map> list = (List<Map>) data;
            seatListData.clear();
            seatListData.addAll(list);
            seatListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        swipeRefresh.setRefreshing(false);
        toastCustom(getString(R.string.app_no_data));
    }
}
