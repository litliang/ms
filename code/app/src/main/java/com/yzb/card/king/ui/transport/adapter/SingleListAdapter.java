package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.bean.ticket.BankInfo;
import com.yzb.card.king.bean.ticket.PlaneTicket;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.appdialog.DiscBankDialog;
import com.yzb.card.king.ui.ticket.activity.BaseTicketActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dev on 2016/5/6.
 */
public class SingleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PlaneTicket> dataList = null;
    private LayoutInflater inflater = null;
    private ICallBack callBack;
    int babyNum;
    int childrenNum;
    Context context;

    public SingleListAdapter(BaseTicketActivity context, int babyNum, int childrenNum)
    {
        this.context = context;
        this.babyNum = babyNum;
        this.childrenNum = childrenNum;
        inflater = LayoutInflater.from(context);
    }

    public void appendData(List<PlaneTicket> dataList)
    {
        if (dataList == null) {
            return;
        }
        if (this.dataList == null) {
            this.dataList = new ArrayList<>();
        }
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }


    public List<PlaneTicket> getDataList()
    {
        return dataList;
    }

    public void clear()
    {
        if (this.dataList != null) {
            this.dataList.clear();
        }
        notifyDataSetChanged();
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
        if (holder instanceof MyViewHolder) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            PlaneTicket map = dataList.get(position);
            setHolderData(viewHolder, map);
            setVisible(viewHolder, map);
            setListener(position, viewHolder);
        }
    }

    private void setListener(final int position, MyViewHolder viewHolder)
    {
        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (callBack != null) {
                    callBack.callBack(position);
                }
            }
        });

