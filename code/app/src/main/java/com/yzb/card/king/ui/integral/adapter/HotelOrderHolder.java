package com.yzb.card.king.ui.integral.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.order.BaseHotelOrderInfoBean;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.activtiy.HotelProductInfoActivity;
import com.yzb.card.king.ui.order.HotelOrderDetailActivity;
import com.yzb.card.king.ui.order.dialog.OrderMessagFuctioneDialog;
import com.yzb.card.king.ui.other.activity.WypjActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.OrderUtils;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;

/**
 * 类  名：酒店订单
 * 作  者：Li Yubing
 * 日  期：2017/8/25
 * 描  述：
 */
public class HotelOrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    @ViewInject(R.id.tvProductName)
    public TextView tvProductName;

    @ViewInject(R.id.tvHotelName)
    public TextView tvHotelName;

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

    @ViewInject(R.id.tvOrderAmount)
    public TextView tvOrderAmount;

    @ViewInject(R.id.tvOrderStatus)
    public TextView tvOrderStatus;

    @ViewInject(R.id.tvOrderData)
    public TextView tvOrderData;

    @ViewInject(R.id.llParent)
    private LinearLayout llParent;

    private Handler handler;

    private Activity context;

    public HotelOrderHolder(View itemView, Activity mContext,Handler handler)
    {
        super(itemView);
        x.view().inject(this, itemView);

        context = mContext;

        this.handler = handler;

        tvOrderFunction.setOnClickListener(this);

        tvOrderFanxian.setOnClickListener(this);

        tvOrderDel.setOnClickListener(this);

        tvOrderPingjia.setOnClickListener(this);

        tvOrderPay.setOnClickListener(this);

        tvOrderAgain.setOnClickListener(this);

        llParent.setOnClickListener(this);
    }

    public void initData(OrderBean orderBean)
    {
        String hoteTyp = orderBean.getHotelInfo().getHotelType();

        tvOrderFunction.setVisibility(View.GONE);

        tvOrderFanxian.setVisibility(View.GONE);

        tvOrderDel.setVisibility(View.GONE);

        tvOrderPingjia.setVisibility(View.GONE);

        tvOrderPay.setVisibility(View.GONE);

        tvOrderAgain.setVisibility(View.GONE);

        tvOrderQuxiaoTuikuan.setVisibility(View.GONE);

        tvOrderTuikuan.setVisibility(View.GONE);

        if (HoteUtil.HOTEL_ROOM_CODE.equals(hoteTyp)) {//房间

            tvHotelName.setText(orderBean.getHotelInfo().getHotelName());

            tvProductName.setText(orderBean.getHotelInfo().getGoodsName());


            switch (orderBean.getOrderStatus()) {

                case OrderBean.ORDER_STATUS_DAIQUEREN://待确认

                    tvOrderFunction.setVisibility(View.VISIBLE);

                    break;
                case OrderBean.ORDER_STATUS_NO_PAY:  //待支付

                    tvOrderFunction.setVisibility(View.VISIBLE);

                    tvOrderPay.setVisibility(View.VISIBLE);

                    break;
                case OrderBean.ORDER_STATUS_YIQUEREN:  //已确认

                    tvOrderFunction.setVisibility(View.VISIBLE);

                    break;
                case OrderBean.ORDER_STATUS_YIRUZHU://已入住

                    if(orderBean.getHotelInfo().getVoteStatus()== 0){

                        tvOrderPingjia.setVisibility(View.VISIBLE);
                   }

                    tvOrderAgain.setVisibility(View.VISIBLE);

                    break;
                case OrderBean.ORDER_STATUS_YILIDIAN://已离店

                    tvOrderFanxian.setVisibility(View.VISIBLE);

                    tvOrderAgain.setVisibility(View.VISIBLE);
                    break;
                case OrderBean.ORDER_STATUS_CANCEL:  //已取消

                    tvOrderDel.setVisibility(View.VISIBLE);

                    tvOrderAgain.setVisibility(View.VISIBLE);
                    break;
                case OrderBean.ORDER_STATUS_QUERENSHIBAI:  //确认失败

                case OrderBean.ORDER_STATUS_YUDINGWEIRUZHU://预订未入住

                    tvOrderDel.setVisibility(View.VISIBLE);

                    break;

            }

        } else if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(hoteTyp) || HoteUtil.HOTEL_BAR_CODE.equals(hoteTyp) || HoteUtil.HOTEL_LOBBY_BAR_CODE.equals(hoteTyp) || HoteUtil.HOTEL_SPA_CODE.equals(hoteTyp) || HoteUtil.HOTEL_GYM_CODE.equals(hoteTyp) || HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(hoteTyp)) {//餐厅、酒吧、大堂吧、SPA、健身房、游泳池

            tvProductName.setText(orderBean.getHotelInfo().getPolicysName());

            tvHotelName.setText(orderBean.getHotelInfo().getGoodsName());

            switch (orderBean.getOrderStatus()) {

                case OrderBean.ORDER_STATUS_NO_PAY:  //待支付

                    tvOrderFunction.setVisibility(View.VISIBLE);

                    tvOrderPay.setVisibility(View.VISIBLE);

                    break;

                case OrderBean.ORDER_STATUS_CANCEL:  //已取消

                    tvOrderDel.setVisibility(View.VISIBLE);

                    tvOrderAgain.setVisibility(View.VISIBLE);
                    break;

                case OrderBean.ORDER_STATUS_COMPLETE://已支付

                  //  tvOrderFunction.setVisibility(View.VISIBLE);

                    tvOrderTuikuan.setVisibility(View.VISIBLE);

                    break;

                case OrderBean.ORDER_STATUS_DAITUIKUAN:  //待退款

                    tvOrderQuxiaoTuikuan.setVisibility(View.VISIBLE);

                    break;
                case OrderBean.ORDER_STATUS_YIWANCHENG://已成交

                    tvOrderDel.setVisibility(View.VISIBLE);

                    break;
                case OrderBean.ORDER_STATUS_YIGUOQI://已过期

                    tvOrderTuikuan.setVisibility(View.VISIBLE);

                    break;

                case OrderBean.ORDER_STATUS_YITUIKUAN:  //已退款

                    tvOrderDel.setVisibility(View.VISIBLE);

                    break;

            }
        }else if(HoteUtil.HOTEL_FAST_BUY_CODE.equals(hoteTyp) ){//限时抢购

            tvProductName.setText(orderBean.getHotelInfo().getGoodsName());

            tvHotelName.setText(orderBean.getHotelInfo().getHotelName());

            switch (orderBean.getOrderStatus()) {

                case OrderBean.ORDER_STATUS_NO_PAY:  //待支付

                    tvOrderFunction.setVisibility(View.VISIBLE);

                    tvOrderPay.setVisibility(View.VISIBLE);

                    break;

                case OrderBean.ORDER_STATUS_CANCEL:  //已取消


                    tvOrderDel.setVisibility(View.VISIBLE);

                    break;

                case OrderBean.ORDER_STATUS_COMPLETE://已支付



                    break;
                case OrderBean.ORDER_STATUS_YIWANCHENG:  ///已成交

                    tvOrderDel.setVisibility(View.VISIBLE);

                    break;

                case OrderBean.ORDER_STATUS_YIGUOQI://已过期

                    tvOrderDel.setVisibility(View.VISIBLE);

                    break;

            }

        }else if(HoteUtil.HOTEL_CARD_EQUITY_CODE.equals(hoteTyp)){

            tvProductName.setText(orderBean.getHotelInfo().getGoodsName());

            tvHotelName.setText(orderBean.getHotelInfo().getHotelName());

            switch (orderBean.getOrderStatus()) {

                case OrderBean.ORDER_STATUS_NO_PAY:  //待支付

                    tvOrderFunction.setVisibility(View.VISIBLE);

                    tvOrderPay.setVisibility(View.VISIBLE);

                    break;

                case OrderBean.ORDER_STATUS_CANCEL:  //已取消

                    tvOrderDel.setVisibility(View.VISIBLE);

                    break;

                case OrderBean.ORDER_STATUS_COMPLETE://已支付

                    tvOrderTuikuan.setVisibility(View.VISIBLE);

                    break;
                case OrderBean.ORDER_STATUS_YIWANCHENG:  ///已成交

                    tvOrderDel.setVisibility(View.VISIBLE);

                    break;

                case OrderBean.ORDER_STATUS_DAITUIKUAN://待退款

                    tvOrderQuxiaoTuikuan.setVisibility(View.VISIBLE);

                    break;
                case OrderBean.ORDER_STATUS_YITUIKUAN://已退款

                    tvOrderDel.setVisibility(View.VISIBLE);

                    break;

            }
        }

        tvOrderAmount.setText(Utils.subZeroAndDot(orderBean.getOrderAmount()));

        long tiemL = Utils.toTimestamp(orderBean.getOrderTime(), 1);

        tvOrderData.setText(Utils.toData(tiemL, 5));

        tvOrderStatus.setText(OrderUtils.getOrderStatus(orderBean.getOrderStatus()));

        llParent.setTag(orderBean);

        tvOrderQuxiaoTuikuan.setTag(orderBean);

        tvOrderFunction.setTag(orderBean);

        tvOrderFunction.setTag(orderBean);

        tvOrderFanxian.setTag(orderBean);

        tvOrderDel.setTag(orderBean);

        tvOrderPingjia.setTag(orderBean);

        tvOrderPay.setTag(orderBean);

        tvOrderAgain.setTag(orderBean);

    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId()) {

            case R.id.tvOrderFunction://取消订单

                if (v.getTag() != null) {

                    OrderBean orderBeanOne = (OrderBean) v.getTag();

                    Message message = handler.obtainMessage();

                    message.what = 100;//

                    message.obj = orderBeanOne;

                    handler.sendMessage(message);
                }
                break;

            case R.id.tvOrderQuxiaoTuikuan://取消退款
                if(v.getTag() != null){
                    OrderBean orderBean = (OrderBean) v.getTag();

                    Message message = handler.obtainMessage();

                    message.what = 100;//

                    message.obj = orderBean;

                    handler.sendMessage(message);
                }

                break;

            case R.id.tvOrderTuikuan://退款
                if(v.getTag() != null){
                    OrderBean orderBean = (OrderBean) v.getTag();

                    Message message = handler.obtainMessage();

                    message.what = 100;//

                    message.obj = orderBean;

                    handler.sendMessage(message);
                }

                break;
            case R.id.tvOrderFanxian://申请返现
                if(v.getTag() != null){
                    OrderBean orderBean = (OrderBean) v.getTag();

                    Message messageDel = handler.obtainMessage();

                    messageDel.what = 2;

                    messageDel.obj = orderBean;

                    handler.sendMessage(messageDel);
                }

                break;

            case R.id.tvOrderDel://删除
                if (v.getTag() != null) {

                    OrderBean orderBeanDel = (OrderBean) v.getTag();

                    Message messageDel = handler.obtainMessage();

                    messageDel.what = 0;

                    messageDel.obj = orderBeanDel;

                    handler.sendMessage(messageDel);
                }
                break;

            case R.id.tvOrderPingjia://评价
                if(v.getTag() != null){
                    OrderBean orderBean = (OrderBean) v.getTag();

                    BaseHotelOrderInfoBean hotelOrderInfoBean = orderBean.getHotelInfo();

                    Intent intent = new Intent(context, WypjActivity.class);
                    intent.putExtra("industryId", Integer.parseInt(HoteUtil.HOTEL_CARD_EQUITY_CODE));
                    intent.putExtra("shopId", hotelOrderInfoBean.getHotelId());
                     intent.putExtra("storeId", hotelOrderInfoBean.getStoreId());
                    intent.putExtra("goodsIdTwo", hotelOrderInfoBean.getGoodsId());
                    intent.putExtra("goodsIdThree", hotelOrderInfoBean.getPolicysId());
                    intent.putExtra("goodsName", hotelOrderInfoBean.getGoodsName());
                    intent.putExtra("orderId", Long.parseLong(orderBean.getOrderId()));
                    context.startActivity(intent);
                }

                break;
            case R.id.tvOrderPay://支付
                if(v.getTag() != null){
                    OrderBean orderBean = (OrderBean) v.getTag();

                    Message message = handler.obtainMessage();

                    message.what = 600;//

                    message.obj = orderBean;

                    handler.sendMessage(message);

                }

                break;
            case R.id.tvOrderAgain://再次预订

                if(v.getTag() != null){
                    Date startDate = new Date();
                    Date endDate = DateUtil.addDay(startDate, 1);
                    //设置查看酒店详情参数
                    HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();
                    productListParam.setArrDate(DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE));
                    productListParam.setDepDate( DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_DATE));

                    OrderBean orderBean = (OrderBean) v.getTag();
                    Intent intent = new Intent(context, HotelProductInfoActivity.class);
                    intent.putExtra("hotelId",  orderBean.getHotelInfo().getHotelId()+"");
                    context.startActivity(intent);
                }

                break;
            case R.id.llParent://查看酒店订单详情

                if (v.getTag() != null) {
                    OrderBean orderBean = (OrderBean) v.getTag();
                    String orderId = orderBean.getOrderId();
                    String hotelType =  orderBean.getHotelInfo().getHotelType();
                    Intent intent = new Intent(context, HotelOrderDetailActivity.class);
                    intent.putExtra("id", orderId);
                    intent.putExtra("hotelType", hotelType);
                    context.startActivity(intent);
                }

                break;


            default:
                break;

        }
    }




}