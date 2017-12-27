package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.activtiy.HotelProductInfoActivity;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;

/**
 * Created by 玉兵 on 2017/10/29.
 */

public class MyYouhuiquanCouponeAdapter extends BaseListAdapter<CouponInfoBean>
{

    private ImageOptions options;



    public MyYouhuiquanCouponeAdapter(Context context)
    {
        super(context);
        options = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(5), ImageView.ScaleType.FIT_XY);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        MyViewHolder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_coupon_app_goods, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (MyViewHolder) convertView.getTag();
        }
        final CouponInfoBean bean = getItem(position);

        //
        x.image().bind(holder.ivLogo, ServiceDispatcher.getImageUrl(bean.getStoreImageUrl()), options);

        holder.tvShopName.setText(bean.getStoreName());

        int couponType = bean.getCouponType();

        if(couponType == 1){

            holder.tvFullCut.setText("满" + Utils.subZeroAndDot(bean.getFullAmount() + "")
                    + "减" + Utils.subZeroAndDot(bean.getCutAmount() + ""));

            holder.tvAmount.setText("¥" + Utils.subZeroAndDot(bean.getCutAmount() + ""));

        }else if(couponType == 2){

            float cutAmount = bean.getCutAmount();

            String zhekouStr = Utils.handNumberToString(cutAmount);

            holder.tvFullCut.setText("满" + Utils.subZeroAndDot(bean.getFullAmount() + "")
                    + "打" + zhekouStr + "折");

            holder.tvAmount.setText(zhekouStr+"折");

        }

        if(bean.getHot()  == 0){

            holder.starBar.setVisibility(View.GONE);

        }else {

            holder.starBar.setStarMarkAndSore(bean.getHot() / 2);
            holder.starBar.setVisibility(View.VISIBLE);
        }

        holder.tvScore.setText(bean.getHot() + "分");


        holder.tvShopAddress.setText(bean.getDistrictName());

        int receiveStatus = bean.getReceiveStatus();

        if(receiveStatus == 4){

            holder.tvGet.setText( "已使用");

            holder.tvGet.setTextColor(Color.parseColor("#d8d8d8"));

            holder.tvGet.setBackgroundResource(R.drawable.style_btn_gray_circle);

            holder.tvAmount.setBackgroundResource(R.mipmap.bg_gray_youhuiquan_no);

        }else if(receiveStatus == 3){

            holder.tvGet.setText( "立即使用");

            holder.tvGet.setTextColor(Color.parseColor("#980103"));

            holder.tvGet.setBackgroundResource(R.drawable.style_btn_red_circle);

            //检测是否在有效期间内容
            boolean e = AppUtils.checkStartEndDate(bean.getStartDate(),bean.getEndDate());

            if(e) {

                holder.tvAmount.setBackgroundResource(R.mipmap.icon_coupon_bg2);

                holder.tvGet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (bean.getReceiveStatus() == 3) {

                            /**
                             * 目前只有酒店
                             */

                            GlobalVariable.industryId = Integer.parseInt(AppConstant.hotel_id);
                            //其它产品订单
                            Date startDate = new Date();
                            Date endDate = DateUtil.addDay(startDate, 1);

                            String startDateStr = DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE);
                            String endDateStr = DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_DATE);
                            //设置查看酒店详情参数
                            HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();
                            productListParam.setArrDate(startDateStr);
                            productListParam.setDepDate(endDateStr);

                            //目前只处理酒店的优惠券
                            long storeId = bean.getStoreId();
                            Intent intent = new Intent(mContext, HotelProductInfoActivity.class);
                            intent.putExtra("hotelId", storeId + "");
                            mContext.startActivity(intent);

                        }
                    }
                });
            }else{

                holder.tvAmount.setBackgroundResource(R.mipmap.bg_gray_youhuiquan_no);
                holder.tvGet.setOnClickListener(null);
            }

        }else if(receiveStatus == 5){

            holder.tvGet.setText( "已过期");

            holder.tvGet.setTextColor(Color.parseColor("#d8d8d8"));

            holder.tvGet.setBackgroundResource(R.drawable.style_btn_gray_circle);

            holder.tvAmount.setBackgroundResource(R.mipmap.bg_gray_youhuiquan_no);
        }


        return convertView;
    }


    public CouponInfoBean getItemById(String yhqId)
    {
        if (!TextUtils.isEmpty(yhqId))
        {
            for (int i = 0; i < mList.size(); i++)
            {
                if (yhqId.equals(mList.get(i).getActId() + ""))
                {
                    return mList.get(i);
                }
            }
        }
        return null;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder
    {
        @ViewInject(R.id.ivLogo)
        public ImageView ivLogo;
        @ViewInject(R.id.tvShopName)
        public TextView tvShopName;
        @ViewInject(R.id.tvScore)
        public TextView tvScore;
        @ViewInject(R.id.tvShopAddress)
        public TextView tvShopAddress;
        @ViewInject(R.id.tvFullCut)
        public TextView tvFullCut;
        @ViewInject(R.id.tvAmount)
        public TextView tvAmount;
        @ViewInject(R.id.tvGet)
        public TextView tvGet;
        @ViewInject(R.id.starBar)
        public StarBar starBar;

        public View root;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            this.root = itemView;
            x.view().inject(this, itemView);
        }
    }
}

