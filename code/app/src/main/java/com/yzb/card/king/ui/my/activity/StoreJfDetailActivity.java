package com.yzb.card.king.ui.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.adapter.StoreJsDetailAdapter;
import com.yzb.card.king.ui.gift.bean.GiftCardDetailBean;
import com.yzb.card.king.ui.gift.presenter.GiftCardDetailPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类 名： 商店积分明细查询
 * 作 者： gaolei
 * 日 期：2017年01月03日
 * 描 述：商店积分明细查询
 */
public class StoreJfDetailActivity extends BaseActivity implements BaseViewLayerInterface, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerview;
    private GiftCardDetailPresenter presenter;
    private List<GiftCardDetailBean> listBean = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private StoreJsDetailAdapter adapter;
    private static int flag = 1;
    private static int flag_img = 1;
    private int size;
    private int lastvisiableItem;
    private ImageView check_img;
    private String nowDay;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_jf_deatil);

        Calendar timeNow = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        nowDay = df.format(timeNow.getTime());
        presenter = new GiftCardDetailPresenter(this);
        initGiftCardPresent(0);
        initData();
        initView();
        //initSwipe();


    }


    private void initSwipe() {

        recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastvisiableItem == (adapter.getItemCount() - 1)) {
                    if (listBean.size() > 19) {
                        initGiftCardPresent(flag);
                        listBean.addAll(listBean);
                        //设置子类的滑动
                        refreshLayout.canChildScrollUp();
                        adapter.notifyDataSetChanged();
                        flag++;
                        //刷新完成
                    } else {
                        Toast.makeText(StoreJfDetailActivity.this, "亲,么有了！到底了！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastvisiableItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

    }

    //六个月的信息 礼品卡
    private void initGiftCardPresent(int page) {
        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", page);
        param.put("pageSize", 20);
        param.put("startDate", getDateStr(6));
        param.put("endDate", nowDay);
        param.put("type", 4);//1现金；2红包；3礼品卡；4商家积分；5平台积分
        presenter.getListData(param, CardConstant.QueryPaymentsDetailed, 1);
    }

    //全部的信息
    private void initGiftCardPresentAll(int page) {
        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", page);
        param.put("pageSize", 20);
        param.put("type", 4);//1现金；2红包；3礼品卡；4商家积分；5平台积分
        presenter.getListData(param, CardConstant.QueryPaymentsDetailed, 1);
    }


    private void initData() {

        if (listBean != null && listBean.size() != 0) {
            adapter = new StoreJsDetailAdapter(this, listBean);
            recyclerview.setLayoutManager(linearLayoutManager);
            recyclerview.setAdapter(adapter);
            initDataChangeAdapter();
        }

    }

    private void initDataChangeAdapter() {
        if (adapter != null) {
            adapter.setOnClickListenerBillingDetail(new StoreJsDetailAdapter.OnClickListenerBillingDetail() {
                @Override
                public void OnLoad() {
                    if ( size > 19 ) {//条件后面变化
                        final List<GiftCardDetailBean> demos = new ArrayList<GiftCardDetailBean>();
                        initGiftCardPresent(flag);
                        int len = listBean.size();
                        for (int i = len; i < len + len;) {//最后服务器会有20条数据
                            demos.add(listBean.get(i-len));
                            i++;
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addData(demos);
                            }
                        },2000);
                        flag++;
                    } else {
                        Toast.makeText( getApplicationContext(), "亲,么有了！到底了！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initView() {
        recyclerview = (RecyclerView) findViewById(R.id.card_bill_detail_recycler);
        check_img = (ImageView) findViewById(R.id.giftcard_check_img);
        check_img.setOnClickListener(this);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.giftcard_swipe_layout);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW);
    }

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
                        refreshLayout.canChildScrollUp();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

        if (flag_img % 2 == 0) {
            check_img.setImageDrawable(getResources().getDrawable(R.mipmap.icon_giftcard_check));
            initGiftCardPresent(0);
        } else {
            check_img.setImageDrawable(getResources().getDrawable(R.mipmap.icon_giftcard_nocheck));
            initGiftCardPresentAll(0);
        }

        flag_img++;
        flag = 1;
    }


    //六个月的转换 num为后面的月份
    public static String getDateStr(int Num) {
        Calendar timeNow = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String day = df.format(timeNow.getTime());
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate2 = new Date(nowDate.getTime() - (long) Num * 24 * 60 * 60 * 1000 * 30);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }

    public void backAction(View v) {
        flag = 1;
        finish();
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == 1) {
            listBean = JSON.parseArray(String.valueOf(o), GiftCardDetailBean.class);
            size = listBean.size();
            Log.d("gl", "GiftCardDetailBean : " + listBean.size());
            initData();
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if (type == 1) {
            Toast.makeText(this, "礼品卡服务器没有返回数据", Toast.LENGTH_SHORT).show();
        }
    }
}
