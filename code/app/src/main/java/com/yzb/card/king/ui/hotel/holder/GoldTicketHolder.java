package com.yzb.card.king.ui.hotel.holder;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.BaseCouponBean;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：酒店--优惠券
 * 作  者：Li Yubing
 * 日  期：2017/8/9
 * 描  述：
 */
public class GoldTicketHolder extends BaseViewHolder<BaseCouponBean> implements View.OnClickListener {

    private Context context;

    private TextView tvPrice;

    private TextView tvFuctionGet;

    private TextView tvCondition;

    private TextView tvCouponData;

    private TextView tvUnite;

    private TextView tvCouponDesc;

    private  Handler handler;

    public GoldTicketHolder(ViewGroup parent,Handler handler)
    {
        super(parent, R.layout.view_item_gold_ticker);

        context = parent.getContext();

        this.handler = handler;
    }


    @Override
    public void setData(BaseCouponBean data)
    {

        String recieveStatus = data.getReceiveStatus();

        tvFuctionGet.setTag(data);

        String desc = "【" + data.getActName() + "】";

        if(!TextUtils.isEmpty(data.getActDesc())){

            desc = desc+data.getActDesc();

        }

        tvCouponDesc.setText(desc);

        tvCouponData.setText(data.getStartDate() + "至" + data.getEndDate());

        String type = data.getCouponType();

        if ("1".equals(type)) {//1满减券

            tvPrice.setText( Utils.subZeroAndDot(data.getCutValue() + ""));

            tvUnite.setVisibility(View.VISIBLE);

            tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

            StringBuffer sb = new StringBuffer();

            if(data.getFullAmount() == 0){

                sb.append("不限");

            }else {
                sb.append("满").append( Utils.subZeroAndDot(data.getFullAmount()+""));
            }

            tvCondition.setText(sb.toString());

            if ("1".equals(recieveStatus)) {

                tvFuctionGet.setText("已领取");

                tvFuctionGet.setBackgroundResource(R.drawable.bg_round_corner_gray);

            } else if ("0".equals(recieveStatus)) {

                tvFuctionGet.setText("立即领取");

                tvFuctionGet.setBackgroundResource(R.drawable.bg_round_corner_red);

            }

        } else if ("2".equals(type)) {//2折扣券

            String zhekouStr = Utils.handNumberToString(data.getCutValue());

            tvUnite.setVisibility(View.GONE);

            tvPrice.setText( zhekouStr+ "折");

            tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);

            StringBuffer sb = new StringBuffer();

            if(data.getFullAmount() == 0){

                sb.append("不限");

            }else {
                sb.append("满").append( Utils.subZeroAndDot(data.getFullAmount()+""));
            }

            tvCondition.setText(sb.toString());

            if ("1".equals(recieveStatus)) {

                tvFuctionGet.setText("已领取");

                tvFuctionGet.setBackgroundResource(R.drawable.bg_round_corner_gray);

            } else if ("0".equals(recieveStatus)) {

                tvFuctionGet.setText("立即领取");

                tvFuctionGet.setBackgroundResource(R.drawable.bg_round_corner_red);

            }

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

        tvFuctionGet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvFuctionGet:

                //检查是否登录
                if (UserManager.getInstance().isLogin()) {

                    BaseCouponBean  baseCouponBean = (BaseCouponBean) v.getTag();

                    String recieveStatus = baseCouponBean.getReceiveStatus();

                    if ("0".equals(recieveStatus)) {

                       Message message = handler.obtainMessage();

                        message.obj = baseCouponBean;

                        handler.sendMessage(message);
                    }

                } else {

                    new GoLoginDailog(context).create().show();
                }
                break;

            default:
                break;

        }
    }


}


