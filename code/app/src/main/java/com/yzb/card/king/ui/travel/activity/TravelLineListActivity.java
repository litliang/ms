package com.yzb.card.king.ui.travel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.flyco.tablayout.SlidingTabLayout;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.bean.ticket.IFilterPopItem;
import com.yzb.card.king.bean.ticket.SortType;
import com.yzb.card.king.bean.travel.FilterBean;
import com.yzb.card.king.bean.travel.FilterTwoBean;
import com.yzb.card.king.bean.travel.TravelCategorySBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.AppFactory;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.DefindTabView;
import com.yzb.card.king.ui.appwidget.MoreFunctionPublicTitleView;
import com.yzb.card.king.ui.appwidget.SortTopListPop;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.appwidget.popup.TravelDepartrueTimePop;
import com.yzb.card.king.ui.appwidget.popup.TravelScreenPopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.manage.TravelDataManager;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.ticket.interfaces.OnItemSelectLis;
import com.yzb.card.king.ui.travel.bean.TravelScreenBean;
import com.yzb.card.king.ui.travel.bean.TravelSearchCriteriaBean;
import com.yzb.card.king.ui.travel.fragment.TravelLineFragment;
import com.yzb.card.king.ui.travel.model.ITravelList;
import com.yzb.card.king.ui.travel.presenter.TravelListPresenter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：旅游线路列表
 * 作  者：Li Yubing
 * 日  期：2016/11/23
 * 描  述：
 */
