package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.OverbalanceGoodsBean;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.common.PayOrderBean;
import com.yzb.card.king.bean.giftcard.CardRightsInfoBean;
import com.yzb.card.king.bean.hotel.ExtraGoodsComboBean;
import com.yzb.card.king.bean.hotel.GoodsPlusParam;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelOrderParam;
import com.yzb.card.king.bean.hotel.InvoiceInfoParam;
import com.yzb.card.king.bean.ticket.PostFeeBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.app.activity.AddressManageActivity;
import com.yzb.card.king.ui.app.activity.HotelBillActivity;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.appwidget.popup.AppCalendarPopup;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.appwidget.popup.BottomDataListPP;
import com.yzb.card.king.ui.appwidget.popup.HotelProductOrderDetailsPopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.activity.pay.AppPreferentialPaymentActivity;
import com.yzb.card.king.ui.discount.adapter.OverbalanceGoodsItemAdapter;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.persenter.HotelProductOrderPresenter;
import com.yzb.card.king.ui.hotel.view.HotelRoomCreateOrderView;
import com.yzb.card.king.ui.ticket.presenter.GetPostFeePresenter;
import com.yzb.card.king.ui.ticket.view.GetPostFeeView;
import com.yzb.card.king.util.ContactUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.ValidatorUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：卡权益下单页面
 * 作  者：Li Yubing
 * 日  期：2017/9/11
 * 描  述：
 */
@ContentView(R.layout.activity_cardrights_order)
public class CardRightsOrderActivity extends BaseActivity implements HotelRoomCreateOrderView, GetPostFeeView,View.OnClickListener {

    private static final int REQ_GET_ADDRESS = 3;//获取收获地址；
    //常理
    private static final int REQ_CONTACT_LXR = 2; //获取通讯录联系人；

    @ViewInject(R.id.tvEffDate)
    private TextView tvEffDate;

    @ViewInject(R.id.tvMailType)
    private TextView tvMailType;

    @ViewInject(R.id.llDate)
    private LinearLayout llDate;

    @ViewInject(R.id.llEmailAddress)
    private LinearLayout llEmailAddress;

    @ViewInject(R.id.tvQuickPrice)
    private TextView tvQuickPrice;

    @ViewInject(R.id.etEmailAddress)
    private EditText etEmailAddress;

    private TextView tvRoomDate, tvRoomName, tvRoomInfoDesc, tvGuestNum, tvTotalAmount, tvStorePrice, tvInvoice, tvPayMethodType;

    private ImageView ivBottomArrow;

    private EditText et_user_phone, etLinkManName;

    private LinearLayout llPlusGoods;

    private CardRightsInfoBean extraGoodsComboBean;

    private BottomDataListPP pp = null;

    private String fuzengquanyi;

    private int currentGuesterNumber = 1;//人数；

    private HotelProductOrderPresenter hotelProductOrderPresenter;

    private GetPostFeePresenter postFeePresenter;//计算邮费

    private OverbalanceGoodsItemAdapter overbalanceGoodsItemAdapter;

    private  GoodsAddressBean addressBean;

    private View ll_bottom;

    private String tvEffDateStr = null;

    private int selectedApplicantIndex = 0;

