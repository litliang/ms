package com.yzb.card.king.ui.ticket.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FlightDynamicsBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuAdapter;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuView;
import com.yzb.card.king.ui.ticket.activity.PlaneDtDetailActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharedPreferencesUtils;
import com.yzb.card.king.util.Utils;


import org.xutils.x;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/10
 * 描  述：
 */
public class MyCollectListAdapter extends ItemMenuAdapter {

    private List<FlightDynamicsBean> listfb;

    private View.OnClickListener onClickListener;

    private Activity context;

    private LayoutInflater inflater;


    public MyCollectListAdapter(Activity context, List<FlightDynamicsBean> listfb, View.OnClickListener onClickListener)
    {
        this.context = context;
        this.listfb = listfb;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }


    @Override
    public void onDeleItem(int pos)
    {
        removeBoard(listfb.get(pos), pos);

        notifyDataSetChanged();
    }

    private void removeBoard(FlightDynamicsBean f, int pos)
    {
        for (int i = 0; i < listfb.size(); i++) {
            if (f.getFlight_number().equals(listfb.get(i).getFlight_number())) {
                listfb.remove(pos);
                Intent intent = new Intent("com.planeMyConcernFragmet");
                intent.putExtra("notice", 1);
                intent.putExtra("listId", i);
                context.sendBroadcast(intent);
            }
        }
        SharedPreferencesUtils.setParam(context, "list",
                JSON.toJSONString(listfb), "collect");
    }

    @Override
    public Object getItem(int position)
    {
        return listfb.get(position);
    }

    @Override
    public ItemMenuView getItemView(final int position, ItemMenuView convertView, ViewGroup parent)
    {
        ViewHolder vh;
        if (convertView == null) {
            convertView = (ItemMenuView) context.getLayoutInflater().inflate(R.layout.plane_myconcern, null);

            vh = new ViewHolder();
            vh.hbName = (TextView) convertView.findViewById(R.id.hb_name);
            vh.starTime = (TextView) convertView.findViewById(R.id.star_time);
            vh.endTime = (TextView) convertView.findViewById(R.id.end_time);
            vh.starJc = (TextView) convertView.findViewById(R.id.star_jc);
            vh.endJc = (TextView) convertView.findViewById(R.id.end_jc);
            vh.btnZt = (TextView) convertView.findViewById(R.id.btn_zt);
            vh.tvDelete = (ImageView) convertView.findViewById(R.id.tvDelete);
            vh.llItem = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            vh.hbLog = (ImageView) convertView.findViewById(R.id.hbLog);
            vh.chufaRiqi = (TextView) convertView.findViewById(R.id.chufaRiqi);
            vh.seTime = (TextView) convertView.findViewById(R.id.seTime);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final FlightDynamicsBean flightDynamicsBean = listfb.get(position);
        if (flightDynamicsBean != null) {

            LogUtil.e("AAAAAA","flightDynamicsBean.getShopLogo()="+flightDynamicsBean.getShopLogo());
            x.image().bind(vh.hbLog, ServiceDispatcher.getImageUrl(flightDynamicsBean.getShopLogo()));

            vh.hbName.setText(flightDynamicsBean.getCompany() + flightDynamicsBean.getFlight_number());

//            .substring(11, flightDynamicsBean.getSjdeptime().length())
            vh.starTime.setText(flightDynamicsBean.getJhdeptime());
//            .substring(11, flightDynamicsBean.getSjarrtime().length())
            vh.endTime.setText(flightDynamicsBean.getJharrtime());

            vh.starJc.setText(flightDynamicsBean.getDepcity());

            vh.endJc.setText(flightDynamicsBean.getArrcity());

            vh.btnZt.setText(flightDynamicsBean.getState());

            vh.chufaRiqi.setText("日期:"+flightDynamicsBean.getTimeseres());

            final ItemMenuView itemMenuView = convertView;
            vh.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    itemMenuView.hideMenu();
                    deleteItem(position, itemMenuView);

                }
            });


            vh.llItem.setTag(position);
            vh.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    onClickListener.onClick(view);
                }
            });

            String a1 = flightDynamicsBean.getTimeseres();
            String a2 = flightDynamicsBean.getJhdeptime();

            String b1 = flightDynamicsBean.getYjtimeseres();

            String b2 = flightDynamicsBean.getJharrtime();

            LogUtil.e("BBBBB",a1+" "+a2+"----+"+position+"------"+b1+" "+b2);

            if(TextUtils.isEmpty(a1) || TextUtils.isEmpty(a2) || TextUtils.isEmpty(b1) || TextUtils.isEmpty(b2)){
                vh.seTime.setText("");
            }else{


                vh.seTime.setText(Utils.getHourSpace(a1+" "+a2,b1+" "+b2,5));
            }


        }


        return convertView;
    }


    @Override
    public int getCount()
    {
        return listfb.size();
    }

    class ViewHolder {

        TextView hbName;
        TextView starTime;
        TextView endTime;
        TextView starJc;
        TextView endJc;
        TextView btnZt;
        ImageView tvDelete;
        RelativeLayout llItem;
        ImageView hbLog;
        TextView chufaRiqi;
        TextView seTime ;
    }

}
