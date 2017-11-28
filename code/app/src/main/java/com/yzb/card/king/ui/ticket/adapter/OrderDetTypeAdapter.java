package com.yzb.card.king.ui.ticket.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.x;

import java.util.Date;


/**
 * 功能 ：订单详情 原，新；
 *
 * @author:gengqiyun
 * @date: 2016/12/6
 */
public class OrderDetTypeAdapter extends BaseListAdapter<TicketOrderDetBean.MyFlight> {
    public OrderDetTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.plane_detail_hbitem2, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TicketOrderDetBean.MyFlight myFlight = getItem(position);
        viewHolder.startTime.setText(myFlight.getDepTime());
        viewHolder.endTime.setText(myFlight.getArrTime());

        long timeSeres = myFlight.getTimeSeres();

        String startDateStr = Utils.toData(timeSeres, 14);

        String startDateStr1 = DateUtil.getWeek(Utils.toData(timeSeres, 4));

        viewHolder.tvStartDate.setText(startDateStr + "周" + startDateStr1);



        String flyingStr = myFlight.getFlyingTime();

        viewHolder.tvDddd.setText(flyingStr);

        String arrDay = myFlight.getArrDay();//yyyy-MM-dd HH:mm:ss
        long endLong = Long.parseLong(arrDay);

        String endDateStr = Utils.toData(endLong, 14);

        String endDateStr1 = DateUtil.getWeek(Utils.toData(endLong, 4));

        viewHolder.tvEndDate.setText(endDateStr + "周" + endDateStr1);


        if (TextUtils.isEmpty(myFlight.getStopCityContext())||"无经停".equals(myFlight.getStopCityContext()) ) {
            viewHolder.tvJingting.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tvJingting.setVisibility(View.VISIBLE);
        }


        viewHolder.startAirportName.setText(myFlight.getDepAirPort() + myFlight.getDepartureTerminal());
        viewHolder.endAirportName.setText(myFlight.getArrAirPort() + myFlight.getArrartureTerminal());


        //   x.image().bind(viewHolder.hbImage, ServiceDispatcher.getImageUrl(myFlight.getShopLogo()));
        // viewHolder.flightIntro.setText(myFlight.getStoreName() + "\n" + myFlight.getFlightNumber() + "\n" + myFlight.getAcft());

        return viewHolder.root;
    }

    public class ViewHolder {
        public final TextView startTime;
        public final TextView endTime;
        public final TextView startAirportName;
        public final TextView endAirportName;
        public final View root;

        public TextView tvStartDate, tvEndDate;

        public TextView tvDddd;

        public TextView tvJingting;

        public ViewHolder(View root) {
            startTime = (TextView) root.findViewById(R.id.startTime);
            endTime = (TextView) root.findViewById(R.id.endTime);
            startAirportName = (TextView) root.findViewById(R.id.startAirportName);
            endAirportName = (TextView) root.findViewById(R.id.endAirportName);
            tvStartDate = (TextView) root.findViewById(R.id.tvStartDate);
            tvEndDate = (TextView) root.findViewById(R.id.tvEndDate);
            tvJingting = (TextView) root.findViewById(R.id.tvJingting);

            tvDddd = (TextView) root.findViewById(R.id.tvDddd);
            this.root = root;
        }
    }
}
