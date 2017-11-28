package com.yzb.card.king.ui.transport.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.transport.adapter.TrainTimeTableListAdapter;
import com.yzb.card.king.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 火车时刻表
 */
public class TrainTimetableActivity extends BaseActivity
{
    private Map trainInfo = null;
    private TextView middleTitle = null;
    private List<Map> timeListData = null;
    private ListView timeListView = null;
    private TrainTimeTableListAdapter timeListAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_timetable);
        initView();
        initData();
    }

    private void initView()
    {
        trainInfo = JSON.parseObject(getIntent().getStringExtra("trainInfo"), Map.class);

        middleTitle = (TextView) findViewById(R.id.middleTitle);
    }

    private void initData()
    {

        middleTitle.setText(String.valueOf(trainInfo.get("trainNumber")) + getResources().getString(R.string.train_shikebiao));

        timeListData = getData();
        timeListView = (ListView) findViewById(R.id.timeListView);
        timeListAdapter = new TrainTimeTableListAdapter(TrainTimetableActivity.this, timeListData);
        timeListView.setAdapter(timeListAdapter);

        getDateList();
    }

    private void getDateList()
    {
        /*new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                Map<String, Object> param = new HashMap<>();
                param.put("trainId", String.valueOf(trainInfo.get("trainId")));
                param.put("startDate", String.valueOf(trainInfo.get("startDate")));
                param.put("pageStart", "0");
                param.put("pageSize", "15");

                LogUtil.i("火车票时刻表请求参数-param:" + JSON.toJSONString(param));
                return ServiceDispatcher.call(TrainTimetableActivity.this,
                        ServiceDispatcher.setParams("", CardConstant.card_app_discount_queryseatlist, AppConstant.UUID, JSON.toJSONString(param)));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                LogUtil.i("火车票时刻表请求结果-result:" + result);
                if(null == result || result.isEmpty() || !"0000".equals(result.get("code"))) {
                    LogUtil.i("火车票时刻表请求结果-result:请求失败");
                    return ;
                }

                if (TextUtils.isEmpty(result.get("data"))) {
                    LogUtil.i("火车票时刻表请求结果-result:无数据");
                    return ;
                }

                List<Map> list = JSON.parseArray(result.get("data"), Map.class);

                if (list != null && !list.isEmpty()) {
                    timeListData.clear();
                    timeListData.addAll(list);
                    timeListAdapter.notifyDataSetChanged();
                }
            }
        }.executeOnExecutor(Executors.newCachedThreadPool());  //任务执行处理器；*/
    }

    public void back(View view)
    {
        this.finish();
    }

    public List<Map> getData()
    {
        List<Map> list = new ArrayList<>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("trainName", "德州东");
        map.put("reachTime", "");
        map.put("startTime", "06:43");
        map.put("dueTime", "");
        map.put("type", "0");
        list.add(map);

        map = new HashMap<>();
        map.put("trainName", "苏州北");
        map.put("reachTime", "07:06");
        map.put("startTime", "07:08");
        map.put("dueTime", "2分钟");
        map.put("type", "0");
        list.add(map);

        map = new HashMap<>();
        map.put("trainName", "常州北");
        map.put("reachTime", "07:30");
        map.put("startTime", "07:32");
        map.put("dueTime", "2分钟");
        map.put("type", "0");
        list.add(map);

        map = new HashMap<>();
        map.put("trainName", "上海虹桥");
        map.put("reachTime", "08:06");
        map.put("startTime", "08:09");
        map.put("dueTime", "3分钟");
        map.put("type", "1");
        list.add(map);

        map = new HashMap<>();
        map.put("trainName", "滁州");
        map.put("reachTime", "08:27");
        map.put("startTime", "08:29");
        map.put("dueTime", "2分钟");
        map.put("type", "2");
        list.add(map);

        map = new HashMap<>();
        map.put("trainName", "枣庄");
        map.put("reachTime", "09:43");
        map.put("startTime", "09:45");
        map.put("dueTime", "2分钟");
        map.put("type", "2");
        list.add(map);

        map = new HashMap<>();
        map.put("trainName", "济南西");
        map.put("reachTime", "10:36");
        map.put("startTime", "10:39");
        map.put("dueTime", "3分钟");
        map.put("type", "2");
        list.add(map);

        map = new HashMap<>();
        map.put("trainName", "南京南");
        map.put("reachTime", "11:03");
        map.put("startTime", "11:05");
        map.put("dueTime", "2分钟");
        map.put("type", "3");
        list.add(map);

        map = new HashMap<>();
        map.put("trainName", "北京南");
        map.put("reachTime", "12:18");
        map.put("startTime", "");
        map.put("dueTime", "");
        map.put("type", "0");
        list.add(map);

        return list;
    }
}
