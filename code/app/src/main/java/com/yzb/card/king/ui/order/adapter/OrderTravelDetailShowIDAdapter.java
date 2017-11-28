package com.yzb.card.king.ui.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelOrderDetailguestListBean;
import com.yzb.card.king.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sushuiku on 2016/11/18.
 */

public class OrderTravelDetailShowIDAdapter extends BaseAdapter{


    private LayoutInflater mInflater;
    private Context mContext;
    private List<TravelOrderDetailguestListBean> rooms = new ArrayList<>();

    public OrderTravelDetailShowIDAdapter(Context context,List<TravelOrderDetailguestListBean> rooms){
        mContext = context;
        this.rooms = rooms;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.orderd_travel_detail_showid,null);
            holder.travelNameTV = (TextView) convertView.findViewById(R.id.travelnametv);
            holder.travelerIDtv = (TextView) convertView.findViewById(R.id.travelidtv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TravelOrderDetailguestListBean travelOrderDetailguestListBean = rooms.get(position);
        holder.travelNameTV.setText(travelOrderDetailguestListBean.getGuestName());
        String idNum = travelOrderDetailguestListBean.getGuestCertNo();
        if (RegexUtil.validIdNumNoToast(idNum)){
            holder.travelerIDtv.setText(RegexUtil.hideIdMidNum(idNum));
        }

        return convertView;
    }

    class ViewHolder{
        TextView travelNameTV,travelerIDtv;
    }
}
