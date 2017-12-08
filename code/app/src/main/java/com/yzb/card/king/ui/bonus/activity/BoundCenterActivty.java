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
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.appwidget.LoadMoreListView;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.discount.fragment.AppCouponSucessDialgFragment;
import com.yzb.card.king.ui.gift.activity.BuyMindPhysCardActivity;
import com.yzb.card.king.ui.gift.adapter.GiftcardLimitAdapter;
import com.yzb.card.king.ui.gift.adapter.GiftcardSelectLimitAdapter;
import com.yzb.card.king.ui.gift.bean.GiftcardLimitBean;
import com.yzb.card.king.ui.hotel.persenter.GetCouponPersenter;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.my.activity.CouponMoreShopsActivity;
import com.yzb.card.king.ui.my.activity.CouponSearchActivity;
import com.yzb.card.king.ui.my.activity.CouponsMySelfActivity;
import com.yzb.card.king.ui.my.adapter.CouponInfoAdapter;
import com.yzb.card.king.ui.my.pop.BuySucesWithOkDialog;
import com.yzb.card.king.ui.my.presenter.CouponInfoPresenter;
import com.yzb.card.king.ui.my.view.CouponInfoView;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.other.bean.IPlace;
import com.yzb.card.king.ui.ticket.presenter.ReceiveYhqPresenter;
import com.yzb.card.king.ui.ticket.view.ReceiveYhqView;
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
import com.yzb.wallet.openInterface.PayMethodListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券推荐首页
 * Created by 玉兵 on 2017/10/30.
 */
@ContentView(R.layout.fragment_coupons_mall)
public class BoundCenterActivty extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        LoadMoreListView.OnLoadMoreListener, CouponInfoView, ReceiveYhqView, BaseViewLayerInterface {

    private static final int REQ_GET_CITY = 0x001;

    @ViewInject(R.id.swipeRefresh)
    private SwipeRefreshLayout swipeRefresh;

    @ViewInject(R.id.listView)
    private LoadMoreListView listView;

    @ViewInject(R.id.slideView)
    private SlideShow1ItemView slideView;

    private CouponInfoAdapter adapter;

    @ViewInject(R.id.tvCity)
    private TextView tvCity;

    @ViewInject(R.id.panelSearch)
    private LinearLayout panelSearch;

    @ViewInject(R.id.panelMore)
    private LinearLayout panelMore;

    @ViewInject(R.id.panelLocation)
    private LinearLayout panelLocation;

    @ViewInject(R.id.ivRight)
    private ImageView ivRight;

    @ViewInject(R.id.tvTitleName)
    private TextView tvTitleName;

    private String cityId;

    private CouponInfoPresenter shopsPresenter;

    private ReceiveYhqPresenter receiveYhqPresenter;

