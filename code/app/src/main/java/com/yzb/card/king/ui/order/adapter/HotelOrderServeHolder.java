package com.yzb.card.king.ui.order.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.GiftComboBean;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.common.PayFormIf;
import com.yzb.card.king.bean.common.PayOrderBean;
import com.yzb.card.king.bean.order.HotelOrderServeBean;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.activity.HotelBillActivity;
import com.yzb.card.king.ui.app.manager.DrivingRouteOverlayManager;
import com.yzb.card.king.ui.app.manager.PoiOverlayManager;
import com.yzb.card.king.ui.appwidget.appdialog.MyTransitDialog;
import com.yzb.card.king.ui.discount.activity.pay.AppPreferentialPaymentActivity;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.adapter.RouteLineAdapter;
import com.yzb.card.king.ui.order.HotelOrderDetailActivity;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.BaiduMapUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.OrderUtils;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import java.util.Date;
import java.util.List;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/16
 * 描  述：
 */
public class HotelOrderServeHolder extends BaseViewHolder<HotelOrderServeBean> implements View.OnClickListener {


    private Context context;

    private TextView tvOrderDate, tvOrderStatus, tvOrderMsg, tvFunctionOne, tvFunctionTwo, tvFunctionThree,tvFunctionFour, tvComboPrice, tvConfirm;

    private RelativeLayout quesation_layout;

    private LinearLayout llPay,llParent;

    protected BaiduMap baiduMap;

    private Handler handler;

    private int index;

    public HotelOrderServeHolder(ViewGroup parent,Handler handler)
    {
        super(parent, R.layout.view_item_hotel_order_serve);

        context = parent.getContext();

        this.handler = handler;

    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        tvOrderDate = findViewById(R.id.tvOrderDate);

        tvOrderStatus = findViewById(R.id.tvOrderStatus);

        tvOrderMsg = findViewById(R.id.tvOrderMsg);

        tvFunctionOne = findViewById(R.id.tvFunctionOne);

        tvFunctionTwo = findViewById(R.id.tvFunctionTwo);

        tvFunctionThree = findViewById(R.id.tvFunctionThree);

        tvFunctionFour = findViewById(R.id.tvFunctionFour);

        tvComboPrice = findViewById(R.id.tvComboPrice);

        tvConfirm = findViewById(R.id.tvConfirm);

        quesation_layout = findViewById(R.id.quesation_layout);

        llPay = findViewById(R.id.llPay);

        llParent = findViewById(R.id.llParent);

        llParent.setOnClickListener(this);

        tvConfirm.setOnClickListener(this);

        tvFunctionOne.setOnClickListener(this);

        tvFunctionTwo.setOnClickListener(this);

        tvFunctionThree.setOnClickListener(this);

        tvFunctionFour.setOnClickListener(this);

    }

    @Override
    public void setData(HotelOrderServeBean data)
    {
        super.setData(data);
        //发起驾车路线规划

        index = getAdapterPosition();
       //routePlanSearch.drivingSearch(getDrivingRoutePlanOption(data.getLat(),data.getLng()));
        llParent.setTag(data.getOrderId());

        tvConfirm.setTag(data);

        String arrDate = data.getArrDate();

        Date arrDateD = DateUtil.string2Date(arrDate, DateUtil.DATE_FORMAT_DATE);

        String orderDateDStr = DateUtil.date2String(arrDateD, DateUtil.DATE_FORMAT_MONTH_DAY);

        String orderDateDWeek = DateUtil.getDateExplain(arrDateD);

        if ("今日".equals(orderDateDWeek)) {
            tvOrderDate.setBackgroundResource(R.mipmap.bg_blue_date_order_serve);

            tvOrderDate.setTextColor(context.getResources().getColor(R.color.white_ffffff));
        } else {

            tvOrderDate.setBackgroundResource(R.mipmap.bg_blue_kong_date_order_serve);

            tvOrderDate.setTextColor(Color.parseColor("#436a8e"));
        }

        tvOrderDate.setText(orderDateDStr + " " + orderDateDWeek);

        int orderStatus = data.getOrderStatus();

        tvOrderStatus.setText(OrderUtils.getOrderStatus(orderStatus));

        StringBuffer sb = new StringBuffer();

        sb.append(data.getHotelName());

        sb.append("\n").append("入住时间：").append(orderDateDStr + " " + orderDateDWeek);


        Date depDate = DateUtil.string2Date(data.getDepDate(), DateUtil.DATE_FORMAT_DATE);

        int spaceTime = DateUtil.naturalDaysBetween(arrDateD, depDate);

        sb.append("\n").append("房间名称：").append(data.getRoomsName() + " " + spaceTime + "晚/" + data.getGoodsQuantity() + "间");

        sb.append("\n").append("酒店地址：").append(data.getHotelAddr());

        tvOrderMsg.setText(sb.toString());

        quesation_layout.setVisibility(View.VISIBLE);

        tvFunctionOne.setVisibility(View.GONE);

        tvFunctionTwo.setVisibility(View.GONE);

        tvFunctionThree.setVisibility(View.GONE);

        tvFunctionFour.setVisibility(View.INVISIBLE);

        llPay.setVisibility(View.GONE);

        int paymentType = data.getPaymentType();

        if (PayFormIf.PAYMENT_TYPE_DAODIANFU == paymentType) {

            tvComboPrice.setText(Utils.subZeroAndDot(data.getGuaranteeAmount()+""));

            switch (orderStatus) {

                case OrderBean.ORDER_STATUS_DAIQUEREN://待确认

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("催确认");

                    tvFunctionTwo.setText("查询酒店信息");

                    tvFunctionThree.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_CUIKUANQUEREN_CODE);
                    tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE);
                    tvFunctionThree.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    break;

                case OrderBean.ORDER_STATUS_YIQUEREN:  //已确认

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);
                    tvFunctionFour.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("如何开发票");

