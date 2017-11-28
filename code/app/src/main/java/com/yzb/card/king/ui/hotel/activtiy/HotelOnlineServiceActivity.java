package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.common.PayFormIf;
import com.yzb.card.king.bean.order.HotelOrderDetailBean;
import com.yzb.card.king.bean.order.HotelOrderServeBean;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.order.dialog.OrderMessagFuctioneDialog;
import com.yzb.card.king.ui.order.presenter.HotelTicketsDetailPresenter;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：在线服务
 * 作  者：Li Yubing
 * 日  期：2017/9/14
 * 描  述：
 */
@ContentView(R.layout.activity_online_service_hotel)
public class HotelOnlineServiceActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    @ViewInject(R.id.titleName)
    private TextView titleName;

    @ViewInject(R.id.tvMsg)
    private TextView tvMsg;

    @ViewInject(R.id.tvButton)
    private TextView tvButton;

    private HotelOrderServeBean orderInfo;

    private int paymentType;
    private int code;

    private HotelTicketsDetailPresenter hotelTicketsDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitleNmae("在线服务");

        orderInfo = (HotelOrderServeBean) getIntent().getSerializableExtra("orderInfo");

        paymentType = orderInfo.getPaymentType();

        code = getIntent().getIntExtra("code", 0);

        initView();

    }

    private void initView()
    {

        if (HoteUtil.HOTEL_CHAKANQUERENXINXI_CODE == code) {//查看确认信息

            titleName.setText("查看确认信息");
            tvMsg.setText("您的订单已确认成功，酒店将为您保留房间，请及时入住。请直接向酒店前台告知入住人姓名、电话办理入住");

        } else if (HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE == code) {//查询酒店信息

            titleName.setText("查询酒店信息");
            tvMsg.setText("如果需要了解您订单里包含的酒店信息、房间信息和优惠信息，请前往酒店详情页面查看相关内容。如果您还需要了解其他方面的内容，请直接电话联系酒店");
            tvButton.setText("查看酒店详情");
            tvButton.setVisibility(View.VISIBLE);
            tvButton.setOnClickListener(this);

        } else if (HoteUtil.HOTEL_DANBAOHESHITUIHUAN_CODE == code) {//担保何时退还

            titleName.setText("担保何时退还");
            tvMsg.setText("因酒店房间紧张，需暂扣首晚房费进行担保。到店后仍需支付房费，审核离店后，担保金将于离店后5个工作日内原路退回。");

        } else if (HoteUtil.HOTEL_FAPIAOHESHIJISONG_CODE == code) {//发票何时寄送

            titleName.setText("发票何时寄");
            tvMsg.setText("嗨生活开具的纸质发票将在您离店后3-5工作日内寄出，届时可在订单详情页查看发票物流号。电子发票将在您离店1-2个工作日内后寄到收票邮箱");
            tvButton.setText("联系酒店");
            tvButton.setVisibility(View.VISIBLE);
            tvButton.setOnClickListener(this);

        } else if (HoteUtil.HOTEL_CANCELORDER_CODE == code) {//取消订单

            hotelTicketsDetailPresenter = new HotelTicketsDetailPresenter(this);

            titleName.setText("取消订单");

            //检测退款信息
            ProgressDialogUtil.getInstance().showProgressDialog(GlobalApp.getInstance().getPublicActivity(), false);

            Map<String, Object> mapRefundMap = new HashMap<>();

            mapRefundMap.put("orderId", orderInfo.getOrderId());

            hotelTicketsDetailPresenter.refundMoneyCheck(CardConstant.ORDER_REFUNDORDERCHECK, mapRefundMap);

            tvButton.setText("取消订单");
            tvButton.setVisibility(View.VISIBLE);
            tvButton.setOnClickListener(this);

        } else if (HoteUtil.HOTEL_RUHEFANXIAN_CODE == code) {//如何返现

            titleName.setText("如何返现");

            StringBuffer sb = new StringBuffer();

            sb.append("您的返现金额为").append(orderInfo.getCashbackAmount()).append("元，你可在离店后30天内申请返现。申请返现的方式是点击嗨生活客户端订单详情页面的“返现”按钮。\n返现金额将于发起返现起5个工作日内打入您的嗨生活账户");


            tvMsg.setText("因酒店房间紧张，需暂扣首晚房费进行担保。到店后仍需支付房费，审核离店后，担保金将于离店后5个工作日内原路退回。");

        } else if (HoteUtil.HOTEL_RUHEKAIFAPIAO_CODE == code) {

            titleName.setText("如何开发票");

            int paymentType = orderInfo.getPaymentType();

            if (paymentType == PayFormIf.PAYMENT_TYPE_DANBAO) {
                tvMsg.setText("担保支付的订单，请到酒店前台索取发票");
            }
            if (paymentType == PayFormIf.PAYMENT_TYPE_DAODIANFU) {
                tvMsg.setText("到店付的订单，请到酒店前台索取发票");
            }
        } else if (HoteUtil.HOTEL_WANGJIKAIFAPIAO_CODE == code) {

            titleName.setText("忘记开发票");

            tvMsg.setText("该笔订单的发票由酒店商家开具，请致电酒店前台询问补开发票相关事宜");
        } else if (HoteUtil.HOTEL_YUDAOWENTI_CODE == code) {

            titleName.setText("支付遇到问题");

            tvMsg.setText("您的订单尚未支付，支付不成功可能是您的账户余额不足、银行卡余额不足或网络环境不稳定等。\n建议您查看并确保支付的账户里的余额足够，并在稳定的网络环境下重新支付");
        }


    }

    @Override
    public void onClick(View v)
    {

        if (HoteUtil.HOTEL_CHAXUNJIUDIANXIN_CODE == code) {//查询酒店信息


            Intent intent = new Intent(this, HotelProductInfoActivity.class);
            intent.putExtra("hotelId", orderInfo.getHotelId() + "");
            startActivity(intent);

        } else if (HoteUtil.HOTEL_FAPIAOHESHIJISONG_CODE == code) {//发票何时寄送


            String phoneStr = orderInfo.getTel();

            callHotelPhone(phoneStr);
        } else if (HoteUtil.HOTEL_CANCELORDER_CODE == code) {//取消订单

            orderHandlerFunction("订单取消后不可恢复，是否确定取消？", OrderMessagFuctioneDialog.FUCTION_ORDER_REFUND_CODE);

        }

    }

    private void callHotelPhone(String number)
    {
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

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

            Map<String, Object> mapRefundMap = new HashMap<>();

            mapRefundMap.put("orderId", orderInfo.getOrderId());

            mapRefundMap.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));

            mapRefundMap.put("goodsName", orderInfo.getRoomsName());

            mapRefundMap.put("storeId", orderInfo.getHotelId());


            hotelTicketsDetailPresenter.confirmRefundMoney(CardConstant.ORDER_REFUNDORDERCONFIRM, mapRefundMap);
        }
    };

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        dimissPdialog();

        if (type == 2) {//退款检查

            StringBuffer sb = new StringBuffer();

            sb.append("点击“取消订单”按钮取消当前订单。订单取消后不可恢复，请谨慎操作。").append("\n");

            sb.append(o + "");

            tvMsg.setText(sb.toString());
        } else if (type == 3) {

            ToastUtil.i(this,o+"");

            finish();
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
}
