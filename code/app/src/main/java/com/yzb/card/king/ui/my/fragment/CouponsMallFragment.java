package com.yzb.card.king.ui.my.fragment;

import android.app.Activity;
import android.content.Context;
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
import com.yzb.card.king.bean.common.FragmentMessageEvent;
import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.bean.my.CouponShopBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.LoadMoreListView;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.discount.fragment.AppCouponSucessDialgFragment;
import com.yzb.card.king.ui.discount.fragment.LqSucessDialogFragment;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.my.activity.CouponMoreShopsActivity;
import com.yzb.card.king.ui.my.activity.CouponSearchActivity;
import com.yzb.card.king.ui.my.activity.CouponsHomeActivity;
import com.yzb.card.king.ui.my.adapter.CouponInfoAdapter;
import com.yzb.card.king.ui.my.adapter.CouponShopsAdapter;
import com.yzb.card.king.ui.my.presenter.CouponInfoPresenter;
import com.yzb.card.king.ui.my.presenter.CouponShopsPresenter;
import com.yzb.card.king.ui.my.view.CouponInfoView;
import com.yzb.card.king.ui.my.view.CouponShopsView;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.ui.other.bean.IPlace;
import com.yzb.card.king.ui.ticket.presenter.ReceiveYhqPresenter;
import com.yzb.card.king.ui.ticket.view.ReceiveYhqView;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：优惠卷商城碎片
 * 作  者：Li Yubing
 * 日  期：2017/1/10
 * 描  述：
 */
@ContentView(R.layout.fragment_coupons_mall)
public class CouponsMallFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        LoadMoreListView.OnLoadMoreListener, CouponInfoView, ReceiveYhqView
{
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

    private String cityId;

    private CouponInfoPresenter shopsPresenter;

    private ReceiveYhqPresenter receiveYhqPresenter;

    private String industryId = "0";
    private String lng;
    private String lat;
    private String sort = "3";//1最近领取；2最近到期；3：离我最近，4：人气最高
    protected Location positionedCity, selectedCity;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        shopsPresenter = new CouponInfoPresenter(this);
        receiveYhqPresenter = new ReceiveYhqPresenter(this);
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

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        initLocation();
    }

    @Event(R.id.ivBack)
    private void backAction(View view)
    {
        if (GlobalApp.backFlag)
        {
            startActivity(new Intent(getContext(), CouponsHomeActivity.class));
            GlobalApp.backFlag = false;
            //首先通知更新我的碎片，再次开启礼品卡页
            FragmentMessageEvent event = new FragmentMessageEvent();
            event.setFragmentIndex(3);//此处与appFactory里面的getHomeTabFragmentList方法排序一直
            EventBus.getDefault().post(event);
        } else
        {
            FragmentMessageEvent event = new FragmentMessageEvent();
            event.setFragmentIndex(2);//此处与appFactory里面的getHomeTabFragmentList方法排序一直
            EventBus.getDefault().post(event);
        }

    }

    private void initView()
    {
        SwipeRefreshSettings.setAttrbutes(getActivity(), swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        tvCity.setText(!Utils.isEmpty(selectedCity.getCityName()) ? selectedCity.getCityName() : positionedCity.getCityName());

        listView.setCanLoadMore(true);
        listView.setOnLoadMoreListener(this);
        adapter = new CouponInfoAdapter(getContext());
        adapter.setHandler(handler);
        listView.setAdapter(adapter);

        slideView.setDuration(2000);
        slideView.setImageScaleType(ImageView.ScaleType.FIT_XY);

        panelSearch.setOnClickListener(this);
        panelMore.setOnClickListener(this);
        panelLocation.setOnClickListener(this);
    }

    private void initLocation()
    {
        positionedCity = GlobalApp.getPositionedCity();
        selectedCity = GlobalApp.getSelectedCity();

        lng = positionedCity.getLongitude() + "";
        lat = positionedCity.getLatitude() + "";

        cityId = !Utils.isEmpty(selectedCity.getCityId()) ? selectedCity.getCityId() : positionedCity.getCityId();
    }

    private Handler handler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case CouponInfoAdapter.WHAT_GET:
                    CouponInfoBean shopBean = adapter.getItem(msg.arg1);
                    exeGet(shopBean.getCouponId());
                    break;
            }
            return false;
        }
    });

    /**
     * 领取优惠券；
     */
    private void exeGet(long actId)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("actId", actId + "");
        params.put("orderId", actId + "");
        params.put("serviceName", CardConstant.card_app_receivecoupon);
        receiveYhqPresenter.loadData(params);
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
        slideView.setParam(AppConstant.COUPON_SHOPS, cityId, AppConstant.coupons_id + "");
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

        shopsPresenter.requestRecommendCoponRequest(isRefresh, args);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panelSearch: //搜索；
                readyGo(getActivity(), CouponSearchActivity.class);
                break;

            case R.id.panelLocation: //城市；
                Intent intent = new Intent(getContext(), SelectPlaceActivity.class);
                startActivityForResult(intent, REQ_GET_CITY);
                break;
            case R.id.panelMore: //更多；
                Intent moreIntent = new Intent(getContext(), CouponMoreShopsActivity.class);
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

    @Override
    public void onReceiveYhqSucess(String yhqId)
    {
        CouponInfoBean shopBean = adapter.getItemById(yhqId);
        if (shopBean != null)
        {
            AppCouponSucessDialgFragment.getInstance(shopBean.getCutAmount() + "","2",
                    shopBean.getCouponType()+"").show(getFragmentManager(), "");
        }
        loadCouponList(true);
    }

    @Override
    public void onReceiveYhqFail(String failMsg)
    {
        toastCustom(failMsg);
    }
}
