package com.yzb.card.king.ui.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.travel.TourTicketDetailContactsBean;
import com.yzb.card.king.bean.travel.TourTicketDetailProductsInfoBean;
import com.yzb.card.king.bean.travel.TravelOrderDetailBean;
import com.yzb.card.king.bean.travel.TravelOrderDetailguestListBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.activity.pay.DiscountPayActivity;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.my.presenter.CommandPresenter;
import com.yzb.card.king.ui.my.view.CommandView;
import com.yzb.card.king.ui.order.adapter.OrderTravelDetailShowIDAdapter;
import com.yzb.card.king.ui.order.presenter.HotelTicketsDetailPresenter;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.other.activity.PjActivity;
import com.yzb.card.king.ui.travel.activity.TravelProductDetailActivity;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 旅遊訂單詳情
 */
public class TourismTicketsDetailActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface, CommandView
{
    private TravelOrderDetailBean travelOrderDetailBean;
    private TourTicketDetailContactsBean tourTicketDetailContactsBean;
    private TourTicketDetailProductsInfoBean tourTicketDetailProductsInfoBean;
    private ImageView showIDImg;
    private LinearLayout shareLayout;
    private TextView orderStatusTv, orderAmountTv, orderNoTv, bookAgainTV, pinfangYNTV;
    private TextView travelName, travelName1, travelName2, travelName3, travelRouteTV, dataUseTV, pinFangTV;
    private TextView buyCopiesTV, travelersNameTV, contactsNameTV, telphoneNumberTV;
    private RelativeLayout EvaluationLayout, bookAgainLayout, showIDlayout;
    private HotelTicketsDetailPresenter hotelTicketsDetailPresenter;
    private OrderBean orderBean;
    private ListView showIDlistView;
    private String orderId;
    private int status;
    private boolean isShowID = false;
    private List<TravelOrderDetailguestListBean> guestList = new ArrayList<>();

