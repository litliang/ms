package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.ui.base.BaseRecyAdapter;

import java.util.List;

/**
 * 功能：旅游详情航线列表；
 *
 * @author:gengqiyun
 * @date: 2016/11/16
 */
public class TravelLineAdapter extends BaseRecyAdapter<TravelLineBean>
{
    private String[] letterArray;

    public TravelLineAdapter(List<TravelLineBean> mList, Context context)
    {
        super(mList, context);
        letterArray = context.getResources().getStringArray(R.array.cap_letters);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.travel_detail_line_item, parent, false);
        return new LineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        LineViewHolder viewHolder = (LineViewHolder) holder;
        final TravelLineBean lineBean = mList.get(position);

        viewHolder.tvLineName.setText(letterArray[position] + "线");
        //卢浮宫/直飞
        String scenic = "";
        String trafficType = "";

        //取经典景点描述的第一个
        String classicSpots = lineBean.getClassicSpotDesc();
        if (!TextUtils.isEmpty(classicSpots))
        {
            String[] spotsArray = classicSpots.split(",");
            scenic = spotsArray != null && spotsArray.length > 0 ? spotsArray[0] : "";
        }

        List<TravelLineBean.Schedule> schedules = lineBean.getScheduleList();
        if (schedules != null && schedules.size() > 0)
        {
            List<TravelLineBean.MyPlan> planList = schedules.get(0).getPlanList();
            if (planList != null && planList.size() > 0)
            {
                //交通工具类型  0无、1直飞、2中转
                switch (planList.get(0).getTrafficType())
                {
                    case "0":
                        trafficType = "无";
                        break;
                    case "1":
                        trafficType = "直飞";
                        break;
                    case "2":
                        trafficType = "中转";
                        break;
                }
            }
        }
        viewHolder.tvLineType.setText(TextUtils.isEmpty(scenic) ? trafficType : scenic + "/" + trafficType);
        holder.itemView.setSelected(lineBean.isSelct());
        viewHolder.tvLineName.setSelected(lineBean.isSelct());
        viewHolder.tvLineType.setSelected(lineBean.isSelct());

        //线路点击；
        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (lineClick != null)
                {
                    lineClick.getClickLine(position);
                }
            }
        });
    }

    private ILineClick lineClick;

    public void setLineClick(ILineClick lineClick)
    {
        this.lineClick = lineClick;
    }

    public int getSelectItemPos()
    {
        if (mList != null)
        {
            for (int i = 0; i < mList.size(); i++)
            {
                if (mList.get(i).isSelct())
                {
                    return i;
                }
            }
        }
        return -1;
    }

    public interface ILineClick
    {
        /**
         * 选中的bean；
         *
         * @param bean
         */
        void getClickLine(int bean);
    }

    /**
     * 选中item；
     *
     * @param lineBean
     */
    public void selectItem(TravelLineBean lineBean)
    {
        if (lineBean != null)
        {
            for (int i = 0; i < mList.size(); i++)
            {
                if (mList.get(i).isSelct())
                {
                    mList.get(i).setIsSelct(false);
                }
            }
            lineBean.setIsSelct(true);
            notifyDataSetChanged();
        }
    }

    public TravelLineBean getSelectItem()
    {
        if (mList != null)
        {
            for (int i = 0; i < mList.size(); i++)
            {
                if (mList.get(i).isSelct())
                {
                    return mList.get(i);
                }
            }
        }
        return null;
    }

    class LineViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvLineName;
        public TextView tvLineType;

        public LineViewHolder(View itemView)
        {
            super(itemView);
            tvLineName = (TextView) itemView.findViewById(R.id.tvLineName);
            tvLineType = (TextView) itemView.findViewById(R.id.tvLineType);
        }
    }

}
