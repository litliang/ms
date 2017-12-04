package com.yzb.card.king.ui.hotel.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.GiftComboBean;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.holder.HotelCardEquityHolder;
import com.yzb.card.king.ui.hotel.holder.HotelFlashSaleHolder;
import com.yzb.card.king.ui.hotel.persenter.HotelServicePersenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.lemon.view.adapter.MultiTypeAdapter;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/6
 * 描  述：礼品卡套餐：限时抢购 卡权益
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_hotel_gift_combn)
public class HotelGiftCombmFragment extends BaseFragment implements BaseViewLayerInterface {

    private Hotel.GoodsType goodsType;

    /**
     * 酒店名称
     */
    private String hotelName;

    private  long hotelId = 1l;

    @ViewInject(R.id.wvLvData)
    private  RecyclerView wvLvData;

    private  boolean productType = false ;

    private HotelServicePersenter persenter;

    public static HotelGiftCombmFragment getInstance(Hotel.GoodsType bean,String hotelName,long hotelId)
    {

        HotelGiftCombmFragment sf = new HotelGiftCombmFragment();

        sf.goodsType = bean;

        sf.hotelName = hotelName;

        sf.hotelId = hotelId;

        return sf;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    private void initRequest()
    {
        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        persenter = new HotelServicePersenter(this);



        if(productType){
            persenter.queryHotelFlashSaleAction(hotelId,  productListParam.getArrDate());
        }else {
            persenter.sendQueryHotelCardRights(hotelId,  productListParam.getArrDate());
        }

    }

    private   MultiTypeAdapter    mAdapter;

    private void initView(View view)
    {
        mAdapter = new MultiTypeAdapter(getContext());

        String tempStr = goodsType.getGoodsTypeCode();

        if(HoteUtil.HOTEL_CARD_EQUITY_CODE.equals(tempStr)){

            productType = false;

        }else    if(HoteUtil.HOTEL_FAST_BUY_CODE.equals(tempStr)){


            productType = true;
        }

        wvLvData.setLayoutManager(new LinearLayoutManager(getContext()));
        wvLvData.setAdapter(mAdapter);

        wvLvData.post(new Runnable() {
            @Override
            public void run()
            {
                /**
                 * 请求该酒店的房间信息
                 */
                initRequest();
            }
        });

        View   viewRim = (LinearLayout) view.findViewById(R.id.viewRim);

        HotelProductRimView hotelProductRimView = new HotelProductRimView(viewRim,getContext());

        hotelProductRimView.setHotelDetailServiceBean(HotelLogicManager.getInstance().getHotelDetailServiceBean());
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        List<GiftComboBean> list = JSONArray.parseArray(o + "", GiftComboBean.class);

        for(GiftComboBean bean:list){

            bean.ifHomeGiftComboFlag = false;
        }

        mAdapter.clear();

        if (productType) {
            mAdapter.addAll(HotelFlashSaleHolder.class, list);
        } else {
            mAdapter.addAll(HotelCardEquityHolder.class, list);
        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }
}
