package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseRecyAdapter;
import com.yzb.card.king.bean.OverbalanceGoodsBean;
import com.yzb.card.king.util.Utils;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/7
 * 描  述：
 */
public class OverbalanceGoodsItemAdapter  extends BaseRecyAdapter<OverbalanceGoodsBean>
{
    private IStateCallBack callBack;

    private float orderAmount = 0f; //订单金额；

    public void setOrderAmount(float orderAmount)
    {
        this.orderAmount = orderAmount;
        notifyDataSetChanged();
    }

    public OverbalanceGoodsItemAdapter(Context context, List<OverbalanceGoodsBean> datas)
    {
        super(datas, context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.item_hotel_overbalance_goods, parent, false),callBack);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {
            final MyViewHolder viewHolder = (MyViewHolder) holder;

            final OverbalanceGoodsBean insurance = mList.get(position);

            viewHolder.initData(insurance);

            if(position+1==getItemCount()){
                viewHolder.lineItem.setVisibility(View.GONE);
            }else {
                viewHolder.lineItem.setVisibility(View.VISIBLE);
            }

        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvTitle;

        TextView tvGoodsPrice;

        LinearLayout tvSubstract;

        LinearLayout tvAdd;

        TextView tvNumber;

        OverbalanceGoodsBean insurance;

        TextView tvUnit;

        View lineItem;

        IStateCallBack callBack;

        public MyViewHolder(View view, IStateCallBack callBack)
        {
            super(view);
            this.callBack = callBack;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);

            tvGoodsPrice = (TextView) view.findViewById(R.id.tvGoodsPrice);

            tvSubstract = (LinearLayout) view.findViewById(R.id.tvSubstract);

            tvAdd = (LinearLayout) view.findViewById(R.id.tvAdd);

            tvNumber = (TextView) view.findViewById(R.id.tvNumber);

            lineItem = view.findViewById(R.id.lineItem);

            tvUnit = (TextView) view.findViewById(R.id.tvUnit);

            tvSubstract.setOnClickListener(this);

            tvAdd.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId()){
                case R.id.tvAdd:
                    int a = insurance.number;

                    if(a<100){

                        a= a+1;

                        insurance.number = a;

                        tvNumber.setText(a+"");

                        if(callBack!=null){
                            callBack.updatePriceAction(insurance);
                        }
                    }

                    break;
                case R.id.tvSubstract:

                    int b = insurance.number;

                    if(b>1){

                        b= b-1;

                        insurance.number =b;

                        tvNumber.setText(b+"");

                        if(callBack!=null){
                            callBack.updatePriceAction(insurance);
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        public void initData(OverbalanceGoodsBean insurance)
        {
            this.insurance = insurance;
            tvTitle.setText(insurance.getActName());
            tvGoodsPrice.setText(Utils.subZeroAndDot(insurance.getPrice()+""));
            tvNumber.setText(insurance.number+"");
            tvUnit.setText(insurance.getUnit());
        }
    }

    public void setStateChangeCallBack(IStateCallBack callBack)
    {
        this.callBack = callBack;
    }

    /**
     * 状态切换回调；
     */
    public interface IStateCallBack
    {
        void updatePriceAction(OverbalanceGoodsBean bean);

    }
}

