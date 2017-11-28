package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.base.BaseRecyAdapter;
import com.yzb.card.king.ui.discount.bean.Insurance;
import com.yzb.card.king.util.Utils;

import java.util.List;

/**
 * @author gengqiyun
 * @date 2016/9/28.
 * 保险列表
 */
public class PlaneBxAdapter extends BaseRecyAdapter<Insurance>
{
    private IStateCallBack callBack;
    private float orderAmount = 0f; //订单金额；

    public void setOrderAmount(float orderAmount)
    {
        this.orderAmount = orderAmount;
        notifyDataSetChanged();
    }

    public PlaneBxAdapter(Context context, List<Insurance> datas)
    {
        super(datas, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.plane_bx_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder viewHolder = (MyViewHolder) holder;

            final Insurance insurance = mList.get(position);

            viewHolder.tvTitle.setText(insurance.shortName);
            viewHolder.tvDesc.setText(insurance.insuranceSummary);

            //金额类型  1固定金额、2总额比例
            String actuAmount = insurance.getActuAmount(orderAmount);
            viewHolder.tvPrice.setText("¥" + Utils.subZeroAndDot(actuAmount) );
            //初始化状态；
            viewHolder.slideButton.setToggleState(insurance.isSelected ?
                    SlideButton.ToggleState.open : SlideButton.ToggleState.close);

            viewHolder.slideButton.setOnToggleStateChangeListener(new SlideButton.OnToggleStateChangeListener()
            {
                @Override
                public void onToggleStateChange(SlideButton.ToggleState state)
                {
                    insurance.isSelected = state == SlideButton.ToggleState.open ? true : false;
                    if (callBack != null)
                    {
                        callBack.stateChange();
                    }
                }
            });

            //说明；
            if (TextUtils.isEmpty(insurance.getInsuranceUrl()))
            {
                viewHolder.iv_introduce.setVisibility(View.GONE);
            } else
            {
                viewHolder.iv_introduce.setVisibility(View.VISIBLE);
                viewHolder.iv_introduce.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (callBack != null)
                        {
                            callBack.goIntroducePage(insurance.getShortName(), insurance.getInsuranceUrl());
                        }
                    }
                });
            }

//            viewHolder.tvPhone.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
//                    Utils.p
//                }
//            });
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
        void stateChange();

        void goIntroducePage(String shortName, String url);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvTitle;
        TextView tvDesc;
        TextView tvPrice;
        View iv_introduce;
        SlideButton slideButton;
        TextView tvPhone;

        public MyViewHolder(View view)
        {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvDesc = (TextView) view.findViewById(R.id.tv_desc);
            iv_introduce = view.findViewById(R.id.iv_introduce);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            slideButton = (SlideButton) view.findViewById(R.id.slideButton);

            tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        }
    }
}
