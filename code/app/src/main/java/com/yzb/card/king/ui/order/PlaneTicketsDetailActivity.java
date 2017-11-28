package com.yzb.card.king.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.common.PayOrderBean;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.activity.pay.AppPreferentialPaymentActivity;
import com.yzb.card.king.ui.discount.adapter.TicketDetailFlightAdapter2;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.ticket.presenter.TicketOrderDetailPresenter;
import com.yzb.card.king.ui.ticket.view.TicketOrderDetailView;
import com.yzb.card.king.util.OrderUtils;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gegnqiyun on 16/12/05.
 * 机票订单详情
 */
public class PlaneTicketsDetailActivity extends BaseActivity implements View.OnClickListener, TicketOrderDetailView {
    public static final String ORDER_BEAN = "id";
    private TextView tvTicketStatus;
    private TextView tvOrderAmount;
    private TextView tvOrderNo;

    private WholeListView originalListView; //原 新 航班；

    private TicketOrderDetBean detailBean;
    private String orderAmount = ""; //订单金额；
    private int orderStatus; //订单状态；
    private TicketDetailFlightAdapter2 detailFlightAdapter2; //改签模块的原新；
    private String orderId;
    private TicketOrderDetailPresenter detailPresenter;

    private TextView tvGaiqian;

    private TextView tvTuipiao;

    private TextView tvGaiqianXiangqing;

