package com.yzb.card.king.ui.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HbDialogParam;
import com.yzb.card.king.bean.ticket.GoodActivityBean;
import com.yzb.card.king.bean.ticket.ShopGoodsActivityBean;
import com.yzb.card.king.bean.travel.DateBean;
import com.yzb.card.king.bean.travel.FromPlaceBean;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.bean.travel.TravelProduDetailBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BasePresenter;
import com.yzb.card.king.ui.app.interfaces.DiscountListener;
import com.yzb.card.king.ui.appwidget.SmoothScrollView;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.travel.activity.view.TravelDetailCouponView;
import com.yzb.card.king.ui.travel.activity.view.TravelDetailEvaluateView;
import com.yzb.card.king.ui.travel.activity.view.TravelDetailHeaderTabView;
import com.yzb.card.king.ui.travel.activity.view.TravelDetailHeaderView;
import com.yzb.card.king.ui.travel.activity.view.TravelDetailJourneyView;
import com.yzb.card.king.ui.travel.activity.view.TravelDetailLinesView;
import com.yzb.card.king.ui.travel.activity.view.TravelDetailNoticeView;
import com.yzb.card.king.ui.appwidget.popup.CalendarPop;
import com.yzb.card.king.ui.appwidget.popup.TravelChoseDesPopup;
import com.yzb.card.king.ui.appwidget.popup.TravelDetailTripPop;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.discount.bean.PjBean;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.discount.presenter.CollectionPresenter;
import com.yzb.card.king.ui.discount.presenter.impl.CollectionPresenterImpl;
import com.yzb.card.king.ui.luxury.presenter.impl.VoteListPresenter;
import com.yzb.card.king.ui.luxury.view.VoteListView;
import com.yzb.card.king.ui.my.presenter.CommandPresenter;
import com.yzb.card.king.ui.my.view.CommandView;
import com.yzb.card.king.ui.other.activity.PjActivity;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.ui.ticket.fragment.ShopCouponDialogFragment;
import com.yzb.card.king.ui.ticket.fragment.ShopBounsDialogFragment;
import com.yzb.card.king.ui.ticket.presenter.DiscountPresenter;
import com.yzb.card.king.ui.ticket.view.DiscountView;
import com.yzb.card.king.ui.travel.presenter.impl.TravelFromPlacePresenter;
import com.yzb.card.king.ui.travel.presenter.impl.TravelGetDatesPImpl;
import com.yzb.card.king.ui.travel.presenter.impl.TravelLinePresenter;
import com.yzb.card.king.ui.travel.presenter.impl.TravelProductDetPresenter;
import com.yzb.card.king.ui.travel.view.ITravelDetailDataProvider;
import com.yzb.card.king.ui.travel.view.TravelFromPlaceView;
import com.yzb.card.king.ui.travel.view.TravelGetDatesView;
import com.yzb.card.king.ui.travel.view.TravelLineView;
import com.yzb.card.king.ui.travel.view.TravelProductDetView;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 旅游产品详情；
 *
 * @author gengqiyun
 * @date 2016.11.21
 */
