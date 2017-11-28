package com.yzb.card.king.ui.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.hotel.persenter.impl.UpdateOrderStatusPresenter;
import com.yzb.card.king.ui.integral.adapter.OrderManageAdapter;
import com.yzb.card.king.ui.order.adapter.OrderManAgeGridViewAdapter;
import com.yzb.card.king.ui.order.presenter.OrderListsPresenter;
import com.yzb.card.king.ui.order.view.OrderManagerListsView;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sushuiku
 * 订单管理页
 */
public class OrderManageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
        , OrderManagerListsView, View.OnClickListener, BaseViewLayerInterface {

    public static final String ORDERMANAGER_ACTION = "com.yzb.card.king.action.ordermanager";
    private OrderManageAdapter mAdapter;
    private HeadFootRecyclerView mRecyclerView;
    private SwipeRefreshLayout sRL;
    private OrderListsPresenter orderListsPresenter;
    private RelativeLayout industryLayout, orderStatusLayout;
    private String industryText[];
    private String orderStatusText[] = new String[]{"全部", "已取消", "待支付", "已支付", "已完成", "已退款"};
    private PopupWindow popupWindow;
    private ImageView industryImg, orderStatusImg;
    private LinearLayout industry_orderstatus_layout;
    private TextView industry_text, order_status_text;
    /**
     * 行业id
     */
    private int orderType = OrderBean.ORDER_TYPE_ALL;
    private int startPage = 0; //开始查询条数；
    private String orderStatus = null;
    private UpdateOrderStatusPresenter orderStatusPresenter;

    /**
     * 频道数据集合
     */
    private List<ChildTypeBean> childTypeBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_manage);

        orderListsPresenter = new OrderListsPresenter(this);

        orderStatusPresenter = new UpdateOrderStatusPresenter(this);

        initData();

        initView();

        registerRefreshReceiver();

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        //自动刷新
        selfRefresh();
    }

    private void initData()
    {
        String childTypeJson = SharePrefUtil.getValueFromSp(GlobalApp.getInstance().getPublicActivity(), AppConstant.sp_childtypelist_home, "[]");
        if (childTypeJson == null && TextUtils.isEmpty(childTypeJson)) {
            return;
        }
        childTypeBeans = new ArrayList<>();

        ChildTypeBean allChildTypeBean = new ChildTypeBean();
        allChildTypeBean.typeName = getString(R.string.travel_all_function);
        allChildTypeBean.id = OrderBean.ORDER_TYPE_ALL + "";
        childTypeBeans.add(allChildTypeBean);

//
//        ChildTypeBean allChildTypeBean1 = new ChildTypeBean();
//        allChildTypeBean1.typeName = "火车票";
//        allChildTypeBean1.id = OrderBean.ORDER_TYPE_TRAIN+"";
//        childTypeBeans.add(allChildTypeBean1);


        ChildTypeBean allChildTypeBean2 = new ChildTypeBean();
        allChildTypeBean2.typeName = "机票";
        allChildTypeBean2.id = OrderBean.ORDER_TYPE_AIRCRAFT + "";
        childTypeBeans.add(allChildTypeBean2);

//
//        ChildTypeBean allChildTypeBean3 = new ChildTypeBean();
//        allChildTypeBean3.typeName = "船票";
//        allChildTypeBean3.id = OrderBean.ORDER_TYPE_FERRY+"";
//        childTypeBeans.add(allChildTypeBean3);
//
//        ChildTypeBean allChildTypeBean4 = new ChildTypeBean();
//        allChildTypeBean4.typeName = "汽车票";
//        allChildTypeBean4.id = OrderBean.ORDER_TYPE_CAR+"";
//        childTypeBeans.add(allChildTypeBean4);

//        ChildTypeBean allChildTypeBean5 = new ChildTypeBean();
//        allChildTypeBean5.typeName = "叫车";
//        allChildTypeBean5.id = OrderBean.ORDER_TYPE_TAXI+"";
//        childTypeBeans.add(allChildTypeBean5);

        ChildTypeBean allChildTypeBean6 = new ChildTypeBean();
        allChildTypeBean6.typeName = "旅游";
        allChildTypeBean6.id = OrderBean.ORDER_TYPE_TOUR + "";
        childTypeBeans.add(allChildTypeBean6);

        ChildTypeBean allChildTypeBean7 = new ChildTypeBean();
        allChildTypeBean7.typeName = "酒店";
        allChildTypeBean7.id = OrderBean.ORDER_TYPE_HOTELS + "";
        childTypeBeans.add(allChildTypeBean7);

        ChildTypeBean allChildTypeBean8 = new ChildTypeBean();
        allChildTypeBean8.typeName = "礼品卡";
        allChildTypeBean8.id = OrderBean.ORDER_TYPE_LIPING + "";
        childTypeBeans.add(allChildTypeBean8);

        ChildTypeBean allChildTypeBean9 = new ChildTypeBean();
        allChildTypeBean9.typeName = "红包";
        allChildTypeBean9.id = OrderBean.ORDER_TYPE_REDPACGE + "";
        childTypeBeans.add(allChildTypeBean9);

//        ChildTypeBean allChildTypeBean = new ChildTypeBean();
//        allChildTypeBean.typeName = getString(R.string.travel_all_function);
//        allChildTypeBean.id = OrderBean.ORDER_TYPE_ALL+"";
//        childTypeBeans.add(allChildTypeBean);

        int size = childTypeBeans.size();

        industryText = new String[size];

        for (int i = 0; i < size; i++) {
            ChildTypeBean ctB = childTypeBeans.get(i);

            industryText[i] = ctB.typeName;

        }

    }

    /**
     * 注册刷新数据的广播；
     */
    private void registerRefreshReceiver()
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ORDERMANAGER_ACTION);
        registerReceiver(receiver, filter);
    }

    /**
     * 刷新数据的receiver；
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            selfRefresh();
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public void initView()
    {
        //订单标记
        if (getIntent().hasExtra("orderType")) {
            orderType = getIntent().getIntExtra("orderType", Integer.parseInt(AppConstant.home_type_id));

            selectedIndustry = getIndustryIndex(orderType);
        }

        if (getIntent().hasExtra("orderStatus")) {

            orderStatus = getIntent().getStringExtra("orderStatus");

            selectedStatusIndex = Integer.parseInt(orderStatus) + 1;

        }

        setTitleNmae("全部订单");

        findView();

        SwipeRefreshSettings.setAttrbutes(this, sRL);

        sRL.setOnRefreshListener(this);

        mRecyclerView = (HeadFootRecyclerView) findViewById(R.id.recycler_view_ll);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new OrderManageAdapter(this);

        mAdapter.setUiHandler(uiHandler);

        mAdapter.setOrderListsPresenter(orderListsPresenter);

        mAdapter.setOrderStatusPresenter(orderStatusPresenter);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setIsEnale(true);

        mRecyclerView.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener()
            {
                orderListsPresenter.sendOrderInforRequestByInfo(startPage, orderType + "", orderStatus);
            }
        });

        //自动刷新
        selfRefresh();

        industry_orderstatus_layout = (LinearLayout) findViewById(R.id.industry_orderstatus_layout);
    }

    /**
     * 检查行业id编号
     *
     * @param orderType
     * @return
     */
    private int getIndustryIndex(int orderType)
    {
        int indexTemp = 0;

        for (int a = 0; a < childTypeBeans.size(); a++) {

            ChildTypeBean b = childTypeBeans.get(a);

            if (b.id.equals(orderType + "")) {
                indexTemp = a;

                break;
            }
        }

        return indexTemp;
    }

    /**
     * 自动刷新
     */
    private void selfRefresh()
    {
        sRL.post(new Runnable() {
            @Override
            public void run()
            {
                sRL.setRefreshing(true);
            }
        });
        this.onRefresh();
    }

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            selfRefresh();
        }
    };

    private void findView()
    {
        industryLayout = (RelativeLayout) findViewById(R.id.industry_layout);

        industryLayout.setOnClickListener(this);

        orderStatusLayout = (RelativeLayout) findViewById(R.id.order_status_layout);

        orderStatusLayout.setOnClickListener(this);


        sRL = (SwipeRefreshLayout) findViewById(R.id.sRL1);

        mRecyclerView = (HeadFootRecyclerView) findViewById(R.id.recycler_view_ll);
        industryImg = (ImageView) findViewById(R.id.industry_img);
        orderStatusImg = (ImageView) findViewById(R.id.order_status_img);
        industry_text = (TextView) findViewById(R.id.industry_text);
        order_status_text = (TextView) findViewById(R.id.order_status_text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        startPage = 0;
        orderListsPresenter.sendOrderInforRequestByInfo(startPage, orderType + "", orderStatus);
    }

    @Override
    public void onRefresh()
    {
        startPage = 0;
        orderListsPresenter.sendOrderInforRequestByInfo(startPage, orderType + "", orderStatus);
    }


    @Override
    public void getOrderLists(List<OrderBean> maps)
    {
        int size = maps.size();
        if (size == AppConstant.MAX_PAGE_NUM) {
            startPage = size;
        } else {
            mRecyclerView.calByNewNum(size);
        }
        mAdapter.appendData(maps);

        sRL.setRefreshing(false);
    }

    @Override
    public void getMoreDataList(List<OrderBean> moreDataList)
    {

        mAdapter.addNewData(moreDataList);

        int size = moreDataList.size();

        if (size == AppConstant.MAX_PAGE_NUM) {

            startPage = mAdapter.getItemCount();

            mRecyclerView.notifyData();

        } else {

            mRecyclerView.calByNewNum(size);

        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.industry_layout:// 行业分类


                if (popupWindow != null && popupWindow.isShowing()) {

                    popupWindow.dismiss();

                } else {
                    showPupuWindow(industryText, 1);

                    industryImg.setImageResource(R.mipmap.icon_arrow_blue_down);
                    //   industryLayout.setBackgroundResource(R.color.bgWhite);
                    Resources resource = (Resources) getBaseContext().getResources();
                    ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.color_456992);
                    industry_text.setTextColor(csl);
                    //   Utils.changeBackground(this,industryImg);
                }

                break;
            case R.id.order_status_layout:// 订单状态
                if (popupWindow != null && popupWindow.isShowing()) {

                    popupWindow.dismiss();

                } else {
                    showPupuWindow(orderStatusText, 2);

                    orderStatusImg.setImageResource(R.mipmap.icon_arrow_blue_down);
                    //   orderStatusLayout.setBackgroundResource(R.color.bgWhite);
                    Resources resource = (Resources) getBaseContext().getResources();
                    ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.color_456992);
                    order_status_text.setTextColor(csl);
                    //  Utils.changeBackground(this,orderStatusImg);
                }

                break;
        }

    }


    private OrderManAgeGridViewAdapter adapter;


    private int currentType; //1:分类；2：订单状态；

    /**
     * 显示popuwindow
     */
    private void showPupuWindow(String text[], int type)
    {

        currentType = type;
        if (popupWindow == null) {

            adapter = new OrderManAgeGridViewAdapter(this, text);

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.order_manage_popuwindow, null);

            GridView orderManageGridView = (GridView) view.findViewById(R.id.order_manage_gridvew);

            orderManageGridView.setAdapter(adapter);

            popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            popupWindow.setHeight(Utils.getDisplayHeight(this) - industryLayout.getHeight());

            popupWindow.setFocusable(true);

            popupWindow.setOutsideTouchable(true);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (popupWindow.isShowing()) {
                        orderStatusImg.setImageResource(R.mipmap.icon_arrow_black_up);
                        industryImg.setImageResource(R.mipmap.icon_arrow_black_up);

                        //    orderStatusLayout.setBackgroundResource(R.color.wordBlack);
                        Resources resource = (Resources) getBaseContext().getResources();
                        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.color_333333);
                        order_status_text.setTextColor(csl);

                        //  industryLayout.setBackgroundResource(R.color.wordBlack);
                        Resources resource2 = (Resources) getBaseContext().getResources();
                        ColorStateList csl2 = (ColorStateList) resource2.getColorStateList(R.color.color_333333);
                        industry_text.setTextColor(csl2);
                        popupWindow.dismiss();
                    }
                }
            });

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss()
                {

                    Resources resource = (Resources) getBaseContext().getResources();
                    ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.color_333333);
                    order_status_text.setTextColor(csl);

                    Resources resource2 = (Resources) getBaseContext().getResources();
                    ColorStateList csl2 = (ColorStateList) resource2.getColorStateList(R.color.color_333333);
                    industry_text.setTextColor(csl2);

                    orderStatusImg.setImageResource(R.mipmap.icon_arrow_black_up);
                    industryImg.setImageResource(R.mipmap.icon_arrow_black_up);

                }
            });

            adapter.setListener(new OrderManAgeGridViewAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position)
                {

                    if (currentType == 1) {

                        sendIndustryRequest(position);

                    } else if (currentType == 2) {

                        sendOrderStatusRequest(position);

                    }

                    if (popupWindow != null && popupWindow.isShowing()) {

                        orderStatusImg.setImageResource(R.mipmap.icon_arrow_black_up);
                        industryImg.setImageResource(R.mipmap.icon_arrow_black_up);

                        //        orderStatusLayout.setBackgroundResource(R.color.wordBlack);
                        Resources resource = (Resources) getBaseContext().getResources();
                        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.color_333333);
                        order_status_text.setTextColor(csl);

                        //      industryLayout.setBackgroundResource(R.color.wordBlack);
                        Resources resource2 = (Resources) getBaseContext().getResources();
                        ColorStateList csl2 = (ColorStateList) resource2.getColorStateList(R.color.color_333333);
                        industry_text.setTextColor(csl2);

                        popupWindow.dismiss();
                    }
                }
            });
            //设置订单状态
            if (type == 2) {

                adapter.setNewDataArray(text, selectedStatusIndex);

            } else if (type == 1) {

                adapter.setNewDataArray(text, selectedIndustry);
            }

        } else {
            int index = 0;
            if (type == 1) {

                index = selectedIndustry;

            } else if (type == 2) {

                index = selectedStatusIndex;

            }

            adapter.setNewDataArray(text, index);
        }
        int[] location = new int[2];

        industry_orderstatus_layout.getLocationInWindow(location);

        int height = popupWindow.getHeight();

        popupWindow.showAtLocation(industry_orderstatus_layout, Gravity.NO_GRAVITY, 0, location[1] - height);
    }

    /**
     * 根据订单状态和行业查询订单信息
     *
     * @param position
     */
    private int selectedIndustry = 0;

    public void sendIndustryRequest(int position)
    {
        selectedIndustry = position;

        ChildTypeBean bean = childTypeBeans.get(position);

        orderType = Integer.parseInt(bean.id);

        selfRefresh();

    }

    /**
     * 根据订单状态和行业查询订单信息
     *
     * @param position
     */
    private int selectedStatusIndex = 0;

    public void sendOrderStatusRequest(int position)
    {
        selectedStatusIndex = position;

        if (position == 0) {

            orderStatus = null;

        } else {

            orderStatus = (position - 1) + "";
        }

        selfRefresh();

    }


    @Override
    public void onError(Object o)
    {

        if (!TextUtils.isEmpty(o + "")) {
            if (startPage == 0) {

                sRL.setRefreshing(false);

                //检查是否为999

                if (HttpConstant.chechNoInfo(o + "")) {

                    mAdapter.clearData();

                }
            } else {

                mRecyclerView.calByNewNum(0);
            }
        }

    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        closePDialog();

        toastCustom(R.string.app_op_info_success);

        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                sRL.setRefreshing(true);
                onRefresh();
            }
        }, 100);
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        closePDialog();
        toastCustom(o + "");
    }
}
