package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.ticket.activity.RefundTicketRuleActivity;
import com.yzb.card.king.ui.ticket.adapter.OrderDetTypeAdapter;
import com.yzb.card.king.util.LogUtil;

import org.xutils.x;

import java.util.List;

/**
 * @author gengqiyun
 *         航班路线详情；无中转
 *         <p/>
 *         原新布局绑定； 无原时，新布局隐藏，原显示，原显示flightList节点下的数据；
 *         有原时，新布局显示，原显示，原显示regFlight节点下的数据，新显示flightList节点下的数据；
 */
public class TicketDetailFlightAdapter2 extends BaseListAdapter<TicketOrderDetBean.OrderInfoBean> {


    public TicketDetailFlightAdapter2(Context context) {
        super(context);
    }

    private String flightType = "OW";

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.plane_detail_hbitem, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final TicketOrderDetBean.OrderInfoBean item = mList.get(position);
        if (!TextUtils.isEmpty(item.getAirwaysLogo())) {
            x.image().bind(viewHolder.ivCompanyLogo, ServiceDispatcher.getImageUrl(item.getAirwaysLogo()));
        }


        if (flightType.equals("OW")) {

            viewHolder.tvIndexOrignal.setVisibility(View.GONE);

        } else if (flightType.equals("RT")) {

            viewHolder.tvIndexOrignal.setVisibility(View.VISIBLE);

            if (position == 0) {
                viewHolder.tvIndexOrignal.setText("去");
            } else if (position == 1) {
                viewHolder.tvIndexOrignal.setText("返");
            }


        } else if (flightType.equals("MT")) {

            viewHolder.tvIndexOrignal.setVisibility(View.VISIBLE);

            int positionT = position + 1;

            viewHolder.tvIndexOrignal.setText(positionT);

        } else {

            viewHolder.tvIndexOrignal.setVisibility(View.GONE);
        }

        //原；
        List<TicketOrderDetBean.MyFlight> orignalFlights = item.getRetFlight();
        //新；
        List<TicketOrderDetBean.MyFlight> newFlights = item.getFlightList();

        //原  是否为空；
        boolean orignalEmpty = orignalFlights == null || orignalFlights.size() == 0;


        if (orignalEmpty) {

            initPassengers(viewHolder.panelOrignalContainer, item.getTicketsList(), orignalEmpty);

        } else {

            initPassengers(viewHolder.panelOrignalContainer, item.getRetticketsList(), orignalEmpty);

        }

        //实际承运航班信息
        List<TicketOrderDetBean.MyFlight> totalFlights = null;

        if (orignalEmpty) {

            totalFlights = newFlights;

        } else {
            totalFlights = orignalFlights;
        }
        //检测是否实际航运信息
        if (totalFlights != null) {

            int size = totalFlights.size();

            if (size > 0) {

                TicketOrderDetBean.MyFlight myFlight = totalFlights.get(0);
                viewHolder.tvHangbanNumber.setText(myFlight.getFlightNumber());
                String sjcarrName = myFlight.getSjcarrName();

                if (!TextUtils.isEmpty(sjcarrName)) {

                    viewHolder.llShijiHangbanInfo.setVisibility(View.VISIBLE);

                    viewHolder.tvHangbanInfo.setText(sjcarrName);

                } else {

                    viewHolder.llShijiHangbanInfo.setVisibility(View.GONE);
                }

            } else {
                viewHolder.llShijiHangbanInfo.setVisibility(View.GONE);
            }

        } else {

            viewHolder.llShijiHangbanInfo.setVisibility(View.GONE);
        }

        //状态；
        viewHolder.panelOrignalFlight.setVisibility(item.orignalState ? View.VISIBLE : View.GONE);

        // 原数据；
        viewHolder.tvStartCity.setText(item.getStartCityName());
        viewHolder.tvEndCity.setText(item.getEndCityName());
        viewHolder.tvCangdeng.setText(item.getProduct());

        OrderDetTypeAdapter orignalAdapter = new OrderDetTypeAdapter(mContext);
        orignalAdapter.clearAll();

        //原为空：显示flightList节点的数据；否则：显示regFlight节点下的数据；
        orignalAdapter.appendALL(orignalEmpty ? newFlights : orignalFlights);

        viewHolder.flightListView.setAdapter(orignalAdapter);