@ContentView(R.layout.activity_travel_line_list)
public class TravelLineListActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    //底部控件
    @ViewInject(R.id.llBottomTab)
    private LinearLayout llBottomTab;

    @ViewInject(R.id.iv_menu)
    private ImageView iv_menu;

    @ViewInject(R.id.ivSearch)
    private ImageView ivSearch;

    @ViewInject(R.id.panel_left)
    private LinearLayout panelLeft;

    private SlidingTabLayout tabLayout_8;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private static TravelScreenBean travelScreenBean = new TravelScreenBean(); //筛选和出发时间参数对象

    private TravelScreenPopup travelFilterPop; //筛选pop

    private TravelDepartrueTimePop travelDepartrueTimePop;  //出发时间pop

    private String[] mTitles;

    private MyPagerAdapter mAdapter;

    private TravelListPresenter presenter;

    private SortTopListPop sortTopListPop;

    private TextView sortTv;

    private FilterTwoBean filterTwoBean;

    private ViewPager vp;

    private MoreFunctionPublicTitleView moreFunctionPublicTitleView;
    /**
     * 当前排序编号
     */
    private int sortCurrentIndex = 0;

    private TravelSearchCriteriaBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (travelDepartrueTimePop == null) {
            travelDepartrueTimePop = new TravelDepartrueTimePop(this, travelScreenBean);
        }
        if (travelFilterPop == null) {
            travelFilterPop = new TravelScreenPopup(TravelLineListActivity.this, travelScreenBean);
        }

        initBottom();

        initData();
    }

    /**
     * 返回按钮
     *
     * @param v
     */
    public void backAction(View v)
    {

        if (bean != null) {
            Intent intent = new Intent();
            intent.putExtra("dataBean", bean);
            setResult(1001, intent);
        }

        finish();
    }


    /**
     * 初始化数据
     */
    private void initData()
    {
        moreFunctionPublicTitleView = new MoreFunctionPublicTitleView();

        bean = (TravelSearchCriteriaBean) getIntent().getSerializableExtra("searchBean");

        final TravelDataManager manager = TravelDataManager.getInstance();

        manager.setSort("1");

        manager.setBankStatus(ITravelList.ALL_BANKSTATUS_CODE);

        /**
         * 计算出显示筛选页面的单人预算进度值、出行天数进度值
         */
        int pA = bean.getBudgetPriceProValue();

        int tA = bean.getTravelDaysValue();

        if (pA != 0 || tA != 0) {


            isShowBottomTab(true, 0);

            travelFilterPop.setPriceProgressValue(pA, tA);

        } else {

            isShowBottomTab(false, 0);

        }
        /**
         * 计算最早和最晚出发时间
         */
        String startStr = bean.getStartStr();//最早出发日期

        String endStr = bean.getEndStr();//最晚出发日期

        if (!TextUtils.isEmpty(startStr) || !TextUtils.isEmpty(endStr)) {

            isShowBottomTab(true, 2);

            travelDepartrueTimePop.setQueryDateStartEnd(startStr, endStr);

        } else {

            isShowBottomTab(false, 2);

        }


        travelFilterPop.setOnInfoClick(new TravelScreenPopup.ConfigInfo() {
            @Override
            public void getDataInfo(TravelScreenBean screenBean)
            {

                manager.setMinPrice(screenBean.getSinglBudget());
                manager.setMaxPrice(screenBean.getSinglBudgetMax());

                manager.setMinTravelDay(screenBean.getDaysCount());
                manager.setMaxTravelDay(screenBean.getDaysCountMax());

                manager.setAgentIds(screenBean.getAgentIds());
                manager.setLabelIds(screenBean.getLabelIds());

                bean.setBudgetPriceProValue(screenBean.getBudgetPriceProValue());

                bean.setTravelDaysValue(screenBean.getTravelDaysValue());

                updateFragmentData();

                boolean falg = screenBean.isSelect();

                isShowBottomTab(falg, bottomTabIndex);
            }
        });

        travelDepartrueTimePop.setOnInfoClick(new TravelDepartrueTimePop.ConfigInfo() {
            @Override
            public void getDataInfo(TravelScreenBean screenBean)
            {
                manager.setStartTravelDate(screenBean.getElaryDate());//出发最早时间
                manager.setEndTravelDate(screenBean.getLateDate());

                bean.setStartStr(screenBean.getStartStr());
                bean.setEndStr(screenBean.getEndStr());
                updateFragmentData();
                //
                boolean falg = screenBean.isSelect();
                isShowBottomTab(falg, bottomTabIndex);
            }
        });
        //设置出发城市id
        manager.setDepCityId(bean.getStarCity()); //出发地
        //设置目的城市Id和景点id
        manager.setArrDetailId(bean.getEndCity()); //目的地
        //设置出发城市和景点类型
        manager.setArrType(bean.getEndCityType());  //目的地类型
        manager.setStartTravelDate(bean.getTheEarliest());//出发最早时间
        manager.setEndTravelDate(bean.getTheEndTime()); //出发最晚时间
        manager.setMinPrice(bean.getSinglBudget()); //最小预算
        manager.setMaxPrice(bean.getSinglBudgetMax());  //最大预算
        manager.setMinTravelDay(bean.getDaysCount());   //最小出行天数
        manager.setMaxTravelDay(bean.getDaysCountMax());    //最大出行天数
        presenter = new TravelListPresenter(this);
        //发送旅游列表分类数据请求
        presenter.getTravelScheduleData(bean.getStarCity());

    }

    /**
     * 是否显示底部按钮状态
     *
     * @param isShow
     */
    private void isShowBottomTab(boolean isShow, int tabIndex)
    {
        DefindTabView defindTabView = defindTabViewList.get(tabIndex);
        if (isShow) {//用户选择了出发时间
            defindTabView.setSelectedTabStatus(true);
        } else {//用户从未选择出发时间
            defindTabView.setSelectedTabStatus(false);
        }
    }

    private List<DefindTabView> defindTabViewList = new ArrayList<DefindTabView>();

    //初始化底部
    private void initBottom()
    {

        for (int i = 0; i < 4; i++) {

            DefindTabView defindTabView = new DefindTabView(this, tabOnClick);

            if (i == 0) {//筛选

                int[] drawable0 = new int[]{R.mipmap.filter_default, R.mipmap.filter_selected};

                defindTabView.setViewData(R.string.text_sx, drawable0);
                defindTabView.setSelectedTabStatus(false);
                defindTabView.setPpflag(true);
            } else if (i == 1) {//银行优惠
                int[] drawable1 = new int[]{R.mipmap.bank_disc_default, R.mipmap.bank_disc_selected};

                defindTabView.setViewData(R.string.hotel_bank_privilege, drawable1);

            } else if (i == 2) {//出发时间
                int[] drawable2 = new int[]{R.mipmap.icon_nav_time_default, R.mipmap.icon_nav_time_active};
                defindTabView.setViewData(R.string.train_chufashijian, drawable2);
                defindTabView.setSelectedTabStatus(false);
                defindTabView.setPpflag(true);
            } else if (i == 3) {//排序
                int[] drawable3 = new int[]{R.mipmap.icon_nav_px_default, R.mipmap.icon_nav_px_active};
                defindTabView.setViewData(R.string.travel_tab_price_l_h, drawable3);
                defindTabView.setSelectedTabStatus(true);
                defindTabView.setPpflag(true);
            }
            defindTabView.addTabToLL(llBottomTab, i);
            defindTabViewList.add(defindTabView);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        travelDepartrueTimePop.onStart();
    }

    //初始化试图
    private void initView(List<TravelCategorySBean> list)
    {
        mFragments.clear();
        //分类条目
        int size = list.size();

        mTitles = new String[size];

        for (int i = 0; i < size; i++) {

            TravelCategorySBean bean = list.get(i);

            mFragments.add(TravelLineFragment.getInstance(bean));

            mTitles[i] = bean.getObjName();
        }

        View decorView = getWindow().getDecorView();

        if (vp == null) {
            vp = UiUtils.find(decorView, R.id.vp);
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());

        vp.setAdapter(mAdapter);


        /** indicator三角形 */
        if (tabLayout_8 == null) {

            tabLayout_8 = UiUtils.find(decorView, R.id.tl_8);

        } else {

            //  tabLayout_8.removeAllViews();

        }

        tabLayout_8.setViewPager(vp, mTitles, this, mFragments);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {

                TravelLineFragment fragmet = (TravelLineFragment) mFragments.get(vp.getCurrentItem());
                //主动更新相应fragment数据
                if (fragmet.isIfRefresh()) {

                    fragmet.reStartSendHandler();

                    fragmet.setIfRefresh(false);
                }
                //主动更新相应fragment无法数据
                if (fragmet.isIfUserNoCard()) {

                    fragmet.reStartSendHandler();
                    fragmet.setIfUserNoCard(false);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        ivSearch.setOnClickListener(this);
        iv_menu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.ivSearch://搜索

                Intent intent1 = new Intent(TravelLineListActivity.this, DestinationSearchActivity.class);
                intent1.putExtra("category", filterTwoBean);
                intent1.putExtra("industryId", AppConstant.travel_id);

                startActivityForResult(intent1, 0);
                break;
            case R.id.iv_menu://频道切换器

                moreFunctionPublicTitleView.showChannelPopWindow(panelLeft, 1, uiHandler, TravelLineListActivity.this);

                break;
            default:
                break;

        }
    }

    private Handler uiHandler = new Handler() {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            int what = msg.what;

            if (what == 0) {

                ChildTypeBean selectedBeanT = (ChildTypeBean) msg.obj;

                int pagetypet = AppFactory.channelIdToFragmentIndex(selectedBeanT.id);

                if (pagetypet != -1) {

                    finish();

                } else {
                    ToastUtil.i(TravelLineListActivity.this, "敬请期待");
                }

            }
        }
    };

    /**
     * 设置筛选参数
     *
     * @param tsr
     */
    public static void setTravelScreenBean(TravelScreenBean tsr)
    {
        travelScreenBean = tsr;
    }

    /**
     * 底部四个按钮编号
     */
    private int bottomTabIndex = 0;
    /**
     * 底部tab监听事件
     */
    private DefindTabView.OnClickAction tabOnClick = new DefindTabView.OnClickAction() {
        @Override
        public void onTabClickItem(int index, TextView textView, boolean selectedStatus)
        {

            bottomTabIndex = index;

            if (index == 0) {//筛选

                if (travelFilterPop == null) {
                    travelFilterPop = new TravelScreenPopup(TravelLineListActivity.this, travelScreenBean);
                }
                travelFilterPop.showAtLocation(llBottomTab, Gravity.TOP, 0, 0);

            } else if (index == 1) {//银行优惠

                if (selectedStatus) {//未设定银行优惠

                    TravelDataManager.getInstance().setBankStatus(ITravelList.ALL_BANKSTATUS_CODE);

                    updateFragmentData();

                } else {//已设定银行优惠

                    //检查用户是否登录
                    boolean flag = UserManager.getInstance().isLogin();

                    if (flag) {//已登录

                        //检查用户的银行卡数量
                        int creditNum = UserManager.getInstance().getUserBean().getCreditCardNum();

                        if (creditNum > 0) {//用户有绑定的银行卡

                            TravelDataManager.getInstance().setBankStatus(ITravelList.MY_BANKSTATUS_CODE);

                            updateFragmentData();

                        } else {//用户未绑定银行卡

                            updateNoCardFlagAllFragment();

                        }

                    } else {//未登录

                        TravelDataManager.getInstance().setBankStatus(ITravelList.ALL_BANKSTATUS_CODE);

                        //弹出登录提示框
                        new GoLoginDailog(TravelLineListActivity.this).show();

                    }

                }

            } else if (index == 2) {//出发时间

                travelDepartrueTimePop.showAtLocation(llBottomTab, Gravity.TOP, 0, 0);

            } else if (index == 3) {//排序


                if (sortTopListPopIsShow) {

                    if (sortTopListPop != null) {

                        sortTopListPop.dismiss();
                    }

                } else {

                    if (sortTv == null) {

                        sortTv = textView;
                    }

                    if (sortTopListPop == null) {

                        List<IFilterPopItem> list = new ArrayList<>();
                        list.add(new SortType("1", getString(R.string.travel_tab_price_l_h), 0));
                        list.add(new SortType("2", getString(R.string.travel_tab_price_h_l), 1));
                        list.add(new SortType("4", getString(R.string.travel_tab_sales_l_h), 2));
                        list.add(new SortType("3", getString(R.string.travel_tab_sales_h_l), 3));

                        AppBaseDataBean bean = GlobalApp.getInstance().getAppBaseDataBean();

                        View menuView = LayoutInflater.from(TravelLineListActivity.this).inflate(R.layout.popup_ticket_filter, null);

                        int h = (int) getResources().getDimension(R.dimen.tab_bottom_h);
                        ;

                        sortTopListPop = new SortTopListPop(menuView, ViewGroup.LayoutParams.MATCH_PARENT, bean.getScreenHeight() - h - bean.getStatusBarHeight());

                        sortTopListPop.setOnItemSelectLis(ppSelect);

                        sortTopListPop.setSelectedItem(0);

                        sortTopListPop.addDataList(list);

                        sortTopListPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss()
                            {

                                if (sortTopListPopIsShow) {
                                    sortTopListPopIsShow = false;
                                }

                            }
                        });

                    } else {

                        sortTopListPop.setSelectedItem(sortCurrentIndex);

                    }

                    sortTopListPop.showPositionTopByOnclickView(llBottomTab);

                    sortTopListPopIsShow = sortTopListPop.isShowing();

                }

            }

        }
    };

    private boolean sortTopListPopIsShow;

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == ITravelList.QUERY_TRAVEL_BASEINFO_URL) {

            List<TravelCategorySBean> list = JSONArray.parseArray(o + "", TravelCategorySBean.class);

            initView(list);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }


    /**
     * 分类碎片适配器
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public int getCount()
        {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragments.get(position);
        }

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        TravelDataManager.getInstance().clearTravleListRequestBean();

        moreFunctionPublicTitleView = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            filterTwoBean = (FilterTwoBean) data.getSerializableExtra("backData");
            FilterBean filterBean = filterTwoBean.getSelectedFilterEntity();

            TravelDataManager manager = TravelDataManager.getInstance();
            manager.setArrType(filterBean.getType());
            manager.setArrDetailId(filterBean.getObjId() + "");
            //发送刷新信息请求
            //发送旅游列表分类数据请求
            presenter.getTravelScheduleData(filterBean.getObjId() + "");
        }else if (requestCode == 1000 && resultCode == Activity.RESULT_OK){//当前的fragment调用activit回到刷新

            //通知頁面更新
            updateFragmentData();
        }
    }


    private OnItemSelectLis ppSelect = new OnItemSelectLis() {
        @Override
        public void onItemSelect(IFilterPopItem item)
        {
            SortType type = (SortType) item;

            sortTv.setText(type.getName());

            TravelDataManager.getInstance().setSort(type.getCode());
            //通知頁面更新
            updateFragmentData();

            sortCurrentIndex = type.getSortIndex();
        }
    };

    /**
     * 更新fragment数据
     */
    private void updateFragmentData()
    {
        for (Fragment f : mFragments) {
            TravelLineFragment fragmet = (TravelLineFragment) f;

            fragmet.setIfRefresh(true);
        }

        TravelLineFragment fragmet = (TravelLineFragment) mFragments.get(vp.getCurrentItem());

        fragmet.reStartSendHandler();

        fragmet.setIfRefresh(false);


    }

    /**
     * 更新所有fragment的无卡标记
     */
    private void updateNoCardFlagAllFragment()
    {

        for (Fragment f : mFragments) {
            TravelLineFragment fragmet = (TravelLineFragment) f;

            fragmet.setIfUserNoCard(true);
        }

        TravelLineFragment fragmet = (TravelLineFragment) mFragments.get(vp.getCurrentItem());

        fragmet.reStartSendHandler();

        fragmet.setIfUserNoCard(false);

    }

    //重载物理返回按键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (travelDepartrueTimePop != null && travelFilterPop != null) {

                if (travelDepartrueTimePop.isShowing()) {

                    travelDepartrueTimePop.dismiss();
                    return false;

                } else if (travelFilterPop.isShowing()) {

                    travelFilterPop.dismiss();
                    return false;
                } else {

                    if (bean != null) {
                        Intent intent = new Intent();
                        intent.putExtra("dataBean", bean);
                        setResult(1001, intent);
                    }
                    return super.onKeyDown(keyCode, event);
                }


            } else {
                if (bean != null) {
                    Intent intent = new Intent();
                    intent.putExtra("dataBean", bean);
                    setResult(1001, intent);
                }

                return super.onKeyDown(keyCode, event);

            }

        } else {

            if (bean != null) {
                Intent intent = new Intent();
                intent.putExtra("dataBean", bean);
                setResult(1001, intent);
            }
            return super.onKeyDown(keyCode, event);
        }

    }



}
