package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
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
import com.yzb.card.king.bean.giftcard.FlashSaleInfoBean;
import com.yzb.card.king.bean.giftcard.GiftsIncrementBean;
import com.yzb.card.king.bean.hotel.GoodsPlusParam;
import com.yzb.card.king.bean.hotel.HotelOrderParam;
import com.yzb.card.king.bean.hotel.InvoiceInfoParam;
import com.yzb.card.king.bean.ticket.PostFeeBean;
import com.yzb.card.king.sys.AppConstant;
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
 * 类  名：限时抢购下单页面
 * 作  者：Li Yubing
 * 日  期：2017/9/12
 * 描  述：
 */
@ContentView(R.layout.activity_flashsaleproduct_order)
public class FlashSaleProductOrderActivity extends BaseActivity implements HotelRoomCreateOrderView, View.OnClickListener {

    //常理
    private static final int REQ_CONTACT_LXR = 2; //获取通讯录联系人；

    @ViewInject(R.id.tvMailType)
    private TextView tvMailType;

    @ViewInject(R.id.llDuifang)
    private LinearLayout llDuifang;

    private TextView tvRoomDate, tvRoomName, tvTotalAmount, tvStorePrice, tvInvoice, tvPayMethodType;

    private ImageView ivBottomArrow;

    private EditText et_user_phone, etLinkManName;

    private LinearLayout llPlusGoods;

    private FlashSaleInfoBean extraGoodsComboBean;

    private BottomDataListPP pp = null;

    private HotelProductOrderPresenter hotelProductOrderPresenter;


    private OverbalanceGoodsItemAdapter overbalanceGoodsItemAdapter;


    private View ll_bottom;

    private int selectedApplicantIndex = 0;

    private int receiveType = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        if (extraGoodsComboBean != null) {

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

        tvRoomDate.setText(extraGoodsComboBean.getActName());

        StringBuffer sb = new StringBuffer();

        sb.append("有效期：");

        sb.append(extraGoodsComboBean.getEffDateDesc());

        List<GiftsIncrementBean> giftsIncrementBeenList = extraGoodsComboBean.getGiftsIncrementBeenList();

        if (giftsIncrementBeenList != null) {

            StringBuffer giftSb = new StringBuffer();

            int size = giftsIncrementBeenList.size();

            for (int i = 0; i < size; i++) {

                GiftsIncrementBean giftsIncrementBean = giftsIncrementBeenList.get(i);

                giftSb.append(giftsIncrementBean.getItemName()).append(giftsIncrementBean.getItemQuantity()).append("份");
                if (i == size) {

                } else {

                    giftSb.append("\n");
                }

            }

            sb.append("\n").append(giftSb.toString());

        }
        tvRoomName.setText(sb.toString());


        tvPayMethodType.setText("在线支付");

        tvTotalAmount.setText(Utils.subZeroAndDot(extraGoodsComboBean.getOnlinePrice() + ""));

        float storePrice = extraGoodsComboBean.getStorePrice();

        String str = new String("门店价    ¥" + Utils.subZeroAndDot(storePrice+""));

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

            extraGoodsComboBean = (FlashSaleInfoBean) getIntent().getSerializableExtra("dataDetail");

        }

        List<OverbalanceGoodsBean> overbalanceGoodsBeanList = new ArrayList<OverbalanceGoodsBean>();

        overbalanceGoodsItemAdapter = new OverbalanceGoodsItemAdapter(this, overbalanceGoodsBeanList);

        overbalanceGoodsItemAdapter.setStateChangeCallBack(callBack);

        WholeRecyclerView wvOverbancegoods = (WholeRecyclerView) findViewById(R.id.wvOverbancegoods);

        wvOverbancegoods.setLayoutManager(new LinearLayoutManager(this));

        wvOverbancegoods.setAdapter(overbalanceGoodsItemAdapter);

        findViewById(R.id.tv_commit).setOnClickListener(this);

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

        tvInvoice = (TextView) findViewById(R.id.tvInvoice);

        findViewById(R.id.llBill).setOnClickListener(this);

        findViewById(R.id.iv_open_contact).setOnClickListener(this);

        et_user_phone = (EditText) findViewById(R.id.et_user_phone);