public class TravelProductDetailActivity extends BaseActivity implements View.OnClickListener, TravelProductDetView,
        SwipeRefreshLayout.OnRefreshListener, SmoothScrollView.OnScrollistener, ITravelDetailDataProvider, VoteListView,
        TravelLineView, TravelFromPlaceView, TravelGetDatesView, DiscountView, BaseViewLayerInterface, CommandView
{
    private TravelDetailHeaderView headerView;

    private TravelDetailHeaderTabView tabView;

    private TravelDetailHeaderTabView topTabView; //假的悬浮view；

    private TravelDetailLinesView topDetailLinesView;  //假的悬浮行程介绍 view；

    private SmoothScrollView scrollView;

    private View panelTitlebar;

    private int titlebarHeight; //标题栏height；

    private TravelDetailCouponView couponView;

    private TravelDetailEvaluateView evaluateView;

    private TravelDetailJourneyView journeyView;

    private TravelDetailNoticeView noticeView;

    private ImageView ivCollect; //收藏图片；

    private TextView tvModeSwitch;

    private View panelJourneySummary; //行程概要；

    private TravelProductDetPresenter detailPresenter;

    private VoteListPresenter voteListPresenter;

    private SwipeRefreshLayout swipeRefresh;

    private TravelProduDetailBean detailBean;

    private String productId; //产品id；

    private List<GoodActivityBean> couponBankBeans; //优惠列表

    private TravelLinePresenter travelLinePresenter; //旅游线路；

    private List<TravelLineBean> lineBeans; //旅游线路；

    private TravelFromPlacePresenter fromPlacePresenter; //出发地列表；

    private List<FromPlaceBean> fromPlaceBeanList; //出发地列表；

    private List<PjBean> pjBeanList; //评价列表；

    private List<DateBean> dateList; //最新的日期列表；

    private BasePresenter getDatesPresenter;

    private FromPlaceBean selecFromPlaceBean; //选择的出发地；

    private DiscountPresenter discountPresenter;//银行优惠；

    private CollectionPresenter collectionPresenter; //收藏；

    private View panelCollect; //收藏块，控制重复点击的问题；

    private CommandPresenter commandPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_produ_detail);
        detailPresenter = new TravelProductDetPresenter(this);
        voteListPresenter = new VoteListPresenter(this);
        discountPresenter = new DiscountPresenter(this);
        travelLinePresenter = new TravelLinePresenter(this);
        fromPlacePresenter = new TravelFromPlacePresenter(this);
        getDatesPresenter = new TravelGetDatesPImpl(this);
        collectionPresenter = new CollectionPresenterImpl(this);
        commandPresenter = new CommandPresenter(this);

        recvIntentData();
        assignViews();
        initData();
    }

    private void recvIntentData()
    {
        productId = getIntent().getStringExtra("id");
    }

    private void assignViews()
    {
        findViewById(R.id.panelBack).setOnClickListener(this);
        panelTitlebar = findViewById(R.id.panelTitlebar);
        panelTitlebar.setAlpha(0);

        headerView = (TravelDetailHeaderView) findViewById(R.id.headerView);
        headerView.setDataProvider(this);
        headerView.setClickListener(this);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        tabView = (TravelDetailHeaderTabView) findViewById(R.id.headerTabView);
        tabView.setDataProvider(this);
        tabView.setTabListener(selectListener);
        topTabView = (TravelDetailHeaderTabView) findViewById(R.id.topTabView);
        topTabView.setDataProvider(this);
        topTabView.setTabListener(selectListener);

        //优惠部分；
        couponView = (TravelDetailCouponView) findViewById(R.id.couponView);
        couponView.setDataProvider(this);
        couponView.setClickListener(this);
        evaluateView = (TravelDetailEvaluateView) findViewById(R.id.evaluateView);
        evaluateView.setDataProvider(this);
        evaluateView.setClickListener(this);

        topDetailLinesView = (TravelDetailLinesView) findViewById(R.id.topDetailLinesView);
        topDetailLinesView.setDataProvider(this);
        topDetailLinesView.setNeedRefresh(true);

        journeyView = (TravelDetailJourneyView) findViewById(R.id.journeyView);
        journeyView.setDataProvider(this);

        noticeView = (TravelDetailNoticeView) findViewById(R.id.noticeView);
        noticeView.setDataProvider(this);

        scrollView = (SmoothScrollView) findViewById(R.id.scrollView);
        scrollView.scrollTo(0, 0);
        scrollView.setOnScrollistener(this);

        tvModeSwitch = (TextView) findViewById(R.id.tvModeSwitch);
        tvModeSwitch.setVisibility(View.GONE);
        tvModeSwitch.setOnClickListener(this);
        panelJourneySummary = findViewById(R.id.panelJourneySummary);
        panelJourneySummary.setVisibility(View.GONE);
        panelJourneySummary.setOnClickListener(this);

        //收藏；
        panelCollect = findViewById(R.id.panelCollect);
        panelCollect.setOnClickListener(this);
        ivCollect = (ImageView) findViewById(R.id.ivCollect);
        findViewById(R.id.tvCommit).setOnClickListener(this);
    }

    @Override
    public void onProductDetSucess(boolean event_tag, TravelProduDetailBean data)
    {
        swipeRefresh.setRefreshing(false);
        detailBean = data;
        fillDetailViewData();
    }

    /**
     * 刷新详情view的数据；
     */
    private void fillDetailViewData()
    {
        headerView.notifyDataChanged();
        tabView.notifyDataChanged();
        topTabView.notifyDataChanged();
        couponView.notifyDataChanged();
        //evaluateView.notifyEvaluateChanged();
        journeyView.notifyDataChanged();
        // journeyView.notifyTravelLineChanged();

        // topDetailLinesView.notifyTravelLineChanged();
        noticeView.notifyDataChanged();
        LogUtil.i("收藏状态==" + detailBean.isCollectionStatus());

        if (detailBean != null)
        {
            updateCollectIvState(detailBean.isCollectionStatus());

            //有银行优惠，请求银行优惠；
            if ("1".equals(detailBean.getBankStatus()))
            {
                LogUtil.i("有银行优惠--开始请求");
                loadCouponBanks();
            }
        }
    }

    @Override
    public void onProductDetFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);
        toastCustom(failMsg);
    }

    @Override
    public void onRefresh()
    {
        refreshData();
    }

    /**
     * 下拉刷新数据；
     */
    public void initData()
    {
        loadDataHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
//                swipeRefresh.setRefreshing(true);
                loadTravelLine();
                loadLineDateList(null);
            }
        }, 100);
    }

    /**
     * 下拉刷新数据；
     */
    public void refreshData()
    {
        loadDataHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                loadProductDetail();
                loadEvaluateList();
                loadTravelLine();
                loadLineDateList(null);
            }
        }, 100);
    }

    /**
     * 加载线路的日期列表；
     *
     * @param date 日期；
     */
    private void loadLineDateList(String date)
    {
        if (topDetailLinesView.getSelectLine() == null)
        {
            LogUtil.i("选中的线路为空");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("lineId", topDetailLinesView.getSelectLine().getLineId());
        params.put("depDate", date); // 用户选择的日期；
        getDatesPresenter.loadData(true, params);
    }

    /**
     * 加载优惠银行；
     */
    private void loadCouponBanks()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("platformType", DiscountListener.platform_type_bank + "");
        params.put("activityItem", DiscountListener.type_all + "");
        params.put("shopIds", detailBean.getAgentId());//商家id(多个使用英文逗号分割)
        params.put("goodsIds", detailBean.getProductId());//商品id(多个使用英文逗号分割)
        params.put("industryIds", AppConstant.travel_id);//行业分类(多个使用英文逗号分割)
        params.put("goodscodes", AppConstant.discount_code_travel);//商品code码
        discountPresenter.loadData(params);
    }

    /**
     * 执行收藏；
     */
    public void exeCollect()
    {
        if (detailBean != null)
        {
            Map<String, Object> params = new HashMap<>();
            params.put("category", "1"); //类型；
            params.put("type", AppConstant.collect_product_type);
            params.put("detailsId", productId); //产品id；
            params.put("status", detailBean.isCollectionStatus() ? "0" : "1");
            collectionPresenter.loadData(params);
        } else
        {
            toastCustom("详情加载失败，无法收藏");
        }
    }

    /**
     * 查询旅游线路；
     */
    private void loadTravelLine()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("productId", productId);
        travelLinePresenter.loadData(true, param);
    }

    @Override
    public List<PjBean> getPjBeanList()
    {
        return pjBeanList;
    }

    @Override
    public List<DateBean> getDateBeanList()
    {
        return dateList;
    }

    @Override
    public void selectDeparture(String lineId)
    {
        //此处判断出发地是否存在，存在直接显示；目前每次都请求；
        TravelLineBean lineBean = topDetailLinesView.getSelectLine();
        //数据为空或为不同线路；
        if (this.fromPlaceBeanList == null || (lineBean != null && !isEmpty(lineId) && !lineId.equals(lineBean.getLineId())))
        {
            Map<String, Object> param = new HashMap<>();
            param.put("lineId", lineId);
            fromPlacePresenter.loadData(true, param);
        } else
        {
            showFromPlaceDialog();
        }
    }

    /**
     * 显示出发地对话框；
     */
    private void showFromPlaceDialog()
    {
        TravelChoseDesPopup desPopup = new TravelChoseDesPopup(this, fromPlaceBeanList);
        desPopup.setSelectBean(selecFromPlaceBean);
        //pop点击回调
        desPopup.setSetOnItemClick(new TravelChoseDesPopup.OnItemClick()
        {
            @Override
            public void setOnClick(int pos)
            {
                selecFromPlaceBean = fromPlaceBeanList.get(pos);
                journeyView.setDeparture(selecFromPlaceBean.getCityName());
            }
        });
        desPopup.openPop(getWindow().getDecorView());
    }

    private CalendarPop calendarPop;

    @Override
    public void selectMoreDate()
    {
        if (detailBean != null)
        {
            TravelLineBean lineBean = topDetailLinesView.getSelectLine();
            calendarPop = new CalendarPop();
            Map<String, String> args = new HashMap<>();
            args.put("productId", detailBean.getProductId());
            args.put("lineId", lineBean == null ? "" : lineBean.getLineId());
            args.put("depDate", DateUtil.date2String(journeyView.getSelectDate(), DateUtil.DATE_FORMAT_DATE));
            calendarPop.setTravelParams(args);

            Date selectDate = journeyView.getSelectDate();
            if (selectDate != null)
            {
                calendarPop.setStartDate(selectDate);
            }
            calendarPop.setListener(new OnItemClickListener<CalendarDay>()
            {
                @Override
                public void onItemClick(CalendarDay data)
                {
                    loadLineDateList(DateUtil.date2String(data.getDay(), DateUtil.DATE_FORMAT_DATE));
                    calendarPop.dismiss();
                }
            });
            calendarPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void onBackPressed()
    {
        if (calendarPop != null && calendarPop.isShowing())
        {
            calendarPop.dismiss();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void switchLines(int linePos, boolean needRefresh)
    {
        TravelLineBean lineBean = lineBeans.get(linePos);
        //锦江模式时，隐藏悬浮view；
        tvModeSwitch.setVisibility(!isEmpty(lineBean.getLineUrl()) ? View.GONE : View.VISIBLE);
        panelJourneySummary.setVisibility(!isEmpty(lineBean.getLineUrl()) ? View.GONE : View.VISIBLE);

        topDetailLinesView.selectItem(lineBean);
        journeyView.refreshData(lineBean);

        //控制线路列表2次刷新的问题；
        if (needRefresh)
        {
            loadLineDateList(null);
        }
    }

    /**
     * 查询评价列表；
     */
    private void loadEvaluateList()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("detailId", productId);
        param.put("type", "2");
        param.put("pageStart", "0");
        param.put("pageSize", "2");
        param.put("voteType", "0");
        param.put("serviceName", CardConstant.CARD_APP_VOTELIST);
        voteListPresenter.loadData(true, param);
    }

    /**
     * 获取旅游详情；
     */
    private void loadProductDetail()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("productId", productId);
        if (getUserBean() != null)
        {
            param.put("sessionId", AppConstant.sessionId);
        }
        detailPresenter.loadData(true, param);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panelBack:
                finish();
                break;
            case R.id.panel_share: //分享；
                generateCommand();
                break;
            case R.id.panelShopDetail://商家详情；
                toastCustom("敬请期待");
                break;
            case R.id.panelGetCoupon://领取优惠券；
                showCouponDialog();
                break;
            case R.id.panelGetRedPacket://领取红包；
                showPacketDialog();
                break;
            case R.id.tvLookMore://查看更多评论；
                if (detailBean != null)
                {
                    Intent pjIntent = new Intent(this, PjActivity.class);
                    pjIntent.putExtra("detailId", detailBean.getProductId());
                    pjIntent.putExtra("type", "2");
                    startActivity(pjIntent);
                }
                break;
            case R.id.tvModeSwitch://简单行程模式切换；
                journeyView.switchMode();
                tvModeSwitch.setText(journeyView.getCurMode() ? "简单\n行程" : "图文\n行程");
                break;
            case R.id.panelJourneySummary://行程概要；
                if (detailBean != null && topDetailLinesView.getSelectLine() != null)
                {
                    TravelDetailTripPop tripPop = new TravelDetailTripPop(this, topDetailLinesView.getSelectLine().getLineId(), 1, "");
                    tripPop.openPop();
                }
                break;
            case R.id.panelCollect://收藏；
                if (!isLogin())
                {
                    readyGo(this, LoginActivity.class);
                    return;
                }
                panelCollect.setClickable(false);
                exeCollect();
                break;
            case R.id.tvCommit://开始预定；
                if (!isLogin())
                {
                    readyGo(this, LoginActivity.class);
                    return;
                }
                startOrder();
                break;
        }
    }

    /**
     * 生成口令；
     */
    private void generateCommand()
    {
        if (detailBean == null)
        {
            return;
        }
        showPDialog(R.string.loading);
        Map<String, Object> args = new HashMap<>();
        args.put("codeType", AppConstant.command_type_shop);
        args.put("code", productId); //编码
        args.put("industryId", AppConstant.travel_id);
        args.put("activityData", JSON.toJSONString(getActivityMap()));
        commandPresenter.loadData(args);
    }

    private Map<String, Object> getActivityMap()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("productId", productId);
        return param;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadProductDetail();
        loadEvaluateList();
    }

    /**
     * 开始预定；
     */
    private void startOrder()
    {
        if (detailBean == null)
        {
            toastPageError();
            return;
        }
        if (journeyView.getSelectDate() == null)
        {
            toastCustom("请选择出发日期");
            return;
        }

        Intent commitIntent = new Intent(this, TravelSignUpActivity.class);
        HashMap<String, Object> map = new HashMap();
        map.put("agentId", detailBean.getAgentId());//商家id；
        map.put("productId", detailBean.getProductId());//产品id；

        Date startDate = journeyView.getSelectDate();
        if (startDate != null)
        {
            map.put("startDate", DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE));//开始日期；

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_MONTH, detailBean.getTravelDays() - 1);
            map.put("returnDate", DateUtil.date2String(calendar.getTime(), DateUtil.DATE_FORMAT_DATE));//返回日期；
        }
        map.put("dateBean", journeyView.getSelectDateBean());
        if (topDetailLinesView.getSelectLine() != null)
        {
            map.put("lineId", topDetailLinesView.getSelectLine().getLineId());//选择的线路id；
        }
        map.put("storeName", detailBean.getAgentName());//门店名称；
        commitIntent.putExtra(TravelSignUpActivity.ARG_MAP, map);
        readyGo(commitIntent);

    }

    /**
     * 红包dialog；
     */
    private void showPacketDialog()
    {
        if (detailBean != null)
        {
            HbDialogParam param = new HbDialogParam(detailBean.getAgentId(), detailBean.getProductId()
                    , DiscountListener.type_all, DiscountListener.type_hb, AppConstant.discount_code_travel);
            ShopBounsDialogFragment.getInstance("", "").setData(param).show(getSupportFragmentManager(), "");
        } else
        {
            toastPageError();
        }
    }

    public void toastPageError()
    {
        toastCustom("页面加载失败了，刷新试试");
    }

    /**
     * 优惠券dialog；
     */
    private void showCouponDialog()
    {
        if (detailBean != null)
        {
            HbDialogParam param = new HbDialogParam(detailBean.getAgentId(), detailBean.getProductId()
                    , DiscountListener.platform_type_platform_shop, DiscountListener.type_yhq, AppConstant.discount_code_travel);
            ShopCouponDialogFragment.getInstance("", "").setData(param).show(getSupportFragmentManager(), "");
        } else
        {
            toastPageError();
        }
    }

    /**
     * Tab点击选中的监听器；
     * 说明：Tab不可使用TabLayout自带的Tab选中监听器；会造成滚动和选中的无限循环；
     */
    private TravelDetailHeaderTabView.IItemSelect selectListener = new TravelDetailHeaderTabView.IItemSelect()
    {
        @Override
        public void itemSelect(int position)
        {
            LogUtil.i("选中的position=" + position);
            tabView.selectTab(position);
            topTabView.selectTab(position);
            scrollToTabItem(position);
        }
    };

    /**
     * 根据滚动高度，动态设置tabs的选中状态；
     *
     * @param scrollHeight 当前的滚动高度；
     */
    private void switchTabs(int scrollHeight)
    {
        int baseHeight = headerView.getCurHeight() - titlebarHeight;
        int couponHeight = couponView.getCurHeight();
        int evaluateHeight = evaluateView.getCurHeight();
        int journeyHeight = journeyView.getCurHeight();
        int noticeHeight = noticeView.getCurHeight();
        int index = 0;
        if (scrollHeight < baseHeight + couponHeight)
        {
            index = 0;
        } else if (scrollHeight < baseHeight + couponHeight + evaluateHeight)
        {
            index = 1;
        } else if (scrollHeight < baseHeight + couponHeight + evaluateHeight + journeyHeight)
        {
            index = 2;
        } else if (scrollHeight < baseHeight + couponHeight + evaluateHeight + journeyHeight + noticeHeight)
        {
            index = 3;
        }
        topTabView.selectTab(index);
        tabView.selectTab(index);
    }

    /**
     * 滚动到特定的item;
     *
     * @param position
     */
    private void scrollToTabItem(int position)
    {
        int baseHeight = headerView.getCurHeight() - titlebarHeight;
        int couponHeight = couponView.getCurHeight();
        int evaluateHeight = evaluateView.getCurHeight();
        int journeyHeight = journeyView.getCurHeight();

        int targetHeight = 0; //目的高度；
        switch (position)
        {
            case 0://优惠详情；
                targetHeight = baseHeight;
                break;
            case 1: //评价；
                targetHeight = baseHeight + couponHeight;
                break;
            case 2://5日行程；
                targetHeight = baseHeight + couponHeight + evaluateHeight;
                break;
            case 3://须知；
                targetHeight = baseHeight + couponHeight + evaluateHeight + journeyHeight;
                break;
        }
        //高度不等才滚动；
        if (scrollView.getScrollY() != targetHeight)
        {
            scrollView.scrollTo(0, targetHeight);
        }
    }

    @Override
    public void onGetTravelLineSucess(boolean event_tag, List<TravelLineBean> lineBeans)
    {
        LogUtil.i("旅游线路加载成功" + lineBeans);
        this.lineBeans = lineBeans;
        topDetailLinesView.notifyTravelLineChanged();
        journeyView.notifyChangeTravelLine();
    }

    public List<TravelLineBean> getTravelLineBeans()
    {
        return this.lineBeans;
    }

    @Override
    public void onGetTravelLineFail(String failMsg)
    {
        LogUtil.i("旅游线路error=" + failMsg);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        titlebarHeight = panelTitlebar.getMeasuredHeight();
    }

    /**
     * 更新收藏图标状态；
     *
     * @param hasCollect true:已收藏；
     */
    public void updateCollectIvState(boolean hasCollect)
    {
        ivCollect.setImageResource(hasCollect ? R.mipmap.icon_star_dark_red_select :
                R.mipmap.icon_star_dark_red_normal);
    }

    /**
     * 设置tab的可见性；
     *
     * @param scrollY 垂直滚动的距离；
     */
    public void showOrHideTopView(int scrollY)
    {
        if (topTabView != null)
        {
            boolean relEqual = scrollY + titlebarHeight >= headerView.getCurHeight();
            topTabView.setVisibility(relEqual ? View.VISIBLE : View.GONE);
        }
        //线路为1条不显示；
        if (lineBeans == null || lineBeans.size() < 2)
        {
            return;
        }
        //判断高度；
        boolean relEqual = scrollY + titlebarHeight >= headerView.getCurHeight() +
                +couponView.getCurHeight()
                + evaluateView.getCurHeight()
                + journeyView.getTitleMeasureHeight();
        topDetailLinesView.setVisibility(relEqual ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onScroll(int scrollY)
    {
        showOrHideTopView(scrollY);
//        // 处理标题栏颜色渐变
        panelTitlebar.setAlpha(scrollY > 25 ? 1 : 0);
        switchTabs(scrollY);
    }

    @Override
    public TravelProduDetailBean getProduDetailBean()
    {
        return detailBean;
    }

    @Override
    public List<GoodActivityBean> getCouponBankBean()
    {
        return couponBankBeans;
    }

    @Override
    public void onLoadVoteListSucess(boolean event_tag, Object data)
    {
        if (data != null)
        {
            pjBeanList = (List<PjBean>) data;
            evaluateView.notifyEvaluateChanged();
        }
    }

    @Override
    public void onLoadVoteListFail(String failMsg)
    {
        LogUtil.i("评价列表failMsg=" + failMsg);
    }

    @Override
    public void onGetFromPlaceSucess(boolean event_tag, List<FromPlaceBean> fromPlaceBeanList)
    {
        this.fromPlaceBeanList = fromPlaceBeanList;
        showFromPlaceDialog();
    }

    @Override
    public void onGetFromPlaceFail(String failMsg)
    {
        LogUtil.i("线路列表failMsg=" + failMsg);
        toastCustom(failMsg);
    }

    @Override
    public void onGetDatesSucess(boolean event_tag, List<DateBean> dateList)
    {
        this.dateList = dateList;
        journeyView.notifyDateListChanged();
    }

    @Override
    public void onGetDatesFail(String failMsg)
    {
        this.dateList = null;
        journeyView.notifyDateListChanged();
        LogUtil.i("日期列表failMsg==" + failMsg);
    }

    @Override
    public void onGetDiscountSucess(String req_flag, Object data)
    {
        if (data != null)
        {
            LogUtil.i("优惠银行列表结果--" + JSON.toJSONString(data));
            List<ShopGoodsActivityBean> gabList = (List<ShopGoodsActivityBean>) data;
            if (gabList != null && gabList.size() > 0)
            {
                this.couponBankBeans = gabList.get(0).getGoodActivityBeans();
                LogUtil.i("优惠银行列表--" + couponBankBeans);
                couponView.notifyCouponBanksChanged();
            }
        }
    }

    @Override
    public void onGetDiscountFail(String failMsg)
    {
        LogUtil.i("优惠银行failMsg==" + failMsg);
    }



    @Override
    public void onGetCommandSuc(String commandAndUrl)
    {
        closePDialog();
        if (detailBean != null)
        {
            String firstImageUrl = null;
            if (!isEmpty(detailBean.getProductImageUrl()))
            {
                firstImageUrl = detailBean.getProductImageUrl().split(",")[0];
            }

            ShareDialogFragment.getInstance("", "")
                    .setTitle(detailBean != null ? detailBean.getProductName() : "旅游")
                    .setImage(firstImageUrl)
                    .setUrl(CommonUtil.getGiftcardShareUrl(commandAndUrl))
                    .setContent("快来点开看看吧~")
                    .show(getSupportFragmentManager(), "");
        }
    }

    @Override
    public void onGetCommandFail(String failMsg)
    {
        closePDialog();
        // toastCustom(failMsg);
    }

    @Override
    public void callSuccessViewLogic(Object data, int type)
    {
        panelCollect.setClickable(true);
        String msg;
        if (detailBean.isCollectionStatus())
        {
            msg = getString(R.string.qxcoll);
        } else
        {
            msg = getString(R.string.collsucc);
        }
        toastCustom(msg);

        detailBean.setCollectionStatus(!detailBean.isCollectionStatus());
        updateCollectIvState(detailBean.isCollectionStatus());
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        panelCollect.setClickable(true);
        toastCustom(o+"");
    }
}
