package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.adapter.StoreJfDetailSearchAdapter;
import com.yzb.card.king.ui.my.bean.StoreJfBean;
import com.yzb.card.king.ui.my.presenter.StoreJfSearchPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类 名： 商店积分查询
 * 作 者： gaolei
 * 日 期：2017年01月03日
 * 描 述：商店积分查询
 */

public class StoreJfSearchActivity extends BaseActivity implements StoreJfDetailSearchAdapter.OnMyItemClickListener, BaseViewLayerInterface {

    private RecyclerView recyclerView;
    private StoreJfDetailSearchAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<StoreJfBean> listData;
    private StoreJfSearchPresenter presenter;
    private static int flag = 1;
    private int lastvisiableItem;
    private Handler handler = new Handler();
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_jf_search);
        presenter = new StoreJfSearchPresenter(this);
        initView();
    }

    private void initData(int page) {
        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", page);
        param.put("pageSize", 20);
        presenter.getStoreJfData(param , CardConstant.QueryShopPointsList , 1);
    }

    private void initAdapter() {
        if (adapter != null ) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new StoreJfDetailSearchAdapter(this, listData);
            recyclerView.addItemDecoration(new DecorationUtil(3));
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            adapter.setOnMyItemClickListener(this);
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.store_detail_jf_recyclerview);
    }

    @Override
    public void OnMyItemClick(RecyclerView parent, View view, int position, List<StoreJfBean> data) {
        startActivity(new Intent(this, StoreJfDetailActivity.class));
    }

    @Override
    public void OnLoad() {

//        if ( size > 19 ) {//条件后面变化
//            final List<StoreJfBean> demos = new ArrayList<StoreJfBean>();
//            initData(flag);
//            int len = listData.size();
//            for (int i = len; i < len + len;) {//最后服务器会有20条数据
//                demos.add(listData.get(i-len));
//                i++;
//            }
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    adapter.addData(demos);
//                }
//            },2000);
//            flag++;
//        } else {
//            Toast.makeText( getApplicationContext(), "亲,么有了！到底了！", Toast.LENGTH_SHORT).show();
//        }
    }



    @Override
    public void onStart() {
        super.onStart();
        initData(0);
        initSwipe();
    }

    @Override
    public void onStop() {
        super.onStop();
        flag = 1;
    }


    private void initSwipe() {

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int itemCount;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastvisiableItem == (adapter.getItemCount() - 1)) {
                    if (size > 19) {
                        final List<StoreJfBean> demos = new ArrayList<StoreJfBean>();
                        initData(flag);
                        int len = listData.size();
                        for (int i = len; i < len + len;) {//最后服务器会有20条数据
                            demos.add(listData.get(i-len));
                            i++;
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter != null) {
                                    adapter.addData(demos);
                                }
                            }
                        },2000);
                        flag++;
                    }else {
                        //Toast.makeText(getApplicationContext() , "亲,么有了！到底了！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastvisiableItem = linearLayoutManager.findLastVisibleItemPosition();
                itemCount = linearLayoutManager.getItemCount();//adapter.getItemCount() 效果一样
            }
        });
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == 1) {
            listData = JSON.parseArray(String.valueOf(o) ,StoreJfBean.class);
            size = listData.size();
            Log.d("gl","listData=======" + listData);
            initAdapter();
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }
}
