package com.yzb.card.king.ui.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.presenter.AddressListPresenter;
import com.yzb.card.king.ui.app.presenter.UpdateDefaultPresenter;
import com.yzb.card.king.ui.app.view.AddressListView;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.menulistview.MenuListView;
import com.yzb.card.king.ui.app.adapter.RecvAddressListAdapter;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.21
 * 收货地址管理；
 */
public class AddressManageActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, AddressListView, AppBaseView {
    private static final int REQ_CODE_ADD_ADDRESS = 0x001;
    private MenuListView listview;
    private RecvAddressListAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    public final static int GET_ADDRESS_DATA = 1;
    public final static int MODEY_ADDRESS_DATA = 2;
    private int currentFlag = MODEY_ADDRESS_DATA;

    private AddressListPresenter addressListPresenter;
    private UpdateDefaultPresenter defaultPresenter; //更新默认收货地址；

    private TextView tvGoonAdd;

    private LinearLayout viewAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage);
        addressListPresenter = new AddressListPresenter(this);
        defaultPresenter = new UpdateDefaultPresenter(this);
        assignViews();
    }

    private void assignViews() {
        setHeader(R.mipmap.icon_arrow_back_black, "邮寄地址");
        findViewById(R.id.headerLeft).setOnClickListener(this);

        TextView tvFunctionName = (TextView) findViewById(R.id.tvFunctionName);
        tvFunctionName.setText("新增地址");

        if (getIntent().hasExtra("flag")) {
            currentFlag = getIntent().getIntExtra("flag", MODEY_ADDRESS_DATA);
        }
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        listview = (MenuListView) findViewById(R.id.listview);
        listview.setFocusable(false);
        adapter = new RecvAddressListAdapter(this);
        adapter.setHandler(dataHandler);
        listview.setAdapter(adapter);


        tvGoonAdd = (TextView) findViewById(R.id.tvGoonAdd);
        tvGoonAdd.setOnClickListener(this);

        viewAdd = (LinearLayout) findViewById(R.id.viewAdd);

        viewAdd.setVisibility(View.VISIBLE);

        tvGoonAdd.setVisibility(View.GONE);

        viewAdd.setOnClickListener(this);

    }


    @Override
    public void finish() {
        if (listview.getAdapter().getCount() == 0) {
            setResult(1003);
        }
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }
    int resultcode;
    private Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case RecvAddressListAdapter.UPDATE_GOOD_ADDRESS_EVENT: // 设置默认收货地址；
                    updateDefaultGoodsAddress(msg.arg1);
                    GoodsAddressBean temp1 = adapter.getItem(msg.arg1);
                    Intent intentTemp1 = new Intent();
                    intentTemp1.putExtra("addressBeanTemp", temp1);
                    resultcode = 1005;
                    setResult(1005, intentTemp1);
                    break;
                case RecvAddressListAdapter.ITEM_CLICK_EVENT: // item 点击；
                    if (currentFlag == GET_ADDRESS_DATA) {
                        GoodsAddressBean temp = adapter.getItem(msg.arg1);
                        Intent intentTemp = new Intent();
                        intentTemp.putExtra("addressBeanTemp", temp);
                        setResult(1002, intentTemp);
                        finish();
                    } else {
                        Intent intent = new Intent(AddressManageActivity.this, AddressEditActivity.class);
                        intent.putExtra(AddressEditActivity.ARG_DATA_BEAN, adapter.getItem(msg.arg1));
                        startActivityForResult(intent, REQ_CODE_ADD_ADDRESS);
                    }
                    break;
                case RecvAddressListAdapter.DEL_SUC_EVENT: //adapter删除成功；
                    //   addImg.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
//                    GoodsAddressBean temp = (GoodsAddressBean) msg.obj;
//                    Intent intentTemp = new Intent();
//                    intentTemp.putExtra("addressBeanTemp", temp);
//                    setResult(1003, intentTemp);
                    int arg = msg.arg1;

                    if (arg == 0) {

                        viewAdd.setVisibility(View.VISIBLE);

                        tvGoonAdd.setVisibility(View.GONE);
                    }

                    break;
            }
            return false;
        }
    });

    @Override
    public void onViewCallBackSucess(Object data) {
        toastCustom(R.string.app_set_success);
        adapter.updateDefaultById(intentGoodsAddressIndex);
    }

    @Override
    public void onViewCallBackFail(String failMsg) {
        toastCustom(failMsg);
    }

    public int intentGoodsAddressIndex = -1; // 要更新的默认发票抬头的下标；

    /**
     * 更新默认的收货地址；
     *
     * @param position 点击的收货地址的下标；
     */
    private void updateDefaultGoodsAddress(final int position) {
        this.intentGoodsAddressIndex = position;
        Map<String, Object> param = new HashMap<>();
        param.put("serviceName", CardConstant.setting_customeraddressupdate);
        param.put("addressId", adapter.getItem(position).getAddressId());
        param.put("status", "2"); //
        defaultPresenter.loadData(param);
    }

    @Override
    public void onRefresh() {
        loadDataHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
                loadPassengerList();
            }
        }, 150);
    }

    @Override
    public void onLoadAddressListSucess(boolean event_flag, Object data) {
        swipeRefresh.setRefreshing(false);
        if (data != null) {
            refreshAdapter((List<GoodsAddressBean>) data);

            viewAdd.setVisibility(View.GONE);

            tvGoonAdd.setVisibility(View.VISIBLE);
        } else {

        }
    }

    @Override
    public void onLoadAddressListFail(String failMsg) {
        swipeRefresh.setRefreshing(false);
        //  addImg.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }

    /**
     * 收货地址列表
     */
    private void loadPassengerList() {
        Map<String, Object> param = new HashMap<>();
        addressListPresenter.loadData(true, param);
    }

    private void refreshAdapter(List<GoodsAddressBean> data) {
        if (data != null && data.size() > 0) {
            adapter.clear();
            adapter.appendDataList(data);
        }
        //addImg.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeft:
                finish();
                break;
            case R.id.tvGoonAdd:
            case R.id.viewAdd: // 新增地址；
                readyGoForResult(this, AddressEditActivity.class, REQ_CODE_ADD_ADDRESS);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQ_CODE_ADD_ADDRESS://新增地址返回；
                String id = data.getStringExtra("id");
                //删除操作；
                if (!isEmpty(id)) {
                    adapter.deleteById(id);

                    int count = adapter.getCount();

                    if (count == 0) {

                        viewAdd.setVisibility(View.VISIBLE);

                        tvGoonAdd.setVisibility(View.GONE);
                    }


                } else {
                    onRefresh();
                }
                break;
        }
    }

}
