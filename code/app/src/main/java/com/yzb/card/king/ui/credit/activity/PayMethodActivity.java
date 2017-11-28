package com.yzb.card.king.ui.credit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.app.presenter.PayMethodsPresenter;
import com.yzb.card.king.ui.app.view.PayMethodsView;
import com.yzb.card.king.ui.appwidget.DividerDecoration;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.adapter.PayMethodAdapter;
import com.yzb.card.king.ui.my.activity.AddCardAllActivity;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 信用卡还款方式；
 *
 * @author gengqiyun
 * @date 2016.6.7
 */
public class PayMethodActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
        , PayMethodAdapter.FooterViewOnClick, PayMethodsView
{
    private static final int REQ_CODE_ADD_BANK = 0x001; // 添加银行卡；
    public static final String PAYMETHOD_TYPE_USER = "1"; //手动；
    public static final String PAYMETHOD_STATUS = "paymethodStatus"; //标识有序和默认；
    private SwipeRefreshLayout swipeRefresh;
    private WholeRecyclerView listview;

    private List<PayMethod> dataList;
    private View footerView;
    private String paymentStatus;//支付顺序设置（0：自动；1：手动）
    private TextView titlebar_title;
    private PayMethodAdapter payMethodAdapter; // 付款方式；
    private PayMethodsPresenter payMethodsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_method);
        assignViews();

        payMethodsPresenter = new PayMethodsPresenter(this, this);


        String titleName = getIntent().getStringExtra("titleName");
        titlebar_title.setText(titleName);

        swipeRefresh.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                getPayMethods(paymentStatus);
            }
        }, 200);
    }

    private void assignViews()
    {
        if (getUserBean() != null)
        {
            UserBean userBean = getUserBean();
            paymentStatus = userBean.getPaymentStatus();
        }

        dataList = new ArrayList<>();
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        titlebar_title = (TextView) findViewById(R.id.titlebar_title);
        listview = (WholeRecyclerView) findViewById(R.id.listview);
        footerView = getLayoutInflater().inflate(R.layout.footer_list_pay_method, null);

        listview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        payMethodAdapter = new PayMethodAdapter(dataList, this);
        payMethodAdapter.setFooterView(footerView);
        listview.setAdapter(payMethodAdapter);
        listview.addItemDecoration(new DividerDecoration(this, DividerDecoration.VERTICAL_LIST));
        payMethodAdapter.setmFooterViewOnClick(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            paymentStatus = getUserBean().getPaymentStatus();
        }
    }

    @Override
    public void onRefresh()
    {
        getPayMethods(paymentStatus);
    }

    public void goBack(View view)
    {
        finish();
    }

    @Override
    public void onGetPayMethodListSucess(String flag, List<PayMethod> data)
    {
        swipeRefresh.setRefreshing(false);
        if (data != null)
        {
            dataList.clear();
            dataList.addAll(getAvailableAccount(data));
            payMethodAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取可用付款方式
     *
     * @param data
     * @return
     */
    private List<PayMethod> getAvailableAccount(List<PayMethod> data)
    {
        List<PayMethod> payMethodBeans = new ArrayList<>();
        PayMethod payMethodBean;
        for (int i = 0; i < data.size(); i++)
        {
            payMethodBean = data.get(i);
            // 0银行卡快捷支付 ,1(余额)
            if ("0".equals(payMethodBean.getAccountType()) || "1".equals(payMethodBean.getAccountType()))
            {
                payMethodBeans.add(payMethodBean);
            }
        }
        return payMethodBeans;
    }

    @Override
    public void onGetPayMethodListFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);
        toastCustom(failMsg);
    }

    /**
     * 获取支付方式；
     *
     * @param type 有序或默认；  （0：自动；1：手动）
     */
    private void getPayMethods(String type)
    {
        LogUtil.i("付款方式类型type==" + type);
        Map<String, String> params = new HashMap<>();
        params.put("mobile", getUserBean().getAmountAccount());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", AppConstant.sign);
        params.put(PAYMETHOD_STATUS, type);//用来标志是有序还是无序；
        payMethodsPresenter.loadData(params);
    }

    @Override
    public void onFooterClick(int CLICK_TYPE, int position)
    {
        //添加银行卡或信用卡；
        if (CLICK_TYPE == PayMethodAdapter.CLICK_TYPE_ADD)
        {
            Intent intent = new Intent(PayMethodActivity.this, AddCardAllActivity.class);
            intent.putExtra(AddCardAllActivity.BUSINESS_ADD_CARD,AddCardAllActivity.ALL_BUSINESS_VALUE);
            startActivityForResult(intent, REQ_CODE_ADD_BANK);

        } else if (CLICK_TYPE == PayMethodAdapter.CLICK_TYPE_CHOICE) // item点击；
        {
            PayMethod payMethodBean = dataList.get(position);
            Intent intent = new Intent();
            intent.putExtra("payMethodData", payMethodBean);
            setResult(Activity.RESULT_OK, intent);
            LogUtil.i("点击的paymethod==" + JSON.toJSONString(payMethodBean));
            finish();
        }
    }

}
