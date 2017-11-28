package com.yzb.card.king.ui.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.common.InsuranceBean;
import com.yzb.card.king.bean.common.PayFormIf;
import com.yzb.card.king.bean.common.PayOrderBean;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.hotel.InvoiceInfoParam;
import com.yzb.card.king.bean.order.BaseHotelOrderInfoBean;
import com.yzb.card.king.bean.order.DeductionOrderBean;
import com.yzb.card.king.bean.order.GoodsInfoBean;
import com.yzb.card.king.bean.order.GoodsPlusOrderBean;
import com.yzb.card.king.bean.order.HotelOrderDetailBean;
import com.yzb.card.king.bean.order.HotelOrderServeBean;
import com.yzb.card.king.bean.order.HotelRoomOrderBean;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.order.OrderInvoiceInfoBean;
import com.yzb.card.king.bean.order.ProductBaseOrderBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.activity.HotelBillActivity;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.activity.pay.AppPreferentialPaymentActivity;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.activtiy.HotelProductInfoActivity;
import com.yzb.card.king.ui.my.presenter.CommandPresenter;
import com.yzb.card.king.ui.my.view.CommandView;
import com.yzb.card.king.ui.order.dialog.HotelReturenMoneySucceedDialog;
import com.yzb.card.king.ui.order.dialog.OrderMessagFuctioneDialog;
import com.yzb.card.king.ui.order.presenter.HotelTicketsDetailPresenter;
import com.yzb.card.king.ui.other.activity.NearByMapActivity;
import com.yzb.card.king.ui.other.activity.PjActivity;
import com.yzb.card.king.ui.other.activity.WypjActivity;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.OrderUtils;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 酒店订单详情
 */
@ContentView(R.layout.activity_hotel_order_detail)
public class HotelOrderDetailActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface, CommandView {

    @ViewInject(R.id.llConfirmCancel)
    private LinearLayout llConfirmCancel;

    @ViewInject(R.id.tvCancelOrderMsg)
    private TextView tvCancelOrderMsg;

    @ViewInject(R.id.tvOrderTuikuan)
    public TextView tvOrderTuikuan;

    @ViewInject(R.id.tvOrderQuxiaoTuikuan)
    public TextView tvOrderQuxiaoTuikuan;

    @ViewInject(R.id.tvOrderFunction)
    public TextView tvOrderFunction;

    @ViewInject(R.id.tvOrderDel)
    public TextView tvOrderDel;

    @ViewInject(R.id.tvOrderPingjia)
    public TextView tvOrderPingjia;

    @ViewInject(R.id.tvOrderFanxian)
    public TextView tvOrderFanxian;

    @ViewInject(R.id.tvOrderPay)
    public TextView tvOrderPay;

    @ViewInject(R.id.tvOrderAgain)
    public TextView tvOrderAgain;

    @ViewInject(R.id.tvOtherProductName)
    private TextView tvOtherProductName;

    @ViewInject(R.id.tvHotelOtherProductPhone)
    private TextView tvHotelOtherProductPhone;

    @ViewInject(R.id.llHotelOtherProductPhone)
    private LinearLayout llHotelOtherProductPhone;

    @ViewInject(R.id.vOtherLine)
    private View vOtherLine;

    @ViewInject(R.id.llOneOrder)
    private LinearLayout llOneOrder;

    @ViewInject(R.id.tvOrderStatusMsg)
    public TextView tvOrderStatusMsg;

    @ViewInject(R.id.tvOtherOrderMoney)
    private TextView tvOtherOrderMoney;

    @ViewInject(R.id.tvOrderPromt)
    public TextView tvOrderPromt;

    @ViewInject(R.id.quesation_layout)
    private RelativeLayout quesation_layout;

    @ViewInject(R.id.tvFunctionOne)
    public TextView tvFunctionOne;

    @ViewInject(R.id.tvFunctionTwo)
    public TextView tvFunctionTwo;

    @ViewInject(R.id.tvFunctionThree)
    public TextView tvFunctionThree;

    @ViewInject(R.id.question2)
    private LinearLayout question2;
    //卡权益收货人信息
    @ViewInject(R.id.llCardBenefit)
    private LinearLayout llCardBenefit;

    @ViewInject(R.id.llRoomOrderHotel)
    private LinearLayout llRoomOrderHotel;

    @ViewInject(R.id.llOtherOrderHotel)
    private LinearLayout llOtherOrderHotel;

    //电话号码
    @ViewInject(R.id.tvHotelPhoneNumber)
    private TextView tvHotelPhoneNumber;

    @ViewInject(R.id.tvSupplierPhoneNumber)
    private TextView tvSupplierPhoneNumber;

    //发票消息
    @ViewInject(R.id.tvSuoquFapiao)
    private TextView tvSuoquFapiao;

    @ViewInject(R.id.tvFapiaoInfo)
    private TextView tvFapiaoInfo;

    //订单操作时间
    @ViewInject(R.id.tvHotelOrderHandleTimeInfo)
    private TextView tvHotelOrderHandleTimeInfo;

    //酒店产品信息
    @ViewInject(R.id.tvProductName)
    private TextView tvProductName;

    //房间订单入住人以及订单信息
    @ViewInject(R.id.tvHotelOrderLinkeManInfo)
    private TextView tvHotelOrderLinkeManInfo;

    //酒店订单明细
    @ViewInject(R.id.llHotelOrderDetail)
    private LinearLayout llHotelOrderDetail;
    /**
     * 订单功能操作
     */
    @ViewInject(R.id.tvHotelOrderPay)
    public TextView tvHotelOrderPay;

    @ViewInject(R.id.tvHotelOrderCancel)
    private TextView tvHotelOrderCancel;

    @ViewInject(R.id.tvHotelOrderDel)
    public TextView tvHotelOrderDel;

    @ViewInject(R.id.tvHotelOrderRefund)
    public TextView tvHotelOrderRefund;

    @ViewInject(R.id.tvHotelOrderCancelRefund)
    public TextView tvHotelOrderCancelRefund;

    @ViewInject(R.id.tvDeliveryStatus)
    private TextView tvDeliveryStatus;//配送状态

    @ViewInject(R.id.tvConsigneeInfo)
    private TextView tvConsigneeInfo;//收货人信息

    private String orderId;

    private String hotelType;

    private CommandPresenter commandPresenter;

    private TextView hotelsName, address,
            transportion, foodTv, amusement, landscape;

    private HotelTicketsDetailPresenter hotelTicketsDetailPresenter;

    private ProductBaseOrderBean orderInfo;

    private BaseHotelOrderInfoBean hotelInfo;

    private HotelRoomOrderBean roomInfo;

    private GoodsInfoBean goodsInfoBean;

    private ShareDialogFragment shareDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        colorStatusResId = R.color.color_44698f;
        super.onCreate(savedInstanceState);

        commandPresenter = new CommandPresenter(this);

