package com.yzb.card.king.ui.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.adapter.DebitRiseListAdapter;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.app.presenter.DebitRiseListPresenter;
import com.yzb.card.king.ui.appwidget.menulistview.MenuListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.20
 * 用户发票管理
 */
public class DebitRiseManageActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener,BaseViewLayerInterface
{
    private static final int REQ_CODE_ADD_DEBIT_RISE = 0x001;//新增发票抬头；
    private static final String FLAG = "flag";
    private MenuListView listview;
    private DebitRiseListAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    public final static int GET_RISE_DATA = 1;
    public final static int MODEY_RIST_DATA = 2;
    private int currentFlag = MODEY_RIST_DATA;
    private Handler loadDataHandler = new Handler();
    private DebitRiseListPresenter debitRiseListPresenter;

    private TextView tvGoonAdd;

    private View viewAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_rise);
        debitRiseListPresenter = new DebitRiseListPresenter(this);
        assignViews();
        onRefresh();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadDebitRiseList(true);
        onRefresh();
    }

    private void assignViews()
    {
        setHeader(R.mipmap.icon_arrow_back_black, getString(R.string.seting_debit_info));

        if (getIntent().hasExtra(FLAG))
        {
            currentFlag = getIntent().getIntExtra(FLAG, MODEY_RIST_DATA);
        }

        findViewById(R.id.headerLeft).setOnClickListener(this);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        listview = (MenuListView) findViewById(R.id.listview);
        listview.setFocusable(false);
        listview.setSelector(new ColorDrawable(Color.TRANSPARENT));

        adapter = new DebitRiseListAdapter(this);

        adapter.setHandler(dataHandler);

        listview.setAdapter(adapter);

        tvGoonAdd = (TextView) findViewById(R.id.tvGoonAdd);

        tvGoonAdd.setOnClickListener(this);

        viewAdd = findViewById(R.id.viewAdd);

        viewAdd.setOnClickListener(this);

        viewAdd.setVisibility(View.GONE);

        tvGoonAdd.setVisibility(View.GONE);

    }

    private Handler dataHandler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case DebitRiseListAdapter.ITEM_CLICK_EVENT: // item 点击；

                    if (currentFlag == GET_RISE_DATA)
                    {
                        DebitRiseBean temp = adapter.getItem(msg.arg1);
                        Intent intentTemp = new Intent();
                        intentTemp.putExtra("risetBean", temp);
                        setResult(1001, intentTemp);
                        finish();
                    } else
                    {
                        Intent intent = new Intent(DebitRiseManageActivity.this, DebitRiseEditActivity.class);
                        intent.putExtra(AddressEditActivity.ARG_DATA_BEAN, adapter.getItem(msg.arg1));
                        startActivityForResult(intent, REQ_CODE_ADD_DEBIT_RISE);
                    }

                    break;
                case DebitRiseListAdapter.UPDATE_DEBIT_RISE_EVENT: // 设置默认；
                    updateDefaultDebitRise(msg.arg1);
                    break;
                case DebitRiseListAdapter.DEL_SUC_EVENT: //删除；

                    int arg = msg.arg1;

                    if(arg==0){//无抬头信息

                        viewAdd.setVisibility(View.VISIBLE);

                        tvGoonAdd.setVisibility(View.GONE);
                    }

                    break;
            }
            return false;
        }
    });



    private int setFaultIndex ;

    /**
     * 更新默认的发票抬头；
     *
     * @param position 点击的发票抬头的下标；
     */
    private void updateDefaultDebitRise( int position)
    {
        setFaultIndex = position;
        Map<String, Object> param = new HashMap<>();
        param.put("invoiceId", adapter.getItem(position).getInvoiceId() + ""); //发票抬头id；
        param.put("serviceName", CardConstant.setting_invoiceupdate);
        param.put("status", "2"); //
        param.put("id", adapter.getItem(position).getInvoiceId() + "");
        debitRiseListPresenter.addOrUpdateRiseData(param);
    }

    @Override
    public void onRefresh()
    {
        loadDataHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                loadDebitRiseList(true);
            }
        }, 100);
    }

    /**
     * 发票抬头列表
     */
    private void loadDebitRiseList(boolean event_tag)
    {
        Map<String, Object> params = new HashMap<>();
        debitRiseListPresenter.loadData(event_tag, params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
        switch (requestCode)
        {
            case REQ_CODE_ADD_DEBIT_RISE://新增发票抬头
                onRefresh();
                break;
        }
    }

    /**
     * 刷新adapter；
     */
    private void refreshAdapter(List<DebitRiseBean> data)
    {
        if (data != null && data.size() > 0)
        {
            adapter.clear();
            adapter.appendDataList(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.headerLeft:
                finish();
                break;
            case R.id.viewAdd:
            case R.id.tvGoonAdd: // 新增发票抬头；
                readyGoForResult(this, DebitRiseEditActivity.class, REQ_CODE_ADD_DEBIT_RISE);
                break;
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if(type == DebitRiseListPresenter.INVOICELIST_CODE){
            swipeRefresh.setRefreshing(false);
            if (o != null)
            {
                refreshAdapter((List<DebitRiseBean>) o);
            }

            viewAdd.setVisibility(View.GONE);

            tvGoonAdd.setVisibility(View.VISIBLE);

        }else  if(type == DebitRiseListPresenter.INVOICECREATE_CODE){
            toastCustom(R.string.app_set_success);
            adapter.updateDefaultById(setFaultIndex);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if(type == DebitRiseListPresenter.INVOICELIST_CODE){

            swipeRefresh.setRefreshing(false);

            viewAdd.setVisibility(View.VISIBLE);

            tvGoonAdd.setVisibility(View.GONE);

        }else  if(type == DebitRiseListPresenter.INVOICECREATE_CODE){
            toastCustom(o+"");
        }
    }
}
