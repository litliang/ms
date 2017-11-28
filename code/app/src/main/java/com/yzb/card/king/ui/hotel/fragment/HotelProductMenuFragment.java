package com.yzb.card.king.ui.hotel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelMenuBean;
import com.yzb.card.king.ui.appwidget.appdialog.HotelOtherProductIntroFragmentDialog;
import com.yzb.card.king.ui.appwidget.appdialog.HotelProductRoomInfoFragmentDialog;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.holder.HotelDetalServiceFacitityHolder;
import com.yzb.card.king.ui.hotel.holder.HotelDialogMenuHolder;
import com.yzb.card.king.ui.hotel.persenter.HotelMenuPresenter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.lemon.view.adapter.MultiTypeAdapter;

/**
 * 类  名：酒店产品菜单
 * 作  者：Li Yubing
 * 日  期：2017/8/10
 * 描  述：
 */
public class HotelProductMenuFragment extends Fragment implements View.OnClickListener,BaseViewLayerInterface{

    private  HotelOtherProductIntroFragmentDialog.HotelDialogInterface dataCall;

    private HotelProductRoomInfoFragmentDialog.HotelDialogInterface  newDataCallBack;

    private RecyclerView wvLvData;

    private MultiTypeAdapter mAdapter;

    private HotelMenuPresenter hotelMenuPresenter;

    private String goodsType;

    private String menuId;

    private boolean goodsComboFlag = false;//true:商品套餐菜单 false：商品菜单

    public void setDataCall(HotelOtherProductIntroFragmentDialog.HotelDialogInterface dataCall)
    {
        this.dataCall = dataCall;
    }

    public void setDataCall(HotelProductRoomInfoFragmentDialog.HotelDialogInterface  newDataCallBack){

        this.newDataCallBack = newDataCallBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_hotel_menu,null);

        hotelMenuPresenter = new HotelMenuPresenter(this);

        Bundle bundle = getArguments();

        if(bundle.containsKey("goodsType")){

            if(bundle.containsKey("goodsComboFlag")){

                goodsComboFlag = bundle.getBoolean("goodsComboFlag");

            }

            goodsType = bundle.getString("goodsType");
            menuId = bundle.getString("menuId");
        }

        initView(view);

        return view;
    }

    private void initView(View view)
    {

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        tvTitle.setText("菜单");

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

                if(goodsType!=null){

                    ProgressDialogUtil.getInstance().showProgressDialogMsg("正在请求数据……", getContext(), true);

                    if(goodsComboFlag){

                        hotelMenuPresenter.sendHotelGoodsPolicysMenuRequest(goodsType,menuId);

                    }else {
                        hotelMenuPresenter.sendHotelGoodsMenuRequest(goodsType,menuId);
                    }

                }

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.llTemp:

                if(dataCall!=null){
                    dataCall.backAction();
                }

                if(newDataCallBack!=null){
                    newDataCallBack.backAction();
                }

                break;
            default:
                break;

        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        List<HotelMenuBean> list = JSON.parseArray(o+"",HotelMenuBean.class);

        mAdapter.clear();

        mAdapter.addAll(HotelDialogMenuHolder.class, list);
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }
}
