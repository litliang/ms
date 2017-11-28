package com.yzb.card.king.ui.integral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.RemindBean;
import com.yzb.card.king.ui.appwidget.menulistview.MenuListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.CreditCardBillBean;
import com.yzb.card.king.ui.integral.adapter.MyRemindAdapter;
import com.yzb.card.king.ui.integral.presenter.MyRemindPresenter;
import com.yzb.card.king.ui.integral.view.MyRemindView;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.ArrayList;
import java.util.List;


/**
 * 文件名 我的提醒
 * 作者:Li JianQiang
 */
public class MyRemindActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MyRemindView, View.OnClickListener {

    private MenuListView recyclerView;
    private MyRemindAdapter myAdapter;
    private SwipeRefreshLayout sRL;
    private int choseType;
    private MyRemindPresenter presenter;
    private boolean isLoad = true;
    private TextView all, yremind, wremind;
    private List<RemindBean> remindBeanList = new ArrayList<>();
    private FrameLayout frl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_remind);
        initView();


    }

    private void initView()
    {
        presenter = new MyRemindPresenter(MyRemindActivity.this);
        all = (TextView) findViewById(R.id.all);
        yremind = (TextView) findViewById(R.id.yremind);
        wremind = (TextView) findViewById(R.id.wremind);
        all.setOnClickListener(this);
        yremind.setOnClickListener(this);
        wremind.setOnClickListener(this);

        recyclerView = (MenuListView) findViewById(R.id.lvRemind);
        myAdapter = new MyRemindAdapter(this, remindBeanList, presenter);
//        rgRemind = (RadioGroup) findViewById(R.id.rgRemind);
        frl = (FrameLayout) findViewById(R.id.frlinfo);
        sRL = (SwipeRefreshLayout) findViewById(R.id.sRL);
        SwipeRefreshSettings.setAttrbutes(this, sRL);
        sRL.setOnRefreshListener(this);
        recyclerView.setAdapter(myAdapter);
        choseType = 1;
        showAll();
        all.setSelected(true);
        yremind.setSelected(false);
        wremind.setSelected(false);
    }

    /**
     * 显示全部
     */
    private void showAll()
    {
        String type = "0";
        presenter.getData(type);
    }

    /**
     * 显示已提醒
     */
    private void showReminded()
    {
        String type = "1";
        presenter.getData(type);
    }

    /**
     * 显示未提醒
     */
    private void showNotRemind()
    {
        String type = "2";
        presenter.getData(type);
    }

    @Override
    public void onRefresh()
    {
        if (choseType == 1)
        {
            showAll();
        } else if (choseType == 2)
        {
            showReminded();
        } else if (choseType == 3)
        {
            showNotRemind();
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
                    MyRemindActivity.this, true);
        }
    }

    /**
     * 访问成功
     *
     * @param rb
     */
    @Override
    public void onSuccess(List<RemindBean> rb)
    {
        if (rb.size() == 0)
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
            remindBeanList.clear();
            remindBeanList.addAll(rb);
            myAdapter.notifyDataSetChanged();
            isLoad = false;
        }
    }

    /**
     * 访问失败
     *
     * @param errorResult
     */
    @Override
    public void onFailed(String errorResult)
    {
        sRL.setRefreshing(false);
        isLoad = false;
        remindBeanList.clear();
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
        ProgressDialogUtil.getInstance().closeProgressDialog();
        isLoad = false;
    }

    /**
     * 访问成功获得信用卡信息
     *
     * @param creditCardBillBean
     */
    @Override
    public void onGoCardInfo(CreditCardBillBean creditCardBillBean)
    {
//        Intent intent = new Intent();
//        Bundle b = new Bundle();
//        b.putSerializable("creditCataData", creditCardBillBean);
//        intent.putExtras(b);
//        intent.setClass(MyRemindActivity.this, CreditRepayActivityNew.class);
//        startActivity(intent);
    }

    /**
     * 删除提醒
     *
     * @param remindBean
     */
    @Override
    public void onDelete(RemindBean remindBean)
    {
        myAdapter.remindBeans.remove(remindBean);
        myAdapter.notifyDataSetChanged();
    }

    public void goBack(View view)
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
                yremind.setSelected(false);
                wremind.setSelected(false);
                choseType = 1;
                showAll();
                break;
            case R.id.yremind:
                all.setSelected(false);
                yremind.setSelected(true);
                wremind.setSelected(false);
                choseType = 2;
                showReminded();
                break;
            case R.id.wremind:
                all.setSelected(false);
                yremind.setSelected(false);
                wremind.setSelected(true);
                choseType = 3;
                showNotRemind();
                break;
        }
    }
}