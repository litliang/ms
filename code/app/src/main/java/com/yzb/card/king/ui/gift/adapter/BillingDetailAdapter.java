package com.yzb.card.king.ui.gift.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.gift.bean.GiftCardDetailBean;
import com.yzb.card.king.util.Utils;

import java.util.List;

/**
 * 类 名： 礼品卡明细适配
 * 作 者： gaolei
 * 日 期：2016年12月22日
 * 描 述：礼品卡明细适配
 */

public class BillingDetailAdapter extends RecyclerView.Adapter {

    private static Context context;
    private List<GiftCardDetailBean> list;
    private RecyclerView recyclerView;

    public BillingDetailAdapter(Context context, List<GiftCardDetailBean> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == list.size()) {
            return R.layout.load_more_layout;
        } else {
            return R.layout.item_billing_detail;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (viewType == R.layout.load_more_layout) {
            return new LoadMoreVH(view);
        } else {
            return new BillingDetailViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if (holder instanceof BillingDetailViewHolder) {
            ((BillingDetailViewHolder) holder).bind(list, position, context);
        } else if (holder instanceof LoadMoreVH) {
            listener.onLoad();
        }

    }


    //底部加载更多item的viewholder
    static class LoadMoreVH extends RecyclerView.ViewHolder {

        public LoadMoreVH(View itemView)
        {
            super(itemView);
        }
    }


    @Override
    public int getItemCount()
    {
        return list.size() + 1;
    }


    public static class BillingDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView title_date, pay_out, pay_in, tvOne;
        private WholeRecyclerView recycler_View;

        public BillingDetailViewHolder(View itemView)
        {
            super(itemView);
            title_date = (TextView) itemView.findViewById(R.id.billing_detail_titledate_txt);
            pay_in = (TextView) itemView.findViewById(R.id.billing_detail_payin_txt);
            pay_out = (TextView) itemView.findViewById(R.id.billing_detail_payout_txt);
            recycler_View = (WholeRecyclerView) itemView.findViewById(R.id.item_recyclerview);
            tvOne = (TextView) itemView.findViewById(R.id.tvOne);
        }

        public void bind(List<GiftCardDetailBean> list, int position, Context context)
        {
            List<GiftCardDetailBean.DetailListBean> item_lsit = list.get(position).getDetailList();
            String monthStr = list.get(position).getMonthDesc();
            if(TextUtils.isEmpty(monthStr) || "本月".equals(monthStr)){

                title_date.setText(monthStr);

            }else{
                long datelong = Utils.toTimestamp(list.get(position).getMonthDesc(),8);
                title_date.setText(Utils.toData(datelong,17));
            }

            if (list.size() == position + 1) {
                tvOne.setVisibility(View.INVISIBLE);
            } else {
                tvOne.setVisibility(View.VISIBLE);
            }
            pay_out.setText(list.get(position).getPayAmount() + "");
            pay_in.setText(list.get(position).getIncomeAmount() + "");
            //      Log.d("gl","==================item_list" + item_lsit);
            if (item_lsit != null) {
                BillingDetailAdapter2 adapter2 = new BillingDetailAdapter2(context, item_lsit);
                //上下文 流向  是否反转布局(数据从下面往上面走,eg 聊天的记录)
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                recycler_View.setLayoutManager(linearLayoutManager);
                recycler_View.setAdapter(adapter2);
            }
        }

    }

    public static class BillingDetailViewHolder2 extends BillingDetailViewHolder {

        private TextView date, type_card, custom, money_num, add_del, time;
        private ImageView icon;
        private View vOne;

        public BillingDetailViewHolder2(View itemView)
        {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.billing_detail_date);
            add_del = (TextView) itemView.findViewById(R.id.billing_detail_add_txt);
            icon = (ImageView) itemView.findViewById(R.id.billing_detail_cicle_pay);
            type_card = (TextView) itemView.findViewById(R.id.billing_detail_type_txt);
            custom = (TextView) itemView.findViewById(R.id.billing_detail_customer_txt);
            money_num = (TextView) itemView.findViewById(R.id.billing_detail_money);
            vOne = itemView.findViewById(R.id.vOne);
            time = (TextView) itemView.findViewById(R.id.billing_detail_time);

        }
    }


    public static class BillingDetailAdapter2 extends RecyclerView.Adapter<BillingDetailViewHolder2> {

        private Context context;
        private List<GiftCardDetailBean.DetailListBean> item_ist;
        int size;

        public BillingDetailAdapter2(Context context, List<GiftCardDetailBean.DetailListBean> item_ist)
        {
            this.context = context;
            this.item_ist = item_ist;
            size = item_ist.size();
        }

        @Override
        public BillingDetailViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billing_detail2, parent, false);

            return new BillingDetailViewHolder2(view);
        }

        @Override
        public void onBindViewHolder(BillingDetailViewHolder2 holder, int position)
        {


            String tradeTime = item_ist.get(position).getTradeTime();
            long dataLong = Utils.toTimestamp(tradeTime, 1);
            holder.date.setText(Utils.toData(dataLong, 12));
            holder.time.setText(Utils.toData(dataLong, 3));

            holder.custom.setText(item_ist.get(position).getTradeDesc());
            if (item_ist.get(position).getPaymentsStatus().equals("1")) {
                holder.icon.setImageResource(R.mipmap.icon_billing_detail_recycler);
                holder.money_num.setText("" + item_ist.get(position).getTradeAmount());
            } else {
                holder.icon.setImageResource(R.mipmap.icon_billing_detail_send);
                holder.money_num.setText("" + item_ist.get(position).getTradeAmount());
            }

            if (item_ist.get(position).getTradeTypeDesc() != null && !TextUtils.isEmpty(item_ist.get(position).getTradeTypeDesc())) {
                holder.type_card.setText("" + item_ist.get(position).getTradeTypeDesc());
            }

            if (item_ist.get(position).getTradeDesc() != null && !TextUtils.isEmpty(item_ist.get(position).getTradeDesc())) {
                holder.type_card.setText("" + item_ist.get(position).getTradeDesc());
            }

            if (size == position + 1) {
                holder.vOne.setVisibility(View.INVISIBLE);
            } else {
                holder.vOne.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public int getItemCount()
        {
            return item_ist.size();
        }


    }

    public void addData(List<GiftCardDetailBean> data)
    {
        list.addAll(data);
        notifyDataSetChanged();//添加数据后通知 adpter 更新
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    OnClickListenerBillingDetail listener;

    public void setOnClickListenerBillingDetail(OnClickListenerBillingDetail listener)
    {
        this.listener = listener;
    }

    public interface OnClickListenerBillingDetail {
        void onLoad();
    }

    /**
     * 将dp转化为px
     */
    private static int dip2px(float dip)
    {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return (int) (v + 0.5f);
    }


    /**
     * 屏幕高度
     */
    private int basicParamInit()
    {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;

    }
}
