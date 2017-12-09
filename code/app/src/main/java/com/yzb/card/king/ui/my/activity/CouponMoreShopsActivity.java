package com.yzb.card.king.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.bean.hotel.SearchResultBean;
import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.app.popup.CommonBottomPop;
import com.yzb.card.king.ui.appwidget.LoadMoreListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.bonus.activity.BoundCenterActivty;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.fragment.AppCouponSucessDialgFragment;
import com.yzb.card.king.ui.hotel.persenter.FilterListPersenter;
import com.yzb.card.king.ui.hotel.persenter.GetCouponPersenter;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.my.adapter.CouponInfoAdapter;
import com.yzb.card.king.ui.my.adapter.CouponShopsAdapter;
import com.yzb.card.king.ui.my.model.DataUtil;
import com.yzb.card.king.ui.my.pop.BuySucesWithOkDialog;
import com.yzb.card.king.ui.my.presenter.CouponShopsPresenter;
import com.yzb.card.king.ui.my.view.CouponShopsView;
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

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 功能：优惠券对应商家--更多；
 *
 * @author:gengqiyun
 * @date: 2017/1/12
 */
@ContentView(R.layout.activity_coupon_more_shop)
public class CouponMoreShopsActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        LoadMoreListView.OnLoadMoreListener, CouponShopsView, ReceiveYhqView, BaseViewLayerInterface {

    private static final int REQ_GET_PLACE = 0x001;
    @ViewInject(R.id.swipeRefresh)
    private SwipeRefreshLayout swipeRefresh;
    @ViewInject(R.id.listView)
    private LoadMoreListView listView;

    @ViewInject(R.id.tvTabSort)
    private TextView tvTabSort;
    @ViewInject(R.id.ivTabSort)
    private ImageView ivTabSort;

    @ViewInject(R.id.tvTabNearby)
    private TextView tvTabNearby;
    @ViewInject(R.id.ivTabNearby)
    private ImageView ivTabNearby;

    @ViewInject(R.id.tvTabCoupon)
    private TextView tvTabCoupon;
    @ViewInject(R.id.ivTabCoupon)
    private ImageView ivTabCoupon;

    @ViewInject(R.id.ivRight)
    private ImageView ivRight;

    private CouponInfoAdapter adapter;

    private TextView tvCity;

    private String cityId;

    private CouponShopsPresenter shopsPresenter;

    private String industryId = "0";

    private String lng;

    private String lat;

    private String sort = "2";

    private String actStatus = "2"; //2优惠券；

    private String storeName = null;

    private SubItemBean selectedSubItemBean = null;

    private CommonBottomPop bottomPop;

    private View panelBottom;

    private ReceiveYhqPresenter receiveYhqPresenter;

    private FilterListPersenter filterListPersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        shopsPresenter = new CouponShopsPresenter(this);

        receiveYhqPresenter = new ReceiveYhqPresenter(this);

        filterListPersenter = new FilterListPersenter(this);

