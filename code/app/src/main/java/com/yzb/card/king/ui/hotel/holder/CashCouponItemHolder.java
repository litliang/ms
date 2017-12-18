package com.yzb.card.king.ui.hotel.holder;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.BaseCouponBean;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.Utils;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 代金券
 * Created by 玉兵 on 2017/12/18.
 */

public class CashCouponItemHolder extends BaseViewHolder<BaseCouponBean> implements View.OnClickListener {

    private Context context;

    private TextView tvPrice;

    private TextView tvFuctionGet;

    private TextView tvCondition;

    private TextView tvCouponData;

    private TextView tvUnite;

    private TextView tvCouponDesc;

    private Handler handler;

    public CashCouponItemHolder(ViewGroup parent, Handler handler) {
        super(parent, R.layout.view_item_gold_ticker);

        context = parent.getContext();

        this.handler = handler;
    }


    @Override
    public void setData(BaseCouponBean data) {

        String recieveStatus = data.getRecieveStatus();

        tvFuctionGet.setTag(data);

        tvCouponDesc.setText("【" + data.getActName() + "】" + data.getActDesc());

        tvCouponData.setText(data.getStartDate() + "至" + data.getEndDate());

        tvPrice.setText(Utils.subZeroAndDot(data.getFullAmount() + ""));

        tvUnite.setVisibility(View.VISIBLE);

        tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        tvCondition.setText("抵扣");

        if ("1".equals(recieveStatus)) {

            tvFuctionGet.setText("已购买");

            tvFuctionGet.setBackgroundResource(R.drawable.bg_round_corner_gray);

        } else if ("0".equals(recieveStatus)) {

            tvFuctionGet.setText("立即购买");

            tvFuctionGet.setBackgroundResource(R.drawable.bg_round_corner_red);

        }

    }

    @Override
    public void onInitializeView() {
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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvFuctionGet:

                //检查是否登录
                if (UserManager.getInstance().isLogin()) {

                    BaseCouponBean baseCouponBean = (BaseCouponBean) v.getTag();

                    String recieveStatus = baseCouponBean.getRecieveStatus();

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



