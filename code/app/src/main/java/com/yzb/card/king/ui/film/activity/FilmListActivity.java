package com.yzb.card.king.ui.film.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.discount.bean.YpBean;
import com.yzb.card.king.ui.film.presenter.MovieListPersenter;
import com.yzb.card.king.ui.film.view.MovieListView;
import com.yzb.card.king.ui.discount.adapter.FilmListAdapter;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.ToastUtil;

import java.util.List;
import java.util.Map;

/**
 * 电影榜单
 */
public class FilmListActivity extends BaseActivity implements MovieListView
{

    private LinearLayout headerLayout;

    private RecyclerView pioneerList;
    private RecyclerView salesList;
    private RecyclerView collectList;

    private FilmListAdapter listAdapter1;
    private FilmListAdapter listAdapter2;
    private FilmListAdapter listAdapter3;

    private LinearLayoutManager pioneerManager;
    private LinearLayoutManager salesManager;
    private LinearLayoutManager collectManager;

    private MovieListPersenter persenter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        // 设置标题
        setHeader(R.mipmap.icon_back_white, "榜单");

        init();

        switchContentLeft(AppConstant.RES_BACK);

        persenter = new MovieListPersenter(this);


        persenter.sendHitListRequest(null, CardConstant.card_app_query_film_list);

    }


    public void init()
    {

        headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        headerLayout.setBackgroundColor(getResources().getColor(R.color.titleRed));

        pioneerList = (RecyclerView) findViewById(R.id.pioneer_list);
        salesList = (RecyclerView) findViewById(R.id.sales_list);
        collectList = (RecyclerView) findViewById(R.id.collect_list);

        //设置布局管理器
        pioneerManager = new LinearLayoutManager(this);
        salesManager = new LinearLayoutManager(this);
        collectManager = new LinearLayoutManager(this);

        //设置水平
        pioneerManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        salesManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        collectManager.setOrientation(LinearLayoutManager.HORIZONTAL);

    }


    private FilmListAdapter.OnRecyclerViewItemClickListener listener = new FilmListAdapter.OnRecyclerViewItemClickListener()
    {
        @Override
        public void onItemClick(View view, String filmId)
        {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("id", filmId);
            intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
            intent.setClass(FilmListActivity.this, YpDetailActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    @Override
    public void getCategory(List<CategoryBean> categoryBeans)
    {

    }

    @Override
    public void onHitListSuccess(Map<String, Object> data)
    {
        List<Map> pioneer = JSON.parseArray(String.valueOf(data.get("pioneer")), Map.class);
        List<Map> sales = JSON.parseArray(String.valueOf(data.get("sales")), Map.class);
        List<Map> collect = JSON.parseArray(String.valueOf(data.get("collect")), Map.class);

        pioneerList.setLayoutManager(pioneerManager);
        salesList.setLayoutManager(salesManager);
        collectList.setLayoutManager(collectManager);

        listAdapter1 = new FilmListAdapter(FilmListActivity.this, pioneer, listener);
        pioneerList.setAdapter(listAdapter1);

        listAdapter2 = new FilmListAdapter(FilmListActivity.this, sales, listener);
        salesList.setAdapter(listAdapter2);

        listAdapter3 = new FilmListAdapter(FilmListActivity.this, collect, listener);
        collectList.setAdapter(listAdapter3);
    }

    @Override
    public void getMovieList(List<YpBean> ypBeans)
    {

    }

    @Override
    public void getCinemaList(List<StoreBean> storeBeans)
    {

    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }
}
