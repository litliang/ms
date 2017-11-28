package com.yzb.card.king.ui.discount.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.AppFactory;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.app.activity.ia.ExamineIaActivity;
import com.yzb.card.king.ui.app.activity.ia.VerifyResultActivity;
import com.yzb.card.king.ui.appwidget.MoreFunctionPublicTitleView;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.appwidget.SlideShow8ItemView2;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.bonus.activity.BoundCenterActivty;
import com.yzb.card.king.ui.discount.adapter.DiscountGitfCardAdapter;
import com.yzb.card.king.ui.discount.adapter.DiscountIndexFragmentAdapter;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.bean.CouponBean;
import com.yzb.card.king.ui.discount.bean.GiftCardBean;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.discount.presenter.DiscountIndexPresenter;
import com.yzb.card.king.ui.gift.activity.GiftCardGiveMindActivity;
import com.yzb.card.king.ui.gift.activity.GiftCardHomeActivity;
import com.yzb.card.king.ui.gift.presenter.GiftCardStoreProductPresenter;
import com.yzb.card.king.ui.gift.view.GiftCardProductView;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.ui.hotel.activtiy.HotelHomeActivity;
import com.yzb.card.king.ui.luxury.activity.ChannelActivity;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.pop.RealNameCertificationDialog;
import com.yzb.card.king.ui.my.pop.ResetPayPasswordDialog;
import com.yzb.card.king.ui.ticket.activity.AirTicketHomeActivity;
import com.yzb.card.king.ui.ticket.presenter.ReceiveYhqPresenter;
import com.yzb.card.king.ui.ticket.presenter.SelectAppVersionPresenter;
import com.yzb.card.king.ui.ticket.view.ReceiveYhqView;
import com.yzb.card.king.ui.ticket.view.SelectAppVersionView;
import com.yzb.card.king.ui.travel.adapter.SpaceItemDecoration;
import com.yzb.card.king.ui.travel.bean.DiscountRecommendBean;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UpdateAppManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户优惠首页
 */
