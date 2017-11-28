package com.yzb.card.king.ui.my.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.adapter.MessageDetailAdapter;
import com.yzb.card.king.ui.appwidget.menulistview.MenuListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.my.bean.MessageDetailBean;
import com.yzb.card.king.ui.my.presenter.MessagePresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类 名： 活动消息
 * 作 者： gaolei
 * 日 期：2016年12月8日
 * 描 述：活动消息的跳转函数
 */
public class MessageDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, BaseViewLayerInterface {

    private MenuListView listView;
    private MessageDetailAdapter adapter;
    private TextView titleName;
    private List<Map<String, Object>> data;
    private List<Map<String, Object>> dataAct;
    private List<Map<String, Object>> dataOrd;
    private List<Map<String, Object>> dataSys;
    private MsgRemoveReceiver receiver;
    private View.OnClickListener listener;
    private SwipeRefreshLayout refreshLayout;
    private Boolean isButtom = false;
    private Intent intent;
    private MessagePresenter presenter;
    private MessageDetailBean list;
    private List<MessageDetailBean.SubNewsMapListBean> itemList;
    private TextView noData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        presenter = new MessagePresenter(this);
        initView();
        initResg();
        initTitle();
        onRefresh();

    }

    private void initDetailPresenter(String type) {
        Map<String, Object> param = new HashMap<>();
        param.put("taskType", type);//H:活动通知、S:系统通知  D:订单通知、U:个人通知
        param.put("taskSturt", "1,2");//0：删除，1：未阅读 2：已阅读 ，支持多个查询，中间用英文逗号分隔，如查询已阅读和未阅读为1,2
        presenter.getMessageData(param, CardConstant.QuerySubNewsList, 2);
    }

    private void initTitle() {

        if (intent.getExtras().getString("orderMsg") != null && intent.getExtras().getString("orderMsg")
                .equals("订单通知")) {
            titleName.setText("订单通知");
            initDetailPresenter("D");
            if (itemList != null) {
                noData.setVisibility(View.GONE);
            } else {
                noData.setVisibility(View.VISIBLE);
            }
        } else if (intent.getExtras().getString("systemMsg") != null && intent.getExtras().getString("systemMsg")
                .equals("系统消息")) {
            titleName.setText("系统消息");
            initDetailPresenter("S");
            if (itemList != null) {
                noData.setVisibility(View.GONE);
            } else {
                noData.setVisibility(View.VISIBLE);
            }
        } else if (intent.getExtras().getString("activityMsg") != null && intent.getExtras().getString("activityMsg")
                .equals("活动通知")) {
            titleName.setText("活动通知");
            initDetailPresenter("H");
            if (itemList != null) {
                noData.setVisibility(View.GONE);
            } else {
                noData.setVisibility(View.VISIBLE);
            }

        } else {
            Toast.makeText(this, "没有您要查询的信息", Toast.LENGTH_SHORT).show();
        }
    }


    private void initAdapter() {

        if (intent.getExtras().getString("orderMsg") != null && intent.getExtras().getString("orderMsg")
                .equals("订单通知")) {
            if (itemList != null) {
                noData.setVisibility(View.GONE);
                initData(itemList);
            } else {
                noData.setVisibility(View.VISIBLE);
            }

        } else if (intent.getExtras().getString("systemMsg") != null && intent.getExtras().getString("systemMsg")
                .equals("系统消息")) {
            if (itemList != null) {
                initData(itemList);
                noData.setVisibility(View.GONE);
            } else {
                noData.setVisibility(View.VISIBLE);
            }

        } else if (intent.getExtras().getString("activityMsg") != null && intent.getExtras().getString("activityMsg")
                .equals("活动通知")) {
            if (itemList != null) {
                initData(itemList);
                noData.setVisibility(View.GONE);
            } else {
                noData.setVisibility(View.VISIBLE);
            }

        } else {
            Toast.makeText(this, "没有您要查询的信息", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData(List<MessageDetailBean.SubNewsMapListBean> itemList) {

        adapter = new MessageDetailAdapter(this, itemList, listener);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                listView.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }


    private void initView() {
        listView = (MenuListView) findViewById(R.id.message_activity_detail_list);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.message_activity_swipe_layout);
        titleName = (TextView) findViewById(R.id.message_titleName);
        noData = (TextView)findViewById(R.id.message_no_data);
        intent = getIntent();
    }

    private void initResg() {

        receiver = new MsgRemoveReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.gaolei.test");
        filter.addAction("com.NotReceiverActivity");
        registerReceiver(receiver, filter);
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回调接口,可以再里面做处理
            }
        };
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(true);
                        //处理数据,可以添加判断
                        refreshLayout.canChildScrollUp();
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                        //Toast.makeText(MessageDetailActivity.this, "没有更多信息", Toast.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    //上拉加载
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                if (isButtom) {
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "没有更多信息", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            isButtom = true;
        } else {
            isButtom = false;
        }
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (o !=null) {
            if (type == 2) {
                list = JSON.parseObject(String.valueOf(o), MessageDetailBean.class);
                itemList = list.getSubNewsMapList();
                initAdapter();
                if (adapter !=null ){
                    adapter.notifyDataSetChanged();
                }

            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }

    /**
     * 注册广播
     */
    private class MsgRemoveReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.planeMyConcernFragmet")) {
                if (intent.getExtras().getInt("sendmessage") == 1) {
                    int i = intent.getExtras().getInt("delone");
                    data.remove(i);
                    adapter.notifyDataSetChanged();
                } else if (intent.getExtras().getInt("sendmessage") == 2) {
                    data.clear();
                }
            } else if (intent.getAction().equals("com.NotReceiverActivity")) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
