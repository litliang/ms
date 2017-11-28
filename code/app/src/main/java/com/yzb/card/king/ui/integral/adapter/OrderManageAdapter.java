package com.yzb.card.king.ui.integral.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.common.PayOrderBean;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.ticket.TicketOrderBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.appwidget.appdialog.ConfirmDialog;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.bonus.activity.BounsCreateActivity;
import com.yzb.card.king.ui.bonus.activity.RedBagDetailSendActivity;
import com.yzb.card.king.ui.discount.activity.pay.AppPreferentialPaymentActivity;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.gift.activity.BuyMindPhysCardActivity;
import com.yzb.card.king.ui.gift.activity.GiftCardGiveMindActivity;
import com.yzb.card.king.ui.gift.activity.GiftCardUseDetailActivity;
import com.yzb.card.king.ui.gift.activity.GiveCardActivity;
import com.yzb.card.king.ui.gift.fragment.SendGiftCardDialog;
import com.yzb.card.king.ui.hotel.persenter.impl.UpdateOrderStatusPresenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.presenter.CommandPresenter;
import com.yzb.card.king.ui.my.view.CommandView;
import com.yzb.card.king.ui.order.BusTicketsDetaildActivity;
import com.yzb.card.king.ui.order.ModifyOrderActivity;
import com.yzb.card.king.ui.order.PlaneTicketsDetailActivity;
import com.yzb.card.king.ui.order.ShipTicketsDetailActivity;
import com.yzb.card.king.ui.order.TaxiTicketsDetailActivity;
import com.yzb.card.king.ui.order.TourismTicketsDetailActivity;
import com.yzb.card.king.ui.order.TrainTicketsDetailActivity;
import com.yzb.card.king.ui.order.dialog.HotelReturenMoneySucceedDialog;
import com.yzb.card.king.ui.order.dialog.OrderMessagFuctioneDialog;
import com.yzb.card.king.ui.order.presenter.HotelTicketsDetailPresenter;
import com.yzb.card.king.ui.order.presenter.OrderListsPresenter;
import com.yzb.card.king.ui.ticket.activity.RefundTicketActivity;
import com.yzb.card.king.ui.ticket.holder.BaseNullHolder;
import com.yzb.card.king.ui.ticket.presenter.DiscountPayPresenter;
import com.yzb.card.king.ui.ticket.view.DiscountPayView;
import com.yzb.card.king.ui.travel.activity.TravelProductDetailActivity;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.OrderUtils;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yzb.card.king.util.UiUtils.getString;

/**
 * 订单适配器
 */
public class OrderManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiscountPayView, CommandView, BaseViewLayerInterface {

    private static final String SPLIT = ",";

    private Activity mContext;

    private List<OrderBean> mData;

    private LayoutInflater inflater;

    private Handler uiHandler;

    private DiscountPayPresenter discountPayPresenter;

    private UpdateOrderStatusPresenter orderStatusPresenter;

    private OrderListsPresenter orderListsPresenter;

    private HotelTicketsDetailPresenter hotelTicketsDetailPresenter;

    private CommandPresenter commandPresenter;

    private OrderBean currentOrderBean = null;

    public OrderManageAdapter(Activity context)
    {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);

        discountPayPresenter = new DiscountPayPresenter(context, this);

        commandPresenter = new CommandPresenter(this);

        hotelTicketsDetailPresenter = new HotelTicketsDetailPresenter(this);