        etLinkManName = (EditText) findViewById(R.id.etLinkManName);

        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);

        tvStorePrice = (TextView) findViewById(R.id.tvStorePrice);

        findViewById(R.id.tv_commit).setOnClickListener(this);

        findViewById(R.id.panelOrderDetail).setOnClickListener(this);

        llPlusGoods = (LinearLayout) findViewById(R.id.llPlusGoods);

        findViewById(R.id.llGetType).setOnClickListener(this);


    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.panelOrderDetail:

                showOrderDetailPop();

                break;

            case R.id.tv_commit:


                if (receiveType == 2 && TextUtils.isEmpty(etLinkManName.getText().toString().trim())) {

                    ToastUtil.i(FlashSaleProductOrderActivity.this, "请输入对方账户手机号");

                    return;
                }

                if (TextUtils.isEmpty(et_user_phone.getText().toString().trim())) {

                    ToastUtil.i(FlashSaleProductOrderActivity.this, "请输入联系人手机号");

                    return;
                }
                if( !ValidatorUtil.isMobile(et_user_phone.getText().toString().trim())){

                    ToastUtil.i(FlashSaleProductOrderActivity.this, R.string.toast_chech_your_phone_number);

                    return;
                }
                showPDialog("正在请求数据……");
                hotelProductOrderPresenter.sendFlashSaleOrderCreateRequest();

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
            case R.id.llGetType:

                if (pp == null) {

                    String[] nameArray = getResources().getStringArray(R.array.jisong_type_name_array);

                    int[] nameValueArray = getResources().getIntArray(R.array.jisong_type_value_array);

                    pp = new BottomDataListPP(this, -1);

                    pp.setGone();

                    pp.setLogicData(nameArray, nameValueArray);

                    pp.setSelectIndex(selectedApplicantIndex);

                    pp.setCallBack(callHHBack);
                }

                pp.showPP(getWindow().getDecorView());

                break;

            default:
                break;
        }
    }


    private BottomDataListPP.BottomDataListCallBack callHHBack = new BottomDataListPP.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {
            tvMailType.setText(name);

            receiveType = nameValue;

            if (nameValue == 1) {//

                llDuifang.setVisibility(View.GONE);

            } else if (nameValue == 2) {//

                llDuifang.setVisibility(View.VISIBLE);
            }

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
        oneToTwoChilder.productNameStr = extraGoodsComboBean.getActName();
        oneToTwoChilder.detailPrice = extraGoodsComboBean.getOnlinePrice();
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


        if (hotelStarPricePopup == null) {

            hotelStarPricePopup = new HotelProductOrderDetailsPopup(FlashSaleProductOrderActivity.this, -1, BaseFullPP.ViewPostion.VIEW_BOTTOM);

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
        float totalMoney = extraGoodsComboBean.getOnlinePrice();

        //发票快递费
        float expressAmount = 0;
        if (invoiceInfoParam != null) {

            expressAmount = invoiceInfoParam.getExpressAmount();
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

        float totalPrice = (float) totalMoney + goodsPlushMoney + expressAmount;

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


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        dimissPdialog();

        if (HotelProductOrderPresenter.FLASHSALEORDERCREATECODE == type) {

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

            Intent intent = new Intent(FlashSaleProductOrderActivity.this, AppPreferentialPaymentActivity.class);

            intent.putExtra("orderData", bean);

            intent.putExtra("goldTicketParam", goldTicketParam);

            intent.putExtra("goodName", extraGoodsComboBean.getActName() + "(" + extraGoodsComboBean.getShopName() + ")");

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
        return 0;
    }

    @Override
    public HotelOrderParam collectHotelRoomOrderParam()
    {
        HotelOrderParam param = new HotelOrderParam();

        param.setActType(extraGoodsComboBean.getActType());

        param.setActId(extraGoodsComboBean.getActId());

        param.setShopId(extraGoodsComboBean.getShopId());

        param.setHotelId(extraGoodsComboBean.getHotelId());

        param.setBankId(extraGoodsComboBean.getBankId());

        param.setGoodsQuantity(1);

        param.setReceiveType(receiveType);

        if (receiveType == 2) {
            param.setUseName(etLinkManName.getText().toString().trim());
        }


        double moneyD = Double.parseDouble(tvTotalAmount.getText().toString());

        param.setOrderAmount(moneyD);

        param.setContactsInfo(getContactParams());

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


}


