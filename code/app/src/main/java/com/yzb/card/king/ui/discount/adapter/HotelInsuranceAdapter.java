package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.base.BaseRecyAdapter;
import com.yzb.card.king.ui.discount.bean.Insurance;

import java.util.List;

/**
 * 类  名：酒店订单保险适配器
 * 作  者：Li Yubing
 * 日  期：2017/8/7
 * 描  述：
 */
public class HotelInsuranceAdapter extends BaseRecyAdapter<Insurance>
{
    private HotelInsuranceAdapter.IStateCallBack callBack;

    private float orderAmount = 0f; //订单金额；

    public void setOrderAmount(float orderAmount)
    {
        this.orderAmount = orderAmount;
        notifyDataSetChanged();
    }

    public HotelInsuranceAdapter(Context context, List<Insurance> datas)
    {
        super(datas, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.item_hotel_order_insurance, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {
          final   MyViewHolder viewHolder = (MyViewHolder) holder;

            final Insurance insurance = mList.get(position);
            viewHolder.tvTitle.setText(insurance.shortName);
            viewHolder.tvDesc.setText(insurance.insuranceSummary);

            if(position+1==getItemCount()){
                viewHolder.lineItem.setVisibility(View.GONE);
            }else {
                viewHolder.lineItem.setVisibility(View.VISIBLE);
            }

            //初始化状态；
            viewHolder.slideButton.setToggleState(insurance.isSelected ?
                    SlideButton.ToggleState.open : SlideButton.ToggleState.close);
            viewHolder.slideButton.setEnable(false);
            if(insurance.isSelected){

                viewHolder.llBaoxianInfo.setVisibility(View.VISIBLE);
            }else {
                viewHolder.llBaoxianInfo.setVisibility(View.GONE);
            }
            viewHolder.slideButton.setOnToggleStateChangeListener(new SlideButton.OnToggleStateChangeListener()
            {
                @Override
                public void onToggleStateChange(SlideButton.ToggleState state)
                {
                    insurance.isSelected = state == SlideButton.ToggleState.open ? true : false;

                    if(insurance.isSelected){

                        viewHolder.llBaoxianInfo.setVisibility(View.VISIBLE);
                    }else {
                        viewHolder.llBaoxianInfo.setVisibility(View.GONE);
                    }

                    if (callBack != null)
                    {
                        callBack.stateChange();
                    }
                }
            });
//
//            //说明；
//            if (TextUtils.isEmpty(insurance.getInsuranceUrl()))
//            {
//                viewHolder.tvIntroduce.setVisibility(View.GONE);
//            } else
//            {
//                viewHolder.tvIntroduce.setVisibility(View.VISIBLE);
//                viewHolder.tvIntroduce.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        if (callBack != null)
//                        {
//                            callBack.goIntroducePage(insurance.getInsuranceUrl());
//                        }
//                    }
//                });
//            }
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvTitle;

        TextView tvDesc;

        TextView tvPrice;

        View tvIntroduce;

        SlideButton slideButton;

        LinearLayout llBaoxianInfo;

        View lineItem;

        public MyViewHolder(View view)
        {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            tvIntroduce = view.findViewById(R.id.tvIntroduce);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            slideButton = (SlideButton) view.findViewById(R.id.slideButton);
            llBaoxianInfo = (LinearLayout) view.findViewById(R.id.llBaoxianInfo);
            lineItem = view.findViewById(R.id.lineItem);
        }
    }

    public void setStateChangeCallBack(HotelInsuranceAdapter.IStateCallBack callBack)
    {
        this.callBack = callBack;
    }

    /**
     * 状态切换回调；
     */
    public interface IStateCallBack
    {
        void stateChange();

        void goIntroducePage(String url);
    }
}

