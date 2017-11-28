package com.yzb.card.king.ui.transport.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.UpgradeOrderBean;
import com.yzb.card.king.ui.base.BaseRecyAdapter;
import com.yzb.card.king.ui.ticket.activity.BaseTicketActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;

import org.xutils.x;

import java.util.Date;

/**
 * 改签列表；
 *
 * @author gengqiyun
 * @date 2016.12.4
 */
public class RescheduleListAdapter extends BaseRecyAdapter<UpgradeOrderBean.FlightBean>
{
    private ICallBack callBack;

    public RescheduleListAdapter(BaseTicketActivity context)
    {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.single_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            UpgradeOrderBean.FlightBean map = mList.get(position);
            setHolderData(viewHolder, map);
            setVisible(viewHolder, map);
            setListener(position, viewHolder);
        }
    }

    private void setListener(final int position, MyViewHolder viewHolder)
    {
        viewHolder.root.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (callBack != null)
                {
                    callBack.callBack(position);
                }
            }
        });
    }

    private void setVisible(MyViewHolder viewHolder, UpgradeOrderBean.FlightBean map)
    {
        String depDay = map.getOrgDate();
        String arrDay = map.getDstDate();

        if (!TextUtils.isEmpty(depDay) && depDay.equals(arrDay))
        {
            viewHolder.tvAddOneDay.setVisibility(View.GONE);
        } else
        {
            viewHolder.tvAddOneDay.setVisibility(View.VISIBLE);
            int duration;
            Date begin = DateUtil.string2Date(depDay, DateUtil.DATE_FORMAT_DATE3);
            Date end = DateUtil.string2Date(arrDay, DateUtil.DATE_FORMAT_DATE3);
            duration = DateUtil.naturalDaysBetween(begin, end);
            viewHolder.tvAddOneDay.setText(UiUtils.getString(R.string.ticket_add_one_day, duration));
        }

        //不显示中转和经停；
        viewHolder.llTransit.setVisibility(View.GONE);
        //不显示余票；
//        viewHolder.tvLeaveTicket.setVisibility(View.GONE);

        //有共享航班；
        if ("7".equals(map.getCodeShare()))
        {
            viewHolder.llShare.setVisibility(View.VISIBLE);
            viewHolder.tvShare.setText(map.getCodeShareCarriar());

            //共享航班号不显示；
            viewHolder.ivShare.setVisibility(View.GONE);
            x.image().bind(viewHolder.ivShare, "");
        } else
        {
            viewHolder.llShare.setVisibility(View.GONE);
        }
    }

    private void setHolderData(MyViewHolder viewHolder, UpgradeOrderBean.FlightBean map)
    {
        viewHolder.startTime.setText(map.getOrgTime());
        viewHolder.startAirport.setText(map.getDepAirPort() + map.getOrgTerminal()); //出发机场名；暂时为空；

        viewHolder.endTime.setText(map.getDstTime());
        viewHolder.endAirport.setText(map.getArrAirPort() + map.getDstTerminal());//目的机场名；暂时为空；
        viewHolder.dura.setText(map.getFlyIngTime());  //飞行时长；
        viewHolder.aviationType.setText(map.getCarrier() + " " + map.getFlightNo());

        viewHolder.tvAdultPrice.setVisibility(map.getPrice() == 0 ? View.GONE : View.VISIBLE);

        viewHolder.tvAdultPrice.setText(UiUtils.getString(R.string.ticket_price,
                Utils.subZeroAndDot(map.getPrice() + "")));

        viewHolder.tvChildPrice.setVisibility(map.getChdPrice() == 0 ? View.GONE : View.VISIBLE);
        viewHolder.tvChildPrice.setText(UiUtils.getString(R.string.ticket_child_price,
                Utils.subZeroAndDot(map.getChdPrice() + "")));

        viewHolder.tvBabyPrice.setVisibility(map.getInfPrice() == 0 ? View.GONE : View.VISIBLE);
        viewHolder.tvBabyPrice.setText(UiUtils.getString(R.string.ticket_baby_price,
                Utils.subZeroAndDot(map.getInfPrice() + "")));

//        viewHolder.tvLeaveTicket.setVisibility(View.GONE); //不显示余票；
//        viewHolder.tvLeaveTicket.setText(UiUtils.getString(R.string.ticket_leave_ticket, "0"));

        x.image().bind(viewHolder.companyIcon, "");
        setBankVisible(viewHolder, View.GONE, View.GONE);
    }

    private void setBankVisible(MyViewHolder viewHolder, int info, int more)
    {
        viewHolder.llBankInfo.setVisibility(info);
        viewHolder.llBankMore.setVisibility(more);
    }

    public void setOnItemClickListener(ICallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface ICallBack
    {
        void callBack(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public View root;
        public TextView startTime, endTime, startAirport, endAirport, dura, tvAdultPrice, tvTransitCity,
                tvChildPrice, tvBabyPrice, priceType, aviationType, tvAddOneDay, tvBankName,
                tvShare, tvAction;
        public ImageView companyIcon, ivBankLogo, ivShare;
        public LinearLayout llTransit, llBankInfo, llBankMore, llShare;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            startTime = (TextView) itemView.findViewById(R.id.tv_startTime);
            startAirport = (TextView) itemView.findViewById(R.id.tv_startAirport);
            endTime = (TextView) itemView.findViewById(R.id.tv_endTime);
            endAirport = (TextView) itemView.findViewById(R.id.tv_endAirport);
            dura = (TextView) itemView.findViewById(R.id.tv_duration);
            tvAdultPrice = (TextView) itemView.findViewById(R.id.tvAdultPrice);
            tvChildPrice = (TextView) itemView.findViewById(R.id.tvChildPrice);
            tvBabyPrice = (TextView) itemView.findViewById(R.id.tvBabyPrice);
            priceType = (TextView) itemView.findViewById(R.id.tv_priceType);
            aviationType = (TextView) itemView.findViewById(R.id.tv_aviationType);
            tvAddOneDay = (TextView) itemView.findViewById(R.id.tvAddOneDay);
            tvTransitCity = (TextView) itemView.findViewById(R.id.tvTransitCity);
            tvBankName = (TextView) itemView.findViewById(R.id.tvBankName);
//            tvLeaveTicket = (TextView) itemView.findViewById(R.id.tvLeaveTicket);
            tvShare = (TextView) itemView.findViewById(R.id.tvShare);
            tvAction = (TextView) itemView.findViewById(R.id.tvAction);

            llTransit = (LinearLayout) itemView.findViewById(R.id.llTransit);
            llBankInfo = (LinearLayout) itemView.findViewById(R.id.llBankInfo);
            llBankMore = (LinearLayout) itemView.findViewById(R.id.llBankMore);
            llShare = (LinearLayout) itemView.findViewById(R.id.llShare);

            companyIcon = (ImageView) itemView.findViewById(R.id.image_air_company);
            ivBankLogo = (ImageView) itemView.findViewById(R.id.ivBankLogo);
            ivShare = (ImageView) itemView.findViewById(R.id.ivShare);
            root = itemView;
        }
    }
}
