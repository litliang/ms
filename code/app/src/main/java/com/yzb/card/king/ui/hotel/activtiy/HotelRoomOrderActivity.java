package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.OverbalanceGoodsBean;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.common.PayFormIf;
import com.yzb.card.king.bean.common.PayOrderBean;
import com.yzb.card.king.bean.hotel.GoodsPlusParam;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelOrderParam;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.hotel.HotelRoomComboInfoBean;
import com.yzb.card.king.bean.hotel.HotelRoomInfoBean;
import com.yzb.card.king.bean.hotel.InvoiceInfoParam;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.http.hotel.HotelRoomDatePriceBean;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.app.activity.AddressManageActivity;
import com.yzb.card.king.ui.app.activity.DebitRiseManageActivity;
import com.yzb.card.king.ui.app.activity.HotelBillActivity;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.appwidget.popup.BottomDataListPP;
import com.yzb.card.king.ui.appwidget.popup.HotelProductOrderDetailsPopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.activity.pay.AppPreferentialPaymentActivity;
import com.yzb.card.king.ui.discount.adapter.HotelInsuranceAdapter;
import com.yzb.card.king.ui.discount.adapter.OverbalanceGoodsItemAdapter;
import com.yzb.card.king.ui.discount.bean.Insurance;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.persenter.HotelProductOrderPresenter;
import com.yzb.card.king.ui.hotel.view.HotelRoomCreateOrderView;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.ContactUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.ValidatorUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yzb.card.king.util.SharePrefUtil.HOTEL_ROOM_PASSENGERINFO_DATA;

/**
 * 类  名：酒店房间订单
 * 作  者：Li Yubing
 * 日  期：2017/8/7
 * 描  述：
 */
public class HotelRoomOrderActivity extends BaseActivity implements View.OnClickListener, HotelRoomCreateOrderView {
    private final static int SELECT_PASSENGER = 0; //请求选择旅游信息code
    private final static int REQ_GET_DEBITRISE = 1; //请求发票抬头code
    private static final int REQ_CONTACT_LXR = 2; //获取通讯录联系人；
    private static final int REQ_GET_ADDRESS = 3;//获取收获地址；


    private TextView tvInvoice;
    private TextView tvRoomDate;
    private TextView tvRoomName;
    private TextView tvRoomInfoDesc;
    private TextView tvPayMethodType;
    private LinearLayout llBill;
    private View lineFapiao;

    /**
     * 保留时间
     */
    private List<HotelRoomDatePriceBean> hotelRoomDatePriceBeanList;
    private LinearLayout llHotelOrderBaoliuTime;
    private TextView tvHotelBaoliuTime;

    private BottomDataListPP bottomDataListPP;
    private int selectBaoliuTimeIndex = 0;
    /*
      订单提示和订单免费取消说明
     */
    private TextView hotelRoomOrderDesc;
    private TextView tvFreeOrder;
    private ImageView ivTwoImage;
    private LinearLayout llFreeOrder;

    private TextView tvNotice;
    private TextView tvNoticeMessage;

    private TextView tvPianhao;
    private LinearLayout llPianHao;

    private TextView tvTotalAmount; //总金额；
    private ImageView ivBottomArrow; //订单详情指示器图片；
    private View ll_bottom;

    private Connector connector; //联系人
    private List<Insurance> bxListData;  //保险列表；
    private WholeRecyclerView bxListView;  //保险列表；
    private HotelInsuranceAdapter bxAdapter;//保险adapter；
    private LinearLayout panelBxView;


    private GridLayout guestGridLayout; //客户列表；
    private TextView tvGuestNum;
    private ImageView ivSubstration;
    private ImageView ivAddGuest;
    public TextView tvReturnCash;
    private List<PassengerInfoBean> passengerList; //入住人列表；

    //超值加購
    private OverbalanceGoodsItemAdapter overbalanceGoodsItemAdapter;
    private LinearLayout llPlusGoods;

    private HotelRoomComboInfoBean roomPolicy;
    private HotelRoomInfoBean hotel;

    private int currentGuesterNumber = 1;//房间数量；

    private EditText et_user_phone;

    private HotelProductOrderPresenter hotelProductOrderPresenter;

    private TextView tvStorePrice;

