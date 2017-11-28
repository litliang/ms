package com.yzb.card.king.ui.discount.activity.pay;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.ticket.CustCouponBean;
import com.yzb.card.king.bean.ticket.GoodActivityBean;
import com.yzb.card.king.bean.ticket.OrderIdBean;
import com.yzb.card.king.bean.ticket.ShopGoodsActivityBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.app.base.BasePresenter;
import com.yzb.card.king.ui.app.interfaces.DiscountListener;
import com.yzb.card.king.ui.app.manager.BankDiscountManager;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.credit.activity.PayMethodActivity;
import com.yzb.card.king.ui.discount.bean.DiscountIntegralDetailBean;
import com.yzb.card.king.ui.discount.fragment.pay.CustCouponDialogFragment;
import com.yzb.card.king.ui.discount.fragment.pay.DiscountBonusDialogFragment;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.order.OrderManageActivity;
import com.yzb.card.king.ui.ticket.presenter.ActivityDeductionPresenter;
import com.yzb.card.king.ui.ticket.presenter.DiscountPresenter;
import com.yzb.card.king.ui.ticket.presenter.RePayPresenter;
import com.yzb.card.king.ui.ticket.view.ActivityDeductionView;
import com.yzb.card.king.ui.ticket.view.DiscountView;
import com.yzb.card.king.ui.travel.presenter.impl.FlightBookPImpl;
import com.yzb.card.king.ui.travel.view.FlightBookView;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.wallet.logic.pay.PayRequestLogic;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.PayMethodListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特惠付款
 *
 * @第一次修改 gengqiyun 2016.8.18
 */