@ContentView(R.layout.discount_home_page)
public class DiscountIndexFragment extends BaseFragment implements View.OnClickListener,
        GlobalApp.OnCityChangeListener, SwipeRefreshLayout.OnRefreshListener, BaseViewLayerInterface, ViewSwitcher.ViewFactory, ReceiveYhqView, SelectAppVersionView, GiftCardProductView {

    @ViewInject(R.id.srl)
    private SwipeRefreshLayout srl;

    @ViewInject(R.id.homepageList)
    private ListView homePageList;

    @ViewInject(R.id.viewTitle)
    private View viewTitle;

    @ViewInject(R.id.tvCityName)
    private TextView tvCityName;

    private RecyclerView giftListView;

    private static final int HANLDER_FLAG = 0;

    private static List<CouponBean> couponBeans = new ArrayList<>();

    private SlideShow8ItemView2 show8ItemView;
    private String typeParentId = AppConstant.discount_type_parentid;
    private SlideShow1ItemView show1ItemView;
    private String cityId;
    private String cityName;
    private UpdateAppManager updateManager;

    private View headerView;
    // 用于便利textSwitcher的下标
    int textSwitcherIndex = 0;
    // 礼品卡实体类
    private List<GiftCardBean> cardBeens;
    // 优惠推荐的适配器
    private DiscountIndexFragmentAdapter discountIndexFragmentAdapter;
    // 上下滚动的优惠券
    private TextSwitcher textSwitcher;

    private int couponBeansSize;

    private LinearLayout discount_card_more;

    private List<DiscountRecommendBean> data;

    private DiscountGitfCardAdapter gitfCardAdapter;

    private boolean isLoad = true;

    private DiscountIndexPresenter presenter;

    private MoreFunctionPublicTitleView moreFunctionPublicTitleView;

    private ReceiveYhqPresenter receiveYhqPresenter;

    private SelectAppVersionPresenter selectAppVersionPresenter;

    private String numMoney;

    private String getId;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setTranslucentStatus(true, R.color.stateWhite);

        GiftCardStoreProductPresenter productPresenter = new GiftCardStoreProductPresenter(this);

        //　发送嗨生活首页的礼品卡推荐
        productPresenter.sendAppShopGiftRequest();

        presenter = new DiscountIndexPresenter(this, this);

        receiveYhqPresenter = new ReceiveYhqPresenter(this);

        //版本更新
        selectAppVersionPresenter = new SelectAppVersionPresenter(this);

        if (!GlobalApp.updateFlag) {

            checkUpdate();
        }

        headerView = View.inflate(getContext(), R.layout.header_discount_home, null);

        textSwitcher = (TextSwitcher) headerView.findViewById(R.id.discount_recommend_coupon_text);

        // 设置切换动画
        textSwitcher.setFactory(this);

        textSwitcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                R.anim.translate_discount_recommend_textswitcher_anim_in));

        textSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                R.anim.translate_discount_recommend_textswitcher_anim_out));

        discount_card_more = (LinearLayout) headerView.findViewById(R.id.discount_card_more);

        discount_card_more.setOnClickListener(this);

        headerView.findViewById(R.id.panelMoreCoupon).setOnClickListener(this);

        show8ItemView = (SlideShow8ItemView2) headerView.findViewById(R.id.show8ItemView);

        show1ItemView = (SlideShow1ItemView) headerView.findViewById(R.id.show1ItemView);

        giftListView = (RecyclerView) headerView.findViewById(R.id.giftcard_listiew);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);

        giftListView.setLayoutManager(manager);

        SpaceItemDecoration dec = new SpaceItemDecoration(CommonUtil.dip2px(getContext(), 20));

        dec.setHInt(3);

        giftListView.addItemDecoration(new SpaceItemDecoration(CommonUtil.dip2px(getContext(), 20)));

        cardBeens = new ArrayList<>();

        gitfCardAdapter = new DiscountGitfCardAdapter(cardBeens, DiscountIndexFragment.this.getContext());

        giftListView.setAdapter(gitfCardAdapter);

        gitfCardAdapter.setImageOnClickListener(new DiscountGitfCardAdapter.ImageOnClickListener() {
            @Override
            public void setOnImageClick(int posistion, List<GiftCardBean> giftCardBeanLists)
            {

                if (isLogin()) {

                    GiftCardBean cardBean = giftCardBeanLists.get(posistion);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "GiftCardHomePageActivity");
                    bundle.putString("productId", cardBean.getProductId() + "");//productId
                    bundle.putString("blessWord", cardBean.getBlessWord());//寄语
                    bundle.putString("imageCode", cardBean.getImageCode());//图片
                    intent.putExtras(bundle);
                    intent.putExtra("titleName",  cardBean.getTypeName());
                    intent.setClass(DiscountIndexFragment.this.getActivity(), GiftCardGiveMindActivity.class);//EntityCardCreateActivity
                    startActivity(intent);

                } else {
                    new GoLoginDailog(getActivity()).create().show();
                }

            }
        });


        SwipeRefreshSettings.setAttrbutes(DiscountIndexFragment.this.getContext(), srl);
        srl.setOnRefreshListener(this);
        data = new ArrayList<>();
        homePageList.addHeaderView(headerView);
        discountIndexFragmentAdapter = new DiscountIndexFragmentAdapter(getActivity(), data);
        homePageList.setDivider(null);
        homePageList.setAdapter(discountIndexFragmentAdapter);
        homePageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
