package com.yzb.card.king.ui.ticket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.LowPriceBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.ticket.activity.BaseTicketActivity;
import com.yzb.card.king.ui.ticket.holder.BaseNullHolder;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.x;

import java.util.List;

/**
 * 类名： FindLowAdapter
 * 作者： Lei Chao.
 * 日期： 2016-10-11
 * 描述：
 */
public class FindLowAdapter extends RecyclerView.Adapter {

    private List<LowPriceBean> datas1;

    private Context mContext;

    private int type;

    private String starCity;

    private String endCity;

    public FindLowAdapter(List<LowPriceBean> datas, Context mContext, int type, String starCity, String endCity)
    {
        this.starCity = starCity;
        this.endCity = endCity;
        this.type = type;
        this.datas1 = datas;
        this.mContext = mContext;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {


        if (viewType == AppConstant.TYPE_NULL)
        {
            return new BaseNullHolder(LayoutInflater.from(mContext).inflate(R.layout.base_null_data_item, parent, false));
        } else
        {
            return new LowVH(LayoutInflater.from(mContext).inflate(R.layout.low_price_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if (holder instanceof LowVH)
        {
            LowVH vh = (LowVH) holder;
            LowPriceBean lowPriceBean = datas1.get(position);
            String storeName = lowPriceBean.getStoreName();
            if(TextUtils.isEmpty(storeName)){
                storeName="";
            }
            vh.tv_aviationType.setText(storeName+ lowPriceBean.getFlightNumber());

            String priceStr = lowPriceBean.getFareInfor();
            SpannableString ss = new SpannableString("¥" + Utils.subZeroAndDot(priceStr ));
            ss.setSpan(new AbsoluteSizeSpan(CommonUtil.sp2px(mContext, 14)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            vh.tvAdultPrice.setText(ss);

            vh.tv_date.setText(lowPriceBean.getTimeSeres());

            vh.tv_week.setText(DateUtil.getDateExplain(DateUtil.string2Date(lowPriceBean.getTimeSeres())));

            vh.tv_startAirport.setText(starCity);
            vh.tv_endAirport.setText(endCity);
            if (type == BaseTicketActivity.TYPE_ROUND)
            {
                vh.iv_fc.setVisibility(View.VISIBLE);
                vh.tv_duration.setText("");
            } else
            {
                vh.iv_fc.setVisibility(View.GONE);
                vh.tv_duration.setText(lowPriceBean.getFlyIngTime());
            }

            if (lowPriceBean.getShopLogo() != null)
            {
                x.image().bind(vh.image_air_company, ServiceDispatcher.getImageUrl(lowPriceBean.getShopLogo()));
            }


            if (lowPriceBean.getReturnFlightName() != null)
            {
                vh.ll_fchkName.setVisibility(View.VISIBLE);
                vh.tv_aviationType_end.setText(lowPriceBean.getReturnFlightName()+lowPriceBean.getReturnFlightNumber());
            } else
            {

                vh.ll_fchkName.setVisibility(View.GONE);
            }

            if (lowPriceBean.getReturnFlightLogo() != null)
            {
                x.image().bind(vh.image_air_end, ServiceDispatcher.getImageUrl(lowPriceBean.getReturnFlightLogo()));
            }


            if (lowPriceBean.getReturnTime() != null)
            {
                vh.ll_fcTime.setVisibility(View.VISIBLE);
                vh.tv_fdate.setText(lowPriceBean.getReturnTime());
                vh.tv_fweek.setText(DateUtil.getDateExplain(DateUtil.string2Date(lowPriceBean.getReturnTime())));
            } else
            {
                vh.ll_fcTime.setVisibility(View.GONE);
            }
            final int pos = position;
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (onItemClick != null)
                    {
                        onItemClick.onItemClickListener(datas1.get(pos));
                    }
                }
            });
        }

    }

    public void setType(int type)
    {
        this.type = type;
    }

    public interface OnItemClick {
        void onItemClickListener(LowPriceBean lowPriceBean);
    }

    private OnItemClick onItemClick;

    public void setOnItemClickListener(OnItemClick mOnItemClick)
    {
        this.onItemClick = mOnItemClick;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (this.datas1.size() == 0 || this.datas1 == null)
        {
            return AppConstant.TYPE_NULL;
        }
        return AppConstant.TYPE_NORMLL;
    }

    @Override
    public int getItemCount()
    {
        return this.datas1.size() == 0 ? 1 : this.datas1.size();
    }

    class LowVH extends RecyclerView.ViewHolder {

        TextView tv_startAirport;
        TextView tv_endAirport;
        TextView tv_duration;
        TextView tvAdultPrice;
        TextView tv_date;
        TextView tv_week;
        TextView tv_aviationType;
        ImageView image_air_company;
        ImageView iv_fc;
        TextView tv_fdate;
        TextView tv_fweek;
        ImageView image_air_end;
        TextView tv_aviationType_end;
        LinearLayout ll_fcTime;
        LinearLayout ll_fchkName;

        public LowVH(View itemView)
        {
            super(itemView);
            tv_startAirport = (TextView) itemView.findViewById(R.id.tv_startAirport);
            tv_endAirport = (TextView) itemView.findViewById(R.id.tv_endAirport);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tvAdultPrice = (TextView) itemView.findViewById(R.id.tvAdultPrice);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_week = (TextView) itemView.findViewById(R.id.tv_week);
            tv_aviationType = (TextView) itemView.findViewById(R.id.tv_aviationType);
            image_air_company = (ImageView) itemView.findViewById(R.id.image_air_company);
            iv_fc = (ImageView) itemView.findViewById(R.id.iv_fc);
            tv_fdate = (TextView) itemView.findViewById(R.id.tv_fdate);
            tv_fweek = (TextView) itemView.findViewById(R.id.tv_fweek);
            image_air_end = (ImageView) itemView.findViewById(R.id.image_air_end);
            tv_aviationType_end = (TextView) itemView.findViewById(R.id.tv_aviationType_end);
            ll_fcTime = (LinearLayout) itemView.findViewById(R.id.ll_fcTime);
            ll_fchkName = (LinearLayout) itemView.findViewById(R.id.ll_fchkName);
        }
    }


}