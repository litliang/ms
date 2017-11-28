package com.yzb.card.king.ui.gift.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.gift.adapter.BillingDetailAdapter;
import com.yzb.card.king.ui.bonus.adapter.BillingRedBagDetailAdapter;
import com.yzb.card.king.ui.gift.bean.GiftCardDetailBean;
import com.yzb.card.king.ui.gift.presenter.GiftCardDetailPresenter;
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
 * 类 名： 个人账单
 * 作 者： gaolei
 * 日 期：2016年12月22日
 * 描 述：礼品卡账单明细
 */
public class GiftCardBillDetailActivity extends BaseActivity implements BaseViewLayerInterface, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView recyclerview;
    private TextView titleName;
    private String billDetailStr;
    private GiftCardDetailPresenter presenter;
    private List<GiftCardDetailBean> listBean = new ArrayList<>();
    private List<GiftCardDetailBean> redListBean = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private BillingDetailAdapter adapter;
    private static int flag = 1;
    private static int flag_img = 1;
    private int size = 0;
    private BillingRedBagDetailAdapter adapter1;
    private ImageView check_img;
    private String nowDay;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_bill_detail);
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
        presenter = new GiftCardDetailPresenter(this);
        initView();
        initAdapter();
    }



    //六个月的信息 礼品卡
    private void initGiftCardPresent(int page)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", page);
        param.put("pageSize", 20);
        param.put("startDate", getDateStr(6));
        param.put("endDate", nowDay);
        param.put("type", 3);//1现金；2红包；3礼品卡；4平台积分；5商家积分
        presenter.getListData(param, CardConstant.QueryPaymentsDetailed, 1);
        initData();
    }

    //全部的信息
    private void initGiftCardPresentAll(int page)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", page);
        param.put("pageSize", 20);
        param.put("type", 3);//1现金；2红包；3礼品卡；4平台积分；5商家积分
        presenter.getListData(param, CardConstant.QueryPaymentsDetailed, 1);
    }

    //六个月的信息  红包
    private void initRedBagPresent(int page)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", page);
        param.put("pageSize", 20);
        param.put("startDate", getDateStr(6));
        param.put("endDate", nowDay);
        param.put("type", 2);//1现金；2红包；3礼品卡；4平台积分；5商家积分
        presenter.getListData(param, CardConstant.QueryPaymentsDetailed, 2);
    }

    //全部的信息
    private void initRedBagPresentAll(int page)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", page);
        param.put("pageSize", 20);
        param.put("type", 2);//1现金；2红包；3礼品卡；4平台积分；5商家积分
        presenter.getListData(param, CardConstant.QueryPaymentsDetailed, 2);
    }

    private void initAdapter()
    {

        if (billDetailStr.equals("礼品卡明细")) {
            titleName.setText("礼品卡明细");
            initGiftCardPresent(0);
            redListBean = null;
            initData();
//            initDataChangeAdapter();
        } else if (billDetailStr.equals("红包明细")) {
            titleName.setText("红包明细");
            initRedBagPresent(0);
            listBean = null;
            initRedBagData();
//            initDataChangeAdapter1();
        } else if (billDetailStr.equals("现金余额明细")) {
            titleName.setText("现金余额明细");
        } else {
            Toast.makeText(this, "没有您的明细", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData()
    {

        if (listBean != null && listBean.size() != 0) {
            adapter = new BillingDetailAdapter(this, listBean);
            recyclerview.setLayoutManager(linearLayoutManager);
            recyclerview.setAdapter(adapter);
            initDataChangeAdapter();
        }

    }

    private void initRedBagData()
    {

        if (redListBean != null && redListBean.size() != 0) {
            adapter1 = new BillingRedBagDetailAdapter(this, redListBean);
            recyclerview.setLayoutManager(linearLayoutManager);
            recyclerview.setAdapter(adapter1);
            initDataChangeAdapter1();
        }
    }


    private void initDataChangeAdapter1()
    {
        if (adapter1 != null) {
            adapter1.setOnClickListenerBillingDetail(new BillingRedBagDetailAdapter.OnClickListenerBillingDetail() {
                @Override
                public void onLoad()
                {
                    if (size > 19) {//条件后面变化
                        final List<GiftCardDetailBean> demos = new ArrayList<GiftCardDetailBean>();
                        initRedBagPresent(flag);
                        int len = redListBean.size();
                        for (int i = len; i < len + len; ) {//最后服务器会有20条数据
                            demos.add(redListBean.get(i - len));
                            i++;
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run()
                            {
                                adapter1.addData(demos);
                            }
                        }, 2000);
                        flag++;
                    } else {
                        //Toast.makeText(GiftCardBillDetailActivity.this, "亲,么有了！到底了！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void initDataChangeAdapter()
    {
        if (adapter != null) {
            adapter.setOnClickListenerBillingDetail(new BillingDetailAdapter.OnClickListenerBillingDetail() {
                @Override
                public void onLoad()
                {
                    if (size > 19) {//条件后面变化
                        final List<GiftCardDetailBean> demos = new ArrayList<GiftCardDetailBean>();
                        initGiftCardPresent(flag);
                        int len = listBean.size();
                        for (int i = len; i < len + len; ) {//最后服务器会有20条数据
                            demos.add(listBean.get(i - len));
                            i++;
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run()
                            {
                                adapter.addData(demos);
                            }
                        }, 2000);
                        flag++;
                    } else {
                        //Toast.makeText(GiftCardBillDetailActivity.this, "亲,么有了！到底了！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initView()
    {
        recyclerview = (RecyclerView) findViewById(R.id.card_bill_detail_recycler);
        titleName = (TextView) findViewById(R.id.bill_titleName);
        billDetailStr = getIntent().getExtras().getString("billDetail");
        check_img = (ImageView) findViewById(R.id.giftcard_check_img);
        findViewById(R.id.bill_llayout).setOnClickListener(this);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.giftcard_swipe_layout);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW);
    }


    @Override
    public void onRefresh()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        refreshLayout.setRefreshing(true);
                        refreshLayout.canChildScrollUp();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v)
    {
        flag = 1;
        if (flag_img % 2 == 0) {
            check_img.setImageDrawable(getResources().getDrawable(R.mipmap.icon_mingxi_checked));

            if (billDetailStr.equals("礼品卡明细")) {
                initGiftCardPresent(0);
                redListBean = null;
                initData();
            } else if (billDetailStr.equals("红包明细")) {
                initRedBagPresent(0);
                listBean = null;
                initRedBagData();
            } else if (billDetailStr.equals("现金余额明细")) {
            } else {
                Toast.makeText(this, "没有您的明细", Toast.LENGTH_SHORT).show();
            }


        } else {
            check_img.setImageDrawable(getResources().getDrawable(R.mipmap.icon_mingxi_nocheck));

            if (billDetailStr.equals("礼品卡明细")) {
                initGiftCardPresentAll(0);
                redListBean = null;
                initData();
            } else if (billDetailStr.equals("红包明细")) {
                initRedBagPresentAll(0);
                listBean = null;
                initRedBagData();
            } else if (billDetailStr.equals("现金余额明细")) {


            } else {
                Toast.makeText(this, "没有您的明细", Toast.LENGTH_SHORT).show();
            }
        }

        flag_img++;
    }


    //六个月的转换 num为后面的月份
    public static String getDateStr(int Num)
    {
        Calendar timeNow = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String day = df.format(timeNow.getTime());
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date test = new Date((nowDate.getTime() - (long) Num * 24 * 60 * 60 * 1000 * 30) + (long) (24 * 60 * 60 * 1000));
        Date newDate2 = new Date((nowDate.getTime() - (long) Num * 24 * 60 * 60 * 1000 * 30));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }

    public void backAction(View v)
    {
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

        if (type == 2) {
            redListBean = JSON.parseArray(String.valueOf(o), GiftCardDetailBean.class);
            Log.d("gl", "RedBag : " + redListBean.size());
            size = redListBean.size();
            //redListBean.addAll(redListBean);
            initRedBagData();
            adapter1.notifyDataSetChanged();
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
            ToastUtil.i(this,"无信息");
    }
}
