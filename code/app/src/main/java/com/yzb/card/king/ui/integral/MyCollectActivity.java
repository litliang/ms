package com.yzb.card.king.ui.integral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserCollectBean;
import com.yzb.card.king.ui.appwidget.menulistview.MenuListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.integral.adapter.MyCollectAdapter;
import com.yzb.card.king.ui.integral.presenter.MyCollectPresenter;
import com.yzb.card.king.ui.integral.view.MyCollectView;
import com.yzb.card.king.ui.travel.activity.TravelProductDetailActivity;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名  我的收藏
 * 作者:lijianqiang
 */
public class MyCollectActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MyCollectView, View.OnClickListener {
    private MyCollectAdapter myAdapter;
    private MenuListView recyclerView;
    private SwipeRefreshLayout sRL;
    private int choseRb;
    private List<UserCollectBean> userCollectBeen = new ArrayList<>();
    private MyCollectPresenter myCollectPresenter;
    private boolean isLoad = true;
    private TextView all;
    private TextView shop;
    private TextView shopStor;
    private FrameLayout frl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        initView();
        initData();
    }


    private void initView()
    {
        myCollectPresenter = new MyCollectPresenter(MyCollectActivity.this);
        sRL = (SwipeRefreshLayout) findViewById(R.id.sRL1);
        SwipeRefreshSettings.setAttrbutes(MyCollectActivity.this, sRL);
        sRL.setOnRefreshListener(this);
        frl = (FrameLayout) findViewById(R.id.frlinfo);
        all = (TextView) findViewById(R.id.all);
        shop = (TextView) findViewById(R.id.shop);
        shopStor = (TextView) findViewById(R.id.shopStor);
        all.setOnClickListener(this);
        shop.setOnClickListener(this);
        shopStor.setOnClickListener(this);
        recyclerView = (MenuListView) findViewById(R.id.collect_show);
        myAdapter = new MyCollectAdapter(this, userCollectBeen, myCollectPresenter);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyCollectAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int postion)
            {
                UserCollectBean uc = userCollectBeen.get(postion);
                Intent intent = new Intent();
                if (uc.getType().equals("1"))
                {//旅游产品
                    intent.setClass(MyCollectActivity.this, TravelProductDetailActivity.class);
                    intent.putExtra("id", uc.getStoreId() + "");
                    startActivityForResult(intent, 0);
                } else if (uc.getType().equals("5"))
                {//酒店
//                    intent.setClass(MyCollectActivity.this, HotelDetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("id", uc.getStoreId() + "");
//                    intent.putExtras(bundle);
//                    startActivityForResult(intent, 0);
                }
            }
        });

        allList();
        all.setSelected(true);
        shop.setSelected(false);
        shopStor.setSelected(false);

    }


    // 商铺
    private void shopStopList()
    {
        myAdapter.setSearchType(1);
        String type = "1";
        myCollectPresenter.getData(type);
    }

    // 商品
    private void shopList()
    {
        myAdapter.setSearchType(2);
        String type = "2";
        myCollectPresenter.getData(type);
    }

    // 全部
    private void allList()
    {
        myAdapter.setSearchType(0);
        String type = "0";
        myCollectPresenter.getData(type);
    }

    public void initData()
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 0)
        {
            onRefresh();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh()
    {
        if (choseRb == 1)
        {
            shopList();
        } else if (choseRb == 2)
        {
            shopStopList();
        } else if (choseRb == 0) {
            allList();
        }
    }

    /**
     * 访问开始
     */
    @Override
    public void onBegin()
    {
        if (isLoad)
        {
            ProgressDialogUtil.getInstance().showProgressDialogMsg(getString(R.string.list_footer_loading),
                    MyCollectActivity.this, true);
        }
    }

    /**
     * 获取收藏信息成功后
     *
     * @param ucb
     */
    @Override
    public void onSuccess(List<UserCollectBean> ucb)
    {
        if (ucb.size() == 0)
        {
            frl.setVisibility(View.VISIBLE);
            View nullView = View.inflate(this, R.layout.base_null_data_item, null);
            frl.addView(nullView);
            recyclerView.setVisibility(View.GONE);
            sRL.setVisibility(View.GONE);
        } else
        {
            recyclerView.setVisibility(View.VISIBLE);
            frl.setVisibility(View.GONE);
            sRL.setVisibility(View.VISIBLE);
            sRL.setRefreshing(false);
            isLoad = false;
            userCollectBeen.clear();
            userCollectBeen.addAll(ucb);
            myAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取失败
     *
     * @param errorResult
     */
    @Override
    public void onFailed(String errorResult)
    {
        isLoad = false;
        sRL.setRefreshing(false);
        userCollectBeen.clear();
        myAdapter.notifyDataSetChanged();
        frl.setVisibility(View.VISIBLE);
        View nullView = View.inflate(this, R.layout.base_null_data_item, null);
        frl.addView(nullView);
        recyclerView.setVisibility(View.GONE);
        frl.setVisibility(View.VISIBLE);
        sRL.setVisibility(View.GONE);
    }

    /**
     * 网络请求完成
     */
    @Override
    public void onFinish()
    {
        isLoad = false;
        sRL.setRefreshing(false);
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }

    /**
     * 删除我的收藏成功时
     *
     * @param ucBean
     */
    @Override
    public void onDelete(UserCollectBean ucBean)
    {
        isLoad = false;
        sRL.setRefreshing(false);
        myAdapter.notifyDataSetChanged();
        myAdapter.notifyData(ucBean);
        onRefresh();
    }

    public void goBack(View v)
    {
        finish();
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.all:
                all.setSelected(true);
                shop.setSelected(false);
                shopStor.setSelected(false);
                choseRb = 0;
                allList();
                break;
            case R.id.shop:
                all.setSelected(false);
                shop.setSelected(true);
                shopStor.setSelected(false);
                choseRb = 1;
                shopList();
                break;
            case R.id.shopStor:
                all.setSelected(false);
                shop.setSelected(false);
                shopStor.setSelected(true);
                choseRb = 2;
                shopStopList();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