    protected Location positionedCity, selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        shopsPresenter = new CouponInfoPresenter(this);
        receiveYhqPresenter = new ReceiveYhqPresenter(this);
        initLocation();
        initView();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (CitySelectManager.getInstance().getPlace() != null) {
            CitySelectManager csm = CitySelectManager.getInstance();
            IPlace city = csm.getPlace();

            if (city != null) {
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
    public void onStop() {
        super.onStop();
        GlobalApp.backFlag = false;
    }


    private void initView() {
        tvTitleName.setText("领用优惠券");

        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);

        swipeRefresh.setOnRefreshListener(this);

        tvCity.setText(!Utils.isEmpty(selectedCity.getCityName()) ? selectedCity.getCityName() : positionedCity.getCityName());

        listView.setCanLoadMore(true);

        listView.setOnLoadMoreListener(this);

        adapter = new CouponInfoAdapter(this);

        adapter.setHandler(handler);

        listView.setAdapter(adapter);

        slideView.setDuration(2000);

        slideView.setImageScaleType(ImageView.ScaleType.FIT_XY);

        panelSearch.setOnClickListener(this);

        panelMore.setOnClickListener(this);

        panelLocation.setOnClickListener(this);

        ivRight.setBackgroundResource(R.mipmap.icon_coupon_new_center);
    }

    private void initLocation() {

        positionedCity = GlobalApp.getPositionedCity();

        selectedCity = GlobalApp.getSelectedCity();

        cityId = !Utils.isEmpty(selectedCity.getCityId()) ? selectedCity.getCityId() : positionedCity.getCityId();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case CouponInfoAdapter.WHAT_GET:
                    CouponInfoBean shopBean = adapter.getItem(msg.arg1);
                    exeGet(shopBean.getActId());
                    break;

                default:
                    break;

            }
            return false;
        }
    });

    /**
     * 领取优惠券；
     */
    private void exeGet(long actId) {
        Map<String, Object> params = new HashMap<>();
        params.put("actId", actId + "");
        params.put("serviceName", CardConstant.card_app_receivecoupon);
        receiveYhqPresenter.loadData(params);
    }


    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
                loadData(true);
            }
        }, 100);
    }

    public void loadData(boolean isRefresh) {
        slideView.setParam(AppConstant.COUPON_SHOPS, cityId, AppConstant.coupons_id + "");
        loadCouponList(isRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void onLoadMore() {
        loadCouponList(false);
    }

    /**
     * 查询商家列表；
     */
    private void loadCouponList(boolean isRefresh) {
        if (isRefresh) {
            adapter.clearAll();
        }
        Map<String, Object> args = new HashMap<>();

        args.put("cityId", cityId);

        args.put("pageStart", isRefresh ? 0 : adapter.getCount());

        args.put("pageSize", "20");

        shopsPresenter.requestRecommendCoponRequest(isRefresh, args);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.panelSearch: //我的优惠券；

                if (isLogin()) {

                    Intent intentE = new Intent(BoundCenterActivty.this, CouponsMySelfActivity.class);

                    intentE.putExtra("titleName", "我的优惠券");

                    intentE.putExtra("type", 1);

                    startActivity(intentE);

                } else {

                    Intent intentR = new Intent(BoundCenterActivty.this, LoginActivity.class);
                    startActivityForResult(intentR, 101);
                }

                break;

            case R.id.panelLocation: //城市；
                Intent intent = new Intent(BoundCenterActivty.this, SelectPlaceActivity.class);
                startActivityForResult(intent, REQ_GET_CITY);
                break;
            case R.id.panelMore: //更多；
                Intent moreIntent = new Intent(BoundCenterActivty.this, CouponMoreShopsActivity.class);
                startActivity(moreIntent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQ_GET_CITY:

                break;
        }
    }

    @Override
    public void onGetCouponSuc(boolean event_tag, List<CouponInfoBean> list) {
        swipeRefresh.setRefreshing(false);
        if (event_tag) {
            adapter.clearAll();
        }
        adapter.appendALL(list);
    }

    @Override
    public void onGetCouponFail(String failMsg) {
        swipeRefresh.setRefreshing(false);
        toastCustom(failMsg);
    }

    @Override
    public void onReceiveYhqSucess(String yhqId) {
        CouponInfoBean shopBean = adapter.getItemById(yhqId);
        if (shopBean != null) {
            AppCouponSucessDialgFragment.getInstance(shopBean.getCutAmount() + "", "2",
                    shopBean.getCouponType() + "").show(getSupportFragmentManager(), "");
        }
        loadCouponList(true);
    }

    @Override
    public void onReceiveYhqFail(String failMsg) {
        toastCustom(failMsg);
    }

    private OrderOutBean orderOutBean;

    @Override
    public void callSuccessViewLogic(Object o, int type) {

        if (type == GetCouponPersenter.CREATECOUPONORDER_CODE) {

            ProgressDialogUtil.getInstance().closeProgressDialog();

            orderOutBean = com.alibaba.fastjson.JSONObject.parseObject(o + "", OrderOutBean.class);

//            startBuy();

        } else if (type == GetCouponPersenter.UPDATECOUPONPAYDETAIL_CODE) {//更新数据

            initData();

        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }

}