                    tvFunctionTwo.setText("查询确认信息");

                    tvFunctionThree.setText("取消订单");

                    tvFunctionFour.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_RUHEKAIFAPIAO_CODE);
                    tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAKANQUERENXINXI_CODE);
                    tvFunctionThree.setTag(HoteUtil.HOTEL_CANCELORDER_CODE);
                    tvFunctionFour.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    break;
                case OrderBean.ORDER_STATUS_YIRUZHU://已入住
                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("如何开发票");

                    tvFunctionTwo.setText("如何返现");

                    tvFunctionThree.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_RUHEKAIFAPIAO_CODE);
                    tvFunctionTwo.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);

                    tvFunctionThree.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    break;
                case OrderBean.ORDER_STATUS_YILIDIAN://已离店

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("忘记开发票");

                    tvFunctionTwo.setText("如何返现");

                    tvFunctionThree.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_WANGJIKAIFAPIAO_CODE);
                    tvFunctionTwo.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);

                    tvFunctionThree.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);
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

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("查询酒店信息");

                    tvFunctionTwo.setText("支付遇到问题");

                    tvFunctionThree.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE);
                    tvFunctionTwo.setTag(HoteUtil.HOTEL_YUDAOWENTI_CODE);
                    tvFunctionThree.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    llPay.setVisibility(View.VISIBLE);

                    break;
                case OrderBean.ORDER_STATUS_DAIQUEREN://待确认

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("催确认");

                    tvFunctionTwo.setText("查询酒店信息");

                    tvFunctionThree.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_CUIKUANQUEREN_CODE);
                    tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE);
                    tvFunctionThree.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    break;

                case OrderBean.ORDER_STATUS_YIQUEREN:  //已确认

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionFour.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("如何开发票");

                    tvFunctionTwo.setText("查询确认信息");

                    tvFunctionThree.setText("取消订单");

                    tvFunctionFour.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_RUHEKAIFAPIAO_CODE);
                    tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAKANQUERENXINXI_CODE);
                    tvFunctionThree.setTag(HoteUtil.HOTEL_CANCELORDER_CODE);
                    tvFunctionFour.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    break;
                case OrderBean.ORDER_STATUS_YIRUZHU://已入住

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionFour.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("如何开发票");

                    tvFunctionTwo.setText("如何返现");

                    tvFunctionThree.setText("担保何时退还");

                    tvFunctionFour.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_RUHEKAIFAPIAO_CODE);
                    tvFunctionTwo.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);
                    tvFunctionThree.setTag(HoteUtil.HOTEL_DANBAOHESHITUIHUAN_CODE);
                    tvFunctionFour.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    break;
                case OrderBean.ORDER_STATUS_YILIDIAN://已离店

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionFour.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("忘记开发票");

                    tvFunctionTwo.setText("如何返现");

                    tvFunctionThree.setText("担保何时退还");

                    tvFunctionFour.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_WANGJIKAIFAPIAO_CODE);
                    tvFunctionTwo.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);
                    tvFunctionThree.setTag(HoteUtil.HOTEL_DANBAOHESHITUIHUAN_CODE);
                    tvFunctionFour.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    break;
                default:

                    quesation_layout.setVisibility(View.GONE);
                    break;
            }

        } else if (PayFormIf.PAYMENT_TYPE_ONLINE == paymentType) {

            tvComboPrice.setText(Utils.subZeroAndDot(data.getOrderAmount()+""));

            switch (orderStatus) {

                case OrderBean.ORDER_STATUS_NO_PAY://待支付

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("查询酒店信息");

                    tvFunctionTwo.setText("支付遇到问题");

                    tvFunctionThree.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE);

                    tvFunctionTwo.setTag(HoteUtil.HOTEL_YUDAOWENTI_CODE);

                    tvFunctionThree.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    llPay.setVisibility(View.VISIBLE);

                    break;
                case OrderBean.ORDER_STATUS_DAIQUEREN://待确认

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("催确认");

                    tvFunctionTwo.setText("查询酒店信息");

                    tvFunctionThree.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_CUIKUANQUEREN_CODE);

                    tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE);

                    tvFunctionThree.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);
                    break;

                case OrderBean.ORDER_STATUS_YIQUEREN:  //已确认

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionFour.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("补开发票");

                    tvFunctionTwo.setText("查询确认信息");

                    tvFunctionThree.setText("取消订单");

                    tvFunctionFour.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_BUKAIFAPIAO_CODE);

                    tvFunctionTwo.setTag(HoteUtil.HOTEL_CHAKANQUERENXINXI_CODE);

                    tvFunctionThree.setTag(HoteUtil.HOTEL_CANCELORDER_CODE);

                    tvFunctionFour.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    break;
                case OrderBean.ORDER_STATUS_YIRUZHU://已入住

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("补开发票");

                    tvFunctionTwo.setText("如何返现");

                    tvFunctionThree.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_BUKAIFAPIAO_CODE);

                    tvFunctionTwo.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);

                    tvFunctionThree.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    break;
                case OrderBean.ORDER_STATUS_YILIDIAN://已离店

                    tvFunctionOne.setVisibility(View.VISIBLE);

                    tvFunctionTwo.setVisibility(View.VISIBLE);

                    tvFunctionThree.setVisibility(View.VISIBLE);

                    tvFunctionFour.setVisibility(View.VISIBLE);

                    tvFunctionOne.setText("补开发票");

                    tvFunctionTwo.setText("发票何时寄");

                    tvFunctionThree.setText("如何返现");

                    tvFunctionFour.setText("联系客服");

                    tvFunctionOne.setTag(HoteUtil.HOTEL_BUKAIFAPIAO_CODE);
                    tvFunctionTwo.setTag(HoteUtil.HOTEL_FAPIAOHESHIJISONG_CODE);
                    tvFunctionThree.setTag(HoteUtil.HOTEL_RUHEFANXIAN_CODE);
                    tvFunctionFour.setTag(HoteUtil.HOTEL_LIANXIKEFU_CODE);

                    break;
                default:

                    quesation_layout.setVisibility(View.GONE);
                    break;
            }
        }

    }

    /**
     * 获取出发结点；
     */
    protected PlanNode getFromPlanNode()
    {
        Location city = GlobalApp.getPositionedCity();
        return PlanNode.withLocation(new LatLng(city.latitude, city.longitude));
    }

    /**
     * 获取目的地结点；
     */
    protected PlanNode getToPlanNode(double lat, double lng)
    {
        return PlanNode.withLocation(new LatLng(lat, lng));
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId()){

            case R.id.llParent:

                if(v.getTag() != null){

                    long orderId = (long) v.getTag();

                    Intent intent = new Intent(context, HotelOrderDetailActivity.class);
                    intent.putExtra("id", orderId+"");
                    intent.putExtra("hotelType", HoteUtil.HOTEL_ROOM_CODE);
                    context.startActivity(intent);

                }

                break;

            case R.id.tvConfirm:

                if(v.getTag() != null){

                    HotelOrderServeBean data = (HotelOrderServeBean) v.getTag();
                    //发放平台
                    int issuePlatform = 0;
                    //行业id
                    int industryId = Integer.parseInt(AppConstant.hotel_id);//酒店行业id

                    GoldTicketParam goldTicketParam = new GoldTicketParam();

                    goldTicketParam.setIssuePlatform(issuePlatform);

                    goldTicketParam.setIndustryId(industryId);

                    goldTicketParam.setShopId(data.getShopId());

                    goldTicketParam.setStoreId(data.getHotelId());

                    goldTicketParam.setGoodsId(data.getRoomsId());

                    PayOrderBean bean = new PayOrderBean();

                    bean.setOrderAmount(data.getOrderAmount());

                    bean.setNotifyUrl(data.getNotifyUrl());

                    bean.setOrderId(data.getOrderId());

                    bean.setOrderNo(data.getOrderNo());

                    bean.setOrderTime(data.getOrderCreateTime());

                    Intent intent = new Intent(context, AppPreferentialPaymentActivity.class);

                    intent.putExtra("orderData", bean);

                    intent.putExtra("goldTicketParam", goldTicketParam);

                    intent.putExtra("goodName", data.getHotelName() + "(" + data.getRoomsName() + ")");

                    context.startActivity(intent);

                }

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
            case R.id.tvFunctionFour:

                if(v.getTag() != null){

                    int code4 = (int) v.getTag();

                    hotelRoomQuestion(code4);
                }

                break;

            default:
                break;
        }
    }

    private void hotelRoomQuestion(int code1)
    {
        LogUtil.e("login","code1="+code1);

        if (HoteUtil.HOTEL_CUIKUANQUEREN_CODE == code1) {//催确认

            ToastUtil.i(context,"商家已收到信息，正在处理，请等待");

        } else  if (HoteUtil.HOTEL_LIANXIKEFU_CODE == code1) {//联系客服

            callHotelPhone(AppConstant.APP_TEL);

        }  else if (HoteUtil.HOTEL_BUKAIFAPIAO_CODE == code1) {//补开发票

           Message message = handler.obtainMessage();

            message.what = 0;//不开发票

            message.arg2 = getAdapterPosition();

            handler.sendMessage(message);

        } else {

            Message message = handler.obtainMessage();

            message.what = code1;//

            message.arg2 = getAdapterPosition();

            handler.sendMessage(message);


        }

    }

    private void callHotelPhone(String number)
    {
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        context.startActivity(intent);
    }



}
