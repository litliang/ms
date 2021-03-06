package com.yzb.card.king.ui.bonus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.appwidget.LoadMoreListView;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.hotel.persenter.GetCouponPersenter;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.my.activity.CouponsMySelfActivity;
import com.yzb.card.king.ui.my.activity.VoucherMoreShopsActivity;
import com.yzb.card.king.ui.my.adapter.CouponInfoAdapter;
import com.yzb.card.king.ui.my.adapter.VoucherInfoAdapter;
import com.yzb.card.king.ui.my.pop.BuySucesWithOkDialog;
import com.yzb.card.king.ui.my.presenter.CouponInfoPresenter;
import com.yzb.card.king.ui.my.view.CouponInfoView;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.other.bean.IPlace;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.wallet.logic.pay.PayRequestLogic;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.BackListener;
import com.yzb.wallet.openInterface.PayMethodListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 玉兵 on 2017/12/3.
 */
@ContentView(R.layout.fragment_coupons_mall)
public class VoucherCenterActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        LoadMoreListView.OnLoadMoreListener, CouponInfoView,BaseViewLayerInterface
{
    private static final int REQ_GET_CITY = 0x001;

    @ViewInject(R.id.swipeRefresh)
    private SwipeRefreshLayout swipeRefresh;

    @ViewInject(R.id.listView)
    private LoadMoreListView listView;

    @ViewInject(R.id.slideView)
    private SlideShow1ItemView slideView;

    private VoucherInfoAdapter adapter;

    @ViewInject(R.id.tvCity)
    private TextView tvCity;

    @ViewInject(R.id.tvTitleName)
    private TextView tvTitleName;

    @ViewInject(R.id.panelSearch)
    private LinearLayout panelSearch;

    @ViewInject(R.id.panelMore)
    private LinearLayout panelMore;

    @ViewInject(R.id.panelLocation)
    private LinearLayout panelLocation;

    @ViewInject(R.id.ivRight)
    private ImageView ivRight;

    private String cityId;

    private CouponInfoPresenter shopsPresenter;

    private GetCouponPersenter getCouponPersenter;

    protected Location positionedCity, selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        shopsPresenter = new CouponInfoPresenter(this);
        getCouponPersenter = new GetCouponPersenter(this);
        initLocation();
        initView();
        initData();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (CitySelectManager.getInstance().getPlace() != null)
        {
            CitySelectManager csm = CitySelectManager.getInstance();
            IPlace city = csm.getPlace();

            if (city != null)
            {
                cityName = csm.getPlaceName();
                cityId = csm.getPlaceId();
                tvCity.setText(cityName);
                GlobalApp.getInstance().setSelectedCity(cityId, cityName);
                initData();

                csm.clearData();
            }
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        GlobalApp.backFlag = false;
    }


    private void initView()
    {
        tvTitleName.setText("购买代金券");

        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);

        swipeRefresh.setOnRefreshListener(this);

        tvCity.setText(!Utils.isEmpty(selectedCity.getCityName()) ? selectedCity.getCityName() : positionedCity.getCityName());

        listView.setCanLoadMore(true);

        listView.setOnLoadMoreListener(this);

        adapter = new VoucherInfoAdapter(this);

        adapter.setHandler(handler);

        listView.setAdapter(adapter);

        slideView.setDuration(2000);

        slideView.setImageScaleType(ImageView.ScaleType.FIT_XY);

        panelSearch.setOnClickListener(this);

        panelMore.setOnClickListener(this);

        panelLocation.setOnClickListener(this);