        findView();
        initData();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        initRequest();
    }

    public void initData()
    {
        if (!getIntent().hasExtra("id")) {
            return;
        }
        if (!getIntent().hasExtra("hotelType")) {
            return;
        }
        orderId = getIntent().getStringExtra("id");
        if (StringUtils.isEmpty(orderId)) {
            return;
        }

        hotelType = getIntent().getStringExtra("hotelType");

        if (StringUtils.isEmpty(orderId)) {
            return;
        }

        ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);
        initRequest();

    }

    private void initRequest()
    {

        String typeCode = hotelType;

        String serviceName = "";

        if (HoteUtil.HOTEL_ROOM_CODE.equals(typeCode)) {//房间碎片

            serviceName = CardConstant.ORDER_HOTELS_DETAIL;

        } else if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(typeCode) || HoteUtil.HOTEL_BAR_CODE.equals(typeCode)
                || HoteUtil.HOTEL_SPA_CODE.equals(typeCode) || HoteUtil.HOTEL_LOBBY_BAR_CODE.equals(typeCode)
                || HoteUtil.HOTEL_GYM_CODE.equals(typeCode) || HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(typeCode)) {

            serviceName = CardConstant.ORDER_SELECTHOTELOTHERORDERINFO_DETAIL;

        } else if (HoteUtil.HOTEL_CARD_EQUITY_CODE.equals(typeCode)) {//卡权益

            serviceName = CardConstant.ORDER_SELECTRIGHTSORDERINFO;

        } else if (HoteUtil.HOTEL_FAST_BUY_CODE.equals(typeCode)) {//限时抢购

            serviceName = CardConstant.ORDER_SELECTFLASHSALEORDERINFO;
        }

        hotelTicketsDetailPresenter = new HotelTicketsDetailPresenter(this);

        Map<String, Object> map = new HashMap<>();

        map.put("orderId", orderId);

        hotelTicketsDetailPresenter.sendsRequest(serviceName, map);
    }

    public void findView()
    {
        transportion = (TextView) findViewById(R.id.transportion);

        transportion.setOnClickListener(this);

        findViewById(R.id.share_hotel).setOnClickListener(this);

        tvSupplierPhoneNumber.setOnClickListener(this);

        tvHotelPhoneNumber.setOnClickListener(this);

        findViewById(R.id.panelBack).setOnClickListener(this);

        foodTv = (TextView) findViewById(R.id.food_tv);
        foodTv.setOnClickListener(this);
        amusement = (TextView) findViewById(R.id.amusement);
        amusement.setOnClickListener(this);
        landscape = (TextView) findViewById(R.id.landscape);
        landscape.setOnClickListener(this);
        TextView gouwu = (TextView) findViewById(R.id.gouwu);
        gouwu.setOnClickListener(this);
        TextView jiud = (TextView) findViewById(R.id.jiud);
        jiud.setOnClickListener(this);

        findViewById(R.id.hotel_detail_Rl).setOnClickListener(this);

        findViewById(R.id.panelShare).setOnClickListener(this);

        findViewById(R.id.address_layout).setOnClickListener(this);

        tvHotelOrderPay.setOnClickListener(this);

        hotelsName = (TextView) findViewById(R.id.hotels_name);
        address = (TextView) findViewById(R.id.address);

        llHotelOrderDetail.removeAllViews();

        llHotelOtherProductPhone.setOnClickListener(this);

        tvOtherProductName.setOnClickListener(this);

        tvOrderStatusMsg.setOnClickListener(this);

        findViewById(R.id.llCardBenefit).setOnClickListener(this);

        tvProductName.setOnClickListener(this);

        findViewById(R.id.appShopPhone).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.panelBack:
                finish();
                break;

            case R.id.share_hotel:

                if (hotelInfo == null) {

                    return;
                }

                if (shareDialogFragment == null) {
                    showPDialog(R.string.loading);
                    shareDialogFragment = ShareDialogFragment.getInstance("", "");
                    shareDialogFragment.setContent(HotelOrderDetailActivity.this);
                    shareDialogFragment.setTitle(hotelInfo.getHotelName());
                    //  shareDialogFragment.setImage(ServiceDispatcher.getImageUrl(hotel.getDefaultImgUrl()));
                    shareDialogFragment.setContent("快来点开看看吧~");
                    shareDialogFragment.initShareContent(AppConstant.command_type_shop, hotelInfo.getHotelId(), AppConstant.hotel_id);

                } else {
                    shareDialogFragment.show(getSupportFragmentManager(), "");
                }

                break;

            case R.id.tvOrderFanxian://申请返现

                ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);

                Map<String, Object> map = new HashMap<>();

                map.put("orderId", orderId);

                hotelTicketsDetailPresenter.sendsRequestOne(CardConstant.ORDER_APPLYCASHCACK, map);

                break;

            case R.id.tvHotelOrderDel:
            case R.id.tvOrderDel://删除

                String cancelDateStr = "订单删除后不可恢复，是否删除订单？";

                orderHandlerFunction(cancelDateStr, OrderMessagFuctioneDialog.FUCTION_ORDER_DEL_CODE);

                break;
            case R.id.tvHotelOrderPay:
            case R.id.tvOrderPay://支付，进入特惠付款
                payAciton();
                break;
            case R.id.panelShare://分享酒店
                if (hotelInfo != null)
                    shareHotel();
                break;
            case R.id.assessRL:// 评价酒店
                if (hotelInfo != null)
                    assessHotel();
                break;

            case R.id.tvOrderTuikuan:
            case R.id.tvHotelOrderRefund://退款(房间无退款操作功能)

                orderHandlerFunction("是否申请退款？", OrderMessagFuctioneDialog.FUCTION_ORDER_REFUND_CODE);

                break;
            case R.id.tvHotelOrderCancelRefund:
            case R.id.tvOrderQuxiaoTuikuan://取消退款 (房间无取消退款操作功能)
                if (orderInfo == null) {

                    return;
                }
                //检测退款信息
                ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);

                Map<String, Object> mapRefundMapOtherProduct = new HashMap<>();

                mapRefundMapOtherProduct.put("orderId", orderId);

                hotelTicketsDetailPresenter.refundMoneyCheck(CardConstant.ORDER_REFUNDORDERCHECK, mapRefundMapOtherProduct);

                break;
            case R.id.llConfirmCancel://取消说明
            case R.id.tvHotelOrderCancel:
            case R.id.tvOrderFunction://取消订单

                if (orderInfo == null) {

                    return;
                }

                //检测退款信息
                ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);

                Map<String, Object> mapRefundMap = new HashMap<>();

                mapRefundMap.put("orderId", orderId);

                hotelTicketsDetailPresenter.refundMoneyCheck(CardConstant.ORDER_REFUNDORDERCHECK, mapRefundMap);


                break;
            case R.id.tvOrderAgain://再次预订
            case R.id.tvProductName:
            case R.id.tvOtherProductName:
            case R.id.address_layout:
            case R.id.hotel_detail_Rl://进入酒店详情
                if (hotelInfo != null)
                    bookAgainHotel();
                break;

            case R.id.transportion:// 周边交通
                if (hotelInfo != null)
                    toAround(0);
                break;
            case R.id.food_tv:// 周边餐饮
                if (hotelInfo != null)
                    toAround(1);
                break;
            case R.id.amusement:// 周边娱乐
                if (hotelInfo != null)
                    toAround(2);
                break;
            case R.id.gouwu:// 周边景点
                if (hotelInfo != null)
                    toAround(4);
                break;
            case R.id.jiud:// 周边景点
                if (hotelInfo != null)
                    toAround(5);
                break;

            case R.id.tvHotelPhoneNumber:

                String tvHotelPhoneNumberStr = (String) v.getTag();

                callHotelPhone(tvHotelPhoneNumberStr);

                break;

            case R.id.tvSupplierPhoneNumber:

                String tvSupplierPhoneNumberStr = (String) v.getTag();

                callHotelPhone(tvSupplierPhoneNumberStr);

                break;

            case R.id.appShopPhone://商户联系方式

                callHotelPhone(AppConstant.APP_TEL);
                break;
            case R.id.llHotelOtherProductPhone:

                if (orderInfo == null) {

                    return;
                }
                //供应商电话
                String phoneTelStr = orderInfo.getShopTel();
                callHotelPhone(phoneTelStr);
                break;

            case R.id.tvOrderStatusMsg:
            case R.id.tvOrderPingjia://评价

                if (hotelInfo == null && roomInfo == null) {
                    return;
                }

                Intent intent = new Intent(this, WypjActivity.class);
                intent.putExtra("industryId", Integer.parseInt(AppConstant.hotel_id));
                intent.putExtra("shopId", roomInfo.getShopId());
                intent.putExtra("storeId", hotelInfo.getHotelId());
                intent.putExtra("goodsIdTwo", roomInfo.getRoomsId());
                intent.putExtra("goodsIdThree", roomInfo.getPolicysId());
                intent.putExtra("goodsName", roomInfo.getRoomsName());
                intent.putExtra("orderId", Long.parseLong(orderId));

                startActivity(intent);
                break;
            case R.id.llCardBenefit://快递物流查询

                break;

            case R.id.tvFunctionOne:

                int code1 = (int) v.getTag();

                hotelRoomQuestion(code1);

                break;
            case R.id.tvFunctionTwo:

                int code2 = (int) v.getTag();

                hotelRoomQuestion(code2);


                break;
            case R.id.tvFunctionThree:

                int code3 = (int) v.getTag();
                hotelRoomQuestion(code3);
                break;
            default:
                break;

        }
    }

    private InvoiceInfoParam invoiceInfoParam = null;

    private void hotelRoomQuestion(int code1)
    {

        if (HoteUtil.HOTEL_CUIKUANQUEREN_CODE == code1) {//催确认

            ToastUtil.i(this, "商家已收到信息，正在处理，请等待");

        } else if (HoteUtil.HOTEL_BUKAIFAPIAO_CODE == code1) {//补开发票

            Intent intent = new Intent(this, HotelBillActivity.class);

            intent.putExtra("totalPrice", orderInfo.getActualAmount());

            if (invoiceInfoParam != null) {
                intent.putExtra("invoiceDataBean", invoiceInfoParam);
            }
            startActivityForResult(intent, 1001);


        } else {

            if (bean == null) {

                return;
            }

            HotelOrderServeBean tempTemp = new HotelOrderServeBean();

            tempTemp.setOrderStatus(bean.getOrderInfo().getOrderStatus());

            tempTemp.setCashbackAmount(Float.parseFloat(bean.getOrderInfo().getCashbackAmount() + ""));

            tempTemp.setPaymentType(Integer.parseInt(bean.getOrderInfo().getPaymentType()));

            tempTemp.setOrderId(bean.getOrderInfo().getOrderId());

            tempTemp.setHotelId(bean.getHotelInfo().getHotelId());

            tempTemp.setTel(bean.getHotelInfo().getTel());

            tempTemp.setRoomsName(bean.getRoomsInfo().getRoomsName());

            HoteUtil.hotelQuestionAction(this, tempTemp, code1);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null) {
            return;
        }
        switch (requestCode) {

            case 1001:

                if (1000 == resultCode) {

                    if (data != null) {//tvInvoice

                        if (data.hasExtra("invoiceInfoParam")) {

                            invoiceInfoParam = (InvoiceInfoParam) data.getSerializableExtra("invoiceInfoParam");

                            orderHandlerFunction("是否补开发票？", OrderMessagFuctioneDialog.FUCTION_SUPPLEMENTORDERINVOICE_CODE);

                        }
                    }
                }

                break;
            default:
                break;
        }
    }

    /**
     * 支付
     */
    private void payAciton()
    {
        //查询用户的代金券信息
        //发放平台
        int issuePlatform = 0;
        //行业id
        int industryId = Integer.parseInt(AppConstant.hotel_id);//酒店行业id

        if (HoteUtil.HOTEL_ROOM_CODE.equals(hotelType)) {
            if (orderInfo == null || roomInfo == null || hotelInfo == null) {

                return;
            }

            GoldTicketParam goldTicketParam = new GoldTicketParam();

            goldTicketParam.setIssuePlatform(issuePlatform);

            goldTicketParam.setIndustryId(industryId);

            goldTicketParam.setShopId(roomInfo.getShopId());

            goldTicketParam.setStoreId(hotelInfo.getHotelId());

            goldTicketParam.setGoodsId(roomInfo.getRoomsId());

            PayOrderBean bean = new PayOrderBean();

            bean.setOrderAmount(orderInfo.getOrderAmount());

            bean.setNotifyUrl(orderInfo.getNotifyUrl());

            bean.setOrderId(orderInfo.getOrderId());

            bean.setOrderNo(orderInfo.getOrderNo());

            bean.setOrderTime(orderInfo.getOrderCreateTime());

            Intent intent = new Intent(this, AppPreferentialPaymentActivity.class);

            intent.putExtra("orderData", bean);

            intent.putExtra("goldTicketParam", goldTicketParam);

            intent.putExtra("goodName", hotelInfo.getHotelName() + "(" + roomInfo.getRoomsName() + ")");

            startActivity(intent);

        } else if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(hotelType) || HoteUtil.HOTEL_BAR_CODE.equals(hotelType)
                || HoteUtil.HOTEL_SPA_CODE.equals(hotelType) || HoteUtil.HOTEL_LOBBY_BAR_CODE.equals(hotelType)
                || HoteUtil.HOTEL_GYM_CODE.equals(hotelType) || HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(hotelType)) {

            if (orderInfo == null || goodsInfoBean == null) {

                return;
            }

            GoldTicketParam goldTicketParam = new GoldTicketParam();

            goldTicketParam.setIssuePlatform(issuePlatform);

            goldTicketParam.setIndustryId(industryId);

            goldTicketParam.setShopId(goodsInfoBean.getShopId());

            goldTicketParam.setStoreId(hotelInfo.getHotelId());

            goldTicketParam.setGoodsId(goodsInfoBean.getPolicysId());

            PayOrderBean bean = new PayOrderBean();

            bean.setOrderAmount(orderInfo.getOrderAmount());

            bean.setNotifyUrl(orderInfo.getNotifyUrl());

            bean.setOrderId(orderInfo.getOrderId());

            bean.setOrderNo(orderInfo.getOrderNo());

            bean.setOrderTime(orderInfo.getOrderCreateTime());

            Intent intent = new Intent(this, AppPreferentialPaymentActivity.class);

            intent.putExtra("orderData", bean);

            intent.putExtra("goldTicketParam", goldTicketParam);

            intent.putExtra("goodName", goodsInfoBean.getGoodsName() + "(" + goodsInfoBean.getPolicysName() + ")");

            startActivity(intent);

        } else if (HoteUtil.HOTEL_CARD_EQUITY_CODE.equals(hotelType)) {

            if (orderInfo == null || hotelInfo == null || goodsInfoBean == null) {

                return;
            }

            GoldTicketParam goldTicketParam = new GoldTicketParam();

            goldTicketParam.setIssuePlatform(issuePlatform);

            goldTicketParam.setIndustryId(industryId);

            goldTicketParam.setShopId(orderInfo.getShopId());

            goldTicketParam.setStoreId(hotelInfo.getHotelId());

            goldTicketParam.setGoodsId(goodsInfoBean.getActId());

            PayOrderBean bean = new PayOrderBean();

            bean.setOrderAmount(orderInfo.getOrderAmount());

            bean.setNotifyUrl(orderInfo.getNotifyUrl());

            bean.setOrderId(orderInfo.getOrderId());

            bean.setOrderNo(orderInfo.getOrderNo());

            bean.setOrderTime(orderInfo.getOrderCreateTime());

            Intent intent = new Intent(this, AppPreferentialPaymentActivity.class);

            intent.putExtra("orderData", bean);

            intent.putExtra("goldTicketParam", goldTicketParam);

            intent.putExtra("goodName", goodsInfoBean.getGiftsName());

            startActivity(intent);

        } else if (HoteUtil.HOTEL_FAST_BUY_CODE.equals(hotelType)) {

            if (orderInfo == null || goodsInfoBean == null) {

                return;
            }

            GoldTicketParam goldTicketParam = new GoldTicketParam();

            goldTicketParam.setIssuePlatform(issuePlatform);

            goldTicketParam.setIndustryId(industryId);

            goldTicketParam.setShopId(goodsInfoBean.getShopId());

            goldTicketParam.setStoreId(goodsInfoBean.getStoreId());

            goldTicketParam.setGoodsId(goodsInfoBean.getActId());

            PayOrderBean bean = new PayOrderBean();

            bean.setOrderAmount(orderInfo.getOrderAmount());

            bean.setNotifyUrl(orderInfo.getNotifyUrl());

            bean.setOrderId(orderInfo.getOrderId());

            bean.setOrderNo(orderInfo.getOrderNo());

            bean.setOrderTime(orderInfo.getOrderCreateTime());

            Intent intent = new Intent(this, AppPreferentialPaymentActivity.class);

            intent.putExtra("orderData", bean);

            intent.putExtra("goldTicketParam", goldTicketParam);

            intent.putExtra("goodName", goodsInfoBean.getActName());

            startActivity(intent);
        }

    }

    private void toAround(int flag)
    {

        if (hotelInfo.getLat() == 0 || hotelInfo.getLng() == 0 || hotelInfo.getHotelAddr() == null || hotelInfo.getHotelName() == null) {
            ToastUtil.i(this, "无法进入地图");
            return;
        }

        Intent intent1 = new Intent(this, NearByMapActivity.class);
        intent1.putExtra(NearByMapActivity.CATEGORY, flag); //交通，餐饮，娱乐，景点，购物，酒店        //依次传入：0,1,2,3,4,5
        Location location = new Location();
        location.latitude = hotelInfo.getLat();
        location.longitude = hotelInfo.getLng();
        location.streetNumber = hotelInfo.getHotelAddr();
        location.msg = hotelInfo.getHotelName();
        intent1.putExtra(NearByMapActivity.LOCATION, location);

        startActivity(intent1);

    }

    private void callHotelPhone(String number)
    {
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    private void bookAgainHotel()
    {
        Intent intent = new Intent(this, HotelProductInfoActivity.class);
        intent.putExtra("hotelId", hotelInfo.getHotelId() + "");
        startActivity(intent);

    }

    private void assessHotel()
    {
        if (!(hotelInfo.getHotelId() == 0)) {
            Intent pjIntent = new Intent(this, PjActivity.class);
            pjIntent.putExtra("detailId", hotelInfo.getHotelId());
            pjIntent.putExtra("type", "1");
            startActivity(pjIntent);
        }
    }

    private void shareHotel()
    {
        if (hotelInfo == null) {
            LogUtil.i("mHotelDetailBean为空");
            return;
        }
        showPDialog(R.string.loading);
        Map<String, Object> args = new HashMap<>();
        args.put("codeType", AppConstant.command_type_shop);
        args.put("code", hotelInfo.getHotelId()); //编码
        args.put("industryId", AppConstant.hotel_id);
        Map<String, Object> param2 = new HashMap<>();
        param2.put("hotelId", hotelInfo.getHotelId() + "");
        args.put("activityData", JSON.toJSONString(param2));
        commandPresenter.loadData(args);
    }

    @Override
    public void onGetCommandSuc(String commandAndUrl)
    {
        closePDialog();
    }

    @Override
    public void onGetCommandFail(String failMsg)
    {
        closePDialog();
        toastCustom(failMsg);
    }

    private HotelOrderDetailBean bean;

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (type == 0) {

            bean = JSON.parseObject(o + "", HotelOrderDetailBean.class);

            orderInfo = bean.getOrderInfo();

            hotelInfo = bean.getHotelInfo();

            llHotelOrderDetail.removeAllViews();

            tvOrderStatusMsg.setText(OrderUtils.getOrderStatus(orderInfo.getOrderStatus()));

            //发票信息
            OrderInvoiceInfoBean orderInvoiceInfoBean = bean.getInvoiceInfo();
            if (orderInvoiceInfoBean != null) {

                tvSuoquFapiao.setVisibility(View.GONE);

                tvFapiaoInfo.setVisibility(View.VISIBLE);

                StringBuffer invoiceInfoBeanSb = new StringBuffer();

                invoiceInfoBeanSb.append("发票类型：");

                if ("1".equals(orderInvoiceInfoBean.getInvoiceType())) {//电子发票

                    invoiceInfoBeanSb.append("电子发票\n");

                    invoiceInfoBeanSb.append("邮寄邮箱：").append(orderInvoiceInfoBean.getInvoiceExpressEamil());

                } else if ("2".equals(orderInvoiceInfoBean.getInvoiceType())) {//纸质发票

                    invoiceInfoBeanSb.append("纸质发票\n");

                    invoiceInfoBeanSb.append("发票抬头：").append(orderInvoiceInfoBean.getInvoiceTitile()).append("\n");

                    invoiceInfoBeanSb.append("物流单号：").append(orderInvoiceInfoBean.getInvoiceExpressNo()).append("\n");

                    invoiceInfoBeanSb.append("邮寄地址：").append(orderInvoiceInfoBean.getInvoiceExpressAddr());
                }

                tvFapiaoInfo.setText(invoiceInfoBeanSb.toString());

            } else {

                tvSuoquFapiao.setVisibility(View.VISIBLE);

                tvFapiaoInfo.setVisibility(View.GONE);

            }

            //订单信息和订单操作时间
            StringBuffer invoiceInfoBeanSb = new StringBuffer();
            invoiceInfoBeanSb.append("订单号：").append(orderInfo.getOrderNo()).append("\n");

            //把订单的操作时间记录展示
            List<ProductBaseOrderBean.OrderOptTime> orderOptTimeList = orderInfo.getOptTimeList();

            if (orderOptTimeList != null) {

                int size = orderOptTimeList.size();

                if (size > 0) {

                    for (int i = 0; i < size; i++) {

                        ProductBaseOrderBean.OrderOptTime orderOptTime = orderOptTimeList.get(i);

                        if (i == size - 1) {

                            invoiceInfoBeanSb.append(orderOptTime.getOptName()).append("：").append(orderOptTime.getOptTime());

                        } else {

                            invoiceInfoBeanSb.append(orderOptTime.getOptName()).append("：").append(orderOptTime.getOptTime()).append("\n");
                        }
                    }
                }
            }

            tvHotelOrderHandleTimeInfo.setText(invoiceInfoBeanSb.toString());

            if (HoteUtil.HOTEL_ROOM_CODE.equals(hotelType)) {

                roomInfo = bean.getRoomsInfo();

                //房间订单以及入住人信息
                StringBuffer roomOrderInfoAndPeople = new StringBuffer();
                Date startDate = DateUtil.string2Date(orderInfo.getArrDate(), DateUtil.DATE_FORMAT_DATE);
                Date endDate = DateUtil.string2Date(orderInfo.getDepDate(), DateUtil.DATE_FORMAT_DATE);
                //设置查看酒店详情参数
                HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();
                productListParam.setArrDate(orderInfo.getArrDate());
                productListParam.setDepDate(orderInfo.getDepDate());

                String spaceTime = DateUtil.naturalDaysBetween(startDate, endDate) + getString(R.string.hotel_toast_night);
                roomOrderInfoAndPeople.append("时间：").append(orderInfo.getArrDate() + "~" + orderInfo.getDepDate() + "共" + spaceTime).append("\n");
                roomOrderInfoAndPeople.append("房间数：").append(orderInfo.getGoodsQuantity() + "间").append("\n");
                roomOrderInfoAndPeople.append("入住人：").append(orderInfo.getGuestNames()).append("\n");
                if (!TextUtils.isEmpty(orderInfo.getContactName()) && orderInfo.getContactName().length() > 0 && !"null".equals(orderInfo.getContactName())) {
                    roomOrderInfoAndPeople.append("联系人：").append(orderInfo.getContactName()).append("\n");
                }
                roomOrderInfoAndPeople.append("联系电话：").append(orderInfo.getContactTel()).append("\n");

                roomOrderInfoAndPeople.append("备注：").append(orderInfo.getOrderRemark());
                tvHotelOrderLinkeManInfo.setText(roomOrderInfoAndPeople.toString());

            /*
             * 酒店房间订单明细
             */
                View roomProductView = LayoutInflater.from(this).inflate(R.layout.view_hotel_order_detail, null);

                TextView roomProductNameOne = (TextView) roomProductView.findViewById(R.id.tvRoomProductName);

                TextView tvRoomPrice = (TextView) roomProductView.findViewById(R.id.tvRoomPrice);

                roomProductNameOne.setText(roomInfo.getRoomsName() + roomInfo.getBedType() + "(" + spaceTime + "/" + orderInfo.getGoodsQuantity() + "间)");

                int paymentType = Integer.parseInt(orderInfo.getPaymentType());

                if (PayFormIf.PAYMENT_TYPE_DAODIANFU == paymentType) {

                    tvRoomPrice.setText("到店付 ¥" + Utils.subZeroAndDot(orderInfo.getRoomsTotalAmount() + ""));
                } else {
                    tvRoomPrice.setText("¥" + Utils.subZeroAndDot(orderInfo.getRoomsTotalAmount() + ""));
                }
                llHotelOrderDetail.addView(roomProductView);

            } else if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(hotelType) || HoteUtil.HOTEL_BAR_CODE.equals(hotelType)
                    || HoteUtil.HOTEL_SPA_CODE.equals(hotelType) || HoteUtil.HOTEL_LOBBY_BAR_CODE.equals(hotelType)
                    || HoteUtil.HOTEL_GYM_CODE.equals(hotelType) || HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(hotelType)) {

                goodsInfoBean = bean.getGoodsInfo();

                tvProductName.setText(goodsInfoBean.getPolicysName());

                //其它产品订单
                Date startDate = new Date();
                Date endDate = DateUtil.addDay(startDate, 1);

                String startDateStr = DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE);
                String endDateStr = DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_DATE);
                //设置查看酒店详情参数
                HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();
                productListParam.setArrDate(startDateStr);
                productListParam.setDepDate(endDateStr);

                StringBuffer roomOrderInfoAndPeople = new StringBuffer();
                roomOrderInfoAndPeople.append("时间：").append(goodsInfoBean.getEffDateDesc()).append("\n");
                roomOrderInfoAndPeople.append("数量：").append(orderInfo.getGoodsQuantity() + "份").append("\n");

                if (!TextUtils.isEmpty(goodsInfoBean.getLimitPersonDesc())) {
                    roomOrderInfoAndPeople.append("适用人数：").append(goodsInfoBean.getLimitPersonDesc()).append("\n");
                }

                if (!TextUtils.isEmpty(orderInfo.getContactName()) && orderInfo.getContactName().length() > 0 && !"null".equals(orderInfo.getContactName())) {
                    roomOrderInfoAndPeople.append("联系人：").append(orderInfo.getContactName()).append("\n");
                }
                roomOrderInfoAndPeople.append("联系电话：").append(orderInfo.getContactTel()).append("\n");

                roomOrderInfoAndPeople.append("备注：").append(orderInfo.getOrderRemark());
                tvHotelOrderLinkeManInfo.setText(roomOrderInfoAndPeople.toString());

               /*
                * 其它产品订单明细
                */
                View roomProductView = LayoutInflater.from(this).inflate(R.layout.view_hotel_order_detail, null);

                TextView roomProductNameOne = (TextView) roomProductView.findViewById(R.id.tvRoomProductName);

                TextView tvRoomPrice = (TextView) roomProductView.findViewById(R.id.tvRoomPrice);

                roomProductNameOne.setText(goodsInfoBean.getPolicysName());

                tvRoomPrice.setText("¥" + Utils.subZeroAndDot(orderInfo.getOrderAmount() + ""));

                llHotelOrderDetail.addView(roomProductView);

            } else if (HoteUtil.HOTEL_CARD_EQUITY_CODE.equals(hotelType)) {//卡权益

                GoodsAddressBean deliveryAddress = bean.getDeliveryAddress();

                if (deliveryAddress != null) {

                    llCardBenefit.setVisibility(View.VISIBLE);

                    String str = "";

                    String expressStatus = deliveryAddress.getExpressStatus();

                    if ("0".equals(expressStatus)) {
                        str = "未配送";
                    } else if ("1".equals(expressStatus)) {
                        str = "已发货";
                    } else if ("2".equals(expressStatus)) {
                        str = "已收获";
                    }
                    tvDeliveryStatus.setText("快件" + str);

                    StringBuffer deliveryInfo = new StringBuffer();

                    deliveryInfo.append("收货人：").append(deliveryAddress.getContacts()).append("\n");

                    deliveryInfo.append("收货地址：").append(deliveryAddress.getPhone());

                    tvConsigneeInfo.setText(deliveryInfo.toString());
                }
                goodsInfoBean = bean.getGoodsInfo();

                tvProductName.setText(goodsInfoBean.getGiftsName());

                //其它产品订单
                Date startDate = new Date();
                Date endDate = DateUtil.addDay(startDate, 1);

                String startDateStr = DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE);
                String endDateStr = DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_DATE);
                //设置查看酒店详情参数
                HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();
                productListParam.setArrDate(startDateStr);
                productListParam.setDepDate(endDateStr);

                StringBuffer roomOrderInfoAndPeople = new StringBuffer();
                roomOrderInfoAndPeople.append("有效期限：").append(goodsInfoBean.getEffMonthDesc()).append("\n");
                roomOrderInfoAndPeople.append("生效日期：").append(goodsInfoBean.getEffDate()).append("\n");

                roomOrderInfoAndPeople.append("数量：").append(orderInfo.getGoodsQuantity()).append("\n");


                if (!TextUtils.isEmpty(orderInfo.getContactName()) && orderInfo.getContactName().length() > 0 && !"null".equals(orderInfo.getContactName())) {

                    roomOrderInfoAndPeople.append("联系人：").append(orderInfo.getContactName()).append("\n");
                }
                roomOrderInfoAndPeople.append("联系电话：").append(orderInfo.getContactTel()).append("\n");

                String receiveType = goodsInfoBean.getReceiveType();

                String receiveTypeStr = "";

                if ("1".equals(receiveType)) {

                    receiveTypeStr = "门店自取";

                } else if ("2".equals(receiveType)) {

                    receiveTypeStr = "门店自取";
                }

                roomOrderInfoAndPeople.append("邮寄方式：").append(receiveTypeStr);

                tvHotelOrderLinkeManInfo.setText(roomOrderInfoAndPeople.toString());

               /*
                * 其它产品订单明细
                */
                View roomProductView = LayoutInflater.from(this).inflate(R.layout.view_hotel_order_detail, null);

                TextView roomProductNameOne = (TextView) roomProductView.findViewById(R.id.tvRoomProductName);

                TextView tvRoomPrice = (TextView) roomProductView.findViewById(R.id.tvRoomPrice);

                roomProductNameOne.setText(goodsInfoBean.getGiftsName());

                tvRoomPrice.setText("¥" + Utils.subZeroAndDot(orderInfo.getOrderAmount() + ""));

                llHotelOrderDetail.addView(roomProductView);
            } else if (HoteUtil.HOTEL_FAST_BUY_CODE.equals(hotelType)) {//限时抢购

                goodsInfoBean = bean.getGoodsInfo();

                tvProductName.setText(goodsInfoBean.getActName());

                //其它产品订单
                Date startDate = new Date();
                Date endDate = DateUtil.addDay(startDate, 1);

                String startDateStr = DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE);
                String endDateStr = DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_DATE);
                //设置查看酒店详情参数
                HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();
                productListParam.setArrDate(startDateStr);
                productListParam.setDepDate(endDateStr);


                StringBuffer roomOrderInfoAndPeople = new StringBuffer();
                roomOrderInfoAndPeople.append("时间：").append(goodsInfoBean.getEffDateDesc()).append("\n");

                roomOrderInfoAndPeople.append("数量：").append(orderInfo.getGoodsQuantity()).append("\n");


                if (!TextUtils.isEmpty(orderInfo.getContactName()) && orderInfo.getContactName().length() > 0 && !"null".equals(orderInfo.getContactName())) {

                    roomOrderInfoAndPeople.append("联系人：").append(orderInfo.getContactName()).append("\n");
                }
                roomOrderInfoAndPeople.append("联系电话：").append(orderInfo.getContactTel()).append("\n");

                tvHotelOrderLinkeManInfo.setText(roomOrderInfoAndPeople.toString());

                View roomProductView = LayoutInflater.from(this).inflate(R.layout.view_hotel_order_detail, null);

                TextView roomProductNameOne = (TextView) roomProductView.findViewById(R.id.tvRoomProductName);

                TextView tvRoomPrice = (TextView) roomProductView.findViewById(R.id.tvRoomPrice);

                roomProductNameOne.setText(goodsInfoBean.getActShortName());

                tvRoomPrice.setText("¥" + Utils.subZeroAndDot(orderInfo.getOrderAmount() + ""));

                llHotelOrderDetail.addView(roomProductView);

            }

            //纸质发票邮寄费用信息
            OrderInvoiceInfoBean invoiceInfo = bean.getInvoiceInfo();
            if (invoiceInfo != null) {

                float expressAmount = invoiceInfo.getExpressAmount();

                if (expressAmount != 0f) {
                    View invoiceInfoBeanView = LayoutInflater.from(this).inflate(R.layout.view_hotel_order_detail, null);

                    TextView invoiceInfoBeanViewOne = (TextView) invoiceInfoBeanView.findViewById(R.id.tvRoomProductName);

                    TextView invoiceInfoBeanViewPrice = (TextView) invoiceInfoBeanView.findViewById(R.id.tvRoomPrice);

                    invoiceInfoBeanViewOne.setText("纸质发票邮寄费");

                    invoiceInfoBeanViewPrice.setText("¥" + Utils.subZeroAndDot(expressAmount + ""));

                    llHotelOrderDetail.addView(invoiceInfoBeanView);
                }

            }

            //保险产品信息
            List<InsuranceBean> insuranceList = bean.getInsuranceList();
            if (insuranceList != null) {

                for (InsuranceBean insuranceBean : insuranceList) {

                    View insuranceBeanView = LayoutInflater.from(this).inflate(R.layout.view_hotel_order_detail, null);

                    TextView insuranceBeanNameOne = (TextView) insuranceBeanView.findViewById(R.id.tvRoomProductName);

                    TextView insuranceBeanPrice = (TextView) insuranceBeanView.findViewById(R.id.tvRoomPrice);

                    insuranceBeanNameOne.setText(insuranceBean.getGoodsName() + "（" + insuranceBean.getGoodsAmount() + "张）");

                    insuranceBeanPrice.setText("¥" + Utils.subZeroAndDot(insuranceBean.getGoodsAmount() + ""));

                    llHotelOrderDetail.addView(insuranceBeanView);
                }
            }
            //超值加购信息
            List<GoodsPlusOrderBean> goodsplusList = bean.getGoodsplusList();
            if (goodsplusList != null) {

                for (GoodsPlusOrderBean goodsplusBean : goodsplusList) {

                    View insuranceBeanView = LayoutInflater.from(this).inflate(R.layout.view_hotel_order_detail, null);

                    TextView insuranceBeanNameOne = (TextView) insuranceBeanView.findViewById(R.id.tvRoomProductName);

                    TextView insuranceBeanPrice = (TextView) insuranceBeanView.findViewById(R.id.tvRoomPrice);

                    insuranceBeanNameOne.setText(goodsplusBean.getGoodsName() + "（" + goodsplusBean.getGoodsAmount() + "张）");

                    insuranceBeanPrice.setText("¥" + Utils.subZeroAndDot(goodsplusBean.getGoodsAmount() + ""));

                    llHotelOrderDetail.addView(insuranceBeanView);

                }
            }
            //优惠券、积分等抵扣明细信息
            List<DeductionOrderBean> disList = bean.getDisList();
            if (disList != null) {

                for (DeductionOrderBean disBean : disList) {

                    if (disBean.getDisAmount() != 0l && disBean.getDisName().length() > 0) {

                        View insuranceBeanView = LayoutInflater.from(this).inflate(R.layout.view_hotel_order_detail, null);

                        TextView insuranceBeanNameOne = (TextView) insuranceBeanView.findViewById(R.id.tvRoomProductName);

                        TextView insuranceBeanPrice = (TextView) insuranceBeanView.findViewById(R.id.tvRoomPrice);

                        insuranceBeanNameOne.setText(disBean.getDisName());

                        insuranceBeanPrice.setText("-¥" + Utils.subZeroAndDot(disBean.getDisAmount() + ""));

                        llHotelOrderDetail.addView(insuranceBeanView);
                    }
                }
            }
            if (orderInfo.getActualAmount() != 0) {
                //实付金额
                View shifuView = LayoutInflater.from(this).inflate(R.layout.view_hotel_order_detail, null);

                TextView shifuNameOne = (TextView) shifuView.findViewById(R.id.tvRoomProductName);
                shifuNameOne.setTextColor(getResources().getColor(R.color.color_333333));
                TextPaint shifuTp = shifuNameOne.getPaint();
                shifuTp.setFakeBoldText(true);

                TextView shifuPrice = (TextView) shifuView.findViewById(R.id.tvRoomPrice);
                shifuPrice.setTextColor(getResources().getColor(R.color.color_333333));
                TextPaint shifuPriceTp = shifuPrice.getPaint();
                shifuPriceTp.setFakeBoldText(true);

                shifuNameOne.setText("实付金额");
                shifuPrice.setText("¥" + Utils.subZeroAndDot(orderInfo.getActualAmount() + ""));
                llHotelOrderDetail.addView(shifuView);
            }
            //返现
            double cashbackAmount = orderInfo.getCashbackAmount();
            if (cashbackAmount != 0d) {
                View fanxianView = LayoutInflater.from(this).inflate(R.layout.view_hotel_order_detail, null);

                TextView fanxinNameOne = (TextView) fanxianView.findViewById(R.id.tvRoomProductName);
                TextPaint fanxianTp = fanxinNameOne.getPaint();
                fanxianTp.setFakeBoldText(true);
                fanxinNameOne.setTextColor(getResources().getColor(R.color.color_333333));

                TextView fanxianPrice = (TextView) fanxianView.findViewById(R.id.tvRoomPrice);
                TextPaint fanxianPriceTp = fanxianPrice.getPaint();
                fanxianPriceTp.setFakeBoldText(true);
                fanxianPrice.setTextColor(getResources().getColor(R.color.color_333333));

                fanxinNameOne.setText("返现");
                fanxianPrice.setText("¥" + Utils.subZeroAndDot(cashbackAmount + ""));

                llHotelOrderDetail.addView(fanxianView);
            }
            //担保金额
            double guaranteeAmount = orderInfo.getGuaranteeAmount();
            if (guaranteeAmount != 0d) {
                View fanxianView = LayoutInflater.from(this).inflate(R.layout.view_hotel_order_detail, null);

                TextView fanxinNameOne = (TextView) fanxianView.findViewById(R.id.tvRoomProductName);
                TextPaint fanxianTp = fanxinNameOne.getPaint();
                fanxianTp.setFakeBoldText(true);
                fanxinNameOne.setTextColor(getResources().getColor(R.color.color_333333));

                TextView fanxianPrice = (TextView) fanxianView.findViewById(R.id.tvRoomPrice);
                TextPaint fanxianPriceTp = fanxianPrice.getPaint();
                fanxianPriceTp.setFakeBoldText(true);
                fanxianPrice.setTextColor(getResources().getColor(R.color.color_333333));

                fanxinNameOne.setText("担保金额");
                fanxianPrice.setText("¥" + Utils.subZeroAndDot(guaranteeAmount + ""));

                llHotelOrderDetail.addView(fanxianView);
            }

            //房间订单状态以及对应的操作
            if (HoteUtil.HOTEL_ROOM_CODE.equals(hotelType)) {

                llRoomOrderHotel.setVisibility(View.VISIBLE);

                llOtherOrderHotel.setVisibility(View.GONE);

                //酒店房间产品信息
                tvProductName.setText(roomInfo.getRoomsName() + "(" + roomInfo.getBedType() + ")");
                //房间名
                hotelsName.setText(hotelInfo.getHotelName());
                //房间地址
                address.setText(hotelInfo.getHotelAddr());
                //酒店电话
                String phoneTel = hotelInfo.getTel();

                if (!TextUtils.isEmpty(phoneTel)) {

                    tvHotelPhoneNumber.setVisibility(View.VISIBLE);

                    tvHotelPhoneNumber.setTag(phoneTel);

                } else {

                    tvHotelPhoneNumber.setVisibility(View.GONE);
                }
                //供应商电话
                String phoneTelStr = orderInfo.getShopTel();

                if (!TextUtils.isEmpty(phoneTelStr)) {

                    tvSupplierPhoneNumber.setVisibility(View.VISIBLE);

                    tvSupplierPhoneNumber.setTag(phoneTelStr);

                } else {

                    tvSupplierPhoneNumber.setVisibility(View.GONE);
                }

                llOneOrder.setVisibility(View.VISIBLE);

                tvOrderFunction.setVisibility(View.GONE);

                tvOrderFanxian.setVisibility(View.GONE);

                tvOrderDel.setVisibility(View.GONE);

                tvOrderPingjia.setVisibility(View.GONE);

                tvOrderPay.setVisibility(View.GONE);

                tvOrderAgain.setVisibility(View.GONE);

                tvOrderQuxiaoTuikuan.setVisibility(View.GONE);

                tvOrderTuikuan.setVisibility(View.GONE);

                int paymentType = Integer.parseInt(orderInfo.getPaymentType());
                //房间的订单状态对应的操作
                switch (orderInfo.getOrderStatus()) {

                    case OrderBean.ORDER_STATUS_DAIQUEREN://待确认

                        tvOrderFunction.setVisibility(View.VISIBLE);

                        llConfirmCancel.setVisibility(View.VISIBLE);

                        tvOrderPromt.setText("商家将在30分钟内为您确认订单");

                        llConfirmCancel.setOnClickListener(this);

                        tvOrderFunction.setOnClickListener(this);

                        hotelOrderCancelMsg();
                        break;
                    case OrderBean.ORDER_STATUS_NO_PAY:  //待支付

                        tvOrderFunction.setVisibility(View.VISIBLE);

                        llConfirmCancel.setVisibility(View.VISIBLE);

                        llConfirmCancel.setOnClickListener(this);

                        tvOrderFunction.setOnClickListener(this);

                        String paymentTypeStr = orderInfo.getPaymentType();

                        if (!"1".equals(paymentTypeStr)) {

                            tvOrderPay.setVisibility(View.VISIBLE);

                            tvOrderPay.setOnClickListener(this);
                        }

                        tvOrderPromt.setText("请在15分钟内完成支付");

                        hotelOrderCancelMsg();

                        break;
                    case OrderBean.ORDER_STATUS_YIQUEREN:  //已确认

                        llConfirmCancel.setVisibility(View.VISIBLE);

                        tvOrderFunction.setVisibility(View.VISIBLE);

                        llConfirmCancel.setOnClickListener(this);

                        tvOrderFunction.setOnClickListener(this);

                        tvOrderPromt.setText("您已预订成功，请及时入住");

                        hotelOrderCancelMsg();

                        break;
                    case OrderBean.ORDER_STATUS_YIRUZHU://已入住

                        String voteStatusStr = orderInfo.getVoteStatus();

                        if ("0".equals(voteStatusStr)) {//未评价则显示评价按钮

                            tvOrderPingjia.setVisibility(View.VISIBLE);

                            tvOrderPingjia.setOnClickListener(this);
                        }

                        tvOrderAgain.setVisibility(View.VISIBLE);

                        tvOrderAgain.setOnClickListener(this);


                        tvOrderPromt.setText("您已成功入住，祝您旅途愉快");
                        break;
                    case OrderBean.ORDER_STATUS_YILIDIAN://已离店

                        tvOrderFanxian.setVisibility(View.VISIBLE);

                        tvOrderAgain.setVisibility(View.VISIBLE);

                        tvOrderAgain.setOnClickListener(this);

                        tvOrderFanxian.setOnClickListener(this);

                        tvOrderPromt.setText("感谢您的预定，欢迎再次使用嗨生活");
                        break;
                    case OrderBean.ORDER_STATUS_CANCEL:  //已取消

                        tvOrderDel.setVisibility(View.VISIBLE);

                        tvOrderAgain.setVisibility(View.VISIBLE);

                        tvOrderAgain.setOnClickListener(this);

                        tvOrderDel.setOnClickListener(this);

                        tvOrderPromt.setText("订单已取消，可再次预定或重新搜索其它酒店");

                        break;
                    case OrderBean.ORDER_STATUS_QUERENSHIBAI:  //确认失败


                        if (PayFormIf.PAYMENT_TYPE_DAODIANFU == paymentType) {

                            tvOrderPromt.setText("房间确认失败，请重新下单");
                        } else {

                            tvOrderPromt.setText("房间确认失败，支付金额将于2小时内原路退回");
                        }

                        tvOrderDel.setVisibility(View.VISIBLE);


                        tvOrderDel.setOnClickListener(this);

                        break;

                    case OrderBean.ORDER_STATUS_YUDINGWEIRUZHU://预订未入住

                        tvOrderPromt.setText("商家告知您未按订单入住，若已入住，请联系嗨生活客服");

                        tvOrderDel.setVisibility(View.VISIBLE);

                        tvOrderDel.setOnClickListener(this);
                        break;

                }
                //
                quesation_layout.setVisibility(View.VISIBLE);

                tvFunctionOne.setVisibility(View.GONE);

                tvFunctionTwo.setVisibility(View.GONE);

                tvFunctionThree.setVisibility(View.GONE);

                question2.setVisibility(View.GONE);

                tvFunctionOne.setOnClickListener(this);

                tvFunctionTwo.setOnClickListener(this);

                tvFunctionThree.setOnClickListener(this);

                int orderStatus = orderInfo.getOrderStatus();//订单状态

                if (PayFormIf.PAYMENT_TYPE_DAODIANFU == paymentType) {

                    switch (orderStatus) {

                        case OrderBean.ORDER_STATUS_DAIQUEREN://待确认

                            tvFunctionOne.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionOne.setText("催确认");

                            tvFunctionTwo.setText("查询酒店信息");

                            tvFunctionOne.setTag(HoteUtil.HOTEL_CUIKUANQUEREN_CODE);
                            tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE);

                            break;

                        case OrderBean.ORDER_STATUS_YIQUEREN:  //已确认

                            tvFunctionOne.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionThree.setVisibility(View.VISIBLE);

                            tvFunctionOne.setText("如何开发票");

                            tvFunctionTwo.setText("查询确认信息");

                            tvFunctionThree.setText("取消订单");

                            question2.setVisibility(View.VISIBLE);

                            tvFunctionOne.setTag(HoteUtil.HOTEL_RUHEKAIFAPIAO_CODE);
                            tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAKANQUERENXINXI_CODE);
                            tvFunctionThree.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);

                            break;
                        case OrderBean.ORDER_STATUS_YIRUZHU://已入住
                            tvFunctionOne.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionOne.setText("如何开发票");

                            tvFunctionTwo.setText("如何返现");

                            tvFunctionOne.setTag(HoteUtil.HOTEL_RUHEKAIFAPIAO_CODE);
                            tvFunctionTwo.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);

                            break;
                        case OrderBean.ORDER_STATUS_YILIDIAN://已离店

                            tvFunctionOne.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionOne.setText("忘记开发票");

                            tvFunctionTwo.setText("如何返现");

                            tvFunctionOne.setTag(HoteUtil.HOTEL_WANGJIKAIFAPIAO_CODE);
                            tvFunctionTwo.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);
                            break;
                        default:

                            quesation_layout.setVisibility(View.GONE);
                            break;
                    }

                } else if (PayFormIf.PAYMENT_TYPE_DANBAO == paymentType) {


                    switch (orderStatus) {

                        case OrderBean.ORDER_STATUS_NO_PAY://待支付

                            tvFunctionOne.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionOne.setText("查询酒店信息");

                            tvFunctionTwo.setText("支付遇到问题");

                            tvFunctionOne.setTag(HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE);
                            tvFunctionTwo.setTag(HoteUtil.HOTEL_YUDAOWENTI_CODE);

                            break;
                        case OrderBean.ORDER_STATUS_DAIQUEREN://待确认

                            tvFunctionOne.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionOne.setText("催确认");

                            tvFunctionTwo.setText("查询酒店信息");

                            tvFunctionOne.setTag(HoteUtil.HOTEL_CUIKUANQUEREN_CODE);
                            tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE);

                            break;

                        case OrderBean.ORDER_STATUS_YIQUEREN:  //已确认

                            tvFunctionOne.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionThree.setVisibility(View.VISIBLE);

                            tvFunctionOne.setText("如何开发票");

                            tvFunctionTwo.setText("查询确认信息");

                            tvFunctionThree.setText("取消订单");

                            tvFunctionOne.setTag(HoteUtil.HOTEL_RUHEKAIFAPIAO_CODE);
                            tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAKANQUERENXINXI_CODE);
                            tvFunctionThree.setTag(HoteUtil.HOTEL_CANCELORDER_CODE);

                            question2.setVisibility(View.VISIBLE);

                            break;
                        case OrderBean.ORDER_STATUS_YIRUZHU://已入住
                            tvFunctionOne.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionThree.setVisibility(View.VISIBLE);

                            tvFunctionOne.setText("如何开发票");

                            tvFunctionTwo.setText("如何返现");

                            tvFunctionThree.setText("担保何时退还");

                            tvFunctionOne.setTag(HoteUtil.HOTEL_RUHEKAIFAPIAO_CODE);
                            tvFunctionTwo.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);
                            tvFunctionThree.setTag(HoteUtil.HOTEL_DANBAOHESHITUIHUAN_CODE);

                            question2.setVisibility(View.VISIBLE);

                            break;
                        case OrderBean.ORDER_STATUS_YILIDIAN://已离店

                            tvFunctionOne.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionThree.setVisibility(View.VISIBLE);

                            tvFunctionOne.setText("忘记开发票");

                            tvFunctionTwo.setText("如何返现");

                            tvFunctionThree.setText("担保何时退还");

                            tvFunctionOne.setTag(HoteUtil.HOTEL_WANGJIKAIFAPIAO_CODE);
                            tvFunctionTwo.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);
                            tvFunctionThree.setTag(HoteUtil.HOTEL_DANBAOHESHITUIHUAN_CODE);

                            question2.setVisibility(View.VISIBLE);

                            break;
                        default:

                            quesation_layout.setVisibility(View.GONE);
                            break;
                    }

                } else if (PayFormIf.PAYMENT_TYPE_ONLINE == paymentType) {

                    switch (orderStatus) {

                        case OrderBean.ORDER_STATUS_NO_PAY://待支付

                            tvFunctionOne.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionOne.setText("查询酒店信息");

                            tvFunctionTwo.setText("支付遇到问题");

                            tvFunctionOne.setTag(HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE);
                            tvFunctionTwo.setTag(HoteUtil.HOTEL_YUDAOWENTI_CODE);

                            break;
                        case OrderBean.ORDER_STATUS_DAIQUEREN://待确认

                            tvFunctionOne.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionOne.setText("催确认");

                            tvFunctionTwo.setText("查询酒店信息");

                            tvFunctionOne.setTag(HoteUtil.HOTEL_CUIKUANQUEREN_CODE);
                            tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE);
                            break;

                        case OrderBean.ORDER_STATUS_YIQUEREN:  //已确认

                            int invoiceStatus = orderInfo.getInvoiceStatus();

                            if (invoiceStatus == 0) {

                                tvFunctionOne.setVisibility(View.VISIBLE);

                                tvFunctionTwo.setVisibility(View.VISIBLE);

                                tvFunctionThree.setVisibility(View.VISIBLE);

                                tvFunctionOne.setText("补开发票");

                                tvFunctionTwo.setText("查询确认信息");

                                tvFunctionThree.setText("取消订单");

                                tvFunctionOne.setTag(HoteUtil.HOTEL_BUKAIFAPIAO_CODE);

                                tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAKANQUERENXINXI_CODE);

                                tvFunctionThree.setTag(HoteUtil.HOTEL_CANCELORDER_CODE);

                                question2.setVisibility(View.VISIBLE);
                            } else {

                                tvFunctionOne.setVisibility(View.VISIBLE);

                                tvFunctionTwo.setVisibility(View.VISIBLE);

                                tvFunctionOne.setText("查询确认信息");

                                tvFunctionTwo.setText("取消订单");

                                tvFunctionOne.setTag(HoteUtil.HOTEL_CHAKANQUERENXINXI_CODE);

                                tvFunctionTwo.setTag(HoteUtil.HOTEL_CANCELORDER_CODE);
                            }


                            break;
                        case OrderBean.ORDER_STATUS_YIRUZHU://已入住

                            int invoiceStatus1 = orderInfo.getInvoiceStatus();

                            if (invoiceStatus1 == 0) {

                                tvFunctionOne.setVisibility(View.VISIBLE);

                                tvFunctionOne.setText("补开发票");

                                tvFunctionOne.setTag(HoteUtil.HOTEL_BUKAIFAPIAO_CODE);
                            } else {


                            }

                            tvFunctionTwo.setVisibility(View.VISIBLE);

                            tvFunctionTwo.setText("如何返现");

                            tvFunctionTwo.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);

                            break;
                        case OrderBean.ORDER_STATUS_YILIDIAN://已离店

                            int invoiceStatus2 = orderInfo.getInvoiceStatus();

                            if (invoiceStatus2 == 0) {

                                tvFunctionOne.setVisibility(View.VISIBLE);

                                tvFunctionTwo.setVisibility(View.VISIBLE);

                                tvFunctionThree.setVisibility(View.VISIBLE);

                                tvFunctionOne.setText("补开发票");

                                tvFunctionTwo.setText("发票何时寄");

                                tvFunctionThree.setText("如何返现");

                                tvFunctionOne.setTag(HoteUtil.HOTEL_BUKAIFAPIAO_CODE);
                                tvFunctionTwo.setTag(HoteUtil.HOTEL_FAPIAOHESHIJISONG_CODE);
                                tvFunctionThree.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);
                                question2.setVisibility(View.VISIBLE);
                            } else {

                                tvFunctionOne.setVisibility(View.VISIBLE);

                                tvFunctionTwo.setVisibility(View.VISIBLE);

                                tvFunctionOne.setText("发票何时寄");

                                tvFunctionTwo.setText("如何返现");

                                tvFunctionOne.setTag(HoteUtil.HOTEL_FAPIAOHESHIJISONG_CODE);

                                tvFunctionTwo.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);
                            }

                            break;
                        default:

                            quesation_layout.setVisibility(View.GONE);
                            break;
                    }
                }
            } else if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(hotelType) || HoteUtil.HOTEL_BAR_CODE.equals(hotelType)
                    || HoteUtil.HOTEL_SPA_CODE.equals(hotelType) || HoteUtil.HOTEL_LOBBY_BAR_CODE.equals(hotelType)
                    || HoteUtil.HOTEL_GYM_CODE.equals(hotelType) || HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(hotelType)) {//其它产品的订单状态对应的操作

                llRoomOrderHotel.setVisibility(View.GONE);

                llOtherOrderHotel.setVisibility(View.VISIBLE);

                tvOtherProductName.setText(hotelInfo.getHotelName());

                tvOtherOrderMoney.setText("总额： ¥" + Utils.subZeroAndDot(orderInfo.getOrderAmount() + ""));

                if (!TextUtils.isEmpty(orderInfo.getVerificationCode())) {

                    tvOrderPromt.setText("核销码：" + orderInfo.getVerificationCode());
                }

                //供应商电话
                String phoneTelStr = hotelInfo.getTel();

                if (!TextUtils.isEmpty(phoneTelStr)) {

                    llHotelOtherProductPhone.setVisibility(View.VISIBLE);

                    vOtherLine.setVisibility(View.VISIBLE);

                    tvHotelOtherProductPhone.setText("联系电话" + phoneTelStr);

                } else {

                    llHotelOtherProductPhone.setVisibility(View.GONE);

                    vOtherLine.setVisibility(View.GONE);
                }

                tvHotelOrderPay.setVisibility(View.GONE);

                tvHotelOrderCancel.setVisibility(View.GONE);

                tvHotelOrderDel.setVisibility(View.GONE);

                tvHotelOrderRefund.setVisibility(View.GONE);

                tvHotelOrderCancelRefund.setVisibility(View.GONE);

                int orderStatus = orderInfo.getOrderStatus();

                switch (orderStatus) {

                    case OrderBean.ORDER_STATUS_NO_PAY://待支付

                        tvHotelOrderPay.setVisibility(View.VISIBLE);

                        tvHotelOrderCancel.setVisibility(View.VISIBLE);

                        tvHotelOrderPay.setOnClickListener(this);

                        tvHotelOrderCancel.setOnClickListener(this);

                        break;

                    case OrderBean.ORDER_STATUS_CANCEL://待确认

                        tvHotelOrderDel.setVisibility(View.VISIBLE);

                        tvHotelOrderDel.setOnClickListener(this);


                        break;
                    case OrderBean.ORDER_STATUS_COMPLETE://已支付

                        tvHotelOrderRefund.setVisibility(View.VISIBLE);

                        tvHotelOrderRefund.setOnClickListener(this);

                        break;

                    case OrderBean.ORDER_STATUS_DAITUIKUAN:  //待退款

                        tvHotelOrderCancelRefund.setVisibility(View.VISIBLE);

                        tvHotelOrderCancelRefund.setOnClickListener(this);

                        break;
                    case OrderBean.ORDER_STATUS_YIWANCHENG://已成交

                        tvHotelOrderDel.setVisibility(View.VISIBLE);

                        tvHotelOrderDel.setOnClickListener(this);

                        break;
                    case OrderBean.ORDER_STATUS_YIGUOQI://已过期

                        tvHotelOrderRefund.setVisibility(View.VISIBLE);

                        tvHotelOrderRefund.setOnClickListener(this);

                        break;
                    case OrderBean.ORDER_STATUS_YITUIKUAN://已退款

                        tvHotelOrderDel.setVisibility(View.VISIBLE);

                        tvHotelOrderDel.setOnClickListener(this);

                        break;
                    default:

                        break;
                }

            } else if (HoteUtil.HOTEL_CARD_EQUITY_CODE.equals(hotelType)) {//卡权益

                llRoomOrderHotel.setVisibility(View.VISIBLE);

                llOtherOrderHotel.setVisibility(View.GONE);

                tvOtherOrderMoney.setText("总额： ¥" + Utils.subZeroAndDot(orderInfo.getOrderAmount() + ""));

                if (!TextUtils.isEmpty(orderInfo.getVerificationCode())) {

                    tvOrderPromt.setText("核销码：" + orderInfo.getVerificationCode());
                }

                //房间名
                hotelsName.setText(hotelInfo.getHotelName());
                //房间地址
                address.setText(hotelInfo.getHotelAddr());
                //酒店电话
                String phoneTel = hotelInfo.getTel();
                //
                if (!TextUtils.isEmpty(phoneTel)) {

                    tvHotelPhoneNumber.setVisibility(View.VISIBLE);

                    tvHotelPhoneNumber.setTag(phoneTel);

                } else {

                    tvHotelPhoneNumber.setVisibility(View.GONE);
                }
                //供应商电话
                String phoneTelStr = orderInfo.getShopTel();

                if (!TextUtils.isEmpty(phoneTelStr)) {

                    tvSupplierPhoneNumber.setVisibility(View.VISIBLE);

                    tvSupplierPhoneNumber.setTag(phoneTelStr);

                } else {

                    tvSupplierPhoneNumber.setVisibility(View.GONE);
                }

                llOneOrder.setVisibility(View.VISIBLE);
                tvOrderFunction.setVisibility(View.GONE);

                tvOrderFanxian.setVisibility(View.GONE);

                tvOrderDel.setVisibility(View.GONE);

                tvOrderPingjia.setVisibility(View.GONE);

                tvOrderPay.setVisibility(View.GONE);

                tvOrderAgain.setVisibility(View.GONE);

                tvOrderQuxiaoTuikuan.setVisibility(View.GONE);

                tvOrderTuikuan.setVisibility(View.GONE);

                //卡权益的订单状态对应的操作
                switch (orderInfo.getOrderStatus()) {
                    case OrderBean.ORDER_STATUS_NO_PAY:  //待支付

                        tvOrderPay.setVisibility(View.VISIBLE);
                        tvOrderFunction.setVisibility(View.VISIBLE);

                        tvOrderPay.setOnClickListener(this);
                        tvOrderFunction.setOnClickListener(this);

                        break;

                    case OrderBean.ORDER_STATUS_CANCEL:  //已取消

                        tvOrderDel.setVisibility(View.VISIBLE);
                        tvOrderDel.setOnClickListener(this);
                        break;
                    case OrderBean.ORDER_STATUS_COMPLETE:  //已支付

                        tvOrderTuikuan.setVisibility(View.VISIBLE);
                        tvOrderTuikuan.setOnClickListener(this);
                        break;
                    case OrderBean.ORDER_STATUS_YIWANCHENG://已成交

                        tvOrderTuikuan.setVisibility(View.VISIBLE);
                        tvOrderTuikuan.setOnClickListener(this);
                        break;
                    case OrderBean.ORDER_STATUS_DAITUIKUAN://待退款

                        tvOrderQuxiaoTuikuan.setVisibility(View.VISIBLE);
                        tvOrderQuxiaoTuikuan.setOnClickListener(this);
                        break;

                    case OrderBean.ORDER_STATUS_YITUIKUAN:  //已退款
                        tvOrderDel.setVisibility(View.VISIBLE);
                        tvOrderDel.setOnClickListener(this);
                        break;
                }

            } else if (HoteUtil.HOTEL_FAST_BUY_CODE.equals(hotelType)) {//限时抢购

                llRoomOrderHotel.setVisibility(View.VISIBLE);

                llOtherOrderHotel.setVisibility(View.GONE);

                tvOtherOrderMoney.setText("总额： ¥" + Utils.subZeroAndDot(orderInfo.getOrderAmount() + ""));

                if (!TextUtils.isEmpty(orderInfo.getVerificationCode())) {

                    tvOrderPromt.setText("核销码：" + orderInfo.getVerificationCode());
                }

                //房间名
                hotelsName.setText(hotelInfo.getHotelName());
                //房间地址
                address.setText(hotelInfo.getHotelAddr());
                //酒店电话
                String phoneTel = hotelInfo.getTel();
                if (!TextUtils.isEmpty(phoneTel)) {

                    tvHotelPhoneNumber.setVisibility(View.VISIBLE);

                    tvHotelPhoneNumber.setTag(phoneTel);

                } else {

                    tvHotelPhoneNumber.setVisibility(View.GONE);
                }
                //供应商电话
                String phoneTelStr = orderInfo.getShopTel();

                if (!TextUtils.isEmpty(phoneTelStr)) {

                    tvSupplierPhoneNumber.setVisibility(View.VISIBLE);

                    tvSupplierPhoneNumber.setTag(phoneTelStr);

                } else {

                    tvSupplierPhoneNumber.setVisibility(View.GONE);
                }

                llOneOrder.setVisibility(View.VISIBLE);
                tvOrderFunction.setVisibility(View.GONE);

                tvOrderFanxian.setVisibility(View.GONE);

                tvOrderDel.setVisibility(View.GONE);

                tvOrderPingjia.setVisibility(View.GONE);

                tvOrderPay.setVisibility(View.GONE);

                tvOrderAgain.setVisibility(View.GONE);

                tvOrderQuxiaoTuikuan.setVisibility(View.GONE);

                tvOrderTuikuan.setVisibility(View.GONE);

                //限时抢购的订单状态对应的操作
                switch (orderInfo.getOrderStatus()) {
                    case OrderBean.ORDER_STATUS_NO_PAY:  //待支付

                        tvOrderPay.setVisibility(View.VISIBLE);
                        tvOrderFunction.setVisibility(View.VISIBLE);

                        tvOrderPay.setOnClickListener(this);
                        tvOrderFunction.setOnClickListener(this);

                        break;

                    case OrderBean.ORDER_STATUS_CANCEL:  //已取消

                        tvOrderDel.setVisibility(View.VISIBLE);
                        tvOrderDel.setOnClickListener(this);
                        break;
                    case OrderBean.ORDER_STATUS_YIWANCHENG://已成交

                        tvOrderDel.setVisibility(View.VISIBLE);
                        tvOrderDel.setOnClickListener(this);
                        break;
                    case OrderBean.ORDER_STATUS_YIGUOQI://已过期

                        tvOrderDel.setVisibility(View.VISIBLE);
                        tvOrderDel.setOnClickListener(this);
                        break;

                }

            }

        } else if (type == 1) {//申请退款成功

            dialogMsg("申请成功", "申请返现成功，可在快速入住问题-如何返现中查看发现进度");

        } else if (type == 2) {//退款检查

            orderHandlerFunction(o + "", OrderMessagFuctioneDialog.FUCTION_ORDER_REFUND_CODE);

        } else if (type == 3) {//成功退款成功

            dialogMsg("成功", o + "");
            //重新加载酒店订单详情数据
            initRequest();

        } else if (type == 400) {//删除订单成功

            ToastUtil.i(this, "成功删除订单");

        } else if (type == 500) {//成功补开发票

            ToastUtil.i(this, "成功");
            //重新加载酒店订单详情数据
            initRequest();
        }
    }

    /**
     * 酒店订单消息信息提示语句限时
     */
    private void hotelOrderCancelMsg()
    {
        if (orderInfo == null) {

            return;
        }
        int paymentType = Integer.parseInt(orderInfo.getPaymentType());

        int cancelType = Integer.parseInt(orderInfo.getCancelType());

        StringBuffer sb = new StringBuffer();

        sb.append("订单确认后，");

        if (PayFormIf.PAYMENT_TYPE_DAODIANFU == paymentType) {//到店付款

            sb.append("免费取消");

        } else if (PayFormIf.PAYMENT_TYPE_ONLINE == paymentType) {//在线付

            if (PayFormIf.CANCEL_TYPE_TIMELIMIT == cancelType) {//限时取消

                sb.append("限时取消");
            } else if (PayFormIf.CANCEL_TYPE_PAID == cancelType) {//付费取消
                sb.append("付费取消");
            }

        } else if (PayFormIf.PAYMENT_TYPE_DANBAO == paymentType) {//担保付

            if (PayFormIf.CANCEL_TYPE_TIMELIMIT == cancelType) {//限时取消

                sb.append("限时取消");
            } else if (PayFormIf.CANCEL_TYPE_PAID == cancelType) {//付费取消
                sb.append("付费取消");
            }

        }

        tvCancelOrderMsg.setText(sb.toString());

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (type == 0) {

        } else if (type == 1) {//申请退款失败

            dialogMsg("申请失败", "申请返现失败，请自行核查相关信息，如有不解，请联系客服");

        } else if (type == 2) {//退款检测失败


        } else if (type == 3) {//成功退款失败

            dialogMsg("失败", o + "");

        } else if (type == 400) {//删除订单失败

            dialogMsg("失败", "该订单操作失败");

            finish();
        }

    }

    /**
     * dialog提示信息
     *
     * @param title
     * @param msg
     */
    private void dialogMsg(String title, String msg)
    {

        HotelReturenMoneySucceedDialog hotelProductFragmentDialog = new HotelReturenMoneySucceedDialog();

        Bundle bundle = new Bundle();

        bundle.putString("msg", msg);

        bundle.putString("titleName", title);

        hotelProductFragmentDialog.setArguments(bundle);

        hotelProductFragmentDialog.show(getFragmentManager(), "roomFt");
    }

    /**
     * 订单操作功能提示
     *
     * @param cancelDateStr
     */
    private void orderHandlerFunction(String cancelDateStr, int code)
    {

        if (orderInfo == null) {

            return;
        }

        OrderMessagFuctioneDialog hotelOrderDelMessageDialog = new OrderMessagFuctioneDialog();

        hotelOrderDelMessageDialog.setDialogHandler(dialogHandler);

        hotelOrderDelMessageDialog.setCode(code);

        Bundle bundleDel = new Bundle();

        bundleDel.putString("dialogMsg", cancelDateStr);

        hotelOrderDelMessageDialog.setArguments(bundleDel);

        hotelOrderDelMessageDialog.show(getFragmentManager(), "roomFt");
    }

    private Handler dialogHandler = new Handler() {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            int what = msg.what;

            //发送退款确认请求
            ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);

            if (what == OrderMessagFuctioneDialog.FUCTION_ORDER_REFUND_CODE) {

                Map<String, Object> mapRefundMap = new HashMap<>();

                mapRefundMap.put("orderId", orderId);

                mapRefundMap.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));

                String typeCode = hotelType;

                if (HoteUtil.HOTEL_ROOM_CODE.equals(typeCode)) {//房间碎片

                    mapRefundMap.put("goodsName", roomInfo.getRoomsName());

                    mapRefundMap.put("storeId", hotelInfo.getHotelId());

                } else if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(typeCode) || HoteUtil.HOTEL_BAR_CODE.equals(typeCode)
                        || HoteUtil.HOTEL_SPA_CODE.equals(typeCode) || HoteUtil.HOTEL_LOBBY_BAR_CODE.equals(typeCode)
                        || HoteUtil.HOTEL_GYM_CODE.equals(typeCode) || HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(typeCode)) {
                    mapRefundMap.put("goodsName", goodsInfoBean.getGoodsName());

                    mapRefundMap.put("storeId", hotelInfo.getHotelId());

                } else if (HoteUtil.HOTEL_CARD_EQUITY_CODE.equals(typeCode)) {

                    mapRefundMap.put("goodsName", goodsInfoBean.getGiftsName());

                    mapRefundMap.put("storeId", orderInfo.getShopId());
                }

                hotelTicketsDetailPresenter.confirmRefundMoney(CardConstant.ORDER_REFUNDORDERCONFIRM, mapRefundMap);

            } else if (what == OrderMessagFuctioneDialog.FUCTION_ORDER_DEL_CODE) {//删除

                ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);
                Map<String, Object> params = new HashMap<>();
                params.put("orderId", orderId);
                params.put("operateStatus", OrderBean.ORDER_OPERATESTATUS_DELETE + "");

                hotelTicketsDetailPresenter.delOrderRequest(CardConstant.card_app_updateorderstatus, params);

            } else if (what == OrderMessagFuctioneDialog.FUCTION_SUPPLEMENTORDERINVOICE_CODE) {

                ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);
                Map<String, Object> params = new HashMap<>();
                params.put("orderId", orderId);
                params.put("invoiceInfo", JSON.toJSONString(invoiceInfoParam));
                hotelTicketsDetailPresenter.sendSupplementOrderInvoiceRequest(CardConstant.ORDER_SUPPLEMENTORDERINVOICE, params);

            }
        }
    };
}