    private TextView tvPayMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        colorStatusResId = R.color.color_436a8e;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_tickets_detail);
        detailPresenter = new TicketOrderDetailPresenter(this);
        recvInentData();
        initView();
        loadData();
    }

    public void recvInentData() {
        String serializable = getIntent().getStringExtra(ORDER_BEAN);
        if (serializable != null) {
            orderId = serializable;
        }

    }

    public void initView() {
        findViewById(R.id.panel_back).setOnClickListener(this);
        tvTicketStatus = (TextView) findViewById(R.id.tvTicketStatus);
        tvOrderAmount = (TextView) findViewById(R.id.tvOrderAmount);

        SlideButton slideButton = (SlideButton) findViewById(R.id.slideButton);
        slideButton.setToggleState(SlideButton.ToggleState.open);
        slideButton.setEnable(false);

        tvOrderNo = (TextView) findViewById(R.id.orderNo);

        originalListView = (WholeListView) findViewById(R.id.originalListView);
        detailFlightAdapter2 = new TicketDetailFlightAdapter2(this);
        originalListView.setAdapter(detailFlightAdapter2);

        tvGaiqian = (TextView) findViewById(R.id.tvGaiqian);
        tvGaiqian.setOnClickListener(this);

        tvTuipiao = (TextView) findViewById(R.id.tvTuipiao);
        tvTuipiao.setOnClickListener(this);

        tvGaiqianXiangqing = (TextView) findViewById(R.id.tvGaiqianXiangqing);
        tvGaiqianXiangqing.setOnClickListener(this);

        tvPayMoney = (TextView) findViewById(R.id.tvPayMoney);

        tvPayMoney.setOnClickListener(this);
    }

    /**
     * 加载详情；
     */
    private void loadData() {
        showPDialog(R.string.loading);
        Map<String, Object> args = new HashMap<>();
        args.put("orderId", orderId);
        detailPresenter.loadData(true, args);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.panel_back:
                finish();
                break;

            case R.id.tvGaiqian:
                ToastUtil.i(this, "请到列表页操作");

                break;

            case R.id.tvTuipiao:
                ToastUtil.i(this, "请到列表页操作");
                break;
            case R.id.tvGaiqianXiangqing:

                if (orderInfo == null || orderInfo.size() == 0) {

                    ToastUtil.i(this, "该机票尚未改签");

                } else {

                    TicketOrderDetBean ticketOrderDetBean = new TicketOrderDetBean();

                    ticketOrderDetBean.setOrderInfo(orderInfo);

                    Intent intent = new Intent(this, EndorseTicketInfoActivity.class);

                    intent.putExtra("ticketOrderDetBean",ticketOrderDetBean);

                    startActivity(intent);
                }

                break;

            case R.id.tvPayMoney://支付

//                if(detailBean == null){
//
//                return;
//                }
//
//
//                if (detailBean.getFlightType() != null) {
//                    UserManager.getInstance().setFlightType(detailBean.getFlightType());
//                }
//                //新的特惠付款
//                //查询用户的代金券信息
//                //发放平台
//                int issuePlatform = 0;
//                //行业id
//                int industryId = Integer.parseInt(AppConstant.ticket_id);
//
//                GoldTicketParam goldTicketParam = new GoldTicketParam();
//
//                goldTicketParam.setIssuePlatform(issuePlatform);
//
//                goldTicketParam.setIndustryId(industryId);
//
//                if (bean.getOrderType() ==  OrderBean.ORDER_TYPE_HOTELS &&bean.getHotelInfo() != null) {//酒店
//
//                    goldTicketParam.setStoreId(bean.getHotelInfo().getStoreId());
//
//                    goldTicketParam.setGoodsId(bean.getHotelInfo().getInnerGoodsId());
//
//                    goldTicketParam.setShopId(bean.getHotelInfo().getHotelId());
//
//                }else {
//                    if (!TextUtils.isEmpty(bean.getShopIds())) {
//
//                        int index = bean.getShopIds().indexOf(",");
//
//                        if (index == -1) {
//
//                            long a = Long.parseLong(bean.getShopIds());
//
//                            goldTicketParam.setShopId(a);
//                        } else {
//
//                            String[] strArray = bean.getShopIds().split(",");
//
//                            long a = Long.parseLong(strArray[0]);
//
//                            goldTicketParam.setShopId(a);
//                        }
//
//                    }
//
//                    if (!TextUtils.isEmpty(orderListsPresenter.getGoodIds(bean))) {
//
//                        int index = orderListsPresenter.getGoodIds(bean).indexOf(",");
//
//                        if (index == -1) {
//
//                            long a = Long.parseLong(orderListsPresenter.getGoodIds(bean));
//
//                            goldTicketParam.setGoodsId(a);
//                        } else {
//
//                            String[] strArray = orderListsPresenter.getGoodIds(bean).split(",");
//
//                            long a = Long.parseLong(strArray[0]);
//
//                            goldTicketParam.setGoodsId(a);
//                        }
//
//                    }
//                }
//
//                PayOrderBean payOrderBean = new PayOrderBean();
//
//                payOrderBean.setOrderAmount(Double.parseDouble(Utils.subZeroAndDot(bean.getOrderAmount())));
//
//                payOrderBean.setNotifyUrl(bean.getNotifyUrl());
//
//                payOrderBean.setOrderId(Long.parseLong(bean.getOrderId()));
//
//                payOrderBean.setOrderNo(bean.getOrderNo());
//
//                payOrderBean.setOrderTime(bean.getOrderTime());
//
//                Intent intent = new Intent(mContext, AppPreferentialPaymentActivity.class);
//
//                intent.putExtra("orderData", payOrderBean);
//
//                intent.putExtra("goldTicketParam", goldTicketParam);
//
//                intent.putExtra("goodName", bean.getGoodsName());
//
//                mContext.startActivityForResult(intent, 1000);

                break;
        }
    }

    @Override
    public void onGetOrderDetailSucess(TicketOrderDetBean detailBean) {
        closePDialog();

        this.detailBean = detailBean;

        orderAmount = Utils.subZeroAndDot(detailBean.getOrderAmount());

        orderStatus = detailBean.getOrderStatus();

        tvTicketStatus.setText(OrderUtils.getOrderStatus(orderStatus));

        tvOrderAmount.setText(orderAmount);

        refreshViewData();
    }

    private List<TicketOrderDetBean.OrderInfoBean> orderInfo;

    private void refreshViewData() {
        if (detailBean != null) {

            if(orderStatus == OrderBean.ORDER_STATUS_NO_PAY){

                tvPayMoney.setVisibility(View.GONE);

            }else {

                tvPayMoney.setVisibility(View.GONE);
            }

            List<TicketOrderDetBean.OrderInfoBean> orderInfos = detailBean.getOrderInfo();

            detailFlightAdapter2.setFlightType(detailBean.getFlightType());
            detailFlightAdapter2.clearAll();
            for (TicketOrderDetBean.OrderInfoBean bean : orderInfos) {
                bean.setOrignalState(true);
                bean.setNewState(true);
            }
            detailFlightAdapter2.appendALL(orderInfos);

            tvOrderNo.setText(detailBean.getOrdersNo_hi() + "");

            orderInfo = new ArrayList<TicketOrderDetBean.OrderInfoBean>();

            for (TicketOrderDetBean.OrderInfoBean item : orderInfos) {

                //原；
                List<TicketOrderDetBean.MyFlight> orignalFlights = item.getRetFlight();

                //原  是否为空；
                boolean orignalEmpty = orignalFlights == null || orignalFlights.size() == 0;

                if (orignalEmpty) {

                } else {
                    //存在改签机票
                    orderInfo.add(item);

                }
            }


        }


    }


    @Override
    public void onGetOrderDetailFail(String failMsg) {
        closePDialog();
        toastCustom(failMsg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setResult(resultCode);
        finish();
    }
}
