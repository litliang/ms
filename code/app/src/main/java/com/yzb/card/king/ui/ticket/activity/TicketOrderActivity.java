package com.yzb.card.king.ui.ticket.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.OverbalanceGoodsBean;
import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.common.PayOrderBean;
import com.yzb.card.king.bean.ticket.Flight;
import com.yzb.card.king.bean.ticket.FlightDetailBean;
import com.yzb.card.king.bean.ticket.FlightManager;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.bean.ticket.PostFeeBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.app.activity.AddressManageActivity;
import com.yzb.card.king.ui.app.activity.ConnectorManageActivity;
import com.yzb.card.king.ui.app.activity.DebitRiseManageActivity;
import com.yzb.card.king.ui.app.activity.PassengerEditActivity;
import com.yzb.card.king.ui.app.activity.PassengerManageActivity;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.activity.pay.AppPreferentialPaymentActivity;
import com.yzb.card.king.ui.discount.activity.pay.DiscountPayActivity;
import com.yzb.card.king.ui.discount.adapter.OverbalanceGoodsItemAdapter;
import com.yzb.card.king.ui.discount.adapter.PlaneBxAdapter;
import com.yzb.card.king.ui.discount.adapter.PlaneCjrAdapter;
import com.yzb.card.king.ui.discount.adapter.PlaneHb2Adapter;
import com.yzb.card.king.ui.discount.adapter.PlaneHbAdapter;
import com.yzb.card.king.ui.discount.adapter.PlaneHbPopAdapter;
import com.yzb.card.king.ui.discount.bean.Insurance;
import com.yzb.card.king.ui.hotel.activtiy.HotelRoomOrderActivity;
import com.yzb.card.king.ui.hotel.persenter.HotelProductOrderPresenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.ticket.model.IPlaneTicketOrderSingle;
import com.yzb.card.king.ui.ticket.presenter.GetPostFeePresenter;
import com.yzb.card.king.ui.ticket.presenter.TicketOrderCreatePresenter;
import com.yzb.card.king.ui.ticket.view.CreateFlightOrderView;
import com.yzb.card.king.ui.ticket.view.GetPostFeeView;
import com.yzb.card.king.ui.travel.presenter.impl.GetConnectorPresenter;
import com.yzb.card.king.ui.travel.view.GetDefaultConnectorView;
import com.yzb.card.king.util.ContactUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.DialogUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.TextClickableSpan;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机票订单页；
 * 2016.9.27 需求变动修改  gengqiyun
 */
