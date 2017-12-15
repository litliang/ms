package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.bean.my.CouponShopBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.bonus.activity.UseInstructionsActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 类  名：优惠券适配器
 * 作  者：Li Yubing
 * 日  期：2017/10/20
 * 描  述：
 */
public class CouponInfoAdapter extends BaseListAdapter<CouponInfoBean> {

    public static final int WHAT_GET = 0x001;
    public static final int WHAT_BUY = 0x002;
    private Handler handler;
    private ImageOptions options;

    private Context context;

    public CouponInfoAdapter(Context context) {
        super(context);
        this.context = context;
        options = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(5), ImageView.ScaleType.FIT_XY);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_coupon_app_goods, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        final CouponInfoBean bean = getItem(position);


        x.image().bind(holder.ivLogo, ServiceDispatcher.getImageUrl(bean.getStoreImageUrl()), options);

        holder.tvShopName.setText(bean.getStoreName());

        int couponType = bean.getCouponType();

        if (couponType == 1) {//满减券

            holder.tvFullCut.setText("满" + Utils.subZeroAndDot(bean.getFullAmount() + "")
                    + "减" + Utils.subZeroAndDot(bean.getCutAmount() + ""));

            holder.tvAmount.setText("¥" + Utils.subZeroAndDot(bean.getCutAmount() + ""));


        } else if (couponType == 2) {//折扣券

            float cutAmount = bean.getCutAmount();

            String zhekouStr = Utils.handNumberToString(cutAmount);

            holder.tvFullCut.setText("满" + Utils.subZeroAndDot(bean.getFullAmount() + "")
                    + "打" + zhekouStr + "折");

            holder.tvAmount.setText(zhekouStr + "折");


        }

        if (bean.getHot() == 0) {

            holder.starBar.setVisibility(View.GONE);

        } else {

            holder.starBar.setStarMarkAndSore(bean.getHot() / 2);
            holder.starBar.setVisibility(View.VISIBLE);
        }

        holder.tvScore.setText(bean.getHot() + "分");


        holder.tvShopAddress.setText(bean.getDistrictName());

        int receiveStatus = bean.getReceiveStatus();

        if (receiveStatus == 1) {

            holder.tvAmount.setBackgroundResource(R.mipmap.bg_gray_youhuiquan_no);

            holder.tvGet.setBackgroundResource(R.drawable.style_btn_gray_circle);

            holder.tvGet.setTextColor(Color.parseColor("#d8d8d8"));

            holder.tvGet.setText("已领取");

            holder.tvGet.setOnClickListener(null);

        } else if (receiveStatus == 0) {//未领取

            holder.tvGet.setTextColor(context.getResources().getColor(R.color.color_a10000));

            holder.tvAmount.setBackgroundResource(R.mipmap.icon_coupon_bg2);

            holder.tvGet.setBackgroundResource(R.drawable.style_btn_red_circle);

            holder.tvGet.setText("立即领取");

            holder.tvGet.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    //检测是否登录
                                                    if (UserManager.getInstance().isLogin()) {

                                                        if (handler != null) {
                                                            Message msg = handler.obtainMessage(WHAT_GET);
                                                            msg.arg1 = position;
                                                            handler.sendMessage(msg);
                                                        }

                                                    } else {
                                                        new GoLoginDailog(context).create().show();
                                                    }

                                                }
                                            }

            );
        }

        return convertView;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public CouponInfoBean getItemById(String yhqId) {
        if (!TextUtils.isEmpty(yhqId)) {
            for (int i = 0; i < mList.size(); i++) {
                if (yhqId.equals(mList.get(i).getActId() + "")) {
                    return mList.get(i);
                }
            }
        }
        return null;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
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

        public MyViewHolder(View itemView) {
            super(itemView);
            this.root = itemView;
            x.view().inject(this, itemView);
        }
    }
}

