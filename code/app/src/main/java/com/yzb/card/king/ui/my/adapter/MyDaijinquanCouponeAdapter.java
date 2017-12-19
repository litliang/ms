package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.bonus.activity.UseInstructionsActivity;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.activtiy.HotelProductListActivity;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;

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
       final  CouponInfoBean bean = getItem(position);
        //
        holder.tvShopName.setText(bean.getActName());

        holder.tvGoldDate.setText(bean.getStartDate() + "至" + bean.getEndDate());

        holder.tvAmount.setTextColor(Color.parseColor("#8e8e8e"));

        holder.tvAmount.setText("抵");

        holder.tvFullCut.setTextColor(Color.parseColor("#d7c181"));

        holder.tvFullCut.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        holder.tvAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

        String priceStr = "¥" + Utils.subZeroAndDot(bean.getCutAmount() + "");

        SpannableString msp = new SpannableString(priceStr);

        msp.setSpan(new RelativeSizeSpan(0.8f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.tvFullCut.setText(msp);

        int receiveStatus = bean.getReceiveStatus();

        if (receiveStatus == 4) {

            holder.tvGet.setText("已使用");

            holder.tvGet.setTextColor(Color.parseColor("#d8d8d8"));

            holder.tvGet.setBackgroundResource(R.drawable.style_btn_gray_circle);

            holder.tvGet.setOnClickListener(null);

        } else if (receiveStatus == 3) {

            holder.tvGet.setText("立即使用");
            holder.tvGet.setTextColor(Color.parseColor("#ffffff"));
            //检测是否在有效期间内容
            boolean e = AppUtils.checkStartEndDate(bean.getStartDate(),bean.getEndDate());

            if(e){

                holder.tvGet.setBackgroundResource(R.drawable.style_btn_blue_deep_circle);

                holder.tvGet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Date startDate = new Date();

                        Date endDate = DateUtil.addDay(startDate, 1);

                        //设置日期
                        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                        productListParam.setArrDate(DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE));

                        productListParam.setDepDate(DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_DATE));

                        Intent intent = new Intent(mContext, HotelProductListActivity.class);

                        intent.putExtra("CouponInfo",bean);

                        mContext.startActivity(intent);

                    }
                });

            }else {

                holder.tvGet.setBackgroundResource(R.drawable.style_btn_gray_deep_circle);

                holder.tvGet.setOnClickListener(null);

            }


        } else if (receiveStatus == 5) {

            holder.tvGet.setText("已过期");

            holder.tvGet.setTextColor(Color.parseColor("#ffffff"));

            holder.tvGet.setBackgroundResource(R.drawable.style_btn_gray_deep_circle);

            holder.tvGet.setOnClickListener(null);
        }

        holder.tvShuoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useRuleStr =   bean.getUseRule();

                Intent intent = new Intent(mContext, UseInstructionsActivity.class);

                intent.putExtra("useRule",useRuleStr);

                mContext.startActivity(intent);
            }
        });


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

        @ViewInject(R.id.tvShuoming)
        public TextView tvShuoming;

        public View root;

        public MyViewHolder(View itemView) {

            super(itemView);

            this.root = itemView;

            x.view().inject(this, itemView);

        }
    }
}

