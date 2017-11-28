package com.yzb.card.king.ui.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.app.presenter.PassengerListPresenter;
import com.yzb.card.king.ui.app.presenter.UpdateDefaultPresenter;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.app.view.PassengerListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.appwidget.menulistview.MenuListView;
import com.yzb.card.king.ui.app.adapter.PassengerInfoAdapter;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.20
 * 旅客信息管理；
 */
public class PassengerManageActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, PassengerListView, AppBaseView
{
    private static final int REQ_CODE_ADD_PASSENGER = 0x001; //添加旅客信息；
    private MenuListView listview;
    private PassengerInfoAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    public static final String KEY = "sourceActivity";
    private String sourceActivity;
    private PassengerListPresenter passengerListPresenter;
    private UpdateDefaultPresenter defaultPresenter;

    private TextView tvFunctionName;

    private  View  viewAdd;

    private  TextView tvGoonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_info);
        passengerListPresenter = new PassengerListPresenter(this);
        defaultPresenter = new UpdateDefaultPresenter(this);
        assignViews();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        onRefresh();
    }

    private void assignViews()
    {
        sourceActivity = getIntent().getStringExtra(KEY);

        tvFunctionName = (TextView) findViewById(R.id.tvFunctionName);
        tvFunctionName.setText("增加旅客信息");
        setHeader(R.mipmap.icon_arrow_back_black, getString(R.string.setting_passenger_info));
        findViewById(R.id.headerLeft).setOnClickListener(this);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        findViewById(R.id.ll_add_passenger).setOnClickListener(this);
        viewAdd =  findViewById(R.id.viewAdd);
        viewAdd.setOnClickListener(this);
        findViewById(R.id.ll_invite_friends_fill).setOnClickListener(this);

        tvGoonAdd = (TextView) findViewById(R.id.tvGoonAdd);

        tvGoonAdd.setOnClickListener(this);

        listview = (MenuListView) findViewById(R.id.listview);
        listview.setFocusable(false);
        listview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new PassengerInfoAdapter(this);
        adapter.setHandler(dataHandler);
        listview.setAdapter(adapter);
    }

    private Handler dataHandler = new Handler(new Handler.Callback()
    {

        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case PassengerInfoAdapter.ITEM_CLICK_EVENT: // item 点击；
                    if (!isEmpty(sourceActivity))
                    {
                        setResult(RESULT_OK, getIntent().putExtra("passenger", adapter.getItem(msg.arg1)));
                        finish();
                    } else
                    {
                        Intent intent = new Intent(PassengerManageActivity.this, PassengerEditActivity.class);
                        intent.putExtra(PassengerEditActivity.ARG_DATA_BEAN, adapter.getItem(msg.arg1));
                        startActivityForResult(intent, REQ_CODE_ADD_PASSENGER);
                    }
                    break;
                case PassengerInfoAdapter.UPDATE_DEFAULT_EVENT: // 设置默认；
                    updateDefaultPassenger(msg.arg1);
                    break;
                case PassengerInfoAdapter.DEL_SUC_EVENT: //adapter删除成功；
                    viewAdd.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
                    break;
            }
            return false;
        }
    });

    /**
     * 设置为默认旅客；
     *
     * @param position 点击的旅客的下标；
     */
    private void updateDefaultPassenger(int position)
    {
        PassengerInfoBean pib = adapter.getItem(position);
        Map<String, Object> param = new HashMap<>();
        param.put("serviceName", CardConstant.setting_customerguestupdate);
        param.put("guestId", pib.getId());
        param.put("status", "2"); //0否，1是
        param.put("id", pib.getId());
        defaultPresenter.loadData(param);
    }

    @Override
    public void onLoadPassengersSucess(boolean event_flag, Object data)
    {
        swipeRefresh.setRefreshing(false);
        if (data != null)
        {
            refreshAdapter((List<PassengerInfoBean>) data);
        }
    }

    @Override
    public void onLoadPassengersFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);
        viewAdd.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取常用旅客列表
     *
     * @param event_flag true:下拉刷新；false：加载更多；
     */
    private void loadPassengerList(boolean event_flag)
    {
        Map<String, Object> params = new HashMap<>();
        passengerListPresenter.loadData(event_flag, params);
    }

    private Handler loadDataHandler = new Handler();

    @Override
    public void onRefresh()
    {
        loadDataHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                loadPassengerList(true);
            }
        }, 100);
    }

    /**
     * refresh adapter；
     *
     * @param data
     */
    private void refreshAdapter(List<PassengerInfoBean> data)
    {
        if (data != null && data.size() > 0)
        {
            adapter.clear();
            adapter.appendDataList(data);
        }
        viewAdd.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
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
            case REQ_CODE_ADD_PASSENGER: //添加或删除成功，刷新当前页面；
                String id = data.getStringExtra("id");
                //删除操作；
                if (!isEmpty(id))
                {
                    adapter.deleteById(id);
                    viewAdd.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
                } else
                {
                    onRefresh();
                }
                break;
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
            case R.id.tvGoonAdd:
            case R.id.ll_add_passenger:
            case R.id.viewAdd:
                readyGoForResult(this, PassengerEditActivity.class, REQ_CODE_ADD_PASSENGER);
                break;
            case R.id.ll_invite_friends_fill:
                showShareDialog();
                break;
        }
    }

    private void showShareDialog()
    {
        ShareDialogFragment.getInstance("", "")
                .setTitle("旅客信息填写表")
                .setContent("我正在为你预定旅行产品，需要你填写信息！")
                .setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setUrl(ServiceDispatcher.url_passenger_invite + "?sessionId=" + AppConstant.sessionId)
                .show(getSupportFragmentManager(), "");
    }

    @Override
    public void onViewCallBackSucess(Object data)
    {
        String id = String.valueOf(data);
        toastCustom(R.string.app_set_success);
        adapter.updateDefaultById(id);
    }

    @Override
    public void onViewCallBackFail(String failMsg)
    {
        toastCustom(failMsg);
    }
}
