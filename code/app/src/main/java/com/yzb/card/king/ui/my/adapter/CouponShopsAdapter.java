package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.CouponShopBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 功能：优惠券列表；
 *
 * @author:gengqiyun
 * @date: 2016/12/19
 */
public class CouponShopsAdapter extends BaseListAdapter<CouponShopBean>
{
    public static final int WHAT_GET = 0x001;
    private Handler handler;
    private ImageOptions options;

    public CouponShopsAdapter(Context context)
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
            convertView = mInflater.inflate(R.layout.item_coupon_shop, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (MyViewHolder) convertView.getTag();
        }
        final CouponShopBean bean = getItem(position);

        //
        x.image().bind(holder.ivLogo, ServiceDispatcher.getImageUrl(bean.getShopImageCode()), options);

        holder.tvShopName.setText(bean.getShopName());
        holder.tvFullCut.setText("满" + Utils.subZeroAndDot(bean.getFullAmount() + "")
                + "减" + Utils.subZeroAndDot(bean.getCutAmount() + ""));

        holder.starBar.setStarMarkAndSore(bean.getActHot() / 2);
        holder.tvScore.setText(bean.getActHot() + "分");

        holder.tvAmount.setText("¥" + bean.getCutAmount());
        holder.tvShopAddress.setText(bean.getShopRegion());

        holder.tvGet.setText(bean.isReceiveStatus() ? "已领取" : "立即领取");

        if (!bean.isReceiveStatus()){
            holder.tvGet.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (handler != null)
                    {
                        Message msg = handler.obtainMessage(WHAT_GET);
                        msg.arg1 = position;
                        handler.sendMessage(msg);
                    }
                }
            });
        }
        return convertView;
    }

    public void setHandler(Handler handler)
    {
        this.handler = handler;
    }

    public CouponShopBean getItemById(String yhqId)
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
