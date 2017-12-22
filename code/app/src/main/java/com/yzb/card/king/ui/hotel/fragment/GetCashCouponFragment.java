package com.yzb.card.king.ui.hotel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.BaseCouponBean;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.appwidget.appdialog.HotelProductRoomInfoFragmentDialog;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.hotel.adapter.AppCouponItemAdapter;
import com.yzb.card.king.ui.hotel.adapter.CashCouponItemAdapter;
import com.yzb.card.king.ui.hotel.persenter.GetCouponPersenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.pop.BuySucesWithOkDialog;
import com.yzb.card.king.ui.ticket.fragment.TicketDetailFragmentDialog;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.wallet.logic.pay.PayRequestLogic;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.BackListener;
import com.yzb.wallet.openInterface.PayMethodListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代金券
 * Created by 玉兵 on 2017/12/18.
 */

public class GetCashCouponFragment extends Fragment implements View.OnClickListener, BaseViewLayerInterface {

    private HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall;

    TicketDetailFragmentDialog.TicketDialogInterface ticketDetailDataCall;

    private RecyclerView wvLvData;

    private CashCouponItemAdapter mAdapter;

    private GetCouponPersenter persenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        persenter = new GetCouponPersenter(this);

        View view = inflater.inflate(R.layout.view_get_coupon_center, null);

        initView(view);