public class DiscountPayActivity extends BaseActivity implements View.OnClickListener,
        ActivityDeductionView, DiscountView, FlightBookView {
    private static final int REQ_CODE_INTEGRAL = 0x01;
    private static final int REQ_CODE_GIFT_CARD = 0x02;
    private static final int REQ_GET_PAY_METHOD = 0x03;

    public static final String ARG_MAP = "ARG_MAP";

    public static final String STORE_NAME = "STORE_NAME";
    public static final String STORE_IDS = "STORE_IDS";
    public static final String GOODS_IDS = "GOODS_IDS";
    public static final String ORDER_ID = "ORDER_ID";
    public static final String ORDER_NO = "ORDER_NO";
    public static final String ORDER_AMOUNT = "ORDER_AMOUNT";
    public static final String IS_INPUT = "IS_INPUT";
    public static final String GOODS_CODES = "goodsCodes";
    public static final String NOTIFY_URL = "NOTIFY_URL";
    public static final String INDUSTRY_ID = "INDUSTRY_ID";
    public static final String NEED_RESULT = "need_result";

    public static final String ORDER_TIME = "ORDER_TIME";
    public static final String PAYDETAIL_ID = "payDetailId";
    public static final int REQ_TICKET_MODIFY = 0x100; //机票改签无效返回；
    private static final int REQ_ADD_BANK_CARD = 0x101;
    private TextView tvStoreName;
    //消费总额；扫描进入时可输入；否则：禁止输入；
    private EditText etConsumeTotalAmount;
    private View llNoDiscountAmount; //不参与优惠金额行linearlayout；
    //不参与优惠金额；扫描进入时显示；否则：隐藏；
    private EditText etNoDiscountAmount;//不参与优惠金额输入框；
    private TextView tvBonusAmount;
    private TextView tv_yhq_number; //显示已选中的优惠券数量；
    private TextView tvCouponAmount; //显示选中的优惠券总金额；
    private TextView tvIntegralAmount; //显示积分抵扣的总金额；
    private TextView tvGiftCardAmount;//显示礼品卡总金额；

    private TextView tvStoreDiscountPercent;//商家优惠活动比例；
    private TextView tvPlatformDiscountPercent;//平台优惠活动比例；
    private TextView tvStoreDiscountAmount; //显示商家优惠总金额；

    private TextView tvPayMethodName; //支付方式名；
    private TextView tvBankDiscountAmount;//银行优惠金额文本
    private View llBankDiscount;
    private View llStoreDiscount;

    private ToggleButton toggleNoTakePart;  //不参与优惠金额复选框；
    private ToggleButton toggleStoreCoupon; //商家或平台优惠复选；
    private ToggleButton toggleBankCoupon;//银行优惠复选；

    private TextView tvActualPayAmount; //显示实付金额；
    private View llInputPrefAmount;//不参与优惠金额外层布局；根据isInput的标志显示或隐藏；

    private PayMethod payMethodBean;//付款方式实体；

    private float mNoDiscountAmount = 0.0f;// 不参与优惠金额
    //选择的优惠券列表
    private List<CustCouponBean> mSelectCouponList = new ArrayList<>();
    private float mActualPayAmount = 0.0f;//实付金额；
    /**
     * false: 航空，电影，旅游，陆上交通，机票;
     * true: 美食，奢侈品，酒店;
     */
    private boolean isInput;//是否输入金额；false：否；true：是；
    private String orderId; //订单id；
    private String orderNo; //订单号；
    private String shopIds; //商家id(多个使用英文逗号分割)
    private String mStoreName = "";// 门店名称
    private float mTotalAmount = 0.0f;// 订单总金额,优惠前；
    private HashMap<String, String> argMap;//参数列表；
    private ArrayList<DiscountIntegralDetailBean> integralDetailList; //积分折扣返回；

    private String industryId; //行业id；
    private String orderTime; //订单时间；来自于上个页面；
    private String goodsIds;//商品id(多个使用英文逗号分割)

    private ActivityDeductionPresenter updateOrderStatusPresenter;
    private DiscountPresenter discountPresenter;

    private View panel_shop_discount; // 商家优惠块；
    private View panel_platform_discount;// 平台优惠块；
    private View panel_discount;// 平台优惠块；
    private ImageView iv_discount_logo;

    private static final int SHOP_DISCOUNT = 0; //商家优惠；
    private static final int PLATFORM_DISCOUNT = 1;//平台优惠；
    private boolean needResult = false; // 付款成功后需要返回结果时置true即可； argMap.put(NEED_RESULT,"1");
    private String flightType;// 机票时的航线类型；
    private BasePresenter flightBookPresenter;
    private RePayPresenter repayPresenter; //改签后需要支付接口
    private List<OrderIdBean.OrderIds> orderIdBeans;  //机票改签的订单id列表；
    private String goodsCodes; //商品code码  获取商家优惠使用；
    private String notifyUrl; //回调url；钱包使用；
    private double personBounsBalance;
    private double shopBounsBalance;
    private double platformBounsBalance;
    private PayRequestLogic payHandle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_pay);

        updateOrderStatusPresenter = new ActivityDeductionPresenter(this);
        discountPresenter = new DiscountPresenter(this);
        flightBookPresenter = new FlightBookPImpl(this);
        repayPresenter = new RePayPresenter();
        recvIntentArgs();
        initView();
        initViewData();
        calcStoreAndBankCoupon();
        //商家满减；
        getDiscountActivity(DiscountListener.platform_type_shop, DiscountListener.type_full_reduction,
                DiscountListener.req_platform_type_shop + DiscountListener.req_type_full_reduction);
        //平台满减；
        getDiscountActivity(DiscountListener.platform_type_platform, DiscountListener.type_full_reduction,
                DiscountListener.req_platform_type_platform + DiscountListener.req_type_full_reduction);

        //银行折扣列表；用于付款方式为银行卡时 根据bankId获取相应的折扣；
        getDiscountActivity(DiscountListener.platform_type_bank, DiscountListener.type_bank_discount,
                DiscountListener.req_platform_type_bank + DiscountListener.req_type_bank_discount);
    }

    @Override
    public void onGetDiscountSucess(String req_flag, Object data) {
        if (data == null) {
            return;
        }
        List<ShopGoodsActivityBean> sb = (List<ShopGoodsActivityBean>) data;
        switch (req_flag) {
            //商家满减；
            case DiscountListener.req_platform_type_shop + DiscountListener.req_type_full_reduction:
                updateShopDiscountView(sb, SHOP_DISCOUNT);
                break;
            //平台满减；
            case DiscountListener.req_platform_type_platform + DiscountListener.req_type_full_reduction:
                //会有多个平台满减信息；???????????
                updateShopDiscountView(sb, PLATFORM_DISCOUNT);
                break;
            //商家折扣；
            case DiscountListener.req_platform_type_shop + DiscountListener.req_type_discount:
                //会有多个商家的折扣信息；???????????
                updateRate(sb, SHOP_DISCOUNT);
                break;
            //平台折扣；
            case DiscountListener.req_platform_type_platform + DiscountListener.req_type_discount:
                //会有多个平台折扣信息；???????????
                updateRate(sb, PLATFORM_DISCOUNT);
                break;
            //银行折扣列表；
            case DiscountListener.req_platform_type_bank + DiscountListener.req_type_bank_discount:
                //会有多个银行折扣列表信息；用于付款方式上根据bankId筛选折扣信息；
                BankDiscountManager.getInstance().setShopGoodsActivity(sb);
                break;
        }
    }

    /**
     * 更新商家或平添的折扣信息；
     *
     * @param gb
     * @param discountType
     */
    private void updateRate(List<ShopGoodsActivityBean> gb, int discountType) {
        //有优惠活动；
        if (gb != null && gb.size() > 0) {
            //有多个商家时 会有多个折扣，目前先显示第一个；
            List<GoodActivityBean> gabList = gb.get(0).getGoodActivityBeans();
            if (gabList != null && gabList.size() > 0) {
                // 商家折扣；
                if (SHOP_DISCOUNT == discountType) {
                    //长度为0，取第一个即可；
                    shopRate = gabList.get(0);
                } else {
                    platformRate = gabList.get(0);
                }
                calcStoreAndBankCoupon();
            }
        }
    }

    /**
     * 更新商家和平台满减优惠块；
     *
     * @param gb           商家优惠列表；
     * @param discountType 0:商家优惠；1：平台优惠；
     */
    private void updateShopDiscountView(List<ShopGoodsActivityBean> gb, int discountType) {
        //有优惠活动；
        if (gb != null && gb.size() > 0) {
            LogUtil.i("有满减优惠活动");

            //活动列表；
            //有多个商家时  目前先显示第一个；????????????????  会有多个；
            List<GoodActivityBean> gabList = gb.get(0).getGoodActivityBeans();
            if (gabList != null && gabList.size() > 0) {
                panel_discount.setVisibility(View.VISIBLE);

                List<GoodActivityBean> gabStairCutAmount = new ArrayList<>(); //阶梯减；
                GoodActivityBean empBean;
                GoodActivityBean fullAmount = null;
                GoodActivityBean everyAmount = null;
                //筛选出满减，每满减，阶梯减；
                for (int i = 0; i < gabList.size(); i++) {
                    empBean = gabList.get(i);
                    if (AppConstant.TYPE_FULL_CUT == empBean.getScope()) {
                        fullAmount = empBean;
                        continue;
                    }
                    if (AppConstant.TYPE_EVERY_FULL_CUT == empBean.getScope()) {
                        everyAmount = empBean;
                        continue;
                    }
                    if (AppConstant.TYPE_STAIR_CUT == empBean.getScope()) {
                        gabStairCutAmount.add(empBean);
                    }
                }
                //阶梯减信息；满减信息由小到大排序；
                int[][] stairCut = getStairCut(gabStairCutAmount);
                if (SHOP_DISCOUNT == discountType) {
                    this.shopFullAmount = fullAmount;
                    this.shopEveryCutAmount = everyAmount;
                    shopStairCutAmount = stairCut;
                    hasStoreCoupon = true;
                    panel_shop_discount.setVisibility(View.VISIBLE);
                    //目前仅仅显示每满减；
                    tvStoreDiscountPercent.setText(fullAmount != null ? fullAmount.getActName() : "");
                    iv_discount_logo.setBackgroundResource(R.mipmap.icon_shoping_red);
                } else if (PLATFORM_DISCOUNT == discountType) {
                    this.platformFullAmount = fullAmount;
                    this.platformEveryCutAmount = everyAmount;
                    platformStairCutAmount = stairCut;
                    hasPlatformCoupon = true;
                    panel_platform_discount.setVisibility(View.VISIBLE);
                    iv_discount_logo.setBackgroundResource(R.mipmap.ic_launcher);
                    tvPlatformDiscountPercent.setText(fullAmount != null ? fullAmount.getActName() : "");
                }
                calcStoreAndBankCoupon();
            }
        }
    }

    /**
     * 阶梯减信息；
     *
     * @param dataList 未排序的阶梯减bean列表；
     * @return 按照阶梯减降序排列的满减数组；
     */
    private int[][] getStairCut(List<GoodActivityBean> dataList) {
        if (dataList != null && dataList.size() > 0) {
            GoodActivityBean[] beanArray = new GoodActivityBean[dataList.size()];
            dataList.toArray(beanArray);
            //升序排列；
            Arrays.sort(beanArray, new Comparator<GoodActivityBean>() {
                @Override
                public int compare(GoodActivityBean lhs, GoodActivityBean rhs) {
                    if (lhs.getFullAmount() > rhs.getFullAmount()) {
                        return 1;
                    }
                    return -1;
                }
            });
            //升序后的阶梯减数据；
            int[][] stairArray = new int[beanArray.length][2];
            for (int i = 0; i < stairArray.length; i++) {
                stairArray[i][0] = (int) dataList.get(i).getFullAmount();
                stairArray[i][1] = (int) dataList.get(i).getCutAmount();
            }
            return stairArray;
        }
        return null;
    }

    @Override
    public void onGetDiscountFail(String failMsg) {
        // 获取优惠信息失败；
    }

    /**
     * 获取平台或商家优惠活动；
     *
     * @param platformType 活动平台（1平台商家活动；2平台活动；3商家活动；4银行活动）
     * @param activityItem 活动项（当活动平台为123时，0所有；1优惠券；2红包；3折扣；4满减；5积分；6促销；
     *                     当活动平台为银行时，0所有；1银行积分；2银行折扣；）
     *                     <p/>
     *                     具体参数参考：DiscountListener
     * @param flag         用来标志不同的请求； 具体参数参考：DiscountListener中的请求码，要和platformType和activityItem一一对应；
     */
    private void getDiscountActivity(int platformType, int activityItem, String flag) {
        Map<String, Object> params = new HashMap<>();
        params.put("platformType", platformType + "");
        params.put("activityItem", activityItem + "");
        params.put("shopIds", shopIds);//商家id(多个使用英文逗号分割)  此处为代理商的id
        params.put("goodsIds", goodsIds);//商品id(多个使用英文逗号分割) 此处为航班的id；
        params.put("industryIds", industryId);//行业分类(多个使用英文逗号分割)
        params.put("flag", flag);
        params.put("goodscodes", goodsCodes);//商品code码
        discountPresenter.loadData(params);
    }

    /**
     * 接收Intent参数；
     */
    private void recvIntentArgs() {
        Serializable orderIdsObj = getIntent().getSerializableExtra("orderIdBeans");
        if (orderIdsObj != null) {
            orderIdBeans = (List<OrderIdBean.OrderIds>) orderIdsObj;
        }

        Serializable args = getIntent().getSerializableExtra(ARG_MAP);
        if (args != null && args instanceof HashMap) {
            argMap = (HashMap<String, String>) args;
        } else {
            argMap = new HashMap<>();
        }
        goodsCodes = argMap.get(GOODS_CODES);
        notifyUrl = argMap.get(NOTIFY_URL);

        needResult = "1".equals(argMap.get(NEED_RESULT)) ? true : false;

        flightType = argMap.get("flightType");

        LogUtil.i("接收到的参数列表=" + JSON.toJSONString(argMap));
        //0:不需要手输入；1:需要；
        isInput = "1".equals(argMap.get(IS_INPUT)) ? true : false;
        argMap.remove(IS_INPUT);

        //总金额；
        String mTotalAmountEmp = argMap.get(ORDER_AMOUNT);
        mTotalAmount = isEmpty(mTotalAmountEmp) ? 0.0f : Float.parseFloat(mTotalAmountEmp);

        orderTime = argMap.get(ORDER_TIME);

        industryId = argMap.get(INDUSTRY_ID);
        goodsIds = argMap.get(GOODS_IDS);

        //门店名称；
        mStoreName = argMap.get(STORE_NAME);
        //门店id；
        shopIds = argMap.get(STORE_IDS);
        //订单id；
        orderId = argMap.get(ORDER_ID);
        //订单号；
        orderNo = argMap.get(ORDER_NO);
    }

    /**
     * 初始化view相关数据；
     */
    private void initViewData() {
        tvStoreName.setText(mStoreName);

        //消费总金额，优惠前；
        etConsumeTotalAmount.setText("¥" + Utils.subZeroAndDot(mTotalAmount + ""));
        etConsumeTotalAmount.setFocusable(isInput ? true : false);

        llInputPrefAmount.setVisibility(isInput ? View.VISIBLE : View.GONE);
        updateCouponNumberAndAmount();
        toggleNoTakePart.setChecked(true);
    }

    private void initView() {
        setHeader(R.mipmap.icon_back_white, getString(R.string.common_preference_payment));
        findViewById(R.id.headerLeft).setOnClickListener(this);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, 20);

        initDiscountTopView();
        initCenterCouponView();
        // 商家优惠比例；
        tvStoreDiscountPercent = (TextView) findViewById(R.id.tvStoreDiscountPercent);
        panel_discount = findViewById(R.id.panel_discount);
        panel_discount.setVisibility(View.GONE);

        panel_shop_discount = findViewById(R.id.panel_shop_discount);
        panel_shop_discount.setVisibility(View.INVISIBLE);

        panel_platform_discount = findViewById(R.id.panel_platform_discount);
        panel_platform_discount.setVisibility(View.INVISIBLE);

        iv_discount_logo = (ImageView) findViewById(R.id.iv_discount_logo);
        // 平台优惠比例；
        tvPlatformDiscountPercent = (TextView) findViewById(R.id.tvPlatformDiscountPercent);

        initPayMethodView();
        initToggleCouponView();
        // 实付金额
        tvActualPayAmount = (TextView) findViewById(R.id.tvActualPayAmount);
        // 确认付款
        findViewById(R.id.paySubmit).setOnClickListener(this);
    }

    /**
     * 初始化商家优惠和银行优惠复选模块；
     */
    private void initToggleCouponView() {
        //商家优惠切换；
        toggleStoreCoupon = (ToggleButton) findViewById(R.id.toggle_store_coupon);
        toggleStoreCoupon.setOnCheckedChangeListener(checkedChangeListener);
        //商家优惠总金额；
        tvStoreDiscountAmount = (TextView) findViewById(R.id.tvStoreDiscountAmount);
        //底部银行优惠总金额块
        llStoreDiscount = findViewById(R.id.llStoreDiscount);
        llStoreDiscount.setOnClickListener(this);

        //银行优惠切换；
        toggleBankCoupon = (ToggleButton) findViewById(R.id.toggle_bank_coupon);
        toggleBankCoupon.setOnCheckedChangeListener(checkedChangeListener);

        toggleBankCoupon.setChecked(false);
        toggleStoreCoupon.setChecked(false);
        // 银行优惠金额文本；
        tvBankDiscountAmount = (TextView) findViewById(R.id.tvBankDiscountAmount);
        //底部银行优惠总金额块
        llBankDiscount = findViewById(R.id.llBankDiscount);
        llBankDiscount.setOnClickListener(this);
    }

    /**
     * 初始化付款方式view；
     */
    private void initPayMethodView() {
        // 付款方式
        findViewById(R.id.llPayMethod).setOnClickListener(this);
        tvPayMethodName = (TextView) findViewById(R.id.tvPayMethodName);
        tvPayMethodName.setText("点击选择");
    }

    /**
     * 更新付款方式view；
     *
     * @param payMethod 用户选择的付款方式bean；
     */
    private void updatePayMethodView(PayMethod payMethod) {
        //账户类型（0银行卡 1余额账户 2红包账户 3、礼品卡账户）
        tvPayMethodName.setText(!isEmpty(payMethod.getBankName()) ? payMethod.getBankName() : payMethod.getTypeName());

        switch (payMethod.getAccountType()) {
            case AppConstant.ACCOUNT_TYPE_BALANCE: //账户余额
                llBankDiscount.setVisibility(View.GONE);
                break;
            case AppConstant.ACCOUNT_TYPE_BANK: //0银行卡
                //取得优惠信息；
                String discountStr = BankDiscountManager.getInstance().getConcatRateByBankId(payMethod.getBankId());
                // 如果没有优惠，隐藏银行折扣区域；
                llBankDiscount.setVisibility(TextUtils.isEmpty(discountStr) ? View.GONE : View.VISIBLE);
                break;
        }
    }

    /**
     * 初始化 优惠券，积分抵扣，红包，礼品卡view，
     */
    private void initCenterCouponView() {
        //优惠券
        findViewById(R.id.llCoupon).setOnClickListener(this);

        tvCouponAmount = (TextView) findViewById(R.id.tvCouponAmount);
        tvCouponAmount.setText("");

        tv_yhq_number = (TextView) findViewById(R.id.tv_coupon_number);
        tv_yhq_number.setText(getString(R.string.pay_yhq_with_number, "0"));

        // 积分抵扣
        findViewById(R.id.llIntegral).setOnClickListener(this);
        tvIntegralAmount = (TextView) findViewById(R.id.tvIntegralAmount);
        // 红包
        findViewById(R.id.llBonus).setOnClickListener(this);
        tvBonusAmount = (TextView) findViewById(R.id.tvBonusAmount);
        // 礼品卡
        findViewById(R.id.llGiftCard).setOnClickListener(this);
        tvGiftCardAmount = (TextView) findViewById(R.id.tvGiftCardAmount);
    }

    /**
     * 初始化顶部区域view；
     */
    private void initDiscountTopView() {
        //不参与优惠金额块
        llNoDiscountAmount = findViewById(R.id.ll_noDiscountAmount);

        //门店名称
        tvStoreName = (TextView) findViewById(R.id.tvStoreName);
        // 消费总额
        etConsumeTotalAmount = (EditText) findViewById(R.id.etAmount);
        //etConsumeTotalAmount.addTextChangedListener(consumeTotalAmountWatcher);
        //先禁止；
        etConsumeTotalAmount.setEnabled(false);

        //不参与优惠金额最外层；
        llInputPrefAmount = findViewById(R.id.ll_input_pref_amount);
        llInputPrefAmount.setOnClickListener(this);

        //不参与优惠金额选择复选框；
        toggleNoTakePart = (ToggleButton) findViewById(R.id.toggle_noTakePart);
        toggleNoTakePart.setOnCheckedChangeListener(checkedChangeListener);
        findViewById(R.id.tv_toggle_notake_text).setOnClickListener(this);
        //输入框；
        etNoDiscountAmount = (EditText) findViewById(R.id.etNoDiscountAmount);
        etNoDiscountAmount.addTextChangedListener(noTakePartAmountWatcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQ_CODE_INTEGRAL: // 积分返回
                if (resultCode == Activity.RESULT_OK) {
                    Serializable obj = data.getSerializableExtra(DiscountPayIntegralActivity.USER_INTEGRAL_REUSLT);
                    integralDetailList = (obj != null ? (ArrayList<DiscountIntegralDetailBean>) obj : null);
                    updateIntegralView();
                }
                break;
            case REQ_CODE_GIFT_CARD:// 礼品卡返回
                float giftCardAmount = data.getFloatExtra("giftcardBackAmount", 0);
                giftCardAmount = giftCardAmount > 0 ? giftCardAmount : 0;
                L = giftCardAmount;//礼品卡优惠金额
                tvGiftCardAmount.setText(L > 0 ? ("-" + Utils.subZeroAndDot(L + "")) : "");
                //计算商家优惠
                calcStoreAndBankCoupon();
                break;
            case REQ_GET_PAY_METHOD: //付款方式
                Serializable objEmp = data.getSerializableExtra("payMethodData");
                if (objEmp != null) {
                    payMethodBean = (PayMethod) objEmp;
                    updatePayMethodView(payMethodBean);
                    //计算优惠
                    calcStoreAndBankCoupon();
                }
                break;
            case REQ_ADD_BANK_CARD:
                if (payHandle != null) {
                    payHandle.showAddCardSuccess();
                }
                break;
        }
    }

    /**
     * 更新积分view数据；
     */
    private void updateIntegralView() {
        float totalIntegral = getTotalIntegralAmount();
        if (totalIntegral > mTotalAmount) {
            integralDetailList = null;
            toastCustom("积分兑换总金额不能大于订单金额");
            return;
        }
        if (integralDetailList == null) {
            return;
        }
        //=================此处需要区分判断商户，平台，银行各种类的积分，然后赋值；=======================
        DiscountIntegralDetailBean integralDetailBean;
        for (int i = 0; i < integralDetailList.size(); i++) {
            integralDetailBean = integralDetailList.get(i);
            //平台积分；
            if (AppConstant.INTEGRAL_ACCOUNT_PLATFORM.equals(integralDetailBean.getAccountType())) {
                PJ = integralDetailBean.getExchangeMoneyNum();
                PJIntegral = (int) integralDetailBean.getExchangeIntegralNum();
            } else if (AppConstant.INTEGRAL_ACCOUNT_SHOP.equals(integralDetailBean.getAccountType())) {
                MJ = integralDetailBean.getExchangeMoneyNum();
                MJIntegral = (int) integralDetailBean.getExchangeIntegralNum();
            } else {
                IJ += integralDetailBean.getExchangeMoneyNum();
                IJIntegral += integralDetailBean.getExchangeIntegralNum();
            }
        }
        LogUtil.i("商户积分=" + MJIntegral + ",平台积分=" + PJIntegral + ",银行积分=" + IJIntegral);
        LogUtil.i("商户金额=" + MJ + ",平台金额=" + PJ + ",银行金额=" + IJ);
        tvIntegralAmount.setText(totalIntegral <= 0 ? "" : ("-" + Utils.subZeroAndDot(totalIntegral + "")));
        calcStoreAndBankCoupon();
    }

    /**
     * 获取积分兑换的总金额；
     *
     * @return
     */
    private float getTotalIntegralAmount() {
        if (integralDetailList != null) {
            float totalIntegralAmount = 0f;
            LogUtil.i("积分明细列表==" + JSON.toJSONString(integralDetailList));
            for (int i = 0; i < integralDetailList.size(); i++) {
                totalIntegralAmount += integralDetailList.get(i).getExchangeMoneyNum();
            }
            return totalIntegralAmount;
        }
        return 0;
    }

    private boolean hasStoreCoupon = false; //是否有商家优惠；
    private boolean hasPlatformCoupon = false; //是否有平台优惠；
    private boolean isStoreCouponSelect = false; //底部商户优惠选中状态；
    private boolean isBankCouponSelect = false; //底部银行优惠选中状态；

    private float MY = 0;//商家优惠券优惠金额  优惠券dialog返回；
    private float PY = 0;//平台优惠券优惠金额
    private float H = 0;//红包优惠金额
    private float L = 0;//礼品卡优惠金额

    private float MJ = 0;//商户积分兑换金额
    private float PJ = 0;//平台积分兑换金额
    private float IJ = 0;//银行积分兑换金额

    private int MJIntegral = 0;//兑换商户积分
    private int PJIntegral = 0;//兑换平台积分
    private int IJIntegral = 0;//兑换银行积分

    /**
     * 商家或平台优惠类型；
     * 应用范围（1满减;2每满减;3阶梯减;）
     * 折扣 DTP(折扣比例)
     * <p/>
     * 1:满减:例如，满100（CA）减5（DA）
     * 2:每满减:例如，每满200（CA）减5（DA）
     * 3:阶梯减:例如，满100（CA0）减5（DA0）;满200（CA1）减10（DA1）;……；满100（CAn）减5（DAn）
     */
    GoodActivityBean shopRate = null; //商家折扣；
    GoodActivityBean platformRate = null; //平台折扣；
    GoodActivityBean shopFullAmount = null; //商家满减；
    GoodActivityBean shopEveryCutAmount = null; //商家每满减；

    GoodActivityBean platformFullAmount = null; //平台满减；
    GoodActivityBean platformEveryCutAmount = null; //平台每满减；
    private int[][] shopStairCutAmount = null;//商家阶梯减数组；满减金额应由小到大排序；
    private int[][] platformStairCutAmount = null;//平台阶梯减数组；满减金额应由小到大排序；

    float SP = 0.0f; //商家总优惠金额；
    float PP = 0.0f;  //平台总优惠金额；
    float T = mTotalAmount; //总金额(优惠前)；
    float N = mNoDiscountAmount; //不参与优惠金额；
    float PPJ = T - N; //可参与优惠金额

    float SPTD = 0.0f;//可享受商户或平台优惠算法的总额(PPJ-优惠券优惠金额-积分兑换金额);
    float Y = MY + PY;//优惠券总优惠金额(商户优惠券优惠金额+平台优惠券优惠金额);
    float J = MJ + PJ + IJ;//积分总优惠金额(商户积分优惠金额MJ+平台积分优惠金额PJ+银行积分优惠金额IJ);

    /**
     * 计算商家和银行的优惠总金额；
     */
    private void calcStoreAndBankCoupon() {
        //判断输入总金额是否>0;
        if (mTotalAmount <= 0) {
            tvStoreDiscountAmount.setText("-0元");
            tvBankDiscountAmount.setText("-0元");

            llStoreDiscount.setVisibility(View.GONE);
            llBankDiscount.setVisibility(View.GONE);
            return;
        }
        SP = 0.0f; //商家总优惠金额；
        PP = 0.0f;  //平台总优惠金额；
        T = mTotalAmount; //总金额(优惠前)；
        N = mNoDiscountAmount; //不参与优惠金额；
        PPJ = T - N; //可参与优惠金额

        SPTD = 0.0f;//可享受商户或平台优惠算法的总额(PPJ-优惠券优惠金额-积分兑换金额);
        Y = MY + PY;//优惠券总优惠金额(商户优惠券优惠金额+平台优惠券优惠金额);
        J = MJ + PJ + IJ;//积分总优惠金额(商户积分优惠金额MJ+平台积分优惠金额PJ+银行积分优惠金额IJ);

        /*------------计算商家或平台优惠金额start------------*/

        // 说明：商户优惠 和平台优惠 目前不共存；hasStoreCoupon和hasPlatformCoupon最多有一个为true；
        //有商户优惠
        if (hasStoreCoupon) {
            SPTD = PPJ - MY - MJ;
        } else if (hasPlatformCoupon) //有平台优惠
        {
            SPTD = PPJ - PY - PJ;
        }

        //有商户优惠=================参考大话设计模式第31页，使用策略模式实现=================
        if (hasStoreCoupon) {
            //商户折扣与满减不共存；
            if (shopRate != null && shopRate.getRate() > 0) {
                SP = SPTD * (1 - shopRate.getRate() / 100.0f);
            } else {
                if (shopFullAmount != null && shopFullAmount.getFullAmount() > 0) //存在满减；
                {
                    if (SPTD >= shopFullAmount.getFullAmount()) {
                        SP = shopFullAmount.getCutAmount();
                    }
                } else if (shopEveryCutAmount != null && shopEveryCutAmount.getFullAmount() > 0) //每满减,大于最低优惠值才可以优惠；
                {
                    if (SPTD >= shopEveryCutAmount.getFullAmount()) {
                        SP = (SPTD / shopEveryCutAmount.getFullAmount()) * shopEveryCutAmount.getCutAmount();
                    }
                } else if (shopStairCutAmount != null && shopStairCutAmount.length > 0) //阶梯减
                {
                    //从尾到头比较；
                    for (int i = shopStairCutAmount.length - 1; i >= 0; i--) {
                        //大于临界值；
                        if (SPTD >= shopStairCutAmount[i][0]) {
                            SP = shopStairCutAmount[i][1];
                            break;
                        }
                    }
                }
            }
        } else if (hasPlatformCoupon) {
            //商户折扣与满减不共存；
            if (platformRate != null && platformRate.getRate() > 0) {
                PP = SPTD * (1 - platformRate.getRate() / 100.0f);
            } else {
                if (platformFullAmount != null && platformFullAmount.getFullAmount() > 0) //存在满减；
                {
                    if (SPTD >= platformFullAmount.getFullAmount()) {
                        PP = platformFullAmount.getCutAmount();
                    }
                } else if (platformEveryCutAmount != null && platformEveryCutAmount.getFullAmount() > 0) //每满减,大于最低优惠值才可以优惠；
                {
                    if (SPTD >= platformEveryCutAmount.getFullAmount()) {
                        PP = (SPTD / platformEveryCutAmount.getFullAmount()) * platformEveryCutAmount.getCutAmount();
                    }
                } else if (platformStairCutAmount != null && platformStairCutAmount.length > 0) //阶梯减
                {
                    //从尾到头比较；
                    for (int i = platformStairCutAmount.length - 1; i >= 0; i--) {
                        //大于临界值；
                        if (SPTD >= platformStairCutAmount[i][0]) {
                            PP = platformStairCutAmount[i][1];
                            break;
                        }
                    }
                }
            }
        }
        tvStoreDiscountAmount.setText("-" + Utils.subZeroAndDot((PP + SP) + "元"));
        llStoreDiscount.setVisibility((PP + SP) > 0 ? View.VISIBLE : View.GONE);

        /*------------计算商家或平台优惠金额end------------*/

 /*------------计算底部实付金额start--------------*/
        float BYT; //可享受银行优惠总金额
        //银行优惠比例(符合折扣条件才享有此比例：满100（CFTA）元享受9.7折（BYH）) 从付款方式payMethodBean中取得；
        float BYH = 1f; //默认是1；不打折；
        float CFTA = 0f; //银行满100（CFTA）元享受9.7折
        if (payMethodBean != null) {
            //银行满减；
            CFTA = BankDiscountManager.getInstance().getBankFullAmount(payMethodBean.getBankId());
            //银行打折；
            BYH = BankDiscountManager.getInstance().getRateByBankId(payMethodBean.getBankId());
        }
        float BTYJ = 0;//银行总优惠金额

        //底部商家选中,银行未选中；
        if (!isStoreCouponSelect && isBankCouponSelect)//底部商家未选中,银行选中；
        {
            BYT = T - Y - J - H - L; //可享受银行优惠总金额  ？？？？？
            if (BYT >= CFTA) {
                BTYJ = BYT * (1 - BYH);
            }
        } else if (isStoreCouponSelect && isBankCouponSelect) //底部商家银行都选中；
        {
            BYT = 0.0f; //可享受银行优惠总金额
            BTYJ = 0.0f;//银行总优惠金额
            /*---a、只有商户优惠比例---*/  // 此处不共存；
            if (hasStoreCoupon) {
                BYT = T - Y - J - H - L - SP;
            }
            /*---b、只有平台优惠比例---*/
            else if (hasPlatformCoupon) {
                BYT = T - Y - J - H - L - PP;
            }
            if (BYT >= CFTA) {
                BTYJ = BYT * (1 - BYH);
            }
        }

        tvBankDiscountAmount.setText("-" + Utils.subZeroAndDot(BTYJ + "") + "元");
        llBankDiscount.setVisibility(BTYJ > 0 ? View.VISIBLE : View.GONE);

        mActualPayAmount = T - Y - J - H - L;

        //加上商家和平台优惠；
        if (isStoreCouponSelect) {
            mActualPayAmount -= SP + PP;
        }
        //加上银行优惠；
        if (isBankCouponSelect) {
            mActualPayAmount -= BTYJ;
        }

        tvActualPayAmount.setText(Utils.subZeroAndDot(mActualPayAmount + ""));

 /*------------计算底部实付金额end------------*/
    }

    /**
     * toggle切换监听；
     */
    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.toggle_noTakePart:  //不参与优惠金额选择
                    llNoDiscountAmount.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                    //
                    String noDiscountAmount = etNoDiscountAmount.getText().toString();
                    mNoDiscountAmount = isEmpty(noDiscountAmount) ? 0 : Float.parseFloat(noDiscountAmount);
                    calcStoreAndBankCoupon();
                    break;
                case R.id.toggle_store_coupon:  //底部商家优惠复选框；
                    isStoreCouponSelect = isChecked;
                    calcStoreAndBankCoupon();
                    break;
                case R.id.toggle_bank_coupon:  //底部银行优惠复选框；
                    isBankCouponSelect = isChecked;
                    calcStoreAndBankCoupon();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCoupon://优惠券
                showCouponDialog();
                break;
            case R.id.llIntegral://积分
                Intent intent = new Intent(this, DiscountPayIntegralActivity.class);
                intent.putExtra(DiscountPayIntegralActivity.INTEGRA_LDETAI_LIST, integralDetailList);
                intent.putExtra("shopIds", shopIds);
                startActivityForResult(intent, REQ_CODE_INTEGRAL);
                break;
            case R.id.llBonus://红包
                showBonusDialog();
                break;
            case R.id.llGiftCard://礼品卡
                Intent giftIntent = new Intent(this, DiscountPayGiftCardActivity.class);
                giftIntent.putExtra("useAmount", L);
                giftIntent.putExtra("orderAmount", mTotalAmount);
                startActivityForResult(giftIntent, REQ_CODE_GIFT_CARD);
                break;
            case R.id.llPayMethod://付款方式
                Intent payMethodIntent = new Intent(this, PayMethodActivity.class);
                payMethodIntent.putExtra("titleName", getString(R.string.pay_fkfs));
                startActivityForResult(payMethodIntent, REQ_GET_PAY_METHOD);
                break;
            case R.id.paySubmit://确认付款
                if (isInputRight()) {
                    updateActivityDeduction();
                }
                break;
            case R.id.tv_toggle_notake_text:
                toggleNoTakePart.setChecked(!toggleNoTakePart.isChecked());
                break;
            case R.id.headerLeft:
                setResult(REQ_TICKET_MODIFY);
                finish();
                break;
            case R.id.llStoreDiscount: //底部商户优惠总额；
                toggleStoreCoupon.setChecked(!toggleStoreCoupon.isChecked());
                break;
            case R.id.llBankDiscount://底部银行优惠总额；
                toggleBankCoupon.setChecked(!toggleBankCoupon.isChecked());
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(REQ_TICKET_MODIFY);
    }

    /**
     * 红包dialog；
     */
    private void showBonusDialog() {
        DiscountBonusDialogFragment.getInstance()
                .setOrderAmount(mTotalAmount)
                .setShopId(shopIds)
                .setUseAmount(H).setKeyBoardListener(new DiscountBonusDialogFragment.KeyBoardListener() {
            @Override
            public void getBonusAmount(double shopBalance, double personBalance, double platformBalance) {
                LogUtil.i("shopBalance=" + shopBalance + ",personBalance=" + personBalance + ",platformBalance=" + platformBalance);

                shopBounsBalance = shopBalance;
                personBounsBalance = personBalance;
                platformBounsBalance = platformBalance;
                H = Float.parseFloat((shopBalance + personBalance + platformBalance) + ""); //红包优惠金额

                tvBonusAmount.setText(H > 0 ? "-" + Utils.subZeroAndDot(H + "") : "");
                calcStoreAndBankCoupon();
            }
        }).show(getSupportFragmentManager(), "");
    }

    /**
     * 优惠券dialog；
     */
    private void showCouponDialog() {
        CustCouponDialogFragment.getInstance().setUsedCouponList(mSelectCouponList).
                setShopId(shopIds).
                setIndustryId(industryId)
                .setGoodsId(goodsIds)
                .setGoodsCode(goodsCodes)
                .setOrderAmount(mTotalAmount).
                setListener(new CustCouponDialogFragment.ItemListener() {
                    @Override
                    public void getCouponInfo(CustCouponBean yhqBean, int opType) {
                        switch (opType) {
                            case CustCouponDialogFragment.OP_ADD_COUPON://使用；
                                mSelectCouponList.add(yhqBean);
                                break;
                            case CustCouponDialogFragment.OP_DEL_COUPON: //取消使用;
                                String actId;
                                for (int i = 0; i < mSelectCouponList.size(); i++) {
                                    actId = yhqBean.getActId();
                                    if (!isEmpty(actId) && actId.equals(mSelectCouponList.get(i).getActId())) {
                                        mSelectCouponList.remove(i);
                                        break;
                                    }
                                }
                                break;
                        }
                        updateCouponView();
                    }
                }).show(getSupportFragmentManager(), "");
    }

    /**
     * 用户选择优惠券后，更新相应的变量和view的值；
     */
    private void updateCouponView() {
        MY = 0.0f;//商家优惠券优惠金额
        PY = 0.0f;//平台优惠券优惠金额
        if (mSelectCouponList.size() > 0) {
            CustCouponBean yhqBean;
            for (int i = 0; i < mSelectCouponList.size(); i++) {
                yhqBean = mSelectCouponList.get(i);
                //2：平台优惠券；3：商家优惠券；
                if ("2".equals(yhqBean.getPlatformType())) {
                    //累积商家优惠券总额；
                    PY += yhqBean.getCutAmount();
                } else if ("3".equals(yhqBean.getPlatformType())) {
                    //累积平台优惠券总额；
                    MY += yhqBean.getCutAmount();
                }
            }
        }
        updateCouponNumberAndAmount();
        tvCouponAmount.setText((MY + PY) <= 0 ? "" : "-" + Utils.subZeroAndDot((MY + PY) + ""));
        // 计算商家优惠
        calcStoreAndBankCoupon();
    }

    public void onPaySucess() {
        LogUtil.i("======特惠付款成功");

        //机票改签
        if (orderIdBeans != null && orderIdBeans.size() > 0) {
            Map<String, Object> argMap = new HashMap<>();
            argMap.put("orderIdList", JSON.toJSONString(orderIdBeans));
            argMap.put("orderIds", this.argMap.get(ORDER_ID));  //大订单id；
            repayPresenter.loadData(true, argMap);
        } else
            //机票购票需要调用
            if (AppConstant.ticket_id.equals(industryId)) {
                Map<String, Object> argMap = new HashMap<>();
                argMap.put("orderId", this.orderId);
                argMap.put("flightType", flightType); // 航班类型（单程：OW；往返：RT；多段：MT）
                // argMap.put("targetActivity", "");  //消息推送需求不确定，待定中......
                flightBookPresenter.loadData(true, argMap);
            }

        closePDialog();
        toastCustom("支付成功");

        // 无需返回结果时，跳转机票首页面；
        if (!needResult) {
            Intent intent = new Intent();
            Class claz = ChannelMainActivity.class;
            int pageType = 0;
            if (AppConstant.hotel_id.equals(argMap.get(INDUSTRY_ID))) {
                pageType = 4;
            } else if (AppConstant.ticket_id.equals(argMap.get(INDUSTRY_ID))) {
                pageType = 3;
                claz = OrderManageActivity.class;
                intent.putExtra("orderType", OrderBean.ORDER_TYPE_AIRCRAFT);
            } else if (AppConstant.travel_id.equals(argMap.get(INDUSTRY_ID))) {
                pageType = 5;
            }
            intent.setClass(this, claz);
            intent.putExtra("page", pageType);
            startActivity(intent);
        } else {
            setResult(AppConstant.RES_BACK);
        }
        finish();
    }

    public void onPayFail(String failMsg) {
        closePDialog();
        toastCustom(failMsg);
    }

    /**
     * 特惠付款
     * 说明： 除现金账户和银行卡快捷支付，此接口不允许其他方式付款
     */
    private void exePay() {
        payHandle = new PayRequestLogic(this);
        payHandle.showEnvelopPay(false);
        payHandle.showGiftPay(false);
        payHandle.payMethodCallBack(new PayMethodListener() {
            @Override
            public void callBack(Map<String, String> map) {

            }
        });
        //添加卡；
        payHandle.setAddCardCallBack(new AddCardBackListener() {
            @Override
            public void callBack() {
                startActivityForResult(new Intent(DiscountPayActivity.this, AddBankCardActivity.class), REQ_ADD_BANK_CARD);
            }
        });

        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE) {
                onPaySucess();
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                LogUtil.i("=返回结果=>code" + RESULT_CODE + "返回数据=>" + resultMap);
                if(RESULT_CODE.equals( com.yzb.wallet.util.WalletConstant.PAY_TYPE_OFF)){// 支付卡信息不全

                    String str = JSON.toJSONString(resultMap);

                    PayMethod accountInfo = JSON.parseObject(str , PayMethod.class);

                    LogUtil.e("ABCD","---setSuccess----"+resultMap+"-------"+accountInfo.getCardType());
                    int cardType = accountInfo.getCardType();

                    Class claz = null;

                    if(cardType==1){// 储蓄卡

                        claz = AddBankCardActivity.class;

                    }else if(cardType ==2){//信用卡

                        claz = AddCanPayCardActivity.class;

                    }
                    Intent intent = new Intent(DiscountPayActivity.this, claz);
                    intent.putExtra("cardNo",accountInfo.getCardNo());
                    intent.putExtra("name", accountInfo.getName());
                    startActivity(intent);

                }else{

                    onPaySucess();

                }

            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG) {
                LogUtil.i("付款失败；RESULT_CODE=" + RESULT_CODE + ",ERROR_MSG=" + ERROR_MSG);
                onPayFail(ERROR_MSG);
            }
        });
        payHandle.pay(getInputMap(), false);
    }

    private Map<String, String> getInputMap() {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", getUserBean().getAmountAccount());
        params.put("accountType", payMethodBean.getAccountType());

        params.put("payMethodName", !isEmpty(payMethodBean.getBankName()) ? payMethodBean.getBankName() :
                payMethodBean.getTypeName());//支付方式名称

        params.put("sortCode", payMethodBean.getSortCode());//银行卡关联码（快捷支付） N
        params.put("orderNo", orderNo);
        params.put("orderTime", orderTime);
        params.put("leftTime", AppConstant.leftTime); //超时时间
        params.put("goodsName", mStoreName); //商品名称

        params.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP
        params.put("notifyUrl", notifyUrl);
        params.put("merchantNo", WalletConstant.MERCHANT_NO);//商户号
        params.put("sign", AppConstant.sign);//签名

        params.put("amount", String.format("%.2f", mActualPayAmount)); //订单金额；  必须要是0.00格式的数据；
        params.put("amountEnvelop", personBounsBalance <= 0f ? "" : personBounsBalance + ""); //个人红包金额
        params.put("amountEnvelopPt", platformBounsBalance <= 0f ? "" : platformBounsBalance + ""); //平台红包金额
        params.put("amountGift", L <= 0f ? "" : L + ""); //礼品卡
        params.put("amountIntegral", PJIntegral <= 0f ? "" : PJIntegral + ""); //平台积分金额

        LogUtil.i("付款入参==" + JSON.toJSONString(params));
        return params;
    }


    @Override
    public void onFlightBookSucess() {
        LogUtil.i("成功");
    }

    @Override
    public void onFlightBookFail(String failMsg) {
        LogUtil.i(failMsg);
    }

    @Override
    public void onUpdateOrderStatusSucess(String msg) {
        closePDialog();
        LogUtil.i("抵扣成功=" + msg);
        exePay();
    }

    @Override
    public void onUpdateOrderStatusFail(String failMsg) {
        closePDialog();
        toastCustom(failMsg);
    }

    /**
     * 更新活动抵扣金额
     */
    private void updateActivityDeduction() {
        showPDialog(R.string.loading);
        Map<String, Object> params = new HashMap<>();
        params.put("refundType", AppConstant.refund_type_way_return); //退款方式（1：平台钱包，2：原路退回）
        //付款类型；
        int payType = AppConstant.ACCOUNT_TYPE_BALANCE.equals(payMethodBean.getAccountType()) ? 1 :
                (payMethodBean.getCardType() == 2 ? 2 : 3);

        if (payType != 1) {
            params.put("sortCode", payMethodBean.getSortCode());
        }
        params.put("payType", payType + ""); //付款方式(1:钱包余额，2：信用卡，3：储蓄卡)
        params.put("payDetailId", getpayDetailId()); //付款方式明细id
        params.put("giftcardAmount", L > 0 ? (L + "") : ""); //礼品卡抵扣金额

        params.put("counponIds", getCounponIds()); //优惠券id(多个使用英文逗号分割)
        params.put("bankPoint", IJIntegral > 0 ? (IJIntegral + "") : "");//花费银行积分
        params.put("bankPointAmount", IJ > 0 ? (IJ + "") : "");//银行积分抵扣金额

        params.put("activityName", mStoreName);//活动名称
        params.put("activityType", industryId);//活动类型；

        params.put("shopId", shopIds);
        params.put("goodsId", goodsIds);
        params.put("goodscode", goodsCodes);

        params.put("shopPoint", MJIntegral > 0 ? (MJIntegral + "") : "");//花费商家积分
        params.put("bankPointNo", "");//银行积分支付流水号
        params.put("shopPointAmount", MJ > 0 ? (MJ + "") : "");//商家积分抵扣金额
        params.put("platformPoint", PJIntegral > 0 ? (PJIntegral + "") : "");//花费平台积分
        params.put("platformPointAmount", PJ > 0 ? (PJ + "") : "");//平台积分抵扣金额

        params.put("account", getUserBean().getAccount());
        params.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP
        params.put("itemIds", getItemIds());//优惠券明细id
        params.put("counponAmounts", getCounponAmounts());//优惠券抵扣金额,多个使用英文逗号分割

        params.put("personBonusAmount", personBounsBalance <= 0f ? "" : personBounsBalance + "");//个人红包抵扣金额
        params.put("platformBonusAmount", platformBounsBalance <= 0f ? "" : platformBounsBalance + "");//平台红包抵扣金额
        params.put("shopBonusAmount", shopBounsBalance <= 0f ? "" : shopBounsBalance + "");//商家红包抵扣金额

        params.put("discountId", getDiscountActivityId());//折扣活动id

        params.put("discountAmount", getDiscountActivityAmount());//折扣活动抵扣金额

        params.put("fullcutId", getFullCutId());//满减活动id
        params.put("fullcutAmount", getFullCutAmount()); //满减活动抵扣金额
        params.put("orderId", orderId);//订单id；
        params.put("orderAmount", mActualPayAmount <= 0f ? "" : mActualPayAmount + "");//订单金额；
        updateOrderStatusPresenter.loadData(params);
    }

    /**
     * 优惠券抵扣金额列表；多个以逗号分割；
     *
     * @return
     */
    private Object getCounponAmounts() {
        if (mSelectCouponList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (CustCouponBean bean : mSelectCouponList) {
                sb.append(bean.getCutAmount() + ",");
            }
            return sb.toString().substring(0, sb.length() - 1);
        }
        return "";
    }

    /**
     * 优惠券明细id列表；多个以逗号分割；
     *
     * @return
     */
    private String getItemIds() {
        if (mSelectCouponList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (CustCouponBean bean : mSelectCouponList) {
                sb.append(bean.getItemId() + ",");
            }
            return sb.toString().substring(0, sb.length() - 1);
        }
        return "";
    }

    /**
     * 获取付款方式明细id
     */
    private String getpayDetailId() {
        if (argMap != null) {
            return argMap.get(PAYDETAIL_ID);
        }
        return "";
    }

    /**
     * 满减活动抵扣金额
     */
    private String getFullCutAmount() {
        float cutAmount = 0f;
        if (shopFullAmount != null && SPTD >= shopFullAmount.getFullAmount() &&
                isStoreCouponSelect) {
            cutAmount = shopFullAmount.getCutAmount();
        }

        if (platformFullAmount != null && SPTD >= platformFullAmount.getFullAmount() &&
                isStoreCouponSelect) {
            cutAmount = platformFullAmount.getCutAmount();
        }
        return cutAmount > 0 ? cutAmount + "" : "";
    }

    /**
     * 获取满减活动的id；
     */
    private String getFullCutId() {
        if (shopFullAmount != null && isStoreCouponSelect) {
            return shopFullAmount.getActId();
        }
        if (platformFullAmount != null && isStoreCouponSelect) {
            return platformFullAmount.getActId();
        }
        return "";
    }

    /**
     * 获取折扣活动抵扣金额；
     */
    private String getDiscountActivityAmount() {
        float amount = 0f;
        if (shopRate != null) {
            amount = SPTD * (1 - shopRate.getRate() / 100.0f);
        }
        if (platformRate != null) {
            amount = SPTD * (1 - platformRate.getRate() / 100.0f);
        }
        return amount > 0 ? amount + "" : "";
    }

    /**
     * 获取折扣活动id；
     *
     * @return
     */
    private String getDiscountActivityId() {
        if (shopRate != null) {
            return shopRate.getActId();
        }
        return "";
    }


    /**
     * 获取优惠券id数组  多个用英文逗号分割；
     */
    private String getCounponIds() {
        StringBuilder sb = new StringBuilder("");
        CustCouponBean yhqBean;
        for (int i = 0; i < mSelectCouponList.size(); i++) {
            yhqBean = mSelectCouponList.get(i);
            sb.append(i == mSelectCouponList.size() - 1 ? yhqBean.getActId() : yhqBean.getActId() + ",");
        }
        return sb.toString();
    }

    /**
     * 确认付款前的参数验证；
     */
    private boolean isInputRight() {
        if (payMethodBean == null) {
            toastCustom("请选择付款方式");
            return false;
        }
        // 需要输入金额时，校验大于零
        if (isInput) {
            String totalAmountStr = etConsumeTotalAmount.getText().toString().trim();
            float totalAmount = isEmpty(totalAmountStr) ? 0 : Float.parseFloat(totalAmountStr.substring(1));
            if (totalAmount <= 0) {
                toastCustom("总金额不能为0");
                return false;
            }
        }
        return true;
    }

    /**
     * 不参与优惠金额输入监听；
     */
    private MyTextWatcher noTakePartAmountWatcher = new MyTextWatcher() {
        @Override
        public void afterTextChange(String text) {
            String noTakePartAmount = text;
            //超过小数点后2位,重新格式化，但不执行计算；
            if (RegexUtil.verifyJeFormat(noTakePartAmount)) {
                etNoDiscountAmount.setText(Utils.subZeroAndDot(noTakePartAmount));
                return;
            }
            mNoDiscountAmount = isEmpty(noTakePartAmount) ? 0 : Float.parseFloat(noTakePartAmount);
            //计算商家优惠和银行优惠总金额
            calcStoreAndBankCoupon();
        }
    };

    /**
     * 更新优惠券红色文本部分的张数 和扣减金额；
     */
    private void updateCouponNumberAndAmount() {
        int couponNumber = mSelectCouponList.size();
        SpannableString ss = new SpannableString(getString(R.string.pay_yhq_with_number, couponNumber + ""));
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#910000")), 7, ss.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_yhq_number.setText(ss);
    }

}
