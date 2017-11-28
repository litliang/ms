package com.yzb.card.king.ui.credit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.CommonUtil;

import java.text.NumberFormat;
import java.util.List;

/**
 * 类名： PayMethodAdapter
 * 作者： Lei Chao.
 * 日期： 2016-09-12
 * 描述： 选择付款方式适配器
 */
public class PayMethodAdapter extends RecyclerView.Adapter
{

    private List<PayMethod> dataList;

    private static final int TYPE_FOOTER = 1;

    public static final int CLICK_TYPE_ADD = 3;

    public static final int CLICK_TYPE_CHOICE = 4;

    private static final int TYPE_NORMAL = 0;

    private View mFooterView;

    private Context mContext;

    private LayoutInflater inflater;

    public PayMethodAdapter(List<PayMethod> dataList, Context context)
    {
        this.mContext = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    public void setFooterView(View footerView)
    {
        this.mFooterView = footerView;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (dataList.size() == 0)
        {
            return TYPE_FOOTER;
        }
        if (mFooterView == null)
        {
            return TYPE_NORMAL;
        }
        if (position == getItemCount() - 1)
        {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_NORMAL)
        {
            return new ViewHolder1(inflater.inflate(R.layout.item_list_pay_method, parent, false));
        } else
        {
            return new FooterView(inflater.inflate(R.layout.footer_list_pay_method, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof ViewHolder1)
        {
            ViewHolder1 viewHolder = (ViewHolder1) holder;

            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mFooterViewOnClick != null)
                    {
                        mFooterViewOnClick.onFooterClick(CLICK_TYPE_CHOICE, holder.getLayoutPosition());
                    }
                }
            });

            PayMethod bean = this.dataList.get(position);

            if (AppConstant.ACCOUNT_TYPE_BALANCE.equals(bean.getAccountType()))
            {
                //账户余额
                //CommonUtil.fitImageForView(viewHolder.ivbank, 24);
                viewHolder.ivbank.setBackgroundResource(R.mipmap.icon_add_card_logo);
            } else if (AppConstant.ACCOUNT_TYPE_BANK.equals(bean.getAccountType()))//银行卡；
            {
                //cardType;  银行卡类型 1、借记卡 2信用卡
                CommonUtil.downloadImageForView(ServiceDispatcher.getImageUrl(bean.getLogo()), viewHolder.ivbank, 24);
            } else if (AppConstant.ACCOUNT_TYPE_BONUS.equals(bean.getAccountType()))
            {    //红包账户
                viewHolder.ivbank.setBackgroundResource(R.mipmap.icon_jfb_hbye);
            } else if (AppConstant.ACCOUNT_TYPE_GIFT_CARD.equals(bean.getAccountType()))
            {//礼品卡账户
                viewHolder.ivbank.setBackgroundResource(R.mipmap.icon_jfb_lpk);
            }

            viewHolder.tvtitle.setText(!TextUtils.isEmpty(bean.getBankName()) ? bean.getBankName() : bean.getTypeName());

            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            viewHolder.tvcontent.setText(mContext.getString(R.string.credit_repay_available_ye,
                    nf.format(bean.getLimitDay())));
        }

        if (holder instanceof FooterView)
        {
            ((FooterView) holder).itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mFooterViewOnClick != null)
                    {
                        mFooterViewOnClick.onFooterClick(CLICK_TYPE_ADD, holder.getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface FooterViewOnClick
    {
        void onFooterClick(int CLICK_TYPE, int position);
    }

    private FooterViewOnClick mFooterViewOnClick;

    public void setmFooterViewOnClick(FooterViewOnClick footerViewOnClick)
    {
        this.mFooterViewOnClick = footerViewOnClick;
    }

    @Override
    public int getItemCount()
    {
        if (mFooterView == null)
        {
            return this.dataList.size() == 0 ? 0 : this.dataList.size();
        } else
        {
            return this.dataList.size() == 0 ? 1 : this.dataList.size() + 1;
        }
    }


    static class ViewHolder1 extends RecyclerView.ViewHolder
    {

        ImageView ivbank;
        TextView tvtitle;
        TextView tvcontent;


        public ViewHolder1(View root)
        {
            super(root);
            ivbank = (ImageView) root.findViewById(R.id.iv_bank);
            tvtitle = (TextView) root.findViewById(R.id.tv_title);
            tvcontent = (TextView) root.findViewById(R.id.tv_content);
        }
    }

    static class FooterView extends RecyclerView.ViewHolder
    {

        public FooterView(View itemView)
        {
            super(itemView);
        }
    }

}