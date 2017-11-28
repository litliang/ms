package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 玉兵 on 2017/10/29.
 */

public class MyDaijinquanCouponeAdapter extends BaseListAdapter<CouponInfoBean> {


    public MyDaijinquanCouponeAdapter(Context context) {
        super(context);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        MyViewHolder holder;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.item_gold_coupon, parent, false);

            holder = new MyViewHolder(convertView);

            convertView.setTag(holder);

        } else {

            holder = (MyViewHolder) convertView.getTag();

        }
         CouponInfoBean bean = getItem(position);
        //
        LogUtil.e("bean.getCouponName()="+bean.getCouponName());
        holder.tvShopName.setText(bean.getCouponName());

        holder.tvGoldDate.setText(bean.getStartDate()+"至"+bean.getEndDate());

        int couponType = bean.getCouponType();
        LogUtil.e("couponType="+couponType);
        if (couponType == 1) {

            String priceStr = "¥" + Utils.subZeroAndDot(bean.getCutAmount() + "");

            SpannableString msp = new SpannableString(priceStr);

            msp.setSpan(new RelativeSizeSpan(0.8f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.tvAmount.setText(msp);

            holder.tvAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

            holder.tvFullCut.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);

            holder.tvFullCut.setText("满" + Utils.subZeroAndDot(bean.getFullAmount() + ""));

            holder.tvAmount.setTextColor(Color.parseColor("#970000"));

            holder.tvFullCut.setTextColor(Color.parseColor("#8e8e8e"));

        } else if (couponType == 2) {

            float cutAmount = bean.getCutAmount();

            String zhekouStr = Utils.handNumberToString(cutAmount);

            holder.tvFullCut.setText("满" + Utils.subZeroAndDot(bean.getFullAmount() + ""));

            holder.tvAmount.setText(zhekouStr + "折");

            holder.tvAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

            holder.tvFullCut.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);

            holder.tvAmount.setTextColor(Color.parseColor("#970000"));

            holder.tvFullCut.setTextColor(Color.parseColor("#8e8e8e"));

        }else if (couponType == 3) {//抵扣

            holder.tvAmount.setTextColor(Color.parseColor("#8e8e8e"));

            holder.tvAmount.setText("抵");

            holder.tvFullCut.setTextColor(Color.parseColor("#d7c181"));

            holder.tvFullCut.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

            holder.tvAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);

            String priceStr = "¥" + Utils.subZeroAndDot(bean.getCutAmount() + "");

            SpannableString msp = new SpannableString(priceStr);

            msp.setSpan(new RelativeSizeSpan(0.8f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.tvFullCut.setText(msp);
        }


        int receiveStatus = bean.getReceiveStatus();

        if (receiveStatus == 4) {

            holder.tvGet.setText("已使用");

            holder.tvGet.setTextColor(Color.parseColor("#d8d8d8"));

            holder.tvGet.setBackgroundResource(R.drawable.style_btn_gray_circle);

        } else if (receiveStatus == 3) {

            holder.tvGet.setText("立即使用");

            holder.tvGet.setTextColor(Color.parseColor("#ffffff"));

            holder.tvGet.setBackgroundResource(R.drawable.style_btn_blue_deep_circle);

        } else if (receiveStatus == 5) {

            holder.tvGet.setText("已过期");

            holder.tvGet.setTextColor(Color.parseColor("#ffffff"));

            holder.tvGet.setBackgroundResource(R.drawable.style_btn_gray_deep_circle);
        }

        return convertView;
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.tvShopName)
        public TextView tvShopName;

        @ViewInject(R.id.tvFullCut)
        public TextView tvFullCut;

        @ViewInject(R.id.tvAmount)
        public TextView tvAmount;

        @ViewInject(R.id.tvGet)
        public TextView tvGet;

        @ViewInject(R.id.tvGoldDate)
        public TextView tvGoldDate;

        public View root;

        public MyViewHolder(View itemView) {

            super(itemView);

            this.root = itemView;

           x.view().inject(this, itemView);

        }
    }
}

