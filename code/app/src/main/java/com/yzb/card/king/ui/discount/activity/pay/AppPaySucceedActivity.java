package com.yzb.card.king.ui.discount.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.order.PaySucceeOrderDetailBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.home.AppHomeActivity;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.order.HotelOrderDetailActivity;
import com.yzb.card.king.ui.order.PlaneTicketsDetailActivity;
import com.yzb.card.king.ui.order.presenter.AppOrderServePreseter;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;

/**
 * 类  名：支付成功
 * 作  者：Li Yubing
 * 日  期：2017/9/19
 * 描  述：
 */
@ContentView(R.layout.activity_app_pay_succeed)
public class AppPaySucceedActivity extends BaseActivity implements BaseViewLayerInterface, View.OnClickListener{

    @ViewInject(R.id.tvOrderPrice)
    private TextView tvOrderPrice;

    @ViewInject(R.id.tvOne)
    private TextView tvOne;

    @ViewInject(R.id.tvTwo)
    private TextView tvTwo;

    @ViewInject(R.id.tvThree)
    private TextView tvThree;

    @ViewInject(R.id.tvOrderMsg)
    private TextView tvOrderMsg;

    @ViewInject(R.id.llItemContent)
    private LinearLayout llItemContent;

    private   GoldTicketParam goldTicketParam;

