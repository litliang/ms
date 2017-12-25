package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FragmentMessageEvent;
import com.yzb.card.king.bean.my.CouponNearbyBean;
import com.yzb.card.king.bean.my.CouponsHomeBean;
import com.yzb.card.king.bean.travel.FilterBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.AppFactory;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.app.popup.CommonBottomPop;
import com.yzb.card.king.ui.appwidget.LoadMoreListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.ui.my.adapter.CouponsAdapter;
import com.yzb.card.king.ui.my.model.DataUtil;
import com.yzb.card.king.ui.my.presenter.CommandPresenter;
import com.yzb.card.king.ui.my.presenter.CouponNearbyPresenter;
import com.yzb.card.king.ui.my.presenter.CustomCouponsPresenter;
import com.yzb.card.king.ui.my.view.CommandView;
import com.yzb.card.king.ui.my.view.CouponNearbyView;
import com.yzb.card.king.ui.my.view.CustomCouponsView;
import com.yzb.card.king.ui.travel.activity.TravelLineListActivity;
import com.yzb.card.king.ui.travel.activity.TravelProductDetailActivity;
import com.yzb.card.king.ui.travel.bean.TravelSearchCriteriaBean;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的-->优惠券首页；
 *
 * @author gengqiyun
 * @date 2016.12.12
 */
@ContentView(R.layout.activity_coupons_home)
public class CouponsHomeActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        LoadMoreListView.OnLoadMoreListener, CustomCouponsView, CouponNearbyView, CommandView
{
    @ViewInject(R.id.tvTabNoUse)
    private TextView tvTabNoUse;
    @ViewInject(R.id.ivTabNoUse)
    private ImageView ivTabNoUse;

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
    @ViewInject(R.id.couponLv)
    private LoadMoreListView couponLv;
    private CouponsAdapter adapter;
    @ViewInject(R.id.swipeRefresh)
    private SwipeRefreshLayout swipeRefresh;
    @ViewInject(R.id.tvCouponLittle)
    private View tvCouponLittle; //出现优惠券少于4张显示，多于等于4张不显示。
    private CustomCouponsPresenter couponsPresenter;
    private CommonBottomPop bottomPop; //底部pop；
    private String industryId; //行业id；
    private long sort = 1;//1最近领取；2最近到期；3：离我最近，4：人气最高
    private long actStatus = 2; //1代金券；2优惠券；
    private View panelBottom;
    private int clickIndex = -1;
    private String lng;
    private String lat;
    private long circleId;

    private long regionId;
    private String distance; //距离；