        mData = new ArrayList<>();
    }

    public void setUiHandler(Handler uiHandler)
    {

        this.uiHandler = uiHandler;
    }

    public void setOrderStatusPresenter(UpdateOrderStatusPresenter orderStatusPresenter)
    {

        this.orderStatusPresenter = orderStatusPresenter;
    }

    public void setOrderListsPresenter(OrderListsPresenter orderListsPresenter)
    {

        this.orderListsPresenter = orderListsPresenter;

    }

    public void clear()
    {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    public List<OrderBean> getData()
    {
        return mData;
    }

    public void appendData(List<OrderBean> dataList)
    {
        mData.clear();

        mData.addAll(dataList);

        notifyDataSetChanged();

    }

    public void addNewData(List<OrderBean> dataList)
    {

        mData.addAll(dataList);

        notifyDataSetChanged();
    }

    /**
     * 设置handler句柄
     *
     * @param myHandler
     */
    public void setMyHandler(Handler myHandler)
    {
        this.myHandler = myHandler;
    }

    /**
     * 删除条目对象
     *
     * @param orderBean
     */
    public void removeItemView(OrderBean orderBean)
    {
        mData.remove(orderBean);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (mData.size() == 0) {//无数据
            return AppConstant.TYPE_NULL;
        } else {
            int orderType = mData.get(position).getOrderType();

            if (orderType == OrderBean.ORDER_TYPE_AIRCRAFT) {// 机票布局

                return OrderBean.ORDER_TYPE_AIRCRAFT;

            } else if (orderType == OrderBean.ORDER_TYPE_LIPING) {// 礼品卡布局

                return OrderBean.ORDER_TYPE_LIPING;

            } else if (orderType == OrderBean.ORDER_TYPE_REDPACGE) {//红包布局

                return OrderBean.ORDER_TYPE_REDPACGE;

            } else if (orderType == OrderBean.ORDER_TYPE_HOTELS) {//酒店

                return OrderBean.ORDER_TYPE_HOTELS;

            } else {//常用布局

                return AppConstant.TYPE_NORMLL;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == AppConstant.TYPE_NULL) { //无数据布局

            return new BaseNullHolder(inflater.inflate(R.layout.base_null_data_item, parent, false));

        } else if (viewType == OrderBean.ORDER_TYPE_AIRCRAFT) { //机票订单条目页

            return new TypePlanVH(inflater.inflate(R.layout.plaint_recyclerview_item_new, parent, false));

        } else if (viewType == OrderBean.ORDER_TYPE_LIPING) { //礼品卡订单条目页

            View view = inflater.inflate(R.layout.item_giftcard_order, parent, false);

            return new GiftcardHolder(view);

        } else if (viewType == OrderBean.ORDER_TYPE_REDPACGE) { //红包订单条目页

            View view = inflater.inflate(R.layout.item_red_envelope_order, parent, false);

            return new RedenvelopeHolder(view);

        } else if (viewType == OrderBean.ORDER_TYPE_HOTELS) {//酒店订单条目

            View view = inflater.inflate(R.layout.item_order_hotel_view, parent, false);

            return new HotelOrderHolder(view, mContext,functionHandler);

        } else { //订单基本布局

            View view = inflater.inflate(R.layout.item_order_manager, parent, false);

            return new OrderManageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position)
    {
        //礼品卡订单；
        if (holder1 instanceof GiftcardHolder) {

            GiftcardHolder holder = (GiftcardHolder) holder1;

            final OrderBean orderBean = mData.get(position);

            final OrderBean.GiftcardInfo giftcardInfo = orderBean.getGiftcardInfo();

            long orderTime = Utils.toTimestamp(orderBean.getOrderTime(), 1);

            holder.tvDate.setText(Utils.toData(orderTime, 4));
            String cardType = "";
            // 1、实体卡 2、心意卡
            if (giftcardInfo == null) {
                cardType = "";
            } else if (giftcardInfo.getCategory() == 1) {
                cardType = "实体卡";
            } else if (giftcardInfo.getCategory() == 2) {
                cardType = "心意" + "-" + giftcardInfo.getTypeName();
            }
            holder.tvCardType.setText(cardType);

            holder.tvRecvPercent.setVisibility(giftcardInfo == null ? View.INVISIBLE : View.VISIBLE);

            boolean isShowAgaineSend = false;

            if (giftcardInfo != null) {
                holder.tvRecvPercent.setText(giftcardInfo.getReceiveQuantity() + "领/" + giftcardInfo.getTotalQuantity() + "总");

                String amount = giftcardInfo.getSurplusAmount();

                if ("0.00".equals(amount) || "0.0".equals(amount) || "0".equals(amount)) {
                    holder.tvRestAmount.setVisibility(View.INVISIBLE);

                    isShowAgaineSend = true;
                } else {
                    holder.tvRestAmount.setVisibility(View.VISIBLE);
                    holder.tvRestAmount.setText("剩余金额¥" + giftcardInfo.getSurplusAmount());
                }

            }

            //金额；
            SpannableString ss = new SpannableString(Utils.subZeroAndDot(orderBean.getOrderAmount()));
            holder.tvOrderAmount.setText(ss);

            holder.tvStatus.setText(OrderUtils.getOrderStatus(orderBean.getOrderStatus()));

            //删除；
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    adapterDelCallBack(orderBean.getOrderId(), OrderBean.ORDER_OPERATESTATUS_DELETE);
                }
            });
            //再次预定；
            holder.tvReBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    // 1、实体卡 2、心意卡
                    if (giftcardInfo != null && 2 == giftcardInfo.getCategory()) {
                        Intent intent = new Intent(mContext, GiftCardGiveMindActivity.class);
                        intent.putExtra("productId", giftcardInfo.getProductId());
                        intent.putExtra("blessWord", giftcardInfo.getBlessWord());
                        intent.putExtra("imageCode", giftcardInfo.getImageCode());
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, BuyMindPhysCardActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });
//            //分享；
//            holder.tvShare.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//
//                    share(position);
//                }
//            });
            //取消；
            holder.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    adapterDelCallBack(orderBean.getOrderId(), OrderBean.ORDER_STATUS_CANCEL);
                }
            });
            //支付；
            holder.tvPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (myHandler != null) {
                        Message message = myHandler.obtainMessage();
                        message.obj = orderBean;
                        myHandler.sendMessage(message);
                    }
                }
            });
            // item点击；
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mContext, GiftCardUseDetailActivity.class);
                    intent.putExtra("orderId", orderBean.getOrderId());
                    mContext.startActivity(intent);
                }
            });
            //  holder.tvShare.setVisibility(View.GONE);
            //再次发送
            holder.tvAgainSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    share(position);
                }
            });

            if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_NO_PAY) //未支付
            {
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.VISIBLE);
                holder.tvReBuy.setVisibility(View.GONE);
                holder.tvAgainSend.setVisibility(View.GONE);
            } else if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_COMPLETE) //已支付
            {
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvReBuy.setVisibility(View.VISIBLE);
                if (isShowAgaineSend) {
                    holder.tvAgainSend.setVisibility(View.GONE);
                } else {
                    if (giftcardInfo.getCategory() == 2) {
                        holder.tvAgainSend.setVisibility(View.VISIBLE);
                    } else {
                        holder.tvAgainSend.setVisibility(View.GONE);
                    }

                }

            } else if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_CANCEL) //已取消 ,已退票,购票失败
            {
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvReBuy.setVisibility(View.VISIBLE);
                holder.tvAgainSend.setVisibility(View.GONE);

            } else if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_YIWANCHENG) //交易成功
            {
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvReBuy.setVisibility(View.VISIBLE);
                holder.tvAgainSend.setVisibility(View.GONE);

            }

        } else if (holder1 instanceof OrderManageHolder) {
            final OrderManageHolder holder = (OrderManageHolder) holder1;
            final OrderBean orderBean = mData.get(position);
            String title;
            String startTime, startTimes;
            String endTime;
            String status;
            String orderAmount;
            holder.orderAmount.setText(Utils.subZeroAndDot(orderBean.getOrderAmount()));

            switch (orderBean.getOrderType()) {
                case OrderBean.ORDER_TYPE_TRAIN:    //火车
                    title = orderBean.getStartCityName() + "——" + orderBean.getEndCityName();
                    holder.title.setText(title);
                    startTime = Utils.toData(Long.parseLong(orderBean.getStartTime()), 2);
                    endTime = Utils.toData(Long.parseLong(orderBean.getEndTime()), 3);
                    holder.hotelname.setText(startTime + getString(R.string.to_str) + endTime);
                    holder.content2.setText(orderBean.getToolNumber());
                    holder.orderTypeLogo.setVisibility(View.VISIBLE);
                    holder.orderTypeLogo.setBackgroundResource(R.mipmap.icon_lable_hc);
                    holder.again.setVisibility(View.VISIBLE);
                    holder.again.setText(getString(R.string.order_bugticket_again));
                    break;
                case OrderBean.ORDER_TYPE_FERRY:    //船

                    title = orderBean.getStartCityName() + "——" + orderBean.getEndCityName();
                    holder.title.setText(title);
                    startTime = Utils.toData(Long.parseLong(orderBean.getStartTime()), 2);
                    endTime = Utils.toData(Long.parseLong(orderBean.getEndTime()), 3);
                    holder.hotelname.setText(startTime + getString(R.string.to_str) + endTime);
                    holder.content2.setText(orderBean.getToolNumber());
                    holder.orderTypeLogo.setVisibility(View.VISIBLE);
                    holder.orderTypeLogo.setBackgroundResource(R.mipmap.icon_lable_chuan);
                    holder.again.setVisibility(View.VISIBLE);
                    holder.again.setText(getString(R.string.order_bugticket_again));
                    break;
                case OrderBean.ORDER_TYPE_CAR:  //汽车
                    title = orderBean.getStartCityName() + "——" + orderBean.getEndCityName();
                    holder.title.setText(title);
                    startTime = Utils.toData(Long.parseLong(orderBean.getStartTime()), 2);
                    endTime = Utils.toData(Long.parseLong(orderBean.getEndTime()), 3);
                    holder.hotelname.setText(startTime + getString(R.string.to_str) + endTime);
                    holder.content2.setText(orderBean.getToolNumber());
                    holder.orderTypeLogo.setVisibility(View.VISIBLE);
                    holder.orderTypeLogo.setBackgroundResource(R.mipmap.icon_lable_zc);
                    holder.again.setVisibility(View.GONE);
                    break;
                case OrderBean.ORDER_TYPE_TAXI: //出租车
                    title = orderBean.getEndAddr();
                    holder.title.setText(title);
                    holder.hotelname.setText(orderBean.getStartAddr());
                    holder.content2.setText(orderBean.getCarType());
                    holder.orderTypeLogo.setVisibility(View.VISIBLE);
                    holder.orderTypeLogo.setBackgroundResource(R.mipmap.icon_lable_jd);
                    holder.again.setVisibility(View.GONE);
                    break;
                case OrderBean.ORDER_TYPE_TOUR: //旅游
                    holder.itemView.setBackgroundResource(R.mipmap.bg_order_manage_travel);

                    holder.orderTypeLogo.setVisibility(View.VISIBLE);

                    holder.orderTypeLogo.setBackgroundResource(R.mipmap.icon_lable_zyx);

                    holder.hotelname.setVisibility(View.GONE);

                    holder.addressTv.setVisibility(View.GONE);

                    holder.llOne.setVisibility(View.GONE);

                    holder.detailCount.setVisibility(View.VISIBLE);

                    holder.timeInterval.setVisibility(View.GONE);

                    int left = CommonUtil.dip2px(GlobalApp.getInstance().getContext(), 30);

                    LinearLayout.LayoutParams lpOne1 = (LinearLayout.LayoutParams) holder.llOne.getLayoutParams();
                    lpOne1.leftMargin = left;
                    holder.llOne.setLayoutParams(lpOne1);

                    LinearLayout.LayoutParams lpTwo1 = (LinearLayout.LayoutParams) holder.llTwo.getLayoutParams();
                    lpTwo1.leftMargin = left;
                    holder.llTwo.setLayoutParams(lpTwo1);

                    OrderBean.TravelOrderBean travelOrderBean = orderBean.getTravelInfo();

                    holder.title.setTextSize(14);

                    if (travelOrderBean != null) {
                        holder.title.setText(travelOrderBean.getProductName());//
                        holder.content2.setText(travelOrderBean.getStartDate());
                        holder.detailCount.setText(" 至 " + travelOrderBean.getEndDate());
                        // 获取订单状态
                        status = OrderUtils.getOrderStatus(orderBean.getOrderStatus());

                        holder.status.setText(status);
                    }

                    showFunctionKeyByOrderStatus(orderBean, holder);

                    holder.again.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(mContext, TravelProductDetailActivity.class);
                            intent.putExtra("id", orderBean.getTravelInfo() != null ?
                                    orderBean.getTravelInfo().getProductId() : "");
                            mContext.startActivity(intent);
                        }
                    });
                    break;
            }

            holder.cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    holder.status.setText(OrderUtils.getOrderStatus(OrderBean.ORDER_STATUS_CANCEL));
                    adapterDelCallBack(orderBean.getOrderId(), OrderBean.ORDER_STATUS_CANCEL);
                }
            });

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    share(position);
                }
            });


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Class clazz = null;
                    switch (orderBean.getOrderType()) {
                        case OrderBean.ORDER_TYPE_TRAIN:
                            clazz = TrainTicketsDetailActivity.class;
                            break;
                        case OrderBean.ORDER_TYPE_FERRY:
                            clazz = ShipTicketsDetailActivity.class;
                            break;
                        case OrderBean.ORDER_TYPE_AIRCRAFT: //机票；
                            clazz = PlaneTicketsDetailActivity.class;
                            break;
                        case OrderBean.ORDER_TYPE_CAR:
                            clazz = BusTicketsDetaildActivity.class;
                            break;
                        case OrderBean.ORDER_TYPE_TAXI:
                            clazz = TaxiTicketsDetailActivity.class;
                            break;
                        case OrderBean.ORDER_TYPE_TOUR:
                            startTourismTicketsDetailActivity(orderBean);
                            return;

                    }
                    if (clazz != null) {
                        Intent intent = new Intent(mContext, clazz);
                        intent.putExtra("orderId", orderBean.getOrderId());
                        mContext.startActivity(intent);
                    }
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    ConfirmDialog.Builder dialog = new ConfirmDialog.Builder(mContext);
                    dialog.setTitle(mContext.getString(R.string.order_dialog_title_del_order));
                    dialog.setCancelButton(mContext.getString(R.string.order_cancel_order));
                    dialog.setPositiveButton(mContext.getString(R.string.order_confirm_order), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                            adapterDelCallBack(orderBean.getOrderId(), OrderBean.ORDER_OPERATESTATUS_DELETE);
                        }
                    });
                    dialog.create().show();
                }
            });

            holder.tvPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (myHandler != null) {
                        Message message = myHandler.obtainMessage();
                        message.obj = orderBean;
                        myHandler.sendMessage(message);
                    }

                }
            });
            holder.tv_exite_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {


                }
            });

        } else if (holder1 instanceof TypePlanVH) //机票订单布局
        {
            TypePlanVH typePlainVH = (TypePlanVH) holder1;
            //大订单
            final OrderBean orderBean = mData.get(position);
            //大订单数据处理
            final TicketOrderBean ticketOrderBean = new TicketOrderBean();
            // 解析数据
            readDataTypeOfPlain(orderBean, ticketOrderBean);
            ticketOrderBean.setOrderBean(orderBean);

            ticketOrderBean.orderID = orderBean.getOrderId();
            ticketOrderBean.orderStatus = orderBean.getOrderStatus();
            ticketOrderBean.orderAmount = orderBean.getOrderAmount();
            //加载航线信息
            final PlainOrderAdapter plainOrderAdapter = new PlainOrderAdapter(ticketOrderBean, mContext);
            typePlainVH.wholeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            SpannableString ss = new SpannableString(Utils.subZeroAndDot(ticketOrderBean.orderAmount));
            typePlainVH.tvOrderAmount.setText(ss);
            typePlainVH.tvStatus.setText(OrderUtils.getOrderStatus(ticketOrderBean.orderStatus));

            //适配器item点击；
            plainOrderAdapter.setItemListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mContext, PlaneTicketsDetailActivity.class);
                    intent.putExtra("routeType", plainOrderAdapter.getRouteType());
                    intent.putExtra(PlaneTicketsDetailActivity.ORDER_BEAN, orderBean.getOrderId());
                    mContext.startActivity(intent);
                }
            });
            typePlainVH.wholeRecyclerView.setAdapter(plainOrderAdapter);

            //机票item点击；
            typePlainVH.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mContext, PlaneTicketsDetailActivity.class);
                    intent.putExtra("routeType", plainOrderAdapter.getRouteType());
                    intent.putExtra(PlaneTicketsDetailActivity.ORDER_BEAN, orderBean.getOrderId());
                    mContext.startActivity(intent);
                }
            });

            //未支付
            typePlainVH.tvPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    OrderBean temp = orderBean;
                    if (myHandler != null) {
                        Message message = myHandler.obtainMessage();
                        message.obj = temp;
                        myHandler.sendMessage(message);
                    }
                }
            });
            //改签；
            typePlainVH.tvGaiQian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mContext, ModifyOrderActivity.class);
                    intent.putExtra("orderBean", orderBean);
                    intent.putExtra("routeType", plainOrderAdapter.getRouteType());
                    mContext.startActivity(intent);
                }
            });

            // 退票
            typePlainVH.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mContext, RefundTicketActivity.class);
                    intent.putExtra("orderId", orderBean.getOrderId());
                    intent.putExtra("routeType", plainOrderAdapter.getRouteType());
                    intent.putExtra("payMethod", orderBean.getPayMethod());
                    mContext.startActivity(intent);
                }
            });
            // 删除订单；
            typePlainVH.tvShanchu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    adapterDelCallBack(orderBean.getOrderId(), OrderBean.ORDER_OPERATESTATUS_DELETE);
                }
            });
            // 取消订单；
            typePlainVH.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    adapterDelCallBack(orderBean.getOrderId(), OrderBean.ORDER_STATUS_CANCEL);
                }
            });

            if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_NO_PAY) //未支付
            {
                typePlainVH.tvDelete.setVisibility(View.GONE);
                typePlainVH.tvGaiQian.setVisibility(View.GONE);
                //显示 删除、支付、取消。
                typePlainVH.tvPay.setVisibility(View.VISIBLE);
                typePlainVH.tvShanchu.setVisibility(View.GONE);
                typePlainVH.tvCancel.setVisibility(View.VISIBLE);

            } else if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_COMPLETE) //已支付
            {
                typePlainVH.tvDelete.setVisibility(View.GONE);
                typePlainVH.tvGaiQian.setVisibility(View.GONE);
                typePlainVH.tvPay.setVisibility(View.GONE);
                typePlainVH.tvShanchu.setVisibility(View.GONE);
                typePlainVH.tvCancel.setVisibility(View.GONE);

            } else if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_CANCEL ||
                    orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_YITUIKUAN) //已取消或已退款
            {
                typePlainVH.tvDelete.setVisibility(View.GONE);
                typePlainVH.tvGaiQian.setVisibility(View.GONE);
                typePlainVH.tvPay.setVisibility(View.GONE);
                typePlainVH.tvShanchu.setVisibility(View.VISIBLE);
                typePlainVH.tvCancel.setVisibility(View.GONE);
            } else if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_TRADE_SUCESS) //交易成功
            {
                typePlainVH.tvDelete.setVisibility(View.VISIBLE);
                typePlainVH.tvGaiQian.setVisibility(View.VISIBLE);
                typePlainVH.tvPay.setVisibility(View.GONE);
                typePlainVH.tvShanchu.setVisibility(View.VISIBLE);
                typePlainVH.tvCancel.setVisibility(View.GONE);
            }else if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_TRADE_FAIL) { //购票失败
                typePlainVH.tvDelete.setVisibility(View.GONE);
                typePlainVH.tvGaiQian.setVisibility(View.GONE);
                typePlainVH.tvPay.setVisibility(View.GONE);
                typePlainVH.tvShanchu.setVisibility(View.VISIBLE);
                typePlainVH.tvCancel.setVisibility(View.GONE);
            }else {

                typePlainVH.tvDelete.setVisibility(View.GONE);
                typePlainVH.tvGaiQian.setVisibility(View.GONE);
                typePlainVH.tvPay.setVisibility(View.GONE);
                typePlainVH.tvShanchu.setVisibility(View.GONE);
                typePlainVH.tvCancel.setVisibility(View.GONE);
            }
        } else if (holder1 instanceof RedenvelopeHolder) {

            RedenvelopeHolder holder = (RedenvelopeHolder) holder1;
            final OrderBean orderBean = mData.get(position);
            final OrderBean.ReenvelopeInfo reenvelopeInfo = orderBean.getBonusInfo();

            long orderTime = Utils.toTimestamp(orderBean.getOrderTime(), 1);

            holder.tvDate.setText(Utils.toData(orderTime, 4));
            if (reenvelopeInfo == null) {
                holder.tvRecvPercent.setText("0领/0总");
            } else {
                holder.tvRecvPercent.setText(reenvelopeInfo.getReceiveQuantity() + "领/" + reenvelopeInfo.getTotalQuantity() + "总");

                String themeName = reenvelopeInfo.getThemeName();

                if (themeName != null) {
                    holder.tvCardType.setText(themeName);
                }
            }

            //金额；
            SpannableString ss = new SpannableString(Utils.subZeroAndDot(orderBean.getOrderAmount()));
            holder.tvOrderAmount.setText(ss);

            holder.tvStatus.setText(OrderUtils.getOrderStatus(orderBean.getOrderStatus()));

            //删除；
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    adapterDelCallBack(orderBean.getOrderId(), OrderBean.ORDER_OPERATESTATUS_DELETE);
                }
            });
            //再次预定；
            holder.tvReBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    Intent intent = new Intent(mContext, BounsCreateActivity.class);
                    mContext.startActivity(intent);
                }
            });
            //分享；
            holder.tvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    share(position);
                }
            });
            //取消；
            holder.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    adapterDelCallBack(orderBean.getOrderId(), OrderBean.ORDER_STATUS_CANCEL);
                }
            });
            //支付；
            holder.tvPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (myHandler != null) {
                        Message message = myHandler.obtainMessage();
                        message.obj = orderBean;
                        myHandler.sendMessage(message);
                    }
                }
            });
            // item点击；
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //已支付；
                    if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_COMPLETE) {
                        Intent intent = new Intent(mContext, RedBagDetailSendActivity.class);
                        intent.putExtra("orderId", orderBean.getOrderId());
                        intent.putExtra("theme", reenvelopeInfo.getThemeName());
                        mContext.startActivity(intent);
                    }
                }
            });
            holder.tvShare.setVisibility(View.GONE);

            if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_NO_PAY) //未支付
            {
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.VISIBLE);
                holder.tvReBuy.setVisibility(View.GONE);
            } else if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_COMPLETE) //已支付
            {
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvReBuy.setVisibility(View.VISIBLE);
            } else if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_CANCEL) //已取消 ,已退票,购票失败
            {
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvReBuy.setVisibility(View.VISIBLE);

            } else if (orderBean.getOrderStatus() == OrderBean.ORDER_STATUS_YIWANCHENG) //交易成功
            {
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvReBuy.setVisibility(View.VISIBLE);

            }

        } else if (holder1 instanceof HotelOrderHolder) {//解析酒店订单数据

            HotelOrderHolder hotelOrderHolder = (HotelOrderHolder) holder1;

            final OrderBean orderBean = mData.get(position);

            hotelOrderHolder.initData(orderBean);
        }
    }

    /**
     * 公共功能句柄
     */
    private Handler functionHandler = new Handler() {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            int what = msg.what;

            OrderBean tempBean = (OrderBean) msg.obj;

            if (what == 0) {//删除

                adapterDelCallBack(tempBean.getOrderId(), OrderBean.ORDER_OPERATESTATUS_DELETE);

            } else if (what == 1) {//取消

                adapterDelCallBack(tempBean.getOrderId(), OrderBean.ORDER_STATUS_CANCEL);

            } else if (what == 2) {//申请发现

                ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);

                Map<String, Object> map = new HashMap<>();

                map.put("orderId", tempBean.getOrderId());

                hotelTicketsDetailPresenter.sendsRequestOne(CardConstant.ORDER_APPLYCASHCACK, map);

            } else if (what == 100) {//酒店订单 取消订单 退款 取消退款

                currentOrderBean = tempBean;

                //检测退款信息
                ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);

                Map<String, Object> mapRefundMap = new HashMap<>();

                mapRefundMap.put("orderId", tempBean.getOrderId());

                hotelTicketsDetailPresenter.refundMoneyCheck(CardConstant.ORDER_REFUNDORDERCHECK, mapRefundMap);

            } else if (what == 600) {//酒店支付

                Message message = myHandler.obtainMessage();
                message.obj = tempBean;
                myHandler.sendMessage(message);

            }
        }
    };


    protected void readyGoWithBundle(Activity context, Class claz, Bundle bundle)
    {
        Intent intent = new Intent(context, claz);
        if (bundle != null) {
            intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 根据订单状态显示功能按键
     *
     * @param orderBean
     * @param holder
     */
    private void showFunctionKeyByOrderStatus(OrderBean orderBean, OrderManageHolder holder)
    {

        switch (orderBean.getOrderStatus()) {
            case OrderBean.ORDER_STATUS_CANCEL: //已取消
                holder.tvPay.setVisibility(View.GONE);
                holder.cancle.setVisibility(View.GONE);
                holder.comment.setVisibility(View.GONE);
                holder.again.setVisibility(View.VISIBLE);
                holder.tv_exite_money.setVisibility(View.GONE);
                break;
            case OrderBean.ORDER_STATUS_NO_PAY: //未支付
                holder.tvPay.setVisibility(View.VISIBLE);
                holder.cancle.setVisibility(View.VISIBLE);
                holder.comment.setVisibility(View.GONE);
                holder.again.setVisibility(View.GONE);
                holder.tv_exite_money.setVisibility(View.GONE);
                break;
            case OrderBean.ORDER_STATUS_COMPLETE: //已支付
                holder.comment.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);
                holder.cancle.setVisibility(View.GONE);
                holder.again.setVisibility(View.VISIBLE);
                holder.tv_exite_money.setVisibility(View.GONE);
                break;
            case OrderBean.ORDER_STATUS_YIWANCHENG: //已完成
                holder.comment.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);
                holder.cancle.setVisibility(View.GONE);
                holder.again.setVisibility(View.VISIBLE);
                holder.tv_exite_money.setVisibility(View.GONE);
                break;
        }
    }

    public void startTourismTicketsDetailActivity(OrderBean orderBean)
    {
        Intent intent = new Intent(mContext, TourismTicketsDetailActivity.class);
        intent.putExtra("orderBean", orderBean);
        mContext.startActivity(intent);
    }


    private void readDataTypeOfPlain(OrderBean orderBean, TicketOrderBean ticketOrderBean)
    {

        if (orderBean.getProduct() != null && orderBean.getProduct().length() > 0) {

            if (orderBean.getProduct().indexOf(SPLIT) != 1) {
                ticketOrderBean.product = orderBean.getProduct().split(SPLIT);
            } else {
                ticketOrderBean.product[0] = orderBean.getProduct();
            }

        }
        // 订单no
        if (orderBean.getOrderNo() != null && orderBean.getOrderNo().length() > 0) {


            if (orderBean.getOrderNo().indexOf(SPLIT) != 1) {
                ticketOrderBean.orderNo = orderBean.getOrderNo().split(SPLIT);
            } else {
                ticketOrderBean.orderNo[0] = orderBean.getOrderNo();
            }

        }
        // 是否剁成 往返
        if (orderBean.getRouteType() != null && orderBean.getRouteType().length() > 0) {

            if (orderBean.getRouteType().indexOf(SPLIT) != -1) {

                ticketOrderBean.routeType = orderBean.getRouteType().split(SPLIT);

            } else {
                ticketOrderBean.routeType[0] = orderBean.getRouteType();
            }
        }
        // 起始城市
        if (orderBean.getStartCityNames() != null && orderBean.getStartCityNames().length() > 0) {

            if (orderBean.getStartCityNames().indexOf(SPLIT) != -1) {
                ticketOrderBean.startCityName = orderBean.getStartCityNames().split(SPLIT);
            } else {
                ticketOrderBean.startCityName[0] = orderBean.getStartCityNames();
            }
        }
        // 终点城市
        if (orderBean.getEndCityNames() != null && orderBean.getEndCityNames().length() > 0) {

            if (orderBean.getEndCityNames().indexOf(SPLIT) != -1) {
                ticketOrderBean.endCityName = orderBean.getEndCityNames().split(SPLIT);
            } else {
                ticketOrderBean.endCityName[0] = orderBean.getEndCityNames();
            }
        }
        // 起始时间
        if (orderBean.getStartTimes() != null && orderBean.getStartTimes().length() > 0) {


            if (orderBean.getStartTimes().indexOf(SPLIT) != -1) {
                ticketOrderBean.startTime = orderBean.getStartTimes().split(SPLIT);
            } else {
                ticketOrderBean.startTime[0] = orderBean.getStartTimes();
            }
        }
        // 抵达时间
        if (orderBean.getEndTimeses() != null && orderBean.getEndTimeses().length() > 0) {

            if (orderBean.getEndTimeses().indexOf(SPLIT) != -1) {
                ticketOrderBean.endTime = orderBean.getEndTimeses().split(SPLIT);
            } else {
                ticketOrderBean.endTime[0] = orderBean.getEndTimeses();
            }
        }
        // 起飞日
        if (orderBean.getTimeSereses() != null && orderBean.getTimeSereses().length() > 0) {

            if (orderBean.getTimeSereses().indexOf(SPLIT) != -1) {
                ticketOrderBean.timeSereses = orderBean.getTimeSereses().split(SPLIT);
            } else {
                ticketOrderBean.timeSereses[0] = orderBean.getTimeSereses();
            }
        }
        // 票价；
        if (orderBean.getFareInforses() != null && orderBean.getFareInforses().length() > 0) {

            if (orderBean.getFareInforses().indexOf(SPLIT) != -1) {
                ticketOrderBean.fareInfors = orderBean.getFareInforses().split(SPLIT);
            } else {
                ticketOrderBean.fareInfors[0] = orderBean.getFareInforses();
            }
        }

        // 倉等
        if (orderBean.getBasecabinCodes() != null && orderBean.getBasecabinCodes().length() > 0) {

            if (orderBean.getBasecabinCodes().indexOf(SPLIT) != -1) {
                ticketOrderBean.basecabinCodes = orderBean.getBasecabinCodes().split(SPLIT);
            } else {
                ticketOrderBean.basecabinCodes[0] = orderBean.getBasecabinCodes();
            }
        }
        // 代理商logo
        if (orderBean.getCarrierLogos() != null && orderBean.getCarrierLogos().length() > 0) {
            if (orderBean.getCarrierLogos().indexOf(SPLIT) != -1) {
                ticketOrderBean.carrierLogos = orderBean.getCarrierLogos().split(SPLIT);
            } else {
                ticketOrderBean.carrierLogos[0] = orderBean.getCarrierLogos();
            }
        }

        // 代理商名稱
        if (orderBean.getCarrierNames() != null && orderBean.getCarrierNames().length() > 0) {

            if (orderBean.getCarrierNames().indexOf(SPLIT) != -1) {
                ticketOrderBean.carrierNames = orderBean.getCarrierNames().split(SPLIT);
            } else {
                ticketOrderBean.carrierNames[0] = orderBean.getCarrierNames();
            }
        }

        // 航班号
        String flightNumbers = orderBean.getFlightnumbers();
        if (!TextUtils.isEmpty(flightNumbers) && flightNumbers.length() > 0) {
            if (flightNumbers.indexOf(SPLIT) != -1) {
                ticketOrderBean.flightnumbers = flightNumbers.split(SPLIT);
            } else {
                ticketOrderBean.flightnumbers[0] = flightNumbers;
            }
        }
        // 状态
        if (orderBean.getSturts() != null && orderBean.getSturts().length() > 0) {

            if (orderBean.getSturts().indexOf(SPLIT) != -1) {
                ticketOrderBean.sturts = orderBean.getSturts().split(SPLIT);
            } else {
                ticketOrderBean.sturts[0] = orderBean.getSturts();
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return mData == null || mData.size() == 0 ? 1 : mData.size();
    }

    public void clearData()
    {

        if (mData != null) {

            mData.clear();

            notifyDataSetChanged();
        }
    }


    @Override
    public void onPaySucess()
    {
        //通知页面更新
        uiHandler.sendEmptyMessage(0);
    }

    @Override
    public void onPayFail(String failMsg)
    {

        ToastUtil.i(mContext, failMsg);
    }

    /**
     * 支付
     */
    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg)
        {
            if (orderListsPresenter == null) {

                ToastUtil.i(mContext, "orderListsPresenter为null");

                return false;

            }
            OrderBean bean = (OrderBean) msg.obj;
            switch (bean.getOrderType()) {
                case OrderBean.ORDER_TYPE_LIPING: //礼品卡；
                case OrderBean.ORDER_TYPE_REDPACGE: //红包；
                    exePay(bean);
                    break;
                default:

                    if (bean.getFlightType() != null) {
                        UserManager.getInstance().setFlightType(bean.getFlightType());
                    }
                    //新的特惠付款
                    //查询用户的代金券信息
                    //发放平台
                    int issuePlatform = 0;
                    //行业id
                    int industryId = Integer.parseInt(orderListsPresenter.getIndustryIds(bean));

                    GoldTicketParam goldTicketParam = new GoldTicketParam();

                    goldTicketParam.setIssuePlatform(issuePlatform);

                    goldTicketParam.setIndustryId(industryId);

                    if (bean.getOrderType() ==  OrderBean.ORDER_TYPE_HOTELS &&bean.getHotelInfo() != null) {//酒店

                        goldTicketParam.setStoreId(bean.getHotelInfo().getStoreId());

                        goldTicketParam.setGoodsId(bean.getHotelInfo().getInnerGoodsId());

                        goldTicketParam.setShopId(bean.getHotelInfo().getHotelId());

                    }else {
                        if (!TextUtils.isEmpty(bean.getShopIds())) {

                            int index = bean.getShopIds().indexOf(",");

                            if (index == -1) {

                                long a = Long.parseLong(bean.getShopIds());

                                goldTicketParam.setShopId(a);
                            } else {

                                String[] strArray = bean.getShopIds().split(",");

                                long a = Long.parseLong(strArray[0]);

                                goldTicketParam.setShopId(a);
                            }

                        }

                        if (!TextUtils.isEmpty(orderListsPresenter.getGoodIds(bean))) {

                            int index = orderListsPresenter.getGoodIds(bean).indexOf(",");

                            if (index == -1) {

                                long a = Long.parseLong(orderListsPresenter.getGoodIds(bean));

                                goldTicketParam.setGoodsId(a);
                            } else {

                                String[] strArray = orderListsPresenter.getGoodIds(bean).split(",");

                                long a = Long.parseLong(strArray[0]);

                                goldTicketParam.setGoodsId(a);
                            }

                        }
                    }

                    PayOrderBean payOrderBean = new PayOrderBean();

                    payOrderBean.setOrderAmount(Double.parseDouble(Utils.subZeroAndDot(bean.getOrderAmount())));

                    payOrderBean.setNotifyUrl(bean.getNotifyUrl());

                    payOrderBean.setOrderId(Long.parseLong(bean.getOrderId()));

                    payOrderBean.setOrderNo(bean.getOrderNo());

                    payOrderBean.setOrderTime(bean.getOrderTime());

                    Intent intent = new Intent(mContext, AppPreferentialPaymentActivity.class);

                    intent.putExtra("orderData", payOrderBean);

                    intent.putExtra("goldTicketParam", goldTicketParam);

                    intent.putExtra("goodName", bean.getGoodsName());

                    mContext.startActivityForResult(intent, 1000);

                    break;
            }
            return false;
        }
    });

    /**
     * 支付；
     */
    private void exePay(OrderBean outBean)
    {
        Map<String, String> params = new HashMap<>();

        String goodsName = null;
        switch (outBean.getOrderType()) {
            case OrderBean.ORDER_TYPE_LIPING: //礼品卡；
                if (outBean.getGiftcardInfo() != null) {
                    //1、实体卡 2、心意卡
                    int cardType = outBean.getGiftcardInfo().getCategory();
                    goodsName = 1 == cardType ? "实体卡" : "心意卡";
                    if (cardType == 2) {
                        params.put("showGiftcard", "1");
                        params.put("showBouns", "1");
                    }
                }
                break;
            case OrderBean.ORDER_TYPE_REDPACGE: //红包；

                goodsName = "红包";
                params.put("showBouns", "1");
                break;
        }
        params.put("mobile", UserManager.getInstance().getUserBean().getAmountAccount());
        params.put("orderNo", outBean.getOrderNo());
        params.put("orderTime", DateUtil.formatOrderTime(outBean.getOrderTime()));
        params.put("amount", String.format("%.2f", Float.parseFloat(outBean.getOrderAmount()))); //订单金额；
        params.put("leftTime", AppConstant.leftTime); //超时时间
        params.put("goodsName", goodsName); //商品名称
        params.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP
        params.put("notifyUrl", outBean.getNotifyUrl());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);//商户号
        params.put("sign", AppConstant.sign);//签名

        discountPayPresenter.loadData(params);
    }


    /**
     * adapter删除或取消订单回调；
     *
     * @param orderId
     * @param opType
     */
    public void adapterDelCallBack(final String orderId, final int opType)
    {
        ProgressDialogUtil.getInstance().showProgressDialogMsg(mContext.getString(R.string.loading), mContext, true);
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("operateStatus", opType + "");
        orderStatusPresenter.loadData(params);
    }

    private OrderBean orderBean; //选中的订单信息；

    public void share(int position)
    {
        ProgressDialogUtil.getInstance().showProgressDialogMsg(mContext.getString(R.string.loading), mContext, true);
        orderBean = mData.get(position);

        Map<String, Object> args = new HashMap<>();

        //6旅游；7酒店;8礼品卡；9红包
        switch (orderBean.getOrderType()) {
            case OrderBean.ORDER_TYPE_TOUR:
                OrderBean.TravelOrderBean bean = orderBean.getTravelInfo();
                args.put("codeType", AppConstant.command_type_shop);
                args.put("code", bean != null ? bean.getProductId() : ""); //编码
                args.put("industryId", AppConstant.travel_id);
                Map<String, Object> param1 = new HashMap<>();
                param1.put("productId", bean != null ? bean.getProductId() : "");
                args.put("activityData", JSON.toJSONString(param1));
                break;
            case OrderBean.ORDER_TYPE_HOTELS:
                args.put("codeType", AppConstant.command_type_shop);
                args.put("code", orderBean.getHotelId()); //编码
                args.put("industryId", AppConstant.hotel_id);
                Map<String, Object> param2 = new HashMap<>();
                param2.put("hotelId", orderBean.getHotelId() + "");
                args.put("activityData", JSON.toJSONString(param2));
                break;
            case OrderBean.ORDER_TYPE_LIPING:
                args.put("codeType", AppConstant.command_type_giftcard);
                args.put("code", orderBean.getGiftcardInfo() == null ? "" : orderBean.getOrderId()); //编码；
                break;
            case OrderBean.ORDER_TYPE_REDPACGE:
                args.put("codeType", AppConstant.command_type_bouns);
                args.put("code", orderBean.getOrderNo()); //编码
                Map<String, Object> param3 = new HashMap<>();
                param3.put("orderId", orderBean.getOrderId());
                args.put("activityData", JSON.toJSONString(param3));
                break;
        }
        commandPresenter.loadData(args);
    }

    @Override
    public void onGetCommandSuc(String commandAndUrl)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (orderBean != null) {

            FragmentManager fragmentManager = null;

            if (mContext instanceof AppCompatActivity) {

                AppCompatActivity activity = (AppCompatActivity) mContext;

                fragmentManager = activity.getSupportFragmentManager();

            } else if (mContext instanceof FragmentActivity) {

                FragmentActivity activity = (FragmentActivity) mContext;

                fragmentManager = activity.getSupportFragmentManager();
            }

            if (fragmentManager == null) {

                return;
            }

            //6旅游；7酒店;8礼品卡；9红包
            switch (orderBean.getOrderType()) {
                case OrderBean.ORDER_TYPE_TOUR: //旅游


                    ShareDialogFragment.getInstance("", "")
                            .setTitle(!TextUtils.isEmpty(orderBean.getCarrierNames()) ? orderBean.getCarrierNames() : "旅游")
                            .setUrl(CommonUtil.getGiftcardShareUrl(commandAndUrl))
                            .setContent(CommonUtil.getShopShareContent(orderBean.getTravelInfo() != null
                                    ? orderBean.getTravelInfo().getProductName() : "旅游", commandAndUrl))
                            .show(fragmentManager, "");
                    break;
                case OrderBean.ORDER_TYPE_HOTELS://酒店
                    String image = null;
                    if (!Utils.isEmpty(orderBean.getCarrierLogos())) {
                        String[] array = orderBean.getCarrierLogos().split(",");
                        image = ServiceDispatcher.getImageUrl(array[0]);
                    }

                    LogUtil.i("name=" + orderBean.getHotelName() + ",image=" + image + ",commandAndUrl=" + commandAndUrl);

                    ShareDialogFragment.getInstance("", "")
                            .setTitle(orderBean.getHotelName())
                            .setImage(image)
                            .setUrl(CommonUtil.getGiftcardShareUrl(commandAndUrl))
                            .setContent(CommonUtil.getShopShareContent(orderBean.getHotelName(), commandAndUrl))
                            .show(fragmentManager, "");


                    break;
                case OrderBean.ORDER_TYPE_LIPING://礼品卡

//                    SendGiftCardDialog.getInstance()
//                            .setHandler(dataHandler).setFragmentManager(fragmentManager).setCommandAndUrl(
//                            CommonUtil.getGiftcardShareContent("礼品卡", commandAndUrl)).show(fragmentManager, "");

                    SendGiftCardDialog dialog = SendGiftCardDialog.getInstance();
                    dialog.setHandler(dataHandlerHa);
                    dialog.setFragmentManager(fragmentManager);
                    dialog.setCommandAndUrl(
                            CommonUtil.getGiftcardShareContent("心意卡", commandAndUrl));
                    dialog.show(fragmentManager, "");

                    break;
                case OrderBean.ORDER_TYPE_REDPACGE://红包；
                    SendGiftCardDialog.getInstance()
                            .setHandler(dataHandler).setFragmentManager(fragmentManager).setCommandAndUrl(
                            CommonUtil.getGiftcardShareContent("红包", commandAndUrl)).show(fragmentManager, "");
                    break;
            }
        }
    }

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what) {
                case SendGiftCardDialog.WHAT_SHARE_PLATFORM: //分享-嗨生活；

                    //6旅游；7酒店;8礼品卡；9红包
                    String recordIds = null;
                    int pageType = -1;
                    int totalNum = 1;
                    if (orderBean.getOrderType() == 8) {
                        recordIds = orderBean.getGiftcardInfo() == null ? "" : orderBean.getOrderId();
                        pageType = GiveCardActivity.TYPE_GIFTCARD;
                        OrderBean.GiftcardInfo giftcardInfo = orderBean.getGiftcardInfo();
                        totalNum = giftcardInfo.getTotalQuantity() - giftcardInfo.getReceiveQuantity();
                    } else if (orderBean.getOrderType() == 9) {
                        //红包取orderId；
                        recordIds = orderBean.getOrderId();
                        pageType = GiveCardActivity.TYPE_BOUNS;
                        OrderBean.ReenvelopeInfo reenvelopeInfo = orderBean.getBonusInfo();
                        totalNum = reenvelopeInfo.getTotalQuantity() - Integer.parseInt(reenvelopeInfo.getReceiveQuantity());

                    }
                    Intent intent = new Intent(mContext, GiveCardActivity.class);
                    intent.putExtra("recordIds", recordIds);
                    intent.putExtra("pageType", pageType);
                    intent.putExtra("totalNum", totalNum);
                    mContext.startActivity(intent);
                    break;
            }
            return false;
        }
    });

    @Override
    public void onGetCommandFail(String failMsg)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
        ToastUtil.i(mContext, failMsg);
    }

    private Handler dataHandlerHa = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg)
        {

            switch (msg.what) {
                case SendGiftCardDialog.WHAT_SHARE_PLATFORM: //分享-嗨生活；

                    OrderBean.GiftcardInfo giftcardInfo = orderBean.getGiftcardInfo();
                    int totalNum = giftcardInfo.getTotalQuantity() - giftcardInfo.getReceiveQuantity();

                    Intent intent = new Intent(mContext, GiveCardActivity.class);

                    intent.putExtra("recordIds", orderBean.getOrderId());

                    intent.putExtra("pageType", GiveCardActivity.TYPE_GIFTCARD);
                    intent.putExtra("totalNum", totalNum);
                    mContext.startActivity(intent);

                    break;
            }
            return false;
        }
    });


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (type == 1) {//申请退款成功

            dialogMsg("申请成功", "申请返现成功，可在快速入住问题-如何返现中查看发现进度");

        } else if (type == 2) {

            orderHandlerFunction(o + "", OrderMessagFuctioneDialog.FUCTION_ORDER_REFUND_CODE);

        }else{//
            uiHandler.sendEmptyMessage(0);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (type == 1) {//申请退款失败

            dialogMsg("申请失败", "申请返现失败，请自行核查相关信息，如有不解，请联系客服");

        } else if (type == 2) {

            ToastUtil.i(mContext, "操作失败");
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

        hotelProductFragmentDialog.show(mContext.getFragmentManager(), "roomFt");
    }

    /**
     * 订单操作功能提示
     *
     * @param cancelDateStr
     */
    private void orderHandlerFunction(String cancelDateStr, int code)
    {

        OrderMessagFuctioneDialog hotelOrderDelMessageDialog = new OrderMessagFuctioneDialog();

        hotelOrderDelMessageDialog.setDialogHandler(dialogHandler);

        hotelOrderDelMessageDialog.setCode(code);

        Bundle bundleDel = new Bundle();

        bundleDel.putString("dialogMsg", cancelDateStr);

        hotelOrderDelMessageDialog.setArguments(bundleDel);

        hotelOrderDelMessageDialog.show(mContext.getFragmentManager(), "roomFt");
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

                String orderId = currentOrderBean.getOrderId();

                Map<String, Object> mapRefundMap = new HashMap<>();

                mapRefundMap.put("orderId", orderId);

                mapRefundMap.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));

                //     String typeCode = currentOrderBean.getOrderType() + "";

                //    if (HoteUtil.HOTEL_ROOM_CODE.equals(typeCode)) {//房间碎片

                mapRefundMap.put("goodsName", currentOrderBean.getHotelInfo().getGoodsName());

//                } else if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(typeCode) || HoteUtil.HOTEL_BAR_CODE.equals(typeCode)
//                        || HoteUtil.HOTEL_SPA_CODE.equals(typeCode) || HoteUtil.HOTEL_LOBBY_BAR_CODE.equals(typeCode)
//                        || HoteUtil.HOTEL_GYM_CODE.equals(typeCode) || HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(typeCode)) {
//                    mapRefundMap.put("goodsName", goodsInfoBean.getGoodsName());
//
//
//                } else if (HoteUtil.HOTEL_CARD_EQUITY_CODE.equals(typeCode)) {
//
//                    mapRefundMap.put("goodsName", goodsInfoBean.getGiftsName());
//
//
//                }

                mapRefundMap.put("storeId", currentOrderBean.getHotelInfo().getHotelId());

                hotelTicketsDetailPresenter.confirmRefundMoney(CardConstant.ORDER_REFUNDORDERCONFIRM, mapRefundMap);

            }
        }
    };
}