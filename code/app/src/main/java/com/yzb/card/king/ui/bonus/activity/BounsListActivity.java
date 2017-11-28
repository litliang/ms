package com.yzb.card.king.ui.bonus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.GridView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.bonus.bean.BounsBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.bonus.adapter.BounsListAdapter;
import com.yzb.card.king.ui.bonus.fragment.RecvBounsSucDialog;
import com.yzb.card.king.ui.bonus.presenter.BounsListPresenter;
import com.yzb.card.king.ui.bonus.presenter.RecvBounsPresenter;
import com.yzb.card.king.ui.bonus.view.BounsListView;
import com.yzb.card.king.ui.bonus.view.RecvBounsView;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.SwipeRefreshSettings;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的-->红包列表；
 *
 * @author gengqiyun
 * @date 2016.12.30
 */
@ContentView(R.layout.activity_bouns_list)
public class BounsListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
       BounsListView, RecvBounsView {

    @ViewInject(R.id.swipeRefresh)
    private SwipeRefreshLayout swipeRefresh;

    @ViewInject(R.id.gridView)
    private GridView gridView;

    private BounsListAdapter adapter;

    private BounsListPresenter listPresenter;

    private RecvBounsPresenter recvBounsPresenter; //领取红包；

    private String pushOrderId;//推送下来的orderId

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;

        super.onCreate(savedInstanceState);

        listPresenter = new BounsListPresenter(this);

        recvBounsPresenter = new RecvBounsPresenter(this);

        recvInentData();

        initView();

        if (isEmpty(pushOrderId)) {

            List<BounsBean> list = UserManager.getInstance().getRedEnvelepoList();

            if (list == null) {

                initData();

            } else {
                //加载缓存数据
                swipeRefresh.setRefreshing(false);

                adapter.appendALL(list);
            }

        } else {

            exeRecvBouns(pushOrderId);

        }
    }

    private void initView()
    {
        setTitleNmae(getString(R.string.recv_bouns));


        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);

        swipeRefresh.setOnRefreshListener(this);

        gridView.setFocusable(false);

        adapter = new BounsListAdapter(this);

        adapter.setHandler(dataHandler);

        gridView.setAdapter(adapter);
    }

    private Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what) {
                case BounsListAdapter.WHAT_RECV: //红包领取；
                    BounsBean bean = adapter.getItem(msg.arg1);
                    exeRecvBouns(bean.getOrderId());
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        UserManager.getInstance().setRedEnvelepoList(null);
    }

    /**
     * 領取红包；
     *
     * @param orderId 订单id；
     */
    private void exeRecvBouns(String orderId)
    {
        if (!isLogin()) {
            readyGo(this, LoginActivity.class);
            return;
        }
        showPDialog(R.string.loading);
        Map<String, Object> args = new HashMap<>();
        args.put("account", getUserBean().getAccount()); //用户名
        args.put("orderId", orderId);
        recvBounsPresenter.loadData(args);
    }

    public void recvInentData()
    {
        pushOrderId = getIntent().getStringExtra("id");
    }


    @Override
    public void onRefresh()
    {
        initData();
    }

    private void initData()
    {
        dataHandler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                loadListData(true);
            }
        }, 100);
    }

    private void loadListData(boolean isRefresh)
    {
        adapter.clearAll();

        Map<String, Object> args = new HashMap<>();
        args.put("pageStart", isRefresh ? "0" : adapter.getCount() + "");
        args.put("pageSize", "20");
        listPresenter.loadData(isRefresh, args);
    }

    @Override
    public void onGetBounsListSuc(boolean event_tag, List<BounsBean> list)
    {
        swipeRefresh.setRefreshing(false);
        if (event_tag) {
            adapter.clearAll();
        }
        adapter.appendALL(list);
    }

    @Override
    public void onGetBounsListFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);
        toastCustom(failMsg);
    }

    @Override
    public void onRecvBounsSuc(String data)
    {
        String[] items = data.split("#");

        final String orderId = items[0];

        String amount = items[1];

        adapter.delItemById(orderId);

        closePDialog();

        RecvBounsSucDialog.getInstance().setCallBack(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //查看详情；
                Intent intent = new Intent(BounsListActivity.this, RedBagDetailSendActivity.class);

                intent.putExtra("orderId", orderId);

                startActivity(intent);

            }
        }).setBounsAmount(amount).show(getSupportFragmentManager(), "");

        //领取成功后通知后设置信息
        setResult(Activity.RESULT_OK);

    }

    @Override
    public void onRecvBounsFail(String failMsg)
    {
        closePDialog();
        toastCustom(failMsg);
        loadListData(true);
    }
}
