package com.yzb.card.king.ui.my.adapter;

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
import com.yzb.card.king.ui.gift.adapter.BillingDetailAdapter;
import com.yzb.card.king.ui.gift.bean.GiftCardDetailBean;
import com.yzb.card.king.util.Utils;

import java.util.List;

/**
 * 类 名： 现金明细适配
 * 作 者： gaolei
 * 日 期：2016年12月23日
 * 描 述：现金明细适配
 */
public class BillingCrashMoneyAdapter extends RecyclerView.Adapter {


    private static Context context;
    private List<GiftCardDetailBean> list;
    private RecyclerView recyclerView;

    public BillingCrashMoneyAdapter(Context context, List<GiftCardDetailBean> list) {
        this.context = context;
        this.list = list;
    }

    public void addData(List<GiftCardDetailBean> data) {
        list.addAll(data);
        notifyDataSetChanged();//添加数据后通知 adpter 更新
    }

    @Override
    public int getItemViewType(int position) {

        if (position == list.size()) {
            return R.layout.load_more_layout;
        } else {
            return R.layout.item_crashmoney_bill1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (viewType == R.layout.load_more_layout) {
            return new BillingCrashMoneyAdapter.LoadMoreVH(view);
        } else {
            return new BillingCrashMoneyAdapter.BillingDetailViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof BillingCrashMoneyAdapter.BillingDetailViewHolder) {
            ((BillingDetailViewHolder)holder).bind(list,position,context);
        } else if (holder instanceof BillingCrashMoneyAdapter.LoadMoreVH) {
            listener.onLoad();
        }

    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public static class BillingDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView title_date, pay_out, pay_in,tvOne;
        private WholeRecyclerView recycler_View;

        public BillingDetailViewHolder(View itemView) {
            super(itemView);
            title_date = (TextView) itemView.findViewById(R.id.billing_detail_titledate_txt);
            pay_in = (TextView) itemView.findViewById(R.id.billing_detail_payin_txt);
            pay_out = (TextView) itemView.findViewById(R.id.billing_detail_payout_txt);
            recycler_View = (WholeRecyclerView) itemView.findViewById(R.id.item_recyclerview);
            tvOne = (TextView) itemView.findViewById(R.id.tvOne);
        }

        public void bind(List<GiftCardDetailBean> list, int position, Context context) {
            List<GiftCardDetailBean.DetailListBean> item_lsit = list.get(position).getDetailList();
            if (list.get(position).getMonthDesc() != null && !TextUtils.isEmpty(list.get(position).getMonthDesc())) { //list.get(position).getMonthDesc().equals("本月")
                title_date.setText(list.get(position).getMonthDesc());
            } else {
                title_date.setText(list.get(position).getMonthDesc());
            }
            pay_out.setText(list.get(position).getPayAmount() + "");
            pay_in.setText(list.get(position).getIncomeAmount() + "");
            if(list.size() == position+1){
                tvOne.setVisibility(View.INVISIBLE);
            }else{
                tvOne.setVisibility(View.VISIBLE);
            }
            //      Log.d("gl","==================item_list" + item_lsit);
            if (item_lsit != null) {
                BillingCrashMoneyAdapter.BillingDetailAdapter2 adapter2 = new BillingCrashMoneyAdapter.BillingDetailAdapter2(context, item_lsit);
                //上下文 流向  是否反转布局(数据从下面往上面走,eg 聊天的记录)
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                recycler_View.setLayoutManager(linearLayoutManager);
                recycler_View.setAdapter(adapter2);
            }
        }

    }

    //底部加载更多item的viewholder
    static class LoadMoreVH extends RecyclerView.ViewHolder {

        public LoadMoreVH(View itemView) {
            super(itemView);
        }
    }


    public static class BillingDetailViewHolder2 extends BillingDetailAdapter.BillingDetailViewHolder {

        private TextView date, type_card, custom, money_num, add_del,time;
        private ImageView icon;
        private View vOne;

        public BillingDetailViewHolder2(View itemView) {
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


    public static class BillingDetailAdapter2 extends RecyclerView.Adapter<BillingCrashMoneyAdapter.BillingDetailViewHolder2> {

        private Context context;
        private List<GiftCardDetailBean.DetailListBean> item_ist;
        private int size ;
        public BillingDetailAdapter2(Context context, List<GiftCardDetailBean.DetailListBean> item_ist) {
            this.context = context;
            this.item_ist = item_ist;
            size = item_ist.size();
        }

        @Override
        public BillingCrashMoneyAdapter.BillingDetailViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billing_detail2, parent, false);

            return new BillingCrashMoneyAdapter.BillingDetailViewHolder2(view);
        }

        @Override
        public void onBindViewHolder(BillingCrashMoneyAdapter.BillingDetailViewHolder2 holder, int position) {

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
            if(size==1){

                holder.vOne.setVisibility(View.INVISIBLE);
            }else{

                if(position == size -1){

                    holder.vOne.setVisibility(View.INVISIBLE);
                }else{

                    holder.vOne.setVisibility(View.VISIBLE);
                }

            }
        }

        @Override
        public int getItemCount() {
            return item_ist.size();
        }

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    BillingCrashMoneyAdapter.OnClickListenerBillingDetail listener;

    public void setOnClickListenerBillingDetail(BillingCrashMoneyAdapter.OnClickListenerBillingDetail listener) {
        this.listener = listener;
    }

    public interface OnClickListenerBillingDetail {
        void onLoad();
    }

    /**
     * 将dp转化为px
     */
    private static int dip2px(float dip) {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return (int) (v + 0.5f);
    }


    /**
     * 屏幕高度
     */
    private int basicParamInit() {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;

    }
}
