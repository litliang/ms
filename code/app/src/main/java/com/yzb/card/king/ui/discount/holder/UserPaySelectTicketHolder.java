package com.yzb.card.king.ui.discount.holder;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.BaseCouponBean;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/23
 * 描  述：
 */
public class UserPaySelectTicketHolder extends BaseViewHolder<BaseCouponBean> implements View.OnClickListener {

    private Context context;

    private TextView tvPrice;

    private ImageView tvFuctionGet;

    private TextView tvCondition;

    private TextView tvCouponData;

    private TextView tvUnite;

    private TextView tvCouponDesc;

    private Handler handler;

    private LinearLayout llParent;

    private double orderMoney;

    private long selectorActId = -1;

    public UserPaySelectTicketHolder(ViewGroup parent, Handler handler, long actionId,double orderMoney)
    {
        super(parent, R.layout.view_item_user_payment_select_ticker);

        context = parent.getContext();

        this.handler = handler;

        this.orderMoney = orderMoney;

        selectorActId = actionId;
    }


    @Override
    public void setData(BaseCouponBean data)
    {


        if (selectorActId == data.getActId()) {

            tvFuctionGet.setVisibility(View.VISIBLE);
        } else {

            tvFuctionGet.setVisibility(View.INVISIBLE);
        }
        llParent.setTag(data);

        tvCouponDesc.setText(data.getActName());

        tvCouponData.setText(data.getStartDate() + "至" + data.getEndDate());

        String type = data.getCouponType();

        if ("1".equals(type)) {//1满减券

            tvPrice.setText(Utils.subZeroAndDot(data.getCutValue() + ""));

            tvUnite.setVisibility(View.VISIBLE);

            StringBuffer sb = new StringBuffer();

            if (data.getFullAmount() == 0) {

                sb.append("不限");

            } else {
                sb.append("满").append(data.getFullAmount());
            }

            tvCondition.setText(sb.toString());

        } else if ("2".equals(type)) {//2折扣券

            tvUnite.setVisibility(View.GONE);

            String zhekouStr = Utils.handNumberToString(data.getCutValue());

            tvPrice.setText(zhekouStr + "折");

            StringBuffer sb = new StringBuffer();

            if (data.getFullAmount() == 0) {

                sb.append("不限");

            } else {
                sb.append("满").append(data.getFullAmount());
            }

            tvCondition.setText(sb.toString());

        }else if("3".equals(type)){//抵扣券；

            tvPrice.setText(data.getCutValue() + "");

            tvUnite.setVisibility(View.VISIBLE);

            tvPrice.setTextSize(15);

            tvCondition.setText("抵扣");

        }


    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        tvPrice = findViewById(R.id.tvPrice);

        tvFuctionGet = findViewById(R.id.tvFuctionGet);

        tvCondition = findViewById(R.id.tvCondition);

        tvCouponData = findViewById(R.id.tvCouponData);

        tvUnite = findViewById(R.id.tvUnite);

        tvCouponDesc = findViewById(R.id.tvCouponDesc);

        llParent = findViewById(R.id.llParent);

        llParent.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.llParent:

                if (v.getTag() != null) {


                    BaseCouponBean baseCouponBean = (BaseCouponBean) v.getTag();

                    double fullAmount =   baseCouponBean.getFullAmount();

                    if(orderMoney >= fullAmount){

                        Message message = handler.obtainMessage();

                        message.obj = baseCouponBean;

                        handler.sendMessage(message);
                    }else {

                        ToastUtil.i(context,"不满足条件，无法使用此优惠券");
                    }


                }

                break;

            default:
                break;

        }
    }


}