        //原点击；
        viewHolder.panelArrowOrignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //原点击；
                item.orignalState = !item.orignalState;
                notifyDataSetChanged();
            }
        });


        if (TextUtils.isEmpty(item.getNumber())) {
            viewHolder.llOnePiaohao.setVisibility(View.GONE);
        } else {
            viewHolder.llOnePiaohao.setVisibility(View.GONE);
            viewHolder.tvOnePiaohao.setText("票号：" + item.getNumber());
        }

        viewHolder.llOne.setTag(item.getTicketpriceId());

        viewHolder.llOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceId = (String) v.getTag();
                Intent intent = new Intent(mContext, RefundTicketRuleActivity.class);
                intent.putExtra("priceId", priceId);
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }

    /**
     * 乘机人；
     *
     * @param panelPassengers
     * @param tickets
     */
    private void initPassengers(LinearLayout panelPassengers, List<TicketOrderDetBean.TicketsListBean> tickets, boolean orignalEmpty) {
        panelPassengers.removeAllViews();

        if (tickets != null) {
            for (int i = 0; i < tickets.size(); i++) {
                View view = null;

                if (orignalEmpty) {

                    view = mInflater.inflate(R.layout.item_ticket_passenger_new, null);

                } else {

                    view = mInflater.inflate(R.layout.item_ticket_passenger, null);
                    TextView tvFlag = (TextView) view.findViewById(R.id.tvFlag);
                    //状态（0：正常 1： 改升   2：退票   3：改签   4购票成功，5购票失败）
                    String sturt = tickets.get(i).getSturt();
                    String result = "";
                    if ("2".equals(sturt)) {
                        result = "退";
                    } else if ("3".equals(sturt)) {
                        result = "改";
                    }
                    tvFlag.setText(result);

                }

                TextView tvName = (TextView) view.findViewById(R.id.tvName);

                tvName.setText(tickets.get(i).getGuestName());


                panelPassengers.addView(view);
            }
        }
    }


    public class ViewHolder {
        public final TextView tvStartCity;
        public final TextView tvEndCity;
        public final TextView tvCangdeng;
        public final LinearLayout panelArrowOrignal;
        public final WholeListView flightListView;
        public final LinearLayout panelOrignal;
        public final LinearLayout panelOrignalFlight;
        public final LinearLayout panelOrignalContainer; //原乘机人；

        // public final LinearLayout panelPassengers; //乘机人；
        public final View root;

        private LinearLayout llOne;

        public TextView tvOnePiaohao;

        public LinearLayout llOnePiaohao;

        private TextView tvHangbanNumber;


        private LinearLayout llShijiHangbanInfo;

        private TextView tvHangbanInfo;
        private ImageView ivCompanyLogo;

        private TextView tvIndexOrignal;

        public ViewHolder(View root) {
            panelOrignalContainer = (LinearLayout) root.findViewById(R.id.panelOrignalContainer);
            panelOrignalFlight = (LinearLayout) root.findViewById(R.id.panelOrignalFlight);
            tvStartCity = (TextView) root.findViewById(R.id.tvStartCity);
            tvEndCity = (TextView) root.findViewById(R.id.tvEndCity);
            tvCangdeng = (TextView) root.findViewById(R.id.tvCangdeng);
            panelArrowOrignal = (LinearLayout) root.findViewById(R.id.panelArrowOrignal);
            flightListView = (WholeListView) root.findViewById(R.id.flightListView);
            panelOrignal = (LinearLayout) root.findViewById(R.id.panelOrignal);
            //  panelPassengers = (LinearLayout) root.findViewById(R.id.panelPassengers);

            llOne = (LinearLayout) root.findViewById(R.id.llOne);

            llOnePiaohao = (LinearLayout) root.findViewById(R.id.llOnePiaohao);

            tvOnePiaohao = (TextView) root.findViewById(R.id.tvOnePiaohao);


            llShijiHangbanInfo = (LinearLayout) root.findViewById(R.id.llShijiHangbanInfo);

            tvHangbanInfo = (TextView) root.findViewById(R.id.tvHangbanInfo);

            tvHangbanNumber = (TextView) root.findViewById(R.id.tvHangbanNumber);

            ivCompanyLogo = (ImageView) root.findViewById(R.id.ivCompanyLogo);

            tvIndexOrignal = (TextView) root.findViewById(R.id.tvIndexOrignal);

            this.root = root;
        }
    }
}