    private long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        initData();
    }

    private void initData()
    {

        if(getIntent().hasExtra("orderId") && getIntent().hasExtra("goldTicketParam")){

             orderId = getIntent().getLongExtra("orderId",-1);

             goldTicketParam = (GoldTicketParam) getIntent().getSerializableExtra("goldTicketParam");

            double payMoney = getIntent().getDoubleExtra("payMoney",0);

            tvOrderPrice.setText("¥"+ Utils.subZeroAndDot(payMoney+""));

            AppOrderServePreseter appOrderServePreseter = new AppOrderServePreseter(this);

            appOrderServePreseter.sendOrdersPayFinishInfoRequest(orderId);

        }
    }

    private void initView()
    {

        findViewById(R.id.tvBackHome).setOnClickListener(this);

        findViewById(R.id.tvOrderDetail).setOnClickListener(this);

        findViewById(R.id.llProductDetail).setOnClickListener(this);

    }

    private  PaySucceeOrderDetailBean paySucceeOrderDetailBean;

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.tvBackHome://返回首页

                readyGoThenFinish(this,AppHomeActivity.class);

                break;
            case R.id.llProductDetail:
            case R.id.tvOrderDetail://订单详情

                if(paySucceeOrderDetailBean == null){

                    return;
                }

                if(orderId ==-1){
                    return;
                }

                String  industryId = goldTicketParam.getIndustryId()+"";

                if(industryId.equals(AppConstant.hotel_id)){//酒店

                    int orderType =   paySucceeOrderDetailBean.getOrderType();

                    String hoteType ="";
                    if(OrderBean.ORDER_TYPE_HOTELS==orderType) {//酒店房间--房间

                        hoteType = HoteUtil.HOTEL_ROOM_CODE;

                    }else if(OrderBean.ORDER_TYPE_ROOMDING==orderType) {//酒店房间--餐厅

                        hoteType = HoteUtil.HOTEL_DINING_ROOM_CODE;

                    }else if(OrderBean.ORDER_TYPE_JIUBAR==orderType) {//酒店房间--酒吧

                        hoteType = HoteUtil.HOTEL_BAR_CODE;

                    }else if(OrderBean.ORDER_TYPE_HUICHANG==orderType) {//酒店房间--会场

                        hoteType = HoteUtil.HOTEL_MEETING_PLACE_CODE;
                    }else if(OrderBean.ORDER_TYPE_SPA==orderType) {//酒店房间--SPA

                        hoteType = HoteUtil.HOTEL_SPA_CODE;

                    }else if(OrderBean.ORDER_TYPE_DATANGBA==orderType) {//酒店房间--大堂吧

                        hoteType = HoteUtil.HOTEL_LOBBY_BAR_CODE;

                    }else if(OrderBean.ORDER_TYPE_JIANSHENFANG==orderType) {//酒店房间--健身房

                        hoteType = HoteUtil.HOTEL_GYM_CODE;

                    }else if(OrderBean.ORDER_TYPE_YOUYONGCHI==orderType) {//酒店房间--游泳池

                        hoteType = HoteUtil.HOTEL_SWMMING_POOL_CODE;

                    }else if(OrderBean.ORDER_TYPE_XIANSHIQIANGGOU==orderType) {//酒店房间--限时抢购

                        hoteType = HoteUtil.HOTEL_FAST_BUY_CODE;

                    }else if(OrderBean.ORDER_TYPE_KAQUANYI==orderType) {//酒店房间--卡权益

                        hoteType = HoteUtil.HOTEL_CARD_EQUITY_CODE;
                    }

                    Intent intent = new Intent(this, HotelOrderDetailActivity.class);
                    intent.putExtra("id", orderId+"");
                    intent.putExtra("hotelType",hoteType);//1房间、2餐厅、3酒吧、4会场、5SPA、6大堂吧、7卡权益、8限时抢购、9健身房、10游泳池
                    startActivity(intent);
                    finish();

                }else if(industryId.equals(OrderBean.ORDER_TYPE_AIRCRAFT+"")){//机票

                   PaySucceeOrderDetailBean.PlaneInfoSuccessBean  planeInfoSuccessBean = paySucceeOrderDetailBean.getPlaneInfo();

                    Intent intent = new Intent(this, PlaneTicketsDetailActivity.class);
                    intent.putExtra("routeType", planeInfoSuccessBean.getTripType());
                    intent.putExtra(PlaneTicketsDetailActivity.ORDER_BEAN, orderId);
                    startActivity(intent);
                    finish();
                }

                break;

            default:
                break;

        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

        if( type == AppOrderServePreseter.ORDERSPAYFINISHINFO_CODE){

             paySucceeOrderDetailBean = JSONObject.parseObject(o+"",PaySucceeOrderDetailBean.class);

            int orderType =   paySucceeOrderDetailBean.getOrderType();

            StringBuffer sbOne = new StringBuffer();

            StringBuffer sbTwo = new StringBuffer();

            StringBuffer sbThree = new StringBuffer();

            if(OrderBean.ORDER_TYPE_HOTELS==orderType){//酒店房间--房间

               PaySucceeOrderDetailBean.HotelInfoSucceeBean hotelInfo = paySucceeOrderDetailBean.getHotelInfo();

                String arrO = hotelInfo.getArrDate();

                String endO = hotelInfo.getDepDate();

                Date arrDate = DateUtil.string2Date(arrO,DateUtil.DATE_FORMAT_DATE);

                Date endDate = DateUtil.string2Date(endO,DateUtil.DATE_FORMAT_DATE);

                sbOne.append(DateUtil.date2String(arrDate,DateUtil.DATE_FORMAT_MONTH_DAY)).append("~").append(DateUtil.date2String(endDate,DateUtil.DATE_FORMAT_MONTH_DAY)).append(" 共" + AppUtils.ToCH(DateUtil.naturalDaysBetween(arrDate, endDate)) + getString(R.string.hotel_toast_night));

                int cancelType = hotelInfo.getCancelType();

                String str = "";

                if(cancelType == 1){

                    str = "免费取消";

                }else  if(cancelType == 2){
                    str = "限时取消";
                } if(cancelType == 3){
                    str = "付费取消";
                }

                sbTwo.append(hotelInfo.getRoomsName());

                if(!TextUtils.isEmpty(str)){

                    sbTwo.append("(").append(str).append(")");
                }

                sbThree.append(hotelInfo.getWifiDesc()).append(" "+hotelInfo.getBedTypeDesc()).append(" "+hotelInfo.getMealInfo());

            }else if(orderType >= OrderBean.ORDER_TYPE_ROOMDING && orderType<= OrderBean.ORDER_TYPE_YOUYONGCHI){//酒店的其它产品


               PaySucceeOrderDetailBean.HotelOtherInfoSucceeBean otherInfoSucceeBean = paySucceeOrderDetailBean.getHotelOtherInfo();

                sbOne.append(otherInfoSucceeBean.getPolicysName());

                sbTwo.append("使用时间").append(otherInfoSucceeBean.getUseDateDesc()).append("(").append(otherInfoSucceeBean.getUseTimeDesc()).append(")");

                sbThree.append("有效期：").append(otherInfoSucceeBean.getEffDateDesc());

            }else if(orderType == OrderBean.ORDER_TYPE_KAQUANYI ) {//卡权益

              PaySucceeOrderDetailBean.RightsInfoSucceeBean rightsInfoSucceeBean = paySucceeOrderDetailBean.getRightsInfo();

                sbOne.append(rightsInfoSucceeBean.getActName());

                sbTwo.append("使用时间：").append(rightsInfoSucceeBean.getEffDateDesc());

                sbThree.append("附赠权益：").append(rightsInfoSucceeBean.getGoodsList());

            }else if(orderType == OrderBean.ORDER_TYPE_XIANSHIQIANGGOU ) {//限时抢购

                PaySucceeOrderDetailBean.FlashSaleInfoSucceeBean flashSaleInfoSucceeBean = paySucceeOrderDetailBean.getFlashsaleInfo();

                sbOne.append(flashSaleInfoSucceeBean.getActName());

                sbTwo.append("使用时间：").append(flashSaleInfoSucceeBean.getEffDateDesc());

                sbThree.append(flashSaleInfoSucceeBean.getGoodsList());
            }

            tvOne.setText(sbOne.toString());

            tvTwo.setText(sbTwo.toString());

            tvThree.setText(sbThree.toString());

            llItemContent.setVisibility(View.VISIBLE);


            //提示信息
            String  industryId = goldTicketParam.getIndustryId()+"";

            if(industryId.equals(AppConstant.hotel_id)){//酒店

                if(OrderBean.ORDER_TYPE_HOTELS==orderType) {//酒店房间--房间

                    tvOrderMsg.setText("商家将尽快与您短信联系");

                }else if(OrderBean.ORDER_TYPE_ROOMDING==orderType) {//酒店房间--餐厅


                }else if(OrderBean.ORDER_TYPE_JIUBAR==orderType) {//酒店房间--酒吧


                }else if(OrderBean.ORDER_TYPE_HUICHANG==orderType) {//酒店房间--会场

                }else if(OrderBean.ORDER_TYPE_SPA==orderType) {//酒店房间--SPA


                }else if(OrderBean.ORDER_TYPE_DATANGBA==orderType) {//酒店房间--大堂吧


                }else if(OrderBean.ORDER_TYPE_JIANSHENFANG==orderType) {//酒店房间--健身房


                }else if(OrderBean.ORDER_TYPE_YOUYONGCHI==orderType) {//酒店房间--游泳池


                }else if(OrderBean.ORDER_TYPE_XIANSHIQIANGGOU==orderType) {//酒店房间--限时抢购


                }else if(OrderBean.ORDER_TYPE_KAQUANYI==orderType) {//酒店房间--卡权益

                }


            }else if(industryId.equals(AppConstant.ticket_id)){//机票



            }

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }
}
