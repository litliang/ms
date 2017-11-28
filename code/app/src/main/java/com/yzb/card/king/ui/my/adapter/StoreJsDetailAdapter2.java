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

import java.util.List;

/**
 * 类 名： 商店积分明细查询
 * 作 者： gaolei
 * 日 期：2017年01月03日
 * 描 述：商店积分明细查询
 */

public class StoreJsDetailAdapter2 extends RecyclerView.Adapter{


    private Context context;
    private List<GiftCardDetailBean> list;
    private RecyclerView recyclerView;

    public StoreJsDetailAdapter2(Context context, List<GiftCardDetailBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return R.layout.load_more_layout;
        } else {
            return R.layout.item_billing_detail_new;
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
            return new StoreJsDetailAdapter2.LoadMoreVH(view);
        } else {
            return new StoreJsDetailAdapter2.BillingDetailViewHolder(view);
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


        if (holder instanceof StoreJsDetailAdapter2.BillingDetailViewHolder) {
            List<GiftCardDetailBean.DetailListBean> item_lsit=list.get(position).getDetailList();
            if (list.get(position).getMonthDesc()!=null && !TextUtils.isEmpty(list.get(position).getMonthDesc()))
            {
                ((BillingDetailViewHolder)holder).title_date.setText(list.get(position).getMonthDesc());
            }
            if (item_lsit != null) {
                StoreJsDetailAdapter2.BillingDetailAdapter2 adapter2 = new StoreJsDetailAdapter2.BillingDetailAdapter2(context, item_lsit);
                //上下文 流向  是否反转布局(数据从下面往上面走,eg 聊天的记录)
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true);
                ((BillingDetailViewHolder)holder).recycler_View.setLayoutManager(linearLayoutManager);
                ((BillingDetailViewHolder)holder).recycler_View.setAdapter(adapter2);
            }

            if(list.size() == position+1){
                ((BillingDetailViewHolder)holder).tvOne.setVisibility(View.INVISIBLE);
            }else{
                ((BillingDetailViewHolder)holder).tvOne.setVisibility(View.VISIBLE);
            }

        } else {


            listener.onLoad();
        }



    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public static class BillingDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView title_date;
        private WholeRecyclerView recycler_View;
        private View tvOne;

        public BillingDetailViewHolder(View itemView) {
            super(itemView);
            title_date = (TextView) itemView.findViewById(R.id.billing_detail_titledate_txt);
            recycler_View = (WholeRecyclerView) itemView.findViewById(R.id.item_recyclerview);

            tvOne = itemView.findViewById(R.id.tvOne);
        }
    }

    public static class BillingDetailViewHolder2 extends BillingDetailAdapter.BillingDetailViewHolder {

        private TextView date, type_card, custom, money_num, add_del;
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

        }
    }


    public class BillingDetailAdapter2 extends RecyclerView.Adapter<StoreJsDetailAdapter2.BillingDetailViewHolder2>{

        private Context context;
        private List<GiftCardDetailBean.DetailListBean> item_ist;
        private int size;

        public BillingDetailAdapter2(Context context, List<GiftCardDetailBean.DetailListBean> item_ist) {
            this.context = context;
            this.item_ist = item_ist;
            size = item_ist.size();
        }

        @Override
        public StoreJsDetailAdapter2.BillingDetailViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_jf_detail, parent, false);

            return new StoreJsDetailAdapter2.BillingDetailViewHolder2(view);
        }

        @Override
        public void onBindViewHolder(StoreJsDetailAdapter2.BillingDetailViewHolder2 holder, int position) {

            String tradeTime = item_ist.get(position).getTradeTime();
            String[] split = tradeTime.split(" ");
            String time = split[1];
            String[] split2 = time.split(":");
            holder.date.setText("" + split[0] + "\n" + split2[0] + ":" + split2[1]);
            if (item_ist.get(position).getTradeDesc() != null && !TextUtils.isEmpty(item_ist.get(position).getTradeDesc())) {
                holder.custom.setText(item_ist.get(position).getTradeDesc());
            }

            if (item_ist.get(position).getPaymentsStatus().equals("1")){
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

            if(size == position+1){
                holder.vOne.setVisibility(View.INVISIBLE);
            }else{
                holder.vOne.setVisibility(View.VISIBLE);
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

    StoreJsDetailAdapter2.OnClickListenerBillingDetail listener;

    public void setOnClickListenerBillingDetail(StoreJsDetailAdapter2.OnClickListenerBillingDetail listener) {
        this.listener = listener;
    }

    public interface OnClickListenerBillingDetail {
        void onLoad();
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