        ivRight.setBackgroundResource(R.mipmap.icon_voucher_new_center);
    }

    private void initLocation()
    {
        positionedCity = GlobalApp.getPositionedCity();
        selectedCity = GlobalApp.getSelectedCity();

        cityId = !Utils.isEmpty(selectedCity.getCityId()) ? selectedCity.getCityId() : positionedCity.getCityId();
    }

    private Handler handler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what)
            {

                case CouponInfoAdapter.WHAT_BUY:
                    CouponInfoBean shopBeano= adapter.getItem(msg.arg1);
                    exeBuy(shopBeano.getActId(),shopBeano.getCutAmount());
                    break;
            }
            return false;
        }
    });



    private float cutAmount = 0;
    /**
     * 购买优惠券；
     */
    private void exeBuy(long actId, float cutAmount)
    {

        this.cutAmount = cutAmount;

        ProgressDialogUtil.getInstance().showProgressDialog(this,false);

        getCouponPersenter.sendCreateCouponOrderRequest(actId,cutAmount);
    }


    private void initData()
    {
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                swipeRefresh.setRefreshing(true);

                loadData(true);

            }
        }, 100);
    }

    public void loadData(boolean isRefresh)
    {
        slideView.setParam(AppConstant.CASH_COUPON_SHOP, cityId, AppConstant.coupons_voucher_id + "");

        loadCouponList(isRefresh);
    }

    @Override
    public void onRefresh()
    {
        loadData(true);
    }

    @Override
    public void onLoadMore()
    {
        loadCouponList(false);
    }

    /**
     * 查询商家列表；
     */
    private void loadCouponList(boolean isRefresh)
    {
        if (isRefresh)
        {
            adapter.clearAll();
        }
        Map<String, Object> args = new HashMap<>();

        args.put("cityId", cityId);

        args.put("pageStart", isRefresh ? 0 : adapter.getCount());

        args.put("pageSize", "20");

        shopsPresenter.requestVoucherCoponRequest(isRefresh, args);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panelSearch: //搜索；
                if (isLogin())
                {
                    Intent intentE = new Intent(VoucherCenterActivity.this, CouponsMySelfActivity.class);

                    intentE.putExtra("titleName","我的代金券");

                    intentE.putExtra("type",2);

                    startActivity(intentE);


                }else {

                    Intent intentR = new Intent(VoucherCenterActivity.this, LoginActivity.class);
                    startActivityForResult(intentR, 101);
                }
                break;

            case R.id.panelLocation: //城市；

                Intent intent = new Intent(VoucherCenterActivity.this, SelectPlaceActivity.class);
                startActivityForResult(intent, REQ_GET_CITY);

                break;

            case R.id.panelMore: //更多；

                Intent moreIntent = new Intent(VoucherCenterActivity.this, VoucherMoreShopsActivity.class);
                startActivity(moreIntent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null || resultCode != Activity.RESULT_OK)
        {
            return;
        }
        switch (requestCode)
        {
            case REQ_GET_CITY:

                break;
        }
    }

    @Override
    public void onGetCouponSuc(boolean event_tag, List<CouponInfoBean> list)
    {
        swipeRefresh.setRefreshing(false);
        if (event_tag)
        {
            adapter.clearAll();
        }
        adapter.appendALL(list);
    }

    @Override
    public void onGetCouponFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);
        toastCustom(failMsg);
    }


    private OrderOutBean orderOutBean;

    @Override
    public void callSuccessViewLogic(Object o, int type) {

        if(type == GetCouponPersenter.CREATECOUPONORDER_CODE){

            ProgressDialogUtil.getInstance().closeProgressDialog();

            orderOutBean = com.alibaba.fastjson.JSONObject.parseObject(o+"",OrderOutBean.class);

            startBuy();

        }else  if(type == GetCouponPersenter.UPDATECOUPONPAYDETAIL_CODE){//更新数据

            initData();

        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }

    private PayRequestLogic payHandle;

    private String payType ="1";

    private String payDetailId ="";
    /**
     * 付款；
     */
    private void startBuy(  )
    {
        payHandle = new PayRequestLogic(this);
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

                   getCouponPersenter.delteCouponOrderRequest(Long.parseLong(orderOutBean.getOrderId()));
               }

            }
        });

        //添加卡；
        payHandle.setAddCardCallBack(new AddCardBackListener()
        {
            @Override
            public void callBack()
            {

                startActivity(new Intent(VoucherCenterActivity.this, AddBankCardActivity.class));
            }
        });
        payHandle.payMethodCallBack(new PayMethodListener()
        {
            @Override
            public void callBack(Map<String, String> map)
            {
                LogUtil.e("选择付款方式返回数据=" + JSON.toJSONString(map));
                payType = map.get("payType");
                payDetailId = map.get("payDetailId");
            }
        });
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                onPaySucess();
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {

                if(RESULT_CODE.equals( com.yzb.wallet.util.WalletConstant.PAY_TYPE_OFF)){// 支付卡信息不全

                    String str = JSON.toJSONString(resultMap);

                    PayMethod accountInfo = JSON.parseObject(str , PayMethod.class);

                    int cardType = accountInfo.getCardType();

                    Class claz = null;

                    if(cardType==1){// 储蓄卡

                        claz = AddBankCardActivity.class;

                    }else if(cardType ==2){//信用卡

                        claz = AddCanPayCardActivity.class;

                    }
                    Intent intent = new Intent(VoucherCenterActivity.this, claz);
                    intent.putExtra("cardNo",accountInfo.getCardNo());
                    intent.putExtra("name", accountInfo.getName());
                    startActivity(intent);

                }else{

                    onPaySucess();

                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                LogUtil.i("付款失败；RESULT_CODE=" + RESULT_CODE + ",ERROR_MSG=" + ERROR_MSG);
                ToastUtil.i(VoucherCenterActivity.this,ERROR_MSG);
            }
        });
        payHandle.pay(getInputMap(), false);
    }

    private Map<String, String> getInputMap(  )
    {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", getUserBean().getAmountAccount());
        params.put("orderNo", orderOutBean.getOrderNo());
        params.put("orderTime", DateUtil.formatOrderTime(orderOutBean.getOrderTime()));

        String st = String.format("%.2f", cutAmount);

        params.put("amount",st ); //订单金额；

        params.put("leftTime", AppConstant.leftTime); //超时时间

        params.put("goodsName", "折扣券"); //商品名称

        params.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP

        params.put("notifyUrl", orderOutBean.getNotifyUrl());

        params.put("merchantNo", WalletConstant.MERCHANT_NO);//商户号

        params.put("sign", AppConstant.sign);//签名

        return params;
    }


    public void onPaySucess()
    {
        BuySucesWithOkDialog.getInstance().show(getSupportFragmentManager(), "");

        getCouponPersenter.updateCouponPayDetailRequest(orderOutBean.getOrderId(),orderOutBean.getOrderAmount(),orderOutBean.getOrderTime(),payType,payDetailId);

    }

}


