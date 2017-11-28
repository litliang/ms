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
import com.yzb.card.king.ui.gift.adapter.BillingDetailAdapter;
import com.yzb.card.king.ui.gift.bean.GiftCardDetailBean;

import java.util.List;

/**
 * 类 名： 商店积分明细查询
 * 作 者： gaolei
 * 日 期：2017年01月03日
 * 描 述：商店积分明细查询
 */

public class StoreJsDetailAdapter extends RecyclerView.Adapter {


    private Context context;
    private List<GiftCardDetailBean> list;
    private RecyclerView recyclerView;

    public StoreJsDetailAdapter(Context context, List<GiftCardDetailBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return R.layout.load_more_layout;
        } else {
            return R.layout.item_billing_detail;
        }
    }

    public void addData(List<GiftCardDetailBean> data) {
        list.addAll(data);
        notifyDataSetChanged();//添加数据后通知 adpter 更新
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (viewType == R.layout.load_more_layout) {
            return new StoreJsDetailAdapter.LoadMoreVH(view);
        } else {
            return new StoreJsDetailAdapter.BillingDetailViewHolder(view);
        }
    }

    //底部加载更多item的viewholder
    static class LoadMoreVH extends RecyclerView.ViewHolder {

        public LoadMoreVH(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof StoreJsDetailAdapter.BillingDetailViewHolder) {
            List<GiftCardDetailBean.DetailListBean> item_lsit = list.get(position).getDetailList();
//        Calendar timeNow = Calendar.getInstance();
//        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//        String     = format1.format(timeNow.getTime());

            if (list.get(position).getMonthDesc() != null && !TextUtils.isEmpty(list.get(position).getMonthDesc())) {
                ((BillingDetailViewHolder) holder).title_date.setText(list.get(position).getMonthDesc());
            }
            ((BillingDetailViewHolder) holder).pay_out.setText(list.get(position).getPayAmount() + "");
            ((BillingDetailViewHolder) holder).pay_in.setText(list.get(position).getIncomeAmount() + "");
            ViewGroup.LayoutParams params = ((BillingDetailViewHolder) holder).recycler_View.getLayoutParams();

            if (item_lsit != null) {
                params.height = item_lsit.size() * dip2px(50);
                ((BillingDetailViewHolder) holder).recycler_View.setLayoutParams(params);
                StoreJsDetailAdapter.BillingDetailAdapter2 adapter2 = new StoreJsDetailAdapter.BillingDetailAdapter2(context, item_lsit);
                //上下文 流向  是否反转布局(数据从下面往上面走,eg 聊天的记录)
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
                ((BillingDetailViewHolder) holder).recycler_View.setLayoutManager(linearLayoutManager);
                ((BillingDetailViewHolder) holder).recycler_View.setAdapter(adapter2);
            }
        } else {
            listener.OnLoad();
        }


    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public static class BillingDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView title_date, pay_out, pay_in;
        private RecyclerView recycler_View;

        public BillingDetailViewHolder(View itemView) {
            super(itemView);
            title_date = (TextView) itemView.findViewById(R.id.billing_detail_titledate_txt);
            pay_in = (TextView) itemView.findViewById(R.id.billing_detail_payin_txt);
            pay_out = (TextView) itemView.findViewById(R.id.billing_detail_payout_txt);
            recycler_View = (RecyclerView) itemView.findViewById(R.id.item_recyclerview);
        }
    }

    public static class BillingDetailViewHolder2 extends BillingDetailAdapter.BillingDetailViewHolder {

        private TextView date, type_card, custom, money_num, add_del;
        private ImageView icon;

        public BillingDetailViewHolder2(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.billing_detail_date);
            add_del = (TextView) itemView.findViewById(R.id.billing_detail_add_txt);
            icon = (ImageView) itemView.findViewById(R.id.billing_detail_cicle_pay);
            type_card = (TextView) itemView.findViewById(R.id.billing_detail_type_txt);
            custom = (TextView) itemView.findViewById(R.id.billing_detail_customer_txt);
            money_num = (TextView) itemView.findViewById(R.id.billing_detail_money);

        }
    }


    public class BillingDetailAdapter2 extends RecyclerView.Adapter<StoreJsDetailAdapter.BillingDetailViewHolder2> {

        private Context context;
        private List<GiftCardDetailBean.DetailListBean> item_ist;

        public BillingDetailAdapter2(Context context, List<GiftCardDetailBean.DetailListBean> item_ist) {
            this.context = context;
            this.item_ist = item_ist;
        }

        @Override
        public StoreJsDetailAdapter.BillingDetailViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_jf_detail, parent, false);

            return new StoreJsDetailAdapter.BillingDetailViewHolder2(view);
        }

        @Override
        public void onBindViewHolder(StoreJsDetailAdapter.BillingDetailViewHolder2 holder, int position) {

            //Log.d("gl","date = " + "\n" + item_ist.get(position).getTradeTime());
            //Log.d("gl"," item_ist.get(position).getTradeTime() = " +  item_ist.get(position).getTradeTime());

            String tradeTime = item_ist.get(position).getTradeTime();
            String[] split = tradeTime.split(" ");

            holder.date.setText("" + split[0] + "\n" + split[1]);
            if (item_ist.get(position).getTradeDesc() != null && !TextUtils.isEmpty(item_ist.get(position).getTradeDesc())) {
                holder.custom.setText(item_ist.get(position).getTradeDesc());
            }

            if (item_ist.get(position).getPaymentsStatus().equals("1")) {
                holder.icon.setImageResource(R.mipmap.icon_billing_detail_recycler);
                holder.money_num.setText("" + item_ist.get(position).getTradeAmount());
            } else {
                holder.icon.setImageResource(R.mipmap.icon_billing_detail_send);
                holder.money_num.setText("-" + item_ist.get(position).getTradeAmount());
            }

            if (item_ist.get(position).getTradeTypeDesc() != null && !TextUtils.isEmpty(item_ist.get(position).getTradeTypeDesc())) {
                holder.type_card.setText("" + item_ist.get(position).getTradeTypeDesc());
            }

            if (item_ist.get(position).getTradeDesc() != null && !TextUtils.isEmpty(item_ist.get(position).getTradeDesc())) {
                holder.type_card.setText("" + item_ist.get(position).getTradeDesc());
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

    StoreJsDetailAdapter.OnClickListenerBillingDetail listener;

    public void setOnClickListenerBillingDetail(StoreJsDetailAdapter.OnClickListenerBillingDetail listener) {
        this.listener = listener;
    }

    public interface OnClickListenerBillingDetail {
        void OnLoad();
    }

    /**
     * 将dp转化为px
     */
    private int dip2px(float dip) {
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
