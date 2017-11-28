package com.yzb.card.king.ui.hotel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelRoomComboInfoBean;
import com.yzb.card.king.bean.hotel.HotelRoomInfoBean;
import com.yzb.card.king.ui.appwidget.appdialog.HotelProductRoomInfoFragmentDialog;
import com.yzb.card.king.ui.hotel.holder.HotelDetalServiceFacitityHolder;

import java.util.ArrayList;
import java.util.List;

import cn.lemon.view.adapter.MultiTypeAdapter;

/**
 * 类  名：酒店服务设施
 * 作  者：Li Yubing
 * 日  期：2017/8/8
 * 描  述：
 */
public class ServiceFacitityFragment extends Fragment implements View.OnClickListener{


    private  HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall;

    private RecyclerView wvLvData;

    private MultiTypeAdapter mAdapter;

    private  List<HotelRoomInfoBean.RoomService> roomServiceList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_service_facitity,null);

        Bundle bundle = getArguments();

        HotelRoomInfoBean hotelRoomComboInfoBean = (HotelRoomInfoBean) bundle.get("roomInfoData");

        roomServiceList = hotelRoomComboInfoBean.getBaseServiceList();

        initView(view);

        return view;
    }

    private void initView(View view)
    {

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        tvTitle.setText("服务设施");

        view.findViewById(R.id.llTemp).setOnClickListener(this);

        wvLvData = (RecyclerView) view.findViewById(R.id.wvLvData);

        mAdapter = new MultiTypeAdapter(getContext());

        wvLvData.setLayoutManager(new LinearLayoutManager(getContext()));
        wvLvData.setAdapter(mAdapter);

        wvLvData.post(new Runnable() {
            @Override
            public void run()
            {
                /**
                 * 请求该酒店的房间信息
                 */

                mAdapter.clear();

                mAdapter.addAll(HotelDetalServiceFacitityHolder.class, roomServiceList);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.llTemp:

                dataCall.backAction();

                break;
            default:
                break;

        }
    }

    public void setDataCall(HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall)
    {
        this.dataCall = dataCall;
    }
}