//        viewHolder.llBankMore.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                PlaneTicket ticket = dataList.get(position);
//                DiscBankDialog discBankDialog = new DiscBankDialog(context, ticket.getIstList());
//                discBankDialog.showAtLocation(v, Gravity.BOTTOM, 0, 0);
//            }
//        });
    }

    private void setVisible(MyViewHolder viewHolder, PlaneTicket map)
    {
        if (map.getTimeSeres() != null && map.getTimeSeres().equals(map.getArrDay())) {
            viewHolder.tvAddOneDay.setVisibility(View.GONE);
        } else {
            viewHolder.tvAddOneDay.setVisibility(View.VISIBLE);
            int duration = 0;
            String arrDay = map.getArrDay();
            String depDay = map.getTimeSeres();
            Date begin = DateUtil.string2Date(depDay, DateUtil.DATE_FORMAT_DATE);
            Date end = DateUtil.string2Date(arrDay, DateUtil.DATE_FORMAT_DATE);
            duration = DateUtil.naturalDaysBetween(begin, end);
            viewHolder.tvAddOneDay.setText(UiUtils.getString(R.string.ticket_add_one_day, duration));
        }

        if (childrenNum > 0) {
            viewHolder.tvChildPrice.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvChildPrice.setVisibility(View.GONE);
        }

        if (babyNum > 0) {
            viewHolder.tvBabyPrice.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvBabyPrice.setVisibility(View.GONE);
        }

        setStopTransit(viewHolder, map);

        if (TextUtils.isEmpty(map.getIsFlightNumber())) {
            viewHolder.llShare.setVisibility(View.GONE);
        } else {
            viewHolder.llShare.setVisibility(View.VISIBLE);
            viewHolder.tvShare.setText(map.getIsFlightNumber());
            x.image().bind(viewHolder.ivShare, ServiceDispatcher.getImageUrl(map.getSharedFlightLogo()));
        }

        FavInfoBean disMap = map.getDisMap();

        if (disMap != null) {

            int a = 0;

            if ("1".equals(disMap.getBankStatus())) {//银行优惠

                viewHolder.tvPlatform.setVisibility(View.VISIBLE);

            } else {

                viewHolder.tvPlatform.setVisibility(View.GONE);
                a = a+1;
            }

            if ("1".equals(disMap.getTicketStatus())) {//券

                viewHolder.tvShop.setVisibility(View.VISIBLE);

            } else {

                viewHolder.tvShop.setVisibility(View.GONE);
                a = a+1;
            }

            if ("1".equals(disMap.getMinusStatus())) {//减

                viewHolder.tvBank.setVisibility(View.VISIBLE);

            } else {
                viewHolder.tvBank.setVisibility(View.GONE);
                a = a+1;
            }

            if( a == 3){

                viewHolder.llDisMap.setVisibility(View.VISIBLE);
            }

            if ("1".equals(disMap.getLeftStatus())) {//生活分期

                viewHolder.ivLifeStages.setVisibility(View.VISIBLE);

            } else {
                viewHolder.ivLifeStages.setVisibility(View.INVISIBLE);
            }

        } else {
            viewHolder.tvPlatform.setVisibility(View.GONE);
            viewHolder.tvShop.setVisibility(View.GONE);
            viewHolder.tvBank.setVisibility(View.GONE);
            viewHolder.ivLifeStages.setVisibility(View.INVISIBLE);

            viewHolder.llDisMap.setVisibility(View.GONE);
        }
    }

    private void setStopTransit(MyViewHolder viewHolder, PlaneTicket map)
    {
        String stop = "";
        String transit = "";
        if (map.getFlightList().size() <= 1) {//直飞
            boolean stopIsEmpty = TextUtils.isEmpty(map.getStopCityContext()) ||
                    "无经停".equals(map.getStopCityContext());
            if (!stopIsEmpty) {
                stop = "经";
                transit = map.getStopCityContext();
            }
        } else {//中转
            List<String> stopCity = getStopCity(map);
            List<String> transitCity = map.getTransitCities();
            if (stopCity.size() == 0) {//么有经停，只有中转
                if (transitCity.size() > 1) {
                    transit = transitCity.size() + "中转";
                } else if (transitCity.size() == 1) {
                    stop = "转";
                    transit = transitCity.get(0);
                }
            } else {//有经停有中转
                stop = "";
                transit = stopCity.size() + "经停" + transitCity.size() + "中转";
            }
        }
        setTransitStopText(viewHolder, stop, transit);
    }

    private void setTransitStopText(MyViewHolder viewHolder, String stop, String transit)
    {
        boolean stopIsEmpty = TextUtils.isEmpty(stop);
        boolean transitIsEmpty = TextUtils.isEmpty(transit);
        if (stopIsEmpty && transitIsEmpty) {
            viewHolder.llTransit.setVisibility(View.GONE);
        } else {
            viewHolder.llTransit.setVisibility(View.VISIBLE);
            if (stopIsEmpty) {
                viewHolder.tvAction.setVisibility(View.VISIBLE);
                viewHolder.tvAction.setText("转");
                viewHolder.tvTransitCity.setText(transit);
            } else if (transitIsEmpty) {
                viewHolder.tvAction.setVisibility(View.VISIBLE);
                viewHolder.tvAction.setText("经");
                viewHolder.tvTransitCity.setText(stop);
            } else {
                viewHolder.tvAction.setVisibility(View.GONE);
                viewHolder.tvTransitCity.setText(stop);
            }
        }
    }

    private List<String> getStopCity(PlaneTicket map)
    {
        List<String> stopCities = new ArrayList<>();
        for (int i = 0; i < map.getFlightList().size(); i++) {
            if (!TextUtils.isEmpty(getStopCityContext(map, i))) {
                stopCities.add(getStopCityContext(map, i));
            }
        }
        return stopCities;
    }

    private String getStopCityContext(PlaneTicket map, int i)
    {
        return map.getFlightList().get(i).getStopCityContext();
    }

    private void setHolderData(MyViewHolder viewHolder, PlaneTicket map)
    {
        viewHolder.startTime.setText(map.getDepTime());
      viewHolder.startAirport.setText(map.getDepAirPort());


        viewHolder.endTime.setText(map.getArrTime());
      viewHolder.endAirport.setText(map.getArrAirPort());


        viewHolder.dura.setText(map.getFlyIngTime());
        viewHolder.aviationType.setText(map.getStoreName() + map.getFlightNumber());
        viewHolder.tvAdultPrice.setText(UiUtils.getString(R.string.ticket_price,
                Utils.subZeroAndDot((map.getFareAdult() + map.getFueltax()) + "")));

        viewHolder.tvChildPrice.setText(UiUtils.getString(R.string.ticket_child_price,
                Utils.subZeroAndDot((map.getFareChd() + map.getFueltaxChd()) + "")));

        viewHolder.tvBabyPrice.setText(UiUtils.getString(R.string.ticket_baby_price,
                Utils.subZeroAndDot((map.getFareBab() + map.getFueltaxBab()) + "")));

//        viewHolder.tvLeaveTicket.setText(UiUtils.getString(R.string.ticket_leave_ticket,
//                Utils.subZeroAndDot(map.getAcbIninfo()+"")));

        x.image().bind(viewHolder.companyIcon, ServiceDispatcher.getImageUrl(map.getShopLogo()));
//        List<BankInfo> bankInfos = map.getIstList();
//        if (bankInfos != null && bankInfos.size() > 0)
//        {
//            if (bankInfos.size() == 1)
//            {
//                BankInfo bankInfo = bankInfos.get(0);
//                x.image().bind(viewHolder.ivBankLogo, ServiceDispatcher.getImageUrl(bankInfo.getBankLogoCode()));
//                viewHolder.tvBankName.setText(bankInfo.getBankName());
//                setBankVisible(viewHolder, View.VISIBLE, View.GONE);
//            } else
//            {
//                setBankVisible(viewHolder, View.GONE, View.VISIBLE);
//            }
//        } else
//        {
//            setBankVisible(viewHolder, View.GONE, View.GONE);
//        }

    }

