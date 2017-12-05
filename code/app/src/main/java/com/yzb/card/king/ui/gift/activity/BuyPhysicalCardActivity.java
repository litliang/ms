package com.yzb.card.king.ui.gift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.FilterECardMenuView;
import com.yzb.card.king.ui.appwidget.LoadMoreListView2;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.gift.bean.ECardBean;
import com.yzb.card.king.ui.gift.bean.GiftcardTypeBean;
import com.yzb.card.king.ui.gift.presenter.MindECardsPresenter;
import com.yzb.card.king.ui.gift.view.MindECardsView;
import com.yzb.card.king.ui.my.activity.ActivatePhysCardActivity;
import com.yzb.card.king.ui.my.adapter.MinPCardsAdapter;
import com.yzb.card.king.ui.my.adapter.MindECardsAdapter;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：我的-->实体卡；
 * 作  者：Li Yubing
 * 日  期：2017/10/16
 * 描  述：
 */
public class BuyPhysicalCardActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener, LoadMoreListView2.OnLoadMoreListener, FilterECardMenuView.FilterItemListener,
        MindECardsView
{
    private FilterECardMenuView filterMenu;
    private LoadMoreListView2 eCardsLv;
    private MinPCardsAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private MindECardsPresenter eCardsPresenter;
    private String typeName = ""; //分类；
    private String sort = "1"; //排序；1、热度最高(热度=购买量)、2最新（创建时间）

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_physical_ecard);
        eCardsPresenter = new MindECardsPresenter(this);
        initView();
        refreshData();
    }

    private void initView()
    {
        setTitleNmae("选购实体卡");

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        eCardsLv = (LoadMoreListView2) findViewById(R.id.eCardsLv);
//        eCardsLv.setCanLoadMore(true);
        eCardsLv.setOnLoadMoreListener(this);

        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        filterMenu = (FilterECardMenuView) findViewById(R.id.filterMenu);
        filterMenu.setCategory(1,-1);
        filterMenu.setFilterItemListener(this);

        adapter = new MinPCardsAdapter(this);
        adapter.setDataHandler(dataHandler);
        eCardsLv.setAdapter(adapter);

        findViewById(R.id.llBottomOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                readyGo(BuyPhysicalCardActivity.this, ActivatePhysCardActivity.class);
            }
        });
    }

    /**
     * 数据处理；
     */
    private Handler dataHandler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MindECardsAdapter.WHAT_ITEM: //item点击；

                    ECardBean eCardBean = adapter.getData().get(msg.arg1);

                    Intent intent = new Intent(BuyPhysicalCardActivity.this, BuyMindPhysCardActivity.class);

                    intent.putExtra("ECardBean",eCardBean);

                    startActivity(intent);

                    break;
            }
            return false;
        }
    });


    public void refreshData()
    {
        dataHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                onRefresh();
            }
        }, 100);
    }

    @Override
    public void onRefresh()
    {
        loadData(true);
    }

    @Override
    public void onLoadMore()
    {
        loadData(false);
    }

    private void loadData(boolean isRefresh)
    {
        Map<String, Object> args = new HashMap<>();
        args.put("pageStart", isRefresh ? "0" : adapter.getCount() + "");
        args.put("pageSize", "20");
        args.put("category", "1"); //0全部；1实体卡；2心意卡；
        if("全部".equals(typeName)){
            args.put("typeName", "");//分类；
        }else {
            args.put("typeName", typeName);//分类；
        }
        args.put("sort", sort); //排序；
        eCardsPresenter.loadData(isRefresh, args);
    }

    @Override
    public void onGetMindECardsSuc(boolean event_tag, List<ECardBean> list)
    {
        swipeRefresh.setRefreshing(false);
        if (!event_tag)
        {
            eCardsLv.onLoadMoreComplete();
        }
        if (event_tag)
        {
            adapter.clearAll();
        }
        adapter.appendALL(list);
    }

    @Override
    public void onGetMindECardsFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);
        eCardsLv.onLoadMoreComplete();
        //toastCustom(failMsg);

    }

    @Override
    public void onBackPressed()
    {
        if (!filterMenu.isShowing())
        {
            super.onBackPressed();
        } else
        {
            filterMenu.resetAllStatus();
        }
    }

    @Override
    public void onFilterItemClick(GiftcardTypeBean filterBean, int type)
    {
        switch (type)
        {
            case 0: //分类；
                this.typeName = filterBean.getTypeName();
                break;
            case 1: //排序；
                sort = filterBean.getTypeId();
                break;
        }
        refreshData();
    }
}