        recvIntentData();
        initView();
        initData();
    }

    private void recvIntentData() {

        Intent intent = getIntent();

        Serializable ser = intent.getSerializableExtra("paramData");

        if (ser != null) {

            SearchResultBean srb = (SearchResultBean) ser;

            storeName = srb.getStoreName();

        } else {

            if (Intent.ACTION_VIEW.equals(intent.getAction())) {

                initData();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        recvIntentData();
        adapter.clearAll();

        initData();
    }

    private void initView() {
        findViewById(R.id.ivBack).setOnClickListener(this);

        tvCity = (TextView) findViewById(R.id.tvCity);

        tvCity.setText(!isEmpty(selectedCity.getCityName()) ? selectedCity.getCityName() : positionedCity.getCityName());


        lng = positionedCity.getLongitude() + "";
        lat = positionedCity.getLatitude() + "";

        cityId = !isEmpty(selectedCity.getCityId()) ? selectedCity.getCityId() : positionedCity.getCityId();

        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        listView.setFocusable(false);
        listView.setCanLoadMore(true);
        listView.setOnLoadMoreListener(this);

        adapter = new CouponInfoAdapter(this);
        adapter.setHandler(handler);
        listView.setAdapter(adapter);

        panelBottom = findViewById(R.id.panelBottom);
        findViewById(R.id.panelSearch).setOnClickListener(this);
        findViewById(R.id.panelLocation).setOnClickListener(this);

        findViewById(R.id.panelSort).setOnClickListener(this);
        findViewById(R.id.panelNearby).setOnClickListener(this);
        findViewById(R.id.panelShopCoupon).setOnClickListener(this);
        ivRight.setBackgroundResource(R.mipmap.icon_coupon_new_center);
        ivRight.setOnClickListener(this);

        selectTabByIndex(1);
    }

    private int clickIndex = -1;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {


                case CouponShopsAdapter.WHAT_GET:
                    CouponInfoBean shopBean = adapter.getItem(msg.arg1);
                    exeGet(shopBean.getActId());
                    break;
                case CommonBottomPop.WHAT_SINGLE: //未使用或智能排序；

                    SubItemBean filterBean = (SubItemBean) msg.obj;

                    if (clickIndex == 1) {
                        //排序
                        selectTabByIndex(1);

                        sort = filterBean.getFilterId();
                    }
                    initData();

                    break;
                case CommonBottomPop.WHAT_NEAR: //附近；

                    selectTabByIndex(2);

                    SubItemBean nearbyBean = (SubItemBean) msg.obj;

                    selectedSubItemBean = nearbyBean;

                    initData();

                    break;
                case CommonBottomPop.WHAT_CATEGORY: //频道；

                    selectTabByIndex(3);

                    ChildTypeBean typeBean = (ChildTypeBean) msg.obj;

                    industryId = typeBean.id;

                    //全部；
                    if ("0000".equals(industryId)) {
                        industryId = "0";
                    }
                    initData();
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

                onRefresh();

            }
        }, 100);
    }

    @Override
    public void onRefresh() {
        loadCouponList(true);
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

        Map<String, Object> args = new HashMap<>();//actStatus

        args.put("cityId", cityId);

        args.put("lng", lng);

        args.put("lat", lat);

        if (!"0".equals(industryId)) {

            args.put("industryId", industryId);
        }

        if (!TextUtils.isEmpty(storeName)) {
            args.put("storeName", storeName);
        }

        args.put("sort", sort + "");//1离我最近；2人气最高

        if (selectedSubItemBean != null) {

            List<SubItemBean> list = new ArrayList<>();

            list.add(selectedSubItemBean);

            args.put("filterList", JSON.toJSONString(list)); //公里
        }

        args.put("pageStart", isRefresh ? 0 : adapter.getCount());

        args.put("pageSize", "20");

        shopsPresenter.loadData(isRefresh, args);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.panelSearch: //优惠券搜索；

                Intent intentOne = new Intent(this,CouponSearchActivity.class);

                intentOne.putExtra("youhuiType",1);

                startActivityForResult(intentOne,1000);

                break;
            case R.id.panelLocation: //城市；
                Intent intent = new Intent(this, SelectPlaceActivity.class);
                startActivityForResult(intent, REQ_GET_PLACE);
                break;
            case R.id.panelSort://排序；
                clickIndex = 1;
                showBottomPop(clickIndex);
                break;
            case R.id.panelNearby://附近；
                clickIndex = 2;
                showBottomPop(clickIndex);
                break;
            case R.id.panelShopCoupon://商家优惠；
                clickIndex = 3;
                showBottomPop(clickIndex);
                break;
            case R.id.ivRight://电子

                if (isLogin()) {
                    Intent intentE = new Intent(CouponMoreShopsActivity.this, CouponsMySelfActivity.class);

                    intentE.putExtra("titleName", "我的优惠券");

                    intentE.putExtra("type", 1);

                    startActivity(intentE);

                } else {

                    Intent intentR = new Intent(CouponMoreShopsActivity.this, LoginActivity.class);

                    startActivityForResult(intentR, 101);
                }

                break;
            default:
                break;
        }
    }

    private void selectTabByIndex(int index) {
        switch (index) {

            case 1:
                ivTabSort.setSelected(true);
                tvTabSort.setSelected(true);
                break;
            case 2:
                ivTabNearby.setSelected(true);
                tvTabNearby.setSelected(true);
                break;
            case 3:
                ivTabCoupon.setSelected(true);
                tvTabCoupon.setSelected(true);
                break;
        }
    }

    /**
     * 显示底部pop；
     *
     * @param index 2,3,4
     */
    private void showBottomPop(int index) {

        if (bottomPop == null) {

            bottomPop = new CommonBottomPop(this);

            bottomPop.setHeight(Utils.getDisplayHeight(this) - panelBottom.getMeasuredHeight());

            bottomPop.setDataHandler(handler);

        }

        bottomPop.dismiss();

        switch (index) {

            case 1:
                // bottomPop.setSelectItemId(sort);
                List<SubItemBean> oneList = DataUtil.getCouponSorts2();

                for (SubItemBean sb : oneList) {

                    String filterId = sb.getFilterId();

                    if (sort.equals(filterId)) {

                        sb.setDefault(true);

                    } else {

                        sb.setDefault(false);

                    }

                }

                bottomPop.setType(CommonBottomPop.TYPE_ONE);

                bottomPop.setOneData(oneList);

                bottomPop.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);

                break;
            case 2:
                if (bottomPop.getNearbyList() != null && bottomPop.getNearbyList().size() > 0) {

                    bottomPop.setType(CommonBottomPop.TYPE_TWO);

                    bottomPop.initTwoData();

                    bottomPop.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);

                } else {

                    getNearbyData();
                }
                break;

            case 3:

                bottomPop.setType(CommonBottomPop.TYPE_CATEGORY);

                bottomPop.initCategoryData();

                bottomPop.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);

                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (CitySelectManager.getInstance().getPlace() != null) {

            CitySelectManager csm = CitySelectManager.getInstance();

            IPlace city = csm.getPlace();

            if (city != null) {

                cityId = csm.getPlaceId();

                tvCity.setText(csm.getPlaceName());

                loadCouponList(true);

                csm.clearData();
            }
        }
    }

    /**
     * 获取附近筛选条件；
     */
    private void getNearbyData() {

        filterListPersenter.sendCouponFilterData(cityId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQ_GET_PLACE:
                break;
        }
    }

    @Override
    public void onGetCouponShopsSuc(boolean event_tag, List<CouponInfoBean> list) {

        swipeRefresh.setRefreshing(false);

        listView.onLoadMoreComplete();

        if (event_tag) {
            adapter.clearAll();
        }
        adapter.appendALL(list);
    }

    @Override
    public void onGetCouponShopsFail(String failMsg) {

        swipeRefresh.setRefreshing(false);

        listView.onLoadMoreComplete();

        toastCustom(failMsg);
    }

    @Override
    public void onReceiveYhqSucess(String yhqId) {
        CouponInfoBean shopBean = adapter.getItemById(yhqId);
        if (shopBean != null) {
            AppCouponSucessDialgFragment.getInstance(shopBean.getCutAmount() + "", actStatus,
                    shopBean.getCouponType() + "").show(getSupportFragmentManager(), "");
        }
        loadCouponList(true);
    }

    @Override
    public void onReceiveYhqFail(String failMsg) {
        toastCustom(failMsg);
    }

   // private OrderOutBean orderOutBean;

    @Override
    public void callSuccessViewLogic(Object o, int type) {
        if (type == -1) {
            List<CatalogueTypeBean> catalogueTypeBeanList = JSONArray.parseArray(o + "", CatalogueTypeBean.class);

            bottomPop.setType(CommonBottomPop.TYPE_TWO);

            bottomPop.setNearbyBean(catalogueTypeBeanList);

            bottomPop.initTwoData();

            bottomPop.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);

        } else if (type == GetCouponPersenter.UPDATECOUPONPAYDETAIL_CODE) {//更新数据

            initData();

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        if (type == -1) {
            toastCustom("暂无信息");
        } else {

            ProgressDialogUtil.getInstance().closeProgressDialog();
        }
    }

}