//                DiscountRecommendBean discountRecommendBean = (DiscountRecommendBean)
//                        discountIndexFragmentAdapter.getItem(i - 1);
//                Bundle bundle = new Bundle();
//                bundle.putString("url", discountRecommendBean.getUrl());
//                bundle.putString(WebViewClientActivity.TITLE_NAME, "活动名称");
//                UiUtils.readyGoWithBundle(WebViewClientActivity.class, bundle);
            }
        });
        homePageList.setOnScrollListener(new MyScroll());

        if (GlobalApp.getSelectedCity() != null) {
            cityId = GlobalApp.getSelectedCity().cityId;
        }
        if (!TextUtils.isEmpty(cityId)) {
            initShow1ItemView();
            //自动加载
            srl.post(new Runnable() {
                @Override
                public void run()
                {
                    srl.setRefreshing(true);
                }
            });
        } else {

            //开启正在定位视图,由Application开启实质的定位功能
            ProgressDialogUtil.getInstance().showProgressDialogMsg(getString(R.string.is_located), getActivity(), false);

            tvCityName.setText("正在定位");

            GlobalApp.getInstance().startLocate();

            GlobalApp.getInstance().setOnCityChangeListeners(this);
        }

        init8ItemView();

        //　获取优惠券数据
        presenter.getCouponData();

        // 保存优惠券数据
        if (savedInstanceState != null) {
            couponBeans.clear();
            Serializable couponList = savedInstanceState.getSerializable("couponList");
            if (couponList != null) {
                couponBeans.addAll((List<CouponBean>) couponList);
            }
        }

        //标题类
        moreFunctionPublicTitleView = new MoreFunctionPublicTitleView(getActivity(), viewTitle);

    }

    /**
     * 检查更新；
     */
    private void checkUpdate()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("applicationType", "1"); //应用类型
        params.put("serviceName", CardConstant.select_app_version);
        selectAppVersionPresenter.loadData(params);
    }


    private void initShow1ItemView()
    {
        show1ItemView.setParam(AppConstant.DISCOUNT_HOMEPAGER, cityId, typeParentId);

        show1ItemView.setOnDataLoadFinishListener(new SlideShow1ItemView.OnDataLoadFinishListener() {
            @Override
            public void onDataLoadFinish()
            {

                srl.setRefreshing(false);
            }
        });
    }

    private Handler textSwitcherHandler = new Handler() {

        @Override
        public void handleMessage(Message msg)
        {
            if (textSwitcherIndex > couponBeansSize - 1) {
                textSwitcherIndex = 0;
            }
            textSwitcher.setText(couponBeans.get(textSwitcherIndex).getBatchName());
            textSwitcherIndex++;
            textSwitcherHandler.sendEmptyMessageDelayed(HANLDER_FLAG, 3000);
        }
    };


    private void init8ItemView()
    {
        getTypeBean();
        show8ItemView.setOnItemClickListener(new SlideShow8ItemView2.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String typeId)
            {

                String childTypeJson = SharePrefUtil.getValueFromSp(getActivity(),
                        AppConstant.sp_childtypelist_home, "[]");

                List<ChildTypeBean> childTypeBeans = JSON.parseArray(childTypeJson, ChildTypeBean.class);

                ChildTypeBean selectedBean = null;

                for (ChildTypeBean bean : childTypeBeans) {

                    if (bean.id.equals(typeId)) {

                        selectedBean = bean;

                        break;
                    }

                }

                if (selectedBean != null) {

                    String idStr = typeId;

                    int index = AppFactory.channelIdToFragmentIndex(idStr);

                    if (index != -1) {
                        GlobalVariable.industryId = Integer.parseInt(idStr);

                        if (index == 2) {//酒店

                            startActivity(new Intent(getContext(), HotelHomeActivity.class));

                        } else if (index == 1) {//机票

                            startActivity(new Intent(getContext(), AirTicketHomeActivity.class));
                        } else {

                            Intent intent = new Intent();

                            intent.setClass(getContext(), ChannelMainActivity.class);

                            intent.putExtra("pagetype", index);

                            intent.putExtra("data", selectedBean);

                            startActivity(intent);
                        }
                    } else {

                        ToastUtil.i(getContext(), "敬请期待");
                    }

                }
            }
        });
    }

    private void getTypeBean()
    {
        // 本地json格式的子分类；
        String childTypeJson = SharePrefUtil.getValueFromSp(getActivity(),
                AppConstant.sp_childtypelist_home, "[]");

        List<ChildTypeBean> childTypeBeans = JSON.parseArray(childTypeJson, ChildTypeBean.class);
        if (childTypeBeans != null && childTypeBeans.size() > 0) {
            show8ItemView.setDataList(childTypeBeans, Integer.parseInt(typeParentId));
        }
        presenter.getUserChannel(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode) {
            case 100://重新加载修改后的图标
                String childTypeJson = SharePrefUtil.getValueFromSp(getActivity(),
                        AppConstant.sp_childtypelist_home, "[]");
                show8ItemView.setDataList(JSON.parseArray(childTypeJson, ChildTypeBean.class), 0);
                break;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.ll_edit:
                goEditActivity();
                break;
            case R.id.panelMoreCoupon://进入优惠卷商城
//                FragmentMessageEvent event1 = new FragmentMessageEvent();
//                event1.setFragmentIndex(5);//此处与appFactory里面的getHomeTabFragmentList方法排序一直
//                event1.setTag(true);
//                EventBus.getDefault().post(event1);

                Intent moreIntent = new Intent(getContext(), BoundCenterActivty.class);
                startActivity(moreIntent);
                break;
            case R.id.discount_card_more://新意卡
//                FragmentMessageEvent event = new FragmentMessageEvent();
//                event.setTag(true);
//                event.setFragmentIndex(4);//此处与appFactory里面的getHomeTabFragmentList方法排序一直
//
//                EventBus.getDefault().post(event);
                if (UserManager.getInstance().isLogin()) {

                    goNext(new OnIDValid() {
                        @Override
                        public void onValid()
                        {
                            startActivity(new Intent(getActivity(), GiftCardHomeActivity.class));//
                        }
                    });
                } else {
                    new GoLoginDailog(getContext()).create().show();
                }
                break;

            default:
                break;
        }
    }

    private void goNext(OnIDValid valid)
    {
        if (valid == null) return;
        if (UserBean.AuthenticationStatus_SUCCESS.equals(getValidStatus())) {
            valid.onValid();
        } else {
            RealNameCertificationDialog.getInstance().setDataHandler(uiHandler).show(getFragmentManager(), "");
        }
    }


    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            if (RealNameCertificationDialog.WHAT_LOOK == msg.what) {
                //进入实名认证流程
                startActivityForResult(new Intent(getActivity(), ExamineIaActivity.class), VerifyResultActivity.RESULT_REQUEST_CODE);
            } else if (0 == msg.what) {

                ResetPayPasswordDialog.getInstance().setDataHandler(null).show(getFragmentManager(), "");
            }
        }
    };

    private void goEditActivity()
    {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ChannelActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("typeParentId", typeParentId);
        bundle.putString(ChannelActivity.SP_KEY, AppConstant.sp_childtypelist_home);
        bundle.putString("category", AppConstant.discount_channel_category);
        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //如果已经登录，获取用户频道；
        if (UserManager.getInstance().isLogin()) {
            presenter.getUserChannel(getActivity());
        }

        if (!StringUtils.isEmpty(CitySelectManager.getInstance().getPlaceId())) {
            isLoad = true;
            cityName = CitySelectManager.getInstance().getPlaceName();
            cityId = CitySelectManager.getInstance().getPlaceId();
            GlobalApp.getInstance().setSelectedCity(cityId, cityName);
            moreFunctionPublicTitleView.setCityName(cityName);
            tvCityName.setText(cityName);
            CitySelectManager.getInstance().clearData();
            srl.post(new Runnable() {
                @Override
                public void run()
                {
                    srl.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void onCityChange(Location city)
    {

        String text;
        if (TextUtils.isEmpty(city.cityName)) {
            text = city.msg;
        } else {
            cityId = city.cityId;
            text = city.cityName;
            initShow1ItemView();
            presenter.getUserChannel(getActivity());
            //存储城市信息
            SharePrefUtil.saveToSp(getContext(), SharePrefUtil.CURRENT_HISTORY, JSON.toJSONString(city));
        }

        moreFunctionPublicTitleView.setCityName(text);

        tvCityName.setText(text);

        ProgressDialogUtil.getInstance().closeProgressDialog();

        GlobalApp.getInstance().removeListener(this);

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if (textSwitcherHandler != null) {
            textSwitcherHandler.removeMessages(HANLDER_FLAG);
        }
    }

    @Override
    public void onRefresh()
    {
        isLoad = true;
        initShow1ItemView();
        presenter.getUserChannel(getActivity());
    }

    @Override
    public void onStart()
    {
        super.onStart();
        GlobalVariable.industryId = 1003;
    }

    @Override
    public View makeView()
    {
        TextView textView = new TextView(DiscountIndexFragment.this.getActivity());
        textView.setTextColor(getResources().getColor(R.color.blue_5d7c96));
        textView.setTextSize(16);
        if (couponBeans != null && couponBeans.size() > 0) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    CouponBean couponBean = couponBeans.get(textSwitcherIndex - 1);
                    numMoney = couponBean.getBatchAmount() + "";
                    getDiscountCoupon(couponBean.getBatchId() + "");
                }
            });
        } else {

        }
        return textView;
    }


    private void getDiscountCoupon(String actId)
    {
        if (!isLogin()) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        Map<String, Object> params = new HashMap<>();
        getId = actId;
        params.put("actId", actId); //优惠券id
        params.put("orderId", actId); //优惠券id
        params.put("serviceName", CardConstant.card_app_receivecoupon);
        receiveYhqPresenter.loadData(params);
    }


    @Override
    public void onReceiveYhqSucess(String yhqId)
    {
        LqSucessDialogFragment.getInstance(numMoney,
                LqSucessDialogFragment.TYPE_COUPON).show(getFragmentManager(), "");
    }

    @Override
    public void onReceiveYhqFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    @Override
    public void onSelectAppVersionSucess(String data)
    {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String downLoadUrl, limitVersionNo, tip, version, lastVersionNo;
        downLoadUrl = jsonObject.getString("downLoadUrl");
        limitVersionNo = jsonObject.getString("limitVersionNo");
        lastVersionNo = jsonObject.getString("lastVersionNo");
        tip = jsonObject.getString("tip");
        version = GlobalApp.getInstance().getAppBaseDataBean().getVersionName();
        int limitVersionNoInt, versionInt, lastVersionNoInt;
        limitVersionNoInt = StringToInt(limitVersionNo);
        versionInt = StringToInt(version);
        lastVersionNoInt = StringToInt(lastVersionNo);

        if (lastVersionNoInt > versionInt) {
            updateManager = new UpdateAppManager(getContext());
            updateManager.showNoticeDialog(tip, downLoadUrl, limitVersionNoInt, versionInt);
        }
    }

    public int StringToInt(String str)
    {
        str = str.trim();
        str = str.replace(".", "");
        return Integer.parseInt(str);
    }

    @Override
    public void onSelectAppVersionFail(String failMsg)
    {

    }

    @Override
    public void onLoadProductSuccess(Object o, int type)
    {
        srl.setRefreshing(false);
        cardBeens = JSON.parseArray(String.valueOf(o), GiftCardBean.class);
        gitfCardAdapter.addData(cardBeens);
        gitfCardAdapter.notifyDataSetChanged();
        data = new ArrayList<DiscountRecommendBean>();

        DiscountRecommendBean oneBean = new DiscountRecommendBean();

        oneBean.setImageCodeId(R.mipmap.bg_home_shop_one);

        oneBean.setCategoryName("山水之滨.世外桃源");

        data.add(oneBean);

        DiscountRecommendBean twoBean = new DiscountRecommendBean();

        twoBean.setImageCodeId(R.mipmap.bg_home_shop_one);

        twoBean.setCategoryName("世界那么大.我想去看看");

        data.add(twoBean);

        discountIndexFragmentAdapter.appendData(data);

    }

    @Override
    public void onLoadProductFail(Object o, int type)
    {
        srl.setRefreshing(false);

    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        srl.setRefreshing(false);
        if (o == null) {
            return;
        }

        if (type == 2) {//商户频道

            List<ChildTypeBean> displayChannelList = (List<ChildTypeBean>) o;

            show8ItemView.setDataList(displayChannelList, 0);

        } else if (type == 4) {//礼品

            cardBeens = (List<GiftCardBean>) o;
            gitfCardAdapter.addData(cardBeens);
            gitfCardAdapter.notifyDataSetChanged();


        } else if (type == 5) {//优惠卷

            couponBeans.clear();
            couponBeans.addAll((List<CouponBean>) o);
            DiscountIndexFragment.this
                    .couponBeansSize = couponBeans.size();
            // 初始化textSwitch
            textSwitcherHandler.sendEmptyMessage(HANLDER_FLAG);

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        srl.setRefreshing(false);

        if (type == 6) {
            isLoad = false;
            // homePageList.removeFooterView(loadingView);
        }
    }


    private class MyScroll implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState)
        {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount)
        {

            int lastPostion = view.getLastVisiblePosition();
            if (lastPostion + 1 == totalItemCount) {
                if (isLoad) {
                    isLoad = false;
                }
            }

        }
    }

}

