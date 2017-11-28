package com.yzb.card.king.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.hotel.InvoiceInfoParam;
import com.yzb.card.king.bean.order.HotelOrderServeBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.activity.HotelBillActivity;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.order.adapter.HotelOrderServeAdapter;
import com.yzb.card.king.ui.order.dialog.OrderMessagFuctioneDialog;
import com.yzb.card.king.ui.order.presenter.AppOrderServePreseter;
import com.yzb.card.king.ui.order.presenter.HotelTicketsDetailPresenter;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;

/**
 * 类  名：订单服务
 * 作  者：Li Yubing
 * 日  期：2017/9/16
 * 描  述：
 */
@ContentView(R.layout.activity_app_order_serve)
public class AppOrderServeActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    @ViewInject(R.id.llRight)
    private LinearLayout llRight;

    @ViewInject(R.id.tvNoData)
    private TextView tvNoData;

    @ViewInject(R.id.recycler_view)
    private RefreshRecyclerView mRecyclerView;

    @ViewInject(R.id.llLookOrder)
    private LinearLayout llLookOrder;

    private HotelOrderServeAdapter mAdapter;

    private AppOrderServePreseter appOrderServePreseter;

    private HotelTicketsDetailPresenter hotelTicketsDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView()
    {

        hotelTicketsDetailPresenter = new HotelTicketsDetailPresenter(this);

        appOrderServePreseter = new AppOrderServePreseter(this);

        llRight.setOnClickListener(this);

        llLookOrder.setOnClickListener(this);

        mAdapter = new HotelOrderServeAdapter(this, myHanlder);

        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction()
            {
                getData(true);
            }
        });

        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction()
            {
                getData(false);
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run()
            {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });
    }

    private void getData(boolean flag)
    {
        appOrderServePreseter.sendHotelOrdersServicesRequest();

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.llLookOrder:
            case R.id.llRight:

                startActivityForResult(new Intent(this, OrderManageActivity.class), 1000);

                break;
            default:
                break;

        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        getData(true);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

        if (type == AppOrderServePreseter.HOTELORDERSSERVICES_CODE) {

            List<HotelOrderServeBean> list = JSONArray.parseArray(o + "", HotelOrderServeBean.class);

            mAdapter.clear();

            mRecyclerView.dismissSwipeRefresh();

            mAdapter.addAll(list);

            mRecyclerView.getRecyclerView().scrollToPosition(0);

            mRecyclerView.showNoMore();

            llLookOrder.setVisibility(View.GONE);

            mRecyclerView.setVisibility(View.VISIBLE);

            tvNoData.setVisibility(View.GONE);

        } else if (type == 500) {//成功补开发票

            ToastUtil.i(this, "成功");
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

        if (type == AppOrderServePreseter.HOTELORDERSSERVICES_CODE) {

            mRecyclerView.dismissSwipeRefresh();

            Error error = JSONObject.parseObject(JSON.toJSONString(o),Error.class);

            String code = error.getCode();

            if(HttpConstant.NOINFO.equals(code)|| HttpConstant.NOINFO1.equals(code)){

                mAdapter.clear();

                mAdapter.notifyDataSetChanged();
            }
       }
    }

    private InvoiceInfoParam invoiceInfoParam = null;

    private long orderId;

    private Handler myHanlder = new Handler() {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            int what = msg.what;

            if(what == 0 ){

                int index = msg.arg2;

                List<HotelOrderServeBean> list = mAdapter.getData();

                HotelOrderServeBean bean = list.get(index);

                orderId = bean.getOrderId();

                Intent intent = new Intent(AppOrderServeActivity.this, HotelBillActivity.class);

                intent.putExtra("totalPrice", bean.getOrderAmount());

                if (invoiceInfoParam != null) {
                    intent.putExtra("invoiceDataBean", invoiceInfoParam);
                }
                startActivityForResult(intent, 1001);

            } else {

                int index = msg.arg2;

                List<HotelOrderServeBean> list = mAdapter.getData();

                HotelOrderServeBean bean = list.get(index);

                bean.setTel(AppConstant.APP_TEL);

                HoteUtil.hotelQuestionAction(AppOrderServeActivity.this, bean, what);

            }


        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null) {
            return;
        }
        switch (requestCode) {

            case 1001:

                if (1000 == resultCode) {

                    if (data != null) {//tvInvoice

                        if (data.hasExtra("invoiceInfoParam")) {

                            invoiceInfoParam = (InvoiceInfoParam) data.getSerializableExtra("invoiceInfoParam");

                            orderHandlerFunction("是否补开发票？", OrderMessagFuctioneDialog.FUCTION_SUPPLEMENTORDERINVOICE_CODE);

                        }
                    }
                }

                break;
            default:
                break;
        }
    }

    /**
     * 订单操作功能提示
     *
     * @param cancelDateStr
     */
    private void orderHandlerFunction(String cancelDateStr, int code)
    {

        OrderMessagFuctioneDialog hotelOrderDelMessageDialog = new OrderMessagFuctioneDialog();

        hotelOrderDelMessageDialog.setDialogHandler(dialogHandler);

        hotelOrderDelMessageDialog.setCode(code);

        Bundle bundleDel = new Bundle();

        bundleDel.putString("dialogMsg", cancelDateStr);

        hotelOrderDelMessageDialog.setArguments(bundleDel);

        hotelOrderDelMessageDialog.show(getFragmentManager(), "roomFt");

    }

    private Handler dialogHandler = new Handler() {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            int what = msg.what;

            //发送退款确认请求
            ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);

            if (what == OrderMessagFuctioneDialog.FUCTION_SUPPLEMENTORDERINVOICE_CODE) {

                ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);
                Map<String, Object> params = new HashMap<>();
                params.put("orderId", orderId);
                params.put("invoiceInfo", JSON.toJSONString(invoiceInfoParam));
                hotelTicketsDetailPresenter.sendSupplementOrderInvoiceRequest(CardConstant.ORDER_SUPPLEMENTORDERINVOICE, params);

            }
        }
    };
}