        return view;
    }

    private void initView(View view) {

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        tvTitle.setText("购买代金券");

        view.findViewById(R.id.llTemp).setOnClickListener(this);

        wvLvData = (RecyclerView) view.findViewById(R.id.wvLvData);

        mAdapter = new CashCouponItemAdapter(getContext());

        mAdapter.setHandler(dataHandler);

        wvLvData.setLayoutManager(new LinearLayoutManager(getContext()));

        wvLvData.setAdapter(mAdapter);

        wvLvData.post(new Runnable() {
            @Override
            public void run() {
                /**
                 * 请求该酒店的房间信息
                 */
                initRequest();

            }
        });
    }

    public void initRequest() {

        Bundle bundle = getArguments();

        if (bundle.containsKey("localData")) {


        } else {

            ProgressDialogUtil.getInstance().showProgressDialogMsg("正在请求数据……", getContext(), false);

            int issuePlatform = bundle.getInt("issuePlatform");

            int industryId = bundle.getInt("industryId");

            long shopId = bundle.getLong("shopId");

            long storeId = bundle.getLong("storeId");

            long goodsId = bundle.getLong("goodsId");

            persenter.sendCanBuyCashCouponListRequest(issuePlatform, industryId, shopId, storeId, goodsId);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llTemp:

                if (dataCall != null) {
                    dataCall.backAction();
                }

                if (ticketDetailDataCall != null) {

                    ticketDetailDataCall.backAction(2);
                }

                break;
            default:
                break;

        }
    }

    public void setDataCall(HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall) {
        this.dataCall = dataCall;
    }

    private BaseCouponBean baseCouponBean;

    private Handler dataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ProgressDialogUtil.getInstance().showProgressDialogMsg("正在请求数据……", getContext(), false);

            baseCouponBean = (BaseCouponBean) msg.obj;

            exeBuy(baseCouponBean.getActId(), baseCouponBean.getCutAmount());


        }
    };

    private float cutAmount = 0;

    /**
     * 购买代金券；
     */
    private void exeBuy(long actId, float cutAmount) {
        this.cutAmount = cutAmount;

        ProgressDialogUtil.getInstance().showProgressDialog(getContext(), false);

        persenter.sendCreateCouponOrderRequest(actId, cutAmount);
    }

    private OrderOutBean orderOutBean;

    @Override
    public void callSuccessViewLogic(Object o, int type) {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (GetCouponPersenter.CANBUYCASHCOUPONLIST_CODE == type) {
            List<BaseCouponBean> list = JSON.parseArray(o + "", BaseCouponBean.class);

            mAdapter.clear();

            mAdapter.addAll(list);

        }  else if (type == GetCouponPersenter.CREATECOUPONORDER_CODE) {

            ProgressDialogUtil.getInstance().closeProgressDialog();

            orderOutBean = com.alibaba.fastjson.JSONObject.parseObject(o + "", OrderOutBean.class);

            startBuy();

        } else if (type == GetCouponPersenter.UPDATECOUPONPAYDETAIL_CODE) {//更新数据

            initRequest();

        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (GetCouponPersenter.RECEIVECOUPON_CODE == type) {

            ToastUtil.i(getContext(), "领取失败");


        }
    }

    public void setTicketDetailDataCall(TicketDetailFragmentDialog.TicketDialogInterface ticketDetailDataCall) {
        this.ticketDetailDataCall = ticketDetailDataCall;
    }


    private PayRequestLogic payHandle;

    private String payType = "1";

    private String payDetailId = "";

    /**
     * 付款；
     */
    private void startBuy() {
        payHandle = new PayRequestLogic(getActivity());
        // 显示/隐藏 红包账户
        payHandle.showEnvelopPay(false);
        // 显示/隐藏 礼品卡账户
        payHandle.showGiftPay(false);
        // 显示/隐藏 现金账户
        payHandle.showBalancePay(false);
        // 显示/隐藏 信用卡 默认隐藏
        payHandle.showCreditCard(true);
        // 显示/隐藏 借记卡 默认隐藏
        payHandle.showDebitCard(true);

        payHandle.setBack(new BackListener(){

            @Override
            public void callBack(Map<String, String> map) {

                if("0001".equals( map.get("code"))){

                    persenter.delteCouponOrderRequest(Long.parseLong(orderOutBean.getOrderId()));
                }

            }
        });
        //添加卡；
        payHandle.setAddCardCallBack(new AddCardBackListener() {
            @Override
            public void callBack() {

                startActivity(new Intent(getContext(), AddBankCardActivity.class));
            }
        });

        payHandle.payMethodCallBack(new PayMethodListener() {
            @Override
            public void callBack(Map<String, String> map) {
                LogUtil.e("选择付款方式返回数据=" + JSON.toJSONString(map));
                payType = map.get("payType");
                payDetailId = map.get("payDetailId");
            }
        });

        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE) {
                onPaySucess();
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {

                if (RESULT_CODE.equals(com.yzb.wallet.util.WalletConstant.PAY_TYPE_OFF)) {// 支付卡信息不全

                    String str = JSON.toJSONString(resultMap);

                    PayMethod accountInfo = JSON.parseObject(str, PayMethod.class);

                    int cardType = accountInfo.getCardType();

                    Class claz = null;

                    if (cardType == 1) {// 储蓄卡

                        claz = AddBankCardActivity.class;

                    } else if (cardType == 2) {//信用卡

                        claz = AddCanPayCardActivity.class;

                    }
                    Intent intent = new Intent(getContext(), claz);
                    intent.putExtra("cardNo", accountInfo.getCardNo());
                    intent.putExtra("name", accountInfo.getName());
                    startActivity(intent);

                } else {

                    onPaySucess();

                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG) {
                ToastUtil.i(getContext(), ERROR_MSG);
            }
        });

        payHandle.pay(getInputMap(), false);
    }

    private Map<String, String> getInputMap() {
        Map<String, String> params = new HashMap<>();

        params.put("mobile", UserManager.getInstance().getUserBean().getAmountAccount());

        params.put("orderNo", orderOutBean.getOrderNo());

        params.put("orderTime", DateUtil.formatOrderTime(orderOutBean.getOrderTime()));

        String st = String.format("%.2f", cutAmount);

        params.put("amount", st); //订单金额；

        params.put("leftTime", AppConstant.leftTime); //超时时间

        params.put("goodsName", "折扣券"); //商品名称

        params.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP

        params.put("notifyUrl", orderOutBean.getNotifyUrl());

        params.put("merchantNo", WalletConstant.MERCHANT_NO);//商户号

        params.put("sign", AppConstant.sign);//签名

        return params;
    }

    public void onPaySucess() {

        BuySucesWithOkDialog.getInstance().show(getFragmentManager(), "");

        persenter.updateCouponPayDetailRequest(orderOutBean.getOrderId(), orderOutBean.getOrderAmount(), orderOutBean.getOrderTime(), payType, payDetailId);

    }

}

