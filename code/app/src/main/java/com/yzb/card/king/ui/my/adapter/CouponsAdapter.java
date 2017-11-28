package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.CouponsHomeBean;
import com.yzb.card.king.ui.appwidget.AutoSplitTextView;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 我的优惠券首页
 *
 * @author gengqiyun
 * @date 2016.12.13
 */
public class CouponsAdapter extends BaseListAdapter<CouponsHomeBean>
{
    public static final int WHAT_CREATE_COMMAND = 0x004;
    public static final int WHAT_USE = 0x005;
    private Handler dataHandler;
    private String clickShareContent; //点击的要分享的内容；

    public String getShareContent()
    {
        return clickShareContent;
    }

    public CouponsAdapter(Context context)
    {
        super(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_my_coupon, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        final CouponsHomeBean bean = getItem(position);
        holder.tvActName.setText("满减券"); //只有一种类型；

        SpannableString ssAmount = new SpannableString(Utils.subZeroAndDot(bean.getCutAmount() + "") + "元");
        //缩小元字体大小；
        ssAmount.setSpan(new AbsoluteSizeSpan(DensityUtil.dip2px(14)), ssAmount.length() - 1, ssAmount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //加粗金额；
        ssAmount.setSpan(new StyleSpan(Typeface.BOLD), 0, ssAmount.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.tvMaxAmount.setText(ssAmount);

        //是否可用；
        boolean isEnable = CouponsHomeBean.STATUS_NO_USE.equals(bean.getActStatus());
        holder.ivCouponState.setImageResource(isEnable ? R.mipmap.icon_coupon_no_use : R.mipmap.icon_coupon_used);

        holder.tvUseState.setText(isEnable ? "立即使用" :
                CouponsHomeBean.STATUS_USED.equals(bean.getActStatus()) ? "已使用" : "已失效");

        holder.tvUseState.setClickable(isEnable);

        StringBuilder sb = new StringBuilder();

        String shopIds = bean.getShopIds();
        String goodsIds = bean.getGoodsIds();

        /**
         * 说明：industryId 存在 shopIds、goodsIds不存在  是通用券；
         *      industryId 存在  shopIds存在 goodsIds不存在  是所有商品；
         *      industryId 存在  shopIds存在和goodsIds存在  是针对某一个商品；
         */
        int flag = -1; //优惠券类型；0:在某个行业指定商家使用；1：全部商品使用；2：某个行业指定商品使用；
        String channelName;
        if (TextUtils.isEmpty(shopIds) && TextUtils.isEmpty(goodsIds))
        {
            flag = 0;
            channelName = "通用券";
            sb.append("满" + Utils.subZeroAndDot(bean.getFullAmount() + "") + "减"
                    + Utils.subZeroAndDot(bean.getCutAmount() + "\n"))
                    .append("在" + bean.getIndustryName() + "行业\n使用");
        } else
        {
            channelName = bean.getIndustryName();
            sb.append("满" + Utils.subZeroAndDot(bean.getFullAmount() + "") + "减"
                    + Utils.subZeroAndDot(bean.getCutAmount() + "\n"))
                    .append("在" + bean.getShopNames() + "\n");

            if (!TextUtils.isEmpty(shopIds) && TextUtils.isEmpty(goodsIds))
            {
                flag = 1;
                sb.append("全部商品使用" + "\n");
            } else if (!TextUtils.isEmpty(shopIds) && !TextUtils.isEmpty(goodsIds))
            {
                flag = 2;
                sb.append("机票".equals(bean.getIndustryName()) ? "指定航线使用" : "指定商品使用" + "\n");
            }
        }
        final String tempShareContent = sb.toString();

        holder.tvChannelName.setText(channelName);
        sb.append(bean.getUesStartDate() + "至" + bean.getUesEndDate());

        //快到期；
        if (bean.isExpireStatus())
        {
            String arrDate = " 快过期";
            SpannableString ss = new SpannableString(CommonUtil.ToDBC(sb.toString()) + arrDate);
            ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_cc0000)),
                    ss.length() - arrDate.length(), ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.tvCouponIntro.setText(ss);
        } else
        {
            holder.tvCouponIntro.setText(CommonUtil.ToDBC(sb.toString()));
        }

        holder.tvUseIntegral.setText("使用时段: " + bean.getUseDateDesc() + " | " + bean.getUseTimeDesc());
        holder.ivCreateCommand.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickShareContent = tempShareContent;

                LogUtil.i("要分享的内容=" + clickShareContent);
                if (dataHandler != null)
                {
                    sendMsg(WHAT_CREATE_COMMAND, bean);
                }
            }
        });

        holder.panelUse.setClickable(isEnable);
        if (isEnable)
        {
            final int finalFlag = flag;
            holder.ivCouponState.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    userCoupon(position, finalFlag);
                }
            });
            holder.tvUseState.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    userCoupon(position, finalFlag);
                }
            });
        }
        return holder.root;
    }

    /**
     * 使用优惠券；
     *
     * @param position
     * @param finalFlag
     */
    private void userCoupon(int position, int finalFlag)
    {
        if (dataHandler != null)
        {
            //优惠券类型；0:在某个行业指定商家使用；1：全部商品使用；2：某个行业指定商品使用；
            if (dataHandler != null)
            {
                Message msg = dataHandler.obtainMessage(WHAT_USE);
                msg.obj = mList.get(position);
                msg.arg1 = finalFlag;
                dataHandler.sendMessage(msg);
            }
        }
    }

    /**
     * 发送消息；
     *
     * @param what
     * @param bean
     */

    public void sendMsg(int what, CouponsHomeBean bean)
    {
        if (dataHandler != null)
        {
            Message msg = dataHandler.obtainMessage(what);
            msg.obj = bean;
            dataHandler.sendMessage(msg);
        }
    }

    public void setHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
    }

    public class ViewHolder
    {
        @ViewInject(R.id.tvActName)
        TextView tvActName;
        @ViewInject(R.id.tvMaxAmount)
        TextView tvMaxAmount;
        @ViewInject(R.id.tvChannelName)
        TextView tvChannelName;
        @ViewInject(R.id.tvCouponIntro)
        TextView tvCouponIntro;

        @ViewInject(R.id.ivCreateCommand)
        View ivCreateCommand;
        @ViewInject(R.id.tvUseIntegral)
        TextView tvUseIntegral;
        @ViewInject(R.id.tvUseState)
        TextView tvUseState;
        @ViewInject(R.id.panelUse)
        View panelUse;
        @ViewInject(R.id.ivCouponState)
        ImageView ivCouponState;
        public final View root;

        public ViewHolder(View root)
        {
            this.root = root;
            x.view().inject(this, root);
        }
    }
}
