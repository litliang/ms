package com.yzb.card.king.ui.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.adapter.BillingCrashMoneyAdapter;
import com.yzb.card.king.ui.gift.bean.GiftCardDetailBean;
import com.yzb.card.king.ui.my.presenter.BillDetailUtilPresenter;
import com.yzb.card.king.util.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类 名： 现金余额明细
 * 作 者： gaolei
 * 日 期：2016年12月22日
 * 描 述：现金余额明细
 */
public class CrashDetailActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseViewLayerInterface {

    private RecyclerView recyclerview;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private static int flag = 1;
    private static int flag_img = 1;
    private int size;
    private List<GiftCardDetailBean> listBean = new ArrayList<>();
    private ImageView check_img;
    private String nowDay;
    private BillingCrashMoneyAdapter moneyAdapter;
    private Handler handler = new Handler();
    private BillDetailUtilPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_detail);
        initView();
        Calendar timeNow = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String day = df.format(timeNow.getTime());
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date test = new Date(nowDate.getTime() + (long) (24 * 60 * 60 * 1000));
        nowDay = df.format(test);
        presenter = new BillDetailUtilPresenter(this);
        initCrashPresent(0);
    }

    private void initCrashData() {

        if (listBean != null) {
            moneyAdapter = new BillingCrashMoneyAdapter(this, listBean);
            recyclerview.setLayoutManager(linearLayoutManager);
            recyclerview.setAdapter(moneyAdapter);
            moneyAdapter.notifyDataSetChanged();
            initDataChangeAdapter();
        }
    }

    private void initView() {
        recyclerview = (RecyclerView) findViewById(R.id.card_bill_detail_recycler);
        check_img = (ImageView) findViewById(R.id.giftcard_check_img);
        findViewById(R.id.bill_llayout).setOnClickListener(this);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.giftcard_swipe_layout);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW);
    }

    @Override
    public void onClick(View v) {
        flag = 1;
        if (flag_img % 2 == 0) {
            check_img.setImageDrawable(getResources().getDrawable(R.mipmap.icon_mingxi_checked));
            initCrashPresent(0);
        } else {
            check_img.setImageDrawable(getResources().getDrawable(R.mipmap.icon_mingxi_nocheck));
            initCrashPresentAll(0);
        }

        flag_img++;
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


    //六个月的信息  红包
    private void initCrashPresent(int page) {
        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", page);
        param.put("pageSize", 20);
        param.put("startDate", getDateStr(6));
        param.put("endDate", nowDay);
        param.put("type", 1);//1现金；2红包；3礼品卡；4平台积分；5商家积分
        presenter.getBillUtilData(param, CardConstant.QueryPaymentsDetailed, 1);
    }

    //全部的信息
    private void initCrashPresentAll(int page) {
        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", page);
        param.put("pageSize", 20);
        param.put("type", 1);//1现金；2红包；3礼品卡；4商家积分；5商家积分
        presenter.getBillUtilData(param, CardConstant.QueryPaymentsDetailed, 1);
    }






    private void initDataChangeAdapter() {
        if (moneyAdapter != null) {
            moneyAdapter.setOnClickListenerBillingDetail(new BillingCrashMoneyAdapter.OnClickListenerBillingDetail() {
                @Override
                public void onLoad() {
                    if (size > 19) {//条件后面变化
                        final List<GiftCardDetailBean> demos = new ArrayList<GiftCardDetailBean>();
                        if (flag_img % 2 == 0) {
                            initCrashPresent(flag);
                        } else {
                            initCrashPresentAll(flag);
                        }
                        int len = listBean.size();
                        for (int i = len; i < len + len ;) {//最后服务器会有20条数据
                            demos.add(listBean.get(i - len));
                            i++;
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                moneyAdapter.addData(demos);
                            }
                        }, 2000);
                        flag++;
                    } else {
                        //Toast.makeText(getApplicationContext(), "亲,么有了！到底了！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == 1) {
            listBean = JSON.parseArray(String.valueOf(o), GiftCardDetailBean.class);
            size = listBean.size();
            initCrashData();
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
            ToastUtil.i(this,"无信息");
    }
}



