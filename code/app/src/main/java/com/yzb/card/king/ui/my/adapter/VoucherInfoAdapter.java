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
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.bonus.activity.UseInstructionsActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 玉兵 on 2017/12/5.
 */

public class VoucherInfoAdapter extends BaseListAdapter<CouponInfoBean> {

    public static final int WHAT_GET = 0x001;

    public static final int WHAT_BUY = 0x002;

    private Handler handler;

    private Context context;

    public VoucherInfoAdapter(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_voucher_info_shop, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        final CouponInfoBean bean = getItem(position);

        holder.tvShopNameOne.setText(bean.getActName());

        holder.tvGoldDateOne.setText(bean.getStartDate()+"至"+bean.getEndDate());

        String priceStr = "¥" + Utils.subZeroAndDot(bean.getFullAmount() + "");

        SpannableString msp = new SpannableString(priceStr);

        msp.setSpan(new RelativeSizeSpan(0.8f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.tvFullCutOne.setText(msp);

        holder.tvAmount.setText( Utils.subZeroAndDot(bean.getCutAmount() + "")+"元");

        holder.tvShuoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useRuleStr =   bean.getUseRule();

                Intent intent = new Intent(context, UseInstructionsActivity.class);

                intent.putExtra("useRule",useRuleStr);

                context.startActivity(intent);
            }
        });


        int receiveStatus = bean.getReceiveStatus();

        if (receiveStatus == 1) {

            holder.tvAmount.setBackgroundResource(R.mipmap.bg_gray_youhuiquan_no);

            holder.tvGetOne.setBackgroundResource(R.drawable.style_btn_gray_circle);

            holder.tvGetOne.setTextColor(Color.parseColor("#d8d8d8"));

            holder.tvGetOne.setText("已购买");


        } else if (receiveStatus == 0) {//未领取


            holder.tvGetOne.setTextColor(context.getResources().getColor(R.color.color_d6c07f));

            holder.tvGetOne.setBackgroundResource(R.drawable.style_btn_yellow_circle);

            holder.tvAmount.setBackgroundResource(R.mipmap.icon_coupon_bg3);

            holder.tvGetOne.setText("立即购买");

            holder.tvGetOne.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    //检测是否登录
                                                    if (UserManager.getInstance().isLogin()) {

                                                        if (handler != null) {
                                                            Message msg = handler.obtainMessage(WHAT_BUY);
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


        @ViewInject(R.id.tvShopNameOne)
        public TextView tvShopNameOne;

        @ViewInject(R.id.tvFullCutOne)
        public TextView tvFullCutOne;

        @ViewInject(R.id.tvGetOne)
        public TextView tvGetOne;

        @ViewInject(R.id.tvGoldDateOne)
        public TextView tvGoldDateOne;

        @ViewInject(R.id.tvShuoming)
        public TextView tvShuoming;


        @ViewInject(R.id.tvAmount)
        public  TextView tvAmount;

        public View root;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.root = itemView;
            x.view().inject(this, itemView);
        }
    }
}