    private TextView tvProductName;
    private View vOneLine;
    private LinearLayout llOne;
    private LinearLayout evaluation_layout;
    private CommandPresenter commandPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism_tickets_detail);
        orderBean = (OrderBean) getIntent().getSerializableExtra("orderBean");

        commandPresenter = new CommandPresenter(this);
        initView();
        initData();

    }

    private void initData()
    {

        ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);
        hotelTicketsDetailPresenter = new HotelTicketsDetailPresenter(this);
        travelOrderDetailBean = new TravelOrderDetailBean();
        tourTicketDetailContactsBean = new TourTicketDetailContactsBean();
        tourTicketDetailProductsInfoBean = new TourTicketDetailProductsInfoBean();

        orderId = orderBean.getOrderId();
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        hotelTicketsDetailPresenter.sendsRequest(CardConstant.ORDER_TOURISM_DETAIL, map);
    }

    public void setData(TravelOrderDetailBean travelOrderDetailBean)
    {
        if (travelOrderDetailBean == null)
        {
            return;
        }

        status = Integer.parseInt(travelOrderDetailBean.getOrderStatus());

        setOrderDate(travelOrderDetailBean);

        //订单状态
        switch (status)
        {
            case OrderBean.ORDER_STATUS_NO_PAY: // 待支付
                orderStatusTv.setText(getString(R.string.no_pay));
                bookAgainTV.setText(getString(R.string.go_pay));
                break;
            case OrderBean.ORDER_STATUS_COMPLETE: // 已支付
                orderStatusTv.setText(getString(R.string.arleday_pay));
                break;
            case OrderBean.ORDER_STATUS_CANCEL: // 已取消
                orderStatusTv.setText(getString(R.string.arleday_cancle));
                break;
            case OrderBean.ORDER_STATUS_YIWANCHENG: // 已完成
                orderStatusTv.setText(getString(R.string.arleday_complete));
                break;
            case OrderBean.ORDER_STATUS_YITUIKUAN:// 已退款
                orderStatusTv.setText(getString(R.string.arleday_tuikuan));
                break;
        }

        setTravelDate(travelOrderDetailBean);

    }

    private void setTravelDate(TravelOrderDetailBean travelOrderDetailBean)
    {
        if (null != travelOrderDetailBean)
        {

            travelName.setText(tourTicketDetailProductsInfoBean.getProductName() + " ");
            travelRouteTV.setText(tourTicketDetailProductsInfoBean.getLineName() + " ");
            String startTime = tourTicketDetailProductsInfoBean.getDepDate();
            dataUseTV.setText(startTime);
            buyCopiesTV.setText(tourTicketDetailProductsInfoBean.getProductNum() + " 份");
            if (tourTicketDetailProductsInfoBean.getFlatshareStatus().equals("1"))
            {
                pinfangYNTV.setText(getString(R.string.order_travel_detail_pinfang_yes));
            } else
            {
                pinfangYNTV.setText(getString(R.string.order_travel_detail_pinfang_no));
            }

            pinFangTV.setText(tourTicketDetailProductsInfoBean.getRoomCount() + "间房");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < guestList.size(); i++)
            {
                stringBuilder.append(guestList.get(i).getGuestName() + ",");
            }

            String names = stringBuilder.toString();
            names = names.substring(0, names.length() - 1);
            travelersNameTV.setText(names);
            contactsNameTV.setText(tourTicketDetailContactsBean.getContactName() + " ");
            telphoneNumberTV.setText(tourTicketDetailContactsBean.getContactMobile() + " ");

        }

    }

    private void setOrderDate(TravelOrderDetailBean travelOrderDetailBean)
    {
        SpannableString ss = new SpannableString("¥" + Utils.subZeroAndDot(travelOrderDetailBean.getOrderAmount() + ""));
        // ss.setSpan(new AbsoluteSizeSpan(CommonUtil.sp2px(this, 8)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        orderAmountTv.setText(ss);
        if (travelOrderDetailBean.getOrderNo() == null)
        {
            orderNoTv.setText(" ");
        } else
        {
            orderNoTv.setText(travelOrderDetailBean.getOrderNo());
        }

    }

    public void initView()
    {
        vOneLine = findViewById(R.id.vOneLine);
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        llOne = (LinearLayout) findViewById(R.id.llOne);
        evaluation_layout = (LinearLayout) findViewById(R.id.evaluation_layout);

        tvProductName.setVisibility(View.GONE);
        vOneLine.setVisibility(View.GONE);
        llOne.setVisibility(View.GONE);
        evaluation_layout.setBackgroundResource(R.drawable.discount_mindcard_homepage_shape_white_top);

        pinfangYNTV = (TextView) findViewById(R.id.yesorno_pinfang);
        shareLayout = (LinearLayout) findViewById(R.id.share_layout);
        orderStatusTv = (TextView) findViewById(R.id.tv_status);
        orderAmountTv = (TextView) findViewById(R.id.tvOrderAmount);
        orderNoTv = (TextView) findViewById(R.id.order_no);
        EvaluationLayout = (RelativeLayout) findViewById(R.id.assessRL);
        bookAgainLayout = (RelativeLayout) findViewById(R.id.bookagainRl);
        bookAgainTV = (TextView) findViewById(R.id.tv_again_booking);
        travelName = (TextView) findViewById(R.id.travel_name);
        travelName1 = (TextView) findViewById(R.id.travel_content1);
        travelName2 = (TextView) findViewById(R.id.travel_content2);
        travelName3 = (TextView) findViewById(R.id.travel_content3);
        travelRouteTV = (TextView) findViewById(R.id.travel_route);
        dataUseTV = (TextView) findViewById(R.id.date_use);
        buyCopiesTV = (TextView) findViewById(R.id.buy_coyies);
        pinFangTV = (TextView) findViewById(R.id.pinfangtv);
        travelersNameTV = (TextView) findViewById(R.id.traveler_name);
        contactsNameTV = (TextView) findViewById(R.id.contactsname_tv);
        telphoneNumberTV = (TextView) findViewById(R.id.telphoneNumber);
        showIDlistView = (ListView) findViewById(R.id.showidlistview);
        showIDlayout = (RelativeLayout) findViewById(R.id.show_id_layout);
        showIDImg = (ImageView) findViewById(R.id.showidiv);
        initListen();

    }

    private void initListen()
    {
        shareLayout.setOnClickListener(this);
        EvaluationLayout.setOnClickListener(this);
        bookAgainLayout.setOnClickListener(this);
        showIDlayout.setOnClickListener(this);
        showIDImg.setOnClickListener(this);
    }

    public void goBack(View view)
    {
        finish();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.share_layout: // 分享
                shareTravel();
                break;
            case R.id.assessRL:// 点评
                assess();
                break;
            case R.id.bookagainRl:// 再次预定 或 去支付
                if (status == OrderBean.ORDER_STATUS_NO_PAY)
                {
                    payFor();
                } else
                {
                    bookAgain();
                }
                break;

            case R.id.show_id_layout: // 显示证件号
//                showID();
                break;
            case R.id.showidiv://
                showID();
                break;
        }

    }

    private void showID()
    {
        if (isShowID)
        {
            isShowID = false;
            showIDImg.setImageResource(R.mipmap.icon_arrow_bottom_gray);
            travelersNameTV.setVisibility(View.VISIBLE);
            showIDlistView.setVisibility(View.GONE);
        } else
        {
            isShowID = true;
            showIDImg.setImageResource(R.mipmap.icon_arrow_top_gray);
            travelersNameTV.setVisibility(View.GONE);
            showIDlistView.setVisibility(View.VISIBLE);
        }

    }


    private void shareTravel()
    {
        if (travelOrderDetailBean == null)
        {
            LogUtil.i("mHotelDetailBean为空");
            return;
        }
        showPDialog(R.string.loading);
        Map<String, Object> args = new HashMap<>();
        OrderBean.TravelOrderBean bean = orderBean.getTravelInfo();
        args.put("codeType", AppConstant.command_type_shop);
        args.put("code", bean != null ? bean.getProductId() : ""); //编码
        args.put("industryId", AppConstant.travel_id);
        Map<String, Object> param1 = new HashMap<>();
        param1.put("productId", bean != null ? bean.getProductId() : "");
        args.put("activityData", JSON.toJSONString(param1));
        commandPresenter.loadData(args);
    }

    private void assess()
    {
        if (tourTicketDetailProductsInfoBean.getProductId() != null)
        {
            Intent pjIntent = new Intent(this, PjActivity.class);
            pjIntent.putExtra("detailId", tourTicketDetailProductsInfoBean.getProductId());
            pjIntent.putExtra("type", "1");
            startActivity(pjIntent);
        }
    }

    protected void readyGoWithBundle(Activity context, Class claz, Bundle bundle)
    {
        Intent intent = new Intent(context, claz);
        if (bundle != null)
        {
            intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        }
        context.startActivity(intent);
    }

    private void payFor()
    {
        Intent intent = new Intent(this, DiscountPayActivity.class);
        HashMap<String, String> map = new HashMap();
        map.put(DiscountPayActivity.STORE_NAME, orderBean.getCarrierNames());//商家名称；会有多个；
        map.put(DiscountPayActivity.STORE_IDS, orderBean.getShopIds());//商家id；商家id(多个使用英文逗号分割)
        map.put(DiscountPayActivity.ORDER_ID, orderId);//订单id；
        map.put(DiscountPayActivity.ORDER_NO, orderBean.getOrderNo());//订单号；
        map.put(DiscountPayActivity.NOTIFY_URL, orderBean.getNotifyUrl());//回调url；

        Date date = DateUtil.string2Date(orderBean.getOrderTime(), DateUtil.DATE_FORMAT_DATE_TIME);
        map.put(DiscountPayActivity.ORDER_TIME, DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE_TIME4));//订单时间；

        map.put(DiscountPayActivity.INDUSTRY_ID, AppConstant.travel_id);//行业id；
        map.put(DiscountPayActivity.GOODS_IDS, orderBean.getGoodIds());//商品id；商品id(多个使用英文逗号分割)
        map.put(DiscountPayActivity.ORDER_AMOUNT, travelOrderDetailBean.getOrderAmount() + "");//订单金额；
        map.put(DiscountPayActivity.IS_INPUT, AppConstant.ACCOUNT_NO_INPUT);//0：不需要手输入；1：需要；

        map.put(DiscountPayActivity.GOODS_CODES, AppConstant.discount_code_travel); //商品code码；
        intent.putExtra(DiscountPayActivity.ARG_MAP, map);
        startActivity(intent);
    }

    private void bookAgain()
    {
        if (tourTicketDetailProductsInfoBean.getProductId() == null)
        {
            return;
        } else
        {

            Intent it = new Intent(this, TravelProductDetailActivity.class);
            it.putExtra("id", tourTicketDetailProductsInfoBean.getProductId() + "");
            startActivity(it);
        }
    }
    @Override
    public void onGetCommandSuc(String commandAndUrl)
    {
        closePDialog();
        TourTicketDetailProductsInfoBean bean = travelOrderDetailBean.getProductInfo();

        LogUtil.i("name=" + bean.getLineName() + ",imageUrl=" + travelOrderDetailBean.getProductImageUrl());

        ShareDialogFragment.getInstance("", "")
                .setTitle(bean != null ? bean.getLineName() : "旅游")
                .setImage(bean != null ? travelOrderDetailBean.getProductImageUrl() : null)
                .setUrl(CommonUtil.getGiftcardShareUrl(commandAndUrl))
                .setContent("快来点开看看吧~")
                .show(getSupportFragmentManager(), "");
    }

    @Override
    public void onGetCommandFail(String failMsg)
    {
        closePDialog();
        toastCustom(failMsg);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
        if (type == 0)
        {
            travelOrderDetailBean = JSON.parseObject(String.valueOf(o), TravelOrderDetailBean.class);

            if (travelOrderDetailBean.getGuestList() != null)
            {
                guestList.clear();
                guestList.addAll(travelOrderDetailBean.getGuestList());
                OrderTravelDetailShowIDAdapter adapter = new OrderTravelDetailShowIDAdapter(this, guestList);
                showIDlistView.setAdapter(adapter);

            }
            tourTicketDetailContactsBean = travelOrderDetailBean.getContacts();
            tourTicketDetailProductsInfoBean = travelOrderDetailBean.getProductInfo();
            setData(travelOrderDetailBean);

        } else
        {

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }
}