    private CommandPresenter commandPresenter;
    private CouponNearbyPresenter nearbyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        couponsPresenter = new CustomCouponsPresenter(this);
        nearbyPresenter = new CouponNearbyPresenter(this);
        commandPresenter = new CommandPresenter(this);
        initView();
        refreshData();
    }

    private void initView()
    {
        setHeader(R.mipmap.icon_back_white, getString(R.string.jbf_yhq), getString(R.string.txt_recv_coupon_center));
        findViewById(R.id.headerLeft).setOnClickListener(this);
        findViewById(R.id.headerRight).setOnClickListener(this);

        lng = positionedCity.getLongitude() + "";
        lat = positionedCity.getLatitude() + "";

        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        panelBottom = findViewById(R.id.panelBottom);

        findViewById(R.id.panelNoUse).setOnClickListener(this);
        findViewById(R.id.panelSort).setOnClickListener(this);
        findViewById(R.id.panelNearby).setOnClickListener(this);
        findViewById(R.id.panelShopCoupon).setOnClickListener(this);

        tvCouponLittle.setVisibility(View.GONE);

        couponLv.setCanLoadMore(true);
        couponLv.setOnLoadMoreListener(this);
        adapter = new CouponsAdapter(this);
        adapter.setHandler(handler);
        couponLv.setAdapter(adapter);
    }

    private Handler handler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            LogUtil.i("收到消息==" + msg.what);
            switch (msg.what)
            {
                case CouponsAdapter.WHAT_CREATE_COMMAND: //生成口令；
                    CouponsHomeBean data = (CouponsHomeBean) msg.obj;
                    generateCommand(data);
                    break;
                case CouponsAdapter.WHAT_USE: //使用；
                    tartgetActivity(msg.arg1, (CouponsHomeBean) msg.obj);
                    break;
                case CommonBottomPop.WHAT_SINGLE: //未使用或智能排序；
                    FilterBean filterBean = (FilterBean) msg.obj;
                    if (clickIndex == 1) //未使用；
                    {
                        actStatus = filterBean.getObjId();
                     //   tvTabNoUse.setText(filterBean.getObjName());
                        selectTabByIndex(1);
                    } else if (clickIndex == 2)//排序；
                    {
                        sort = filterBean.getObjId();
                        selectTabByIndex(2);
                    }
                    loadData(true);
                    break;
                case CommonBottomPop.WHAT_NEAR: //附近；
                    selectTabByIndex(3);
                    FilterBean nearbyBean = (FilterBean) msg.obj;
                    switch (msg.arg1)
                    {
                        case 0: //附近
                            //不限；
                            if ("不限".equals(nearbyBean.getObjName()))
                            {
                                distance = "";
                                unSelectNearyIndex();
                            } else
                            {
                                String distanceLocal = nearbyBean.getObjName().substring(0, nearbyBean.getObjName().length() - 1);
                                distance = Utils.subZeroAndDot(Integer.parseInt(distanceLocal) / 1000.0f + "");
                            }
                            break;
                        case 1: //行政区
                            regionId = nearbyBean.getObjId();
                            if (regionId == Integer.MAX_VALUE)
                            {
                                unSelectNearyIndex();
                            }
                            break;
                        case 2: //商圈
                            circleId = nearbyBean.getObjId();
                            if (circleId == Integer.MAX_VALUE)
                            {
                                unSelectNearyIndex();
                            }
                            break;
                    }
                    loadData(true);
                    break;
                case CommonBottomPop.WHAT_CATEGORY: //频道；
                    selectTabByIndex(4);
                    ChildTypeBean typeBean = (ChildTypeBean) msg.obj;
                    industryId = typeBean.id;
                    //全部；
                    if ("0000".equals(industryId))
                    {
                        industryId = "";
                    }
                    loadData(true);
                    break;
            }
            return false;
        }
    });

    /**
     * 不选中附近；
     */
    private void unSelectNearyIndex()
    {
        tvTabNearby.setSelected(false);
        ivTabNearby.setSelected(false);
    }

    /**
     * 生成口令；
     *
     * @param data
     */
    private void generateCommand(CouponsHomeBean data)
    {
        showNoCancelPDialog(R.string.loading);
        Map<String, Object> args = new HashMap<>();
        args.put("codeType", AppConstant.command_type_coupon);
        args.put("code", data.getActId());
        args.put("activityData", JSON.toJSONString(getActivityMap(data.getActId())));
        commandPresenter.loadData(args);
    }

    private Map<String, Object> getActivityMap(long actId)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("actId", actId + "");
        return param;
    }

    /**
     * 优惠券类型；0:行业；1：某个商家；2：指定商家指定商品；
     */
    private void tartgetActivity(int page, CouponsHomeBean data)
    {
        LogUtil.i("page==" + page + "data=" + JSON.toJSONString(data));
        int index = AppFactory.channelIdToFragmentIndex(data.getIndustryId());

        switch (page)
        {
            case 0: //行业
                Intent intent = new Intent(this, ChannelMainActivity.class);
                String indusId = data.getIndustryId();
                GlobalVariable.industryId = Integer.parseInt(indusId);
                intent.putExtra("pagetype", index);
                ChildTypeBean typeBean = new ChildTypeBean();
                typeBean.typeName = data.getIndustryName();
                intent.putExtra("data", typeBean);
                startActivity(intent);
                break;
            case 1: //某个商家
                String industryId = data.getIndustryId();
                if (AppConstant.ticket_id.equals(industryId))
                {
                    Intent shopIntent = new Intent(this, ChannelMainActivity.class);
                    shopIntent.putExtra("pagetype", index);
                    ChildTypeBean typeBeanShop = new ChildTypeBean();
                    typeBeanShop.typeName = data.getIndustryName();
                    shopIntent.putExtra("data", typeBeanShop);
                    startActivity(shopIntent);
                } else if (AppConstant.travel_id.equals(industryId))
                {
                    Intent ticketIntent = new Intent(this, TravelLineListActivity.class);
                    TravelSearchCriteriaBean bean = new TravelSearchCriteriaBean();
                    bean.setStarCity(cityId);
                    ticketIntent.putExtra("searchBean", bean);
                    startActivity(ticketIntent);
                } else if (AppConstant.hotel_id.equals(industryId))
                {

//                    Intent ticketIntent = new Intent(this, HotelListActivity.class);
//                    HotelParam hotelParam = new HotelParam();
//                    Calendar calendar = Calendar.getInstance();
//                    hotelParam.setArrDate(calendar.getTime());
//                    calendar.add(Calendar.DAY_OF_MONTH, 1);
//                    hotelParam.setDepDate(calendar.getTime());
//
//                    ticketIntent.putExtra("param", hotelParam);
//                    startActivity(ticketIntent);
                }
                break;
            case 2: //指定商家指定商品
                if (AppConstant.ticket_id.equals(data.getIndustryId()))
                {
                    Intent shopIntent = new Intent(this, ChannelMainActivity.class);
                    shopIntent.putExtra("pagetype", index);
                    ChildTypeBean typeBeanGood = new ChildTypeBean();
                    typeBeanGood.typeName = data.getIndustryName();
                    shopIntent.putExtra("data", typeBeanGood);
                    startActivity(shopIntent);
                } else if (AppConstant.travel_id.equals(data.getIndustryId()))
                {
                    Intent travelIntent = new Intent(this, TravelProductDetailActivity.class);
                    travelIntent.putExtra("id", data.getGoodsIds());
                    startActivity(travelIntent);
                } else if (AppConstant.hotel_id.equals(data.getIndustryId()))
                {
//                    Intent hotelIntent = new Intent(this, HotelDetailActivity.class);
//                    hotelIntent.putExtra("id", data.getGoodsIds());
//                    hotelIntent.putExtra("cityId", cityId);
//                    startActivity(hotelIntent);
                }
                break;
        }
    }

    /**
     * 获取附近筛选条件；
     */
    private void getNearbyData()
    {
        Map<String, Object> args = new HashMap<>();
        args.put("cityId", cityId);
        nearbyPresenter.loadData(true, args);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.headerLeft:  //左侧点击；
                goBack();
                break;
            case R.id.headerRight:  //领券中心；
                GlobalApp.backFlag = true;
                FragmentMessageEvent event = new FragmentMessageEvent();
                event.setFragmentIndex(5);//此处与appFactory里面的getHomeTabFragmentList方法排序一直
                EventBus.getDefault().post(event);
                finish();
                break;
            case R.id.panelNoUse: //未使用；
                clickIndex = 1;
                showBottomPop(1);
                break;
            case R.id.panelSort://排序；
                clickIndex = 2;
                showBottomPop(2);
                break;
            case R.id.panelNearby://附近；
                clickIndex = 3;
                showBottomPop(3);
                break;
            case R.id.panelShopCoupon://商家优惠；
                clickIndex = 4;
                showBottomPop(4);
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            goBack();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 返回；
     */
    private void goBack()
    {
        GlobalApp.backFlag = true;
        FragmentMessageEvent backEvent = new FragmentMessageEvent();
        backEvent.setFragmentIndex(3);
        EventBus.getDefault().post(backEvent);
        finish();
    }

    private void selectTabByIndex(int index)
    {
        switch (index)
        {
            case 1:
                ivTabNoUse.setSelected(true);
                tvTabNoUse.setSelected(true);
                break;
            case 2:
                ivTabSort.setSelected(true);
                tvTabSort.setSelected(true);
                break;
            case 3:
                tvTabNearby.setSelected(true);
                ivTabNearby.setSelected(true);
                break;
            case 4:
                ivTabCoupon.setSelected(true);
                tvTabCoupon.setSelected(true);
                break;
        }
    }

    /**
     * 显示底部pop；
     *
     * @param index 1,2,3,4
     */

    private void showBottomPop(int index)
    {
        if (bottomPop == null)
        {
            bottomPop = new CommonBottomPop(this);
            bottomPop.setHeight(Utils.getDisplayHeight(this) - panelBottom.getMeasuredHeight());
            bottomPop.setDataHandler(handler);
        }
        bottomPop.dismiss();
        switch (index)
        {
            case 1:
               // bottomPop.setSelectItemId(actStatus);
                bottomPop.setType(CommonBottomPop.TYPE_ONE);
                bottomPop.setOneData(DataUtil.getCouponUseCondition());
                bottomPop.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);
                break;
            case 2:
               // bottomPop.setSelectItemId(sort);
                bottomPop.setType(CommonBottomPop.TYPE_ONE);
                bottomPop.setOneData(DataUtil.getCouponSorts());
                bottomPop.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);
                break;
            case 3:
                if (bottomPop.getNearbyList() != null && bottomPop.getNearbyList().size() > 0)
                {
                    bottomPop.setType(CommonBottomPop.TYPE_TWO);
                    bottomPop.initTwoData();
                    bottomPop.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);
                } else
                {
                    getNearbyData();
                }
                break;
            case 4:
                bottomPop.setType(CommonBottomPop.TYPE_CATEGORY);
                bottomPop.initCategoryData();
                bottomPop.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);
                break;
        }
    }

    public void refreshData()
    {
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                onRefresh();
            }
        }, 100);
    }

    @Override
    public void onRefresh()
    {
        loadData(true);
    }

    private void loadData(boolean isRefresh)
    {
        if (isRefresh)
        {
            adapter.clearAll();
        }

        Map<String, Object> args = new HashMap<>();
        args.put("pageStart", isRefresh ? 0 : adapter.getCount());
        args.put("pageSize", "20");
        args.put("industryId", industryId);
        args.put("sort", sort + "");
        args.put("juli", distance); //距离不为空，经纬度必须有值；
        args.put("lng", lng);
        args.put("lat", lat);
        args.put("circleId", circleId == Integer.MAX_VALUE ? "" : circleId + "");
        args.put("regionId", regionId == Integer.MAX_VALUE ? "" : regionId + "");
        args.put("couponType", actStatus + "");
        couponsPresenter.loadData(isRefresh, args);
    }

    @Override
    public void onLoadMore()
    {
        loadData(false);
    }

    @Override
    public void onGetCouponsSuc(boolean event_tag, List<CouponsHomeBean> list)
    {
        swipeRefresh.setRefreshing(false);
        if (event_tag)
        {
            adapter.clearAll();
        }
        adapter.appendALL(list);
    }

    @Override
    public void onGetCouponsFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);
        toastCustom(failMsg);
    }

    @Override
    public void onGetCouponNearbySuc(CouponNearbyBean data)
    {
//        bottomPop.setType(CommonBottomPop.TYPE_TWO);
//        data.appendOne();
//        bottomPop.setNearbyBean(data);
//        bottomPop.initTwoData();
//        bottomPop.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);
    }

    @Override
    public void onGetCouponNearbyFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    @Override
    public void onGetCommandSuc(String commandAndUrl)
    {
        closePDialog();
        ShareDialogFragment.getInstance("", "")
                .setTitle("优惠券分享")
                .setContent(adapter.getShareContent())
                .setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setUrl(CommonUtil.getGiftcardShareUrl(commandAndUrl))
                .show(getSupportFragmentManager(), "");
    }

    @Override
    public void onGetCommandFail(String failMsg)
    {
        closePDialog();
        toastCustom(failMsg);
    }
}