//    private void setBankVisible(MyViewHolder viewHolder, int info, int more)
//    {
//        viewHolder.llBankInfo.setVisibility(info);
//        viewHolder.llBankMore.setVisibility(more);
//    }

    @Override
    public int getItemCount()
    {
        return dataList == null ? 0 : dataList.size();
    }

    public void setOnItemClickListener(ICallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface ICallBack {
        void callBack(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvBank;
        private final TextView tvShop;
        private final TextView tvPlatform;
        public View root;
        public TextView startTime, endTime, startAirport, endAirport, dura, tvAdultPrice, tvTransitCity,
                tvChildPrice, tvBabyPrice, priceType, aviationType, tvAddOneDay, tvBankName,
                tvShare, tvAction;
        public ImageView companyIcon, ivBankLogo, ivShare,ivLifeStages;
        public LinearLayout llTransit, llBankInfo, llBankMore, llShare;
        public LinearLayout llDisMap;

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
            tvShare = (TextView) itemView.findViewById(R.id.tvShare);
            tvAction = (TextView) itemView.findViewById(R.id.tvAction);

            tvPlatform = (TextView) itemView.findViewById(R.id.tvPlatform);
            tvShop = (TextView) itemView.findViewById(R.id.tvShop);
            tvBank = (TextView) itemView.findViewById(R.id.tvBank);

            llTransit = (LinearLayout) itemView.findViewById(R.id.llTransit);
            llBankInfo = (LinearLayout) itemView.findViewById(R.id.llBankInfo);
            llBankMore = (LinearLayout) itemView.findViewById(R.id.llBankMore);
            llShare = (LinearLayout) itemView.findViewById(R.id.llShare);

            llDisMap = (LinearLayout) itemView.findViewById(R.id.llDisMap);

            companyIcon = (ImageView) itemView.findViewById(R.id.image_air_company);
            ivBankLogo = (ImageView) itemView.findViewById(R.id.ivBankLogo);
            ivShare = (ImageView) itemView.findViewById(R.id.ivShare);
            ivLifeStages = (ImageView) itemView.findViewById(R.id.ivLifeStages);

            root = itemView;
        }
    }
}