public class TicketOrderActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface,
        GetDefaultConnectorView, CreateFlightOrderView, GetPostFeeView
{
    private static final int REQ_PICK_CONTACT = 0;   //添加手机联系人
    private static final int REQ_ADD_CONNECTOR = 1;  //添加联系人
    private static final int REQ_SELECT_PASSENGER = 2; //选择乘机人
    private static final int REQ_GET_ADDRESS = 3;  //获取地址
    private static final int REQ_GET_BILL_TITLE = 4;  //获取发票抬头
    private static final int REQ_MODIFY_CJR = 5;  // 乘机人修改；

    private static final int SERVICE1_CLICK_FLAG = 0; //服务条款1点击标记；
    private static final int SERVICE2_CLICK_FLAG = 1;//服务条款2点击标记；
    private static final int SERVICE3_CLICK_FLAG = 2;//服务条款2点击标记；
    public static final int REQ_COMMIT_ORDER = 2;

    private TextView fromTitle;
    private TextView toTitle;
    private TextView cangwei;
    private TextView tv_total_piaojia; //总票价；
    private TextView piaojia; //票价，不包含 税费；
    private TextView tv_tax_fee; //税费
    private TextView tvPassengerNum;//乘机人；
    private EditText lxrName; //联系人姓名；
    private EditText lxrMobile; //联系人电话；
    private WholeRecyclerView cjrListView;
    private PlaneCjrAdapter cjrAdapter;
    private List<PassengerInfoBean> cjrListData; //乘机人列表；
    private WholeRecyclerView bxListView;  //保险列表；
    private PlaneBxAdapter bxAdapter; //保险列表；
    private List<Insurance> bxListData;  //保险列表；

    private TextView tv_post_fee; //邮费；
    private TextView shdzName; //收货人；
    private TextView shdzMobile; //收货人电话；
    private TextView shdzAddress; //收货人地址；
    private LinearLayout panelAddressData;

    private View fwtkLayout;
    private View orderDetailsLayout;
    private ImageView orderDetailsImage; //订单详情指示器；

    private PopupWindow hangbanPopWindow, orderPopWindow;
    private SlideButton slideBxpz;
    private TextView tvTotalAmount;
    private LinearLayout bxpzLayout;
    private TextView tvDebitRise;
    private com.yzb.card.king.ui.ticket.presenter.TicketOrderPresenter presenter;
    private ImageView ivArrowRight;
    private WholeRecyclerView routeListView;
    private View  ll_multi_title, view;
    private TextView multTitle; //订单填写title区；

    private int routeType; //行程类型；
    private List<PlaneTicket> hbListData = new ArrayList<>(); // 航班列表；
    private int adultNum = 0;//成人数量；
    private int childNum = 0;//儿童数量；
    private int babyNum = 0;//婴儿数量；

    private DebitRiseBean debitRiseBean; //发票抬头；
    private GoodsAddressBean addressBean; //收获地址；
    private GetConnectorPresenter connectorPresenter; //获取联系人列表；
    private Connector connector; //联系人；
    private FlightManager flightManager;
    private TicketOrderCreatePresenter orderPresenter;  //创建订单；

    private float adultUnitPrice = 0; //成人单价；
    private float childUnitPrice = 0;//儿童单价；
    private float babyUnitPrice = 0;//婴儿单价；

    private float adultUnitTaxPrice = 0; //成人税费单价；
    private float childUnitTaxPrice = 0;//儿童税费单价；
    private float babyUnitPTaxrice = 0;//婴儿税费单价；


    private PostFeeBean postFeeBean; //邮费；
    private float totalOrderAmount = 0.0f; //订单总价格；
    private float couponAmount = 0f; //优惠金额，来自于上个页面；
    private String flightType; //航班类型（单程：OW；往返：RT；多段：MT，平台组合RT_P）

    private GetPostFeePresenter postFeePresenter;

    private LinearLayout ll_discount;
    private  TextView tv_discount;


    private HotelProductOrderPresenter hotelProductOrderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        colorStatusResId = R.color.color_436a8e;
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        recvIntentArgs();
        initView();
        connectorPresenter = new GetConnectorPresenter(this);
        orderPresenter = new TicketOrderCreatePresenter(this);
        postFeePresenter = new GetPostFeePresenter(this);
        hotelProductOrderPresenter = new HotelProductOrderPresenter(this);
        presenter = new com.yzb.card.king.ui.ticket.presenter.TicketOrderPresenter(this);
        initData();
    }

    private void initView()
    {
        view = LayoutInflater.from(this).inflate(R.layout.activity_plane_ticket_order1, null, false);
        setContentView(view);
        findViewById(R.id.iv_back).setOnClickListener(this);
        fromTitle = (TextView) findViewById(R.id.fromTitle);
        toTitle = (TextView) findViewById(R.id.toTitle);
        cangwei = (TextView) findViewById(R.id.cangwei);
        tv_total_piaojia = (TextView) findViewById(R.id.tv_total_piaojia);
        piaojia = (TextView) findViewById(R.id.piaojia);
        tv_tax_fee = (TextView) findViewById(R.id.tv_tax_fee);
        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
        bxpzLayout = (LinearLayout) findViewById(R.id.bxpzLayout);
        // 航班列表
        routeListView = (WholeRecyclerView) findViewById(R.id.routeListView);
        routeListView.setLayoutManager(new LinearLayoutManager(this));
        ivArrowRight = (ImageView) findViewById(R.id.iv_arrow_right);

        ll_multi_title = findViewById(R.id.ll_multi_title);

        ll_discount = (LinearLayout) findViewById(R.id.ll_discount);
        ll_discount.setOnClickListener(this);

        tv_discount = (TextView) findViewById(R.id.tv_discount);

        multTitle = (TextView) findViewById(R.id.multTitle);

        //乘机人；
        tvPassengerNum = (TextView) findViewById(R.id.tvPassengerNum);
        initTitle();
        initFlightListView();
        initParameterView();
        findViewById(R.id.tvSubmit).setOnClickListener(this);

        initOverBanlanceGoodsView();
    }

    private  OverbalanceGoodsItemAdapter overbalanceGoodsItemAdapter;

    private  LinearLayout llPlusGoods;

    private void initOverBanlanceGoodsView()
    {
        //超值加购
        List<OverbalanceGoodsBean> overbalanceGoodsBeanList = new ArrayList<OverbalanceGoodsBean>();

        overbalanceGoodsItemAdapter = new OverbalanceGoodsItemAdapter(this, overbalanceGoodsBeanList);

        overbalanceGoodsItemAdapter.setStateChangeCallBack(goodsPlushcallBack);

        WholeRecyclerView wvOverbancegoods = (WholeRecyclerView) findViewById(R.id.wvOverbancegoods);

        llPlusGoods = (LinearLayout) findViewById(R.id.llPlusGoods);

        wvOverbancegoods.setLayoutManager(new LinearLayoutManager(this));

        wvOverbancegoods.setAdapter(overbalanceGoodsItemAdapter);
    }

    /**
     * 超值加购监听器
     */
    private OverbalanceGoodsItemAdapter.IStateCallBack goodsPlushcallBack = new OverbalanceGoodsItemAdapter.IStateCallBack() {
        @Override
        public void updatePriceAction(OverbalanceGoodsBean bean)
        {
            updateTotalAmount();
        }
    };


    private void initTitle()
    {
        if (routeType == BaseTicketActivity.TYPE_SINGLE)//单程
        {
            ll_multi_title.setVisibility(View.VISIBLE);
            ivArrowRight.setBackgroundResource(R.mipmap.icon_single);
            multTitle.setVisibility(View.GONE);
            setCityName();
        } else if (BaseTicketActivity.TYPE_ROUND == routeType)   //往返
        {
            ll_multi_title.setVisibility(View.VISIBLE);
            ivArrowRight.setBackgroundResource(R.mipmap.icon_arrow_wf);
            multTitle.setVisibility(View.GONE);
            setCityName();
        } else if (BaseTicketActivity.TYPE_MULTI == routeType)   //多程
        {
            ll_multi_title.setVisibility(View.GONE);
            multTitle.setVisibility(View.VISIBLE);
            multTitle.setText(getString(R.string.ticket_dc_order));
        }
    }

    public void setCityName()
    {
        if (flightManager != null)
        {
            Flight flight = flightManager.getFlights().get(0);
            fromTitle.setText(flight.getStartCity().getCityName());
            toTitle.setText(flight.getEndCity().getCityName());
        }
    }

    /**
     * 初始化航班列表；
     */
    private void initFlightListView()
    {
        //单程
        if (routeType == BaseTicketActivity.TYPE_SINGLE)
        {
            PlaneHbAdapter flightAdapter = new PlaneHbAdapter(this, hbListData);
            routeListView.setAdapter(flightAdapter);
            ll_discount.setVisibility(View.VISIBLE);

            PlaneTicket  planeTicket = hbListData.get(0);
            FavInfoBean pachageBean=  planeTicket.getDisMap();


            tv_discount.setText("退改签规则");

            String priceId = planeTicket.getTicketPriceId();
            ll_discount.setTag(priceId);


        } else if (BaseTicketActivity.TYPE_ROUND == routeType || BaseTicketActivity.TYPE_MULTI == routeType)   //往返或者多程
        {
            PlaneHb2Adapter hbAdapter = new PlaneHb2Adapter(this, hbListData, routeType);
            hbAdapter.setPlanHbHandler(routeHanlder);
            routeListView.setAdapter(hbAdapter);
            ll_discount.setVisibility(View.GONE);
        }
        initCangwei();
    }

    /**
     * 初始化仓位区域；
     */
    private void initCangwei()
    {
        cangwei.setText(getNoRepeatCangwei());
        float totalTicketPrice = 0; //一趟航班的总成人票价，不包含税费；
        PlaneTicket planeTicket;
        for (int i = 0; i < hbListData.size(); i++)
        {
            planeTicket = hbListData.get(i);
            totalTicketPrice += planeTicket.getFareAdult();

            //票价单价初始化；
            adultUnitPrice += planeTicket.getFareAdult();
            childUnitPrice += planeTicket.getFareChd();
            babyUnitPrice += planeTicket.getFareBab();

            //税费单价初始化；
            adultUnitTaxPrice += planeTicket.getFueltax();
            childUnitTaxPrice += planeTicket.getFueltaxChd();
            babyUnitPTaxrice += planeTicket.getFueltaxBab();

            //往返计算1次；
            if (routeType == BaseTicketActivity.TYPE_ROUND)
            {
                break;
            }
        }
        tv_total_piaojia.setText("¥" + Utils.subZeroAndDot((totalTicketPrice + adultUnitTaxPrice) + ""));

        int preResId = routeType == BaseTicketActivity.TYPE_SINGLE ? R.string.ticket_single_pj2 :
                (routeType == BaseTicketActivity.TYPE_ROUND ? R.string.ticket_round_pj : R.string.ticket_multi_pj);

        //票价(不包含税费)；
        piaojia.setText(getString(preResId) + getString(R.string.ticket_yuan_next, Utils.subZeroAndDot(totalTicketPrice + "")));
        //税费；
        tv_tax_fee.setText(getString(R.string.ticket_taxes_fee2, Utils.subZeroAndDot(adultUnitTaxPrice + "")));
    }

    /**
     * 获取不重复的仓位组合；
     *
     * @return 格式：经济舱+普通舱；
     */
    private String getNoRepeatCangwei()
    {
        if (flightManager == null)
        {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        List<Flight> flights = flightManager.getFlights();
        // 拼接航班列表中不重复的仓位；
        String shippingName;
        for (int i = 0; i < flights.size(); i++)
        {
            shippingName = flights.get(i).getShippingSpace().getQabinName();
            //不同才添加仓位名称；
            if (!sb.toString().contains(shippingName))
            {
                if (i != 0)
                {
                    sb.append("+");
                }
                sb.append(shippingName);
            }
        }
        return sb.toString();
    }

    /**
     * 接收Intent参数；
     */
    private void recvIntentArgs()
    {
        Serializable flightManagerObj = getIntent().getSerializableExtra("flightManager");
        if (flightManagerObj != null)
        {
            flightManager = (FlightManager) flightManagerObj;
            routeType = flightManager.getCurrentLine();
            hbListData = flightManager.getTickets();

            adultNum = flightManager.getAdultNum();
            childNum = flightManager.getChildrenNum();
            babyNum = flightManager.getBabyNum();
        }
        flightType = getIntent().getStringExtra("flightType");
    }

    /**
     * 初始化机票订单信息
     */
    private void initParameterView()
    {
        initCjrData();
        initContacts();
        initBxList();
        initInvoice();
        initGoodAddress();
        initTermsOfService();
        //订单详情信息
        orderDetailsLayout = findViewById(R.id.orderDetailsLayout);
        orderDetailsLayout.setOnClickListener(this);
        orderDetailsLayout.setTag(0);
        orderDetailsImage = (ImageView) findViewById(R.id.orderDetailsImage);
    }

    /**
     * 初始化服务条款；
     */
    private void initTermsOfService()
    {
        // 服务条款
        fwtkLayout = findViewById(R.id.fwtkLayout);
        fwtkLayout.setTag(0);
        fwtkLayout.setOnClickListener(this);
        //服务条款文字说明；
        TextView tv_service = (TextView) findViewById(R.id.tv_service);
        tv_service.setMovementMethod(new LinkMovementMethod());

        String service_terms = getString(R.string.ticket_terms_service_line);
        tv_service.setText(addClickablePart(service_terms));
    }

    /**
     * 为不同文字添加不同的点击事件；
     */
    private SpannableString addClickablePart(String text)
    {
        String service_terms_1 = getString(R.string.ticket_service_line2_1);
        String service_terms_2 = getString(R.string.ticket_service_line2_2);
        String service_terms_3 = getString(R.string.ticket_service_line2_3);

        int terms1_start_index = text.indexOf(service_terms_1);
        int terms1_end_index = terms1_start_index + service_terms_1.length();

        int terms2_start_index = text.indexOf(service_terms_2);
        int terms2_end_index = terms2_start_index + service_terms_2.length();

        int terms3_start_index = text.indexOf(service_terms_3);
        int terms3_end_index = terms3_start_index + service_terms_3.length();

        SpannableString sb = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_2780b9));
        //设置颜色；
        sb.setSpan(colorSpan, terms1_start_index, terms1_end_index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(colorSpan, terms2_start_index, terms2_end_index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(colorSpan, terms3_start_index, terms3_end_index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        setTextClickableSpan(sb, terms1_start_index, terms1_end_index, SERVICE1_CLICK_FLAG);
        setTextClickableSpan(sb, terms2_start_index, terms2_end_index, SERVICE2_CLICK_FLAG);
        setTextClickableSpan(sb, terms3_start_index, terms2_end_index, SERVICE3_CLICK_FLAG);

        return sb;
    }

    /**
     * 为文本块设置点击；
     *
     * @param sb
     * @param startIndex
     * @param endIndex
     * @param flag       0：锂电池及危险品须知;
     *                   1:旅行套餐产品服务条款
     */
    public void setTextClickableSpan(SpannableString sb, int startIndex, int endIndex, final int flag)
    {
        sb.setSpan(new TextClickableSpan()
        {
            @Override
            public void onClick()
            {
                String category = null;
                String title = null;
                switch (flag)
                {
                    case SERVICE1_CLICK_FLAG:
                        category = AppConstant.h5_category_ticket_order_h5_1;
                        title = getString(R.string.ticket_service_line2_1);
                        break;
                    case SERVICE2_CLICK_FLAG:
                        category = AppConstant.h5_category_ticket_order_h5_2;
                        title = getString(R.string.ticket_service_line2_2);
                        break;
                    case SERVICE3_CLICK_FLAG:
                        category = AppConstant.h5_category_ticket_order_h5_2;
                        title = getString(R.string.ticket_service_line2_3);
                        break;
                }
                Bundle bundle = new Bundle();
                bundle.putString(WebViewClientActivity.CATEGORY, category);
                bundle.putString(WebViewClientActivity.TITLE_NAME, title);
                readyGoWithBundle(TicketOrderActivity.this, WebViewClientActivity.class, bundle);
            }
        }, startIndex, endIndex, 0);
    }

    /**
     * 初始化送货地址；
     */
    private void initGoodAddress()
    {
        findViewById(R.id.panelAddress).setOnClickListener(this);
        shdzName = (TextView) findViewById(R.id.addressName);
        shdzMobile = (TextView) findViewById(R.id.addressMobile);
        shdzAddress = (TextView) findViewById(R.id.addressAddress);
    }

    /**
     * 初始化报销凭证；
     */
    private void initInvoice()
    {
        //报销凭证按钮
        slideBxpz = (SlideButton) findViewById(R.id.sbWipedOut);
        slideBxpz.setOnToggleStateChangeListener(bxpzToggleListener);
        slideBxpz.setToggleState(SlideButton.ToggleState.close);

        tv_post_fee = (TextView) findViewById(R.id.tv_post_fee);
        tv_post_fee.setText("");
        panelAddressData = (LinearLayout) findViewById(R.id.panelAddressData);
        //发票抬头
        findViewById(R.id.fpttLayout).setOnClickListener(this);
        tvDebitRise = (TextView) findViewById(R.id.tvDebitRise);
    }

    /**
     * 报销凭证切换；
     */
    private SlideButton.OnToggleStateChangeListener bxpzToggleListener = new SlideButton.OnToggleStateChangeListener()
    {
        @Override
        public void onToggleStateChange(SlideButton.ToggleState state)
        {
            boolean isOpen = state == SlideButton.ToggleState.open ? true : false;
            bxpzLayout.setVisibility(isOpen ? View.VISIBLE : View.GONE);
            if (isOpen)
            {
                //获取默认收获地址；
                if (addressBean == null)
                {
                    presenter.getDefaultAddress();
                }
                //获取默认发票抬头；
                if (debitRiseBean == null)
                {
                    presenter.getDefaultDebit();
                }
            }
            updateTotalAmount();
        }
    };


    private View panelInsurance; //保险块；

    /**
     * 初始化保险列表
     */
    private void initBxList()
    {
        panelInsurance = findViewById(R.id.panelInsurance);
        panelInsurance.setVisibility(View.GONE);
        bxListView = (WholeRecyclerView) findViewById(R.id.bxListView);
        bxListView.setNeedDivider(true);

        bxListData = new ArrayList<>();
        bxAdapter = new PlaneBxAdapter(this, bxListData);
        bxAdapter.setStateChangeCallBack(new PlaneBxAdapter.IStateCallBack()
        {
            @Override
            public void stateChange()
            {
                updateTotalAmount();
            }

            @Override
            public void goIntroducePage(String shortName, String url)
            {
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                bundle.putString(WebViewClientActivity.TITLE_NAME, shortName);
                readyGoWithBundle(TicketOrderActivity.this, WebViewClientActivity.class, bundle);
            }
        });
        bxListView.setLayoutManager(new LinearLayoutManager(this));
        bxListView.setAdapter(bxAdapter);
        setBxPrice();
    }


    /**
     * 刷新保险价格；
     */
    public void setBxPrice()
    {
        //总票价+税费
        float totalAmount = getTotalOrderTicketPrice() + getTotalTaxFee() - couponAmount;
        bxAdapter.setOrderAmount(totalAmount);
    }

    /**
     * 初始化联系人view；
     */
    private void initContacts()
    {
        //通讯录联系人
        findViewById(R.id.lxrButton).setOnClickListener(this);

        lxrName = (EditText) findViewById(R.id.lxrName);
        lxrMobile = (EditText) findViewById(R.id.lxrMobile);
    }

    /**
     * 初始化乘机人列表；
     */
    private void initCjrData()
    {
        //初始化乘机人数量
        tvPassengerNum.setText(getPassengerNumberStr());
        // 乘机人
        findViewById(R.id.cjrLayout).setOnClickListener(this);
        //乘机人列表
        cjrListView = (WholeRecyclerView) findViewById(R.id.cjrListView);
        cjrListView.setNeedDivider(true);
        cjrListData = new ArrayList<>();
        cjrAdapter = new PlaneCjrAdapter(this, cjrListData, R.layout.plane_cjr_item, deleteCjrListener);
        cjrListView.setLayoutManager(getLinearLayoutManager());
        cjrAdapter.setOnItemClickListener(cjrItemListener);
        cjrListView.setAdapter(cjrAdapter);
    }

    /**
     * 获取乘机人各人种数量组合；
     */
    private String getPassengerNumberStr()
    {
        StringBuilder sb = new StringBuilder("");
        sb.append("请添加 成人");
        sb.append(adultNum + "人");
        sb.append(childNum <= 0 ? "" : " 儿童" + childNum + "人");
        sb.append(babyNum <= 0 ? "" : " 婴儿" + babyNum + "人");
        return sb.toString();
    }

    private LinearLayoutManager getLinearLayoutManager()
    {
        return new LinearLayoutManager(this);
    }

    /**
     * 乘机人item点击；
     */
    private PlaneCjrAdapter.OnItemClickListener cjrItemListener = new PlaneCjrAdapter.OnItemClickListener()
    {
        @Override
        public void setOnItemClickListener(int position)
        {
            Intent intent = new Intent(TicketOrderActivity.this, PassengerEditActivity.class);
            intent.putExtra(PassengerEditActivity.ARG_DATA_BEAN, cjrListData.get(position));
            startActivityForResult(intent, REQ_MODIFY_CJR);
        }
    };

    /**
     * 删除乘机人的响应事件
     */
    View.OnClickListener deleteCjrListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //标记
            String cjrId = (String) v.getTag();
            for (PassengerInfoBean bean : cjrListData)
            {
                if (cjrId.equals(bean.id))
                {
                    cjrListData.remove(bean);
                    cjrAdapter.notifyDataSetChanged();
                    updateTotalAmount();
                    comparePersonNum();
                    break;
                }
            }
        }
    };

    /**
     * 初始化数据信息
     */
    private void initData()
    {
        updateTotalAmount();
        getBxListData();
        getConnector(true);

        hotelProductOrderPresenter.queryGoodsPlusRequest(AppConstant.ticket_id);
    }


    /**
     * 获取保险列表；
     */
    private void getBxListData()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("industryId", AppConstant.ticket_id);
        //获取保险列表；
        presenter.getBxList(params);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        refreshSelectConnector();
    }

    /**
     * 页面显示时刷新选中的联系人（如果有）；
     * 主要解决常用联系人页面编辑带来的数据不一致问题；
     */
    private void refreshSelectConnector()
    {
        if (connector != null)
        {
            getConnector(false);
        }
    }

    /**
     * 计算总价
     */
    private void updateTotalAmount()
    {
        //总票价；
        float totalAmount = getTotalTicketPrice();
        //总税费；
        totalAmount += getTotalTaxFee();
        //总保险费；
        totalAmount += getBxFee(totalAmount);
        //邮费；
        if (slideBxpz.getState() == SlideButton.ToggleState.open && this.postFeeBean != null)
        {
            totalAmount += postFeeBean.getFee();
        }
        //超值加购总价
        float goodsPlushMoney = 0f;

        if (overbalanceGoodsItemAdapter != null) {

            List<OverbalanceGoodsBean> list = overbalanceGoodsItemAdapter.getData();

            int size = list.size();

            if (size > 0) {

                for (OverbalanceGoodsBean bean : list) {
                    int number = bean.getNumber();
                    if (number > 0) {

                        goodsPlushMoney = goodsPlushMoney + number * Float.parseFloat(bean.getPrice());
                    }
                }
            }
        }

        this.totalOrderAmount = totalAmount+goodsPlushMoney;

        tvTotalAmount.setText("¥" + Utils.subZeroAndDot(this.totalOrderAmount + ""));
    }


    /**
     * 获取订单总的票价；不带税费；
     *
     * @return
     */
    public float getTotalOrderTicketPrice()
    {
        return adultUnitPrice * getPersonTypeNum(AppConstant.TYPE_ADULT) +
                childUnitPrice * getPersonTypeNum(AppConstant.TYPE_CHILD) +
                babyUnitPrice * getPersonTypeNum(AppConstant.TYPE_BABY);
    }

    /**
     * 获取总的票价；不带税费；
     *
     * @return
     */
    public float getTotalTicketPrice()
    {
        return adultUnitPrice * getPersonTypeNum(AppConstant.TYPE_ADULT) +
                childUnitPrice * getPersonTypeNum(AppConstant.TYPE_CHILD) +
                babyUnitPrice * getPersonTypeNum(AppConstant.TYPE_BABY);


    }

    /**
     * 计算 保险费；
     *
     * @param totalAmount
     */
    private float getBxFee(float totalAmount)
    {
        float bxTotalAmount = 0f;
        if (bxListData != null)
        {
            for (Insurance data : bxListData)
            {
                if (data.isSelected)
                {
                    bxTotalAmount += Float.parseFloat(data.getActuAmount(totalAmount));
                }
            }
        }
        return bxTotalAmount;
    }

    private Handler routeHanlder = new Handler(){

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            showPopWindow(R.id.llRount);////航班详情
        }
    };

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back://返回
                setResult(REQ_COMMIT_ORDER);
                finish();
                break;

            case R.id.lxrEditButton://添加联系人
                addConnector();
                break;
            case R.id.cjrLayout://乘机人选择
                goSelectPassenger();
                break;
            case R.id.lxrButton://选择通讯录联系人
                ContactUtil.startContactsActivity(this, REQ_PICK_CONTACT);
                break;
            case R.id.panelAddress://送货地址选择
                getAddressInfo();
                break;
            case R.id.fpttLayout: //发票抬头
                getBillTitle();
                break;
            case R.id.orderDetailsLayout:  // 订单详情；
                showOrHideOrderDetailPop();
                break;
            case R.id.tvSubmit: //提交订单
                if (isInputRight())
                {
                    judgePersonNum();
                }
                break;
            case R.id.ll_discount://单程 退改签

                String priceId = (String) v.getTag();
                Intent intent = new Intent(TicketOrderActivity.this, RefundTicketRuleActivity.class);
                intent.putExtra("priceId", priceId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        setResult(REQ_COMMIT_ORDER);
        finish();
    }

    /**
     * 判断人数；
     */
    private void judgePersonNum()
    {
        //人种数量不一致；
        if (this.adultNum > getPersonTypeNum(AppConstant.TYPE_ADULT) ||
                this.childNum > getPersonTypeNum(AppConstant.TYPE_CHILD) ||
                this.babyNum > getPersonTypeNum(AppConstant.TYPE_BABY))
        {
            DialogUtil.getInstance().showDialog(this, getString(R.string.dialog_title), getString(R.string.dialog_person_not_same),
                    new DialogUtil.DialogCallBack()
                    {
                        @Override
                        public void yes()
                        {
                            exeSubmit();
                        }
                    });
        } else
        {
            exeSubmit();
        }
    }

    /**
     * 显示或隐藏订单详情dialog；
     */
    private void showOrHideOrderDetailPop()
    {
        if (getPersonTypeNum(AppConstant.TYPE_ADULT) <= 0)
        {
            toastCustom("请选择至少一个成年乘机人");
            return;
        }
        int tag = (int) orderDetailsLayout.getTag();
        updateArrowBgByTag(tag);
        //未显示订单详情；
        if (tag == 0)
        {
            showPopWindow(R.id.orderDetailsLayout);
        } else
        {
            if (orderPopWindow != null)
            {
                orderPopWindow.dismiss();
            }
        }
    }

    /**
     * 根据view的tag旋转订单详情指示器；
     *
     * @param orderTag 0:向上旋转，1：向下旋转；
     */
    private void updateArrowBgByTag(int orderTag)
    {
        switch (orderTag)
        {
            case 0:
                Animation startAnimation = AnimationUtils.loadAnimation(this, R.anim.shuttle_rotate_0_180);
                startAnimation.setDuration(0);
                orderDetailsImage.startAnimation(startAnimation);
                break;
            case 1:
                Animation endAnimation = AnimationUtils.loadAnimation(this, R.anim.shuttle_rotate_180_360);
                endAnimation.setDuration(0);
                orderDetailsImage.startAnimation(endAnimation);
                break;
        }
        orderDetailsLayout.setTag(orderTag == 0 ? 1 : 0);
    }

    /**
     * 获取发票抬头
     *
     * @author ysg
     * created at 2016/7/12 16:49
     */
    private void getBillTitle()
    {
        Intent intent = new Intent(this, DebitRiseManageActivity.class);
        intent.putExtra("flag", DebitRiseManageActivity.GET_RISE_DATA);
        startActivityForResult(intent, REQ_GET_BILL_TITLE);
    }

    @Override
    public void onCreateOrderSucess(final Object orderData)
    {
        closePDialog();
        final OrderOutBean orderOutBean = (OrderOutBean) orderData;
        toastCustom(R.string.ticket_create_order_suc);
        loadDataHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
//                //旧的特惠付款
//                Intent intent = new Intent(TicketOrderActivity.this, DiscountPayActivity.class);
//                HashMap<String, String> map = new HashMap();
//                map.put(DiscountPayActivity.STORE_NAME, orderPresenter.getStoreNames());//商家名称；会有多个；
//                map.put(DiscountPayActivity.STORE_IDS, orderPresenter.getStoreIds());//商家id；商家id(多个使用英文逗号分割)
//                map.put(DiscountPayActivity.ORDER_ID, orderOutBean.getOrderId());//订单id；
//                map.put(DiscountPayActivity.ORDER_NO, orderOutBean.getOrderNo());//订单号；
//                map.put(DiscountPayActivity.NOTIFY_URL, orderOutBean.getNotifyUrl());
//
//                map.put(DiscountPayActivity.ORDER_TIME, DateUtil.formatOrderTime(orderOutBean.getOrderTime()));//订单时间；
//                map.put(DiscountPayActivity.INDUSTRY_ID, AppConstant.ticket_id);//行业id；
//                map.put(DiscountPayActivity.GOODS_IDS, orderPresenter.getGoodIds());//商品id；商品id(多个使用英文逗号分割)
//                map.put(DiscountPayActivity.ORDER_AMOUNT, totalOrderAmount + "");//订单金额；
//                map.put(DiscountPayActivity.GOODS_CODES, AppConstant.discount_code_ticket);
//
//                map.put(DiscountPayActivity.IS_INPUT, AppConstant.ACCOUNT_NO_INPUT);//0：不需要手输入；1：需要；
//                map.put("flightType", flightType); //当前航线类型；
//
//                intent.putExtra(DiscountPayActivity.ARG_MAP, map);
//                startActivity(intent);
//                finish();

                UserManager.getInstance().setFlightType(flightType);

                //新的特惠付款
                //查询用户的代金券信息
                //发放平台
                int issuePlatform = 0;
                //行业id
                int industryId = Integer.parseInt(AppConstant.ticket_id);

                GoldTicketParam goldTicketParam = new GoldTicketParam();

                goldTicketParam.setIssuePlatform(issuePlatform);

                goldTicketParam.setIndustryId(industryId);


                if(!TextUtils.isEmpty(orderPresenter.getStoreIds())){

                    int index =  orderPresenter.getStoreIds().indexOf(",");

                    if( index== -1){

                        long a = Long.parseLong(orderPresenter.getStoreIds());

                        goldTicketParam.setShopId(a);
                    }else {

                        String[] strArray = orderPresenter.getStoreIds().split(",");

                        long a = Long.parseLong(strArray[0]);

                        goldTicketParam.setShopId(a);
                    }

                }
//                goldTicketParam.setStoreId(roomPolicy.getHotelId());
//
                if(!TextUtils.isEmpty(orderPresenter.getGoodIds())){

                  int index =  orderPresenter.getGoodIds().indexOf(",");

                    if( index== -1){

                        long a = Long.parseLong(orderPresenter.getGoodIds());

                        goldTicketParam.setGoodsId(a);
                    }else {

                        String[] strArray = orderPresenter.getGoodIds().split(",");

                        long a =  Long.parseLong(strArray[0]);

                        goldTicketParam.setGoodsId(a);
                    }

                }

                PayOrderBean bean = new PayOrderBean();

                bean.setOrderAmount(totalOrderAmount);

                bean.setNotifyUrl(orderOutBean.getNotifyUrl());

                bean.setOrderId(Long.parseLong(orderOutBean.getOrderId()));

                bean.setOrderNo(orderOutBean.getOrderNo());

                bean.setOrderTime(orderOutBean.getOrderTime());

                Intent intent = new Intent(TicketOrderActivity.this, AppPreferentialPaymentActivity.class);

                intent.putExtra("orderData", bean);

                intent.putExtra("goldTicketParam", goldTicketParam);

                intent.putExtra("goodName", orderPresenter.getStoreNames());

                startActivity(intent);

                finish();


            }
        }, 100);
    }

    @Override
    public void onCreateOrderFail(String failMsg)
    {
        closePDialog();
        toastCustom(failMsg);
    }

    @Override
    public List<PlaneTicket> getHbListData()
    {
        return hbListData;
    }

    /**
     * 提交订单
     */
    private void exeSubmit()
    {
        showPDialog(R.string.dialog_msg_comitting);
        Map<String, Object> params = new HashMap<>();
        params.put("contActs", lxrName.getText().toString()); //订单联系人
        params.put("contactOphone", lxrMobile.getText().toString()); //订单联系人联系方式
        //报销凭证状态
        boolean flag = slideBxpz.getState() == SlideButton.ToggleState.open ? true : false;
        params.put("seatno", flag ? "1" : "0"); //报销凭证状态(1：需要报销，0否)

        params.put("flightInfoList", getFlightInfoListNew()); //航班信息
        params.put("favorPassengerList", getFavorPassengerList()); //乘客信息
        if (flag)
        {
            params.put("invoiceNumber", debitRiseBean.getInvoiceId() + ""); //发票抬头
            Map<String, String> express = new HashMap<>();
            express.put("name", addressBean.contacts);
            express.put("ophone", addressBean.phone);
            express.put("addRess", shdzAddress.getText().toString().trim());
            express.put("distributionFee", postFeeBean.getFee() + "");//邮费
            params.put("express", express); //快递信息
        }
        params.put("flightType", flightType);
        params.put("orderAmount", Utils.subZeroAndDot(totalOrderAmount + ""));

        orderPresenter.loadData(params);
    }

    private List<Map<String, String>> getFavorPassengerList()
    {
        List<Map<String, String>> passengers = new ArrayList<>();
        Map<String, String> map;
        PassengerInfoBean pib;
        for (int i = 0; i < cjrListData.size(); i++)
        {
            pib = cjrListData.get(i);
            map = new HashMap<>();
            map.put("passengerId", pib.getId());
            map.put("seatNo", "");
            map.put("passengerType", pib.getPersonTypeEn());
            passengers.add(map);
        }
        return passengers;
    }


    /**
     * 获取提交前的航班列表bean；
     */
    private List<Map<String, String>> getFlightInfoListNew()
    {
        List<Map<String, String>> flightInfos = new ArrayList<>();
        Map<String, String> map;
        PlaneTicket pt;
        for (int i = 0; i < hbListData.size(); i++)
        {
            pt = hbListData.get(i);
            map = new HashMap<>();
            map.put("flightId", pt.getFlightId());
            map.put("agentId", pt.getAgentId());

            map.put("productId", getProductId(pt)); //产品编号；国际：332；国内 : 331

            map.put("routeType", pt.getRouteType());
            map.put("packages", "");
            map.put("state", "0");  //0：正常 1： 改升   2：退票   3：改签
            map.put("ticketPriceId", pt.getTicketPriceId());
            map.put("fareInfor", pt.getFareAdult() + ""); //先传成人票价；
            map.put("fueltax", pt.getFueltax() + ""); //机建燃油费
            flightInfos.add(map);
        }
        return flightInfos;
    }


    /**
     * 获取 产品编号；国际：332；国内 : 331
     *
     * @return
     */
    public String getProductId(PlaneTicket planeTicket)
    {
        if (planeTicket != null)
        {
            return planeTicket.isNationalFlight() ? AppConstant.product_id_outcountry : AppConstant.product_id_incountry;
        }
        return AppConstant.product_id_incountry;
    }

    /**
     * 验证填写的数据
     */
    private boolean isInputRight()
    {
        //乘机人；
        if (cjrListData == null || cjrListData.size() <= 0)
        {
            UiUtils.shortToast(getString(R.string.ticket_xzcjr));
            return false;
        }
        if (!isPassengerNumRight())
        {
            return false;
        }
        //联系人；
        if (TextUtils.isEmpty(lxrName.getText()))
        {
            UiUtils.shortToast(getString(R.string.ticket_txlxr));
            return false;
        }
        if (!RegexUtil.validPhoneNum(lxrMobile.getText().toString()))
        {
            return false;
        }
        //报销凭证打开时，检查发票抬头；
        if (slideBxpz.getState() == SlideButton.ToggleState.open)
        {
            //邮费；
            if (postFeeBean == null)
            {
                toastCustom(R.string.ticket_post_type);
                return false;
            }
            //发票抬头；
            if (debitRiseBean == null)
            {
                toastCustom(R.string.ticket_xzfptt);
                return false;
            }
            //送货地址
            if (addressBean == null)
            {
                toastCustom(R.string.ticket_xzaddress);
                return false;
            }
        }
        return true;
    }

    /**
     * 提交订单前，判断乘机人各个人种的人数是否符合要求；
     */
    private boolean isPassengerNumRight()
    {
        int adultNum = getPersonTypeNum(AppConstant.TYPE_ADULT);
        int childNum = getPersonTypeNum(AppConstant.TYPE_CHILD);
        int babyNum = getPersonTypeNum(AppConstant.TYPE_BABY);
        LogUtil.i("目标成人=" + this.adultNum + ",childNum=" + this.childNum + ",babyNum=" + this.babyNum);
        LogUtil.i("当前成人=" + adultNum + ",childNum=" + childNum + ",babyNum=" + babyNum);
        //成人数量不能为0；
        if (adultNum == 0)
        {
            toastCustom(R.string.ticket_adault_empty);
            return false;
        }
        return true;
    }

    /**
     * 获取地址
     *
     * @author ysg
     * created at 2016/7/11 16:16
     */
    private void getAddressInfo()
    {
        Intent intent = new Intent(this, AddressManageActivity.class);
        intent.putExtra("flag", AddressManageActivity.GET_ADDRESS_DATA);
        startActivityForResult(intent, REQ_GET_ADDRESS);
    }

    /**
     * 添加联系人
     *
     * @author ysg
     * created at 2016/7/8 17:34
     */
    private void addConnector()
    {
        if (UserManager.getInstance().isLogin())
        {
            Intent intent = new Intent(this, ConnectorManageActivity.class);
            intent.putExtra(ConnectorManageActivity.KEY, "TrainOrderActivity");
            startActivityForResult(intent, REQ_ADD_CONNECTOR);
        } else
        {
            new GoLoginDailog(this).show();
        }
    }

    /**
     * 选择乘车人
     *
     * @author ysg
     * created at 2016/7/11 11:00
     */
    private void goSelectPassenger()
    {
        Intent intent = new Intent(this, PassengerManageActivity.class);
        intent.putExtra(PassengerManageActivity.KEY, this.getLocalClassName());
        startActivityForResult(intent, REQ_SELECT_PASSENGER);
    }

    /**
     * 打开泡泡窗口
     */
    private void showPopWindow(int id)
    {
        switch (id)
        {
            case R.id.llRount: // 多程或往返的右侧箭头点击；
                if (hangbanPopWindow == null)
                {
                    hangbanPopWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    initPopWindow(hangbanPopWindow, R.layout.popwindow_content_plane_hb);
                }
                initHbViewData();
                hangbanPopWindow.showAtLocation(view, Gravity.TOP, 0, 0);
                break;
            case R.id.orderDetailsLayout: //订单详情；
                //选择的成人数量不能为0；
                if (getPersonTypeNum(AppConstant.TYPE_ADULT) == 0)
                {
                    return;
                }
                if (orderPopWindow == null)
                {
                    orderPopWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT,
                            Utils.getDisplayHeight(this) - orderDetailsLayout.getHeight());
                    initPopWindow(orderPopWindow, R.layout.popwindow_content_plane_order);
                }
                initOrderViewData();
                orderPopWindow.showAtLocation(view, Gravity.TOP, 0, 0);
                break;
        }
    }

    /**
     * 初始化泡泡window
     */
    private void initPopWindow(PopupWindow window, int contentViewId)
    {
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.popupwindow_animation_style);
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        window.setContentView(getLayoutInflater().inflate(contentViewId, null));
        if (contentViewId == R.layout.popwindow_content_plane_order)
        {
            window.setOnDismissListener(new PopupWindow.OnDismissListener()
            {
                @Override
                public void onDismiss()
                {
                    updateArrowBgByTag(1);
                }
            });
        }
    }

    /**
     * 获取总税费；
     */
    private float getTotalTaxFee()
    {
        return getPersonTypeNum(AppConstant.TYPE_ADULT) * adultUnitTaxPrice +
                getPersonTypeNum(AppConstant.TYPE_CHILD) * childUnitTaxPrice +
                getPersonTypeNum(AppConstant.TYPE_BABY) * babyUnitPTaxrice;
    }

    /**
     * 订单详情金额列表；
     */
    private void initOrderViewData()
    {
        View orderView = orderPopWindow.getContentView();
        ListView listView = (ListView) orderView.findViewById(R.id.listview);
        List<Map<String, String>> orderListData = new ArrayList<>();
        orderListData.addAll(getPassengersFee());
        //税费；
        if (getTotalTaxFee() > 0)
        {
            orderListData.add(getTaxFeeMap());
        }
        //保险；
        addInsuranceToOrderDetail(orderListData);
        //邮费；
        if (slideBxpz.getState() == SlideButton.ToggleState.open && postFeeBean != null)
        {
            orderListData.add(getPostageFee());
        }
        SimpleAdapter adapter = new SimpleAdapter(this, orderListData, R.layout.popwindow_content_plane_order_item,
                new String[]{"title", "details", "price"},
                new int[]{R.id.title, R.id.details, R.id.price});
        listView.setAdapter(adapter);
        //订单详情window中的灰色区域；
        View filterBlack = orderView.findViewById(R.id.filterBlack);
        filterBlack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                orderPopWindow.dismiss();
            }
        });
    }

    /**
     * 订单详情-->邮费；
     */
    private Map<String, String> getPostageFee()
    {
        Map<String, String> map = new HashMap<>();
        map.put("title", getString(R.string.ticke_yjf));
        map.put("details", "");
        map.put("price", getString(R.string.rmb) + Utils.subZeroAndDot(postFeeBean.getFee() + ""));
        return map;
    }

    /**
     * 订单详情-->保险；
     */
    private void addInsuranceToOrderDetail(List<Map<String, String>> orderListData)
    {
        if (bxListData == null) return;

        //总票价+税费； 计算基数
        float totalAmount = getTotalOrderTicketPrice() + getTotalTaxFee() - couponAmount;
        int sumPerson = getActuSumPerson();
        for (Insurance insurance : bxListData)
        {
            Map<String, String> map;
            if (insurance.isSelected)
            {
                map = new HashMap<>();
                map.put("title", insurance.getShortName());
                map.put("details", "");
                map.put("price", getString(R.string.rmb) + Utils.subZeroAndDot(
                        Float.parseFloat(insurance.getActuAmount(totalAmount)) * sumPerson + ""));
                orderListData.add(map);
            }
        }
    }

    /**
     * 获取实际乘客总人数；
     */
    public int getActuSumPerson()
    {
        return getPersonTypeNum(AppConstant.TYPE_ADULT) +
                getPersonTypeNum(AppConstant.TYPE_CHILD) +
                getPersonTypeNum(AppConstant.TYPE_BABY);
    }

    /**
     * 订单详情-->税费；
     */
    private Map<String, String> getTaxFeeMap()
    {
        Map<String, String> map = new HashMap<>();
        map.put("title", getString(R.string.ticket_taxes_fee));
        map.put("details", "");
        map.put("price", getString(R.string.rmb) + Utils.subZeroAndDot(getTotalTaxFee() + ""));
        return map;
    }

    /**
     * 订单详情-->乘客费用列表；
     */
    private List<Map<String, String>> getPassengersFee()
    {
        List<Map<String, String>> list = new ArrayList<>();
        int[] personNum = {getPersonTypeNum(AppConstant.TYPE_ADULT),
                getPersonTypeNum(AppConstant.TYPE_CHILD),
                getPersonTypeNum(AppConstant.TYPE_BABY)}; //人数；

        int[] types = {R.string.cr, R.string.et, R.string.ye};
        String[] titles = {getString(R.string.ticket_pj1), "", ""};
        float[] unitPrice = {adultUnitPrice, childUnitPrice, babyUnitPrice}; //成人，儿童，婴儿单价；

        for (int i = 0; i < personNum.length; i++)
        {
            if (personNum[i] > 0)
            {
                Map<String, String> map = new HashMap<>();
                map.put("title", titles[i]);
                map.put("details", getString(R.string.ticket_person_num, getString(types[i]), personNum[i]));
                map.put("price", getString(R.string.rmb) + Utils.subZeroAndDot(unitPrice[i] * personNum[i] + ""));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 航班对话框列表；
     */
    private void initHbViewData()
    {
        View cityContentView = hangbanPopWindow.getContentView();
        if (cityContentView != null)
        {

            ListView listView = (ListView) cityContentView.findViewById(R.id.listview);
            listView.setSelector(getResources().getDrawable(R.drawable.arrow_down_menu_bg_selector));
            PlaneHbPopAdapter adapter = new PlaneHbPopAdapter(this, hbListData);
            adapter.setRouteType(routeType);
            listView.setAdapter(adapter);
            //航班列表灰色区域；
            cityContentView.findViewById(R.id.filterBlack).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    hangbanPopWindow.dismiss();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null)
        {
            return;
        }
        switch (requestCode)
        {
            case REQ_SELECT_PASSENGER:  //添加乘客
                Serializable serPassenger = data.getSerializableExtra("passenger");
                if (serPassenger != null)
                {
                    PassengerInfoBean pib = (PassengerInfoBean) serPassenger;
                    //人种数不能大于 当前的数量；
                    String en = pib.getPersonTypeEn();
                    if (isSumNumRight(en))
                    {
                        addPassengerToList(pib);

                        updateTotalAmount();
                    }
                }
                break;
            case REQ_MODIFY_CJR://修改乘机人
                Serializable serCjr = data.getSerializableExtra(PassengerEditActivity.MODIFIED_BEAN);
                if (serCjr != null)
                {
                    PassengerInfoBean passenger = (PassengerInfoBean) serCjr;
                    LogUtil.i("修改后的乘机人：" + JSON.toJSONString(passenger));
                    addPassengerToList(passenger);
                }
                break;
            case REQ_PICK_CONTACT:  //选择通讯录联系人
                if (resultCode == Activity.RESULT_OK)
                {
                    Connector connector = new Connector();
                    ContactUtil.getConnector(this, data, connector);
                    initConnectText(connector);
                }
                break;
            case REQ_ADD_CONNECTOR:  //添加联系人
                Serializable obj = data.getSerializableExtra("connector");
                if (obj != null)
                {
                    initConnectText((Connector) obj);
                }
                break;
            case REQ_GET_ADDRESS:  //选择收货地址
                addressBean = (GoodsAddressBean) data.getSerializableExtra("addressBeanTemp");
                shdzName.setText(addressBean.contacts);
                shdzMobile.setText(addressBean.phone);
                String city = addressBean.cityName.equals(addressBean.provinceName) ?
                        addressBean.cityName : addressBean.provinceName +
                        addressBean.cityName;
                shdzAddress.setText(city + addressBean.getDistrictName() + addressBean.address);
                loadPostageFee(addressBean.getCityId());

                panelAddressData.setVisibility(View.VISIBLE);

                break;
            case REQ_GET_BILL_TITLE:  //选择发票抬头
                debitRiseBean = (DebitRiseBean) data.getSerializableExtra("risetBean");
                tvDebitRise.setText(debitRiseBean.content);
                break;
        }
    }

    /**
     * 填充内容联系人内容显示；
     *
     * @param connector
     */
    private void initConnectText(Connector connector)
    {
        if (connector != null)
        {
            this.connector = connector;
            lxrName.setText(connector.nickName);
            lxrMobile.setText(connector.mobile);
        }
    }

    /**
     * 判断某一个乘客类型人数是否已达规定人数上限；
     *
     * @param personType 乘客类型
     * @return true:未达上限，可继续添加；false：已到规定的最大人数；
     */
    private boolean isSumNumRight(String personType)
    {
        // 现有的数量；
        int personSum = getPersonTypeNum(personType);
        switch (personType)
        {
            case AppConstant.TYPE_ADULT:
                if (personSum >= adultNum)
                {
                    toastCustom(R.string.ticket_nomore_adult);
                    return false;
                }
                break;
            case AppConstant.TYPE_CHILD:
                if (personSum >= childNum)
                {
                    toastCustom(R.string.ticket_nomore_child);
                    return false;
                }
                break;
            case AppConstant.TYPE_BABY:
                if (personSum >= babyNum)
                {
                    toastCustom(R.string.ticket_nomore_baby);
                    return false;
                }
                break;
        }
        return true;
    }

    /**
     * 获取某一类型乘客的数量；
     *
     * @param personType 例如：AppConstant.TYPE_ADULT,AppConstant.TYPE_CHILD,AppConstant.TYPE_BABY
     * @return
     */
    private int getPersonTypeNum(String personType)
    {
        int personSum = 0;
        for (int i = 0; i < cjrListData.size(); i++)
        {
            //类型一致；
            if (personType.equals(cjrListData.get(i).getPersonTypeEn()))
            {
                personSum++;
            }
        }
        return personSum;
    }

    /**
     * 添加不重复的乘客信息；
     * 如果有重复的，直接更新；
     *
     * @param passengerInfoBean
     */
    private void addPassengerToList(PassengerInfoBean passengerInfoBean)
    {
        if (cjrListData != null)
        {
            boolean hasSame = false;
            //判断是否有相同项；
            PassengerInfoBean cjr;
            for (int i = 0; i < cjrListData.size(); i++)
            {
                cjr = cjrListData.get(i);
                if (!isEmpty(cjr.id) && cjr.id.equals(passengerInfoBean.id))
                {
                    //元素替换；
                    cjrListData.set(i, passengerInfoBean);
                    hasSame = true;
                }
            }
            if (!hasSame)
            {
                cjrListData.add(passengerInfoBean);
                comparePersonNum();
            }
            cjrAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 比较人种的数量；显示或隐藏数量提示文本；
     */
    private void comparePersonNum()
    {
        boolean visible = this.adultNum == getPersonTypeNum(AppConstant.TYPE_ADULT) &&
                this.childNum == getPersonTypeNum(AppConstant.TYPE_CHILD) &&
                this.babyNum == getPersonTypeNum(AppConstant.TYPE_BABY);

        tvPassengerNum.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onGetConnectorSucess(boolean event_tag, Object connectors)
    {
        if (connectors != null)
        {
            Connector connector;
            List<Connector> connectorList = (List<Connector>) connectors;
            //获取默认联系人；
            if (event_tag)
            {
                connector = connectorPresenter.getDefaultConnector(connectorList);
            } else //刷新当前已经选择的联系人（用于编辑修改的处理）；
            {
                connector = connectorPresenter.updateConnector(connectorList, this.connector);
            }
            if (connector != null)
            {
                lxrName.setText(this.connector.nickName);
                lxrMobile.setText(this.connector.mobile);
            }
        }
    }

    @Override
    public void onGetConnectorFail(String failMsg)
    {
        LogUtil.i("获取默认联系人失败==" + failMsg);
    }

    /**
     * 获取联系人；
     *
     * @param event_tag true:获取默认联系人；false：刷新指定id的联系人；
     */

    private void getConnector(boolean event_tag)
    {
        if (!event_tag && this.connector == null)
        {
            return;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("type", "2");
        connectorPresenter.loadData(event_tag, param);
    }


    private void refreshDefaultDebitRise(List<DebitRiseBean> debitRiseBeans)
    {
        if (debitRiseBeans == null) return;
        for (DebitRiseBean bean : debitRiseBeans)
        {
            if (bean.isDefault())
            {
                this.debitRiseBean = bean;
                tvDebitRise.setText(bean.content);
                break;
            }
        }
    }

    /**
     * 刷新默认收货地址；
     *
     * @param
     */
    private void refreshDefaultAddressList(List<GoodsAddressBean> goodsAddressBeans)
    {
        if (goodsAddressBeans != null)
        {
            for (GoodsAddressBean bean : goodsAddressBeans)
            {
                // 找到默认联系人；
                if (bean.isDefault)
                {
                    addressBean = bean;
                    shdzName.setText(bean.contacts);
                    shdzMobile.setText(bean.phone);
                    String city = addressBean.cityName.equals(addressBean.provinceName) ?
                            addressBean.cityName : addressBean.provinceName + addressBean.cityName;
                    shdzAddress.setText(city + addressBean.getDistrictName() + addressBean.address);
                    loadPostageFee(addressBean.getCityId());
                    break;
                }
            }
        }
    }

    /**
     * 获取邮费信息；
     *
     * @cityId 目的地的cityId；
     */
    private void loadPostageFee(String cityId)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("cityId", cityId);
        params.put("pageStart", "0");
        params.put("pageSize", "1");
        postFeePresenter.loadData(params);
    }

    /**
     * 刷新保险列表数据；
     *
     * @param insuranceList
     */
    private void refreshBxList(List<Insurance> insuranceList)
    {
        if (insuranceList != null)
        {
            bxListData.addAll(insuranceList);
            cjrAdapter.notifyDataSetChanged();
            panelInsurance.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onGetPostFeeSucess(Object data)
    {
        if (data != null)
        {
            List<PostFeeBean> postFeeBeans = (List<PostFeeBean>) data;
            if (postFeeBeans != null && postFeeBeans.size() > 0)
            {
                this.postFeeBean = postFeeBeans.get(0);
                tv_post_fee.setText(postFeeBean.getLogisticsName() + "(" + Utils.subZeroAndDot(postFeeBean.getFee() + "") + "元)");
                updateTotalAmount();
            }
        }
    }

    @Override
    public void onGetPostFeeFail(String failMsg)
    {
        LogUtil.i("获取邮费失败-failMsg=" + failMsg);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == IPlaneTicketOrderSingle.BXLIST_CODE) //保险列表
        {
            refreshBxList((List<Insurance>) o);
        } else if (type == IPlaneTicketOrderSingle.DEFAULTADDRESS_CODE) //默认地址
        {
            refreshDefaultAddressList((List<GoodsAddressBean>) o);
        } else if (type == IPlaneTicketOrderSingle.DEFAULTDEBIT_CODE) //发票抬头；
        {
            refreshDefaultDebitRise((List<DebitRiseBean>) o);
        }else if (HotelProductOrderPresenter.QUERYGOODSPLUSC0DE == type) {//超值加购

            List<OverbalanceGoodsBean> list = JSON.parseArray(o + "", OverbalanceGoodsBean.class);

            overbalanceGoodsItemAdapter.clearAll();

            overbalanceGoodsItemAdapter.setList(list);

            if (list == null || list.size() == 0) {

                llPlusGoods.setVisibility(View.GONE);

            } else {

                llPlusGoods.setVisibility(View.VISIBLE);

            }

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }
}