    //是否是有偿担保支付
    private boolean ifPaid = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_room_order);
        hotelProductOrderPresenter = new HotelProductOrderPresenter(this);
        initView();
        initViewData();
        initData();
    }

    /**
     * 初始化下单页面数据
     */
    private void initData()
    {
        if (roomPolicy == null) return;

        showPDialog("正在请求数据……");
        //请求房间套餐价格
        hotelProductOrderPresenter.sendHotelRoomPolicyRequest(roomPolicy.getPolicysId() + "", roomPolicy.getArrDate(), roomPolicy.getDepDate(), currentGuesterNumber);

        //付款方式，非到店付方式，需要请求超值加购
        if (roomPolicy.getPaymentType() == PayFormIf.PAYMENT_TYPE_DAODIANFU) {

            //到店付,发起查询保留时间
            hotelProductOrderPresenter.sendHotelRoomPolicyTimerRequest(roomPolicy.getPolicysId() + "", roomPolicy.getArrDate());

            llBill.setEnabled(false);

            llBill.setFocusable(false);

            tvInvoice.setText("请到酒店前台索要发票");

            ifPaid = false;

            panelBxView.setVisibility(View.GONE);

        } else {

            hotelProductOrderPresenter.queryGoodsPlusRequest(roomPolicy.getGoodsType());

            initInsuranceView();
        }
        //请求房间套餐剩余数量
        hotelProductOrderPresenter.sendHotelRoomPolicyNumberRequest(roomPolicy.getPolicysId() + "", roomPolicy.getArrDate());

    }

    /**
     * 初始化页面数据
     */
    private void initViewData()
    {
        if (roomPolicy == null) return;

        setTitleNmae(roomPolicy.getHotelName());//tvStorePrice

        Date dateStart = DateUtil.string2Date(roomPolicy.getArrDate(), DateUtil.DATE_FORMAT_DATE);

        Date dateEnd = DateUtil.string2Date(roomPolicy.getDepDate(), DateUtil.DATE_FORMAT_DATE);

        int duration = DateUtil.naturalDaysBetween(dateStart, dateEnd);

        duration = duration == 0 ? 1 : duration;

        StringBuffer sb1 = new StringBuffer();

        sb1.append(DateUtil.getDate(dateStart, 0) + "-");

        sb1.append(DateUtil.getDate(dateEnd, 0));

        sb1.append("  共" + AppUtils.ToCH(duration) + "晚");

        tvRoomDate.setText(sb1.toString());

        StringBuffer sbRoomName = new StringBuffer();

        sbRoomName.append(roomPolicy.getRoomInfo().getRoomsName());

        sbRoomName.append(" (").append(roomPolicy.getMealTypeDesc()).append(")");

        tvRoomName.setText(sbRoomName.toString());

        HotelRoomInfoBean headerItem = roomPolicy.getRoomInfo();

        StringBuffer sb = new StringBuffer();
        String bedTypeDesc = headerItem.getBedTypeDesc();
        if (!TextUtils.isEmpty(bedTypeDesc)) {
            sb.append(bedTypeDesc + "|");
        }
        String wifiDesc = headerItem.getWifiDesc();
        if (!TextUtils.isEmpty(wifiDesc)) {
            sb.append(wifiDesc + "|");
        }
        String areaDesc = headerItem.getAreaDesc();
        if (!TextUtils.isEmpty(areaDesc)) {
            sb.append(areaDesc );
        }
//        String floorDesc = headerItem.getFloorDesc();
//        if (!TextUtils.isEmpty(floorDesc)) {
//            sb.append(floorDesc + "|");
//        }
//
//        String smokeDesc = headerItem.getSmokeDesc();
//        if (!TextUtils.isEmpty(smokeDesc)) {
//            sb.append(smokeDesc + "|");
//        }
//
//        String windowDesc = headerItem.getWindowDesc();
//        if (!TextUtils.isEmpty(windowDesc)) {
//            sb.append(windowDesc + "|");
//        }
        tvRoomInfoDesc.setText(sb.toString());

        //入住提醒说明；
        int paymentTypeStr = roomPolicy.getPaymentType();

        if (PayFormIf.PAYMENT_TYPE_DAODIANFU == paymentTypeStr) {//到店付款

            hotelRoomOrderDesc.setText("现在预订，无需提前支付任何费用");

        } else if (PayFormIf.PAYMENT_TYPE_ONLINE == paymentTypeStr) {//在线付

            hotelRoomOrderDesc.setText("订单确认之后房间将为您整晚保留");

        } else if (PayFormIf.PAYMENT_TYPE_DANBAO == paymentTypeStr) {//担保付

            hotelRoomOrderDesc.setText("担保金为您锁定房源，入住后将退还");

        }
        //房间订单特别说明
        orderSpecialVersion("18:00");

        //预订说明
        if (hotel != null) {
            tvNoticeMessage.setVisibility(View.VISIBLE);

            StringBuffer sbYuding = new StringBuffer();

            sbYuding.append("该酒店不支持接待外宾").append(";");

            String addbedDesc = hotel.getAddbedDesc();//

            if ("可加床".equals(addbedDesc)) {

                sbYuding.append("该房型支持加床").append(";");

            } else {

                sbYuding.append("该房型不支持加床").append(";");
            }

            Hotel hotel = HotelLogicManager.getInstance().getHotel();

            if (hotel != null) {

                String arrTime = hotel.getArrTime();

                String depTime = hotel.getDepTime();

                sbYuding.append("该酒店的入住时间为").append(arrTime).append("，退房标准结算时间为").append(depTime).append("。如提前入住或推迟离店，均须酌情增收一定的费用");
            }

            String keyWord1 = getString(R.string.hotel_order_notice21_pre);
            SpannableString sb12 = new SpannableString(sbYuding.toString());
            if (sbYuding.toString().contains(keyWord1)) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_333333));
                sb12.setSpan(colorSpan, 0, keyWord1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvNoticeMessage.setText(sb12);
            }
        } else {

            tvNoticeMessage.setVisibility(View.GONE);

        }


    }

    public void initView()
    {
        Intent intent = getIntent();

        Serializable sePpolicy = intent.getSerializableExtra("HotelRoomComboInfoBean");

        if (sePpolicy != null) {

            roomPolicy = (HotelRoomComboInfoBean) sePpolicy;

            HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

            roomPolicy.setArrDate(productListParam.getArrDate());

            roomPolicy.setDepDate(productListParam.getDepDate());

            if (roomPolicy.getRoomInfo() != null) {

                hotel = roomPolicy.getRoomInfo();

            }
        }
        initRoomDetailView();
        initSubmitLineView();
        initGuestListView();


    }

    /**
     * 保险view；
     */
    private void initInsuranceView()
    {
        bxListData = new ArrayList<>();

        for (int i = 0; i < 2; i++) {

            Insurance insuranceTemp = new Insurance();
            if (i == 0) {
                insuranceTemp.shortName = "意外出行险";
                insuranceTemp.insuranceSummary = "意外伤害、急病医疗、航班延误、财产丢失等。";
            } else if (i == 1) {
                insuranceTemp.shortName = "取消险";
                insuranceTemp.insuranceSummary = "意外伤害、急病医疗、航班延误、财产丢失等";
            }
            bxListData.add(insuranceTemp);
        }

        bxListView = (WholeRecyclerView) findViewById(R.id.bxListView);
        bxAdapter = new HotelInsuranceAdapter(this, bxListData);
        bxAdapter.setStateChangeCallBack(new HotelInsuranceAdapter.IStateCallBack() {
            @Override
            public void stateChange()
            {
            }

            @Override
            public void goIntroducePage(String url)
            {
            }
        });
        bxListView.setLayoutManager(new LinearLayoutManager(this));
        bxListView.setAdapter(bxAdapter);

        refreshAdapterPrice();

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
     * 刷新adapter保险价格；
     */
    private void refreshAdapterPrice()
    {
        float totalAmount = 0f;
        if (roomPolicy.getPolicysPrice() != 0) {
            totalAmount = roomPolicy.getPolicysPrice() * currentGuesterNumber;
        }
        bxAdapter.setOrderAmount(totalAmount);
    }

    /**
     * 入住人列表view；
     */
    private void initGuestListView()
    {
        tvReturnCash = (TextView) findViewById(R.id.tvReturnCash);

        LinearLayout llBackCash = (LinearLayout) findViewById(R.id.llBackCash);
        //返现
        float bankPriceInt = roomPolicy.getBackPrice();
        if (bankPriceInt == 0f) {

            llBackCash.setVisibility(View.GONE);

            tvStorePrice.setVisibility(View.GONE);

        } else {
            llBackCash.setVisibility(View.VISIBLE);
            tvReturnCash.setText(bankPriceInt + "");

            tvStorePrice.setVisibility(View.VISIBLE);
            tvStorePrice.setText("离店可返  ¥" + Utils.subZeroAndDot(bankPriceInt+""));
        }

        payMoneyUI(0);
        tvGuestNum = (TextView) findViewById(R.id.tvGuestNum);
        guestGridLayout = (GridLayout) findViewById(R.id.guestGridLayout);

        ivSubstration = (ImageView) findViewById(R.id.ivSubstration);
        ivSubstration.setOnClickListener(this);

        ivAddGuest = (ImageView) findViewById(R.id.ivAddGuest);
        ivAddGuest.setOnClickListener(this);

        passengerList = new ArrayList<>();

        initGuestGridLayout();

    }

    /**
     * 入住人view的初始化；
     */
    private void initGuestGridLayout()
    {
        //初始化；
        guestGridLayout.removeAllViews();

        String passengerInfoListStr =  SharePrefUtil.getValueFromSp(this,SharePrefUtil.HOTEL_ROOM_PASSENGERINFO_DATA,null);

        if(!TextUtils.isEmpty(passengerInfoListStr)){

            List<PassengerInfoBean> listData = JSONArray.parseArray(passengerInfoListStr, PassengerInfoBean.class);

            int size = listData.size();
            currentGuesterNumber = size;
            for(PassengerInfoBean bean : listData){
                addGuestToGridLayout(bean);
            }

        }else {

            addGuestToGridLayout(new PassengerInfoBean());
            currentGuesterNumber = 1;
        }

        tvGuestNum.setText(currentGuesterNumber + "");

        String linkManPhoneStr =  SharePrefUtil.getValueFromSp(this,SharePrefUtil.HOTEL_ROOM_LINKMAN_PHONE,null);

        if(!TextUtils.isEmpty(linkManPhoneStr)){
            et_user_phone.setText(linkManPhoneStr);
        }

    }

    /**
     * 房间数量变化时，更新入住人列表布局；
     */
    private void updateGuestListLayout()
    {

        passengerList = hotelProductOrderPresenter.updatePassengerList();
        guestGridLayout.removeAllViews();
        for (int i = 0; i < passengerList.size(); i++) {
            View childView = getGuestItemView();
            if (i == passengerList.size() - 1) {
                childView.findViewById(R.id.hotelDivider).setVisibility(View.GONE);
            }
            initGuestItemData(childView, passengerList.get(i));
            guestGridLayout.addView(childView);
        }
    }

    /**
     * 添加一个bean到passenger；
     *
     * @param passenger
     */
    private void addGuestToGridLayout(PassengerInfoBean passenger)
    {
        List<PassengerInfoBean> noEmptyBeans = hotelProductOrderPresenter.getEtNoEmptyBeans();

        int noEmptySize = noEmptyBeans.size();//不为空的数量；

        boolean hasAdded = hotelProductOrderPresenter.hasPassengerAdded(passenger); //是否已经添加过；

        if (passenger != null && noEmptySize < currentGuesterNumber && !hasAdded) {

            passengerList = hotelProductOrderPresenter.addPassengerToList(passenger);

            guestGridLayout.removeAllViews();

            for (int i = 0; i < passengerList.size(); i++) {

                View childView = getGuestItemView();

                if (i == passengerList.size() - 1) {

                    childView.findViewById(R.id.hotelDivider).setVisibility(View.GONE);
                }

                initGuestItemData(childView, passengerList.get(i));
                guestGridLayout.addView(childView);
            }
        }
    }

    /**
     * 获取入住人item view；
     *
     * @return
     */
    public View getGuestItemView()
    {
        return getLayoutInflater().inflate(R.layout.view_hotel_guester_name, null);
    }

    /**
     * 初始化入住人view  data；
     */
    public void initGuestItemData(View childView, final PassengerInfoBean passenger)
    {
        if (childView != null && passenger != null) {
            EditText etguestname = (EditText) childView.findViewById(R.id.et_guest_name);
            etguestname.setText(passenger.getZhName());
            etguestname.addTextChangedListener(new MyTextWatcher() {
                @Override
                public void afterTextChange(String text)
                {
                    if (!text.equals(passenger.getZhName())) {
                        passenger.setZhName(text);
                    }
                }
            });
        }
    }

    /**
     * 订单提交view；
     */
    private void initSubmitLineView()
    {
        ll_bottom = findViewById(R.id.ll_bottom);
        ivBottomArrow = (ImageView) findViewById(R.id.ivBottomArrow);
        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        findViewById(R.id.panelOrderDetail).setOnClickListener(this);
    }

    /**
     * 更新订单详情pop开闭的指示器；
     *
     * @param flag 0:向上，1：向下；
     */
    private void updateOrderDetailNav(int flag)
    {
        switch (flag) {
            case 0:
                Animation startAnimation = AnimationUtils.loadAnimation(this, R.anim.shuttle_rotate_0_180);
                startAnimation.setDuration(0);
                ivBottomArrow.startAnimation(startAnimation);
                break;
            case 1:
                Animation endAnimation = AnimationUtils.loadAnimation(this, R.anim.shuttle_rotate_180_360);
                endAnimation.setDuration(0);
                ivBottomArrow.startAnimation(endAnimation);
                break;
        }
    }

    /**
     * 订单详情view；
     */
    private void initRoomDetailView()
    {
        tvInvoice = (TextView) findViewById(R.id.tvInvoice);

        tvRoomDate = (TextView) findViewById(R.id.tvRoomDate);

        tvRoomName = (TextView) findViewById(R.id.tvRoomName);

        tvRoomInfoDesc = (TextView) findViewById(R.id.tvRoomInfoDesc);

        tvStorePrice = (TextView) findViewById(R.id.tvStorePrice);

        tvPayMethodType = (TextView) findViewById(R.id.tvPayMethodType);

        hotelRoomOrderDesc = (TextView) findViewById(R.id.hotelRoomOrderDesc);

        tvFreeOrder = (TextView) findViewById(R.id.tvFreeOrder);

        llFreeOrder = (LinearLayout) findViewById(R.id.llFreeOrder);

        llPianHao = (LinearLayout) findViewById(R.id.llPianHao);

        llPianHao.setOnClickListener(this);

        tvPianhao = (TextView) findViewById(R.id.tvPianhao);

        et_user_phone = (EditText) findViewById(R.id.et_user_phone);

        findViewById(R.id.iv_open_contact).setOnClickListener(this);

        tvNoticeMessage = (TextView) findViewById(R.id.tvNoticeMessage);

        tvNotice = (TextView) findViewById(R.id.tvNotice);

        ivTwoImage = (ImageView) findViewById(R.id.ivTwoImage);

        llHotelOrderBaoliuTime = (LinearLayout) findViewById(R.id.llHotelOrderBaoliuTime);

        tvHotelBaoliuTime = (TextView) findViewById(R.id.tvHotelBaoliuTime);

        llHotelOrderBaoliuTime.setVisibility(View.GONE);

        llHotelOrderBaoliuTime.setOnClickListener(this);

        llBill = (LinearLayout) findViewById(R.id.llBill);

        llBill.setOnClickListener(this);

        lineFapiao = findViewById(R.id.lineFapiao);

        panelBxView = (LinearLayout) findViewById(R.id.panelBxView);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.iv_open_contact: // 获取境内外手机号；
                ContactUtil.startContactsActivity(this, REQ_CONTACT_LXR);
                break;
//            case R.id.panelDebitRise: //发票抬头
//                Intent debitRiseIntent = new Intent(this, DebitRiseManageActivity.class);
//                debitRiseIntent.putExtra("flag", DebitRiseManageActivity.GET_RISE_DATA);
//                startActivityForResult(debitRiseIntent, REQ_GET_DEBITRISE);
//                break;
            case R.id.panelAddress:
                Intent addressIntent = new Intent(this, AddressManageActivity.class);
                addressIntent.putExtra("flag", AddressManageActivity.GET_ADDRESS_DATA);
                startActivityForResult(addressIntent, REQ_GET_ADDRESS);
                break;
            case R.id.panelOrderDetail: //显示订单详情；

                showOrderDetailPop();

                break;
            case R.id.tv_commit: //提交订单；

                int presenterSize = hotelProductOrderPresenter.getEtNoEmptyBeans().size();

                if (presenterSize == 0) {

                    ToastUtil.i(HotelRoomOrderActivity.this, "请输入入住人姓名");

                    return;
                }

                if (presenterSize != currentGuesterNumber) {

                    ToastUtil.i(HotelRoomOrderActivity.this, "请补充完入住人姓名");

                    return;
                }

                if (TextUtils.isEmpty(et_user_phone.getText().toString().trim())) {

                    ToastUtil.i(HotelRoomOrderActivity.this, "请输入联系人手机号");

                    return;
                }

                if( !ValidatorUtil.isMobile(et_user_phone.getText().toString().trim())){

                    ToastUtil.i(HotelRoomOrderActivity.this, R.string.toast_chech_your_phone_number);

                    return;
                }

                showPDialog("正在请求数据……");
                hotelProductOrderPresenter.sendCreateRoomOrderRequest();
                break;
            case R.id.ivSubstration:

                if (currentGuesterNumber > 1) {

                    currentGuesterNumber = currentGuesterNumber - 1;

                    guesterHandler.sendEmptyMessage(0);
                } else {
                    ToastUtil.i(this, "入住人数最少为1");
                }

                break;
            case R.id.ivAddGuest:

                if (currentGuesterNumber < 100) {

                    currentGuesterNumber = currentGuesterNumber + 1;

                    guesterHandler.sendEmptyMessage(0);

                } else {
                    ToastUtil.i(this, "入住人数最多为99");
                }

                break;
            case R.id.llBill://发票

                String totalPrice = tvTotalAmount.getText().toString();

                Intent intent = new Intent(this, HotelBillActivity.class);

                intent.putExtra("totalPrice", totalPrice);

                if (invoiceInfoParam != null) {

                    intent.putExtra("invoiceDataBean", invoiceInfoParam);
                }

                startActivityForResult(intent, 1001);
                break;

            case R.id.llPianHao:

                Intent intentPH = new Intent(this, HotelRemarkActivity.class);

                String str = tvPianhao.getText().toString();

                intentPH.putExtra("remarkBean", str);

                startActivityForResult(intentPH, 1001);

                break;
            case R.id.llHotelOrderBaoliuTime://保留时间

                if (bottomDataListPP != null) {

                    bottomDataListPP.showPP(getWindow().getDecorView());
                }

                break;
            default:
                break;
        }
    }

    private Handler guesterHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            updateGuestListLayout();

            calTotalMoney();

            tvGuestNum.setText(currentGuesterNumber + "");
        }
    };

    private HotelProductOrderDetailsPopup hotelStarPricePopup;

    private List<HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean> ppDataList;

    /**
     * 订单详情pop；
     */
    private void showOrderDetailPop()
    {
        //整理明细数据
        if (roomPolicy == null) {

            return;
        }

        if (ppDataList == null) {

            ppDataList = new ArrayList<HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean>();

        } else {

            ppDataList.clear();

        }

        //付款方式
        int paymentType = roomPolicy.getPaymentType();

        if (paymentType == PayFormIf.PAYMENT_TYPE_DAODIANFU) {//到店付

            //到店支付数据
            //房间明细
            if (roomPriceList != null) {

                if (ifPaid) {//如果是有偿担保到店付，则显示担保金额明显

                    HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean danbaoZhifuFather = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                    danbaoZhifuFather.ifFather = true;
                    danbaoZhifuFather.productNameStr = "担保支付";
                    danbaoZhifuFather.detailPrice = Float.parseFloat(tvTotalAmount.getText().toString());
                    ppDataList.add(danbaoZhifuFather);

                    HotelRoomDatePriceBean hotelRoomDatePriceBeanTmep = hotelRoomDatePriceBeanList.get(selectBaoliuTimeIndex);
                    HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean danbaoZhifuChildrenFather = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                    danbaoZhifuChildrenFather.ifFather = false;
                    danbaoZhifuChildrenFather.productNameStr = hotelRoomDatePriceBeanTmep.getRetentionTimeDesc() + "(担保费用)";
                    danbaoZhifuChildrenFather.detailPrice = danbaoZhifuFather.detailPrice;
                    ppDataList.add(danbaoZhifuChildrenFather);

                }

                HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean unOnlineFather = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                unOnlineFather.ifFather = true;
                unOnlineFather.productNameStr = "到店支付";
                unOnlineFather.detailPrice = cellPrice * currentGuesterNumber;
                ppDataList.add(unOnlineFather);

                for (HotelRoomDatePriceBean bean : roomPriceList) {

                    HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneToOneChilder = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                    oneToOneChilder.ifFather = false;
                    oneToOneChilder.productNameStr = bean.getRoomsDate().replaceAll("-", ".") + " (" + currentGuesterNumber + "间)";
                    oneToOneChilder.detailPrice = bean.getPrice() * currentGuesterNumber;
                    ppDataList.add(oneToOneChilder);
                }
            }

        } else if (paymentType == PayFormIf.PAYMENT_TYPE_ONLINE) {//  在线支付

            //在线支付数据
            HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneFather = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
            oneFather.ifFather = true;
            oneFather.productNameStr = "在线支付";
            oneFather.detailPrice = Float.parseFloat(tvTotalAmount.getText().toString());
            ppDataList.add(oneFather);
            //房间明细
            if (roomPriceList != null) {

                for (HotelRoomDatePriceBean bean : roomPriceList) {

                    HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneToOneChilder = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                    oneToOneChilder.ifFather = false;
                    oneToOneChilder.productNameStr = bean.getRoomsDate().replaceAll("-", ".") + " (" + currentGuesterNumber + "间)";
                    oneToOneChilder.detailPrice = bean.getPrice() * currentGuesterNumber;
                    ppDataList.add(oneToOneChilder);
                }
            }
            //超值加购
            List<OverbalanceGoodsBean> list = overbalanceGoodsItemAdapter.getData();
            int size = list.size();
            if (size > 0) {
                for (OverbalanceGoodsBean bean : list) {
                    int number = bean.getNumber();
                    if (number > 0) {
                        HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneToOneChilder = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                        oneToOneChilder.ifFather = false;
                        oneToOneChilder.productNameStr = bean.getActName() + " (" + number + "张)";
                        oneToOneChilder.detailPrice = Float.parseFloat(bean.getPrice()) * number;
                        ppDataList.add(oneToOneChilder);
                    }
                }
            }

            //快递费
            if (invoiceInfoParam != null) {

                float expressAmount = invoiceInfoParam.getExpressAmount();
                if (expressAmount > 0) {
                    HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneToOneChilder = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                    oneToOneChilder.ifFather = false;
                    oneToOneChilder.productNameStr = "快递费";
                    oneToOneChilder.detailPrice = expressAmount;
                    ppDataList.add(oneToOneChilder);
                }

            }


        } else if (paymentType == PayFormIf.PAYMENT_TYPE_DANBAO) {//担保支付

            //在线支付数据
            HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneFather = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
            oneFather.ifFather = true;
            oneFather.productNameStr = "担保支付";
            oneFather.detailPrice = Float.parseFloat(tvTotalAmount.getText().toString());
            ppDataList.add(oneFather);

            HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneToOneChilder1 = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
            oneToOneChilder1.ifFather = false;
            oneToOneChilder1.productNameStr = "在线担保（离店后原路返回）";
            oneToOneChilder1.detailPrice = cellPrice * currentGuesterNumber;
            ppDataList.add(oneToOneChilder1);

            //超值加购
            List<OverbalanceGoodsBean> list = overbalanceGoodsItemAdapter.getData();
            int size = list.size();
            if (size > 0) {
                for (OverbalanceGoodsBean bean : list) {
                    int number = bean.getNumber();
                    if (number > 0) {
                        HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneToOneChilder = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                        oneToOneChilder.ifFather = false;
                        oneToOneChilder.productNameStr = bean.getActName() + " (" + number + "张)";
                        oneToOneChilder.detailPrice = Float.parseFloat(bean.getPrice()) * number;
                        ppDataList.add(oneToOneChilder);
                    }
                }
            }

            //到店支付数据
            //房间明细
            if (roomPriceList != null) {
                HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean unOnlineFather = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                unOnlineFather.ifFather = true;
                unOnlineFather.productNameStr = "到店支付";
                unOnlineFather.detailPrice = cellPrice * currentGuesterNumber;
                ppDataList.add(unOnlineFather);

                for (HotelRoomDatePriceBean bean : roomPriceList) {

                    HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneToOneChilder = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                    oneToOneChilder.ifFather = false;
                    oneToOneChilder.productNameStr = bean.getRoomsDate().replaceAll("-", ".") + " (" + currentGuesterNumber + "间)";
                    oneToOneChilder.detailPrice = bean.getPrice() * currentGuesterNumber;
                    ppDataList.add(oneToOneChilder);
                }

            }
        }
        //返现 立减
        if (roomPolicy != null) {

            float bankPriceInt = roomPolicy.getBackPrice();

            if (bankPriceInt != 0f) {

                //返现
                HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean twoFather = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                twoFather.ifFather = true;
                twoFather.productNameStr = "离店返现";
                twoFather.detailPrice = bankPriceInt;
                ppDataList.add(twoFather);

                HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean twoToTwoFather = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                twoToTwoFather.ifFather = false;
                twoToTwoFather.productNameStr = "返现";
                twoToTwoFather.detailPrice = bankPriceInt;
                ppDataList.add(twoToTwoFather);

            }

            //立减
            float minusPrice =  roomPolicy.getMinusPrice();
            if (minusPrice != 0f) {
                //立减
                HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean threeFather = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                threeFather.ifFather = true;
                threeFather.productNameStr = "立减";
                threeFather.detailPrice = Float.parseFloat(minusPrice + "");
                ppDataList.add(threeFather);

                HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean threeToTwoFather = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                threeToTwoFather.ifFather = false;
                threeToTwoFather.productNameStr = "立减";
                threeToTwoFather.detailPrice = Float.parseFloat("-"+minusPrice );
                ppDataList.add(threeToTwoFather);

            }
        }
        if (hotelStarPricePopup == null) {

            hotelStarPricePopup = new HotelProductOrderDetailsPopup(HotelRoomOrderActivity.this, -1, BaseFullPP.ViewPostion.VIEW_BOTTOM);

            hotelStarPricePopup.setViewDataCallBack(new HotelProductOrderDetailsPopup.ViewDataListCallBack() {
                @Override
                public void onConfirm()
                {
                    updateOrderDetailNav(1);
                }
            });
        }

        hotelStarPricePopup.loadDataView(ppDataList);

        hotelStarPricePopup.showPP(ll_bottom);

        updateOrderDetailNav(0);
    }

    private InvoiceInfoParam invoiceInfoParam = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case SELECT_PASSENGER: //入住人返回；
                Serializable obj = data.getSerializableExtra("passenger");
                if (obj != null) {
                    addGuestToGridLayout((PassengerInfoBean) obj);
                }
                break;

            case REQ_CONTACT_LXR:  //选择通讯录联系人
                connector = new Connector();
                ContactUtil.getConnector(this, data, connector);
                et_user_phone.setText(connector.getMobile());
                break;

            case 1001:

                if (1000 == resultCode) {

                    if (data != null) {//tvInvoice

                        if (data.hasExtra("invoiceInfoParam")) {

                            invoiceInfoParam = (InvoiceInfoParam) data.getSerializableExtra("invoiceInfoParam");

                            float expressAmount = invoiceInfoParam.getExpressAmount();

                            if(expressAmount == 0f){
                                tvInvoice.setText("需要发票");
                            }else {
                                tvInvoice.setText("开票金额 ¥"+ Utils.subZeroAndDot(expressAmount+""));
                            }

                        } else {

                            invoiceInfoParam = null;

                            tvInvoice.setText("不需要发票");
                        }

                    } else {

                        invoiceInfoParam = null;

                        tvInvoice.setText("不需要发票");

                    }

                    calTotalMoney();

                } else if (1001 == resultCode) {//偏好

                    String remarkStr = data.getStringExtra("remarkStr");

                    tvPianhao.setText(remarkStr);
                }

                break;
        }
    }

    /**
     * 时间段内套餐金额
     */
    private int cellPrice;

    private List<HotelRoomDatePriceBean> roomPriceList;

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        dimissPdialog();

        if (HotelProductOrderPresenter.QUERYROOMSPOLICYSPRICECODE == type) {

            List<HotelRoomDatePriceBean> list = JSONArray.parseArray(o + "", HotelRoomDatePriceBean.class);

            this.roomPriceList = list;

            //入住人和入住时长的总价格
            int totalMoney = 0;

            for (HotelRoomDatePriceBean bean : list) {

                int price = bean.getPrice();

                totalMoney = totalMoney + price;
            }

            cellPrice = totalMoney;

            calTotalMoney();

        } else if (HotelProductOrderPresenter.CREATEROOMORDERCODE == type) {

            if (roomPolicy == null) {

                return;
            }
            //付款方式
            int paymentType = roomPolicy.getPaymentType();

            if (paymentType == PayFormIf.PAYMENT_TYPE_DAODIANFU && !ifPaid) {//到店付且不是有偿担保

                finish();

            } else if (paymentType == PayFormIf.PAYMENT_TYPE_ONLINE || paymentType == PayFormIf.PAYMENT_TYPE_DANBAO || (paymentType == PayFormIf.PAYMENT_TYPE_DAODIANFU && ifPaid) ) {//在线支付\担保付\到店付（有偿担保）

                //查询用户的代金券信息
                //发放平台
                int issuePlatform = 0;
                //行业id
                int industryId = GlobalVariable.industryId;

                GoldTicketParam goldTicketParam = new GoldTicketParam();

                goldTicketParam.setIssuePlatform(issuePlatform);

                goldTicketParam.setIndustryId(industryId);

                goldTicketParam.setShopId(roomPolicy.getShopId());

                goldTicketParam.setStoreId(roomPolicy.getHotelId());

                goldTicketParam.setGoodsId(roomPolicy.getRoomInfo().getRoomsId());

                PayOrderBean bean = JSONObject.parseObject(o + "", PayOrderBean.class);

                Intent intent = new Intent(HotelRoomOrderActivity.this, AppPreferentialPaymentActivity.class);

                intent.putExtra("orderData", bean);

                intent.putExtra("goldTicketParam", goldTicketParam);

                intent.putExtra("goodName", roomPolicy.getHotelName() + "(" + roomPolicy.getRoomInfo().getRoomsName() + ")");

                startActivity(intent);

                finish();
            }

        } else if (HotelProductOrderPresenter.QUERYGOODSPLUSC0DE == type) {

            List<OverbalanceGoodsBean> list = JSON.parseArray(o + "", OverbalanceGoodsBean.class);

            overbalanceGoodsItemAdapter.clearAll();

            overbalanceGoodsItemAdapter.setList(list);

            if (list == null || list.size() == 0) {

                llPlusGoods.setVisibility(View.GONE);

            } else {

                llPlusGoods.setVisibility(View.VISIBLE);

            }

        } else if (HotelProductOrderPresenter.SELECTROOMSPOLICYSQUANTITYC0DE == type) {

            HotelRoomDatePriceBean hotelRoomDatePriceBean = JSONObject.parseObject(o + "", HotelRoomDatePriceBean.class);

            llFreeOrder.setVisibility(View.VISIBLE);

            int quantity = hotelRoomDatePriceBean.getResidualQuantity();

            int minPriceStatus = hotelRoomDatePriceBean.getMinPriceStatus();

            if (quantity < 3) {

                ivTwoImage.setBackgroundResource(R.mipmap.icon_hotel_order_fire);

                tvFreeOrder.setText("此房间非常抢手，房量紧张");

            } else if (quantity >= 3 && minPriceStatus == 1) {

                ivTwoImage.setBackgroundResource(R.mipmap.icon_hotel_order_sheng);

                tvFreeOrder.setText("您选择了该房型最划算的价格");
            } else {

                llFreeOrder.setVisibility(View.GONE);
            }

        } else if (HotelProductOrderPresenter.QUERYROOMSRETENTIONTIMECODE == type) {

            hotelRoomDatePriceBeanList = JSON.parseArray(o + "", HotelRoomDatePriceBean.class);

            int size = hotelRoomDatePriceBeanList.size();

            if (size > 0) {

                llHotelOrderBaoliuTime.setVisibility(View.VISIBLE);

                if (bottomDataListPP == null) {

                    String[] baoliuTimeArray = new String[size];

                    for (int a = 0; a < size; a++) {

                        HotelRoomDatePriceBean tempBean = hotelRoomDatePriceBeanList.get(a);

                        if (selectBaoliuTimeIndex == a) {

                            tvHotelBaoliuTime.setText(tempBean.getRetentionTimeDesc());

                            orderSpecialVersion(tempBean.getGuaranteeTimeDesc());

                            calTotalMoney();

                        }

                        float guaranteeAmount = tempBean.getGuaranteeAmount();

                        String guaranteeAmountStr = "";

                        if (guaranteeAmount > 0) {

                            guaranteeAmountStr = "  (需担保¥" + Utils.subZeroAndDot(guaranteeAmount+"") + ")";
                        }

                        baoliuTimeArray[a] = tempBean.getRetentionTimeDesc() + guaranteeAmountStr;

                    }

                    bottomDataListPP = new BottomDataListPP(this, -1, false);

                    bottomDataListPP.setSelectIndex(selectBaoliuTimeIndex);

                    bottomDataListPP.setLogicData(baoliuTimeArray, null);

                    bottomDataListPP.setCallBack(invoiceCallBack);
                }


            } else {
                llHotelOrderBaoliuTime.setVisibility(View.GONE);
            }

        }
    }

    private BottomDataListPP.BottomDataListCallBack invoiceCallBack = new BottomDataListPP.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            tvHotelBaoliuTime.setText(name);

            selectBaoliuTimeIndex = selectIndex;
            //特别说明
            orderSpecialVersion(hotelRoomDatePriceBeanList.get(selectBaoliuTimeIndex).getGuaranteeTimeDesc());

            calTotalMoney();

        }
    };

    /**
     * 计算总价
     */
    private void calTotalMoney()
    {
        //房间单价*人数
        int totalMoney = cellPrice * currentGuesterNumber;
        //快递费
        float expressAmount = 0;
        if (invoiceInfoParam != null) {

            expressAmount = invoiceInfoParam.getExpressAmount();
        }

        //保留时间，担保付费用
        float danbaoMoney = 0;
        if (hotelRoomDatePriceBeanList != null) {

            HotelRoomDatePriceBean hotelRoomDatePriceBeanTemp = hotelRoomDatePriceBeanList.get(selectBaoliuTimeIndex);

            if (hotelRoomDatePriceBeanTemp.getGuaranteeAmount() > 0) {

                danbaoMoney = hotelRoomDatePriceBeanTemp.getGuaranteeAmount();
            }
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


        if (danbaoMoney > 0) {//如果到店支付，选择了有偿的保留时间，则需要支付担保费用，

            ifPaid = true;

            float totalPrice = danbaoMoney;//快递费加担保费

            tvTotalAmount.setText(Utils.subZeroAndDot(totalPrice + ""));

            tvPayMethodType.setText("担保支付");

            tvStorePrice.setVisibility(View.VISIBLE);

            tvStorePrice.setText("到店支付  ¥" + Utils.subZeroAndDot(totalMoney+""));

        } else {

            //立减
            float minusPrice =  roomPolicy.getMinusPrice();

            float totalPrice = (float) totalMoney + goodsPlushMoney + expressAmount-minusPrice;

            tvTotalAmount.setText(Utils.subZeroAndDot(totalPrice + ""));

            payMoneyUI(totalMoney);
        }
    }

    /**
     * 不同支付方式的订单支付金额信息
     *
     * @param totalMoney
     */
    private void payMoneyUI(float totalMoney)
    {
        //付款方式
        int paymentType = roomPolicy.getPaymentType();

        if (paymentType == PayFormIf.PAYMENT_TYPE_DAODIANFU) {//到店付

            tvPayMethodType.setText("到店付");

            tvStorePrice.setVisibility(View.VISIBLE);

            tvStorePrice.setText("到店支付  ¥" + Utils.subZeroAndDot(totalMoney+""));

        } else if (paymentType == PayFormIf.PAYMENT_TYPE_ONLINE) {//在线支付

            tvPayMethodType.setText("在线支付");

        } else if (paymentType == PayFormIf.PAYMENT_TYPE_DANBAO) {//担保支付

            tvPayMethodType.setText("担保支付");

            tvStorePrice.setVisibility(View.VISIBLE);

            tvStorePrice.setText("到店支付  ¥" + Utils.subZeroAndDot(totalMoney+""));
        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        dimissPdialog();

        if(!TextUtils.isEmpty(o+"")){

            Error error = JSONObject.parseObject(JSON.toJSONString(o),Error.class);

            ToastUtil.i(this,error.getError());
        }
    }

    @Override
    public List<PassengerInfoBean> getPassengerList()
    {
        return passengerList;
    }

    @Override
    public int getRoomCount()
    {
        return currentGuesterNumber;
    }

    @Override
    public HotelOrderParam collectHotelRoomOrderParam()
    {
        HotelOrderParam param = new HotelOrderParam();

        param.setShopId(roomPolicy.getShopId());

        param.setHotelId(roomPolicy.getHotelId());

        param.setRoomsId(roomPolicy.getRoomInfo().getRoomsId());

        param.setPolicysId(roomPolicy.getPolicysId());

        param.setPaymentType(roomPolicy.getPaymentType());

        param.setGoodsQuantity(hotelProductOrderPresenter.getEtNoEmptyBeans().size());

        param.setArrDate(roomPolicy.getArrDate());

        param.setDepDate(roomPolicy.getDepDate());

        double moneyD = Double.parseDouble(tvTotalAmount.getText().toString());

        param.setOrderAmount(moneyD);

        param.setOrderRemark(tvPianhao.getText().toString());

        param.setGuestList(hotelProductOrderPresenter.getEtNoEmptyBeans());

        param.setContactsInfo(getContactParams());

        if (overbalanceGoodsItemAdapter != null) {

            List<OverbalanceGoodsBean> list = overbalanceGoodsItemAdapter.getData();

            int size = list.size();

            if (size > 0) {

                List<GoodsPlusParam> tempList = new ArrayList<GoodsPlusParam>();

                for (OverbalanceGoodsBean bean : list) {
                    int number = bean.getNumber();
                    if (number > 0) {

                        GoodsPlusParam paramPlus = new GoodsPlusParam();

                        paramPlus.setGoodsid(bean.getActId() + "");

                        paramPlus.setGoodsQuantity(number);

                        tempList.add(paramPlus);
                    }
                }
                param.setAddList(tempList);
            }

        }
        param.setInvoiceInfo(invoiceInfoParam);

        //保留时间和担保时间
        if (hotelRoomDatePriceBeanList != null) {

            if (hotelRoomDatePriceBeanList.size() > 0) {

                HotelRoomDatePriceBean hotelRoomDatePriceBean = hotelRoomDatePriceBeanList.get(selectBaoliuTimeIndex);

                param.setRetentionTime(hotelRoomDatePriceBean.getRetentionTimeDesc());

                param.setGuaranteeTime(hotelRoomDatePriceBean.getGuaranteeTimeDesc());
            }
        }

        return param;
    }

    /**
     * 联系人信息
     */
    private Map<String, Object> getContactParams()
    {
        Map<String, Object> contactParams = new HashMap<>();
        contactParams.put("name", connector != null ? connector.getNickName() : ""); //联系人姓名
        contactParams.put("ophone", et_user_phone.getText().toString().trim()); //联系人姓名
        return contactParams;
    }

    /**
     * 超值加购监听器
     */
    private OverbalanceGoodsItemAdapter.IStateCallBack goodsPlushcallBack = new OverbalanceGoodsItemAdapter.IStateCallBack() {
        @Override
        public void updatePriceAction(OverbalanceGoodsBean bean)
        {
            calTotalMoney();
        }
    };

    /**
     * 房间订单特别提示
     *
     * @param time 时间
     */
    private void orderSpecialVersion(String time)
    {

        //入住提醒说明；
        int paymentTypeStr = roomPolicy.getPaymentType();

        int cancelType = roomPolicy.getCancelType();

        String noticeContent = null;
        //特别说明 预定说明
        if (PayFormIf.PAYMENT_TYPE_DAODIANFU == paymentTypeStr) {//到店付款

            if (PayFormIf.CANCEL_TYPE_TIMELIMIT == cancelType) {//限时取消

                noticeContent = time + getString(R.string.hotel_order_notice_three);

            } else if (PayFormIf.CANCEL_TYPE_PAID == cancelType) {//付费取消

                noticeContent = time + getString(R.string.hotel_order_notice_four);

            }

        } else if (PayFormIf.PAYMENT_TYPE_ONLINE == paymentTypeStr) {//在线付

            if (PayFormIf.CANCEL_TYPE_TIMELIMIT == cancelType) {//限时取消

                noticeContent = getString(R.string.hotel_order_notice_one);

            } else if (PayFormIf.CANCEL_TYPE_PAID == cancelType) {//付费取消

                noticeContent = getString(R.string.hotel_order_notice_two);
            }

        } else if (PayFormIf.PAYMENT_TYPE_DANBAO == paymentTypeStr) {//担保付

            if (PayFormIf.CANCEL_TYPE_TIMELIMIT == cancelType) {//限时取消

                noticeContent = getString(R.string.hotel_order_notice_five);

            } else if (PayFormIf.CANCEL_TYPE_PAID == cancelType) {//付费取消

                noticeContent = getString(R.string.hotel_order_notice_six);
            }

        }

        if (noticeContent != null) {

            tvNotice.setVisibility(View.VISIBLE);
            String keyWord = getString(R.string.hotel_order_notice2_pre);
            String totalStr = keyWord + noticeContent;
            SpannableString sb13 = new SpannableString(totalStr);
            if (totalStr.contains(keyWord)) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_333333));
                sb13.setSpan(colorSpan, 0, keyWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvNotice.setText(sb13);
            }
        } else {

            tvNotice.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        saveUseControlerData();

    }


    private void saveUseControlerData(){

        List<PassengerInfoBean>  passengerInfoBeanList =  hotelProductOrderPresenter.getEtNoEmptyBeans();

        int size = passengerInfoBeanList.size();

        if(size>0){

            String  passengerInfoBeanListStr = JSON.toJSONString(passengerInfoBeanList);
            //先清理数据，再记录数据
            SharePrefUtil.saveToSp(this,SharePrefUtil.HOTEL_ROOM_PASSENGERINFO_DATA,passengerInfoBeanListStr);

        }

        String linkManPhone = et_user_phone.getText().toString().trim();
        SharePrefUtil.saveToSp(this,SharePrefUtil.HOTEL_ROOM_LINKMAN_PHONE,linkManPhone);
    }
}