    private int receiveType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        if (extraGoodsComboBean != null) {

            postFeePresenter = new GetPostFeePresenter(this);
            hotelProductOrderPresenter = new HotelProductOrderPresenter(this);
            initData();

            initRequest();
        }
    }

    private void initRequest()
    {
        showPDialog("正在请求数据……");

        hotelProductOrderPresenter.queryGoodsPlusRequest(HoteUtil.HOTEL_CARD_EQUITY_CODE);
    }

    private void initData()
    {

        tvRoomDate.setText(extraGoodsComboBean.getGiftsName());

        StringBuffer sb = new StringBuffer();

        sb.append("有效期：");

        sb.append(extraGoodsComboBean.getEffMonthDesc());

        tvRoomName.setText(sb.toString());

        StringBuffer quanyiSb = new StringBuffer();

        quanyiSb.append("附赠权益：").append(fuzengquanyi);

        tvRoomInfoDesc.setText("有效期：" + fuzengquanyi);


        tvPayMethodType.setText("特惠价");

        tvTotalAmount.setText(extraGoodsComboBean.getOnlinePrice() + "");

        int storePrice = extraGoodsComboBean.getStorePrice();

        String str = new String("原价    ¥" + storePrice);

        int startIndex = str.indexOf("¥") + 1;

        SpannableString sp = new SpannableString(str);

        StrikethroughSpan span = new StrikethroughSpan();
        sp.setSpan(span, startIndex, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvStorePrice.setText(sp);
    }

    private void initView()
    {
        setTitleNmae("订单");

        initRoomDetailView();

        String[] nameArray = getResources().getStringArray(R.array.mail_name_array);

        tvMailType.setText(nameArray[selectedApplicantIndex]);
        //解析数据
        boolean comboBeanFlag = getIntent().hasExtra("dataDetail");

        if (comboBeanFlag) {

            extraGoodsComboBean = (CardRightsInfoBean) getIntent().getSerializableExtra("dataDetail");

            fuzengquanyi = getIntent().getStringExtra("fuzengquanyi");

        }

        List<OverbalanceGoodsBean> overbalanceGoodsBeanList = new ArrayList<OverbalanceGoodsBean>();

        overbalanceGoodsItemAdapter = new OverbalanceGoodsItemAdapter(this, overbalanceGoodsBeanList);

        overbalanceGoodsItemAdapter.setStateChangeCallBack(callBack);

        WholeRecyclerView wvOverbancegoods = (WholeRecyclerView) findViewById(R.id.wvOverbancegoods);

        wvOverbancegoods.setLayoutManager(new LinearLayoutManager(this));

        wvOverbancegoods.setAdapter(overbalanceGoodsItemAdapter);

        findViewById(R.id.tv_commit).setOnClickListener(this);

        etEmailAddress.setOnClickListener(this);


        //历史数据

        String linkMan = SharePrefUtil.getValueFromSp(this,SharePrefUtil.HOTEL_OTHER_PRODUCT_LINKMAN_PHONE,null);

        if(!TextUtils.isEmpty(linkMan)){

            PassengerInfoBean passengerInfoBean = JSONObject.parseObject(linkMan,PassengerInfoBean.class);

            et_user_phone.setText(passengerInfoBean.getMobile());

            etLinkManName.setText(passengerInfoBean.getZhName());
        }
    }


    /**
     * 订单详情view；
     */
    private void initRoomDetailView()
    {

        tvPayMethodType = (TextView) findViewById(R.id.tvPayMethodType);

        ivBottomArrow = (ImageView) findViewById(R.id.ivBottomArrow);

        ll_bottom = findViewById(R.id.ll_bottom);

        tvRoomDate = (TextView) findViewById(R.id.tvRoomDate);

        tvRoomName = (TextView) findViewById(R.id.tvRoomName);

        tvRoomInfoDesc = (TextView) findViewById(R.id.tvRoomInfoDesc);

        tvGuestNum = (TextView) findViewById(R.id.tvGuestNum);

        tvInvoice = (TextView) findViewById(R.id.tvInvoice);

        tvGuestNum.setText(currentGuesterNumber + "");

        findViewById(R.id.ivSubstration).setOnClickListener(this);

        findViewById(R.id.ivAddGuest).setOnClickListener(this);

        findViewById(R.id.llBill).setOnClickListener(this);

        findViewById(R.id.iv_open_contact).setOnClickListener(this);

        et_user_phone = (EditText) findViewById(R.id.et_user_phone);

        etLinkManName = (EditText) findViewById(R.id.etLinkManName);

        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);

        tvStorePrice = (TextView) findViewById(R.id.tvStorePrice);

        findViewById(R.id.tv_commit).setOnClickListener(this);

        findViewById(R.id.panelOrderDetail).setOnClickListener(this);

        llPlusGoods = (LinearLayout) findViewById(R.id.llPlusGoods);

        llDate.setOnClickListener(this);

        findViewById(R.id.llGetType).setOnClickListener(this);

        findViewById(R.id.llEmailAddressTemp).setOnClickListener(this);

        etEmailAddress.setFocusable(false);

        etEmailAddress.setFocusableInTouchMode(false);

        etEmailAddress.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.panelOrderDetail:

                showOrderDetailPop();

                break;

            case R.id.tv_commit:

                if (TextUtils.isEmpty(tvEffDateStr)) {

                    ToastUtil.i(CardRightsOrderActivity.this, "请选择生效日期");

                    return;
                }

                if (TextUtils.isEmpty(etLinkManName.getText().toString().trim())) {

                    ToastUtil.i(CardRightsOrderActivity.this, "请输入使用者姓名");

                    return;
                }

                if (TextUtils.isEmpty(et_user_phone.getText().toString().trim())) {

                    ToastUtil.i(CardRightsOrderActivity.this, "请输入联系人手机号");

                    return;
                }

                if( !ValidatorUtil.isMobile(et_user_phone.getText().toString().trim())){

                    ToastUtil.i(CardRightsOrderActivity.this, R.string.toast_chech_your_phone_number);

                    return;
                }

                showPDialog("正在请求数据……");
                hotelProductOrderPresenter.sendRightsOrderCreateRequest();

                break;

            case R.id.ivSubstration:

                if (currentGuesterNumber > 1) {

                    currentGuesterNumber = currentGuesterNumber - 1;

                    guesterHandler.sendEmptyMessage(0);
                } else {
                    ToastUtil.i(this, "订购数量最少为1");
                }


                break;

            case R.id.ivAddGuest:
                if (currentGuesterNumber < 100) {

                    currentGuesterNumber = currentGuesterNumber + 1;

                    guesterHandler.sendEmptyMessage(0);

                } else {
                    ToastUtil.i(this, "订购数量最多为99");
                }
                break;
            case R.id.llBill:

                String totalPrice = tvTotalAmount.getText().toString();

                Intent intent = new Intent(this, HotelBillActivity.class);

                intent.putExtra("totalPrice", totalPrice);

                if (invoiceInfoParam != null) {
                    intent.putExtra("invoiceDataBean", invoiceInfoParam);
                }
                startActivityForResult(intent, 1001);

                break;
            case R.id.iv_open_contact:
                ContactUtil.startContactsActivity(this, REQ_CONTACT_LXR);
                break;
            case R.id.llDate://生效日期

                // if (appCalendarPopup == null) {

                AppCalendarPopup appCalendarPopup = new AppCalendarPopup(CardRightsOrderActivity.this, -1, BaseFullPP.ViewPostion.VIEW_BOTTOM, false);

                appCalendarPopup.setType(AppCalendarPopup.CalendarType.SINGLE);

                appCalendarPopup.setTitleName("请选择有效日期");

                appCalendarPopup.setCalendarCallBack(calendarDataCallBack);

                //   }

                View rootView = getWindow().getDecorView();

                appCalendarPopup.showBottomByViewPP(rootView);

                break;
            case R.id.llGetType:

                if (pp == null) {

                    String[] nameArray = getResources().getStringArray(R.array.mail_name_array);

                    int[] nameValueArray = getResources().getIntArray(R.array.mail_value_array);

                    pp = new BottomDataListPP(this, -1);

                    pp.setGone();

                    pp.setLogicData(nameArray, nameValueArray);

                    pp.setSelectIndex(selectedApplicantIndex);

                    pp.setCallBack(callHHBack);
                }

                pp.showPP(getWindow().getDecorView());

                break;
            case R.id.etEmailAddress:
            case R.id.llEmailAddressTemp://邮寄地址

                Intent addressIntent = new Intent(this, AddressManageActivity.class);
                addressIntent.putExtra("flag", AddressManageActivity.GET_ADDRESS_DATA);
                startActivityForResult(addressIntent, REQ_GET_ADDRESS);

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

            int what = msg.what;

            calTotalMoney();

            tvGuestNum.setText(currentGuesterNumber + "");
        }


    };

    private BottomDataListPP.BottomDataListCallBack callHHBack = new BottomDataListPP.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {
            tvMailType.setText(name);

            receiveType = nameValue;

            if(nameValue == 1){//门店自取

                llEmailAddress.setVisibility(View.GONE);

            }else if(nameValue == 2){//邮寄

                llEmailAddress.setVisibility(View.VISIBLE);
            }

        }
    };

    private AppCalendarPopup.DataCalendarCallBack calendarDataCallBack = new AppCalendarPopup.DataCalendarCallBack() {
        @Override
        public void onSelectorStartEndDate(Date startDate, Date endDate)
        {

            tvEffDateStr = DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE);


            tvEffDate.setText(tvEffDateStr);
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
        if (ppDataList == null) {

            ppDataList = new ArrayList<HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean>();

        } else {

            ppDataList.clear();

        }

        //在线支付数据
        HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneFather = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
        oneFather.ifFather = true;
        oneFather.productNameStr = "在线支付";
        oneFather.detailPrice = Float.parseFloat(tvTotalAmount.getText().toString());
        ppDataList.add(oneFather);

        //产品信息
        HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneToTwoChilder = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
        oneToTwoChilder.ifFather = false;
        oneToTwoChilder.productNameStr = extraGoodsComboBean.getGiftsName();
        oneToTwoChilder.detailPrice = extraGoodsComboBean.getOnlinePrice() * currentGuesterNumber;
        ppDataList.add(oneToTwoChilder);

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
                oneToOneChilder.productNameStr = "发票快递费";
                oneToOneChilder.detailPrice = expressAmount;
                ppDataList.add(oneToOneChilder);
            }

        }

        //实体邮寄费
        if( receiveType == 2){

            float mailMoney = Float.parseFloat(tvQuickPrice.getText().toString());

            if (mailMoney > 0) {
                HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean oneToOneChilder = new HotelProductOrderDetailsPopup.HotelProductOrderDetailPpBean();
                oneToOneChilder.ifFather = false;
                oneToOneChilder.productNameStr = "实体券快递费";
                oneToOneChilder.detailPrice = mailMoney;
                ppDataList.add(oneToOneChilder);
            }
        }


        if (hotelStarPricePopup == null) {

            hotelStarPricePopup = new HotelProductOrderDetailsPopup(CardRightsOrderActivity.this, -1, BaseFullPP.ViewPostion.VIEW_BOTTOM);

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


    private void calTotalMoney()
    {
        //产品价格总价
        int totalMoney = extraGoodsComboBean.getOnlinePrice() * currentGuesterNumber;

        //发票快递费
        float expressAmount = 0;
        if (invoiceInfoParam != null) {

            expressAmount = invoiceInfoParam.getExpressAmount();
        }

        //物件快递快递费
        float emailMoney = 0;

        if( receiveType == 2){

            emailMoney = Float.parseFloat(tvQuickPrice.getText().toString());
        }

        //超值加购总价
        float goodsPlushMoney = 0f;

        List<OverbalanceGoodsBean> list = overbalanceGoodsItemAdapter.getData();

        int size = list.size();

        if (size > 0) {

            for (OverbalanceGoodsBean bean : list) {

                int number = bean.getNumber();

                if (number > 0) {

                    goodsPlushMoney = goodsPlushMoney + bean.getNumber() * Float.parseFloat(bean.getPrice());
                }
            }
        }

        float totalPrice = (float) totalMoney + goodsPlushMoney + expressAmount+emailMoney;

        tvTotalAmount.setText(Utils.subZeroAndDot(totalPrice + ""));

    }

    private OverbalanceGoodsItemAdapter.IStateCallBack callBack = new OverbalanceGoodsItemAdapter.IStateCallBack() {
        @Override
        public void updatePriceAction(OverbalanceGoodsBean bean)
        {
            calTotalMoney();
        }
    };

    private InvoiceInfoParam invoiceInfoParam = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null) {
            return;
        }
        switch (requestCode) {

            case REQ_GET_ADDRESS:  //选择收货地址

                Serializable addressObj = data.getSerializableExtra("addressBeanTemp");
                if (addressObj != null) {
                    addressBean = (GoodsAddressBean) addressObj;

                    etEmailAddress.setText(addressBean.getProvinceName() + addressBean.getCityName() + addressBean.getAddress());

                    loadPostageFee(addressBean.getCityId());
                }
                break;
            case REQ_CONTACT_LXR:  //选择通讯录联系人

                Connector connector = new Connector();
                ContactUtil.getConnector(this, data, connector);
                et_user_phone.setText(connector.getMobile());
                etLinkManName.setText(connector.getNickName());

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
                }

                break;
            default:
                break;
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

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        dimissPdialog();

        if (HotelProductOrderPresenter.RIGHTSORDERCREATECODE == type) {

            finish();
            //查询用户的代金券信息
            //发放平台
            int issuePlatform = 0;
            //行业id
            int industryId = Integer.parseInt(AppConstant.hotel_id);//酒店行业id;

            GoldTicketParam goldTicketParam = new GoldTicketParam();

            goldTicketParam.setIssuePlatform(issuePlatform);

            goldTicketParam.setIndustryId(industryId);

            goldTicketParam.setShopId(extraGoodsComboBean.getShopId());

            goldTicketParam.setStoreId(extraGoodsComboBean.getHotelId());

            goldTicketParam.setGoodsId(extraGoodsComboBean.getActId());

            PayOrderBean bean = JSONObject.parseObject(o + "", PayOrderBean.class);

            Intent intent = new Intent(CardRightsOrderActivity.this, AppPreferentialPaymentActivity.class);

            intent.putExtra("orderData", bean);

            intent.putExtra("goldTicketParam", goldTicketParam);

            intent.putExtra("goodName", extraGoodsComboBean.getGiftsName() + "(" + extraGoodsComboBean.getShopName() + ")");

            startActivity(intent);

        } else if (HotelProductOrderPresenter.QUERYGOODSPLUSC0DE == type) {

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
        dimissPdialog();
    }

    @Override
    public List<PassengerInfoBean> getPassengerList()
    {
        return null;
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

        param.setShopId(extraGoodsComboBean.getShopId());

        param.setHotelId(extraGoodsComboBean.getHotelId());

        param.setActId(extraGoodsComboBean.getActId());

        param.setGoodsQuantity(getRoomCount());

        param.setEffDate(tvEffDateStr);

        param.setReceiveType(receiveType);

        double moneyD = Double.parseDouble(tvTotalAmount.getText().toString());

        param.setOrderAmount(moneyD);

        param.setContactsInfo(getContactParams());

        param.setInvoiceInfo(invoiceInfoParam);

        String name = etLinkManName.getText().toString();

        param.setUseName(name);

        if( receiveType == 2){

            GoodsAddressBean bean = new GoodsAddressBean();

            bean.setContacts(name);

            String phone = et_user_phone.getText().toString();

            bean.setPhone(phone);

            bean.setDistrictId(addressBean.getDistrictId());

            bean.setAddress(etEmailAddress.getText().toString());

            bean.setFee(tvQuickPrice.getText().toString());

            param.setDeliveryAddress(bean);

        }else {
            param.setDeliveryAddress(null);
        }

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

        param.setInvoiceInfo(invoiceInfoParam);

        return param;
    }

    /**
     * 联系人信息
     */
    private Map<String, Object> getContactParams()
    {
        Map<String, Object> contactParams = new HashMap<>();
        String name = etLinkManName.getText().toString();
        contactParams.put("name", name); //联系人姓名
        String phone = et_user_phone.getText().toString().trim();
        contactParams.put("ophone", phone); //联系人姓名
        return contactParams;
    }

    @Override
    public void onGetPostFeeSucess(Object data)
    {
        if (data != null) {

            List<PostFeeBean> postFeeBeans = (List<PostFeeBean>) data;

            if (postFeeBeans != null && postFeeBeans.size() > 0) {

                PostFeeBean postFeeBean = postFeeBeans.get(0);

                tvQuickPrice.setText(Utils.subZeroAndDot(postFeeBean.getFee() + ""));

                calTotalMoney();
            }
        }
    }

    @Override
    public void onGetPostFeeFail(String failMsg)
    {

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        String linkManPhone = et_user_phone.getText().toString().trim();
        String etLinkManNameStr = etLinkManName.getText().toString().trim();

        PassengerInfoBean passengerInfoBean = new PassengerInfoBean();

        passengerInfoBean.setZhName(etLinkManNameStr);

        passengerInfoBean.setMobile(linkManPhone);

        String passengerInfoBeanStr = JSON.toJSONString(passengerInfoBean);

        SharePrefUtil.saveToSp(this,SharePrefUtil.HOTEL_OTHER_PRODUCT_LINKMAN_PHONE,passengerInfoBeanStr);
    }
}

